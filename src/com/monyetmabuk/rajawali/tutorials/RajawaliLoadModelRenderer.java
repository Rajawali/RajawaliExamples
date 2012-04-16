package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAroundAnimation3D;
import rajawali.animation.RotateAroundAnimation3D.Axis;
import rajawali.lights.DirectionalLight;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliLoadModelRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mObjectGroup;
	private Animation3D mCameraAnim, mLightAnim;
	
	public RajawaliLoadModelRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(0, 0, -10);

		mCamera.setLookAt(0, 0, 0);

		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.multiobjects_obj);
		objParser.parse();
		mObjectGroup = objParser.getParsedObject();
		mObjectGroup.setLight(mLight);
		addChild(mObjectGroup);

		mCameraAnim = new RotateAroundAnimation3D(new Number3D(), Axis.Y, 18);
		mCameraAnim.setDuration(8000);
		mCameraAnim.setRepeatCount(Animation3D.INFINITE);
		mCameraAnim.setTransformable3D(mCamera);

		mLightAnim = new RotateAroundAnimation3D(new Number3D(), Axis.X, 10);
		mLightAnim.setDuration(3000);
		mLightAnim.setRepeatCount(Animation3D.INFINITE);
		mLightAnim.setTransformable3D(mLight);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mCameraAnim.start();
		mLightAnim.start();		
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
