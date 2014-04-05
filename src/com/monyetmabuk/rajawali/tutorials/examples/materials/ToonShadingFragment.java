package com.monyetmabuk.rajawali.tutorials.examples.materials;

import java.io.ObjectInputStream;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.math.vector.Vector3.Axis;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class ToonShadingFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new ToonShadingRenderer(getActivity());
	}

	private final class ToonShadingRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private Object3D mMonkey1, mMonkey2, mMonkey3;

		public ToonShadingRenderer(Context context) {
			super(context);
			getCurrentScene().setBackgroundColor(0xffeeeeee);
		}

		protected void initScene() {
			mLight = new DirectionalLight(0, 0, -1);
			mLight.setPower(1);
			
			getCurrentScene().addLight(mLight);
			getCurrentCamera().setPosition(0, 0, 12);

			try {
				ObjectInputStream ois = new ObjectInputStream(mContext
						.getResources().openRawResource(R.raw.monkey_ser));
				SerializedObject3D serializedMonkey = (SerializedObject3D) ois
						.readObject();
				ois.close();

				Material toonMat = new Material();
				toonMat.enableLighting(true);
				toonMat.setDiffuseMethod(new DiffuseMethod.Toon());

				mMonkey1 = new Object3D(serializedMonkey);
				mMonkey1.setMaterial(toonMat);
				mMonkey1.setPosition(-1.5f, 2, 0);
				getCurrentScene().addChild(mMonkey1);

				toonMat = new Material();
				toonMat.enableLighting(true);
				toonMat.setDiffuseMethod(new DiffuseMethod.Toon(0xffffffff, 0xff000000, 0xff666666,
						0xff000000));

				mMonkey2 = mMonkey1.clone();
				mMonkey2.setMaterial(toonMat);
				mMonkey2.setPosition(1.5f, 2, 0);
				getCurrentScene().addChild(mMonkey2);

				toonMat = new Material();
				toonMat.enableLighting(true);
				toonMat.setDiffuseMethod(new DiffuseMethod.Toon(0xff999900, 0xff003300, 0xffff0000,
						0xffa60000));
				mMonkey3 = mMonkey1.clone();
				mMonkey3.setMaterial(toonMat);
				mMonkey3.setPosition(0, -2, 0);
				getCurrentScene().addChild(mMonkey3);
			} catch (Exception e) {
				e.printStackTrace();
			}

			RotateOnAxisAnimation anim = new RotateOnAxisAnimation(Axis.Y, 360);
			anim.setDuration(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setTransformable3D(mMonkey1);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			anim = new RotateOnAxisAnimation(Axis.Y, -360);
			anim.setDuration(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setTransformable3D(mMonkey2);
			getCurrentScene().registerAnimation(anim);
			anim.play();

			anim = new RotateOnAxisAnimation(Axis.Y, -360);
			anim.setDuration(6000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setTransformable3D(mMonkey3);
			getCurrentScene().registerAnimation(anim);
			anim.play();
		}

	}

}
