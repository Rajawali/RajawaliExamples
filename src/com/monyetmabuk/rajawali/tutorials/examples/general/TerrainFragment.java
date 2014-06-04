package com.monyetmabuk.rajawali.tutorials.examples.general;

import rajawali.ChaseCamera;
import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.SplineTranslateAnimation3D;
import rajawali.curves.CatmullRomCurve3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.plugins.FogMaterialPlugin.FogParams;
import rajawali.materials.plugins.FogMaterialPlugin.FogType;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.NormalMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.math.MathUtil;
import rajawali.math.vector.Vector3;
import rajawali.terrain.SquareTerrain;
import rajawali.terrain.TerrainGenerator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class TerrainFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new TerrainRenderer(getActivity());
	}
	
	private final class TerrainRenderer extends AExampleRenderer {
		
		private SquareTerrain mTerrain;
		private double mLastY = 0;

		public TerrainRenderer(Context context) {
			super(context);
		}
		
		public void initScene() {
			getCurrentScene().setBackgroundColor(0x999999);

			// 
			// -- Use a chase camera that follows and invisible ('empty') object
			//    and add fog for a nice effect.
			//

			ChaseCamera chaseCamera = new ChaseCamera(new Vector3(0, 4, -8), .9f);
			chaseCamera.setFarPlane(1000);
			getCurrentScene().replaceAndSwitchCamera(chaseCamera, 0);
			
			getCurrentScene().setFog(new FogParams(FogType.LINEAR, 0x999999, 50, 100));
			
			//
			// -- Load a bitmap that represents the terrain. Its color values will
			//    be used to generate heights.
			//

			Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.terrain);

			try {
				SquareTerrain.Parameters terrainParams = SquareTerrain.createParameters(bmp);
				// -- set terrain scale
				terrainParams.setScale(4f, 54f, 4f);
				// -- the number of plane subdivisions
				terrainParams.setDivisions(128);
				// -- the number of times the textures should be repeated
				terrainParams.setTextureMult(4);
				//
				// -- Terrain colors can be set by manually specifying base, middle and
				//    top colors.   
				//
				// --  terrainParams.setBasecolor(Color.argb(255, 0, 0, 0));
				//     terrainParams.setMiddleColor(Color.argb(255, 200, 200, 200));
				//     terrainParams.setUpColor(Color.argb(255, 0, 30, 0));
				//
				// -- However, for this example we'll use a bitmap
				//
				terrainParams.setColorMapBitmap(bmp);
				//
				// -- create the terrain
				//
				mTerrain = TerrainGenerator.createSquareTerrainFromBitmap(terrainParams);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//
			// -- The bitmap won't be used anymore, so get rid of it.
			//
			bmp.recycle();

			DirectionalLight light = new DirectionalLight(0.2f, -1f, 0f);
			light.setPower(1f);
			getCurrentScene().addLight(light);

			//
			// -- A normal map material will give the terrain a bit more detail.
			//
			Material material = new Material();
			material.enableLighting(true);
			material.useVertexColors(true);
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			try {
				Texture groundTexture = new Texture("ground", R.drawable.ground);
				groundTexture.setInfluence(.5f);
				material.addTexture(groundTexture);
				material.addTexture(new NormalMapTexture("groundNormalMap", R.drawable.groundnor));
				material.setColorInfluence(0);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			//
			// -- Blend the texture with the vertex colors
			//
			material.setColorInfluence(.5f);

			mTerrain.setMaterial(material);

			getCurrentScene().addChild(mTerrain);

			//
			// -- The empty object that will move along a curve and that
			//    will be follow by the camera
			//
			Object3D empty = new Object3D();
			empty.setVisible(false);

			//
			// -- Tell the camera to chase the empty.
			//
			chaseCamera.setObjectToChase(empty);

			//
			// -- Create a camera path based on the terrain height
			//
			CatmullRomCurve3D cameraPath = createCameraPath();

			SplineTranslateAnimation3D anim = new SplineTranslateAnimation3D(cameraPath);
			anim.setTransformable3D(empty);
			anim.setDurationMilliseconds(60000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setOrientToPath(true);
			getCurrentScene().registerAnimation(anim);
			anim.play();
		}
		
		private CatmullRomCurve3D createCameraPath() {
			CatmullRomCurve3D path = new CatmullRomCurve3D();

			float radius = 200;
			float degreeStep = 15;
			float distanceFromGround = 20;

			for (int i = 0; i < 360; i += degreeStep) {
				double radians = MathUtil.degreesToRadians(i);
				double x = Math.cos(radians) * Math.sin(radians)
						* radius;
				double z = Math.sin(radians) * radius;
				double y = mTerrain.getAltitude(x, z) + distanceFromGround;

				if (i > 0)
					y = (y + mLastY) * .5f;
				mLastY = y;

				path.addPoint(new Vector3(x, y, z));
			}
			path.isClosedCurve(true);
			return path;
		}
		
	}

}
