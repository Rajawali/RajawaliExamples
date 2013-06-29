package com.monyetmabuk.rajawali.tutorials.examples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.RajawaliFragment;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.monyetmabuk.rajawali.tutorials.R;

public abstract class AExampleFragment extends RajawaliFragment {

	protected RajawaliRenderer mRenderer;

	private ProgressBar mImageViewLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = createRenderer();
		mRenderer.setSurfaceView(mSurfaceView);
		setRenderer(mRenderer);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mLayout = (FrameLayout) inflater.inflate(R.layout.rajawali_fragment,
				container, false);

		mLayout.addView(mSurfaceView);

		mLayout.findViewById(R.id.relative_layout_loader_container)
				.bringToFront();

		// Create the loader
		mImageViewLoader = (ProgressBar) mLayout
				.findViewById(R.id.image_view_loader);
		mImageViewLoader.setVisibility(View.GONE);

		return mLayout;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mRenderer.onSurfaceDestroyed();
	}

	protected abstract ARajawaliRenderer createRenderer();

	public void hideLoader() {
		mImageViewLoader.post(new Runnable() {
			@Override
			public void run() {
				mImageViewLoader.setVisibility(View.GONE);
			}
		});
	}

	public void showLoader() {
		mImageViewLoader.post(new Runnable() {
			@Override
			public void run() {
				mImageViewLoader.setVisibility(View.VISIBLE);
			}
		});
	}

	protected abstract class ARajawaliRenderer extends RajawaliRenderer {

		public ARajawaliRenderer(Context context) {
			super(context);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			showLoader();
			super.onSurfaceCreated(gl, config);
			hideLoader();
		}

	}

}
