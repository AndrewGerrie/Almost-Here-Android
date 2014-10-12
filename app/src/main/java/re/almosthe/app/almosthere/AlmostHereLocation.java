package re.almosthe.app.almosthere;

import android.app.Activity;
import android.app.DownloadManager;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.HashMap;
import java.util.Map;


public class AlmostHereLocation {

    String dbId;
    final String BASE_UPDATE_URL = "https://almost-here.herokuapp.com/";
    final String HASH_VALUE = "<<placeholder>>";


    public void setDatabaseId() {
        System.out.println("new");
        Ion.with(MapsActivity.getContext())
                .load(BASE_UPDATE_URL + "setCollections/loc?latitude=51.5008&longitude=0.1247")
                .setHeader("secureHash", HASH_VALUE)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                            dbId = result.getResult().replace("\"", "");;
                    }
                });

    }

    public void updateLocation(String longitude, String latitude){

        Ion.with(MapsActivity.getContext())
                .load(BASE_UPDATE_URL + "/updateCollections/loc/"+ dbId +"?latitude="+ latitude +"&longitude=" + longitude)
                .setHeader("secureHash", HASH_VALUE)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        System.out.println("location updated");
                    }
                });
    }


}

