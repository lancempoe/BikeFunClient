package com.bikefunfinder.client.client.places.gmaphomescreen;

import com.bikefunfinder.client.shared.Tools.BikeRideHelper;
import com.bikefunfinder.client.shared.Tools.HtmlTools;
import com.bikefunfinder.client.shared.constants.ScreenConstants;
import com.bikefunfinder.client.shared.model.BikeRide;
import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.widgets.base.MainMenuHeaderPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.*;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: lance poehler
 * Date: 3/16/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMapHomeDisplayGwtImpl extends Composite implements GMapHomeDisplay {
    private static final double HERE_AND_NOW_ZOOM = 12;
    private static final double METERS_IN_A_MILE = 1609.34;
    public static final int HERE_AND_NOW_RADIUS = 3;
    private static final String CIRCLE_HEX_COLOR = "#4E8D34";

    private static boolean showHereAndNowOnly = true; //Default to showing all rides.
    private static final String SHOW_All_RIDES = "Show All Rides";
    private static final String SHOW_HERE_AND_NOW_RIDES = "Show Current Rides";
    boolean wasAdjusted = false;

    interface MyStyle extends CssResource {
        String buttonTreatment();
    }
    @UiField MyStyle style;

    private static OverviewDisplayGwtImplUiBinder uiBinder = GWT.create(OverviewDisplayGwtImplUiBinder.class);

    interface OverviewDisplayGwtImplUiBinder extends UiBinder<Widget, GMapHomeDisplayGwtImpl> {
    }

    private final FlowPanel mapPanel;

    @UiField
    LayoutPanel main;

    @UiField
    FlowPanel footerFlowPanel;

    @UiField
    Button mainScreenToggleButton;

    @UiField
    Button showHideToggleButton;

    @UiField
    Button refreshButton;

    @UiField
    MainMenuHeaderPanel headerPanel;

    private GoogleMap map;
    private Circle circle;
    private LatLng center;
    private double zoom;

    private List<InfoWindow> infoWindows = new ArrayList<InfoWindow>();
    private List<Marker> markers = new ArrayList<Marker>();
    private Presenter presenter;

    public GMapHomeDisplayGwtImpl() {

        initWidget(uiBinder.createAndBindUi(this));

        mainScreenToggleButton.addStyleName(style.buttonTreatment());
        mainScreenToggleButton.addStyleName("icon-globe");
        mainScreenToggleButton.addStyleName("icon-large");
        showHideToggleButton.addStyleName(style.buttonTreatment());
        refreshButton.addStyleName(style.buttonTreatment());
        refreshButton.addStyleName("icon-refresh");
        refreshButton.addStyleName("icon-large");
        //TODO find way for this buttons to work with visually impaired people.
        //right now is stays "unreadable text"

        //Build the map
        mapPanel = new FlowPanel();
        mapPanel.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getLayoutCss().fillPanelExpandChild());
        main.add(mapPanel);
    }

    @UiHandler("mainScreenToggleButton")
    protected void onMainScreenToggleButton(TapEvent event) {
        if (presenter != null) {
            presenter.onMainScreenToggleButton();
        }
    }

    @UiHandler("showHideToggleButton")
    protected void onShowHideToggleButton(TapEvent event) {
        showHereAndNowOnly = !showHereAndNowOnly;
        if (presenter != null) {
            presenter.onShowHideToggleButton();
        }
    }

    @UiHandler("refreshButton")
    protected void onRefreshButton(TapEvent event) {
        if (presenter != null) {
            presenter.onRefreshButton();
        }
        centerZoom();
    }

    private void centerZoom() {
        map.panTo(center);
        map.setZoom(zoom);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void display(GeoLoc geoLoc, List<BikeRide> bikeRides) {

        clearMarkers();
        if (map == null) {
            center = LatLng.create(geoLoc.getLatitude(), geoLoc.getLongitude());
            zoom = HERE_AND_NOW_ZOOM;
            if (circle != null) {
                circle.setVisible(true);
            }

            buildMapView();
            drawRideShed();
        }
        placeAllPins(geoLoc, bikeRides);
        setMainScreenToggleButtonText();
    }

    private void setMainScreenToggleButtonText() {
        if (showHereAndNowOnly) {
            this.showHideToggleButton.setText(SHOW_All_RIDES);
        } else {
            this.showHideToggleButton.setText(SHOW_HERE_AND_NOW_RIDES);
        }
    }

    private void adjustMainPanelSize(int mainSize) {
        if(main==null) {
            return;
        }

        main.setHeight(mainSize + "px");
        Logger.getLogger("").log(Level.INFO, "window adjusted: " + mainSize + "px");
        wasAdjusted = true;
    }

    /**
     * Clear out the markers
     */
    private void clearMarkers() {
        for (Marker marker : markers) {
            marker.setMap((GoogleMap)null);
        }
    }

    @Override
    public void setTitle(String cityNameText) {
        headerPanel.setTitle(cityNameText);
    }

    @Override
    public void setMainSize(int mainSize) {
        if(!wasAdjusted) {
            adjustMainPanelSize(mainSize);
        }
    }

    private void pinCurrentLocationOfEvents(List<BikeRide> bikeRides) {
        //Event Locations.
        for(final BikeRide bikeRide: bikeRides) {

            //EXCLUDE IF NEEDED
            if (showHereAndNowOnly && !BikeRideHelper.isHereAndNow(bikeRide)) {
                continue;
            }


            //If tracking then show the location of the ride leader or first track.
            BikeRideHelper.Content content = new BikeRideHelper.Content(bikeRide);
            if (bikeRide.isCurrentlyTracking()) {
                if (bikeRide.getRideLeaderTracking() != null && bikeRide.getRideLeaderTracking().getGeoLoc() != null) {
                    AddAsMarker(bikeRide.getRideLeaderTracking().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.LEADER, content.getShortDescriptionForBike());
                } else {
                    AddAsMarker(bikeRide.getCurrentTrackings().get(0).getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.LEADER, content.getShortDescriptionForBike());
                }

            //If not tracking then show the start of the ride.
            } else {
                AddAsMarker(bikeRide.getLocation().getGeoLoc(), bikeRide, ScreenConstants.TargetIcon.EVENT, content.getShortDescriptionForStart());
            }
        }
    }

    /**
     * Draw the search radius circle
     */
    private void drawRideShed() {
        if (circle == null) {
            final CircleOptions circleOptions = createCircleOptions(map, center, METERS_IN_A_MILE * HERE_AND_NOW_RADIUS);
            circle = Circle.create(circleOptions);
        } else {
            circle.setCenter(center);
            circle.setRadius(METERS_IN_A_MILE * HERE_AND_NOW_RADIUS);
        }
    }

    private void placeAllPins(GeoLoc phoneGpsLoc, List<BikeRide> bikeRides) {
        pinBlueBike(phoneGpsLoc);
        pinCurrentLocationOfEvents(bikeRides);
    }

    /**
     * Phone Location (only print if not the ride leader)
     * @param phoneGpsLoc
     */
    private void pinBlueBike(GeoLoc phoneGpsLoc) {
        SafeHtmlBuilder safeHtmlBuilder;SafeHtml safeHtml;
        safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.appendHtmlConstant(HtmlTools.H1_RIDENAME);
        safeHtmlBuilder.appendHtmlConstant(ScreenConstants.YOU);
        safeHtmlBuilder.appendHtmlConstant(HtmlTools.H1_CLOSE_TAG);
        AddAsMarker(phoneGpsLoc, null, ScreenConstants.TargetIcon.CLIENT, safeHtmlBuilder.toSafeHtml());
    }

    /**
     * Build the view of the map
     */
    private void buildMapView() {
        final MapOptions mapOptions = createMapOptions(center, zoom);
        map = GoogleMap.create(mapPanel.getElement(), mapOptions);

        BicyclingLayer bicyclingLayer = BicyclingLayer.create();
        bicyclingLayer.setMap(map);
    }

    private void AddAsMarker(GeoLoc geoLoc, final BikeRide bikeRide, ScreenConstants.TargetIcon icon, final SafeHtml safeHtml) {
        final Double convertedLat = geoLoc.getLatitude();
        final Double convertedLong = geoLoc.getLongitude();

        final LatLng bikeRideLoc = LatLng.create(convertedLat, convertedLong);
        final MarkerOptions markerOptions = createMarkerOptions(this.map, bikeRideLoc, icon);
        final Marker bikeRideMarker = Marker.create(markerOptions);

        markers.add(bikeRideMarker);

        if(safeHtml != null) {
            bikeRideMarker.addClickListener(new Marker.ClickHandler() {
                @Override
                public void handle(MouseEvent event) {
                    for (InfoWindow infoWindow : infoWindows) {
                        infoWindow.close();
                    }
                    drawInfoWindow(bikeRideMarker, bikeRide, event, safeHtml);
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

    //30 minute rideshed
    private static CircleOptions createCircleOptions(final GoogleMap map, final LatLng center, final double radius) {
        final CircleOptions circleOptions = CircleOptions.create();
        circleOptions.setMap(map);
        circleOptions.setCenter(center);
        circleOptions.setRadius(radius);
        circleOptions.setStrokeColor(CIRCLE_HEX_COLOR);
        circleOptions.setStrokeOpacity(0.8);
        circleOptions.setStrokeWeight(2);
        circleOptions.setFillColor(CIRCLE_HEX_COLOR);
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

    protected void drawInfoWindow(final Marker marker, final BikeRide bikeRide, MouseEvent mouseEvent, SafeHtml safeHtml) {
        if (marker == null) {
            return;
        }

        drawInfoWindow(marker,
                       buildBikeRideHTMLWidgetFor(bikeRide, safeHtml),
                       mouseEvent);
    }

    protected void drawInfoWindow(final Marker marker, Element content, MouseEvent mouseEvent) {

        if (content == null) {
            return;
        }

        InfoWindowOptions options = InfoWindowOptions.create();
        options.setContent(content);
        InfoWindow iw = InfoWindow.create(options);
        infoWindows.add(iw);
        iw.open(map, marker);
    }

    private Element buildBikeRideHTMLWidgetFor(final BikeRide bikeRide, SafeHtml safeHtml) {
        FlowPanel fp = new FlowPanel();

        if (bikeRide != null) {
            //        //TODO This will be implemented in version 2
            //        //Only available in iphone
            //        if (MGWT.getOsDetection().isIPhone()) {
            //            Anchor link = new Anchor("(more information)", presenter.provideTokenHrefFor(bikeRide));
            //            link.getElement().getStyle().setColor("black");
            //            fp.add(link);
            //        }
        }

        final HTML htmlWidget = new HTML(safeHtml);
        htmlWidget.getElement().getStyle().setColor("black");
        fp.add(htmlWidget);

        return fp.getElement();
    }
}