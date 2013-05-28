package com.monyetmabuk.rajawali.tutorials.primitives;

import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class ColoredLineSegmentsActivity extends RajawaliExampleActivity {
	private ColoredLineSegmentsRenderer mRenderer;

	public void onCreate(Bundle savedInstanceState) {
		mMultisamplingEnabled = true;
		super.onCreate(savedInstanceState);
		mRenderer = new ColoredLineSegmentsRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
