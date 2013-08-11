package com.monyetmabuk.rajawali.tutorials.examples.animation;

import rajawali.Object3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.curves.CompoundCurve3D;
import rajawali.curves.CubicBezierCurve3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.PhongMaterial;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Sphere;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class BezierFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new BezierRenderer(getActivity());
	}

	private final class BezierRenderer extends AExampleRenderer {
		private DirectionalLight mLight;

		public BezierRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(0, 1, -1);
			mLight.setPower(1);
			getCurrentCamera().setPosition(0, 0, 20);

			Object3D redSphere = new Sphere(1, 16, 16);
			redSphere.addLight(mLight);
			redSphere.setPosition(0, -4, 0);
			redSphere.setColor(0xffff0000);

			PhongMaterial phong = new PhongMaterial();
			phong.setUseSingleColor(true);
			redSphere.setMaterial(phong);
			addChild(redSphere);

			Object3D yellowSphere = new Sphere(.6f, 16, 16);
			yellowSphere.addLight(mLight);
			yellowSphere.setPosition(2, 4, 0);
			yellowSphere.setColor(0xffffff00);
			DiffuseMaterial diffuse = new DiffuseMaterial();
			diffuse.setUseSingleColor(true);
			yellowSphere.setMaterial(diffuse);
			addChild(yellowSphere);

			CompoundCurve3D redBezierPath = new CompoundCurve3D();
			redBezierPath.addCurve(new CubicBezierCurve3D(
					new Vector3(0, -4, 0), new Vector3(-2, -4, .2f),
					new Vector3(4, 4, 4), new Vector3(-2, 4, 4.5f)));
			redBezierPath.addCurve(new CubicBezierCurve3D(new Vector3(-2, 4,
					4.5f), new Vector3(2, -2, -2), new Vector3(4, 4, 4),
					new Vector3(-2, 4, 4.5f)));

			CompoundCurve3D yellowBezierPath = new CompoundCurve3D();
			yellowBezierPath.addCurve(new CubicBezierCurve3D(new Vector3(2, 4,
					0), new Vector3(-8, 3, 4), new Vector3(-4, 0, -2),
					new Vector3(4, -3, 30)));
			yellowBezierPath.addCurve(new CubicBezierCurve3D(new Vector3(4, -3,
					30), new Vector3(6, 1, 2), new Vector3(4, 2, 3),
					new Vector3(-3, -3, -4.5f)));

			Animation3D redAnim = new TranslateAnimation3D(redBezierPath);
			redAnim.setDuration(2000);
			redAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			redAnim.setTransformable3D(redSphere);
			registerAnimation(redAnim);
			redAnim.play();

			Animation3D yellowAnim = new TranslateAnimation3D(yellowBezierPath);
			yellowAnim.setDuration(3800);
			yellowAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			yellowAnim.setTransformable3D(yellowSphere);
			registerAnimation(yellowAnim);
			yellowAnim.play();
		}

	}

}
