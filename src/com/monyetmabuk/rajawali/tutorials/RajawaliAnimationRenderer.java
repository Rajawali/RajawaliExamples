package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.Animation3DQueue;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.ScaleAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Number3D;
import rajawali.math.Number3D.Axis;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

public class RajawaliAnimationRenderer extends RajawaliRenderer {
	private PointLight mLight;
	private BaseObject3D mMonkey;
	private Animation3DQueue mQueue;

	public RajawaliAnimationRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		mLight = new PointLight();
		mLight.setPosition(-2, 1, 4);
		mLight.setPower(1.5f);
		mCamera.setPosition(0, 0, 14);

		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();
			mMonkey = new BaseObject3D(serializedMonkey);
			mMonkey.addLight(mLight);
			addChild(mMonkey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DiffuseMaterial material = new DiffuseMaterial();
		material.setUseColor(true);
		mMonkey.setMaterial(material);
		mMonkey.setColor(0xff00ff00);

		mQueue = new Animation3DQueue();

		Animation3D anim = new ScaleAnimation3D(new Number3D(1.6f, .8f, 1));
		anim.setInterpolator(new LinearInterpolator());
		anim.setDuration(1000);
		anim.setRepeatCount(3);
		anim.setRepeatMode(RepeatMode.REVERSE);
		anim.setTransformable3D(mMonkey);
		registerAnimation(anim);
		mQueue.addAnimation(anim);

		Number3D axis = new Number3D(10, 5, 2);
		axis.normalize();
		
		anim = new RotateAnimation3D(axis, 0, 360);
		anim.setDuration(2000);
		anim.setTransformable3D(mMonkey);
		registerAnimation(anim);
		mQueue.addAnimation(anim);

		anim = new TranslateAnimation3D(new Number3D(-2, -2, 0));
		anim.setDuration(500);
		anim.setTransformable3D(mMonkey);
		registerAnimation(anim);
		mQueue.addAnimation(anim);

		anim = new TranslateAnimation3D(new Number3D(-2, -2, 0), new Number3D(2, 2, 0));
		anim.setDuration(2000);
		anim.setTransformable3D(mMonkey);
		anim.setInterpolator(new BounceInterpolator());
		anim.setRepeatCount(3);
		registerAnimation(anim);
		mQueue.addAnimation(anim);

		anim = new EllipticalOrbitAnimation3D(new Number3D(), new Number3D(0, 3, 0), Number3D.getAxisVector(Axis.Z), 0, OrbitDirection.CLOCKWISE);
		anim.setInterpolator(new LinearInterpolator());
		anim.setDuration(2000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		anim.setTransformable3D(mMonkey);
		registerAnimation(anim);
		mQueue.addAnimation(anim);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mQueue.start();	
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
