package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.lights.PointLight;
import rajawali.materials.SphereMapMaterial;
import rajawali.materials.TextureInfo;
import rajawali.materials.TextureManager.TextureType;
import rajawali.math.Number3D;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliSphereMapRenderer extends RajawaliRenderer {
	private Animation3D[] mAnims;

	public RajawaliSphereMapRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		PointLight light = new PointLight();
		light.setZ(-6);
		light.setPower(2);

		Bitmap sphereMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.manila_sphere_map);
		Bitmap texture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.jettexture);
		TextureInfo sphereMapTextureInfo = mTextureManager.addTexture(sphereMap, TextureType.SPHERE_MAP);

		BaseObject3D jet1 = null;
		// -- sphere map with texture
		SphereMapMaterial material1 = new SphereMapMaterial();
		material1.setSphereMapStrength(.5f);
		
		try {
			ObjectInputStream ois;
			ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.jet));
			jet1 = new BaseObject3D((SerializedObject3D)ois.readObject());
			jet1.setMaterial(material1);
			jet1.addLight(light);
			// -- add sphere map texture
			jet1.addTexture(sphereMapTextureInfo);
			// -- add diffuse texture
			jet1.addTexture(mTextureManager.addTexture(texture, TextureType.DIFFUSE));
			jet1.setY(2.5f);
			addChild(jet1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Number3D axis = new Number3D(2, -4, 1);
		axis.normalize();
		
		mAnims = new Animation3D[2];
		
		Animation3D anim1 = new RotateAnimation3D(axis, 360);
		anim1.setRepeatCount(Animation3D.INFINITE);
		anim1.setDuration(12000);
		anim1.setTransformable3D(jet1);
		
		mAnims[0] = anim1;
		
		SphereMapMaterial material2 = new SphereMapMaterial();
		// -- set strength to 1
		material2.setSphereMapStrength(2);
		// -- important, indicate that we want to mix the sphere map with a color
		material2.setUseColor(true);		
		
		BaseObject3D jet2 = jet1.clone(false);
		jet2.setMaterial(material2);
		jet2.addLight(light);
		// -- add sphere map texture only
		jet2.addTexture(sphereMapTextureInfo);
		// -- also specify a color
		jet2.setColor(0xff666666, true);
		jet2.setY(-2.5f);
		addChild(jet2);
		
		Animation3D anim2 = new RotateAnimation3D(axis, -360);
		anim2.setRepeatCount(Animation3D.INFINITE);
		anim2.setDuration(12000);
		anim2.setTransformable3D(jet2);

		mAnims[1] = anim2;
		
		mCamera.setPosition(0, 0, -14);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mAnims[0].start();
		mAnims[1].start();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
