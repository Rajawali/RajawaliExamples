package com.monyetmabuk.rajawali.tutorials.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.monyetmabuk.rajawali.tutorials.R;

public class FragmentActivity extends Activity implements OnClickListener {
	private EarthFragment mEarthFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_example_activity);
		
		configureButtons();
		
		FragmentManager fragmentManager = getFragmentManager();
		
		if(fragmentManager.findFragmentByTag("rajawali") == null)
		{
			mEarthFragment = new EarthFragment();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.add(R.id.fragment_right, mEarthFragment, "rajawali");
			transaction.commit();
		} else {
			mEarthFragment = (EarthFragment)fragmentManager.findFragmentByTag("rajawali");
		}
	}
	
	private void configureButtons()
	{
		Button button = (Button)findViewById(R.id.london);
		button.setOnClickListener(this);
		button = (Button)findViewById(R.id.jakarta);
		button.setOnClickListener(this);//--
		button = (Button)findViewById(R.id.san_francisco);
		button.setOnClickListener(this);
		button = (Button)findViewById(R.id.buenos_aires);
		button.setOnClickListener(this);
		button = (Button)findViewById(R.id.seoul);
		button.setOnClickListener(this);
		button = (Button)findViewById(R.id.moscow);
		button.setOnClickListener(this);
		button = (Button)findViewById(R.id.ankara);
		button.setOnClickListener(this);
		button = (Button)findViewById(R.id.cape_town);
		button.setOnClickListener(this);
		button = (Button)findViewById(R.id.toronto);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		if(mEarthFragment == null) return;
		
		switch(v.getId())
		{
		case R.id.jakarta:
			mEarthFragment.setLongitudeLatitudeAndElevation(106.8451300f, -6.2146200f, 3);
			break;
		case R.id.london:
			mEarthFragment.setLongitudeLatitudeAndElevation(-0.1257400f, 51.5085300f, 21);
			break;
		case R.id.san_francisco:
			mEarthFragment.setLongitudeLatitudeAndElevation(-122.4194200f, 37.7749300f, 60);
			break;
		case R.id.buenos_aires:
			mEarthFragment.setLongitudeLatitudeAndElevation(-58.3772300f, -34.6131500f, 68);
			break;
		case R.id.seoul:
			mEarthFragment.setLongitudeLatitudeAndElevation(126.9778300f, 37.5682600f, 47);
			break;
		case R.id.moscow:
			mEarthFragment.setLongitudeLatitudeAndElevation(37.6155600f, 55.7522200f, 151);
			break;
		case R.id.ankara:
			mEarthFragment.setLongitudeLatitudeAndElevation(32.8542700f, 39.9198700f, 887);
			break;
		case R.id.cape_town:
			mEarthFragment.setLongitudeLatitudeAndElevation(18.4166700f, -33.9166700f, 7);
			break;
		case R.id.toronto:
			mEarthFragment.setLongitudeLatitudeAndElevation(-79.4163000f, 43.7001100f, 173);
			break;
		}
	}
}
