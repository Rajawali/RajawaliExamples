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
	private boolean mSceneInitialized;

	public RajawaliLoadModelRenderer(Context context) {
		super(context);
		setFrameRate(30);
		mClearChildren = false;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).showLoader();

		if (!mSceneInitialized) {
			mLight = new DirectionalLight(0, 0, 1);
			mLight.setPosition(0, 0, -10);

			mCamera.setLookAt(0, 0, 0);
			
			ObjParser objParser = new ObjParser(mContext.getResources(),
					mTextureManager, R.raw.multiobjects_obj);
			objParser.parse();
			mObjectGroup = objParser.getParsedObject();
			mObjectGroup.setLight(mLight);
			addChild(mObjectGroup);
		
			mSceneInitialized = true;
			
			startRendering();
			
			Animation3D cameraAnim = new RotateAroundAnimation3D(new Number3D(), Axis.Y, 18);
			cameraAnim.setDuration(8000);
			cameraAnim.setRepeatCount(Animation3D.INFINITE);
			cameraAnim.setTransformable3D(mCamera);
			cameraAnim.start();
			
			Animation3D lightAnim = new RotateAroundAnimation3D(new Number3D(), Axis.X, 10);
			lightAnim.setDuration(3000);
			lightAnim.setRepeatCount(Animation3D.INFINITE);
			lightAnim.setTransformable3D(mLight);
			lightAnim.start();
		}

		((RajawaliExampleActivity) mContext).hideLoader();
	}

	@Override
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
