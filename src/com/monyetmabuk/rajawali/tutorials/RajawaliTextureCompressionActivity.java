package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliTextureCompressionActivity extends RajawaliExampleActivity {
	private RajawaliTextureCompressionRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliTextureCompressionRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
