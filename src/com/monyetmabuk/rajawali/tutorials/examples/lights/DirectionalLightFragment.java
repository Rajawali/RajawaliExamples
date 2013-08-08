package com.monyetmabuk.rajawali.tutorials.examples.lights;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.IAnimation3DListener;
import rajawali.lights.DirectionalLight;
import rajawali.materials.PhongMaterial;
import rajawali.math.MathUtil;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Sphere;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class DirectionalLightFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new DirectionalLightRenderer(getActivity());
	}

	private final class DirectionalLightRenderer extends AExampleRenderer {

		public DirectionalLightRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			super.initScene();

			final DirectionalLight directionalLight = new DirectionalLight();
			directionalLight.setPower(1.5f);

			getCurrentCamera().setPosition(0, 2, 6);
			getCurrentCamera().setLookAt(0, 0, 0);

			PhongMaterial sphereMaterial = new PhongMaterial();
			sphereMaterial.setShininess(180);
			sphereMaterial.setUseSingleColor(true);

			Sphere rootSphere = new Sphere(.2f, 12, 12);
			rootSphere.setMaterial(sphereMaterial);
			rootSphere.setRenderChildrenAsBatch(true);
			rootSphere.addLight(directionalLight);
			rootSphere.setVisible(false);
			addChild(rootSphere);

			// -- inner ring

			float radius = .8f;
			int count = 0;

			for (int i = 0; i < 360; i += 36) {
				double radians = MathUtil.degreesToRadians(i);
				int color = 0xfed14f;
				if (count % 3 == 0)
					color = 0x10a962;
				else if (count % 3 == 1)
					color = 0x4184fa;
				count++;

				BaseObject3D sphere = rootSphere.clone(false);
				sphere.setPosition((float) Math.sin(radians) * radius, 0,
						(float) Math.cos(radians) * radius);
				sphere.setMaterial(sphereMaterial);
				sphere.setColor(color);
				sphere.addLight(directionalLight);
				addChild(sphere);
			}

			// -- outer ring

			radius = 2.4f;
			count = 0;

			for (int i = 0; i < 360; i += 12) {
				double radians = MathUtil.degreesToRadians(i);
				int color = 0xfed14f;
				if (count % 3 == 0)
					color = 0x10a962;
				else if (count % 3 == 1)
					color = 0x4184fa;
				count++;

				BaseObject3D sphere = rootSphere.clone(false);
				sphere.setPosition((float) Math.sin(radians) * radius, 0,
						(float) Math.cos(radians) * radius);
				sphere.setMaterial(sphereMaterial);
				sphere.setColor(color);
				sphere.addLight(directionalLight);
				addChild(sphere);
			}

			final BaseObject3D target = new BaseObject3D();

			EllipticalOrbitAnimation3D anim = new EllipticalOrbitAnimation3D(
					new Vector3(0, .2f, 0), new Vector3(1, .2f, 1), 0, 359);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setDuration(6000);
			anim.setTransformable3D(target);
			anim.registerListener(new IAnimation3DListener() {
				public void onAnimationUpdate(Animation3D animation,
						double interpolatedTime) {
					Vector3 position = target.getPosition().clone();
					position.normalize();
					directionalLight.setDirection(position);
				}

				public void onAnimationStart(Animation3D animation) {
				}

				public void onAnimationRepeat(Animation3D animation) {
				}

				public void onAnimationEnd(Animation3D animation) {
				}
			});
			registerAnimation(anim);
			anim.play();
		}

	}

}
