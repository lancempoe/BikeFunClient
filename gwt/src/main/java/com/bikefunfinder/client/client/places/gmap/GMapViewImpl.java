package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Tracking;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Node;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
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
    private static final double DEFAULT_ZOOM = 16;
    private static final double EVENT_ZOOM = 30;
    private static final int RESUME_AUTO_PAN_AND_ZOOM_DELAY_MILLIS = 10000;
    private static final double metersInAMile = 1609.34;

    private final LayoutPanel main;
    private final HeaderButton  backButton;
    private final FlowPanel mapPanel;
    private final Timer resumeAutoPanAndZoomTimer;

    private GoogleMap map;
    private Circle circle;
    private List<Marker> bikeRides = new ArrayList<Marker>();
    private Polygon polygon;
    private Polyline polyline;
    private Marker myBicycleMarker;

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
    public void setMapInfo(final GeoLoc phonesGeoLoc, final double accuracy,
                           List<BikeRide> list,
                           String cityNameText) {
        center = LatLng.create(phonesGeoLoc.getLatitude(), phonesGeoLoc.getLongitude());
        zoom = DEFAULT_ZOOM;

        if (map == null) {
            final MapOptions mapOptions = createMapOptions(center, zoom);
            map = GoogleMap.create(mapPanel.getElement(), mapOptions);

            BicyclingLayer bicyclingLayer = BicyclingLayer.create();
            bicyclingLayer.setMap(map);



            //Button trackRide = new Button();
            //trackRide.setText("TrackRide");
            //
            //FlowPanel widget = new FlowPanel();
            //final HTML titleText = new HTML("Ride Controls");
            //widget.add(titleText);
            //widget.add(trackRide);
            //
            ////copied from a turd who doesn't know how to set styles
            //DOM.setStyleAttribute(widget.getElement(), "background", "white");
            //DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
            //DOM.setStyleAttribute(widget.getElement(), "margin", "3px");
            //DOM.setStyleAttribute(widget.getElement(), "border", "3px solid #FF0000");
            //DOM.setStyleAttribute(titleText.getElement(), "color", "black");
            //MVCArray<Node> array = MVCArray.create();
            //array.push(widget.getElement());
            //map.getControls().set((int) ControlPosition.RIGHT_CENTER.getValue(), array);

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
            final CircleOptions circleOptions = createCircleOptions(map, center, metersInAMile*2);
            circle = Circle.create(circleOptions);
        } else {
            circle.setCenter(center);
            circle.setRadius(accuracy);
        }

        circle.addClickListener(new Circle.ClickHandler() {
            @Override
            public void handle(MouseEvent event) {
                //Window.alert("It's a ME! " );
            }
        });

        if (myBicycleMarker == null)
        {
            final MarkerOptions markerOptions = createMarkerOptions(map, center);
            myBicycleMarker = Marker.create(markerOptions);
            myBicycleMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    HTML meWidget = new HTML("It's a me, mario!");
                    meWidget.getElement().getStyle().setColor("black");
                    drawInfoWindow(myBicycleMarker, meWidget.getElement(), event);
                }
            });
        } else {
            myBicycleMarker.setPosition(center);
            myBicycleMarker.setVisible(true);
        }

        for(final BikeRide bikeRide: list) {

            AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide);
        }

//        if (polyline == null) {
//            final PolylineOptions polylineOptions = createPolylineOptions(map, center);
//            polyline = Polyline.create(polylineOptions);
//        } else {
//            polyline.getPath().push(center);
//        }
    }

    @Override
    public void setMapInfo(GeoLoc geoLoc, BikeRide bikeRide, String cityNameText) {

        center = LatLng.create(geoLoc.getLatitude(), geoLoc.getLongitude());
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

        if (myBicycleMarker == null)
        {
            final MarkerOptions markerOptions = createMarkerOptions(map, center);
            myBicycleMarker = Marker.create(markerOptions);
            myBicycleMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    HTML meWidget = new HTML("It's a me, mario!");
                    meWidget.getElement().getStyle().setColor("black");
                    drawInfoWindow(myBicycleMarker, meWidget.getElement(), event);
                }
            });
        } else {
            myBicycleMarker.setPosition(center);
            myBicycleMarker.setVisible(true);
        }

        //Starting location
        AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide);

        //Ride leader
        if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null)   {
            AddAsMarker(bikeRide.getRideLeaderTracking().getGeoLoc(), null);
        }

        //Every other tracker
        if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) {
            for (int i = 0; i < bikeRide.getCurrentTrackings().length(); i++) {
                AddAsMarker(bikeRide.getCurrentTrackings().get(i).getGeoLoc(), null);
            }
        }
    }

    private void AddAsMarker(GeoLoc geoLoc, final BikeRide bikeRide) {
        final Double convertedLat = geoLoc.getLatitude();
        final Double convertedLong = geoLoc.getLongitude();

        final LatLng bikeRideLoc = LatLng.create(convertedLat, convertedLong);
        final MarkerOptions markerOptions = createMarkerOptions(map, bikeRideLoc);
        final Marker bikeRideMarker = Marker.create(markerOptions);

        if(bikeRide==null) {
            bikeRideMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    drawInfoWindow(bikeRideMarker, bikeRide, event);
                }
            });
        }

        bikeRides.add(bikeRideMarker);
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

    private static MarkerOptions createMarkerOptions(final GoogleMap map, final LatLng center)
    {
        final MarkerOptions markerOptions = MarkerOptions.create();
        markerOptions.setMap(map);
        MarkerImage markerImage = MarkerImage.create("icons/rideIcon28.png");
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