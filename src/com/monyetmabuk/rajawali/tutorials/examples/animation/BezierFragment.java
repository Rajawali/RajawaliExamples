package com.monyetmabuk.rajawali.tutorials.examples.animation;

import rajawali.Object3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.Animation3D;
import rajawali.animation.SplineTranslateAnimation3D;
import rajawali.curves.CompoundCurve3D;
import rajawali.curves.CubicBezierCurve3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.methods.SpecularMethod;
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
			getCurrentScene().addLight(mLight);
			getCurrentCamera().setPosition(0, 0, 20);

			Object3D redSphere = new Sphere(1, 16, 16);
			redSphere.setPosition(0, -4, 0);
			redSphere.setColor(0xffff0000);

			Material phong = new Material();
			phong.enableLighting(true);
			phong.setDiffuseMethod(new DiffuseMethod.Lambert());
			phong.setSpecularMethod(new SpecularMethod.Phong());
			redSphere.setMaterial(phong);
			getCurrentScene().addChild(redSphere);

			Object3D yellowSphere = new Sphere(.6f, 16, 16);
			yellowSphere.setPosition(2, 4, 0);
			yellowSphere.setColor(0xffffff00);
			Material diffuse = new Material();
			diffuse.enableLighting(true);
			diffuse.setDiffuseMethod(new DiffuseMethod.Lambert());
			yellowSphere.setMaterial(diffuse);
			getCurrentScene().addChild(yellowSphere);

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

			Animation3D redAnim = new SplineTranslateAnimation3D(redBezierPath);
			redAnim.setDuration(2000);
			redAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			redAnim.setTransformable3D(redSphere);
			getCurrentScene().registerAnimation(redAnim);
			redAnim.play();

			Animation3D yellowAnim = new SplineTranslateAnimation3D(yellowBezierPath);
			yellowAnim.setDuration(3800);
			yellowAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			yellowAnim.setTransformable3D(yellowSphere);
			getCurrentScene().registerAnimation(yellowAnim);
			yellowAnim.play();
		}
	}
}
