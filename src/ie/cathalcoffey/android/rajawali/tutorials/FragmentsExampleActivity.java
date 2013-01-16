package ie.cathalcoffey.android.rajawali.tutorials;

import com.monyetmabuk.rajawali.tutorials.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class FragmentsExampleActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_example_activity);
		
		FragmentManager fragmentManager = getFragmentManager();
		
		if(fragmentManager.findFragmentByTag("listview") == null) {
		    RajawaliListFragment left = new RajawaliListFragment();   
		    FragmentTransaction transaction_left = fragmentManager.beginTransaction();
		    transaction_left.add(R.id.fragment_left, left, "listview");
		    transaction_left.commit();
		}
		
		if(fragmentManager.findFragmentByTag("rajawali") == null) {
			RajawaliLoadModel right = new RajawaliLoadModel();   
		    FragmentTransaction transaction_right = fragmentManager.beginTransaction();
		    transaction_right.add(R.id.fragment_right, right, "rajawali");
		    transaction_right.commit();
		}
	}

}