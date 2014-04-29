package edu.cs2110.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
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
	private String currentWeapon;
	private boolean ghostNear;
	
	private Marker userMarker;
	private MarkerOptions userMarkerOptions;
	private Location userLocation;
	
	private LocationManager manager;
	private LocationListener listen;
	/*
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private long lastUpdate = 0;
	private float last_x = 0;
	private float last_y = 0;*/
	
	private boolean playerKilled = false;
	
	private MediaPlayer mp;
	private MediaPlayer danger;
	
	private Toast toast;
	
	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<Ghosts> getGhosts() {
		return ghosts;
	}
	
	public void setGhosts(ArrayList<Ghosts> g) {
		ghosts = g;
	}
	
	@SuppressLint("ShowToast")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity_fragment);
		Intent i = getIntent();
		//player = (Player)i.getSerializableExtra("player");
		Bundle stats = i.getExtras();
		Log.d(TAG, "Got here");
		mp = MediaPlayer.create(getApplicationContext(), R.raw.ghost_music);
		mp.setLooping(true);
		mp.start();
		
		danger = MediaPlayer.create(getApplicationContext(), R.raw.ghost_danger);
		danger.setLooping(true);
		
		ghostNear = false;
		ghosts = new ArrayList<Ghosts>();
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
		
		toast = Toast.makeText(getApplicationContext(), "Ghost Nearby!", Toast.LENGTH_SHORT);
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
		
	/*	sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		*/
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
				Log.d(TAG, "Something is not screwing up");
				if (thread != null)
					thread.setRunning(true);
				return true;
			case R.id.inventory:
				thread.setRunning(false);
				InventoryDialog inventory = InventoryDialog.newInstance(player, thread);
				FragmentManager fm = getFragmentManager();
				inventory.show(fm, "Inventory");
				return true;
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
			public boolean onMarkerClick(Marker m) {
				//Log.d("Store Marker", "Registered Marker Click");
				if (m.getTitle().equals("Store")) {
					DialogFragment hs = StoreDialog.newInstance(thread, player);
					FragmentManager fm = getFragmentManager();
					hs.show(fm, "Store");
				}
				else if (m.getTitle().equals("Ghost")) {
					//Log.d(TAG, "Ghost click registered");
					currentWeapon = player.getWeapon();
					if (currentWeapon.equals("power")) {
						changeGhosts("killall", null);
						player.setWeapon("default");
					}
					
					for (Ghosts g: ghostMap.keySet()) {
						if (player.inRange(g)) {
							if (currentWeapon.equals("default") && ghostMap.get(g).equals(m)) {
								player.attackGhost(g);
							}
							else if (currentWeapon.equals("bomb")) {
								player.useBomb(g);
								player.setWeapon("default");
							}
							
							if (!currentWeapon.equals("power")) {
								Log.d(TAG, "Ghost Health: " + g.getHealth());
								if (g.getHealth() <= 0) {
									/*thread.cancel(true);
									while(!thread.isCancelled()) {}*/
	
									Log.d("About to loot", "Looting Ghosts");
									player.lootChance();
									changeGhosts("remove", g);
									break;
	//								thread.execute();
								}
							}
						}
					}
				}
				return false;
			}
		});

		setUpUserLocation();
		//generateGhost();
	}
	
	
	
	public void remove(Ghosts g) {
		ghostMap.get(g).remove();
		ghostMap.remove(g);
		ghostLocationMap.remove(g);
		ghosts.remove(g);
		player.killGhost();
	}
	
	private void updateScreen(ArrayList<Ghosts> g) {
		//ghosts = g;
		
		Criteria c = new Criteria();
		c.setHorizontalAccuracy(3);
		String bestProvider = manager.getBestProvider(c, true);
		userLocation = manager.getLastKnownLocation(bestProvider);
		if (userLocation == null) {
			bestProvider = "network";
			userLocation = manager.getLastKnownLocation(bestProvider);
		}
		
		player.setLocation(userLocation);
		
//		Log.d(TAG, "No trouble setting user location"); 
		
		for (Ghosts ghost: ghosts) {
			//Log.d(TAG, "Can Call on list of ghosts");
			if (!ghostMap.keySet().contains(ghost)) {
				//Log.d(TAG, "Can call dependent data structures");
				MarkerOptions a = new MarkerOptions()
					.position(new LatLng(ghost.getLocation().getLatitude(), ghost.getLocation().getLongitude()))
					.title("Ghost")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_sprite));

//				Log.d(TAG, "Made markeroptions at: " + ghost.getLocation().getLatitude() + ", " + 
//						ghost.getLocation().getLongitude());
				
				Marker m = mMap.addMarker(new MarkerOptions()
					.position(new LatLng(0, 0))
					.title("Ghost")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_sprite)));

//				Log.d(TAG, "made marker");
//				mMap.addMarker(a);

