package edu.cs2110.ghost;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HighScoreFragment extends DialogFragment {
	
	private TextView score;
	private Button exit;
	private int high;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        
			getDialog().setTitle("Inventory");
			
			View v = inflater.inflate(R.layout.high_scores, container);
		
			score = (TextView)v.findViewById(R.id.high_score);
			SharedPreferences prefs = getActivity().getSharedPreferences("myPrefsKey", 
                    Context.MODE_PRIVATE);
			high = prefs.getInt("key", 0); //0 is the default value
			score.setText(""+high);
			
			exit = (Button)v.findViewById(R.id.exit_high_scores);
			exit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dismiss();
				}
				
			});
			
			return v;
	}
}
