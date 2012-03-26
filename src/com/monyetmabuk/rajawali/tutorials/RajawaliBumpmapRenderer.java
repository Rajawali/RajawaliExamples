package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAroundAnimation3D;
import rajawali.animation.RotateAroundAnimation3D.Axis;
import rajawali.lights.DirectionalLight;
import rajawali.materials.BumpmapMaterial;
import rajawali.materials.TextureManager.TextureType;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliBumpmapRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mHalfSphere;
	private boolean mSceneInitialized;
	private Bitmap mDiffuseTexture;
	private Bitmap mBumpTexture;
	
	public RajawaliBumpmapRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mClearChildren = false;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity)mContext).showLoader();
		
		if (!mSceneInitialized) {
			mLight = new DirectionalLight(0, 0, 1);
			mLight.setPosition(-2, -2, -5);
			mCamera.setPosition(0, 0, -5);
			
			ObjParser objParser = new ObjParser(mContext.getResources(),
					mTextureManager, R.raw.half_sphere_obj);
			objParser.parse();
			mHalfSphere = objParser.getParsedObject();
			mHalfSphere.setLight(mLight);
			mHalfSphere.setRotX(-90);
			addChild(mHalfSphere);
			
			mDiffuseTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.half_sphere_texture);
			mBumpTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.half_sphere_normal);

			mSceneInitialized = true;
			
			startRendering();
		}
		
		mHalfSphere.setMaterial(new BumpmapMaterial());
		mHalfSphere.addTexture(mTextureManager.addTexture(mDiffuseTexture, TextureType.DIFFUSE, true, false));
		mHalfSphere.addTexture(mTextureManager.addTexture(mBumpTexture, TextureType.BUMP, false, false));

		Animation3D lightAnim = new RotateAroundAnimation3D(new Number3D(), Axis.Z, 6);
		lightAnim.setDuration(5000);
		lightAnim.setRepeatCount(Animation3D.INFINITE);
		lightAnim.setTransformable3D(mLight);
		lightAnim.start();
		
		((RajawaliExampleActivity)mContext).hideLoader();
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
