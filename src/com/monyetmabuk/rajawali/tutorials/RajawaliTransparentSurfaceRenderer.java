package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RajawaliTransparentSurfaceRenderer extends RajawaliRenderer {
	private Animation3D mAnim;
	
	public RajawaliTransparentSurfaceRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		ALight mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(0, 0, -3);
		mLight.setPower(1);
		mCamera.setPosition(0, 0, -16);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();

			BaseObject3D monkey = new BaseObject3D(serializedMonkey);
			monkey.setMaterial(new DiffuseMaterial());
			monkey.addLight(mLight);
			monkey.getMaterial().setUseColor(true);
			monkey.setColor(0xffff8C00);
			monkey.setScale(2);
			addChild(monkey);

			mAnim = new RotateAnimation3D(new Number3D(0, 360, 0));
			mAnim.setDuration(6000);
			mAnim.setRepeatCount(Animation3D.INFINITE);
			mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			mAnim.setTransformable3D(monkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// -- set the background color to be transparent
		//    you need to have called setGLBackgroundTransparent(true); in the activity
		//    for this to work.
		setBackgroundColor(0);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mAnim.start();
	}
}
