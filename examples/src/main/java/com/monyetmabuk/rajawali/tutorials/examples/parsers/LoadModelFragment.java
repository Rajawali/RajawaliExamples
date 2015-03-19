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
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.parser.LoaderOBJ;
import rajawali.parser.ParsingException;

public class LoadModelFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new LoadModelRenderer(getActivity());
	}

	private final class LoadModelRenderer extends AExampleRenderer {
		private PointLight mLight;
		private Object3D mObjectGroup;
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

			LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(),
					mTextureManager, R.raw.rajawali_obj);
			try {
				objParser.parse();
				mObjectGroup = objParser.getParsedObject();
				getCurrentScene().addChild(mObjectGroup);

				mCameraAnim = new RotateOnAxisAnimation(Axis.Y, 360);
				mCameraAnim.setDurationMilliseconds(8000);
				mCameraAnim.setRepeatMode(RepeatMode.INFINITE);
				mCameraAnim.setTransformable3D(mObjectGroup);
			} catch (ParsingException e) {
				e.printStackTrace();
			}

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

	}

}
