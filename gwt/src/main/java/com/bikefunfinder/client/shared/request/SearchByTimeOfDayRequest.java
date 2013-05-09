package com.bikefunfinder.client.shared.request;
/*
 * @author: tneuwerth
 * @created 3/19/13 10:33 PM
 */

import com.bikefunfinder.client.shared.model.GeoLoc;
import com.bikefunfinder.client.shared.model.Root;
import com.bikefunfinder.client.shared.constants.Settings;
import com.bikefunfinder.client.shared.model.json.Utils;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

import java.math.BigDecimal;

public final class SearchByTimeOfDayRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(Root root);
    }

    public static final class Builder {
        private SearchByTimeOfDayRequest.Callback callback;
        private BigDecimal longitude;
        private BigDecimal latitude;

        public Builder(final SearchByTimeOfDayRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final SearchByTimeOfDayRequest.Callback callback) {
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

        public SearchByTimeOfDayRequest send() {
            return new SearchByTimeOfDayRequest(this);
        }
    }

    private static final String URL = Settings.HOST + "FunService/rest/display/by_time_of_day/";
    //private static final String URL = "";

    private final SearchByTimeOfDayRequest.Callback callback;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private SearchByTimeOfDayRequest(final Builder builder) {
        callback = builder.callback;
        latitude = builder.latitude;
        longitude = builder.longitude;
        request = send();
    }

    private Request send() {
        Request request = null;

        final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlWithQuery());
        try {
            request = requestBuilder.sendRequest(null, getRequestCallback());
        } catch (final RequestException e) {
            e.printStackTrace();
        }

        return request;
    }

    private String getUrlWithQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(URL);
        builder.append("geoloc=");
        builder.append(latitude);
        builder.append(",");
        builder.append(longitude);

        return builder.toString();
    }

    private RequestCallback getRequestCallback() {
        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onError(final Request request, final Throwable exception) {
                Dialogs.alert("Error", "Unable to get by_time_of_day.", new Dialogs.AlertCallback() {
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
                    builder.append("Unable to get by_time_of_day.");
                    builder.append(" Status Code: ").append(statusCode);
                    builder.append("; Status Text: ").append(response.getStatusText());
                    Dialogs.alert("Error", builder.toString(), new Dialogs.AlertCallback() {
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
