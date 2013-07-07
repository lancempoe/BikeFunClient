package com.bikefunfinder.client.client.places.fullmenubar;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.request.AnonymousRequest;
import com.bikefunfinder.client.shared.request.management.AnnonymousUserCacheStrategy;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 7/7/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class FullMenuBarActivityImpl extends MGWTAbstractActivity implements FullMenuBarActivity {
    public abstract ClientFactory getClientFactory();

    public void onNewButton() {
        getClientFactory().getPlaceController().goTo(new CreateScreenPlace(null, null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType()));
    }

    public void onSearchButton() {
        getClientFactory().getPlaceController().goTo(new SearchScreenPlace());
    }

    public void onLoginButton() {

        WebServiceResponseConsumer<AnonymousUser> callback = new WebServiceResponseConsumer<AnonymousUser>() {
            @Override
            public void onResponseReceived(AnonymousUser anonymousUser) {
                getClientFactory().getPlaceController().goTo(new ProfileScreenPlace(null, anonymousUser));
            }
        };
        new AnonymousRequest.Builder(callback).send();
    }

}
