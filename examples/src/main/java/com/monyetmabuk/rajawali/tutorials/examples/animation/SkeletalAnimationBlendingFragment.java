package com.monyetmabuk.rajawali.tutorials.examples.animation;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.animation.mesh.SkeletalAnimationObject3D;
import org.rajawali3d.animation.mesh.SkeletalAnimationSequence;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.md5.LoaderMD5Anim;
import org.rajawali3d.loader.md5.LoaderMD5Mesh;

public class SkeletalAnimationBlendingFragment extends AExampleFragment implements
		OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.BOTTOM);

		Button button1 = new Button(getActivity());
		button1.setText(R.string.skeletal_animation_blending_button_idle);
		button1.setOnClickListener(this);
		button1.setId(0);
		ll.addView(button1);

		Button button2 = new Button(getActivity());
		button2.setText(R.string.skeletal_animation_blending_button_walk);
		button2.setOnClickListener(this);
		button2.setId(1);
		ll.addView(button2);

		Button button3 = new Button(getActivity());
		button3.setText(R.string.skeletal_animation_blending_button_arm_stretch);
		button3.setOnClickListener(this);
		button3.setId(2);
		ll.addView(button3);

		Button button4 = new Button(getActivity());
		button4.setText(R.string.skeletal_animation_blending_button_bend);
		button4.setOnClickListener(this);
		button4.setId(3);
		ll.addView(button4);

		mLayout.addView(ll);

		ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.TOP);

		TextView creatorText = new TextView(getActivity());
		creatorText
				.setText(R.string.skeletal_animation_blending_button_arm_stretch);
		ll.addView(creatorText);

		mLayout.addView(ll);

		return mLayout;
	}

	@Override
    public AExampleRenderer createRenderer() {
		return new SkeletalAnimationBlendingRenderer(getActivity());
	}

	@Override
	public void onClick(View v) {
		((SkeletalAnimationBlendingRenderer) mRenderer).transitionAnimation(v.getId());
	}

	private final class SkeletalAnimationBlendingRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private SkeletalAnimationObject3D mObject;
		private SkeletalAnimationSequence mSequenceWalk;
		private SkeletalAnimationSequence mSequenceIdle;
		private SkeletalAnimationSequence mSequenceArmStretch;
		private SkeletalAnimationSequence mSequenceBend;

		public SkeletalAnimationBlendingRenderer(Context context) {
			super(context);
		}

        @Override
		protected void initScene() {
			mLight = new DirectionalLight(0, -0.2f, -1.0f); // set the direction
			mLight.setColor(1.0f, 1.0f, .8f);
			mLight.setPower(1);

			getCurrentScene().addLight(mLight);
			getCurrentCamera().setZ(8);

			try {
				LoaderMD5Mesh meshParser = new LoaderMD5Mesh(this,
						R.raw.ingrid_mesh);
				meshParser.parse();

				LoaderMD5Anim animParser = new LoaderMD5Anim("idle", this,
						R.raw.ingrid_idle);
				animParser.parse();

				mSequenceIdle = (SkeletalAnimationSequence) animParser
						.getParsedAnimationSequence();

				animParser = new LoaderMD5Anim("walk", this, R.raw.ingrid_walk);
				animParser.parse();

				mSequenceWalk = (SkeletalAnimationSequence) animParser
						.getParsedAnimationSequence();

				animParser = new LoaderMD5Anim("armstretch", this,
						R.raw.ingrid_arm_stretch);
				animParser.parse();

				mSequenceArmStretch = (SkeletalAnimationSequence) animParser
						.getParsedAnimationSequence();

				animParser = new LoaderMD5Anim("bend", this, R.raw.ingrid_bend);
				animParser.parse();

				mSequenceBend = (SkeletalAnimationSequence) animParser
						.getParsedAnimationSequence();

				mObject = (SkeletalAnimationObject3D) meshParser
						.getParsedAnimationObject();
				mObject.setAnimationSequence(mSequenceIdle);
				mObject.setFps(24);
				mObject.setScale(.8f);
				mObject.play();

				getCurrentScene().addChild(mObject);
			} catch (ParsingException e) {
				e.printStackTrace();
			}
		}

		public void transitionAnimation(int id) {
			switch (id) {
			case 0:
				mObject.transitionToAnimationSequence(mSequenceIdle, 1000);
				break;
			case 1:
				mObject.transitionToAnimationSequence(mSequenceWalk, 1000);
				break;
			case 2:
				mObject.transitionToAnimationSequence(mSequenceArmStretch, 1000);
				break;
			case 3:
				mObject.transitionToAnimationSequence(mSequenceBend, 1000);
				break;
			}
		}

	}

}
