package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.ParticleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class RajawaliMoreParticlesRenderer extends RajawaliRenderer {
	private final int MAX_FRAMES = 200;
	private int mFrameCount;
	private ExampleParticleSystem mParticleSystem;

	public RajawaliMoreParticlesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
		getCurrentCamera().setPosition(0, 0, 10);

		mParticleSystem = new ExampleParticleSystem();
		ParticleMaterial material = new ParticleMaterial();
		try {
			material.addTexture(new Texture(R.drawable.particle));
		} catch (TextureException e) {
			e.printStackTrace();
		}
		mParticleSystem.setMaterial(material);
		mParticleSystem.setPointSize(100);
		addChild(mParticleSystem);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);

		mParticleSystem.setTime((float) mFrameCount * .2f);

		if (mFrameCount++ >= MAX_FRAMES)
			mFrameCount = 0;
	}
}
