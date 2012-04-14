package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliBezierActivity extends RajawaliExampleActivity {
	private RajawaliBezierRenderer mRenderer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliBezierRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
