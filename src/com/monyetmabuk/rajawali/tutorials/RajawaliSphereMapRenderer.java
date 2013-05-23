package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.SphereMapMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.SphereMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliSphereMapRenderer extends RajawaliRenderer {
	public RajawaliSphereMapRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		PointLight light = new PointLight();
		light.setZ(6);
		light.setPower(2);

		Texture jetTexture = new Texture(R.drawable.jettexture);
		SphereMapTexture sphereMapTexture = new SphereMapTexture(R.drawable.manila_sphere_map);

		BaseObject3D jet1 = null;
		// -- sphere map with texture
		
		try {
			SphereMapMaterial material1 = new SphereMapMaterial();
			material1.setSphereMapStrength(.5f);
			material1.addTexture(jetTexture);
			material1.addTexture(sphereMapTexture);

			ObjectInputStream ois;
			ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.jet));
			jet1 = new BaseObject3D((SerializedObject3D)ois.readObject());
			jet1.setMaterial(material1);
			jet1.addLight(light);
			jet1.setY(2.5f);
			addChild(jet1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Vector3 axis = new Vector3(2, -4, 1);
		axis.normalize();
		
		Animation3D anim1 = new RotateAnimation3D(axis, 360);
		anim1.setRepeatMode(RepeatMode.INFINITE);
		anim1.setDuration(12000);
		anim1.setTransformable3D(jet1);
		registerAnimation(anim1);
		anim1.play();

		SphereMapMaterial material2 = new SphereMapMaterial();
		// -- set strength to 1
		material2.setSphereMapStrength(2);
		// -- important, indicate that we want to mix the sphere map with a color
		material2.setUseColor(true);		
		try {
			material2.addTexture(sphereMapTexture);
		} catch (TextureException e) {
			e.printStackTrace();
		}
		
		BaseObject3D jet2 = jet1.clone(false);
		jet2.setMaterial(material2);
		jet2.addLight(light);
		// -- also specify a color
		jet2.setColor(0xff666666, true);
		jet2.setY(-2.5f);
		addChild(jet2);
		
		Animation3D anim2 = new RotateAnimation3D(axis, -360);
		anim2.setRepeatMode(RepeatMode.INFINITE);
		anim2.setDuration(12000);
		anim2.setTransformable3D(jet2);
		registerAnimation(anim2);
		anim2.play();

		getCurrentCamera().setPosition(0, 0, 14);
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
