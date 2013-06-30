package com.monyetmabuk.rajawali.tutorials.examples.animation;

import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.ColorAnimation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3.Axis;
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

			SimpleMaterial material1 = new SimpleMaterial();
			material1.setUseSingleColor(true);

			Cube cube1 = new Cube(1);
			cube1.setMaterial(material1);
			cube1.setTransparent(true);
			cube1.setX(-1);
			getCurrentScene().addChild(cube1);

			Animation3D anim = new ColorAnimation3D(0xaaff1111, 0xffffff11);
			anim.setTransformable3D(cube1);
			anim.setDuration(2000);
			anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			registerAnimation(anim);
			anim.play();

			anim = new RotateAnimation3D(Axis.Y, 359);
			anim.setTransformable3D(cube1);
			anim.setDuration(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			registerAnimation(anim);
			anim.play();

			//
			// -- second cube
			//

			SimpleMaterial material2 = new SimpleMaterial();
			material2.setColorBlendFactor(.5f);
			try {
				material2.addTexture(new Texture(R.drawable.camden_town_alpha));
			} catch (TextureException e) {
				e.printStackTrace();
			}
			material2.setUseSingleColor(true);

			Cube cube2 = new Cube(1);
			cube2.setMaterial(material2);
			cube2.setX(1);
			getCurrentScene().addChild(cube2);

			anim = new ColorAnimation3D(0xaaff1111, 0xff0000ff);
			anim.setTransformable3D(cube2);
			anim.setDuration(2000);
			anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			registerAnimation(anim);
			anim.play();

			anim = new RotateAnimation3D(Axis.Y, -359);
			anim.setTransformable3D(cube2);
			anim.setDuration(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			registerAnimation(anim);
			anim.play();

			getCurrentCamera().setPosition(0, 4, 8);
			getCurrentCamera().setLookAt(0, 0, 0);
		}

	}

}
