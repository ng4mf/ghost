package edu.cs2110.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";
	private GoogleMap mMap;
	private MapFragment mMapFragment;
	private GoogleMapOptions options = new GoogleMapOptions();
	
	private ArrayList<Ghosts> ghosts = new ArrayList<Ghosts>();
	private Map<Ghosts, Marker> ghostMap = new HashMap<Ghosts, Marker>();
	private Map<Ghosts, Location> ghostLocationMap = new HashMap<Ghosts, Location>();
	private GhostThread thread;
	private Player player;
	
	private Marker userMarker;
	private MarkerOptions userMarkerOptions;
	private Location userLocation;
	
	private LocationManager manager;
	private LocationListener listen;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity_fragment);
		//Log.d(TAG, "Made GameActivity");
		Intent i = getIntent();
		//player = (Player)i.getSerializableExtra("player");
		Bundle stats = i.getExtras();
		
		//Player(String name, int maxHealth, int power, double currency,
		//double attackDistance)
		player = new Player (stats.getString("name"), stats.getInt("maxHealth"),
				stats.getInt("power"), stats.getInt("currency"), stats.getDouble("attack"));
		Log.d(TAG, player.getUserName());
		Log.d(TAG, "" + player.getHealth());
		Log.d(TAG, "" + player.getPower());
		Log.d(TAG, "" + player.getCurrency());
		Log.d(TAG, "" + player.getAttackRadius());
		
		int x = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		if (x != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(x, this, 0);
			Log.d(TAG, "maps not installed?");
		}
		
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.gameContainer) != null) {
        	//Log.d(TAG, "gameContainer exists");
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
           // Log.d(TAG, "MapFragment exists: " + !(mMapFragment == null));
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            setUpMapIfNeeded();
            // Add the fragment to the 'fragment_container' FrameLayout

    		
            getFragmentManager().beginTransaction()
                    .replace(R.id.gameContainer, mMapFragment).commit();
        }
		
	}
	
	private void setUpUserLocation() {
		//Log.d("UserLoc Setup", "Starting method");
		// Acquire a reference to the system Location Manager
		manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		listen = new LocationListener() {
		    public void onLocationChanged(Location location) {
			      // Called when a new location is found by the location provider.
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listen);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listen);
		
		
		//Log.d("UserLoc Setup", "Calling last location");
		Criteria c = new Criteria();
		c.setHorizontalAccuracy(3);
		String bestProvider = manager.getBestProvider(c, true);
		userLocation = manager.getLastKnownLocation(bestProvider);
		if (userLocation == null) {
			bestProvider = "network";
			userLocation = manager.getLastKnownLocation(bestProvider);
		}
		
		player.setLocation(userLocation);
		
		//Log.d("UserLoc Setup", "Last Loc: " + userLocation.getLatitude() + ", " + userLocation.getLongitude());
		if (userMarker == null) {
			userMarkerOptions = new MarkerOptions()
				.position(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()))
				.title("Ghost")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.user));
			userMarker = mMap.addMarker(userMarkerOptions);
			mMap.addMarker(userMarkerOptions);
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
		if (mMapFragment == null) {
			mMapFragment = new MapFragment() {
				@Override
	            public void onActivityCreated(Bundle savedInstanceState) {
	                super.onActivityCreated(savedInstanceState);
	                
        	    	mMap = this.getMap();
        	        // Check if we were successful in obtaining the map.
        	        if (mMap != null) {
        	            // The Map is verified. It is now safe to manipulate the map.
        	        	//Log.d(TAG, "Moving Camera");
        	        	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.036558,-78.507319), 13));
        	        	setUpStores();
        	        }
        	    }  
			};
		}
		return true;
	}
	
	private void setUpStores() {
		// 38.036558, -78.507319
		// UVA Bookstore Coordinates
		//Log.d(TAG, "Setting Store Up");
		mMap.addMarker(new MarkerOptions()
				.position(new LatLng(38.036558, -78.507319))
				.title("Store"));
		mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				//Log.d("Store Marker", "Registered Marker Click");
				DialogFragment hs = StoreDialog.newInstance(thread, player);
				FragmentManager fm = getFragmentManager();
				hs.show(fm, "Store");
				return false;
			}
		});

		setUpUserLocation();
		//generateGhost();
	}
	
	public void updateScreen(ArrayList<Ghosts> g) {
		//Log.d(TAG, "Updating");
		//Log.d(TAG, g.toString());
		ghosts = g;

		//Log.d("GameActivity", "User Location about to be updated");
		
		Criteria c = new Criteria();
		c.setHorizontalAccuracy(3);
		String bestProvider = manager.getBestProvider(c, true);
		userLocation = manager.getLastKnownLocation(bestProvider);
		if (userLocation == null) {
			bestProvider = "network";
			userLocation = manager.getLastKnownLocation(bestProvider);
		}
		
		player.setLocation(userLocation);
		
		//Log.d("UserLoc Setup", "Last Loc: " + userLocation.getLatitude() + ", " + userLocation.getLongitude());
		
		//Log.d("GameActivity", "User Location about to be updated");
		for (Ghosts ghost: ghosts) {
			//Log.d(TAG, "Gets into loop");
			//Log.d(TAG, "" + (ghostMap.keySet().size()));
			//Log.d(TAG, "" + (ghostMap.keySet().toString()));
			if (!ghostMap.keySet().contains(ghost)) {
				//Log.d(TAG, "Can't do if block");
				MarkerOptions a = new MarkerOptions()
					.position(new LatLng(ghost.getXCoord(), ghost.getYCoord()))
					.title("Ghost")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_sprite));
				Marker m = mMap.addMarker(a);
				mMap.addMarker(a);
				ghostMap.put(ghost, m);
				Location tempLoc = new Location("Ghost"); 
				tempLoc.setLatitude(ghost.getXCoord());
				tempLoc.setLongitude(ghost.getYCoord());
				ghostLocationMap.put(ghost, tempLoc);
			}
			else {				
				//Log.d(TAG, "Can't do else block");
				ghostMap.get(ghost).setPosition(new LatLng(ghost.getXCoord(), ghost.getYCoord()));
				Location tempLoc = new Location("Ghost"); 
				tempLoc.setLatitude(ghost.getXCoord());
				tempLoc.setLongitude(ghost.getYCoord());
				ghostLocationMap.put(ghost, tempLoc);
			}
		}
		
		for (Ghosts ghost: ghosts) {
			proximityCheck(ghost);
			//Toast toast = Toast.makeText(getApplicationContext(), "Ghost Nearby!", Toast.LENGTH_SHORT);
			//toast.show();
		}
	}
	
	private void proximityCheck(Ghosts ghost) {
		Log.d("GameActivity", "Distance to Ghost: " + userLocation.distanceTo(ghost.getLocation()));
		if (userLocation.distanceTo(ghost.getLocation()) < 100) {
			Log.d(TAG, "Proximate");
			Toast toast = Toast.makeText(getApplicationContext(), "Ghost Nearby!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (setUpMapIfNeeded() && thread == null) {
			thread = new GhostThread(this);
			thread.execute();
			
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (setUpMapIfNeeded() && thread == null) {
			thread = new GhostThread(this);
		}

    	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.036558,-78.507319), 13));
	}
}


