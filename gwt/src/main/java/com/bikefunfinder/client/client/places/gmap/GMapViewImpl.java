package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.constants.ScreenConstants.MapScreenType;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.google.maps.gwt.client.*;
import com.google.maps.gwt.client.GoogleMap.CenterChangedHandler;
import com.google.maps.gwt.client.GoogleMap.DragEndHandler;
import com.google.maps.gwt.client.GoogleMap.DragStartHandler;
import com.google.maps.gwt.client.GoogleMap.ZoomChangedHandler;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.*;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tim
 * Date: 3/16/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapViewImpl implements GMapDisplay {
    private static final double HERE_AND_NOW_ZOOM = 12;
    private static final double EVENT_ZOOM = 17;
    private static final int RESUME_AUTO_PAN_AND_ZOOM_DELAY_MILLIS = 10000;
    private static final double METERS_IN_A_MILE = 1609.34;
    private static final int HEAR_AND_NOW_RADIUS = 3;


    private final LayoutPanel main;
    private final HeaderButton  backButton;
    private final FlowPanel mapPanel;
    private final Timer resumeAutoPanAndZoomTimer;

    private GoogleMap map;
    private Circle circle;
    private LatLng center;
    private double zoom;
    private boolean isRecentUserActivity = false;
    private MapScreenType priorMapScreenType;
    private boolean resetMap = true;

    private List<Marker> markers = new ArrayList<Marker>();  //TODO WHY SAVE THEM?  NEVER REUSED.
    private Polygon polygon; //TODO WHAT IS THIS?
    private Polyline polyline;  //TODO WHAT IS THIS?
    private Marker myBicycleMarker;  //TODO WHAT IS THIS?

    private Presenter presenter;

    BikeRide bikeRide;
    HeaderPanel headerPanel;
    Button trackingRideButton;

    public GMapViewImpl() {
        main = new LayoutPanel();

        //Build header
        this.headerPanel = new HeaderPanel();
        main.add(headerPanel);

        //Add in the back button
        backButton = new HeaderButton();
        backButton.setText("Back");
        backButton.setBackButton(true);
//        if (MGWT.getOsDetection().isTablet()) {
//            backButton.setBackButton(false);
//            backButton.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getUtilCss().portraitonly());
//        } else {
//            backButton.setBackButton(true);
//        }
        backButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(final TapEvent tapEvent) {
                backButtonSelected(tapEvent);
            }
        });
        this.headerPanel.setLeftWidget(backButton);

        //Build the tracking button
        trackingRideButton = new Button();
        trackingRideButton.setWidth("100%");
        trackingRideButton.setVisible(false);
        main.add(trackingRideButton);

        //Build the map
        mapPanel = new FlowPanel();
        mapPanel.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getLayoutCss().fillPanelExpandChild());
        main.add(mapPanel);

        resumeAutoPanAndZoomTimer = new Timer() {
            @Override
            public void run() {
                isRecentUserActivity = false;
//                map.panTo(center);
//                map.setZoom(zoom);
            }
        };
    }

    @Override
    public Widget asWidget() {
        return main;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void display(BikeRide bikeRide) {
        this.bikeRide = bikeRide;
    }

    @Override
    public void displayPageName(String pageName) {
        this.headerPanel.setCenter(pageName);
    }

    protected void backButtonSelected(TapEvent event) {
        if (presenter != null) {
            presenter.backButtonSelected();
        }
    }

    @Override
    public void resetForHereAndNow(final GeoLoc centerGeoLoc) {
        if (!MapScreenType.HERE_AND_NOW.equals(priorMapScreenType)) {
            resetMap = true;
            priorMapScreenType = MapScreenType.HERE_AND_NOW;
        }
        trackingRideButton.setVisible(false);
        center = LatLng.create(centerGeoLoc.getLatitude(), centerGeoLoc.getLongitude());
        zoom = HERE_AND_NOW_ZOOM;
        if (circle != null) {
            circle.setVisible(true);
        }
    }
    @Override
    public void resetForEvent(final GeoLoc centerGeoLoc) {
        if (!MapScreenType.EVENT.equals(priorMapScreenType)) {
            resetMap = true;
            priorMapScreenType = MapScreenType.EVENT;
        }
        this.trackingRideButton.setVisible(true);
        this.trackingRideButton.setText("Start Tracking");
        center = LatLng.create(centerGeoLoc.getLatitude(), centerGeoLoc.getLongitude());
        zoom = EVENT_ZOOM;
        if (circle != null) {
            circle.setVisible(false);
        }
    }

    @Override
    public void setMapInfo(final GeoLoc phoneGpsLoc, List<BikeRide> list) {

        //Build the view of the map
        if (map == null || resetMap) {
            buildMap();
        } else {
            if (!isRecentUserActivity) {
                map.panTo(center);
                map.setZoom(zoom);
            }
        }

        //Draw the search radius circle
        if (circle == null || resetMap) {
            final CircleOptions circleOptions = createCircleOptions(map, center, METERS_IN_A_MILE * HEAR_AND_NOW_RADIUS);
            circle = Circle.create(circleOptions);
        } else {
            circle.setCenter(center);
            circle.setRadius(METERS_IN_A_MILE * HEAR_AND_NOW_RADIUS);
        }

        //Phone Location
        AddAsMarker(map, phoneGpsLoc, null, ScreenConstants.TargetIcon.CLIENT);

        //Event Locations.
        for(final BikeRide bikeRide: list) {
            AddAsMarker(map, bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);
        }

        //Reset the check
        resetMap = false;
    }

    @Override
    public void setMapInfo(GeoLoc phoneGpsLoc, BikeRide bikeRide) {

        //Build the view of the map
        if (map == null || resetMap) {
            buildMap();
        } else {
            map.panTo(center);
            map.setZoom(zoom);
        }

        //        //TODO WHAT IS THIS DOING?
//        if (myBicycleMarker == null)
//        {
//            final MarkerOptions markerOptions = createMarkerOptions(map, center, ScreenConstants.TargetIcon.CLIENT);
//            myBicycleMarker = Marker.create(markerOptions);
//            myBicycleMarker.addClickListener(new Marker.ClickHandler() {
//                @Override
//                public void handle(MouseEvent event) {
//                    HTML meWidget = new HTML("It's a me, mario!");
//                    meWidget.getElement().getStyle().setColor("black");
//                    drawInfoWindow(myBicycleMarker, meWidget.getElement(), event);
//                }
//            });
//        } else {
//            myBicycleMarker.setPosition(center);
//            myBicycleMarker.setVisible(true);
//        }

        //        if (polyline == null) {
//            final PolylineOptions polylineOptions = createPolylineOptions(map, center);
//            polyline = Polyline.create(polylineOptions);
//        } else {
//            polyline.getPath().push(center);
//        }

        //Phone Location
        AddAsMarker(map, phoneGpsLoc, null, ScreenConstants.TargetIcon.CLIENT);

        //Starting location
        AddAsMarker(map, bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);

        //Ride leader
        if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null)   {
            AddAsMarker(map, bikeRide.getRideLeaderTracking().getGeoLoc(), null, ScreenConstants.TargetIcon.LEADER);
        }

        //Every other tracker
        if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) {
            for (int i = 0; i < bikeRide.getCurrentTrackings().length(); i++) {
                AddAsMarker(map, bikeRide.getCurrentTrackings().get(i).getGeoLoc(), null, ScreenConstants.TargetIcon.TRACKER);
            }
        }

        //Reset the check
        resetMap = false;
    }

    private void buildMap() {
        final MapOptions mapOptions = createMapOptions(center, zoom);
        map = GoogleMap.create(mapPanel.getElement(), mapOptions);

        BicyclingLayer bicyclingLayer = BicyclingLayer.create();
        bicyclingLayer.setMap(map);

        map.addCenterChangedListener(new CenterChangedHandler() {
            @Override
            public void handle() {
                if (!map.getCenter().equals(center)) {
                    isRecentUserActivity = true;
                    resumeAutoPanAndZoomTimer.schedule(RESUME_AUTO_PAN_AND_ZOOM_DELAY_MILLIS);
                }
            }
        });
        map.addZoomChangedListener(new ZoomChangedHandler() {
            @Override
            public void handle() {
                if (map.getZoom() != zoom) {
                    isRecentUserActivity = true;
                    resumeAutoPanAndZoomTimer.schedule(RESUME_AUTO_PAN_AND_ZOOM_DELAY_MILLIS);
                }
            }
        });
        map.addDragStartListener(new DragStartHandler() {
            @Override
            public void handle() {
                isRecentUserActivity = true;
                resumeAutoPanAndZoomTimer.cancel();
            }
        });
        map.addDragEndListener(new DragEndHandler() {
            @Override
            public void handle() {
                resumeAutoPanAndZoomTimer.schedule(RESUME_AUTO_PAN_AND_ZOOM_DELAY_MILLIS);
            }
        });
    }

    private void AddAsMarker(GoogleMap myMap, GeoLoc geoLoc, final BikeRide bikeRide, ScreenConstants.TargetIcon icon) {
        final Double convertedLat = geoLoc.getLatitude();
        final Double convertedLong = geoLoc.getLongitude();

        final LatLng bikeRideLoc = LatLng.create(convertedLat, convertedLong);
        final MarkerOptions markerOptions = createMarkerOptions(myMap, bikeRideLoc, icon);
        final Marker bikeRideMarker = Marker.create(markerOptions);

        if(ScreenConstants.TargetIcon.EVENT.equals(icon)) {
            bikeRideMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    drawInfoWindow(bikeRideMarker, bikeRide, event);
                }
            });
        }

