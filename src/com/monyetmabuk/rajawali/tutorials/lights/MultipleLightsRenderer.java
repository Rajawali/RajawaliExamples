package com.monyetmabuk.rajawali.tutorials.lights;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class MultipleLightsRenderer extends RajawaliRenderer {
	public MultipleLightsRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		PointLight light1 = new PointLight();
		light1.setPower(5);
		PointLight light2 = new PointLight();
		light2.setPower(5);

		getCurrentCamera().setPosition(-8, 8, 8);
		getCurrentCamera().setLookAt(0, 0, 0);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.jet));
			SerializedObject3D serializedJet = (SerializedObject3D) ois.readObject();
			ois.close();
			
			BaseObject3D jet = new BaseObject3D(serializedJet);
			DiffuseMaterial material = new DiffuseMaterial();
			material.addTexture(new Texture(R.drawable.jettexture));
			jet.setMaterial(material);
			jet.setPosition(1, 0, 0);
			jet.addLight(light1);
			jet.addLight(light2);
			jet.setRotY(180);
			addChild(jet);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Animation3D anim = new TranslateAnimation3D(new Vector3(-10, -10, 5), new Vector3(-10, 10, 5));
		anim.setDuration(4000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		anim.setTransformable3D(light1);
		registerAnimation(anim);
		anim.play();
		
		anim = new TranslateAnimation3D(new Vector3(10, 10, 5), new Vector3(10, -10, 5));
		anim.setDuration(2000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		anim.setTransformable3D(light2);
		registerAnimation(anim);
		anim.play();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
}
