package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;
import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.ToonMaterial;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliToonShadingRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mMonkey1, mMonkey2, mMonkey3;
	private Stack<Animation3D> mAnimations;

	public RajawaliToonShadingRenderer(Context context) {
		super(context);
		setBackgroundColor(0xffeeeeee);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(0, 0, -6);
		mCamera.setPosition(0, 0, -12);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();
			
			ToonMaterial toonMat = new ToonMaterial();
			
			mMonkey1 = new BaseObject3D(serializedMonkey);
			mMonkey1.setMaterial(toonMat);
			mMonkey1.setPosition(-1.5f, 2, 0);
			mMonkey1.setLight(mLight);
			addChild(mMonkey1);
			
			toonMat = new ToonMaterial();
			toonMat.setToonColors(0xffffffff, 0xff000000, 0xff666666, 0xff000000);
			mMonkey2 = mMonkey1.clone();
			mMonkey2.setMaterial(toonMat);
			mMonkey2.setPosition(1.5f, 2, 0);
			mMonkey2.setLight(mLight);
			addChild(mMonkey2);
			
			toonMat = new ToonMaterial();
			toonMat.setToonColors(0xff999900, 0xff003300, 0xffff0000, 0xffa60000);
			mMonkey3 = mMonkey1.clone();
			mMonkey3.setMaterial(toonMat);
			mMonkey3.setPosition(0, -2, 0);
			mMonkey3.setLight(mLight);
			addChild(mMonkey3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mAnimations = new Stack<Animation3D>();
		
		RotateAnimation3D anim = new RotateAnimation3D(new Number3D(0, 360, 0));
		anim.setDuration(6000);
		anim.setRepeatCount(RotateAnimation3D.INFINITE);
		anim.setTransformable3D(mMonkey1);
		mAnimations.add(anim);
		
		anim = new RotateAnimation3D(new Number3D(0, -360, 0));
		anim.setDuration(6000);
		anim.setRepeatCount(RotateAnimation3D.INFINITE);
		anim.setTransformable3D(mMonkey2);
		mAnimations.add(anim);
		
		anim = new RotateAnimation3D(new Number3D(0, -360, 0));
		anim.setDuration(6000);
		anim.setRepeatCount(RotateAnimation3D.INFINITE);
		anim.setTransformable3D(mMonkey3);
		mAnimations.add(anim);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		for(int i=0; i<mAnimations.size(); i++)
			mAnimations.get(i).start();
	}
}
