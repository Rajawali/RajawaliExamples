package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliSkyboxActivity extends RajawaliExampleActivity {
	private RajawaliSkyboxRenderer mRenderer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliSkyboxRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}