package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.math.Number3D;
import rajawali.math.Number3D.Axis;
import rajawali.parser.ObjParser;
import rajawali.parser.AParser.ParsingException;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliLoadModelRenderer extends RajawaliRenderer {
	private PointLight mLight;
	private BaseObject3D mObjectGroup;
	private Animation3D mCameraAnim, mLightAnim;
	
	public RajawaliLoadModelRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new PointLight();
		mLight.setPosition(0, 0, 4);
		mLight.setPower(3);
		getCurrentCamera().setZ(12);

		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.multiobjects_obj);
		try {
			objParser.parse();
			mObjectGroup = objParser.getParsedObject();
			mObjectGroup.addLight(mLight);
			addChild(mObjectGroup);
	
			mCameraAnim = new RotateAnimation3D(Axis.Y, 360);
			mCameraAnim.setDuration(8000);
			mCameraAnim.setRepeatMode(RepeatMode.INFINITE);
			mCameraAnim.setTransformable3D(mObjectGroup);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
			
		mLightAnim = new EllipticalOrbitAnimation3D(new Number3D(), new Number3D(0, 10, 0), Number3D.getAxisVector(Axis.Z), 0, 360, OrbitDirection.CLOCKWISE);
		
		mLightAnim.setDuration(3000);
		mLightAnim.setRepeatMode(RepeatMode.INFINITE);
		mLightAnim.setTransformable3D(mLight);
		
		registerAnimation(mCameraAnim);
		registerAnimation(mLightAnim);

		mCameraAnim.play();
		mLightAnim.play();		
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
