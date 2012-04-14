package com.monyetmabuk.rajawali.tutorials;

import java.io.ObjectInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAroundAnimation3D;
import rajawali.animation.RotateAroundAnimation3D.Axis;
import rajawali.lights.DirectionalLight;
import rajawali.materials.BumpmapMaterial;
import rajawali.materials.BumpmapPhongMaterial;
import rajawali.materials.TextureManager.TextureType;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliBumpmapRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mHalfSphere1;
	private BaseObject3D mHalfSphere2;
	private boolean mSceneInitialized;
	private Bitmap mDiffuseTexture1;
	private Bitmap mBumpTexture1;
	private Bitmap mDiffuseTexture2;
	private Bitmap mBumpTexture2;
	
	public RajawaliBumpmapRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mClearChildren = false;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity)mContext).showLoader();
		
		if (!mSceneInitialized) {
			mLight = new DirectionalLight(0, 0, 1);
			mLight.setPosition(-2, -2, -3);
			mCamera.setPosition(0, 0, -6);
			
			ObjParser objParser = new ObjParser(mContext.getResources(),
					mTextureManager, R.raw.half_sphere_obj);
			objParser.parse();
			mHalfSphere1 = objParser.getParsedObject();
			mHalfSphere1.setLight(mLight);
			mHalfSphere1.setRotX(-90);
			mHalfSphere1.setY(-1.2f);
			addChild(mHalfSphere1);
			
			try {
				ObjectInputStream ois = new ObjectInputStream(mContext.getResources().openRawResource(R.raw.monkey_normal));
				SerializedObject3D serializedMonkey = (SerializedObject3D)ois.readObject();
				ois.close();
				
				mHalfSphere2 = new BaseObject3D(serializedMonkey);
				mHalfSphere2.setLight(mLight);
				mHalfSphere2.setRotX(-90);
				mHalfSphere2.setY(1.2f);
				addChild(mHalfSphere2);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			mDiffuseTexture1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.half_sphere_texture);
			mBumpTexture1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.half_sphere_normal);
			mDiffuseTexture2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.monkey_texture);
			mBumpTexture2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.monkey_normal);
			
			mSceneInitialized = true;
			
			startRendering();
		}
		
		mHalfSphere1.setMaterial(new BumpmapMaterial());
		mHalfSphere1.addTexture(mTextureManager.addTexture(mDiffuseTexture1, TextureType.DIFFUSE, true, false));
		mHalfSphere1.addTexture(mTextureManager.addTexture(mBumpTexture1, TextureType.BUMP, false, false));
		
		mHalfSphere2.setMaterial(new BumpmapPhongMaterial());
		mHalfSphere2.addTexture(mTextureManager.addTexture(mDiffuseTexture2, TextureType.DIFFUSE, true, false));
		mHalfSphere2.addTexture(mTextureManager.addTexture(mBumpTexture2, TextureType.BUMP, false, false));

		Animation3D lightAnim = new RotateAroundAnimation3D(new Number3D(0, 0, 0), Axis.Z, 4);
		lightAnim.setDuration(5000);
		lightAnim.setRepeatCount(Animation3D.INFINITE);
		lightAnim.setTransformable3D(mLight);
		lightAnim.start();
		
		((RajawaliExampleActivity)mContext).hideLoader();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
