package com.monyetmabuk.rajawali.tutorials.examples.general;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Sphere;

public class BasicFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new BasicRenderer(getActivity());
	}

	private final class BasicRenderer extends AExampleRenderer {

		private DirectionalLight mLight;
		private Object3D mSphere;

		public BasicRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(1.0, 0.2, -1.0); // set the direction
			mLight.setColor(1.0f, 1.0f, 1.0f);
			mLight.setPower(2);
			
			getCurrentScene().addLight(mLight);

			try {
				Material material = new Material();
				material.addTexture(new Texture("earthColors",
						R.drawable.earthtruecolor_nasa_big));
				material.setColorInfluence(0);
				mSphere = new Sphere(1, 24, 24);
				mSphere.setMaterial(material);
				getCurrentScene().addChild(mSphere);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			getCurrentCamera().setLookAt(0, 0, 0);
			getCurrentCamera().setZ(6);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mSphere.rotate(Axis.Y, -1.0);
		}
	}
}
