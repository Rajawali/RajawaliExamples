package com.monyetmabuk.rajawali.tutorials.curves;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class CatmullRomActivity extends RajawaliExampleActivity {
	private CatmullRomRenderer mRenderer;

	public void onCreate(Bundle savedInstanceState) {
		mMultisamplingEnabled = true;
		super.onCreate(savedInstanceState);
		mRenderer = new CatmullRomRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
