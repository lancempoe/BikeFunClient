package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
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
    private List<Marker> markers = new ArrayList<Marker>();  //TODO WHY SAVE THEM?  NEVER REUSED.
    private Polygon polygon;
    private Polyline polyline;
//    private Marker myBicycleMarker;

    private LatLng center;
    private double zoom;
    private boolean isRecentUserActivity = false;

    private Presenter presenter;

    BikeRide bikeRide;
    HeaderPanel headerPanel;
    Button trackingRideButton;

    public GMapViewImpl() {
        main = new LayoutPanel();

        mapPanel = new FlowPanel();

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
                oBackButtonPressed(tapEvent);
            }
        });
        this.headerPanel = new HeaderPanel();
        this.headerPanel.setLeftWidget(backButton);
        mapPanel.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getLayoutCss().fillPanelExpandChild());
        main.add(headerPanel);

        trackingRideButton = new Button();
        trackingRideButton.setWidth("100%");
        trackingRideButton.setVisible(false);
        main.add(trackingRideButton);

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
        this.trackingRideButton.setVisible(true);
        this.trackingRideButton.setText("Start Tracking");
    }

    @Override
    public void displayPageName(String pageName) {
        this.headerPanel.setCenter(pageName);
    }

    protected void oBackButtonPressed(TapEvent event) {
        if (presenter != null) {
            presenter.onBackButtonPressed();
        }
    }

    @Override
    public void refresh() {
    }

    @Override
    public void clearMapInfo() {
    }


    @Override
    public void setMapInfo(final GeoLoc phoneGeoLoc,
                           List<BikeRide> list,
                           String cityNameText) {
        center = LatLng.create(phoneGeoLoc.getLatitude(), phoneGeoLoc.getLongitude());
        zoom = HERE_AND_NOW_ZOOM;

        if (map == null) {
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
        } else {
            if (!isRecentUserActivity) {
                map.panTo(center);
                map.setZoom(zoom);
            }
        }

        if (circle == null) {
            final CircleOptions circleOptions = createCircleOptions(map, center, METERS_IN_A_MILE *HEAR_AND_NOW_RADIUS);
            circle = Circle.create(circleOptions);
        } else {
            circle.setCenter(center);
            circle.setRadius(METERS_IN_A_MILE *HEAR_AND_NOW_RADIUS);
        }

        circle.clearClickListeners();
        circle.clearMouseOverListeners();

//        circle.addClickListener(new Circle.ClickHandler() {
//            @Override
//            public void handle(MouseEvent event) {
//                //Window.alert("It's a ME! " );
//            }
//        });

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

        //Phone Location
        AddAsMarker(phoneGeoLoc, null, ScreenConstants.TargetIcon.CLIENT);

        //Event Locations.
        for(final BikeRide bikeRide: list) {
            AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);
        }

//        if (polyline == null) {
//            final PolylineOptions polylineOptions = createPolylineOptions(map, center);
//            polyline = Polyline.create(polylineOptions);
//        } else {
//            polyline.getPath().push(center);
//        }
    }

    @Override
    public void setMapInfo(GeoLoc phoneGeoLoc, BikeRide bikeRide, String cityNameText) {

        center = LatLng.create(bikeRide.getLocation().getGeoLoc().getLatitude(),
                               bikeRide.getLocation().getGeoLoc().getLongitude());
        zoom = EVENT_ZOOM;

        if (map == null) {
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
        } else {
            if (!isRecentUserActivity) {
                map.panTo(center);
                map.setZoom(zoom);
            }
        }

        if (circle != null) {
            circle.setRadius(0);
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

        //Phone Location
        AddAsMarker(phoneGeoLoc, null, ScreenConstants.TargetIcon.CLIENT);

        //Starting location
        AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);

        //Ride leader
        if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null)   {
            AddAsMarker(bikeRide.getRideLeaderTracking().getGeoLoc(), null, ScreenConstants.TargetIcon.LEADER);
        }

        //Every other tracker
        if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) {
            for (int i = 0; i < bikeRide.getCurrentTrackings().length(); i++) {
                AddAsMarker(bikeRide.getCurrentTrackings().get(i).getGeoLoc(), null, ScreenConstants.TargetIcon.TRACKER);
            }
        }
    }

    private void AddAsMarker(GeoLoc geoLoc, final BikeRide bikeRide, ScreenConstants.TargetIcon icon) {
        final Double convertedLat = geoLoc.getLatitude();
        final Double convertedLong = geoLoc.getLongitude();

        final LatLng bikeRideLoc = LatLng.create(convertedLat, convertedLong);
        final MarkerOptions markerOptions = createMarkerOptions(map, bikeRideLoc, icon);
        final Marker bikeRideMarker = Marker.create(markerOptions);

        if(bikeRide==null) {
            bikeRideMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    drawInfoWindow(bikeRideMarker, bikeRide, event);
                }
            });
        }

        markers.add(bikeRideMarker);
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

    protected void drawInfoWindow(final Marker marker, BikeRide bikeRide, MouseEvent mouseEvent) {
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