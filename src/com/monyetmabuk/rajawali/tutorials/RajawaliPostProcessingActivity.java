package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliPostProcessingActivity extends RajawaliExampleActivity {
	private RajawaliPostProcessingRenderer mRenderer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliPostProcessingRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
