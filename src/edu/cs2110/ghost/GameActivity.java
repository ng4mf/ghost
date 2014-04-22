package edu.cs2110.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Images;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";
	private GoogleMap mMap;
	private MapFragment mMapFragment;
	private GoogleMapOptions options = new GoogleMapOptions();
	
	private ArrayList<Ghosts> ghosts = new ArrayList<Ghosts>();
	private Map<Ghosts, Marker> ghostMap = new HashMap<Ghosts, Marker>();
	private GhostThread thread;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity_fragment);
		Log.d(TAG, "Made GameActivity");
		
		
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.gameContainer) != null) {
        	Log.d(TAG, "gameContainer exists");
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		switch (item.getItemId()) {
			case R.id.pause_game:
				if (thread != null)
					thread.setRunning(false);
				return true;
			case R.id.play_game:
				if (thread != null)
					thread.setRunning(true);
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private boolean setUpMapIfNeeded() {
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
	        	return true;
	        }
	        
	    }
	    return false;
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
		//generateGhost();
	}
	
	/*private void generateGhost() {
		Log.d(TAG, "Setting Store Up");
		mMap.addMarker(new MarkerOptions()
				.position(new LatLng(38.036550, -78.507310))
				.title("Ghost")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_sprite)));
		mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				Log.d("Store Marker", "Registered Marker Click");
				return false;
			}
		});
	}*/
	
	public void updateScreen(ArrayList<Ghosts> g) {
		Log.d(TAG, "Updating");
		Log.d(TAG, g.toString());
		ghosts = g;
		for (Ghosts ghost: g)
			Log.d("updateScreen", ghost.toString());
		
		for (Ghosts ghost: ghosts) {
			Log.d(TAG, "Gets into loop");
			Log.d(TAG, "" + (ghostMap.keySet().toString()));
			Log.d(TAG, "" + (ghostMap.keySet().toString()));
			if (!ghostMap.keySet().contains(ghost)) {
				//Log.d(TAG, "Can't do if block");
				MarkerOptions a = new MarkerOptions()
					.position(new LatLng(ghost.getXCoord(), ghost.getYCoord()))
					.title("Ghost")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_sprite));
				Marker m = mMap.addMarker(a);
				ghostMap.put(ghost, m);
			}
			else {				
				//Log.d(TAG, "Can't do else block");
				ghostMap.get(ghost).setPosition(new LatLng(ghost.getXCoord(), ghost.getYCoord()));
			}
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (setUpMapIfNeeded() && thread == null) {
			Log.d(TAG, "onStart");
			thread = new GhostThread(this);
			thread.execute();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (setUpMapIfNeeded() && thread == null) {
			thread = new GhostThread(this);
			Log.d(TAG, "onResume");
			//thread.execute();
		}
    	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.036558,-78.507319), 13));
	}
	


	
}


