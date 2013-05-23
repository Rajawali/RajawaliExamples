package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Vector3.Axis;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RajawaliTransparentSurfaceRenderer extends RajawaliRenderer {
	public RajawaliTransparentSurfaceRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		DirectionalLight light = new DirectionalLight(0, 0, -1);
		light.setPower(1);
		getCurrentCamera().setPosition(0, 0, 16);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();

			BaseObject3D monkey = new BaseObject3D(serializedMonkey);
			DiffuseMaterial material = new DiffuseMaterial();
			material.setUseColor(true);
			monkey.setMaterial(material);
			monkey.addLight(light);
			monkey.setColor(0xffff8C00);
			monkey.setScale(2);
			addChild(monkey);

			RotateAnimation3D anim = new RotateAnimation3D(Axis.Y, 360);
			anim.setDuration(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setInterpolator(new AccelerateDecelerateInterpolator());
			anim.setTransformable3D(monkey);
			registerAnimation(anim);
			anim.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// -- set the background color to be transparent
		//    you need to have called setGLBackgroundTransparent(true); in the activity
		//    for this to work.
		getCurrentScene().setBackgroundColor(0);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
}
