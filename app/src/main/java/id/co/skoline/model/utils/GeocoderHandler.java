package id.co.skoline.model.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GeocoderHandler extends Handler {

    String locationAddress;
    @Override
    public void handleMessage(Message message) {

        switch (message.what) {
            case 1:
                Bundle bundle = message.getData();
                locationAddress = bundle.getString("address");
                break;
            default:
                locationAddress = null;
        }
        Log.i("place",locationAddress);
        setLocation(locationAddress);
    }
    public void setLocation (String location){
       this.locationAddress=location;
    }
    public String getLocation(){
        return locationAddress;
    }
}


