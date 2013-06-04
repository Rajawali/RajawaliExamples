package com.monyetmabuk.rajawali.tutorials.materials;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class VideoTextureActivity extends RajawaliExampleActivity {
	private VideoTextureRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new VideoTextureRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
