package com.monyetmabuk.rajawali.tutorials.examples.loaders;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.math.vector.Vector3.Axis;
import rajawali.loader.LoaderAWD;

public class AwdFragment extends AExampleFragment {

	@Override
    public AExampleRenderer createRenderer() {
		return new AwdRenderer(getActivity());
	}

	private final class AwdRenderer extends AExampleRenderer {

		public AwdRenderer(Context context) {
			super(context);
		}

		@Override
		protected void initScene() {

			try {
				final LoaderAWD parser = new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.awd_arrows);
				parser.parse();

				final Object3D obj = parser.getParsedObject();
				obj.setScale(0.25f);
				getCurrentScene().addChild(obj);

				final Animation3D anim = new RotateOnAxisAnimation(Axis.Y, -360);
				anim.setDurationDelta(4d);
				anim.setRepeatMode(RepeatMode.INFINITE);
				anim.setTransformable3D(obj);
				anim.play();
				getCurrentScene().registerAnimation(anim);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
