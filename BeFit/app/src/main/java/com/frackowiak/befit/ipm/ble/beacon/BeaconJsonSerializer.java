package com.frackowiak.befit.ipm.ble.beacon;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Class used to serialize Beacon with all the needed properties to JSON standard
 * Used by Gson library
 */
public class BeaconJsonSerializer implements JsonSerializer<Beacon> {

    @Override
    public JsonElement serialize(@NonNull Beacon beacon, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", beacon.getUuid());
        jsonObject.addProperty("address", beacon.getAddress());
        jsonObject.addProperty("rssi", beacon.getRssi());
        jsonObject.addProperty("txPower", beacon.getTxPower());
        return jsonObject;
    }

}
