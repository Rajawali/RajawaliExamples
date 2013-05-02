package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.Camera2D;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class Rajawali2DRenderer extends RajawaliRenderer {
	private float mTime;
	private CustomMaterial mCustomMaterial;

	public Rajawali2DRenderer(Context context) {
		super(context);
		getCurrentScene().switchCamera(new Camera2D());
		setFrameRate(60);
		mTime = 0;
	}
	
	protected void initScene() {
		mCustomMaterial = new CustomMaterial();

		Plane plane = new Plane(1, 1, 1, 1);
		plane.setMaterial(mCustomMaterial);
		//plane.setDoubleSided(true);
		addChild(plane);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mTime += .2f;
		mCustomMaterial.setTime(mTime);
	}
}
