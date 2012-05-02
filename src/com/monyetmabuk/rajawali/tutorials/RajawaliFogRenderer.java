package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RajawaliFogRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private Animation3D mCamAnim;
	private BaseObject3D mRoad;

	public RajawaliFogRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		mLight = new DirectionalLight();
		mLight.setPosition(0, 4, 10);
		mLight.setPower(.5f);
		
		mCamera.setPosition(0, 1, -4);
		mCamera.setFogNear(1);
		mCamera.setFogFar(15);
		mCamera.setFogColor(0x999999);
		
		setFogEnabled(true);
		setBackgroundColor(0x999999);
		
		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.road);
		objParser.parse();
		mRoad = objParser.getParsedObject();
		mRoad.addLight(mLight);
		mRoad.setZ(2);
		addChild(mRoad);
		
		Bitmap roadTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.road);
		mRoad.getChildByName("Road").addTexture(mTextureManager.addTexture(roadTexture));
		
		Bitmap signTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sign);
		mRoad.getChildByName("Sign").addTexture(mTextureManager.addTexture(signTexture));
		
		Bitmap warningTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.warning);
		mRoad.getChildByName("Warning").addTexture(mTextureManager.addTexture(warningTexture));
		
		mCamAnim = new TranslateAnimation3D(new Number3D(0, 1, 23));
		mCamAnim.setDuration(8000);
		mCamAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		mCamAnim.setRepeatCount(Animation3D.INFINITE);
		mCamAnim.setRepeatMode(Animation3D.REVERSE);
		mCamAnim.setTransformable3D(mCamera);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mCamAnim.start();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mLight.setZ(mCamera.getZ());
	}
}
