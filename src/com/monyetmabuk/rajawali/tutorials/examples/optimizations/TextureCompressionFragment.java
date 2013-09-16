package com.monyetmabuk.rajawali.tutorials.examples.optimizations;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Etc1Texture;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class TextureCompressionFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new TextureCompressionRenderer(getActivity());
	}

	private final class TextureCompressionRenderer extends AExampleRenderer {
		private Object3D mMipmappedPlane;
		private Object3D mPlane;

		public TextureCompressionRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			getCurrentCamera().setPosition(0, 0, 7);

			try {
				Texture texture1 = new Texture("texture1", new Etc1Texture(
						"etc1Tex1", R.raw.rajawali_tex_mip_0, null));
				Material material1 = new Material();
				material1.addTexture(texture1);
				mPlane = new Plane(2, 2, 1, 1);
				mPlane.setMaterial(material1);
				mPlane.setPosition(0, -1.25f, 0);
				mPlane.setDoubleSided(true);
				mPlane.setRotZ(90);
				addChild(mPlane);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			try {
				int[] resourceIds = new int[] { R.raw.rajawali_tex_mip_0,
						R.raw.rajawali_tex_mip_1, R.raw.rajawali_tex_mip_2,
						R.raw.rajawali_tex_mip_3, R.raw.rajawali_tex_mip_4,
						R.raw.rajawali_tex_mip_5, R.raw.rajawali_tex_mip_6,
						R.raw.rajawali_tex_mip_7, R.raw.rajawali_tex_mip_8,
						R.raw.rajawali_tex_mip_9 };
				Texture texture2 = new Texture("texture2", new Etc1Texture(
						"etc1Tex2", resourceIds));

				Material material2 = new Material();
				material2.addTexture(texture2);

				mMipmappedPlane = new Plane(2, 2, 1, 1);
				mMipmappedPlane.setMaterial(material2);
				mMipmappedPlane.setPosition(0, 1.25f, 0);
				mMipmappedPlane.setDoubleSided(true);
				mMipmappedPlane.setRotZ(90);
				addChild(mMipmappedPlane);
			} catch (TextureException e) {
				e.printStackTrace();
			}
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			// Rotate the plane to showcase difference between a mipmapped
			// texture and non-mipmapped texture.
			if (mMipmappedPlane != null) {
				mMipmappedPlane.setRotX(mMipmappedPlane.getRotX() - 0.1f);
				mMipmappedPlane.setRotY(mMipmappedPlane.getRotY() - 0.1f);
			}
			if (mPlane != null) {
				mPlane.setRotX(mPlane.getRotX() + 0.1f);
				mPlane.setRotY(mPlane.getRotY() + 0.1f);
			}
		}

	}

}