//				Log.d(TAG, "added marker to map");
				ghostMap.put(ghost, m);

//				Log.d(TAG, "put ghost and marker on map");
				
				Location tempLoc = new Location("Ghost"); 
				
				tempLoc.setLatitude(ghost.getXCoord());
				tempLoc.setLongitude(ghost.getYCoord());
				
				
				ghostLocationMap.put(ghost, tempLoc);
//				Log.d(TAG, "Can update dependent data structures");
			}
			else {				
//				Log.d(TAG, "Can start updating of existing ghosts");
				ghostMap.get(ghost).setPosition(new LatLng(ghost.getXCoord(), ghost.getYCoord()));
				Location tempLoc = new Location("Ghost"); 
				tempLoc.setLatitude(ghost.getXCoord());
				tempLoc.setLongitude(ghost.getYCoord());
				ghostLocationMap.put(ghost, tempLoc);
//				Log.d(TAG, "Completed ghost position update");
			}
		}
		boolean doubleCheck = false;
		for (Ghosts ghost: ghosts) {
			if (proximityCheck(ghost)) {
				ghostNear = true;
				doubleCheck = true;
				toast.show();
			}
		}
		if (!doubleCheck && ghostNear) {
			toast.cancel();
			ghostNear = false;
			if (danger.isPlaying())
				danger.pause();
			if (!mp.isPlaying())
				mp.start();
		}
		else if (doubleCheck && ghostNear) {
			toast.show();
			mp.pause();
			if (mp.isPlaying())
				mp.pause();
			if (!danger.isPlaying())
				danger.start();
		}
	}
	
	public synchronized void changeGhosts(String command, Ghosts g) {
		if (command.equals("add")) {
//			Log.d(TAG, "Almost there, adding ghost now");
			ghosts.add(g);
//			Log.d(TAG, "Added ghost");
		}
		else if (command.equals("remove")) {
//			Log.d(TAG, "Started Ghost removal process");
			remove(g);
//			Log.d(TAG, "Completed Ghost Removal");
		}
		else if (command.equals("update")) {
//			Log.d(TAG, "Updating");
			for (Ghosts ghost: ghosts)
				thread.determineMovement(ghost);
//			Log.d(TAG, "Completed update");
		}
		else if (command.equals("updateAll")) {
//			Log.d(TAG, command);
			updateScreen(ghosts);
//			Log.d(TAG, command + " complete");
		}
		else if (command.equals("attack")) {
			if (playerKilled)
				Log.d(TAG, "Something is calling this again");
			for (Ghosts ghost: ghosts) {
				if (ghost.attackPlayer(player)) {
					Log.d(TAG, "Ghost damaged player");
					if (player.getHealth() == 0) {
						Log.d(TAG, "Player killed");
						playerKilled = true;
						break;
					}
					if (playerKilled)
						break;
				}
				if (playerKilled)
					break;
			}
		}
		else if (command.equals("killall")) {
			for (Ghosts ghost: ghosts) {
				ghostMap.get(ghost).remove();
				ghostMap.remove(ghost);
				ghostLocationMap.remove(ghost);
				player.killGhost();
			}
			ghosts = new ArrayList<Ghosts>();
		}
		if (playerKilled) {
			thread.setCanceled(true);
			thread.cancel(true);
			endGame();
		}
	}
	
	private boolean proximityCheck(Ghosts ghost) {
		//Log.d("GameActivity", "Distance to Ghost: " + userLocation.distanceTo(ghost.getLocation()));
		if (userLocation.distanceTo(ghost.getLocation()) < player.getAttackRadius()) {
//			Log.d(TAG, "Proximate");
			return true;
		}
		return false;
	}

	private void endGame() {
		mp.stop();
		danger.stop();
		Intent i = new Intent(this, EndActivity.class);
		Bundle bundle = new Bundle();
		Log.d(TAG, "" + player.getScore());
		bundle.putInt("KillScore", player.getScore());
		i.putExtras(bundle);
		startActivity(i);
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
			//AsyncRunner = new Thread();
		}

    	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.036558,-78.507319), 13));
    	//sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    //sensorManager.unregisterListener(this);
	}

	/*@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			float x = event.values[0];
	        float y = event.values[1];
	        
	        
	        long curTime = System.currentTimeMillis();
	        
	        if ((curTime - lastUpdate) > 200) {
	            long diffTime = (curTime - lastUpdate);
	            lastUpdate = curTime;
	            
	            float xchange = Math.abs(x - last_x)/ diffTime / 100;
	            float ychange = Math.abs(y - last_y)/ diffTime / 100;
	            
	            player.getLocation().setLatitude(player.getLocation().getLatitude() + xchange);
	            player.getLocation().setLongitude(player.getLocation().getLongitude() + ychange);
	            
	            last_x = x;
	            last_y = y;
	        }
		}*/
	//}


}


