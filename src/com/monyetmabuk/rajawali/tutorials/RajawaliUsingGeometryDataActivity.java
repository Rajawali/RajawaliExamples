package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliUsingGeometryDataActivity extends RajawaliExampleActivity {
	private RajawaliUsingGeometryDataRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliUsingGeometryDataRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
