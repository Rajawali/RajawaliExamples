package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.ParticleMaterial;
import rajawali.materials.TextureManager.TextureInfo;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliMoreParticlesRenderer extends RajawaliRenderer {
	private boolean mSceneInitialized;
	private final int MAX_FRAMES = 200;
	private int mFrameCount;
	private ExampleParticleSystem mParticleSystem;

	public RajawaliMoreParticlesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).showLoader();
		mCamera.setPosition(0, 0, -10);

		Bitmap particleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.particle);
		TextureInfo particleTexture = mTextureManager.addTexture(particleBitmap);

		if (!mSceneInitialized) {
			mParticleSystem = new ExampleParticleSystem();
			mParticleSystem.setPointSize(100);
			addChild(mParticleSystem);
			startRendering();
		}

		mParticleSystem.setMaterial(new ParticleMaterial(), false);
		mParticleSystem.addTexture(particleTexture);

		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);

		mParticleSystem.setTime((float) mFrameCount * .2f);

		if (mFrameCount++ >= MAX_FRAMES)
			mFrameCount = 0;
	}
}
