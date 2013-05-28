package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliCatmullRomActivity extends RajawaliExampleActivity {
	private RajawaliCatmullRomRenderer mRenderer;

	public void onCreate(Bundle savedInstanceState) {
		mMultisamplingEnabled = true;
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliCatmullRomRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
