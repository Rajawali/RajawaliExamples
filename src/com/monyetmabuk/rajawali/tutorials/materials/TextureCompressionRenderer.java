/**
 * @author Andrew Jo (andrewjo@gmail.com)
 * www.andrewjo.com
 */
package com.monyetmabuk.rajawali.tutorials.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Etc1Texture;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;

public class TextureCompressionRenderer extends RajawaliRenderer {
	private BaseObject3D mMipmappedPlane;
	private BaseObject3D mPlane;

	public TextureCompressionRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		getCurrentCamera().setPosition(0, 0, 7);

		try {
			Texture texture1 = new Texture("texture1", new Etc1Texture(
					"etc1Tex1", R.raw.rajawali_tex_mip_0, null));
			SimpleMaterial material1 = new SimpleMaterial();
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
			Texture texture2 = new Texture("texture2", new Etc1Texture("etc1Tex2", resourceIds));
			
			SimpleMaterial material2 = new SimpleMaterial();
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
