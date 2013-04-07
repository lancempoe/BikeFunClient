package com.bikefunfinder.client.shared.request;

/**
 * Created with IntelliJ IDEA.
 * User: lancepoehler
 * Date: 4/7/13
 * Time: 12:57 PM
 */

import com.bikefunfinder.client.client.places.homescreen.HomeScreenActivity;
import com.bikefunfinder.client.shared.model.Root;
import com.google.gwt.http.client.*;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;

import java.math.BigDecimal;

public final class SearchByTimeOfDayForProfileRequest {
    public interface Callback {
        void onError();
        void onResponseReceived(Root root);
    }

    public static final class Builder {
        private SearchByTimeOfDayForProfileRequest.Callback callback;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String rideLeaderId;

        public Builder(final SearchByTimeOfDayForProfileRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
        }

        public Builder callback(final SearchByTimeOfDayForProfileRequest.Callback callback) {
            if (callback == null) {
                throw new NullPointerException();
            }

            this.callback = callback;
            return this;
        }

        public Builder rideLeaderId(final String rideLeaderId) {
            this.rideLeaderId = rideLeaderId;
            return this;
        }
        public Builder latitude(final BigDecimal latitude) {
            this.latitude = latitude;
            return this;
        }
        public Builder longitude(final BigDecimal longitude) {
            this.longitude = longitude;
            return this;
        }
        public Builder latitude(final double latitude) {
            this.latitude = new BigDecimal(latitude);
            return this;
        }

        public Builder longitude(final double longitude) {
            this.longitude = new BigDecimal(longitude);
            return this;
        }

        public SearchByTimeOfDayForProfileRequest send() {
            return new SearchByTimeOfDayForProfileRequest(this);
        }
    }

    private static final String URL = "http://appworks.timneuwerth.com/FunService/rest/display/by_time_of_day/";

    private final SearchByTimeOfDayForProfileRequest.Callback callback;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String rideLeaderId;
    private final Request request;

    public void cancel() {
        request.cancel();
    }

    public boolean isPending() {
        return request.isPending();
    }

    private SearchByTimeOfDayForProfileRequest(final Builder builder) {
        callback = builder.callback;
        latitude = builder.latitude;
        longitude = builder.longitude;
        rideLeaderId = builder.rideLeaderId;
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
        builder.append("/rideLeaderId=");
        builder.append(rideLeaderId);

        return builder.toString();
    }

    private RequestCallback getRequestCallback() {
        final RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void onError(final Request request, final Throwable exception) {
                Dialogs.alert("Error", "Unable to get by_time_of_day for rideleader.", new Dialogs.AlertCallback() {
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
                    builder.append("Unable to get by_time_of_day for profile.");
                    builder.append(" Status Code: ").append(statusCode);
                    builder.append("; Status Text: ").append(response.getStatusText());
                    Dialogs.alert("Error", builder.toString(), new Dialogs.AlertCallback() {
                        @Override
                        public void onButtonPressed() {
                            callback.onError();
                        }
                    });
                } else {
                    Root root = HomeScreenActivity.testObjectParse(response.getText());
                    callback.onResponseReceived(root);

                }
            }
        };

        return requestCallback;
    }
}
