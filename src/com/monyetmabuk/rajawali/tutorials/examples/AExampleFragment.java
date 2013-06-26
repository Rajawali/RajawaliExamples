package com.monyetmabuk.rajawali.tutorials.examples;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.monyetmabuk.rajawali.tutorials.R;

import rajawali.RajawaliFragment;
import rajawali.renderer.RajawaliRenderer;

public class AExampleFragment extends RajawaliFragment {
	
	protected RajawaliRenderer mRenderer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer.setSurfaceView(mSurfaceView);
		setRenderer(mRenderer);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mLayout = (FrameLayout) inflater.inflate(R.layout.rajawali_fragment,
				container, false);
		mLayout.addView(mSurfaceView);

		return mLayout;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mRenderer.onSurfaceDestroyed();
	}
	
}
