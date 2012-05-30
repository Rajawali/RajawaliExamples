package com.monyetmabuk.rajawali.tutorials;

import rajawali.math.Number3D;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class RajawaliChaseCamActivity extends RajawaliExampleActivity implements OnSeekBarChangeListener {
	private RajawaliChaseCamRenderer mRenderer;
	private SeekBar mSeekBarX, mSeekBarY, mSeekBarZ;
	private Number3D mCameraOffset;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mMultisamplingEnabled = false;
		mCameraOffset = new Number3D();
		super.onCreate(savedInstanceState);

		mRenderer = new RajawaliChaseCamRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		mRenderer.setUsesCoverageAa(mUsesCoverageAa);
		super.setRenderer(mRenderer);
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.BOTTOM);

        mSeekBarZ = new SeekBar(this);
        mSeekBarZ.setMax(40);
        mSeekBarZ.setProgress(16);
        mSeekBarZ.setOnSeekBarChangeListener(this);
        ll.addView(mSeekBarZ);
        
        mSeekBarY = new SeekBar(this);
        mSeekBarY.setMax(20);
        mSeekBarY.setProgress(13);
        mSeekBarY.setOnSeekBarChangeListener(this);
        ll.addView(mSeekBarY);
        
        mSeekBarX = new SeekBar(this);
        mSeekBarX.setMax(20);
        mSeekBarX.setProgress(10);
        mSeekBarX.setOnSeekBarChangeListener(this);
        ll.addView(mSeekBarX);
        
        mLayout.addView(ll);
        
        initLoader();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		mCameraOffset.setAll(mSeekBarX.getProgress()-10, mSeekBarY.getProgress()-10, mSeekBarZ.getProgress());
		mRenderer.setCameraOffset(mCameraOffset);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
}
