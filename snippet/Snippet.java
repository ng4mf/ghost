package snippet;

public class Snippet {
	
	
	this.setContentView(R.layout.fragment_menu);
	FragmentManager fm = getFragmentManager();
	fm.beginTransaction().attach(new MenuFragment()).commit();
}

