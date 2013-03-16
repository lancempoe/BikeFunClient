package com.googlecode.gwtphonegap.showcase.client.contact;

import java.util.LinkedList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.contacts.Contact;
import com.googlecode.gwtphonegap.client.contacts.ContactError;
import com.googlecode.gwtphonegap.client.contacts.ContactFindCallback;
import com.googlecode.gwtphonegap.client.contacts.ContactFindOptions;
import com.googlecode.gwtphonegap.collection.shared.CollectionFactory;
import com.googlecode.gwtphonegap.collection.shared.LightArray;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;
import com.googlecode.gwtphonegap.showcase.client.model.DemoContact;

public class ContactActivity extends NavBaseActivity implements ContactDisplay.Presenter {

  private final PhoneGap phoneGap;
  private final ContactDisplay display;

  public ContactActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.display = clientFactory.getContactDisplay();
    this.phoneGap = clientFactory.getPhoneGap();

  }

  // protected void bind() {
  //
  // display.getCreateButton().addClickHandler(new ClickHandler() {
  //
  // @Override
  // public void onClick(ClickEvent event) {
  // String value = display.getCreateValue().getValue();
  //
  // if (value == null || "".equals(value)) {
  // display.getCreateFeedback().setHTML("please enter a name");
  // }
  //
  // Contact contact = phoneGap.getContacts().create();
  //
  // contact.getName().setFamilyName(value);
  //
  // contact.getPhoneNumbers().push(phoneGap.getContacts().getFactory().createContactField("home",
  // "012345 678", true));
  // contact.save();
  //
  // display.getCreateFeedback().setHTML("saved");
  //
  // }
  // });
  // }

  @Override
  public void onSearchTermEntered(String term) {

    if (term == null)
      return;
    if ("".equals(term)) {
      return;
    }

    LightArray<String> fields = CollectionFactory.<String> constructArray();

    fields.push("displayName");
    fields.push("name");

    ContactFindOptions findOptions = new ContactFindOptions(term, true);

    phoneGap.getContacts().find(fields, new ContactFindCallback() {

      @Override
      public void onSuccess(LightArray<Contact> contacts) {

        LinkedList<DemoContact> list = new LinkedList<DemoContact>();

        for (int i = 0; i < contacts.length(); i++) {
          DemoContact contact = new DemoContact(contacts.get(i).getName().getFormatted());
          list.add(contact);
        }

        if (contacts.length() == 0) {
          list.add(new DemoContact("nothing found...."));
        }

        display.display(list);
      }

      @Override
      public void onFailure(ContactError error) {
        // TODO improve error display
        Window.alert("error while searching for contacts");

      }
    }, findOptions);

  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    display.setPresenter(this);

    panel.setWidget(display);

  }
}
