package com.monyetmabuk.rajawali.tutorials.examples.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.primitives.Cube;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.materials.CustomMaterialPlugin;

public class CustomMaterialShaderFragment extends AExampleFragment {
	@Override
	protected AExampleRenderer createRenderer() {
		return new CustomShaderRenderer(getActivity());
	}

	private final class CustomShaderRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private Object3D mCube;
		private float mTime;
		private Material mMaterial;

		public CustomShaderRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(0, 1, 1);

			getCurrentScene().addLight(mLight);
			mMaterial = new Material();
			mMaterial.enableTime(true);
			mMaterial.addPlugin(new CustomMaterialPlugin());

			mCube = new Cube(2);
			mCube.setMaterial(mMaterial);
			getCurrentScene().addChild(mCube);

			getCurrentCamera().setPosition(0, 0, 10);

			mTime = 0;
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mTime += .007f;
			mMaterial.setTime(mTime);
			mCube.setRotX(mCube.getRotX() + .5f);
			mCube.setRotY(mCube.getRotY() + .5f);
		}
	}
}
