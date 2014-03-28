package com.monyetmabuk.rajawali.tutorials.examples.parsers;

import rajawali.Object3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.math.vector.Vector3.Axis;
import rajawali.parser.LoaderAWD;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class AwdFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new AwdRenderer(getActivity());
	}

	private final class AwdRenderer extends AExampleRenderer {

		public AwdRenderer(Context context) {
			super(context);
		}

		@Override
		protected void initScene() {

			try {
				final LoaderAWD parser = new LoaderAWD(mContext.getResources(),
						mTextureManager, R.raw.awd_arrows);
				parser.parse();

				final Object3D obj = parser.getParsedObject();
				obj.setScale(0.25f);
				getCurrentScene().addChild(obj);

				final RotateAnimation3D anim = new RotateAnimation3D(Axis.Y,
						-360);
				anim.setDuration(4d);
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
