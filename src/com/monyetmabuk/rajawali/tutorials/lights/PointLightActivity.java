package com.monyetmabuk.rajawali.tutorials.lights;

import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class PointLightActivity extends RajawaliExampleActivity {
	private PointLightRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new PointLightRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}

}
