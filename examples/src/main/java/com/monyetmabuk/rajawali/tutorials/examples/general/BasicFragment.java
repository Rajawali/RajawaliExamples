package com.monyetmabuk.rajawali.tutorials.examples.general;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;

import javax.microedition.khronos.opengles.GL10;

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
			} catch (ATexture.TextureException e) {
				e.printStackTrace();
			}

			getCurrentCamera().setLookAt(0, 0, 0);
			getCurrentCamera().setZ(6);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mSphere.rotate(Vector3.Axis.Y, -1.0);
		}
	}
}
