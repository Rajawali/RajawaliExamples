package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.PhongMaterial;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliUIElementsRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mMonkey;
	private boolean mSceneInitialized;
	
	public RajawaliUIElementsRenderer(Context context) {
		super(context);
		setFrameRate(60);
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
			
			try {
				ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
				SerializedObject3D serializedMonkey = (SerializedObject3D)ois.readObject();
				ois.close();
				
				mMonkey = new BaseObject3D(serializedMonkey);
				mMonkey.setLight(mLight);
				addChild(mMonkey);
			} catch(Exception e) {
				e.printStackTrace();
			}
			startRendering();
			mSceneInitialized = true;
		}
		
		mMonkey.setMaterial(new PhongMaterial());
		mMonkey.getMaterial().setUseColor(true);
		mMonkey.setColor(0xffcfb52b);
		
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMonkey.setRotY(mMonkey.getRotY() + 1);
	}
}
