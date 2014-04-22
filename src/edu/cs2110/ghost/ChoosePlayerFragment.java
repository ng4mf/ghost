package edu.cs2110.ghost;

import edu.cs2110.actors.Player;
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

public class ChoosePlayerFragment extends Fragment {
	private Button healthButton;
	private Button strengthButton;
	private Button reachButton;
	private static final String TAG = "ChoosePlayerFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActivity().setTitle(R.string.app_name);
		Log.d(TAG, "Choose Your Player Ability");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.character_choice_fragment, parent, false);

		healthButton = (Button) v.findViewById(R.id.create_health_character);
		healthButton.setText("Health");
		Log.d(TAG, "Position before OnClickListener");
		healthButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Player player = new Player("Health", 20, 1, 1000, 0.000074917); // 2*radius
																				// is
																				// half
																				// the
																				// coordinate
																				// width
																				// olsson
																				// hall
				Log.d(TAG, "Health Player Has Been Created And Registered");
				Intent i = new Intent(getActivity(), GameActivity.class);// modify
				startActivity(i);
			}
		});

		strengthButton = (Button) v
				.findViewById(R.id.create_strength_character);
		strengthButton.setText("Strength");
		strengthButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Player splayer = new Player("Strength", 10, 2, 1000,
						0.000074917); // 2*radius is half the coordinate width
										// olsson hall
				Log.d(TAG, "Strength Player Has Been Created And Registered");
				Intent i = new Intent(getActivity(), GameActivity.class);// modify
				startActivity(i);
			}
		});

		reachButton = (Button) v.findViewById(R.id.create_reach_character);
		reachButton.setText("Reach");
		reachButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Player player = new Player("Reach", 10, 1, 1000, 0.000149833); // 2*radius
																				// is
																				// the
																				// coordinate
																				// width
																				// olsson
																				// hall
				Log.d(TAG, "Reach Player Has Been Created And Registered");
				Intent i = new Intent(getActivity(), GameActivity.class);// modify
				startActivity(i);
			}
		});
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
