package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class TerrainActivity extends RajawaliExampleActivity {
	private TerrainRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new TerrainRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
