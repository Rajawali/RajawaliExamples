package com.monyetmabuk.rajawali.tutorials.lights;

import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class DirectionalLightActivity extends RajawaliExampleActivity {
	private DirectionalLightRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new DirectionalLightRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}

}
