package com.monyetmabuk.rajawali.tutorials.materials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.ToonMaterial;
import rajawali.math.Vector3.Axis;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class ToonShadingRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mMonkey1, mMonkey2, mMonkey3;

	public ToonShadingRenderer(Context context) {
		super(context);
		getCurrentScene().setBackgroundColor(0xffeeeeee);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(0, 0, -1);
		mLight.setPower(1);
		getCurrentCamera().setPosition(0, 0, 12);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();
			
			ToonMaterial toonMat = new ToonMaterial();
			
			mMonkey1 = new BaseObject3D(serializedMonkey);
			mMonkey1.setMaterial(toonMat);
			mMonkey1.setPosition(-1.5f, 2, 0);
			mMonkey1.addLight(mLight);
			addChild(mMonkey1);
			
			toonMat = new ToonMaterial();
			toonMat.setToonColors(0xffffffff, 0xff000000, 0xff666666, 0xff000000);
			mMonkey2 = mMonkey1.clone();
			mMonkey2.setMaterial(toonMat);
			mMonkey2.setPosition(1.5f, 2, 0);
			mMonkey2.addLight(mLight);
			addChild(mMonkey2);
			
			toonMat = new ToonMaterial();
			toonMat.setToonColors(0xff999900, 0xff003300, 0xffff0000, 0xffa60000);
			mMonkey3 = mMonkey1.clone();
			mMonkey3.setMaterial(toonMat);
			mMonkey3.setPosition(0, -2, 0);
			mMonkey3.addLight(mLight);
			addChild(mMonkey3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RotateAnimation3D anim = new RotateAnimation3D(Axis.Y, 360);
		anim.setDuration(6000);
		anim.setRepeatMode(RepeatMode.INFINITE);
		anim.setTransformable3D(mMonkey1);
		registerAnimation(anim);
		anim.play();
		
		anim = new RotateAnimation3D(Axis.Y, -360);
		anim.setDuration(6000);
		anim.setRepeatMode(RepeatMode.INFINITE);
		anim.setTransformable3D(mMonkey2);
		registerAnimation(anim);
		anim.play();
		
		anim = new RotateAnimation3D(Axis.Y, -360);
		anim.setDuration(6000);
		anim.setRepeatMode(RepeatMode.INFINITE);
		anim.setTransformable3D(mMonkey3);
		registerAnimation(anim);
		anim.play();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
}