//        markers.add(bikeRideMarker);
    }

    private static MapOptions createMapOptions(final LatLng center, final double zoom) {
        final MapOptions mapOptions = MapOptions.create();
        mapOptions.setCenter(center);
        mapOptions.setZoom(zoom);
        mapOptions.setMapTypeId(MapTypeId.ROADMAP);
        mapOptions.setStreetViewControl(false);

        return mapOptions;
    }

    private static CircleOptions createCircleOptions(final GoogleMap map, final LatLng center, final double radius) {
        final CircleOptions circleOptions = CircleOptions.create();
        circleOptions.setMap(map);
        circleOptions.setCenter(center);
        circleOptions.setRadius(radius);
        circleOptions.setStrokeColor("#FF0000");
        circleOptions.setStrokeOpacity(0.8);
        circleOptions.setStrokeWeight(2);
        circleOptions.setFillColor("#FF0000");
        circleOptions.setFillOpacity(0.35);
        circleOptions.setClickable(false);

        return circleOptions;
    }

    private static MarkerOptions createMarkerOptions(final GoogleMap map, final LatLng center, ScreenConstants.TargetIcon icon)
    {
        final MarkerOptions markerOptions = MarkerOptions.create();
        markerOptions.setMap(map);
        MarkerImage markerImage = MarkerImage.create("icons/" + icon.getDisplayName());
        markerOptions.setIcon(markerImage);
        markerOptions.setPosition(center);
        return markerOptions;
    }


    private static PolylineOptions createPolylineOptions(final GoogleMap map, final LatLng point) {
        final MVCArray<LatLng> path = MVCArray.<LatLng> create();
        path.push(point);

        final PolylineOptions polylineOptions = PolylineOptions.create();
        polylineOptions.setMap(map);
        polylineOptions.setPath(path);
        polylineOptions.setStrokeColor("#FF0000");
        polylineOptions.setStrokeOpacity(0.8);
        polylineOptions.setStrokeWeight(2);

        return polylineOptions;
    }

    protected void drawInfoWindow(final Marker marker, final BikeRide bikeRide, MouseEvent mouseEvent) {
        if (marker == null || bikeRide == null) {
            return;
        }
        drawInfoWindow(marker,
                buildBikeRideHTMLWidgetFor(bikeRide),
                mouseEvent);
    }

    protected void drawInfoWindow(final Marker marker, Element content, MouseEvent mouseEvent) {

        if (content == null) {
            return;
        }

        InfoWindowOptions options = InfoWindowOptions.create();
        options.setContent(content);
        InfoWindow iw = InfoWindow.create(options);
        iw.open(map, marker);

        // If you want to clear widgets, Use options.clear() to remove the widgets
        // from map
        // options.clear();
    }


    private Element buildBikeRideHTMLWidgetFor(final BikeRide bikeRide) {
        FlowPanel fp = new FlowPanel();

        HomeScreenDisplay.Content stuff = new HomeScreenDisplay.Content(bikeRide);
        final SafeHtml safeHtml = stuff.getShortDescription();
        final HTML htmlWidget = new HTML(safeHtml);
        htmlWidget.getElement().getStyle().setColor("black");
        fp.add(htmlWidget);

        Anchor link = new Anchor("(more information)", presenter.provideTokenHrefFor(bikeRide));
        link.getElement().getStyle().setColor("black");
        fp.add(link);

        return fp.getElement();
    }
}