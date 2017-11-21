package edu.orangecoastcollege.cs273.caffeinefinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

//TODO: (1) Implement the OnMapReadCallback interface for Google Maps
//TODO: First, you'll need to compile GoogleMaps in build.gradle
//TODO: and add permissions and your Google Maps API key in the AndroidManifest.xml
public class CaffeineListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Location> allLocationsList;
    private ListView locationsListView;
    private LocationListAdapter locationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffeine_list);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importLocationsFromCSV("locations.csv");

        allLocationsList = db.getAllCaffeineLocations();
        locationsListView = (ListView) findViewById(R.id.locationsListView);
        locationListAdapter = new LocationListAdapter(this, R.layout.location_list_item, allLocationsList);
        locationsListView.setAdapter(locationListAdapter);

        //TODO: (2) Load the support map fragment asynchronously
    }

    // TODO: (3) Implement the onMapReady method, which will add a special "marker" for our current location,
    // TODO: which is 33.671028, -117.911305  (MBCC 139)
    // TODO: Then add normal markers for all the caffeine locations from the allLocationsList.
    // TODO: Set the zoom level of the map to 15.0f


    // TODO: (4) Create a viewLocationsDetails(View v) method to create a new Intent to the
    // TODO: CaffeineDetailsActivity class, sending it the selectedLocation the user picked from the locationsListView
}
