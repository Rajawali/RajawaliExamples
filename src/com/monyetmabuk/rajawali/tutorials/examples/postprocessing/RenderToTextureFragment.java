package com.monyetmabuk.rajawali.tutorials.examples.postprocessing;

import java.util.Random;

import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.math.vector.Vector3;
import rajawali.postprocessing.PostProcessingManager;
import rajawali.postprocessing.passes.RenderPass;
import rajawali.primitives.Cube;
import rajawali.primitives.Sphere;
import rajawali.scene.RajawaliScene;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class RenderToTextureFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new RenderToTextureRenderer(getActivity());
	}

	private final class RenderToTextureRenderer extends AExampleRenderer {
		private PostProcessingManager mEffects;
		private RajawaliScene mOtherScene;
		private Object3D mSphere;
		private ATexture mCurrentTexture;

		public RenderToTextureRenderer(Context context) {
			super(context);
		}

		public void initScene() {
			
			//
			// -- Create the scene that we are going to use for
			//    off-screen rendering
			//
			
			DirectionalLight light = new DirectionalLight();
			light.setPower(1);
			getCurrentScene().setBackgroundColor(0xeeeeee);
			getCurrentScene().addLight(light);

			Material material = new Material();
			material.enableLighting(true);
			material.setDiffuseMethod(new DiffuseMethod.Lambert());

			getCurrentCamera().setZ(10);

			Random random = new Random();

			for (int i = 0; i < 20; i++) {
				Cube cube = new Cube(1);
				cube.setPosition(-5 + random.nextFloat() * 10,
						-5 + random.nextFloat() * 10, random.nextFloat() * -10);
				cube.setMaterial(material);
				cube.setColor(0x666666 + random.nextInt(0x999999));
				getCurrentScene().addChild(cube);

				Vector3 randomAxis = new Vector3(random.nextFloat(),
						random.nextFloat(), random.nextFloat());
				randomAxis.normalize();

				RotateOnAxisAnimation anim = new RotateOnAxisAnimation(randomAxis,
						360);
				anim.setTransformable3D(cube);
				anim.setDurationMilliseconds(3000 + (int) (random.nextDouble() * 5000));
				anim.setRepeatMode(RepeatMode.INFINITE);
				getCurrentScene().registerAnimation(anim);
				anim.play();
			}

			//
			// -- Create the scene that will contain an object
			//    that uses the rendered to texture
			//
			
			mOtherScene = new RajawaliScene(this);
			mOtherScene.setBackgroundColor(0xffffff);
			mOtherScene.addLight(light);

			Material cubeMaterial = new Material();
			cubeMaterial.enableLighting(true);
			cubeMaterial.setColorInfluence(0);
			cubeMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());

			mSphere = new Sphere(1, 32, 32);
			mSphere.setMaterial(cubeMaterial);
			mOtherScene.addChild(mSphere);

			Vector3 axis = new Vector3(1, 1, 0);
			axis.normalize();

			RotateOnAxisAnimation anim = new RotateOnAxisAnimation(axis, 360);
			anim.setTransformable3D(mSphere);
			anim.setDurationMilliseconds(10000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			//
			// -- Set up the post processing manager
			//
			
			mEffects = new PostProcessingManager(this);
			RenderPass renderPass = new RenderPass(getCurrentScene(),
					getCurrentCamera(), 0);
			mEffects.addPass(renderPass);
			
			//
			// -- Other effect passes could be added here
			//

			switchScene(mOtherScene);
		}

		@Override
		public void onRender(final double deltaTime) {
			
			//
			// -- Off screen rendering first. Render to texture.
			
			mEffects.render(deltaTime);
			try {
				if (mCurrentTexture != null)
					mSphere.getMaterial().removeTexture(mCurrentTexture);
				
				//
				// -- Get the latest updated texture from the post
				//    processing manager
				//
				
				mCurrentTexture = mEffects.getTexture();
				mSphere.getMaterial().addTexture(mCurrentTexture);
			} catch (TextureException e) {
				e.printStackTrace();
			}
			super.onRender(deltaTime);
		}
	}
}
