package com.monyetmabuk.rajawali.tutorials.examples.effects;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Camera;
import rajawali.Object3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.parser.LoaderOBJ;
import rajawali.parser.ParsingException;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

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
			
			Camera camera = getCurrentCamera();
			camera.setPosition(0, 1, 4);
			camera.setFogNear(1);
			camera.setFogFar(15);
			camera.setFogColor(0x999999);

			setFogEnabled(true);
			getCurrentScene().setBackgroundColor(0x999999);

			LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(),
					mTextureManager, R.raw.road);
			try {
				objParser.parse();
				mRoad = objParser.getParsedObject();
				mRoad.setZ(-2);
				mRoad.setRotY(180);
				getCurrentScene().addChild(mRoad);
			} catch (ParsingException e) {
				e.printStackTrace();
			}
			mRoad = objParser.getParsedObject();
			mRoad.setZ(-2);
			mRoad.setRotY(180);
			getCurrentScene().addChild(mRoad);

			try {
				Material roadMaterial = new Material();
				roadMaterial.enableLighting(true);
				roadMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				roadMaterial.addTexture(new Texture("road", R.drawable.road));
				roadMaterial.setColorInfluence(0);
				mRoad.getChildByName("Road").setMaterial(roadMaterial);

				Material signMaterial = new Material();
				signMaterial.enableLighting(true);
				signMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				signMaterial.addTexture(new Texture("sign", R.drawable.sign));
				signMaterial.setColorInfluence(0);
				mRoad.getChildByName("Sign").setMaterial(signMaterial);

				Material warningMaterial = new Material();
				warningMaterial.enableLighting(true);
				warningMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				warningMaterial.addTexture(new Texture("warning", R.drawable.warning));
				warningMaterial.setColorInfluence(0);
				mRoad.getChildByName("Warning").setMaterial(warningMaterial);
			} catch (TextureException tme) {
				tme.printStackTrace();
			}

			TranslateAnimation3D camAnim = new TranslateAnimation3D(
					new Vector3(0, 1, -23));
			camAnim.setDuration(8000);
			camAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			camAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			camAnim.setTransformable3D(getCurrentCamera());
			getCurrentScene().registerAnimation(camAnim);
			camAnim.play();
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mLight.setZ(getCurrentCamera().getZ());
		}

	}

}
