package com.monyetmabuk.rajawali.tutorials.examples.general;

import java.util.Stack;

import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.materials.SimpleMaterial;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Line3D;
import android.content.Context;
import android.graphics.Color;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class ColoredLinesFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new ColoredLinesRenderer(getActivity());
	}

	private final class ColoredLinesRenderer extends AExampleRenderer {

		public ColoredLinesRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			super.initScene();
			getCurrentCamera().setPosition(0, 0, 10);
			getCurrentCamera().setLookAt(0, 0, 0);

			Stack<Vector3> points = new Stack<Vector3>();
			int[] colors = new int[2000];
			int colorCount = 0;
			for (int i = -1000; i < 1000; i++) {
				double j = i * .5;
				Vector3 v = new Vector3();
				v.x = Math.cos(j * .4);
				v.y = Math.sin(j * .3);
				v.z = j * .01;
				points.add(v);
				colors[colorCount++] = Color.argb(255,
						(int) (190.f * Math.sin(j)),
						(int) (190.f * Math.cos(j * .3f)),
						(int) (190.f * Math.sin(j * 2) * Math.cos(j)));
			}

			Line3D line = new Line3D(points, 1, colors);
			SimpleMaterial material = new SimpleMaterial();
			material.setUseVertexColors(true);
			line.setMaterial(material);
			getCurrentScene().addChild(line);

			RotateAnimation3D lineAnim = new RotateAnimation3D(Axis.Y, 359);
			lineAnim.setDuration(10000);
			lineAnim.setRepeatMode(RepeatMode.INFINITE);
			lineAnim.setTransformable3D(line);
			registerAnimation(lineAnim);
			lineAnim.play();

			TranslateAnimation3D camAnim = new TranslateAnimation3D(
					new Vector3(0, 0, 10), new Vector3(0, 0, -10));
			camAnim.setDuration(12000);
			camAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			camAnim.setTransformable3D(getCurrentCamera());
			registerAnimation(camAnim);
			camAnim.play();
		}

	}

}
