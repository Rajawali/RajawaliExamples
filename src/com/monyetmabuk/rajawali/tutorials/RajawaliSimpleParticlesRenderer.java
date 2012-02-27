package com.monyetmabuk.rajawali.tutorials;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.ParticleMaterial;
import rajawali.materials.TextureManager.TextureInfo;
import rajawali.math.Number3D;
import rajawali.primitives.Particle;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RajawaliSimpleParticlesRenderer extends RajawaliRenderer {
	private Stack<MyParticle> mParticles;
	private boolean mSceneInitialized;
	private final int NUM_PARTICLES = 100;
	private final int MAX_FRAMES = 200;
	private MyParticle mParticle;
	private int mFrameCount;
	private boolean mDoReset;
	
	public RajawaliSimpleParticlesRenderer(Context context) {
		super(context);
		setFrameRate(30);
		mClearChildren = false;
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		((RajawaliSimpleParticlesActivity)mContext).showLoader();
		mCamera.setPosition(0, 0, -10);

		Bitmap particleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.particle);
        TextureInfo particleTexture = mTextureManager.addTexture(particleBitmap);

        if(!mSceneInitialized) {
        	mParticles = new Stack<MyParticle>();
        	
        	for(int i=0; i<NUM_PARTICLES; ++i) {
		        mParticle = new MyParticle();
		        mParticle.setPointSize(100);
		        mParticles.add(mParticle);
		        mParticle.getPosition().y = -3;
		        mParticle.setZ((float)Math.random() * 40);
		        mParticle.velocity.setAll(-.05f + (Math.random() * .1f), Math.random() * .15f,-.05f + (Math.random() * .1f));
		        addChild(mParticle);
        	}
	        startRendering();
	        mSceneInitialized = true;
        }
    	for(int i=0; i<NUM_PARTICLES; ++i) {
    		mParticle = mParticles.get(i);
	        mParticle.setMaterial(new ParticleMaterial(), false);
	        mParticle.addTexture(particleTexture);
    	}
        ((RajawaliSimpleParticlesActivity)mContext).hideLoader();
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		
		if(mFrameCount++ >= MAX_FRAMES) 
		{
			mFrameCount = 0;
			mDoReset = true;
		}
		else mDoReset = false;
		
		for(int i=0; i<NUM_PARTICLES; ++i) {
			mParticle = mParticles.get(i);
			if(mDoReset)
			{
				mParticle.setPosition(0, -3, 0);
				mParticle.setZ((float)Math.random() * 40);
			}
			else
				mParticle.getPosition().add(mParticle.velocity);
		}
	}
	
	class MyParticle extends Particle {
		public Number3D velocity;
		
		public MyParticle() {
			super();
			velocity = new Number3D();
		}
	}
}
