package com.monyetmabuk.rajawali.tutorials.examples.parsers;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.lights.PointLight;
import rajawali.loader.ALoader;
import rajawali.loader.LoaderOBJ;
import rajawali.loader.async.IAsyncLoaderCallback;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Cube;
import rajawali.util.RajLog;

public class AsyncLoadModelFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new LoadModelRenderer(getActivity());
	}

	private final class LoadModelRenderer extends AExampleRenderer implements IAsyncLoaderCallback {
		private PointLight mLight;
        private Cube mBaseObject;
		private Animation3D mCameraAnim, mLightAnim;

		public LoadModelRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new PointLight();
			mLight.setPosition(0, 0, 4);
			mLight.setPower(3);
			
			getCurrentScene().addLight(mLight);
			getCurrentCamera().setZ(16);

            getCurrentScene().setBackgroundColor(0.7f, 0.7f, 0.7f, 1.0f);

            // Add the base object
            mBaseObject = new Cube(2.0f);
            mBaseObject.setPosition(-2.0, 3.0, 0.0);
            try {
                Material material = new Material();
                material.addTexture(new Texture("camdenTown", R.drawable.camden_town_alpha));
                material.setColorInfluence(0);
                mBaseObject.setMaterial(material);
                getCurrentScene().addChild(mBaseObject);
            } catch (ATexture.TextureException e) {
                e.printStackTrace();
            }

            //Begin loading
            final LoaderOBJ loaderOBJ = new LoaderOBJ(mContext.getResources(),
                mTextureManager, R.raw.multiobjects_obj);
            getCurrentScene().loadModel(loaderOBJ, this, R.raw.multiobjects_obj);

			mCameraAnim = new RotateOnAxisAnimation(Axis.Y, 360);
			mCameraAnim.setDurationMilliseconds(8000);
			mCameraAnim.setRepeatMode(RepeatMode.INFINITE);
			mCameraAnim.setTransformable3D(getCurrentCamera());

			mLightAnim = new EllipticalOrbitAnimation3D(new Vector3(),
					new Vector3(0, 10, 0), Vector3.getAxisVector(Axis.Z), 0,
					360, OrbitDirection.CLOCKWISE);

			mLightAnim.setDurationMilliseconds(3000);
			mLightAnim.setRepeatMode(RepeatMode.INFINITE);
			mLightAnim.setTransformable3D(mLight);

			getCurrentScene().registerAnimation(mCameraAnim);
			getCurrentScene().registerAnimation(mLightAnim);

			mCameraAnim.play();
			mLightAnim.play();
		}

        @Override
        public void onModelLoadComplete(ALoader aLoader) {
            RajLog.d(this, "Model load complete: " + aLoader);
            final LoaderOBJ obj = (LoaderOBJ) aLoader;
            final Object3D parsedObject = obj.getParsedObject();
            parsedObject.setPosition(Vector3.ZERO);
            getCurrentScene().addChild(parsedObject);
        }

        @Override
        public void onModelLoadFailed(ALoader aLoader) {
            RajLog.e(this, "Model load failed: " + aLoader);
        }
    }

}
