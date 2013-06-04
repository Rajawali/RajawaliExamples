package com.monyetmabuk.rajawali.tutorials.materials;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class CameraVideoTextureActivity extends RajawaliExampleActivity {
	private CameraVideoTextureRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {/*
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
*/
		super.onCreate(savedInstanceState);
		mRenderer = new CameraVideoTextureRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
