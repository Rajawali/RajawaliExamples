package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class Rajawali2000PlanesActivity extends RajawaliExampleActivity {
private Rajawali2000PlanesRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new Rajawali2000PlanesRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
