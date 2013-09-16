package com.monyetmabuk.rajawali.tutorials.examples.general;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Sphere;
import rajawali.util.RajLog;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class BasicFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new BasicRenderer(getActivity());
	}

	private final class BasicRenderer extends AExampleRenderer {

		private DirectionalLight mLight;
		private Object3D mSphere;

		public BasicRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			setFrameRate(1);
			mLight = new DirectionalLight(1.0, 0.2, -1.0); // set the direction
			mLight.setColor(1.0f, 1.0f, 1.0f);
			mLight.setPower(2);
			
			getCurrentScene().addLight(mLight);

			try {
				Material material = new Material();
				material.addTexture(new Texture("earthColors",
						R.drawable.earthtruecolor_nasa_big));
				mSphere = new Sphere(1, 24, 24);
				mSphere.setMaterial(material);
				getCurrentScene().addChild(mSphere);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			/*RajLog.i("Camera Position: " + getCurrentCamera().getPosition());
			RajLog.i("Camera LookAt: " + getCurrentCamera().getLookAt());
			RajLog.i("Camera LookAt Enabled: " + getCurrentCamera().isLookAtEnabled());
			RajLog.i("Camera LookAt Valid: " + getCurrentCamera().isLookAtValid());
			RajLog.i("Camera Orientation: " + getCurrentCamera().getOrientation(new Quaternion()));*/
			getCurrentCamera().setLookAt(0, 0, 0);
			getCurrentCamera().setZ(6);
			getCurrentCamera().setY(1);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mSphere.rotate(Axis.Y, 1.0);
			RajLog.i("Camera View Matrix: " + getCurrentCamera().getViewMatrix());
			RajLog.v("Sphere Model Matrix: \n" + mSphere.getModelMatrix());
		}
	}
}
