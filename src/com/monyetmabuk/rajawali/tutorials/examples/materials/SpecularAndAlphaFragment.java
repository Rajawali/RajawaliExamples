package com.monyetmabuk.rajawali.tutorials.examples.materials;

import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.PhongMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.SpecularMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import rajawali.math.Vector3.Axis;
import rajawali.primitives.Sphere;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class SpecularAndAlphaFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new SpecularAndAlphaRenderer(getActivity());
	}

	private final class SpecularAndAlphaRenderer extends AExampleRenderer {

		public SpecularAndAlphaRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			PointLight pointLight = new PointLight();
			pointLight.setPower(1);
			pointLight.setPosition(-1, 1, 4);

			try {
				Texture earthTexture = new Texture(R.drawable.earth_diffuse);

				PhongMaterial material = new PhongMaterial();
				material.addTexture(earthTexture);
				material.addTexture(new SpecularMapTexture(
						R.drawable.earth_specular));
				material.setShininess(40);

				Sphere sphere = new Sphere(1, 32, 24);
				sphere.setMaterial(material);
				sphere.addLight(pointLight);
				sphere.setY(1.2f);
				addChild(sphere);

				RotateAnimation3D sphereAnim = new RotateAnimation3D(Axis.Y,
						359);
				sphereAnim.setDuration(14000);
				sphereAnim.setRepeatMode(RepeatMode.INFINITE);
				sphereAnim.setTransformable3D(sphere);
				registerAnimation(sphereAnim);
				sphereAnim.play();

				material = new PhongMaterial();
				material.setAlphaMaskingEnabled(true);
				material.addTexture(earthTexture);
				material.addTexture(new AlphaMapTexture(
						R.drawable.camden_town_alpha));

				sphere = new Sphere(1, 32, 24);
				sphere.setMaterial(material);
				sphere.setDoubleSided(true);
				sphere.addLight(pointLight);
				sphere.setY(-1.2f);
				addChild(sphere);

				sphereAnim = new RotateAnimation3D(Axis.Y, -359);
				sphereAnim.setDuration(10000);
				sphereAnim.setRepeatMode(RepeatMode.INFINITE);
				sphereAnim.setTransformable3D(sphere);
				registerAnimation(sphereAnim);
				sphereAnim.play();
			} catch (TextureException e) {
				e.printStackTrace();
			}

			TranslateAnimation3D lightAnim = new TranslateAnimation3D(
					new Vector3(-2, 3, 3), new Vector3(2, -1, 3));
			lightAnim.setDuration(3000);
			lightAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			lightAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			lightAnim.setTransformable3D(pointLight);
			registerAnimation(lightAnim);
			lightAnim.play();

			getCurrentCamera().setZ(6);
		}

	}

}
