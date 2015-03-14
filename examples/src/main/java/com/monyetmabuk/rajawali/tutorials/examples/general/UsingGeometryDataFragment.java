package com.monyetmabuk.rajawali.tutorials.examples.general;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import java.io.ObjectInputStream;
import java.nio.FloatBuffer;
import java.util.zip.GZIPInputStream;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.RotateOnAxisAnimation;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.methods.SpecularMethod;
import rajawali.math.MathUtil;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Sphere;

public class UsingGeometryDataFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new UsingGeometryDataRenderer(getActivity());
	}

	private final class UsingGeometryDataRenderer extends AExampleRenderer {
		private Object3D mRootSpike;

		public UsingGeometryDataRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			DirectionalLight light = new DirectionalLight(0, -.6f, -.4f);
			light.setColor(1, 1, 1);
			
			getCurrentScene().addLight(light);
			getCurrentScene().setBackgroundColor(0xffeeeeee);

			getCurrentCamera().setZ(16);

			Object3D sphere = new Sphere(1, 16, 8);

			Material spikeMaterial = new Material();
			spikeMaterial.enableLighting(true);
			spikeMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			spikeMaterial.setSpecularMethod(new SpecularMethod.Phong());

			try {
				// -- open gzipped serialized file
				GZIPInputStream gzi = new GZIPInputStream(mContext
						.getResources().openRawResource(R.raw.spike));
				ObjectInputStream fis = new ObjectInputStream(gzi);
				mRootSpike = new Object3D(
						(SerializedObject3D) fis.readObject());
				mRootSpike.setMaterial(spikeMaterial);
				mRootSpike.setColor(0xff33ff33);
				mRootSpike.setVisible(false);
				// -- objects that share the same geometry and material,
				// so batch rendering gives a performance boost.
				mRootSpike.setRenderChildrenAsBatch(true);
				getCurrentScene().addChild(mRootSpike);

				// -- get vertex buffer
				FloatBuffer vertBuffer = sphere.getGeometry().getVertices();
				// -- get the normal buffer
				FloatBuffer normBuffer = sphere.getGeometry().getNormals();
				int numVerts = vertBuffer.limit();

				// -- define the up axis. we will use this to rotate
				// the spikes
				Vector3 upAxis = new Vector3(0, 1, 0);

				// -- now loop through the sphere's vertices and place
				// a spike on each vertex
				for (int i = 0; i < numVerts; i += 3) {
					Object3D spike = mRootSpike.clone(true, false);
					// -- set the spike's position to the sphere's current vertex position
					spike.setPosition(vertBuffer.get(i), vertBuffer.get(i + 1),
							vertBuffer.get(i + 2));
					// -- get the normal so we can orient the spike to the normal
					Vector3 normal = new Vector3(normBuffer.get(i),
							normBuffer.get(i + 1), normBuffer.get(i + 2));
					// -- get the rotation axis
					Vector3 axis = Vector3.crossAndCreate(upAxis, normal);
					// -- get the rotation angle
					double angle = MathUtil.radiansToDegrees(Math.acos(Vector3.dot(upAxis, normal)));
					// -- create the quaternion
					Quaternion q = new Quaternion();
					q.fromAngleAxis(axis, angle);
					// -- set the orientation so that it is aligned with the current normal
					spike.setOrientation(q);
					mRootSpike.addChild(spike);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Vector3 rotationAxis = new Vector3(.3f, .9f, .15f);
			rotationAxis.normalize();

			RotateOnAxisAnimation mAnim = new RotateOnAxisAnimation(rotationAxis, 360);
			mAnim.setDurationMilliseconds(8000);
			mAnim.setRepeatMode(RepeatMode.INFINITE);
			mAnim.setTransformable3D(mRootSpike);

			getCurrentScene().registerAnimation(mAnim);
			mAnim.play();
		}

	}

}
