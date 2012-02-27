package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliMoreParticlesActivity extends RajawaliExampleActivity {
	private RajawaliMoreParticlesRenderer mRenderer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliMoreParticlesRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
