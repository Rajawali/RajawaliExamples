package com.monyetmabuk.rajawali.tutorials.examples.animation;

import android.content.Context;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.SerializedObject3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.AnimationGroup;
import org.rajawali3d.animation.EllipticalOrbitAnimation3D;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.animation.ScaleAnimation3D;
import org.rajawali3d.animation.TranslateAnimation3D;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.math.vector.Vector3;

import java.io.ObjectInputStream;

public class AnimationFragment extends AExampleFragment {
	
	@Override
    public AExampleRenderer createRenderer() {
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
			animGroup.setRepeatMode(Animation.RepeatMode.INFINITE);
			animGroup.setRepeatCount(1);

			Animation3D anim = new ScaleAnimation3D(new Vector3(1.6f, .8f, 1));
			anim.setInterpolator(new LinearInterpolator());
			anim.setDurationMilliseconds(1000);
			anim.setRepeatCount(2);
			anim.setRepeatMode(Animation.RepeatMode.REVERSE);
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
					3, 0), Vector3.getAxisVector(Vector3.Axis.Z), 0, 360,
					EllipticalOrbitAnimation3D.OrbitDirection.CLOCKWISE);
			anim.setInterpolator(new LinearInterpolator());
			anim.setDurationMilliseconds(2000);
			anim.setRepeatCount(3);
			anim.setRepeatMode(Animation.RepeatMode.REVERSE);
			anim.setTransformable3D(mMonkey);
			animGroup.addAnimation(anim);
			
			getCurrentScene().registerAnimation(animGroup);
			animGroup.play();
		}

	}
	
}
