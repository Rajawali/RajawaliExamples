package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliBumpmapActivity extends RajawaliExampleActivity {
	private RajawaliBumpmapRenderer mRenderer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliBumpmapRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
