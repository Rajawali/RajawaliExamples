package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.lights.PointLight;
import rajawali.materials.BumpmapMaterial;
import rajawali.materials.BumpmapPhongMaterial;
import rajawali.materials.TextureManager.TextureType;
import rajawali.math.Number3D;
import rajawali.math.Number3D.Axis;
import rajawali.parser.ObjParser;
import rajawali.parser.AParser.ParsingException;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliBumpmapRenderer extends RajawaliRenderer {
	private PointLight mLight;
	private BaseObject3D mHalfSphere1;
	private BaseObject3D mHalfSphere2;
	private Bitmap mDiffuseTexture1;
	private Bitmap mBumpTexture1;
	private Bitmap mDiffuseTexture2;
	private Bitmap mBumpTexture2;
	private Animation3D mLightAnim;

	public RajawaliBumpmapRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new PointLight();
		mLight.setPosition(-2, -2, 8);
		mLight.setPower(2f);
		getCurrentCamera().setPosition(0, 0, 6);

		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bumpsphere);
		try {
			objParser.parse();
			mHalfSphere1 = objParser.getParsedObject();
			mHalfSphere1.addLight(mLight);
			mHalfSphere1.setRotX(-90);
			mHalfSphere1.setY(-1.2f);
			addChild(mHalfSphere1);
			
			objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bumptorus);
			objParser.parse();
			mHalfSphere2 = objParser.getParsedObject();
			mHalfSphere2.addLight(mLight);
			mHalfSphere2.setRotX(-45);
			mHalfSphere2.setY(1.2f);
			mHalfSphere2.setRotX(-45);
			addChild(mHalfSphere2);
	
			mDiffuseTexture1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sphere_texture);
			mBumpTexture1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sphere_normal);
			mDiffuseTexture2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.torus_texture);
			mBumpTexture2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.torus_normal);
	
			mHalfSphere1.setMaterial(new BumpmapMaterial());
			mHalfSphere1.addTexture(mTextureManager.addTexture(mDiffuseTexture1, TextureType.DIFFUSE));
			mHalfSphere1.addTexture(mTextureManager.addTexture(mBumpTexture1, TextureType.BUMP));
	
			mHalfSphere2.setMaterial(new BumpmapPhongMaterial());
			mHalfSphere2.addTexture(mTextureManager.addTexture(mDiffuseTexture2, TextureType.DIFFUSE));
			mHalfSphere2.addTexture(mTextureManager.addTexture(mBumpTexture2, TextureType.BUMP));
		} catch(ParsingException e) {
			e.printStackTrace();
		}
		mHalfSphere2 = objParser.getParsedObject();
		mHalfSphere2.addLight(mLight);
		mHalfSphere2.setRotX(-45);
		mHalfSphere2.setY(1.2f);
		mHalfSphere2.setRotX(-45);
		addChild(mHalfSphere2);

		mDiffuseTexture1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sphere_texture);
		mBumpTexture1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sphere_normal);
		mDiffuseTexture2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.torus_texture);
		mBumpTexture2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.torus_normal);

		mHalfSphere1.setMaterial(new BumpmapMaterial());
		mHalfSphere1.addTexture(mTextureManager.addTexture(mDiffuseTexture1, TextureType.DIFFUSE));
		mHalfSphere1.addTexture(mTextureManager.addTexture(mBumpTexture1, TextureType.BUMP));

		mHalfSphere2.setMaterial(new BumpmapPhongMaterial());
		mHalfSphere2.addTexture(mTextureManager.addTexture(mDiffuseTexture2, TextureType.DIFFUSE));
		mHalfSphere2.addTexture(mTextureManager.addTexture(mBumpTexture2, TextureType.BUMP));

		mLightAnim = new EllipticalOrbitAnimation3D(new Number3D(0, 0, -4), new Number3D(0, 4, 0), Number3D.getAxisVector(Axis.Z), 0, OrbitDirection.CLOCKWISE);
		mLightAnim.setDuration(5000);
		mLightAnim.setRepeatMode(RepeatMode.INFINITE);
		mLightAnim.setTransformable3D(mLight);
		registerAnimation(mLightAnim);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mLightAnim.play();		
	}
}
