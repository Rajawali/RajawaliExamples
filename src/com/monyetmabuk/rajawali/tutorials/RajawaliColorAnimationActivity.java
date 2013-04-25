package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliColorAnimationActivity extends RajawaliExampleActivity{

	private RajawaliColorAnimationRenderer mRenderer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new RajawaliColorAnimationRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
	}
}
