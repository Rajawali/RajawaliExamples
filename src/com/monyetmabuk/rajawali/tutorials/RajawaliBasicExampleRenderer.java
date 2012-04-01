package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;

public class RajawaliBasicExampleRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mSphere;
	
	public RajawaliBasicExampleRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity)mContext).showLoader();
		
		mLight = new DirectionalLight(0.1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPosition(.5f, 0, -2);
		mLight.setPower(2);
		
		Bitmap bg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earthtruecolor_nasa_big);
		mSphere = new Sphere(1, 12, 12);
		mSphere.setLight(mLight);
		mSphere.addTexture(mTextureManager.addTexture(bg));
		addChild(mSphere);
		
		mCamera.setZ(-4.2f);
		
		startRendering();
        ((RajawaliExampleActivity)mContext).hideLoader();
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mSphere.setRotY(mSphere.getRotY() + 1);
	}
}
