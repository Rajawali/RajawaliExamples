package com.monyetmabuk.rajawali.tutorials.examples.animation;

import java.io.ObjectInputStream;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.AnimationGroup;
import rajawali.animation.AnimationQueue;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.animation.ScaleAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import android.content.Context;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class AnimationFragment extends AExampleFragment {
	
	@Override
	protected AExampleRenderer createRenderer() {
		return new AnimationRenderer(getActivity());
	}

	public class AnimationRenderer extends AExampleRenderer {
		
		public AnimationRenderer(Context context) {
			super(context);
		}

		private PointLight mLight;
		private Object3D mMonkey;

		protected void initScene() {
			mLight = new PointLight();
			mLight.setPosition(-2, 1, -4);
			mLight.setPower(1.5f);
			getCurrentCamera().setPosition(0, 0, -14);
			getCurrentCamera().setLookAt(0, 0, 0);

			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(mContext.getResources()
						.openRawResource(R.raw.monkey_ser));
				SerializedObject3D serializedMonkey = (SerializedObject3D) ois
						.readObject();
				ois.close();
				mMonkey = new Object3D(serializedMonkey);
				getCurrentScene().addLight(mLight);
				getCurrentScene().addChild(mMonkey);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Material material = new Material();
			material.enableLighting(true);
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			mMonkey.setMaterial(material);
			mMonkey.setColor(0xff00ff00);

			final AnimationQueue mAnimGroup = new AnimationQueue();

			Animation3D anim = new ScaleAnimation3D(new Vector3(1.6f, .8f, 1));
			anim.setInterpolator(new LinearInterpolator());
			anim.setDuration(1000);
			anim.setRepeatCount(2);
			anim.setRepeatMode(RepeatMode.REVERSE);
			anim.setTransformable3D(mMonkey);
			mAnimGroup.addAnimation(anim);

			Vector3 axis = new Vector3(10, 5, 2);
			axis.normalize();

			anim = new RotateOnAxisAnimation(axis, 0, 360);
			anim.setDuration(2000);
			anim.setTransformable3D(mMonkey);
			mAnimGroup.addAnimation(anim);

			anim = new TranslateAnimation3D(new Vector3(-2, -2, 0));
			anim.setDuration(500);
			anim.setTransformable3D(mMonkey);
			mAnimGroup.addAnimation(anim);

			anim = new TranslateAnimation3D(new Vector3(-2, -2, 0),
					new Vector3(2, 2, 0));
			anim.setDuration(2000);
			anim.setTransformable3D(mMonkey);
			anim.setInterpolator(new BounceInterpolator());
			anim.setRepeatCount(3);
			mAnimGroup.addAnimation(anim);

			anim = new EllipticalOrbitAnimation3D(new Vector3(), new Vector3(0,
					3, 0), Vector3.getAxisVector(Axis.Z), 0, 360,
					OrbitDirection.CLOCKWISE);
			anim.setInterpolator(new LinearInterpolator());
			anim.setDuration(2000);
			anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			anim.setTransformable3D(mMonkey);
			mAnimGroup.addAnimation(anim);
			
			getCurrentScene().registerAnimation(mAnimGroup);
			mAnimGroup.play();
		}

	}
	
}
