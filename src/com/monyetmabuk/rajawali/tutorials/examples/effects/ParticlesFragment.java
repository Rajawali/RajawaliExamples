package com.monyetmabuk.rajawali.tutorials.examples.effects;

import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.ParticleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.effects.particles.ExampleParticleSystem;

public class ParticlesFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new ParticlesRenderer(getActivity());
	}

	private final class ParticlesRenderer extends AExampleRenderer {
		private final int MAX_FRAMES = 200;
		private int mFrameCount;
		private ExampleParticleSystem mParticleSystem;

		public ParticlesRenderer(Context context) {
			super(context);
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

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);

			mParticleSystem.setTime((float) mFrameCount * .2f);

			if (mFrameCount++ >= MAX_FRAMES)
				mFrameCount = 0;
		}

	}

}
