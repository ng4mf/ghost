	package edu.cs2110.ghost;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class DifficultyDialog extends DialogFragment {
	
	private int level;
	private SeekBar bar;
	private Button exit;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        
			getDialog().setTitle("Choose Difficulty");
			level = 5;
			View v = inflater.inflate(R.layout.dialog_difficulty, container);
		
			bar = (SeekBar)v.findViewById(R.id.difficulty_chooser);
			bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					level = progress;
					
				}

				@Override
				public void onStartTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStopTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			exit = (Button)v.findViewById(R.id.exit_difficulty);
			exit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dismiss();
				}
				
			});
			return v;
	}
	
	@Override
	public void dismiss() {
		sendResult(5);
		super.dismiss();
	}
	
	private void sendResult(int INT_CODE) {
	    Intent i = new Intent();
	    Bundle b = new Bundle();
	    if (level < 1)
	    	level = 1;
	    b.putInt("Level", level);
	    i.putExtras(b);
	    getTargetFragment().onActivityResult(getTargetRequestCode(), INT_CODE, i);
	}
}
