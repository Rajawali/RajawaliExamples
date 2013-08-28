package com.monyetmabuk.rajawali.tutorials.examples.materials;

import rajawali.Object3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.NormalMapMaterial;
import rajawali.materials.NormalMapPhongMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.NormalMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class BumpMappingFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new BumpMappingRenderer(getActivity());
	}

	private final class BumpMappingRenderer extends AExampleRenderer {
		private PointLight mLight;
		private Object3D mEarth;
		private Animation3D mLightAnim;

		public BumpMappingRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new PointLight();
			mLight.setPosition(-2, -2, 0);
			mLight.setPower(2f);
			getCurrentCamera().setPosition(0, 0, 6);

			try {
				Plane cube = new Plane(18, 12, 2, 2);
				NormalMapMaterial material1 = new NormalMapMaterial();
				material1.addTexture(new Texture(
						R.drawable.masonry_wall_texture));
				material1.addTexture(new NormalMapTexture(
						R.drawable.masonry_wall_normal_map));
				cube.setMaterial(material1);
				cube.addLight(mLight);
				cube.setZ(-2);
				addChild(cube);

				RotateAnimation3D anim = new RotateAnimation3D(Axis.Y, -5, 5);
				anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
				anim.setDuration(5000);
				anim.setTransformable3D(cube);
				registerAnimation(anim);
				anim.play();

				mEarth = new Sphere(1, 32, 32);
				mEarth.setZ(-.5f);
				mEarth.addLight(mLight);
				addChild(mEarth);

				NormalMapPhongMaterial material2 = new NormalMapPhongMaterial();
				material2.addTexture(new Texture(R.drawable.earth_diffuse));
				material2
						.addTexture(new NormalMapTexture(R.drawable.earth_bump));
				material2.setShininess(150);
				mEarth.setMaterial(material2);

				RotateAnimation3D earthAnim = new RotateAnimation3D(Axis.Y, 359);
				earthAnim.setDuration(6000);
				earthAnim.setRepeatMode(RepeatMode.INFINITE);
				earthAnim.setTransformable3D(mEarth);
				registerAnimation(earthAnim);
				earthAnim.play();

			} catch (TextureException e) {
				e.printStackTrace();
			}

			mLightAnim = new TranslateAnimation3D(new Vector3(-2, 2, 2),
					new Vector3(2, -2, 2));
			mLightAnim.setDuration(4000);
			mLightAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			mLightAnim.setTransformable3D(mLight);
			mLightAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			registerAnimation(mLightAnim);
			mLightAnim.play();
		}

	}

}
