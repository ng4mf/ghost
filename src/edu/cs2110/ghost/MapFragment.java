package edu.cs2110.ghost;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;


public class MapFragment extends Fragment {
	private static final String TAG = "MapFragment";
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "GameActivity Initiated.");
        getActivity().setContentView(R.layout.fragment_map);
    }
}
