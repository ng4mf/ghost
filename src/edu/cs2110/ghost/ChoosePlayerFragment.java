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
	
	private static ChoosePlayerFragment c;
	private Button healthButton;
	private Button strengthButton;
	private Button reachButton;
	private Bundle b;
	private static int difficulty;
	private static final String TAG = "ChoosePlayerFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActivity().setTitle(R.string.app_name);
		Log.d(TAG, "Choose Your Player Ability");
	}

	public static ChoosePlayerFragment newInstance(int diff) {
		difficulty = diff;
		c = new ChoosePlayerFragment();
		return c;
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
				Log.d(TAG, "Health Player Has Been Created And Registered");
				Intent i = new Intent(getActivity(), GameActivity.class);// modify
				b = new Bundle();
				b.putString("name", "Health");
				b.putInt("maxHealth", 200);
				b.putInt("power", 1);
				b.putInt("currency", 1000);
				b.putDouble("attack", 200);
				b.putInt("level", difficulty);
				i.putExtras(b);
				startActivity(i);
			}
		});

		strengthButton = (Button) v
				.findViewById(R.id.create_strength_character);
		strengthButton.setText("Strength");
		strengthButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Strength Player Has Been Created And Registered");
				Log.d(TAG, getActivity().getTitle().toString());
				Log.d(TAG, "Not issue with activity");
				Intent i = new Intent(getActivity(), GameActivity.class);// modify
				Log.d(TAG, "Made intent");
				
				b = new Bundle();
				b.putString("name", "Health");
				b.putInt("maxHealth", 100);
				b.putInt("power", 2);
				b.putInt("currency", 1000);
				b.putDouble("attack", 200);
				b.putInt("level", difficulty);
				Log.d(TAG, "Made extras for bundle");
				i.putExtras(b);
				Log.d(TAG, "Registered extras");
				startActivity(i);
				Log.d(TAG, "Made Activity");
			}
		});

		reachButton = (Button) v.findViewById(R.id.create_reach_character);
		reachButton.setText("Reach");
		reachButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Reach Player Has Been Created And Registered");
				Intent i = new Intent(getActivity(), GameActivity.class);// modify

				b = new Bundle();
				b.putString("name", "Health");
				b.putInt("maxHealth", 100);
				b.putInt("power", 1);
				b.putInt("currency", 1000);
				b.putDouble("attack", 200);
				b.putInt("level", difficulty);
				i.putExtras(b);
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
