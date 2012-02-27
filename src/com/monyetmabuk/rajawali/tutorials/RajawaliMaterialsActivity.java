package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliMaterialsActivity extends RajawaliExampleActivity {
	private RajawaliMaterialsRenderer mRenderer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliMaterialsRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
