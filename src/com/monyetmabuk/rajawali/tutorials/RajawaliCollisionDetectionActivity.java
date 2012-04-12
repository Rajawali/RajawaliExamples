package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliCollisionDetectionActivity extends RajawaliExampleActivity {
	private RajawaliCollisionDetectionRenderer mRenderer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliCollisionDetectionRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
