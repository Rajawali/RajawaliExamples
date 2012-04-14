package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliLoadModelActivity extends RajawaliExampleActivity {
	private RajawaliLoadModelRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliLoadModelRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
