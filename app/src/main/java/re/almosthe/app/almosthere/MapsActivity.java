package re.almosthe.app.almosthere;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import io.nlopez.smartlocation.SmartLocation;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    AlmostHereLocation AlmostHere;
    private static Context appContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        appContext = getApplicationContext();
        AlmostHere = new AlmostHereLocation();
        AlmostHere.setDatabaseId();

        Button share = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLink();
            }
        });


        SmartLocation.getInstance().start(
                getContext(),
                new SmartLocation.OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location, DetectedActivity detectedActivity) {

                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                        if (AlmostHere.dbId != null) {
                            AlmostHere.updateLocation(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
                        }
                    }
                });
    }

        public static Context getContext() {
            return appContext;
        }

        @Override
        protected void onResume() {
            super.onResume();
            setUpMapIfNeeded();
        }


        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();
                if (mMap != null) {
                    setUpMap();
                }
            }
        }

        private void shareLink() {

            if (AlmostHere.dbId != null) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Follow me using almost Here! http://live.almosthe.re/live?id=" + AlmostHere.dbId);
                startActivity(Intent.createChooser(intent, "Share"));
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Sorry you don't seem to have connected to the Almost Here Server yet :(";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }

        private void setUpMap() {
            mMap.setMyLocationEnabled(true);
        }

}
