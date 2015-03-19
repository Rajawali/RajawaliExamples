package com.monyetmabuk.rajawali.tutorials.examples.optimizations;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.materials.textures.TextureAtlas;
import rajawali.materials.textures.TexturePacker;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;

public class TextureAtlasFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new TextureAtlasRenderer(getActivity());
	}

	private final class TextureAtlasRenderer extends AExampleRenderer {
		private TextureAtlas mAtlas;
		private Material mAtlasMaterial, mSphereMaterial, mCubeMaterial, mPlaneMaterial;
		private Plane mAtlasPlane, mTilePlane;
		private Cube mTileCube;
		private Sphere mTileSphere;

		public TextureAtlasRenderer(Context context) {
			super(context);
		    getCurrentScene().setBackgroundColor(0x666666);
		}

		public void initScene() {
			//
			// -- Pack all textures in the "assets/atlas" folder into an 1024x1024 atlas
			// -- this should be used to simplify implementation of multiple textures
			// -- and to reduce texture binding calls to the GPU for increased performance
			//
			mAtlas = new TexturePacker(mContext).packTexturesFromAssets(1024, 1024, 0, false, "atlas");
			
			mAtlasMaterial = new Material();
			mSphereMaterial = new Material();
			mCubeMaterial = new Material();
			mPlaneMaterial = new Material();

			try {
				//
				// -- Add the entire atlas to a material so it can be shown in the example
				// -- this is not necessary in typical use cases
				//
				mAtlasMaterial.addTexture(new Texture("atlasTexture", mAtlas.getPages()[0]));
				mAtlasMaterial.setColorInfluence(0);
				//
				// -- Add each target texture to the material
				// -- they are pulled from the atlas by their original resource name
				//
				mSphereMaterial.addTexture(new Texture("earthtruecolor_nasa_big", mAtlas));
				mSphereMaterial.setColorInfluence(0);
				mCubeMaterial.addTexture(new Texture("camden_town_alpha", mAtlas));
				mCubeMaterial.setColorInfluence(0);
				mPlaneMaterial.addTexture(new Texture("rajawali", mAtlas));
				mPlaneMaterial.setColorInfluence(0);
			} catch (TextureException e) {
				e.printStackTrace();
			}
			
			//
			// -- Show the full atlas for demonstration purposes
			//
			mAtlasPlane = new Plane(Axis.Z);
			mAtlasPlane.setMaterial(mAtlasMaterial);
			mAtlasPlane.setY(1);
			getCurrentScene().addChild(mAtlasPlane);
		
			mTileSphere = new Sphere(.35f, 20, 20);
			mTileSphere.setMaterial(mAtlasMaterial);
			//
			// -- The method 'setAtlasTile' is used to scale the UVs of the target object
			// -- so that the appropriate image within the atlas is displayed
			//
			mTileSphere.setAtlasTile("earthtruecolor_nasa_big", mAtlas);
			mTileSphere.setPosition(0, -.1f, 0);
			getCurrentScene().addChild(mTileSphere);

			mTileCube = new Cube(.5f);
			mTileCube.setMaterial(mAtlasMaterial);
			mTileCube.setAtlasTile("camden_town_alpha", mAtlas);
			mTileCube.setPosition(-.5f, -1f, 0);
			mTileCube.setRotX(-1);
			getCurrentScene().addChild(mTileCube);

			mTilePlane = new Plane(.6f,.6f,1,1);
			mTilePlane.setMaterial(mAtlasMaterial);
			mTilePlane.setAtlasTile("rajawali", mAtlas);
			mTilePlane.setPosition(.5f, -1f, 0);
			getCurrentScene().addChild(mTilePlane);
		}
		
		@Override
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mTileCube.setRotY(mTileCube.getRotY()+1);
			mTileSphere.setRotY(mTileSphere.getRotY()+1);
		}		
	}

}
