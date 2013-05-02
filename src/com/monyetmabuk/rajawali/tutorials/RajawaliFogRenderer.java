package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.parser.AParser.ParsingException;
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
		mLight = new DirectionalLight(0, -1, -1);
		mLight.setPower(.5f);
		
		Camera camera = getCurrentCamera();
		camera.setPosition(0, 1, 4);
		camera.setFogNear(1);
		camera.setFogFar(15);
		camera.setFogColor(0x999999);
		
		setFogEnabled(true);
		getCurrentScene().setBackgroundColor(0x999999);
		
		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.road);
		try {
			objParser.parse();
			mRoad = objParser.getParsedObject();
			mRoad.addLight(mLight);
			mRoad.setZ(-2);
			mRoad.setRotY(180);
			addChild(mRoad);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
		mRoad = objParser.getParsedObject();
		mRoad.addLight(mLight);
		mRoad.setZ(-2);
		mRoad.setRotY(180);
		addChild(mRoad);
		
		Bitmap roadTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.road);
		mRoad.getChildByName("Road").addTexture(mTextureManager.addTexture(roadTexture));
		
		Bitmap signTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sign);
		mRoad.getChildByName("Sign").addTexture(mTextureManager.addTexture(signTexture));
		
		Bitmap warningTexture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.warning);
		mRoad.getChildByName("Warning").addTexture(mTextureManager.addTexture(warningTexture));
		
		mCamAnim = new TranslateAnimation3D(new Number3D(0, 1, -23));
		mCamAnim.setDuration(8000);
		mCamAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		mCamAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		mCamAnim.setTransformable3D(getCurrentCamera());
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		registerAnimation(mCamAnim);
		mCamAnim.play();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mLight.setZ(getCurrentCamera().getZ());
	}
}
