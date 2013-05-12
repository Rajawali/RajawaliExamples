package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliSpecularAndAlphaActivity extends RajawaliExampleActivity {
	private RajawaliSpecularAndAlphaRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliSpecularAndAlphaRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
