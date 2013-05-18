package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

import java.math.BigDecimal;

public final class SearchByProximityRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(Root root);
    }

    public static final class Builder {
        private SearchByProximityRequest.Callback callback;
        private BigDecimal longitude;
        private BigDecimal latitude;

        public Builder(final SearchByProximityRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final SearchByProximityRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }

        public Builder latitude(final GeoLoc geoLoc) {
            this.latitude = new BigDecimal(geoLoc.getLatitude());
            return this;
        }

        public Builder longitude(final GeoLoc geoLoc) {
            this.longitude = new BigDecimal(geoLoc.getLongitude());
            return this;
        }

        public SearchByProximityRequest send() {
            return new SearchByProximityRequest(this);
        }

        public SearchByProximityRequest sendAndDebug() {
            return new SearchByProximityRequest(this, false);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/display/by_proximity/";

    private final SearchByProximityRequest.Callback callback;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private SearchByProximityRequest(final Builder builder) {
        this(builder, false);
    }

    private SearchByProximityRequest(final Builder builder, boolean debug) {
        callback = builder.callback;
        latitude = builder.latitude;
        longitude = builder.longitude;
        request = send(debug);
    }

    private Request send(Boolean debug) {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlWithQuery(debug));
        try {
            request = requestBuilder.sendRequest(null, getRequestCallback());
        } catch (final RequestException e) {
            e.printStackTrace();
        }

        return request;
    }

    private String getUrlWithQuery(boolean debug) {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append("geoloc=");
        builder.append(latitude);
        builder.append(",");
        builder.append(longitude);

        if(debug) {
            Window.alert(builder.toString());
        }

        return builder.toString();
    }

    private RequestCallback getRequestCallback() {
        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onError(final Request request, final Throwable exception) {
                Dialogs.alert("Error", "Unable to get by_proximity.", new Dialogs.AlertCallback() {
                    @Override
                    public void onButtonPressed() {
                        callback.onError();
                    }
                });
            }

            @Override
            public void onResponseReceived(final Request request, final Response response) {
                final int statusCode = response.getStatusCode();
                if ((statusCode < 200) || (statusCode >= 300)) {
                    final StringBuilder builder = new StringBuilder();
                    builder.append(response.getText());
                    Dialogs.alert("Notice: ", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    Root root = Utils.castJsonTxtToJSOObject(response.getText());
                    callback.onResponseReceived(root);
                }
            }
        };

        return requestCallback;
    }
}
