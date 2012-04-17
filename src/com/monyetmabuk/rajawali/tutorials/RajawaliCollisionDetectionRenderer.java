package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;
import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.bounds.IBoundingVolume;
import rajawali.lights.DirectionalLight;
import rajawali.materials.PhongMaterial;
import rajawali.math.Number3D;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliCollisionDetectionRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mCubeBox, mBoxesBox;
	private BaseObject3D mCubeSphere, mBoxesSphere;
	private boolean mBoxIntersect = false;
	private boolean mSphereIntersect = false;
	private Stack<Animation3D> mAnimations;

	public RajawaliCollisionDetectionRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(-2, -2, -5);
		mCamera.setPosition(0, 0, -7);

		mCubeBox = new Cube(1);
		mCubeBox.addLight(mLight);
		mCubeBox.getMaterial().setUseColor(true);
		mCubeBox.setColor(0xff990000);
		mCubeBox.setPosition(-1, -3, 0);
		mCubeBox.setShowBoundingVolume(true);
		addChild(mCubeBox);

		mCubeSphere = new Cube(1);
		mCubeSphere.addLight(mLight);
		mCubeSphere.getMaterial().setUseColor(true);
		mCubeSphere.setColor(0xff00bfff);
		mCubeSphere.setPosition(1, -2, 0);
		mCubeSphere.setShowBoundingVolume(true);
		addChild(mCubeSphere);

		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.boxes));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();

			mBoxesBox = new BaseObject3D(serializedMonkey);
			mBoxesBox.setMaterial(new PhongMaterial());
			mBoxesBox.getMaterial().setUseColor(true);
			mBoxesBox.setColor(0xff990000);
			mBoxesBox.addLight(mLight);
			mBoxesBox.setScale(.2f);
			mBoxesBox.setRotY(180);
			mBoxesBox.setPosition(-1, 3, 0);
			mBoxesBox.setShowBoundingVolume(true);
			addChild(mBoxesBox);

			mBoxesSphere = new BaseObject3D(serializedMonkey);
			mBoxesSphere.setMaterial(new PhongMaterial());
			mBoxesSphere.getMaterial().setUseColor(true);
			mBoxesSphere.setColor(0xff00bfff);
			mBoxesSphere.addLight(mLight);
			mBoxesSphere.setScale(.3f);
			mBoxesSphere.setRotX(180);
			mBoxesSphere.setPosition(1, 2, 0);
			mBoxesSphere.setShowBoundingVolume(true);
			addChild(mBoxesSphere);

		} catch (Exception e) {
			e.printStackTrace();
		}

		mAnimations = new Stack<Animation3D>();
		
		Animation3D anim = new TranslateAnimation3D(new Number3D(-1, 3, 0));
		anim.setDuration(4000);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setRepeatCount(Animation3D.INFINITE);
		anim.setTransformable3D(mCubeBox);
		mAnimations.push(anim);

		anim = new RotateAnimation3D(new Number3D(90, 30, 120));
		anim.setDuration(4000);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setRepeatCount(Animation3D.INFINITE);
		anim.setTransformable3D(mCubeBox);
		mAnimations.push(anim);

		anim = new TranslateAnimation3D(new Number3D(-1, -3, 0));
		anim.setDuration(4000);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setRepeatCount(Animation3D.INFINITE);
		anim.setTransformable3D(mBoxesBox);
		mAnimations.push(anim);

		anim = new TranslateAnimation3D(new Number3D(1, 2, 0));
		anim.setDuration(2000);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setRepeatCount(Animation3D.INFINITE);
		anim.setTransformable3D(mCubeSphere);
		mAnimations.push(anim);

		anim = new TranslateAnimation3D(new Number3D(1, -2, 0));
		anim.setDuration(2000);
		anim.setRepeatMode(Animation3D.REVERSE);
		anim.setRepeatCount(Animation3D.INFINITE);
		anim.setTransformable3D(mBoxesSphere);
		mAnimations.push(anim);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		for(int i=0; i<mAnimations.size(); i++)
			mAnimations.get(i).start();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		IBoundingVolume bbox = mBoxesBox.getGeometry().getBoundingBox();
		bbox.transform(mBoxesBox.getModelMatrix());

		IBoundingVolume bbox2 = mCubeBox.getGeometry().getBoundingBox();
		bbox2.transform(mCubeBox.getModelMatrix());

		mBoxIntersect = bbox.intersectsWith(bbox2);

		IBoundingVolume bsphere = mBoxesSphere.getGeometry().getBoundingSphere();
		bsphere.transform(mBoxesSphere.getModelMatrix());

		IBoundingVolume bsphere2 = mCubeSphere.getGeometry().getBoundingSphere();
		bsphere2.transform(mCubeSphere.getModelMatrix());

		mSphereIntersect = bsphere.intersectsWith(bsphere2);

		if (mSphereIntersect && !mBoxIntersect)
			setBackgroundColor(0xff00bfff);
		else if (!mSphereIntersect && mBoxIntersect)
			setBackgroundColor(0xff990000);
		else if (mSphereIntersect && mBoxIntersect)
			setBackgroundColor(0xff999999);
		else
			setBackgroundColor(0xff000000);
	}
}
