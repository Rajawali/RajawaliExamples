package com.monyetmabuk.rajawali.tutorials.materials;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class AnimatedSpritesActivity extends RajawaliExampleActivity {
	private AnimatedSpritesRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new AnimatedSpritesRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
