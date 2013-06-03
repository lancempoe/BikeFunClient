package com.bikefunfinder.client.shared.request.converters;
/*
 * @author: tneuwerth
 * @created 6/2/13 12:41 PM
 */

import com.bikefunfinder.client.shared.model.*;
import com.bikefunfinder.client.shared.model.json.Utils;

public class PayloadConverters {
    public static final JsonObjectConverter<Root> ROOT_JSON_OBJECT_CONVERTER = new JsonObjectConverter<Root>(){
        @Override
        public Root convert(String jsonTxt) {
            return Utils.castJsonTxtToJSOObject(jsonTxt);
        };
    };

    public static final JsonObjectConverter<BikeRide> BikeRide_JSON_OBJECT_CONVERTER = new JsonObjectConverter<BikeRide>(){
        @Override
        public BikeRide convert(String jsonTxt) {
            return Utils.castJsonTxtToJSOObject(jsonTxt);
        };
    };

    public static final JsonObjectConverter<User> User_JSON_OBJECT_CONVERTER = new JsonObjectConverter<User>(){
        @Override
        public User convert(String jsonTxt) {
            return Utils.castJsonTxtToJSOObject(jsonTxt);
        };
    };

    public static final JsonObjectConverter<AnonymousUser> AnonymousUser_JSON_OBJECT_CONVERTER = new JsonObjectConverter<AnonymousUser>(){
        @Override
        public AnonymousUser convert(String jsonTxt) {
            return Utils.castJsonTxtToJSOObject(jsonTxt);
        };
    };


    public static final JsonObjectConverter<Tracking> Tracking_JSON_OBJECT_CONVERTER = new JsonObjectConverter<Tracking>(){
        @Override
        public Tracking convert(String jsonTxt) {
            return Utils.castJsonTxtToJSOObject(jsonTxt);
        };
    };


    public static final JsonObjectConverter<NoOpResponseObject> NoOpResponse_JSON_OBJECT_CONVERTER = new JsonObjectConverter<NoOpResponseObject>(){
        @Override
        public NoOpResponseObject convert(String jsonTxt) {
            return NoOpResponseObject.NO_OP_RESPONSE_OBJECT;
        };
    };



}
