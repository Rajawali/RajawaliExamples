package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliCustomShaderRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mSphere;
	private CustomMaterial mCustomMaterial;
	private float mTime;

	public RajawaliCustomShaderRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(0, 0, -80);

		mCustomMaterial = new CustomMaterial();

		mSphere = new Sphere(2, 32, 32);
		mSphere.setLight(mLight);
		mSphere.setMaterial(mCustomMaterial);
		addChild(mSphere);

		mCamera.setPosition(0, 0, -10);

		mTime = 0;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mTime += .1f;
		mCustomMaterial.setTime(mTime);
		mSphere.setRotX(mSphere.getRotX() + 1);
		mSphere.setRotY(mSphere.getRotY() + 1);
	}
}
