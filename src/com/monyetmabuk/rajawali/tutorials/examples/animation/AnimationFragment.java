package com.monyetmabuk.rajawali.tutorials.examples.animation;

import java.io.ObjectInputStream;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.AnimationGroup;
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

		protected void initScene() {
			PointLight mLight = new PointLight();
			mLight.setPosition(-2, 1, -4);
			mLight.setPower(1.5f);
			getCurrentCamera().setPosition(0, 0, -14);
			getCurrentCamera().setLookAt(0, 0, 0);

			ObjectInputStream ois;
			Object3D mMonkey = null;
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
				throw new IllegalStateException(e);
			}

			Material material = new Material();
			material.enableLighting(true);
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			mMonkey.setMaterial(material);
			mMonkey.setColor(0xff00ff00);

			final AnimationGroup animGroup = new AnimationGroup();
			animGroup.setRepeatMode(RepeatMode.INFINITE);
			animGroup.setRepeatCount(1);

			Animation3D anim = new ScaleAnimation3D(new Vector3(1.6f, .8f, 1));
			anim.setInterpolator(new LinearInterpolator());
			anim.setDurationMilliseconds(1000);
			anim.setRepeatCount(2);
			anim.setRepeatMode(RepeatMode.REVERSE);
			anim.setTransformable3D(mMonkey);
			animGroup.addAnimation(anim);

			Vector3 axis = new Vector3(10, 5, 2);
			axis.normalize();

			anim = new RotateOnAxisAnimation(axis, 0, 360);
			anim.setDurationMilliseconds(2000);
			anim.setTransformable3D(mMonkey);
			animGroup.addAnimation(anim);

			anim = new TranslateAnimation3D(new Vector3(-2, -2, 0));
			anim.setDurationMilliseconds(500);
			anim.setTransformable3D(mMonkey);
			animGroup.addAnimation(anim);

			anim = new TranslateAnimation3D(new Vector3(-2, -2, 0),
					new Vector3(2, 2, 0));
			anim.setDurationMilliseconds(2000);
			anim.setTransformable3D(mMonkey);
			anim.setInterpolator(new BounceInterpolator());
			anim.setRepeatCount(3);
			animGroup.addAnimation(anim);

			anim = new EllipticalOrbitAnimation3D(new Vector3(), new Vector3(0,
					3, 0), Vector3.getAxisVector(Axis.Z), 0, 360,
					OrbitDirection.CLOCKWISE);
			anim.setInterpolator(new LinearInterpolator());
			anim.setDurationMilliseconds(2000);
			anim.setRepeatCount(3);
			anim.setRepeatMode(RepeatMode.REVERSE);
			anim.setTransformable3D(mMonkey);
			animGroup.addAnimation(anim);
			
			getCurrentScene().registerAnimation(animGroup);
			animGroup.play();
		}

	}
	
}
