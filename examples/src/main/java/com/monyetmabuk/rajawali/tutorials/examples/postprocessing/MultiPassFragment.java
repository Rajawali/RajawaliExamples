package com.monyetmabuk.rajawali.tutorials.examples.postprocessing;

import java.util.Random;

import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.math.vector.Vector3;
import rajawali.postprocessing.PostProcessingManager;
import rajawali.postprocessing.passes.BlurPass;
import rajawali.postprocessing.passes.BlurPass.Direction;
import rajawali.postprocessing.passes.EffectPass;
import rajawali.postprocessing.passes.RenderPass;
import rajawali.postprocessing.passes.SepiaPass;
import rajawali.primitives.Cube;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class MultiPassFragment extends AExampleFragment {
	@Override
	protected AExampleRenderer createRenderer() {
		return new SepiaFilterRenderer(getActivity());
	}
	
	private final class SepiaFilterRenderer extends AExampleRenderer {
		private PostProcessingManager mEffects;
		
		public SepiaFilterRenderer(Context context) {
			super(context);
		}
		
		public void initScene() {
			DirectionalLight light = new DirectionalLight();
			getCurrentScene().addLight(light);
			
			//
			// -- Create a material for all cubes
			//
			
			Material material = new Material();
			material.enableLighting(true);
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			
			getCurrentCamera().setZ(10);
			
			Random random = new Random();
			
			//
			// -- Generate cubes with random x, y, z
			//
			
			for(int i=0; i<10; i++) {
				Cube cube = new Cube(1);
				cube.setPosition(-5 + random.nextFloat() * 10, -5 + random.nextFloat() * 10, random.nextFloat() * -10);
				cube.setMaterial(material);
				cube.setColor(0x666666 + random.nextInt(0x999999));
				getCurrentScene().addChild(cube);
				
				Vector3 randomAxis = new Vector3(random.nextFloat(), random.nextFloat(), random.nextFloat());
				randomAxis.normalize();
				
				RotateOnAxisAnimation anim = new RotateOnAxisAnimation(randomAxis, 360);
				anim.setTransformable3D(cube);
				anim.setDurationMilliseconds(3000 + (int)(random.nextDouble() * 5000));
				anim.setRepeatMode(RepeatMode.INFINITE);
				getCurrentScene().registerAnimation(anim);
				anim.play();
			}
			
			//
			// -- Create a post processing manager. We can add multiple passes to this.
			//
			
			mEffects = new PostProcessingManager(this);
			
			//
			// -- A render pass renders the current scene to a texture. This texture will
			//    be used for post processing
			//
			
			RenderPass renderPass = new RenderPass(getCurrentScene(), getCurrentCamera(), 0);
			mEffects.addPass(renderPass);
			
			//
			// -- Add a Sepia effect
			//
			
			EffectPass sepiaPass = new SepiaPass();
			mEffects.addPass(sepiaPass);
			
			//
			// -- Add a Gaussian blur effect. This requires a horizontal and a vertical pass.
			//
			
			EffectPass horizontalPass = new BlurPass(Direction.HORIZONTAL, 6, mViewportWidth, mViewportHeight);
			mEffects.addPass(horizontalPass);
			EffectPass verticalPass = new BlurPass(Direction.VERTICAL, 6, mViewportWidth, mViewportHeight);
			
			//
			// -- Important. The last pass should render to screen or nothing will happen.
			//
			
			verticalPass.setRenderToScreen(true);
			mEffects.addPass(verticalPass);
		}
		
		@Override
		public void onRender(final double deltaTime) {
			
			//
			// -- Important. Call render() on the post processing manager.
			//
			
			mEffects.render(deltaTime);
		}
	}
}
