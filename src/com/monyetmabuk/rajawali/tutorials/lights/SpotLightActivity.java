package com.monyetmabuk.rajawali.tutorials.lights;

import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class SpotLightActivity extends RajawaliExampleActivity {
	private SpotLightRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new SpotLightRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}

}
