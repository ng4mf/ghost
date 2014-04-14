package edu.cs2110.ghost;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";
	private GoogleMap mMap;
	private MapFragment mMapFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "GameActivity Initiated");
		setContentView(R.layout.fragment_map);
		//while (((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap() == null)
			//continue;
		setUpMapIfNeeded();
	}
	
	private void setUpMapIfNeeded() {
		/*mMapFragment = MapFragment.newInstance();
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.add(R.id.mapContainer, mMapFragment).commit();
		*/
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	                            .getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	            // The Map is verified. It is now safe to manipulate the map.

	        }
	        setUpStores();
	    }
	}
	
	private void setUpStores() {
		// 38.036558, -78.507319
		// UVA Bookstore Coordinates
		mMap.addMarker(new MarkerOptions()
				.position(new LatLng(38.036558, -78.507319))
				.title("Store"));
		mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				Log.d("Store Marker", "Registered Marker Click");
				
				return false;
			}
		});
	}
}
