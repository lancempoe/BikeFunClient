package com.bikefunfinder.client.client.places.gmap;

import com.bikefunfinder.client.client.places.homescreen.HomeScreenDisplay;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.constants.ScreenConstants.MapScreenType;
import com.bikefunfinder.client.shared.css.AppBundle;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.google.maps.gwt.client.*;
import com.google.maps.gwt.client.GoogleMap.CenterChangedHandler;
import com.google.maps.gwt.client.GoogleMap.DragEndHandler;
import com.google.maps.gwt.client.GoogleMap.DragStartHandler;
import com.google.maps.gwt.client.GoogleMap.ZoomChangedHandler;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
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
public class GMapDisplayImpl implements GMapDisplay {
    private static final double HERE_AND_NOW_ZOOM = 12;
    private static final double EVENT_ZOOM = 17;
    private static final int RESUME_AUTO_PAN_AND_ZOOM_DELAY_MILLIS = 10000;
    private static final double METERS_IN_A_MILE = 1609.34;
    private static final int HERE_AND_NOW_RADIUS = 3;
    private static final String START_TRACKING = "Share ride location!";
    private static final String STOP_TRACKING = "Stop Tracking";


    private final LayoutPanel main;
    private final HeaderButton  backButton;
    private final FlowPanel mapPanel;

    private GoogleMap map;
    private Circle circle;
    private LatLng center;
    private double zoom;

    private MapScreenType priorMapScreenType;
    private boolean resetMap = true;

    private List<InfoWindow> inforWindows = new ArrayList<InfoWindow>();
    private List<Marker> markers = new ArrayList<Marker>();
    private static Polyline polyline;

    private Presenter presenter;

    private BikeRide bikeRide;
    private BikeRide priorBikeRide;

    private String userId;
    private HeaderPanel headerPanel;
    private Button trackingRideButton;

