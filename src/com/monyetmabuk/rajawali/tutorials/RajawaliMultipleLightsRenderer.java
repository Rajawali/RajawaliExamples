package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;
import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.TextureManager.TextureType;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliMultipleLightsRenderer extends RajawaliRenderer {
	private Stack<Animation3D> mAnimations;
	
	public RajawaliMultipleLightsRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		PointLight light1 = new PointLight();
		light1.setPower(5);
		PointLight light2 = new PointLight();
		light2.setPower(5);

		mCamera.setPosition(-8, 8, 8);
		mCamera.setLookAt(0, 0, 0);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.jet));
			SerializedObject3D serializedJet = (SerializedObject3D) ois.readObject();
			ois.close();
			
			BaseObject3D jet = new BaseObject3D(serializedJet);
			jet.setMaterial(new DiffuseMaterial());
			jet.setPosition(1, 0, 0);
			Bitmap tex = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.jettexture);
			jet.addTexture(mTextureManager.addTexture(tex, TextureType.DIFFUSE));
			jet.addLight(light1);
			jet.addLight(light2);
			jet.setRotY(180);
			addChild(jet);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Animation3D anim = new TranslateAnimation3D(new Number3D(-10, -10, 5), new Number3D(-10, 10, 5));
		anim.setDuration(4000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		anim.setTransformable3D(light1);
		
		mAnimations = new Stack<Animation3D>();
		mAnimations.add(anim);
		
		anim = new TranslateAnimation3D(new Number3D(10, 10, 5), new Number3D(10, -10, 5));
		anim.setDuration(2000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		anim.setTransformable3D(light2);
		
		mAnimations.add(anim);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		for(int i=0; i<2; i++) {
			Animation3D anim = mAnimations.get(i);
			registerAnimation(anim);
			anim.play();
		}
	}
}
