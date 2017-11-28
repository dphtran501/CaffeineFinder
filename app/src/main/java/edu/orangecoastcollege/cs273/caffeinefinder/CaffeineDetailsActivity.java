package edu.orangecoastcollege.cs273.caffeinefinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This activity displays the details about a coffee shop that was selected in
 * <code>CaffeineListActivity</code>, along with a Google Map image showing its location.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 21, 2017
 */
public class CaffeineDetailsActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private Location selectedLocation;

    /**
     * Initializes <code>CaffeineDetailsActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffeine_details);

        TextView namesTextView = (TextView) findViewById(R.id.detailsNameTextView);
        TextView addressTextView = (TextView) findViewById(R.id.detailsAddressTextView);
        TextView phoneTextView = (TextView) findViewById(R.id.detailsPhoneTextView);
        TextView latLngTextView = (TextView) findViewById(R.id.detailsLatLngTextView);

        selectedLocation = getIntent().getExtras().getParcelable("SelectedLocation");

        namesTextView.setText(selectedLocation.getName());
        addressTextView.setText(selectedLocation.getFullAddress());
        phoneTextView.setText(selectedLocation.getPhone());
        latLngTextView.setText(selectedLocation.getLatitude() + " " + selectedLocation.getLongitude());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailsCaffeineMapFragment);
        mapFragment.getMapAsync(this);
    }

    /**
     * Called when the map is ready to be used.
     *
     * @param googleMap Non-null instance of GoogleMap associated with MapFragment or MapView
     *                  defining call back.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // This method is called AFTER the map is loaded from Google Play services
        // At this point the map is ready

        // Store the reference to the Google Map in our member variable
        mMap = googleMap;
        // Custom marker (Big Blue one - mymarker.png)
        LatLng myPosition = new LatLng(selectedLocation.getLatitude(), selectedLocation.getLongitude());

        // Add a custom marker at "myPosition"
        mMap.addMarker(new MarkerOptions().position(myPosition).title(selectedLocation.getName()));

        // Center the camera over myPosition
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myPosition).zoom(15.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        // Move map to our cameraUpdate
        mMap.moveCamera(cameraUpdate);
    }
}
