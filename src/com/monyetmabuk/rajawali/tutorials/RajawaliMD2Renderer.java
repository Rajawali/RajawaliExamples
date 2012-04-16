package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.mesh.VertexAnimationObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.parser.MD2Parser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliMD2Renderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private VertexAnimationObject3D mOgre;

	public RajawaliMD2Renderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	public void playAnimation(String name) {
		if (name.equals("loop all")) {
			mOgre.play();
		} else {
			mOgre.play(name, false);
		}
	}
	
	protected void initScene() {
		mLight = new DirectionalLight(0, 0, 1);
		mLight.setPosition(0, 0, -6);
		mLight.setPower(2);
		mCamera.setPosition(0, 0, -8);

		MD2Parser parser = new MD2Parser(mContext.getResources(), mTextureManager, R.raw.ogro);
		parser.parse();

		mOgre = (VertexAnimationObject3D) parser.getParsedAnimationObject();
		mOgre.setLight(mLight);
		mOgre.setScale(.07f);
		mOgre.setRotZ(90);
		mOgre.setRotY(180);
		mOgre.setY(-1);

		addChild(mOgre);

		mOgre.play();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
}
