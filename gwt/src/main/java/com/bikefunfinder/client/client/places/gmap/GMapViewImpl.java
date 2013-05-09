package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.printer.JsDateWrapper;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.*;
import com.google.maps.gwt.client.GoogleMap.CenterChangedHandler;
import com.google.maps.gwt.client.GoogleMap.DragEndHandler;
import com.google.maps.gwt.client.GoogleMap.DragStartHandler;
import com.google.maps.gwt.client.GoogleMap.ZoomChangedHandler;
import com.google.maps.gwt.client.OverlayView.*;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
    private static final int RESUME_AUTO_PAN_AND_ZOOM_DELAY_MILLIS = 10000;

    private final LayoutPanel main;
    private final HeaderButton  backButton;
    private final FlowPanel mapPanel;
    private final Timer resumeAutoPanAndZoomTimer;

    private GoogleMap map;
    private Circle circle;
    private List<Marker> bikeRides = new ArrayList<Marker>();
    private Polygon polygon;
    private Polyline polyline;
    private Marker marker;

    private LatLng center;
    private double zoom;
    private boolean isRecentUserActivity = false;

    private Presenter presenter;

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
        final HeaderPanel headerPanel = new HeaderPanel();
        headerPanel.setCenter("Map");
        headerPanel.setLeftWidget(backButton);
        mapPanel.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getLayoutCss().fillPanelExpandChild());
        main.add(headerPanel);
        main.add(mapPanel);

        resumeAutoPanAndZoomTimer = new Timer() {
            @Override
            public void run() {
                isRecentUserActivity = false;

                map.panTo(center);
                map.setZoom(zoom);
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
    public void setMapInfo(final double latitude, final double longitude, final double accuracy,
                           List<BikeRide> list,
                           String cityNameText) {
        center = LatLng.create(latitude, longitude);
        zoom = DEFAULT_ZOOM;

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
            final CircleOptions circleOptions = createCircleOptions(map, center, accuracy);
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

        if (marker == null)
        {
            final MarkerOptions markerOptions = createMarkerOptions(map, center);
            marker = Marker.create(markerOptions);
            marker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    drawInfoWindow(marker, null, event);
                }
            });
        } else {
            marker.setPosition(center);
        }
//        Window.alert("BikeRideSize="+list.size());
        for(final BikeRide bikeRide: list) {

            final String latitude1 = bikeRide.getLocation().getGeoLoc().getLatitude();
            final String longitude1 = bikeRide.getLocation().getGeoLoc().getLongitude();
            final Double convertedLat = Double.parseDouble(latitude1);
            final Double convertedLong = Double.parseDouble(longitude1);

            final LatLng bikeRideLoc = LatLng.create(convertedLat, convertedLong);
            final MarkerOptions markerOptions = createMarkerOptions(map, bikeRideLoc);
            final Marker bikeRideMarker = Marker.create(markerOptions);

            bikeRideMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    drawInfoWindow(bikeRideMarker, bikeRide, event);
                }
            });
            bikeRides.add(bikeRideMarker);
        }

//        if (polyline == null) {
//            final PolylineOptions polylineOptions = createPolylineOptions(map, center);
//            polyline = Polyline.create(polylineOptions);
//        } else {
//            polyline.getPath().push(center);
//        }
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
        if (marker == null || mouseEvent == null) {
            return;
        }
        StringBuilder htmlString;
        SafeHtmlBuilder safeHtml = new SafeHtmlBuilder();
        if(bikeRide == null)
        {
            safeHtml.appendHtmlConstant("<h2>")
                    .appendEscaped("Hello, this is you!");

        }
        else {
        JsDateWrapper bikeRideDate = bikeRide.createJsDateWrapperRideStartTime();
        String timeString = bikeRideDate.toString("h:mm tt");


        safeHtml.appendHtmlConstant("<h2>")
                .appendEscaped(bikeRide.getBikeRideName())
                .appendHtmlConstant("</h2><p>")
                .appendEscaped(timeString)
                .appendHtmlConstant("</p><p>")
                .appendEscaped(bikeRide.getDetails())
                .appendHtmlConstant("</p>");
        }


        InfoWindowOptions options = InfoWindowOptions.create();
        options.setContent(safeHtml.toString());
        InfoWindow iw = InfoWindow.create(options);
        iw.open(map, marker);


        // If you want to clear widgets, Use options.clear() to remove the widgets
        // from map
        // options.clear();
    }
}