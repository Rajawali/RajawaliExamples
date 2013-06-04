package com.monyetmabuk.rajawali.tutorials.lights;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class MultipleLightsActivity extends RajawaliExampleActivity {
	private MultipleLightsRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new MultipleLightsRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
