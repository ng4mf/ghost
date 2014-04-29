package edu.cs2110.ghost;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends Activity {

	private int score;
	private TextView finalScore;
	
	public EndActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
		score = savedInstanceState.getInt("KillScore");
		
		//finalScore = getView()
	}

}
