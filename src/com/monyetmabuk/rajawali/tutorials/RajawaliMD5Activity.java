package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliMD5Activity extends RajawaliExampleActivity {
	private RajawaliMD5Renderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliMD5Renderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
