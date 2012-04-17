package com.monyetmabuk.rajawali.tutorials;

import android.os.Bundle;

public class RajawaliLinesActivity extends RajawaliExampleActivity {
	private RajawaliLinesRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new RajawaliLinesRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }

}
