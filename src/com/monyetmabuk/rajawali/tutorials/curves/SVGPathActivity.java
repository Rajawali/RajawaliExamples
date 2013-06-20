package com.monyetmabuk.rajawali.tutorials.curves;

import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class SVGPathActivity extends RajawaliExampleActivity {
	private SVGPathRenderer mRenderer;

    public void onCreate(Bundle savedInstanceState) {
    	mMultisamplingEnabled = true;
        super.onCreate(savedInstanceState);
        mRenderer = new SVGPathRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        
        initLoader();
    }
}
