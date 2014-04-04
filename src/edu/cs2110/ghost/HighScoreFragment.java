package edu.cs2110.ghost;

import edu.cs2110.ghost.*;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class HighScoreFragment extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//View v = getActivity().getLayoutInflater().inflate(R.layout.layout_high_scores, null);
		builder.setMessage(R.string.high_scores).setNegativeButton(R.string.okay,
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// User says OK, exits dialog
						
					}
				});
		return builder.create();
	}
}
