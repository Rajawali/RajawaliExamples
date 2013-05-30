package com.monyetmabuk.rajawali.tutorials.materials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.CubeMapMaterial;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.GouraudMaterial;
import rajawali.materials.PhongMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.CubeMapTexture;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class MaterialsRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mMonkey1, mMonkey2, mMonkey3, mMonkey4;

	public MaterialsRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		mLight = new DirectionalLight(.3f, -.3f, -1);
		mLight.setPower(.6f);
		getCurrentCamera().setPosition(0, 0, 9);

		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_ser));
			SerializedObject3D serializedMonkey = (SerializedObject3D) ois.readObject();
			ois.close();

			mMonkey1 = new BaseObject3D(serializedMonkey);
			mMonkey1.addLight(mLight);
			mMonkey1.setScale(.7f);
			mMonkey1.setPosition(-1, 1, 0);
			mMonkey1.setRotY(0);
			addChild(mMonkey1);

			mMonkey2 = mMonkey1.clone();
			mMonkey2.addLight(mLight);
			mMonkey2.setScale(.7f);
			mMonkey2.setPosition(1, 1, 0);
			mMonkey2.setRotY(45);
			addChild(mMonkey2);

			mMonkey3 = mMonkey1.clone();
			mMonkey3.addLight(mLight);
			mMonkey3.setScale(.7f);
			mMonkey3.setPosition(-1, -1, 0);
			mMonkey3.setRotY(90);
			addChild(mMonkey3);

			mMonkey4 = mMonkey1.clone();
			mMonkey4.addLight(mLight);
			mMonkey4.setScale(.7f);
			mMonkey4.setPosition(1, -1, 0);
			mMonkey4.setRotY(135);
			addChild(mMonkey4);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DiffuseMaterial diffuse = new DiffuseMaterial();
		diffuse.setUseSingleColor(true);
		mMonkey1.setMaterial(diffuse);
		mMonkey1.setColor(0xff00ff00);

		GouraudMaterial gouraud = new GouraudMaterial();
		gouraud.setUseSingleColor(true);
		gouraud.setSpecularIntensity(.1f, .1f, .1f, 1);
		mMonkey2.setMaterial(gouraud);
		mMonkey2.setColor(0xff999900);

		PhongMaterial phong = new PhongMaterial();
		phong.setShininess(60);
		phong.setUseSingleColor(true);
		mMonkey3.setMaterial(phong);
		mMonkey3.setColor(0xff00ff00);

		int[] resourceIds = new int[] { R.drawable.posx, R.drawable.negx, R.drawable.posy, R.drawable.negy, R.drawable.posz, R.drawable.negz };

		CubeMapMaterial cubeMapMaterial = new CubeMapMaterial();
		try {
			cubeMapMaterial.addTexture(new CubeMapTexture("monkeyCubeMap", resourceIds));
		} catch (TextureException e) {
			e.printStackTrace();
		}
		mMonkey4.setMaterial(cubeMapMaterial);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMonkey1.setRotY(mMonkey1.getRotY() - 1f);
		mMonkey2.setRotY(mMonkey2.getRotY() + 1f);
		mMonkey3.setRotY(mMonkey3.getRotY() - 1f);
		mMonkey4.setRotY(mMonkey4.getRotY() + 1f);
	}
}
