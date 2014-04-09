package com.monyetmabuk.rajawali.tutorials.examples.materials;

import java.io.ObjectInputStream;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.lights.PointLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.SphereMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class SphereMapFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new SphereMapRenderer(getActivity());
	}

	private final class SphereMapRenderer extends AExampleRenderer {

		public SphereMapRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			PointLight light = new PointLight();
			light.setZ(6);
			light.setPower(2);
			
			getCurrentScene().addLight(light);

			Texture jetTexture = new Texture("jetTexture", R.drawable.jettexture);
			SphereMapTexture sphereMapTexture = new SphereMapTexture("manilaSphereMapTex", R.drawable.manila_sphere_map);
			
			jetTexture.setInfluence(.8f);
			// -- important!
			sphereMapTexture.isEnvironmentTexture(true);
			sphereMapTexture.setInfluence(.2f);
			
			Object3D jet1 = null;
			// -- sphere map with texture

			try {
				Material material1 = new Material();
				material1.enableLighting(true);
				material1.setDiffuseMethod(new DiffuseMethod.Lambert());
				material1.addTexture(jetTexture);
				material1.addTexture(sphereMapTexture);
				material1.setColorInfluence(0);

				ObjectInputStream ois;
				ois = new ObjectInputStream(mContext.getResources()
						.openRawResource(R.raw.jet));
				jet1 = new Object3D((SerializedObject3D) ois.readObject());
				jet1.setMaterial(material1);
				jet1.setY(2.5f);
				getCurrentScene().addChild(jet1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Vector3 axis = new Vector3(2, -4, 1);
			axis.normalize();

			Animation3D anim1 = new RotateOnAxisAnimation(axis, 360);
			anim1.setRepeatMode(RepeatMode.INFINITE);
			anim1.setDurationMilliseconds(12000);
			anim1.setTransformable3D(jet1);
			getCurrentScene().registerAnimation(anim1);
			anim1.play();

			sphereMapTexture = new SphereMapTexture("manilaSphereMapTex2", R.drawable.manila_sphere_map);
			sphereMapTexture.isEnvironmentTexture(true);
			sphereMapTexture.setInfluence(.5f);
			
			Material material2 = new Material();
			// -- important, indicate that we want to mix the sphere map with a color
			material2.enableLighting(true);
			material2.setDiffuseMethod(new DiffuseMethod.Lambert());
			try {
				material2.addTexture(sphereMapTexture);
			} catch (TextureException e) {
				e.printStackTrace();
			}
			material2.setColorInfluence(.5f);

			Object3D jet2 = jet1.clone(false);
			jet2.setMaterial(material2);
			// -- also specify a color
			jet2.setColor(0xff666666);
			jet2.setY(-2.5f);
			getCurrentScene().addChild(jet2);

			Animation3D anim2 = new RotateOnAxisAnimation(axis, -360);
			anim2.setRepeatMode(RepeatMode.INFINITE);
			anim2.setDurationMilliseconds(12000);
			anim2.setTransformable3D(jet2);
			getCurrentScene().registerAnimation(anim2);
			anim2.play();

			getCurrentCamera().setPosition(0, 0, 14);
		}

	}

}
