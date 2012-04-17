package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliMultipleLightsActivity extends RajawaliExampleActivity {
	private RajawaliMultipleLightsRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliMultipleLightsRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
