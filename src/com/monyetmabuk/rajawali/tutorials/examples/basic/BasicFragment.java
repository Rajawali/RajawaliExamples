package com.monyetmabuk.rajawali.tutorials.examples.basic;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Sphere;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class BasicFragment extends AExampleFragment {

	@Override
	protected ARajawaliRenderer createRenderer() {
		return new BasicRenderer(getActivity());
	}

	private final class BasicRenderer extends ARajawaliRenderer {

		private DirectionalLight mLight;
		private BaseObject3D mSphere;

		public BasicRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
			mLight.setColor(1.0f, 1.0f, 1.0f);
			mLight.setPower(2);

			try {
				DiffuseMaterial material = new DiffuseMaterial();
				material.addTexture(new Texture(
						R.drawable.earthtruecolor_nasa_big));
				mSphere = new Sphere(1, 24, 24);
				mSphere.setMaterial(material);
				mSphere.addLight(mLight);
				addChild(mSphere); // Queue an addition task for mSphere
			} catch (TextureException e) {
				e.printStackTrace();
			}

			getCurrentCamera().setZ(6);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mSphere.setRotY(mSphere.getRotY() + 1);
		}

	}

}
