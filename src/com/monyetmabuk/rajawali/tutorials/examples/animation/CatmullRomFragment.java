package com.monyetmabuk.rajawali.tutorials.examples.animation;

import java.util.Stack;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.EllipticalOrbitAnimation3D.OrbitDirection;
import rajawali.animation.TranslateAnimation3D;
import rajawali.curves.CatmullRomCurve3D;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Vector3;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.ObjParser;
import rajawali.primitives.Line3D;
import rajawali.primitives.Sphere;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class CatmullRomFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new CatmullRomRenderer(getActivity());
	}

	private final class CatmullRomRenderer extends AExampleRenderer {

		public CatmullRomRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			super.initScene();
			ALight light1 = new DirectionalLight(0, 0, -1);
			light1.setPower(1);
			getCurrentCamera().setPosition(0, 0, 10);
			getCurrentCamera().setLookAt(0, 0, 0);

			SimpleMaterial material = new SimpleMaterial();
			material.setUseSingleColor(true);

			// -- create a catmull-rom path. The first and the last point are control points.
			CatmullRomCurve3D path = new CatmullRomCurve3D();
			float r = 12;
			float rh = r * .5f;

			for (int i = 0; i < 16; i++) {
				path.addPoint(new Vector3(-rh + (Math.random() * r), -rh
						+ (Math.random() * r), -rh + (Math.random() * r)));
			}

			try {
				ObjParser parser = new ObjParser(mContext.getResources(),
						mTextureManager, R.raw.arrow);
				parser.parse();
				BaseObject3D arrow = parser.getParsedObject();
				arrow.setMaterial(material);
				arrow.setScale(.2f);
				arrow.setColor(0xffffff00);
				arrow.addLight(light1);
				addChild(arrow);

				TranslateAnimation3D anim = new TranslateAnimation3D(path);
				anim.setDuration(12000);
				anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
				// -- orient to path
				anim.setOrientToPath(true);
				anim.setTransformable3D(arrow);
				registerAnimation(anim);
				anim.play();
			} catch (ParsingException e) {
				e.printStackTrace();
			}

			int numPoints = path.getNumPoints();

			for (int i = 0; i < numPoints; i++) {
				Sphere s = new Sphere(.2f, 6, 6);
				s.addLight(light1);
				s.setMaterial(material);
				s.setPosition(path.getPoint(i));

				if (i == 0)
					s.setColor(0xffff0000);
				else if (i == numPoints - 1)
					s.setColor(0xff0066ff);
				else
					s.setColor(0xff999999);

				addChild(s);
			}

			// -- visualize the line
			Stack<Vector3> linePoints = new Stack<Vector3>();
			for (int i = 0; i < 100; i++) {
				Vector3 point = new Vector3();
				path.calculatePoint(point, i / 100f);
				linePoints.add(point);
			}
			Line3D line = new Line3D(linePoints, 1, 0xffffffff);
			line.setMaterial(material);
			addChild(line);

			EllipticalOrbitAnimation3D camAnim = new EllipticalOrbitAnimation3D(
					new Vector3(), new Vector3(26, 0, 0), 0, 360,
					OrbitDirection.CLOCKWISE);
			camAnim.setDuration(10000);
			camAnim.setRepeatMode(RepeatMode.INFINITE);
			camAnim.setTransformable3D(getCurrentCamera());
			registerAnimation(camAnim);
			camAnim.play();
		}

	}

}
