package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliVideoTextureActivity extends RajawaliExampleActivity {
	private RajawaliVideoTextureRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliVideoTextureRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
