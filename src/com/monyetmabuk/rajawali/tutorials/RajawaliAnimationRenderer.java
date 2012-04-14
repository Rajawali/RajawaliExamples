package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3DQueue;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.RotateAroundAnimation3D;
import rajawali.animation.RotateAroundAnimation3D.Axis;
import rajawali.animation.ScaleAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

public class RajawaliAnimationRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mMonkey;

	public RajawaliAnimationRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).showLoader();

		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(-2, -2, -5);
		mCamera.setPosition(0, 0, -14);

		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();
			mMonkey = new BaseObject3D(serializedMonkey);
			mMonkey.setLight(mLight);
			addChild(mMonkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		startRendering();

		mMonkey.setMaterial(new DiffuseMaterial());
		mMonkey.getMaterial().setUseColor(true);
		mMonkey.setColor(0xff00ff00);

		Animation3DQueue queue = new Animation3DQueue();

		Animation3D anim = new ScaleAnimation3D(new Number3D(1.6f, .8f, 1));
		// Animation3D anim = new RotateAroundAnimation3D(new Number3D(),
		// Axis.Z, 2, -1);
		anim.setInterpolator(new LinearInterpolator());
		anim.setDuration(1000);
		anim.setRepeatCount(3);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setTransformable3D(mMonkey);
		queue.addAnimation(anim);

		anim = new RotateAnimation3D(new Number3D(90, 180, 270));
		anim.setDuration(2000);
		anim.setTransformable3D(mMonkey);
		queue.addAnimation(anim);

		anim = new TranslateAnimation3D(new Number3D(-2, -2, 0));
		anim.setDuration(500);
		anim.setTransformable3D(mMonkey);
		queue.addAnimation(anim);

		anim = new TranslateAnimation3D(new Number3D(-2, -2, 0), new Number3D(2, 2, 0));
		anim.setDuration(2000);
		anim.setTransformable3D(mMonkey);
		anim.setInterpolator(new BounceInterpolator());
		anim.setRepeatCount(3);
		queue.addAnimation(anim);

		anim = new RotateAroundAnimation3D(new Number3D(), Axis.Z, 3, 1);
		anim.setInterpolator(new LinearInterpolator());
		anim.setDuration(2000);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setRepeatCount(Animation3D.INFINITE);
		anim.setTransformable3D(mMonkey);
		queue.addAnimation(anim);

		queue.start();

		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
