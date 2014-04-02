package edu.cs2110.ghost;

import edu.cs2110.ghost.R;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;

public abstract class SingleFragmentActivity extends Fragment {
	protected abstract Fragment createFragment();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setContentView(R.layout.activity_fragment);
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}
