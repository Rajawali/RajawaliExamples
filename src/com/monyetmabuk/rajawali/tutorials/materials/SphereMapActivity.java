package com.monyetmabuk.rajawali.tutorials.materials;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class SphereMapActivity extends RajawaliExampleActivity {
	private SphereMapRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new SphereMapRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
