package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.CubeMapMaterial;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
		mLight.setPosition(.5f, 0, -2);
		mLight.setPower(2);

		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();

			mMonkey = new BaseObject3D(serializedMonkey);
			mMonkey.addLight(mLight);
			mMonkey.setRotY(0);
			addChild(mMonkey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mCamera.setZ(-7);

		Bitmap[] textures = new Bitmap[6];
		textures[0] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.posx);
		textures[1] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.negx);
		textures[2] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.posy);
		textures[3] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.negy);
		textures[4] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.posz);
		textures[5] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.negz);

		mMonkey.setMaterial(new CubeMapMaterial());
		mMonkey.addTexture(mTextureManager.addCubemapTextures(textures));
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMonkey.setRotation(mAccValues.y, mAccValues.x, mAccValues.z);
	}

	public void setAccelerometerValues(float x, float y, float z) {
		mAccValues.setAll(-x, -y, -z);
	}
}
