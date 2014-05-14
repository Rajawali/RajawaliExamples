package com.monyetmabuk.rajawali.tutorials.examples.animation;

import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.ColorAnimation3D;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Cube;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class ColorAnimationFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new ColorAnimationRenderer(getActivity());
	}

	private final class ColorAnimationRenderer extends AExampleRenderer {

		public ColorAnimationRenderer(Context context) {
			super(context);
		}

		@Override
		protected void initScene() {
			//
			// -- First cube
			//

			Material material1 = new Material();

			Cube cube1 = new Cube(1);
			cube1.setMaterial(material1);
			cube1.setTransparent(true);
			cube1.setX(-1);
			getCurrentScene().addChild(cube1);

			Animation3D anim = new ColorAnimation3D(0xaaff1111, 0xffffff11);
			anim.setTransformable3D(cube1);
			anim.setDurationMilliseconds(2000);
			anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			anim = new RotateOnAxisAnimation(Axis.Y, 359);
			anim.setTransformable3D(cube1);
			anim.setDurationMilliseconds(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			//
			// -- second cube
			//

			Material material2 = new Material();
			try {
				AlphaMapTexture alphaTex = new AlphaMapTexture("camdenTown", R.drawable.camden_town_alpha);
				alphaTex.setInfluence(.5f);
				material2.addTexture(alphaTex);
				material2.setColorInfluence(0);
			} catch (TextureException e) {
				e.printStackTrace();
			}
			material2.setColorInfluence(.5f);

			Cube cube2 = new Cube(1);
			cube2.setMaterial(material2);
			cube2.setX(1);
			cube2.setDoubleSided(true);
			getCurrentScene().addChild(cube2);

			anim = new ColorAnimation3D(0xaaff1111, 0xff0000ff);
			anim.setTransformable3D(cube2);
			anim.setDurationMilliseconds(2000);
			anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			anim = new RotateOnAxisAnimation(Axis.Y, -359);
			anim.setTransformable3D(cube2);
			anim.setDurationMilliseconds(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			getCurrentCamera().setPosition(0, 4, 8);
			getCurrentCamera().setLookAt(0, 0, 0);
		}

	}

}
