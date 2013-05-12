package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Number3D;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RajawaliFogRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;
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
		
		try {
			DiffuseMaterial roadMaterial = new DiffuseMaterial();
			roadMaterial.addTexture(new Texture(R.drawable.road));
			mRoad.getChildByName("Road").setMaterial(roadMaterial);
			
			DiffuseMaterial signMaterial = new DiffuseMaterial();
			signMaterial.addTexture(new Texture(R.drawable.sign));
			mRoad.getChildByName("Sign").setMaterial(signMaterial);
			
			DiffuseMaterial warningMaterial = new DiffuseMaterial();
			warningMaterial.addTexture(new Texture(R.drawable.warning));
			mRoad.getChildByName("Warning").setMaterial(warningMaterial);
		} catch(TextureException tme) {
			tme.printStackTrace();
		}
		
		TranslateAnimation3D camAnim = new TranslateAnimation3D(new Number3D(0, 1, -23));
		camAnim.setDuration(8000);
		camAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		camAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		camAnim.setTransformable3D(getCurrentCamera());
		registerAnimation(camAnim);
		camAnim.play();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mLight.setZ(getCurrentCamera().getZ());
	}
}
