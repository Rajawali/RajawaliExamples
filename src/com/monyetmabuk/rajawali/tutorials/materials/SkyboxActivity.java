package com.monyetmabuk.rajawali.tutorials.materials;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class SkyboxActivity extends RajawaliExampleActivity {
	private SkyboxRenderer mRenderer;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new SkyboxRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}