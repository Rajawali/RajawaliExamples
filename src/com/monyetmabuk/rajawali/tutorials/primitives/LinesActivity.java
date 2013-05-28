package com.monyetmabuk.rajawali.tutorials.primitives;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class LinesActivity extends RajawaliExampleActivity {
	private LinesRenderer mRenderer;
	
    public void onCreate(Bundle savedInstanceState) {
		mMultisamplingEnabled = true;
        super.onCreate(savedInstanceState);
        mRenderer = new LinesRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }

}
