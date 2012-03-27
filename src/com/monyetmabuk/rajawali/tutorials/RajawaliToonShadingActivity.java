package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliToonShadingActivity extends RajawaliExampleActivity {
	private RajawaliToonShadingRenderer mRenderer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliToonShadingRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
