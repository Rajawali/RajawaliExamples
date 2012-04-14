package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.filters.SwirlFilter;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RajawaliPostProcessingRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private SwirlFilter mFilter;
	private float mTime;
	private float mDirection = 1;

	public RajawaliPostProcessingRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).showLoader();

		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(-2, -2, -5);
		mCamera.setPosition(0, 0, -7);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(mContext
					.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois
					.readObject();
			ois.close();

			BaseObject3D uberMonkey = new BaseObject3D(serializedMonkey);
			uberMonkey.setMaterial(new DiffuseMaterial());
			uberMonkey.setLight(mLight);
			uberMonkey.setScale(.7f);
			uberMonkey.setPosition(0, 0, 0);
			uberMonkey.setColor(0xff990000);
			uberMonkey.getMaterial().setUseColor(true);
			uberMonkey.setRotY(180);
			addChild(uberMonkey);

			for (int i = 0; i < 7; i++) {
				BaseObject3D monkey = uberMonkey.clone();
				monkey.setMaterial(new DiffuseMaterial());
				monkey.setLight(mLight);
				monkey.setScale(.7f);
				monkey.setPosition(-4 + (int) (Math.random() * 8), -4
						+ (int) (Math.random() * 8), (int) (Math.random() * 20));
				monkey.getMaterial().setUseColor(true);
				monkey.setRotation((int) (Math.random() * 360),
						(int) (Math.random() * 360),
						(int) (Math.random() * 360));
				// -- since it is a cloned object it is important to set the
				//    second parameter to true or else all monkey will change
				//    color.
				monkey.setColor(0xff000000 + (int)(Math.random() * 0xffffff), true);
				addChild(monkey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		startRendering();
		
		Animation3D anim = new TranslateAnimation3D(new Number3D(0, 0, -2));
		anim.setDuration(6000);
		anim.setRepeatCount(Animation3D.INFINITE);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setTransformable3D(mCamera);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.start();
		
		mFilter = new SwirlFilter(mViewportWidth, mViewportHeight, 10, 1f);
		addPostProcessingFilter(mFilter);
		
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		super.onSurfaceChanged(gl, width, height);
		mFilter.setScreenWidth(mViewportWidth);
		mFilter.setScreenHeight(mViewportHeight);
		mFilter.setCenter(mViewportWidth * .5f, mViewportHeight * .5f);
		mFilter.setRadius(mViewportWidth * .5f);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mTime += .05f * mDirection; 
		mFilter.setTime(mTime);
		
		if(mTime >= 1) {
			mTime = 1;
			mDirection = -1;
		} else if(mTime <= 0) {
			mTime = 0;
			mDirection = 1;
		}
		
	}
}
