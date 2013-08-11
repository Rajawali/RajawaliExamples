package com.monyetmabuk.rajawali.tutorials.examples.parsers;

import rajawali.Object3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.math.vector.Vector3.Axis;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.fbx.FBXParser;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class FBXFragment extends AExampleFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.BOTTOM);

		TextView label = new TextView(getActivity());
		label.setText(R.string.fbx_fragment_button_model_by);
		label.setTextSize(20);
		label.setGravity(Gravity.CENTER);
		label.setHeight(100);
		ll.addView(label);

		mLayout.addView(ll);
		
		return mLayout;
	}

	@Override
	protected AExampleRenderer createRenderer() {
		return new FBXRenderer(getActivity());
	}

	private final class FBXRenderer extends AExampleRenderer {
		private Animation3D mAnim;

		public FBXRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mAnim = new RotateAnimation3D(Axis.Y, 360);
			mAnim.setDuration(16000);
			mAnim.setRepeatMode(RepeatMode.INFINITE);
			registerAnimation(mAnim);

			try {
				// -- Model by Sampo Rask
				// (http://www.blendswap.com/blends/characters/low-poly-rocks-character/)
				FBXParser parser = new FBXParser(this,
						R.raw.lowpolyrocks_character_blendswap);
				parser.parse();
				Object3D o = parser.getParsedObject();
				o.setY(-.5f);
				addChild(o);

				mAnim.setTransformable3D(o);
				mAnim.play();
			} catch (ParsingException e) {
				e.printStackTrace();
			}
		}

	}

}
