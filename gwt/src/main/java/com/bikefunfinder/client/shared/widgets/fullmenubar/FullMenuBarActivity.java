package com.bikefunfinder.client.shared.widgets.fullmenubar;

import com.bikefunfinder.client.bootstrap.ClientFactory;
import com.bikefunfinder.client.client.places.createscreen.CreateScreenPlace;
import com.bikefunfinder.client.client.places.profilescreen.ProfileScreenPlace;
import com.bikefunfinder.client.client.places.searchscreen.SearchScreenPlace;
import com.bikefunfinder.client.gin.Injector;
import com.bikefunfinder.client.shared.model.AnonymousUser;
import com.bikefunfinder.client.shared.request.AnonymousRequest;
import com.bikefunfinder.client.shared.request.management.AnnonymousUserCacheStrategy;
import com.bikefunfinder.client.shared.request.management.WebServiceResponseConsumer;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 7/7/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class FullMenuBarActivity extends MGWTAbstractActivity implements FullMenuBarDisplay.Presenter {
    ClientFactory clientFactory;

    public FullMenuBarActivity()
    {
        this.clientFactory = clientFactory = Injector.INSTANCE.getClientFactory();
    }
    @Override
    public void onNewButton() {
        clientFactory.getPlaceController().goTo(new CreateScreenPlace(null, null, AnnonymousUserCacheStrategy.INSTANCE.getCachedType()));
    }

    @Override
    public void onSearchButton() {
        clientFactory.getPlaceController().goTo(new SearchScreenPlace());
    }

    @Override
    public void onLoginButton() {

        WebServiceResponseConsumer<AnonymousUser> callback = new WebServiceResponseConsumer<AnonymousUser>() {
            @Override
            public void onResponseReceived(AnonymousUser anonymousUser) {
                clientFactory.getPlaceController().goTo(new ProfileScreenPlace(null, anonymousUser));
            }
        };
        new AnonymousRequest.Builder(callback).send();
    }
}
