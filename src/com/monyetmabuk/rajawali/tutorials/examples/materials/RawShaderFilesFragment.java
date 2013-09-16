package com.monyetmabuk.rajawali.tutorials.examples.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Sphere;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.materials.materials.CustomRawFragmentShader;
import com.monyetmabuk.rajawali.tutorials.examples.materials.materials.CustomRawVertexShader;

public class RawShaderFilesFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new RawShaderFilesRenderer(getActivity());
	}

	public class RawShaderFilesRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private Object3D mCube;
		private float mTime;
		private Material mMaterial;

		public RawShaderFilesRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(0, 1, 1);

			getCurrentScene().addLight(mLight);

			mMaterial = new Material(new CustomRawVertexShader(), new CustomRawFragmentShader());
			mMaterial.enableTime(true);
			try {
				Texture texture = new Texture("myTex", R.drawable.flickrpics);
				texture.setInfluence(.5f);
				mMaterial.addTexture(texture);
			} catch (TextureException e) {
				e.printStackTrace();
			}
			mMaterial.setColorInfluence(.5f);
			mCube = new Sphere(2, 64, 64);
			mCube.setMaterial(mMaterial);
			addChild(mCube);

			getCurrentCamera().setPosition(0, 0, 10);

			mTime = 0;
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mTime += .007f;
			mMaterial.setTime(mTime);
		}
	}

}
