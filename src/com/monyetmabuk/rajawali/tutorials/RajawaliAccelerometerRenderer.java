package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.CubeMapMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.CubeMapTexture;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliAccelerometerRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mMonkey;
	private Number3D mAccValues;

	public RajawaliAccelerometerRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mAccValues = new Number3D();
	}
	
	protected void initScene() {
		mLight = new DirectionalLight(0.1f, 0.2f, -1.0f);
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(1);

		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();

			mMonkey = new BaseObject3D(serializedMonkey);
			mMonkey.addLight(mLight);
			mMonkey.setRotY(180);
			addChild(mMonkey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		getCurrentCamera().setZ(7);

		int[] resourceIds = new int[] { R.drawable.posx, R.drawable.negx, R.drawable.posy, R.drawable.negy, R.drawable.posz, R.drawable.negz};
		
		CubeMapMaterial material = new CubeMapMaterial();
		try {
			material.addTexture(new CubeMapTexture("environmentMap", resourceIds));
			mMonkey.setMaterial(material);
		} catch (TextureException e) {
			e.printStackTrace();
		}
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMonkey.setRotation(mAccValues.x, mAccValues.y + 180, mAccValues.z);
	}

	public void setAccelerometerValues(float x, float y, float z) {
		mAccValues.setAll(-x, -y, -z);
	}
}
