package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.lights.DirectionalLight;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliSkyboxRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;

	public RajawaliSkyboxRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight();
		mLight.setPosition(5f, 10, -10);
		mCamera.setFarPlane(1000);
		/*
		 * Skybox images by Emil Persson, aka Humus. http://www.humus.name
		 * humus@comhem.se
		 */
		setSkybox(R.drawable.posz, R.drawable.posx, R.drawable.negz, R.drawable.negx, R.drawable.posy, R.drawable.negy);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mSkybox.setRotY(mSkybox.getRotY() + .5f);
	}
}
