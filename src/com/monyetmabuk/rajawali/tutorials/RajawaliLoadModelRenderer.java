package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliLoadModelRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mMonkey;
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
			mLight.setPosition(-2, -2, -5);
			mCamera.setPosition(0, 0, -7);

			ObjParser objParser = new ObjParser(mContext.getResources(),
					mTextureManager, R.raw.monkey);
			objParser.parse();
			mMonkey = objParser.getParsedObject().getChildByName("Monkey");
			mMonkey.setLight(mLight);
			addChild(mMonkey);
			//mMonkey.serializeToSDCard("monkey_ser.ser");

			startRendering();

			mSceneInitialized = true;
		}

		mMonkey.setMaterial(new DiffuseMaterial());
		mMonkey.getMaterial().setUseColor(true);
		mMonkey.setColor(0xff00ff00);

		((RajawaliExampleActivity) mContext).hideLoader();
	}

	@Override
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMonkey.setRotY(mMonkey.getRotY() + 1);
	}
}
