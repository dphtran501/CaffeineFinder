package edu.orangecoastcollege.cs273.caffeinefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

// (1) Implement the OnMapReadCallback interface for Google Maps
// First, you'll need to compile GoogleMaps in build.gradle
// and add permissions and your Google Maps API key in the AndroidManifest.xml

/**
 * This activity displays a list of coffee shops in the local area along with a Google Map image
 * of the locations of each of those coffee shops.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 21, 2017
 */
public class CaffeineListActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private DBHelper db;
    private List<Location> allLocationsList;
    private ListView locationsListView;
    private LocationListAdapter locationListAdapter;
    private GoogleMap mMap;

    /**
     * Initializes <code>CaffeineListActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffeine_list);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importLocationsFromCSV("locations.csv");

        allLocationsList = db.getAllCaffeineLocations();
        locationsListView = (ListView) findViewById(R.id.locationsListView);
        locationListAdapter = new LocationListAdapter(this, R.layout.location_list_item, allLocationsList);
        locationsListView.setAdapter(locationListAdapter);

        // (2) Load the support map fragment asynchronously
        // Instruct android to load a Google Map into our fragment (caffeineMapFragment)
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.caffeineMapFragment);
        mapFragment.getMapAsync(this);
    }

    // (3) Implement the onMapReady method, which will add a special "marker" for our current location,
    // which is 33.671028, -117.911305  (MBCC 139)

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
        LatLng myPosition = new LatLng(33.671028, -117.911305);

        // Add a custom marker at "myPosition"
        mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker)));

        // Center the camera over myPosition
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myPosition).zoom(15.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        // Move map to our cameraUpdate
        mMap.moveCamera(cameraUpdate);

        // Then add normal markers for all the caffeine locations from the allLocationsList.
        // Set the zoom level of the map to 15.0f

        // Now, let's plot each Location form the list with a standard marker
        for (Location location : allLocationsList)
        {
            LatLng caffeineLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(caffeineLocation).title(location.getName()));
        }

    }

    // (4) Create a viewLocationsDetails(View v) method to create a new Intent to the
    // CaffeineDetailsActivity class, sending it the selectedLocation the user picked from the locationsListView

    /**
     * Launches <code>CaffeineDetailsActivity</code> showing information about the <code>Location</code>
     * object that was clicked in the ListView.
     *
     * @param v The view that called this method.
     */
    public void viewLocationDetails(View v)
    {
        if (v instanceof LinearLayout)
        {
            LinearLayout selectedLayout = (LinearLayout) v;
            Location selectedLocation = (Location) selectedLayout.getTag();
            Log.i("CaffeineListActivity", selectedLocation.toString());
            Intent detailsIntent = new Intent(this, CaffeineDetailsActivity.class);
            detailsIntent.putExtra("SelectedLocation", selectedLocation);
            startActivity(detailsIntent);
        }
    }

}
