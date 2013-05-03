package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;

public class RajawaliBasicExampleRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mSphere;

	public RajawaliBasicExampleRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);

		Bitmap bg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earthtruecolor_nasa_big);
		DiffuseMaterial material = new DiffuseMaterial();
		mSphere = new Sphere(1, 18, 18);
		mSphere.setMaterial(material);
		mSphere.addLight(mLight);
		mSphere.addTexture(mTextureManager.addTexture(bg));
		addChild(mSphere); //Queue an addition task for mSphere

		getCurrentCamera().setZ(4.2f);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mSphere.setRotY(mSphere.getRotY() + 1);
	}
}
