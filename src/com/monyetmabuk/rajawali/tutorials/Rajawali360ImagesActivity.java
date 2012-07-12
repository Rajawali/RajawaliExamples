package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class Rajawali360ImagesActivity extends RajawaliExampleActivity {
	private Rajawali360ImagesRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new Rajawali360ImagesRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
