package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.ChaseCamera;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Number3D;
import rajawali.primitives.Cube;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.FloatMath;

public class RajawaliChaseCamRenderer extends RajawaliRenderer {
	private BaseObject3D mRaptor, mSphere;
	private BaseObject3D[] mCubes;
	private BaseObject3D mRootCube;
	private float mTime;
	
	public RajawaliChaseCamRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		DirectionalLight light = new DirectionalLight(0, -.6f, .4f);
		light.setPower(1);
		
		// -- create sky sphere
		mSphere = new Sphere(400, 8, 8);
		mSphere.setMaterial(new SimpleMaterial());
		mSphere.addTexture(mTextureManager.addTexture(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.skysphere)));
		mSphere.setDoubleSided(true);
		addChild(mSphere);

		try {
			// -- load gzipped serialized object
			ObjectInputStream ois;
			GZIPInputStream zis = new GZIPInputStream(mContext.getResources().openRawResource(R.raw.raptor));
			ois = new ObjectInputStream(zis);
			mRaptor = new BaseObject3D((SerializedObject3D)ois.readObject());
			mRaptor.setMaterial(new DiffuseMaterial());
			mRaptor.addLight(light);
			mRaptor.addTexture(mTextureManager.addTexture(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.raptor_texture)));
			mRaptor.setScale(.5f);
			addChild(mRaptor);
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
		// -- create a bunch of cubes that will serve as orientation helpers
		
		mCubes = new BaseObject3D[30];
		
		mRootCube = new Cube(1);
		mRootCube.setMaterial(new DiffuseMaterial());
		mRootCube.addTexture(mTextureManager.addTexture(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.camouflage)));
		mRootCube.addLight(light);
		mRootCube.setY(-1f);
		// -- similar objects with the same material, optimize
		mRootCube.setRenderChildrenAsBatch(true);
		addChild(mRootCube);
		mCubes[0] = mRootCube;
		
		for(int i=1; i<mCubes.length; ++i) {
			BaseObject3D cube = mRootCube.clone(true);
			cube.setY(-1f);
			cube.setZ(i * 30);
			mRootCube.addChild(cube);
			mCubes[i] = cube;
		}		
		
		// -- create a chase camera
		//    the first parameter is the camera offset
		//    the second parameter is the interpolation factor
		ChaseCamera chaseCamera = new ChaseCamera(new Number3D(0, 3, 16), .1f);
		// -- tell the camera which object to chase
		chaseCamera.setObjectToChase(mRaptor);
		// -- set the far plane to 1000 so that we actually see the sky sphere
		chaseCamera.setFarPlane(1000);
		replaceAndSwitchCamera(chaseCamera, 0);
	}
	
	public void setCameraOffset(Number3D offset) {
		// -- change the camera offset
		((ChaseCamera) getCurrentCamera()).setCameraOffset(offset);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		// -- no proper physics here, just a bad approximation to keep
		//    this example as short as possible ;-)
		mRaptor.setZ(mRaptor.getZ() + 2f);
		mRaptor.setX(FloatMath.sin(mTime) * 20f);
		mRaptor.setRotZ(FloatMath.sin(mTime + 8f) * -30f);
		mRaptor.setRotY(180 + (mRaptor.getRotZ() * .1f));
		mRaptor.setRotY(180);
		mRaptor.setY(FloatMath.cos(mTime) * 10f);
		mRaptor.setRotX(FloatMath.cos(mTime + 1f) * -20f);
		
		mSphere.setZ(mRaptor.getZ());
		mTime += .01f;

		if(mRootCube.getZ() - mRaptor.getZ() <= (30 * -6)) {
			mRootCube.setZ(mRaptor.getZ());
		}
	}
}
