package com.monyetmabuk.rajawali.tutorials.examples;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.views.GithubLogoView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.RajawaliFragment;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;

public abstract class AExampleFragment extends RajawaliFragment implements
		OnClickListener {

	public static final String BUNDLE_EXAMPLE_URL = "BUNDLE_EXAMPLE_URL";
	public static final String BUNDLE_EXAMPLE_TITLE = "BUNDLE_EXAMPLE_TITLE";

	protected RajawaliRenderer mRenderer;
	protected ProgressBar mProgressBarLoader;
	protected GithubLogoView mImageViewExampleLink;
	protected String mExampleUrl;
	protected String mExampleTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Bundle bundle = getArguments();
		if (bundle == null || !bundle.containsKey(BUNDLE_EXAMPLE_URL)) {
			throw new IllegalArgumentException(getClass().getSimpleName()
					+ " requires " + BUNDLE_EXAMPLE_URL
					+ " argument at runtime!");
		}

		mExampleUrl = bundle.getString(BUNDLE_EXAMPLE_URL);
		mExampleTitle = bundle.getString(BUNDLE_EXAMPLE_TITLE);

		if (isTransparentSurfaceView())
			setGLBackgroundTransparent(true);

		mRenderer = createRenderer();
		if (mRenderer == null)
			mRenderer = new NullRenderer(getActivity());

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
		mProgressBarLoader = (ProgressBar) mLayout
				.findViewById(R.id.progress_bar_loader);
		mProgressBarLoader.setVisibility(View.GONE);

		// Set the example link
		mImageViewExampleLink = (GithubLogoView) mLayout
				.findViewById(R.id.image_view_example_link);
		mImageViewExampleLink.setOnClickListener(this);

		getActivity().setTitle(mExampleTitle);
		return mLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_view_example_link:
			if (mImageViewExampleLink == null)
				throw new IllegalStateException("Example link is null!");

			final Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(mExampleUrl));
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		if (mLayout != null)
			mLayout.removeView(mSurfaceView);
	}

	@Override
	public void onDestroy() {
		try {
			super.onDestroy();
		} catch (Exception e) {
		}
		mRenderer.onSurfaceDestroyed();
	}

	/**
	 * Create a renderer to be used by the fragment. Optionally null can be returned by fragments
	 * that do not intend to display a rendered scene. Returning null will cause a warning to be
	 * logged to the console in the event null is in error.
	 * 
	 * @return
	 */
	protected abstract AExampleRenderer createRenderer();

	protected void hideLoader() {
		mProgressBarLoader.post(new Runnable() {
			@Override
			public void run() {
				mProgressBarLoader.setVisibility(View.GONE);
			}
		});
	}

	protected boolean isTransparentSurfaceView() {
		return false;
	}

	protected void showLoader() {
		mProgressBarLoader.post(new Runnable() {
			@Override
			public void run() {
				mProgressBarLoader.setVisibility(View.VISIBLE);
			}
		});
	}

	protected abstract class AExampleRenderer extends RajawaliRenderer {

		public AExampleRenderer(Context context) {
			super(context);
			setFrameRate(60);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			showLoader();
			super.onSurfaceCreated(gl, config);
			hideLoader();
		}

	}

	private static final class NullRenderer extends RajawaliRenderer {

		public NullRenderer(Context context) {
			super(context);
			RajLog.w(this + ": Fragment created without renderer!");
		}

		@Override
		public void onSurfaceDestroyed() {
			stopRendering();
		}
	}

}
