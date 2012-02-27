package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliSimpleParticlesActivity extends RajawaliExampleActivity {
	private RajawaliSimpleParticlesRenderer mRenderer;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliSimpleParticlesRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}