package com.monyetmabuk.rajawali.tutorials.examples;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.views.GithubLogoView;

import org.rajawali3d.RajawaliFragment;
import org.rajawali3d.renderer.RajawaliRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class AExampleFragment extends RajawaliFragment implements
		OnClickListener {

	public static final String BUNDLE_EXAMPLE_URL = "BUNDLE_EXAMPLE_URL";
	public static final String BUNDLE_EXAMPLE_TITLE = "BUNDLE_EXAMPLE_TITLE";

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

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
    public int getLayoutID() {
        return R.layout.rajawali_fragment;
    }

	protected void hideLoader() {
		mProgressBarLoader.post(new Runnable() {
			@Override
			public void run() {
				mProgressBarLoader.setVisibility(View.GONE);
			}
		});
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
			setFrameRate(0.5);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			showLoader();
			super.onSurfaceCreated(gl, config);
			hideLoader();
		}
	}
}
