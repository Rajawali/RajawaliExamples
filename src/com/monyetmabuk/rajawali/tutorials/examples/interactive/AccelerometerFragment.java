package com.monyetmabuk.rajawali.tutorials.examples.interactive;

import java.io.ObjectInputStream;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.CubeMapTexture;
import rajawali.math.vector.Vector3;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class AccelerometerFragment extends AExampleFragment implements
		SensorEventListener {

	private final float ALPHA = 0.8f;
	private final int SENSITIVITY = 5;

	private SensorManager mSensorManager;
	private float mGravity[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGravity = new float[3];
		mSensorManager = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected AExampleRenderer createRenderer() {
		return new AccelerometerRenderer(getActivity());
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			mGravity[0] = ALPHA * mGravity[0] + (1 - ALPHA) * event.values[0];
			mGravity[1] = ALPHA * mGravity[1] + (1 - ALPHA) * event.values[1];
			mGravity[2] = ALPHA * mGravity[2] + (1 - ALPHA) * event.values[2];

			((AccelerometerRenderer) mRenderer).setAccelerometerValues(
					event.values[1] - mGravity[1] * SENSITIVITY,
					event.values[0] - mGravity[0] * SENSITIVITY, 0);
		}
	}

	private final class AccelerometerRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private Object3D mMonkey;
		private Vector3 mAccValues;

		public AccelerometerRenderer(Context context) {
			super(context);
			mAccValues = new Vector3();
		}

		protected void initScene() {
			mLight = new DirectionalLight(0.1f, 0.2f, -1.0f);
			mLight.setColor(1.0f, 1.0f, 1.0f);
			mLight.setPower(1);
			getCurrentScene().addLight(mLight);

			try {
				ObjectInputStream ois = new ObjectInputStream(mContext
						.getResources().openRawResource(R.raw.monkey_ser));
				SerializedObject3D serializedMonkey = (SerializedObject3D) ois
						.readObject();
				ois.close();

				mMonkey = new Object3D(serializedMonkey);
				mMonkey.setRotY(180);
				addChild(mMonkey);
			} catch (Exception e) {
				e.printStackTrace();
			}

			getCurrentCamera().setZ(7);

			int[] resourceIds = new int[] { R.drawable.posx, R.drawable.negx,
					R.drawable.posy, R.drawable.negy, R.drawable.posz,
					R.drawable.negz };

			Material material = new Material();
			material.enableLighting(true);
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			try {
				CubeMapTexture envMap = new CubeMapTexture("environmentMap",
						resourceIds);
				envMap.isEnvironmentTexture(true);
				material.addTexture(envMap);
				material.setColorInfluence(0);
				mMonkey.setMaterial(material);
			} catch (TextureException e) {
				e.printStackTrace();
			}
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mMonkey.setRotation(mAccValues.x, mAccValues.y + 180, mAccValues.z);
		}

		public void setAccelerometerValues(float x, float y, float z) {
			mAccValues.setAll(-x, -y, -z);
		}

	}

}
