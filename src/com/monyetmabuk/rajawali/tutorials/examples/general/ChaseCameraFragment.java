package com.monyetmabuk.rajawali.tutorials.examples.general;

import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.microedition.khronos.opengles.GL10;

import rajawali.ChaseCamera;
import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Cube;
import rajawali.primitives.Sphere;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class ChaseCameraFragment extends AExampleFragment implements
		OnSeekBarChangeListener {

	private SeekBar mSeekBarX, mSeekBarY, mSeekBarZ;
	private Vector3 mCameraOffset;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mMultisamplingEnabled = false;
		mCameraOffset = new Vector3();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.BOTTOM);

		mSeekBarZ = new SeekBar(getActivity());
		mSeekBarZ.setMax(40);
		mSeekBarZ.setProgress(16);
		mSeekBarZ.setOnSeekBarChangeListener(this);
		ll.addView(mSeekBarZ);

		mSeekBarY = new SeekBar(getActivity());
		mSeekBarY.setMax(20);
		mSeekBarY.setProgress(13);
		mSeekBarY.setOnSeekBarChangeListener(this);
		ll.addView(mSeekBarY);

		mSeekBarX = new SeekBar(getActivity());
		mSeekBarX.setMax(20);
		mSeekBarX.setProgress(10);
		mSeekBarX.setOnSeekBarChangeListener(this);
		ll.addView(mSeekBarX);

		mLayout.addView(ll);
		
		return mLayout;
	}

	@Override
	protected AExampleRenderer createRenderer() {
		return new ChaseCameraRenderer(getActivity());
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mCameraOffset.setAll(mSeekBarX.getProgress() - 10,
				mSeekBarY.getProgress() - 10, mSeekBarZ.getProgress());
		((ChaseCameraRenderer) mRenderer).setCameraOffset(mCameraOffset);
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	private final class ChaseCameraRenderer extends AExampleRenderer {

		private Object3D mRaptor, mSphere;
		private Object3D[] mCubes;
		private Object3D mRootCube;
		private float mTime;

		public ChaseCameraRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			DirectionalLight light = new DirectionalLight(0, -.6f, .4f);
			light.setPower(1);
			getCurrentScene().addLight(light);

			// -- create sky sphere
			mSphere = new Sphere(400, 8, 8);
			Material sphereMaterial = new Material();
			try {
				sphereMaterial.addTexture(new Texture("skySphere", R.drawable.skysphere));
			} catch (TextureException e1) {
				e1.printStackTrace();
			}
			mSphere.setMaterial(sphereMaterial);
			mSphere.setDoubleSided(true);
			addChild(mSphere);

			try {
				// -- load gzipped serialized object
				ObjectInputStream ois;
				GZIPInputStream zis = new GZIPInputStream(mContext
						.getResources().openRawResource(R.raw.raptor));
				ois = new ObjectInputStream(zis);
				mRaptor = new Object3D(
						(SerializedObject3D) ois.readObject());
				Material raptorMaterial = new Material();
				raptorMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				raptorMaterial.enableLighting(true);
				raptorMaterial.addTexture(new Texture("raptorTex", R.drawable.raptor_texture));
				mRaptor.setMaterial(raptorMaterial);
				mRaptor.setScale(.5f);
				addChild(mRaptor);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// -- create a bunch of cubes that will serve as orientation helpers

			mCubes = new Object3D[30];

			mRootCube = new Cube(1);
			Material rootCubeMaterial = new Material();
			rootCubeMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			rootCubeMaterial.enableLighting(true);
			try {
				rootCubeMaterial.addTexture(new Texture("camouflage", R.drawable.camouflage));
			} catch (TextureException e) {
				e.printStackTrace();
			}
			mRootCube.setMaterial(rootCubeMaterial);
			mRootCube.setY(-1f);
			// -- similar objects with the same material, optimize
			mRootCube.setRenderChildrenAsBatch(true);
			addChild(mRootCube);
			mCubes[0] = mRootCube;

			for (int i = 1; i < mCubes.length; ++i) {
				Object3D cube = mRootCube.clone(true);
				cube.setY(-1f);
				cube.setZ(i * 30);
				mRootCube.addChild(cube);
				mCubes[i] = cube;
			}

			// -- create a chase camera
			// the first parameter is the camera offset
			// the second parameter is the interpolation factor
			ChaseCamera chaseCamera = new ChaseCamera(new Vector3(0, 3, 16),
					.1f);
			// -- tell the camera which object to chase
			chaseCamera.setObjectToChase(mRaptor);
			// -- set the far plane to 1000 so that we actually see the sky sphere
			chaseCamera.setFarPlane(1000);
			replaceAndSwitchCamera(chaseCamera, 0);
		}

		public void setCameraOffset(Vector3 offset) {
			// -- change the camera offset
			((ChaseCamera) getCurrentCamera()).setCameraOffset(offset);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			// -- no proper physics here, just a bad approximation to keep
			// this example as short as possible ;-)
			mRaptor.setZ(mRaptor.getZ() + 2f);
			mRaptor.setX((float) Math.sin(mTime) * 20f);
			mRaptor.setRotZ((float) Math.sin(mTime + 8f) * -30f);
			mRaptor.setRotY(180 + (mRaptor.getRotZ() * .1f));
			mRaptor.setRotY(180);
			mRaptor.setY((float) Math.cos(mTime) * 10f);
			mRaptor.setRotX((float) Math.cos(mTime + 1f) * -20f);

			mSphere.setZ(mRaptor.getZ());
			mTime += .01f;

			if (mRootCube.getZ() - mRaptor.getZ() <= (30 * -6)) {
				mRootCube.setZ(mRaptor.getZ());
			}
		}

	}

}
