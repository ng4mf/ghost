package edu.cs2110.ghost;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";
	
	
	private GoogleMap mMap;
	private MapFragment mMapFragment;
	private GoogleMapOptions options = new GoogleMapOptions();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity_fragment);
		Log.d(TAG, "Made GameActivity");
		
		/*
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.gameContainer) != null) {
        	Log.d(TAG, "container exists");
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            
            Log.d(TAG, "Almost made map");
            // Create a new Fragment to be placed in the activity layout
            //GameMapFragment firstFragment = new GameMapFragment();
            mMapFragment = MapFragment.newInstance();
            Log.d(TAG, "Made map");
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //firstFragment.setArguments(getIntent().getExtras());
            
            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.gameContainer, mMapFragment).commit();
        }
        */
        //Log.d(TAG, "view not made");
		
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.gameContainer) != null) {
        	Log.d(TAG, "gameFragmentContainer exists");
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }


            //set initial state of map
        	options.mapType(GoogleMap.MAP_TYPE_NORMAL)
        		.compassEnabled(false)
        		.rotateGesturesEnabled(false)
        		.tiltGesturesEnabled(false)
        		.zoomControlsEnabled(true);
        	
            // Create a new Fragment to be placed in the activity layout
            mMapFragment = MapFragment.newInstance(options);
            //mMapFragment = MapFragment.newInstance();
            Log.d(TAG, "Successfully made GameMapFragment");
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //mMapFragment.setArguments(getIntent().getExtras());
            setUpMapIfNeeded();
            Log.d(TAG, "Set up Map");
            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.gameContainer, mMapFragment).commit();
        }
		
	}
	
	
	private void setUpMapIfNeeded() {
		//mMapFragment = MapFragment.newInstance();
		//FragmentTransaction trans = getFragmentManager().beginTransaction();
		//trans.add(R.id.mapContainer, mMapFragment).commit();
		
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
		Log.d(TAG, "Setting Store Up");
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
	
	@Override
	public void onStart() {
		super.onStart();
		setUpMapIfNeeded();
    	//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.036558,-78.507319), 13));
    	//setUpStores();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setUpMapIfNeeded();
    	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.036558,-78.507319), 13));
	}
}
