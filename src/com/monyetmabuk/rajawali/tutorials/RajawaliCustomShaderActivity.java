package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliCustomShaderActivity extends RajawaliExampleActivity {
	private RajawaliCustomShaderRenderer mRenderer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliCustomShaderRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}