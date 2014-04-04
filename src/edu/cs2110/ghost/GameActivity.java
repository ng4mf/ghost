package edu.cs2110.ghost;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "GameActivity Initiated");
		setContentView(R.layout.fragment_map);
	}
}
