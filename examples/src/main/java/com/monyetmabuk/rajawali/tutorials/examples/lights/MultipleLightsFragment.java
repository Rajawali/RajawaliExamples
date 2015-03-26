package com.monyetmabuk.rajawali.tutorials.examples.lights;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.TranslateAnimation3D;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;

public class MultipleLightsFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new MultipleLightsRenderer(getActivity());
	}

	private final class MultipleLightsRenderer extends AExampleRenderer {

		public MultipleLightsRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			PointLight light1 = new PointLight();
			light1.setPower(5);
			PointLight light2 = new PointLight();
			light2.setPower(5);

			getCurrentScene().addLight(light1);
			getCurrentScene().addLight(light2);
			
			getCurrentCamera().setPosition(-8, 8, 8);
			getCurrentCamera().setLookAt(0, 0, 0);

			try {
				Object3D jet = new Cube(2.0f);
				Material material = new Material();
				material.setDiffuseMethod(new DiffuseMethod.Lambert());
				material.enableLighting(true);
				material.addTexture(new Texture("jetTexture", R.drawable.jettexture));
				material.setColorInfluence(0);
				jet.setMaterial(material);
				jet.setPosition(1, 0, 0);
				jet.setRotY(180);
				getCurrentScene().addChild(jet);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Animation3D anim = new TranslateAnimation3D(
					new Vector3(-10, -10, 5), new Vector3(-10, 10, 5));
			anim.setDurationMilliseconds(4000);
			anim.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
			anim.setTransformable3D(light1);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			anim = new TranslateAnimation3D(new Vector3(10, 10, 5),
					new Vector3(10, -10, 5));
			anim.setDurationMilliseconds(2000);
			anim.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
			anim.setTransformable3D(light2);
			getCurrentScene().registerAnimation(anim);
			anim.play();
		}

	}
}
