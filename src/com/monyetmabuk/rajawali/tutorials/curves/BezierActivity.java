package com.monyetmabuk.rajawali.tutorials.curves;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

import android.os.Bundle;

public class BezierActivity extends RajawaliExampleActivity {
	private BezierRenderer mRenderer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderer = new BezierRenderer(this);
        mRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(mRenderer);
        initLoader();
    }
}