    public GMapDisplayImpl() {
        main = new LayoutPanel();

        //Build header
        this.headerPanel = new HeaderPanel();
        main.add(headerPanel);

        //Add in the back button
        backButton = new HeaderButton();
        backButton.setText("Back");
        backButton.setBackButton(true);

        backButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(final TapEvent tapEvent) {
                priorBikeRide = null;
                if (presenter != null) {
                    presenter.backButtonSelected();
                }
            }
        });
        this.headerPanel.setLeftWidget(backButton);

        //Build the tracking button
        trackingRideButton = new Button();
        trackingRideButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent tapEvent) {
                if (presenter != null) {
                    presenter.trackingRideButtonSelected();
                }
            }
        });

        trackingRideButton.setVisible(false);
        main.add(trackingRideButton);

        //Build the map
        mapPanel = new FlowPanel();
        mapPanel.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getLayoutCss().fillPanelExpandChild());
        main.add(mapPanel);

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
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendHtmlConstant("<p class=\"eventHeader\">");
        safeHtmlBuilder.appendEscaped(pageName);
        safeHtmlBuilder.appendHtmlConstant("</p>");
        this.headerPanel.setRightWidget(new HTML(safeHtmlBuilder.toSafeHtml().asString()));
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
        priorMapScreenType = MapScreenType.EVENT;

        //Clear out the markers
        for (Marker marker : markers) {
            marker.setMap((GoogleMap)null);
        }

        trackingRideButton.setVisible(true);
        center = LatLng.create(centerGeoLoc.getLatitude(), centerGeoLoc.getLongitude());
        zoom = EVENT_ZOOM;
        if (circle != null) {
            circle.setVisible(false);
        }
    }

    //We can move this to a common area if we want to use outside of this class
    private boolean isEqualBikeRide(BikeRide bikeRide, BikeRide priorBikeRide) {
        if ((bikeRide != null && priorBikeRide != null) &&
             bikeRide.getId().equals(priorBikeRide.getId())) {
            return true;
        }
        return false;
    }

    private void refreshMap() {
        if (map != null) {
            map.triggerResize();
        }
    }

    @Override
    public void truffleShuffle() {
        if(mapPanel!=null && mapPanel.isAttached()) {
            refreshMap(); // maybe? lance says it's only after the map is built.. so.. did it get better? if not nuke
        }
    }

    @Override
    public void resetPolyLine() {
        if(polyline!=null) {
            polyline.setMap(null);
            polyline = null;
        }
    }

    @Override
    public void setTrackingButtonText(boolean isTracking) {
        if (isTracking) {
            Logger.getLogger("").log(Level.INFO, "StopTracking button text");
            this.trackingRideButton.setText(STOP_TRACKING);
        } else {
            Logger.getLogger("").log(Level.INFO, "StartTracking button text");
            this.trackingRideButton.setText(START_TRACKING);
        }
    }

    @Override
    public void setupMapDisplayForHereAndNow(final GeoLoc phoneGpsLoc, List<BikeRide> list) {

        //Build the view of the map
        if (map == null || resetMap) {
            buildMap();
        }

        //Draw the search radius circle
        if (circle == null || resetMap) {
            final CircleOptions circleOptions = createCircleOptions(map, center, METERS_IN_A_MILE * HERE_AND_NOW_RADIUS);
            circle = Circle.create(circleOptions);
        } else {
            circle.setCenter(center);
            circle.setRadius(METERS_IN_A_MILE * HERE_AND_NOW_RADIUS);
        }

        //Phone Location
        AddAsMarker(phoneGpsLoc, null, ScreenConstants.TargetIcon.CLIENT);

        //Event Locations.
        for(final BikeRide bikeRide: list) {

            //If tracking then show the location of the ride leader or first track.
            if (bikeRide.isCurrentlyTracking()) {
                if (bikeRide.getRideLeaderTracking() != null &&
                    bikeRide.getRideLeaderTracking().getGeoLoc() != null) {
                    AddAsMarker(bikeRide.getRideLeaderTracking().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);
                } else {
                    AddAsMarker(bikeRide.getCurrentTrackings().get(0).getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);
                }

            //If not tracking then show the start of the ride.
            } else {
                AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT);
            }
        }

        //Reset the check
        refreshMap();
        resetMap = false;
    }

    @Override
    public void setupMapToDisplayBikeRide(GeoLoc phoneGpsLoc, BikeRide bikeRide, boolean reCenterReZoom, boolean isTracking) {

        //Reset map if new view.
        if (!isEqualBikeRide(bikeRide, priorBikeRide)) {
            resetMap = true;
        }
        priorBikeRide = bikeRide;

        //Build the view of the map
        if (map == null || resetMap) {
            buildMap();
        } else if (reCenterReZoom) {
            map.panTo(center);
            map.setZoom(zoom);
        }

        if (isTracking) {
            //Draw on the users screen.
            LatLng printLineLatLng = LatLng.create(phoneGpsLoc.getLatitude(), phoneGpsLoc.getLongitude());
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
        if (this.userId.equals(bikeRide.getRideLeaderId())) {
            AddAsMarker(phoneGpsLoc, null, ScreenConstants.TargetIcon.LEADER);
        } else if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getId() != null) {
            AddAsMarker(bikeRide.getRideLeaderTracking().getGeoLoc(), null, ScreenConstants.TargetIcon.LEADER);
        }

        //Phone Location (only print if not the ride leader)
        if (!this.userId.equals(bikeRide.getRideLeaderId())) {
            AddAsMarker(phoneGpsLoc, null, ScreenConstants.TargetIcon.CLIENT);
        }

        //Every other tracker
        if (bikeRide.getCurrentTrackings() != null && bikeRide.getCurrentTrackings().length() > 0) {
            for (int i = 0; i < bikeRide.getCurrentTrackings().length(); i++) {
                AddAsMarker(bikeRide.getCurrentTrackings().get(i).getGeoLoc(), null, ScreenConstants.TargetIcon.TRACKER);
            }
        }

        //Reset the check
        refreshMap();
        resetMap = false;
    }

    private void buildMap() {
        final MapOptions mapOptions = createMapOptions(center, zoom);
        map = GoogleMap.create(mapPanel.getElement(), mapOptions);

        BicyclingLayer bicyclingLayer = BicyclingLayer.create();
        bicyclingLayer.setMap(map);

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

//        //TODO This will be implemented in version 2
//        //Only available in iphone
//        if (MGWT.getOsDetection().isIPhone()) {
//            Anchor link = new Anchor("(more information)", presenter.provideTokenHrefFor(bikeRide));
//            link.getElement().getStyle().setColor("black");
//            fp.add(link);
//        }

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