package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliCustomShaderRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mCube;
	private CustomMaterial mCustomMaterial;
	private float mTime;

	public RajawaliCustomShaderRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(0, 1, 1);

		mCustomMaterial = new CustomMaterial();

		mCube = new Cube(2);
		mCube.addLight(mLight);
		mCube.setMaterial(mCustomMaterial);
		addChild(mCube);

		getCurrentCamera().setPosition(0, 0, 10);

		mTime = 0;
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
		mCube.setRotX(mCube.getRotX() + .5f);
		mCube.setRotY(mCube.getRotY() + .5f);
	}
}
