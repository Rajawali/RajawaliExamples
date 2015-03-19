package com.monyetmabuk.rajawali.tutorials.examples.ui;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.materials.CustomMaterialPlugin;

import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.Material;
import rajawali.primitives.ScreenQuad;

public class TwoDimensionalFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new TwoDimensionalRenderer(getActivity());
	}

	private final class TwoDimensionalRenderer extends AExampleRenderer {

		private float mTime;
		private Material mCustomMaterial;

		public TwoDimensionalRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mCustomMaterial = new Material();
			mCustomMaterial.enableTime(true);
			mCustomMaterial.addPlugin(new CustomMaterialPlugin());

			ScreenQuad screenQuad = new ScreenQuad();
			screenQuad.setMaterial(mCustomMaterial);
			getCurrentScene().addChild(screenQuad);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mTime += .007f;
			mCustomMaterial.setTime(mTime);
		}

	}

}
