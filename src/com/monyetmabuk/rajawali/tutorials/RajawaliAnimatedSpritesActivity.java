package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliAnimatedSpritesActivity extends RajawaliExampleActivity {
	private RajawaliAnimatedSpritesRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliAnimatedSpritesRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
