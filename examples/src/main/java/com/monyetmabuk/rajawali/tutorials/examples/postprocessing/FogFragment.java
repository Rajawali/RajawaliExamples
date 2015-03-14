package com.monyetmabuk.rajawali.tutorials.examples.postprocessing;

import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.plugins.FogMaterialPlugin.FogParams;
import rajawali.materials.plugins.FogMaterialPlugin.FogType;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.parser.LoaderOBJ;

public class FogFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new FogRenderer(getActivity());
	}

	private final class FogRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private Object3D mRoad;

		public FogRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(0, -1, -1);
			mLight.setPower(.5f);

			getCurrentScene().addLight(mLight);

			int fogColor = 0x999999;
			
			getCurrentScene().setBackgroundColor(fogColor);
			getCurrentScene().setFog(new FogParams(FogType.LINEAR, fogColor, 1, 15));

			LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(),
					mTextureManager, R.raw.road);
			try {
				objParser.parse();
				mRoad = objParser.getParsedObject();
				mRoad.setZ(5);
				mRoad.setRotY(180);
				getCurrentScene().addChild(mRoad);

				Material roadMaterial = new Material();
				roadMaterial.enableLighting(true);
				roadMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				roadMaterial.addTexture(new Texture("roadTex", R.drawable.road));
				roadMaterial.setColorInfluence(0);
				mRoad.getChildByName("Road").setMaterial(roadMaterial);

				Material signMaterial = new Material();
				signMaterial.enableLighting(true);
				signMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				signMaterial.addTexture(new Texture("rajawaliSign", R.drawable.sign));
				signMaterial.setColorInfluence(0);
				mRoad.getChildByName("WarningSign").setMaterial(signMaterial);

				Material warningMaterial = new Material();
				warningMaterial.enableLighting(true);
				warningMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				warningMaterial.addTexture(new Texture("warning", R.drawable.warning));
				warningMaterial.setColorInfluence(0);
				mRoad.getChildByName("Warning").setMaterial(warningMaterial);
			} catch (Exception e) {
				e.printStackTrace();
			}

			TranslateAnimation3D camAnim = new TranslateAnimation3D(
					new Vector3(0, 2, 0),
					new Vector3(0, 2, -23));
			camAnim.setDurationMilliseconds(8000);
			camAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			camAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			camAnim.setTransformable3D(getCurrentCamera());
			getCurrentScene().registerAnimation(camAnim);
			camAnim.play();
		}
	}
}
