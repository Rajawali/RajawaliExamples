package com.monyetmabuk.rajawali.tutorials.cameras;

import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class OrthographiCamActivity extends RajawaliExampleActivity {
	private OrthographicCamRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new OrthographicCamRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
