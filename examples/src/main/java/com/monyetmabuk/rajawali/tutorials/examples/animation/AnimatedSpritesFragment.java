package com.monyetmabuk.rajawali.tutorials.examples.animation;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.animation.particles.ExampleParticleSystem2;

import javax.microedition.khronos.opengles.GL10;

public class AnimatedSpritesFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new AnimatedSpritesRenderer(getActivity());
	}

	private final class AnimatedSpritesRenderer extends AExampleRenderer {
		private final int MAX_FRAMES = 200;
		private int mFrameCount;
		private ExampleParticleSystem2 mParticleSystem;

		public AnimatedSpritesRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			getCurrentCamera().setPosition(0, 0, 10);
// TODO add particle system
			/*
			// -- explosion sprite sheet from:
			// http://gushh.net/blog/free-game-sprites-explosion-3/
			mParticleSystem = new ExampleParticleSystem2();
			mParticleSystem.setPointSize(600);
			try {
				mParticleSystem.getMaterial().addTexture(
						new Texture(R.drawable.explosion_3_40_128));
			} catch (TextureException e) {
				e.printStackTrace();
			}
			addChild(mParticleSystem);*/
		}

		@Override
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			/*
			mParticleSystem.setCurrentFrame(mFrameCount);
			mParticleSystem.setTime((float) mFrameCount * .1f);

			if (mFrameCount++ >= MAX_FRAMES)
				mFrameCount = 0;*/
		}

	}

}
