package com.monyetmabuk.rajawali.tutorials.cameras;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.primitives.ScreenQuad;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;
import com.monyetmabuk.rajawali.tutorials.materials.CustomMaterial;

public class TwoDRenderer extends RajawaliRenderer {
	private float mTime;
	private CustomMaterial mCustomMaterial;

	public TwoDRenderer(Context context) {
		super(context);
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
