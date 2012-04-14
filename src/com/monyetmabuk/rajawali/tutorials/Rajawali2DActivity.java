package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class Rajawali2DActivity extends RajawaliExampleActivity {
	private Rajawali2DRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new Rajawali2DRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
