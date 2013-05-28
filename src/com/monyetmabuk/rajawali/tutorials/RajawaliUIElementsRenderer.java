package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

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
	private BaseObject3D mAndroid;

	public RajawaliUIElementsRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(0, 0, -1);
		mLight.setPower(.8f);
		getCurrentCamera().setPosition(0, 0, 13);

		GZIPInputStream gzi;
		try {
			gzi = new GZIPInputStream(mContext.getResources().openRawResource(R.raw.android));
			ObjectInputStream fis = new ObjectInputStream(gzi);
			SerializedObject3D serializedAndroid = (SerializedObject3D) fis.readObject();
			fis.close();

			mAndroid = new BaseObject3D(serializedAndroid);
			mAndroid.addLight(mLight);
			addChild(mAndroid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PhongMaterial material = new PhongMaterial();
		material.setUseSingleColor(true);
		mAndroid.setMaterial(material);
		mAndroid.setColor(0xff99C224);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mAndroid.setRotY(mAndroid.getRotY() + 1);
	}
}
