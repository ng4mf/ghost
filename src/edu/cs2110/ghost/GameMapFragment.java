package edu.cs2110.ghost;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GameMapFragment extends Fragment {
	private static final String TAG = "GameMapFragment";
	private GoogleMap mMap;
	private static MapFragment mMapFragment;
	private GoogleMapOptions options = new GoogleMapOptions();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.app_name);
		Log.d(TAG, "Made GameActivity");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_map, parent, false);
		// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
		Log.d(TAG, "MapFrag onCreatView called");
        if (getActivity().findViewById(R.id.mapContainer) != null) {
        	Log.d(TAG, "gameFragmentContainer exists");
            setUpMapIfNeeded();
            Log.d(TAG, "Set up Map");
            
            //set initial state of map
        	options.mapType(GoogleMap.MAP_TYPE_NORMAL)
        		.compassEnabled(false)
        		.rotateGesturesEnabled(false)
        		.tiltGesturesEnabled(false)
        		.zoomControlsEnabled(true);
        	
    		mMapFragment = MapFragment.newInstance(options);
    		setUpMapIfNeeded();
    		
    		getActivity().getFragmentManager().beginTransaction()
            	.add(R.id.mapContainer, mMapFragment).commit();
        }
		
		return v;
	}
	
	private void setUpMapIfNeeded() {
		/*mMapFragment = MapFragment.newInstance();
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.add(R.id.mapContainer, mMapFragment).commit();
		*/
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	    	
	    	Log.d(TAG, "Made it to setting up of map");
	        mMap = mMapFragment.getMap();
	        Log.d(TAG, "" + (mMap == null));
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	            // The Map is verified. It is now safe to manipulate the map.
	        	Log.d(TAG, "Moving Camera");
	        	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.036558,-78.507319), 13));
	        	setUpStores();
	        }
	        
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
