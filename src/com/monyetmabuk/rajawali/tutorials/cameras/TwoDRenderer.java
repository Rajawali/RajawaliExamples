package com.monyetmabuk.rajawali.tutorials.cameras;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.Camera2D;
import rajawali.primitives.ScreenQuad;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.CustomMaterial;
import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class TwoDRenderer extends RajawaliRenderer {
	private float mTime;
	private CustomMaterial mCustomMaterial;

	public TwoDRenderer(Context context) {
		super(context);
		getCurrentScene().switchCamera(new Camera2D());
		setFrameRate(60);
		mTime = 0;
	}
	
	protected void initScene() {
		mCustomMaterial = new CustomMaterial();

		ScreenQuad screenQuad = new ScreenQuad();
		screenQuad.setMaterial(mCustomMaterial);
		addChild(screenQuad);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mTime += .007f;
		mCustomMaterial.setTime(mTime);
	}
}
