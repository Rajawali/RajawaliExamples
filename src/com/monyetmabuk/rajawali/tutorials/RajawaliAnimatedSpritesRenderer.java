package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliAnimatedSpritesRenderer extends RajawaliRenderer {
	private final int MAX_FRAMES = 200;
	private int mFrameCount;
	private ExampleParticleSystem2 mParticleSystem;

	public RajawaliAnimatedSpritesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		mCamera.setPosition(0, 0, 10);

		// -- explosion sprite sheet from:
		//    http://gushh.net/blog/free-game-sprites-explosion-3/
		Bitmap particleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explosion_3_40_128);
		rajawali.materials.TextureInfo particleTexture = mTextureManager.addTexture(particleBitmap);

		mParticleSystem = new ExampleParticleSystem2();
		mParticleSystem.setPointSize(600);
		addChild(mParticleSystem);
		mParticleSystem.addTexture(particleTexture);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);

		mParticleSystem.setCurrentFrame(mFrameCount);		
		mParticleSystem.setTime((float) mFrameCount * .1f);

		if (mFrameCount++ >= MAX_FRAMES)
			mFrameCount = 0;
	}
}
