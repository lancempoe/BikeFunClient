package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.client.places.createscreen.shared.BikeRideCreateUtils;
import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.constants.ScreenConstants.MapScreenType;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final String START_TRACKING = "Start Tracking";
    private static final String STOP_TRACKING = "Stop Tracking";


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
    private boolean tracking = false;

    private List<InfoWindow> inforWindows = new ArrayList<InfoWindow>();
    private List<Marker> markers = new ArrayList<Marker>();
    private Polygon polygon; //TODO WHAT IS THIS?
    private Polyline polyline;

    private Presenter presenter;

    BikeRide bikeRide;
    private String userId;
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
        trackingRideButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent tapEvent) {
                Logger.getLogger("").log(Level.WARNING, "track pressed and setting to : " + !tracking);
                onUpdateRidePressed(tapEvent);
            }
        });
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
        if(allowTracking()) {
            trackingRideButton.setVisible(true);
        } else {
            trackingRideButton.setVisible(false);
        }
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void displayPageName(String pageName) {
        this.headerPanel.setCenter(pageName);
    }

    private void onUpdateRidePressed(TapEvent event) {
        tracking = !tracking;
        setTrackingButtonText();
        if (presenter != null) {
            presenter.trackingRideButtonSelected(tracking);
        }
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

        //Clear out the markers
        for (Marker marker : markers) {
            marker.setMap((GoogleMap)null);
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

        //Clear out the markers
        for (Marker marker : markers) {
            marker.setMap((GoogleMap)null);
        }

        this.trackingRideButton.setVisible(true);
        setTrackingButtonText();

        center = LatLng.create(centerGeoLoc.getLatitude(), centerGeoLoc.getLongitude());
        zoom = EVENT_ZOOM;
        if (circle != null) {
            circle.setVisible(false);
        }
    }

    private void setTrackingButtonText() {
        if (tracking) {
            Logger.getLogger("").log(Level.WARNING, "StopTracking button text");
            this.trackingRideButton.setText(STOP_TRACKING);
        } else {
            Logger.getLogger("").log(Level.WARNING, "StartTracking button text");
            this.trackingRideButton.setText(START_TRACKING);
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
        AddAsMarker(phoneGpsLoc, null, ScreenConstants.TargetIcon.CLIENT);

        //Event Locations.
        for(final BikeRide bikeRide: list) {
            AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);
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

        if (tracking) {
            //Draw on the users screen.
            LatLng printLineLatLng =  LatLng.create(phoneGpsLoc.getLatitude(), phoneGpsLoc.getLongitude());
            if (polyline == null) {
                final PolylineOptions polylineOptions = createPolylineOptions(map, printLineLatLng);
                polyline = Polyline.create(polylineOptions);
            } else {
                polyline.getPath().push(printLineLatLng);
            }
        }


        //Starting location
        AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);

        //Ride leader
        String rideLeaderTrackingUserId = "";
        if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null)   {
            AddAsMarker(bikeRide.getRideLeaderTracking().getGeoLoc(), null, ScreenConstants.TargetIcon.LEADER);
            rideLeaderTrackingUserId = bikeRide.getRideLeaderTracking().getTrackingUserId();
        }

        //Phone Location (only print if not the ride leader)
        if (!this.userId.equals(rideLeaderTrackingUserId)) {
            AddAsMarker(phoneGpsLoc, null, ScreenConstants.TargetIcon.CLIENT);
        }

        //Every other tracker
        if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) {
            for (int i = 0; i < bikeRide.getCurrentTrackings().length(); i++) {
                AddAsMarker(bikeRide.getCurrentTrackings().get(i).getGeoLoc(), null, ScreenConstants.TargetIcon.TRACKER);
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

    private void AddAsMarker(GeoLoc geoLoc, final BikeRide bikeRide, ScreenConstants.TargetIcon icon) {
        final Double convertedLat = geoLoc.getLatitude();
        final Double convertedLong = geoLoc.getLongitude();

        final LatLng bikeRideLoc = LatLng.create(convertedLat, convertedLong);
        final MarkerOptions markerOptions = createMarkerOptions(this.map, bikeRideLoc, icon);
        final Marker bikeRideMarker = Marker.create(markerOptions);

        markers.add(bikeRideMarker);

        if(ScreenConstants.TargetIcon.EVENT.equals(icon)) {
            bikeRideMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    for (InfoWindow infoWindow : inforWindows) {
                        infoWindow.close();
                    }
                    drawInfoWindow(bikeRideMarker, bikeRide, event);
                }
            });
        }

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
        inforWindows.add(iw);
        iw.open(map, marker);
    }


    private Element buildBikeRideHTMLWidgetFor(final BikeRide bikeRide) {
        FlowPanel fp = new FlowPanel();

        HomeScreenDisplay.Content stuff = new HomeScreenDisplay.Content(bikeRide);
        final SafeHtml safeHtml = stuff.getShortDescription();
        final HTML htmlWidget = new HTML(safeHtml);
        htmlWidget.getElement().getStyle().setColor("black");
        fp.add(htmlWidget);



        Anchor link = new Anchor("(more information)",
                presenter.provideTokenHrefFor(bikeRide),
                "_self");
        link.getElement().getStyle().setColor("black");
        fp.add(link);
        
        /*
        Button testButton = new Button();

        testButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(final TapEvent tapEvent) {
                Window.alert("woohoo a button!");
            }
        });

        testButton.setText("(more information)");
        DOM.setStyleAttribute(testButton.getElement(), "color", "black");
        fp.add(testButton);
        */

        return fp.getElement();
    }

    private boolean allowTracking()
    {
        if(this.bikeRide.isTrackingAllowed() || userId.equals(this.bikeRide.getRideLeaderId())) {
            JsDate currentTime = JsDate.create();
            if(currentTime.getTime() + ScreenConstants.MinimumTimeBeforeTrackingAllowed < this.bikeRide.getRideStartTime())
            {
                //Nope! Too Early
                return false;
            } else if(currentTime.getTime() - ScreenConstants.MaximumTimeAfterTrackingAllowed > this.bikeRide.getRideStartTime()) {
                //Nope! Too late
                return false;
            } else {
                return true;
            }
        } else {
            //Nope! Not Allowed
            return false;
        }
    }
}