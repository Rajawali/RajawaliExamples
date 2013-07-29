package com.monyetmabuk.rajawali.tutorials.examples.optimizations;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.materials.textures.TextureAtlas;
import rajawali.materials.textures.TexturePacker;
import rajawali.primitives.Cube;
import rajawali.primitives.NPrism;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class TextureAtlasFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new TextureAtlasRenderer(getActivity());
	}

	private final class TextureAtlasRenderer extends AExampleRenderer {
		private TextureAtlas mAtlas;
		private SimpleMaterial mAtlasMaterial;
		private Plane mAtlasPlane, mTilePlane;
		private Cube mTileCube;
		private Sphere mTileSphere;

		public TextureAtlasRenderer(Context context) {
			super(context);
		    getCurrentScene().setBackgroundColor(0x666666);
		}

		public void initScene() {		
			mAtlas = new TexturePacker(mContext).packTexturesFromAssets(1024, 1024, 0, false, "atlas");
			
			mAtlasMaterial = new SimpleMaterial();
			mAtlasMaterial.setUseSingleColor(true);

			try {
				mAtlasMaterial.addTexture(new Texture("atlasTexture", mAtlas.getPages()[0]));
			} catch (TextureException e) {
				e.printStackTrace();
			}
			
			mAtlasPlane = new Plane(1,1,1,1);
			mAtlasPlane.setMaterial(mAtlasMaterial);
			mAtlasPlane.setY(1);
			addChild(mAtlasPlane);
		
			mTileSphere = new Sphere(.35f, 20, 20);
			mTileSphere.setMaterial(mAtlasMaterial);
			mTileSphere.setAtlasTile("earthtruecolor_nasa_big", mAtlas);
			mTileSphere.setPosition(0, -.1f, 0);
			addChild(mTileSphere);

			mTileCube = new Cube(.5f);
			mTileCube.setMaterial(mAtlasMaterial);
			mTileCube.setAtlasTile("camden_town_alpha", mAtlas);
			mTileCube.setPosition(-.5f, -1f, 0);
			mTileCube.setTransparent(true);
			mTileCube.setRotX(-1);
			addChild(mTileCube);

			mTilePlane = new Plane(.6f,.6f,1,1);
			mTilePlane.setMaterial(mAtlasMaterial);
			mTilePlane.setAtlasTile("rajawali", mAtlas);
			mTilePlane.setPosition(.5f, -1f, 0);
			addChild(mTilePlane);
		}
		
		@Override
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mTileCube.setRotY(mTileCube.getRotY()+1);
			mTileSphere.setRotY(mTileSphere.getRotY()+1);
		}		
	}

}
