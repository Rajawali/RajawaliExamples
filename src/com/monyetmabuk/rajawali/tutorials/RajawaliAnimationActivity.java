package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliAnimationActivity extends RajawaliExampleActivity {
	private RajawaliAnimationRenderer mRenderer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliAnimationRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
