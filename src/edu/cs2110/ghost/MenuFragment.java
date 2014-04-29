package edu.cs2110.ghost;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
	
	private static MenuFragment menu;
	private Button mapButton;
	private Button newGameButton;
	private Button difficultyAdjustor;
	private Button highScoresButton;
	private Button continuePausedGameButton;
	private int level;
	private static final String TAG = "MenuFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.app_name);
		Log.d(TAG, "Made Menu");
		level = 5;
	}
	
	public static MenuFragment newInstance() {
		menu = new MenuFragment();
		return menu;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_menu, parent, false);
		
		newGameButton = (Button)v.findViewById(R.id.start_new_game);
		newGameButton.setText("New Game");
		Log.d(TAG, "Position before OnClickListener");
		newGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "New Game Button Registered");
				ChoosePlayerFragment cp = ChoosePlayerFragment.newInstance(level);
				
	            getFragmentManager().beginTransaction()
	                    .add(R.id.fragmentContainer, cp).commit();
			}
		});/*
		
		continuePausedGameButton = (Button)v.findViewById(R.id.continue_paused_game);
		continuePausedGameButton.setText("Continue*");
		continuePausedGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            //Open paused game
			}
		});*/
		
		difficultyAdjustor = (Button)v.findViewById(R.id.difficulty_adjustor);
		difficultyAdjustor.setText("Adjust Difficulty");
		difficultyAdjustor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            DifficultyDialog d = new DifficultyDialog();
	            d.setTargetFragment(menu, 5);
	            FragmentManager g = getActivity().getFragmentManager();
	            d.show(g, "Difficulty Adjustor");
			}
		});
		
		highScoresButton = (Button)v.findViewById(R.id.high_scores);
		highScoresButton.setText("High Scores");
		highScoresButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	            //Open fragment to show high scores
				DialogFragment hs = new HighScoreFragment();
				FragmentManager fm = getActivity().getFragmentManager();
				hs.show(fm, "High Scores");
				
			}
		});
		return v;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 5){ //make sure fragment codes match up {
	        Bundle b = data.getExtras();
	        level = b.getInt("Level");
	    }
	}
	@Override
	public void onResume() {
		super.onResume();
	}
	
}
