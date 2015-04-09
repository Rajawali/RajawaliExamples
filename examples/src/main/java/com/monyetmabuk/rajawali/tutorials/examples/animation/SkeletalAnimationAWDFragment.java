package com.monyetmabuk.rajawali.tutorials.examples.animation;

import org.rajawali3d.animation.mesh.SkeletalAnimationObject3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderAWD;
import org.rajawali3d.loader.ParsingException;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class SkeletalAnimationAWDFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new SkeletalAnimationAWDRenderer(getActivity());
	}

	private final class SkeletalAnimationAWDRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private SkeletalAnimationObject3D mObject;

		public SkeletalAnimationAWDRenderer(Context context) {
			super(context);
		}

        @Override
		protected void initScene() {
			mLight = new DirectionalLight(0, -0.2f, -1.0f); // set the direction
			mLight.setColor(1.0f, 1.0f, 1.0f);
			mLight.setPower(2);

			getCurrentScene().addLight(mLight);
			getCurrentCamera().setY(1);
			getCurrentCamera().setZ(6);

			try {
				final LoaderAWD parser =
					new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.boblampclean_anim_awd);

				parser.parse();

				mObject =
					(SkeletalAnimationObject3D)parser.getParsedObject();

				mObject.setAnimationSequence(0);
				mObject.setScale(.04f);
				mObject.play();

				getCurrentScene().addChild(mObject);
			} catch (ParsingException e) {
				e.printStackTrace();
			}
		}

	}
}
