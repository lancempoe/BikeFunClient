package com.bikefunfinder.client.shared.request.ratsnest;
/*
 * @author: tneuwerth
 * @created 5/27/13 5:53 PM
 */

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public class RequestCallbackSorter<T> implements RequestCallback {
    private final PoopHandlerStack<T> poopHandlerStack;

    public RequestCallbackSorter(PoopHandlerStack<T> poopHandlerStack) {
        this.poopHandlerStack = poopHandlerStack;
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
        final int statusCode = response.getStatusCode();

        if ((statusCode >= 200) && (statusCode < 300)) {
            poopHandlerStack.goodPoop(response);
        } else {
            poopHandlerStack.messyPoop(request);
        }
    }

    @Override
    public void onError(Request request, Throwable exception) {
        poopHandlerStack.messyPoop(request);
    }

    // the stuff below does not belong here
    public interface PoopHandlerStack<T> extends HandlesFilthyMesses<T>, HandlesGoodPoops<T> {
    }

    public interface HandlesGoodPoops<T> {
        public void goodPoop(Response response);
    }

    public interface HandlesFilthyMesses<T> {
        public void messyPoop(Request request);
    }
}
