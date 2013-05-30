package com.monyetmabuk.rajawali.tutorials.materials;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class SpecularAndAlphaActivity extends RajawaliExampleActivity {
	private SpecularAndAlphaRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new SpecularAndAlphaRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
