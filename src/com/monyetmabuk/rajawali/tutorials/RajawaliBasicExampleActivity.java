package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliBasicExampleActivity extends RajawaliExampleActivity {
	private RajawaliBasicExampleRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliBasicExampleRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
