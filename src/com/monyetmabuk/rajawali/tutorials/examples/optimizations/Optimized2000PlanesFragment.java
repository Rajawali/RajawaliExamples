package com.monyetmabuk.rajawali.tutorials.examples.optimizations;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.curves.CatmullRomCurve3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Vector3;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.interactive.planes.PlanesGalore;
import com.monyetmabuk.rajawali.tutorials.examples.interactive.planes.PlanesGaloreMaterial;

public class Optimized2000PlanesFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new Optimized2000PlanesRenderer(getActivity());
	}

	private final class Optimized2000PlanesRenderer extends AExampleRenderer {

		private PlanesGalore mPlanes;
		private PlanesGaloreMaterial mMaterial;
		private long mStartTime;
		private TranslateAnimation3D mCamAnim;

		public Optimized2000PlanesRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			DirectionalLight light = new DirectionalLight(0, 0, 1);
			getCurrentCamera().setPosition(0, 0, -16);

			mPlanes = new PlanesGalore();
			try {
				mMaterial = (PlanesGaloreMaterial) mPlanes.getMaterial();
				mMaterial.addTexture(new Texture(R.drawable.flickrpics));
			} catch (TextureException e) {
				e.printStackTrace();
			}
			mPlanes.addLight(light);
			mPlanes.setDoubleSided(true);
			mPlanes.setZ(4);
			addChild(mPlanes);

			BaseObject3D empty = new BaseObject3D();
			addChild(empty);

			CatmullRomCurve3D path = new CatmullRomCurve3D();
			path.addPoint(new Vector3(-4, 0, -20));
			path.addPoint(new Vector3(2, 1, -10));
			path.addPoint(new Vector3(-2, 0, 10));
			path.addPoint(new Vector3(0, -4, 20));
			path.addPoint(new Vector3(5, 10, 30));
			path.addPoint(new Vector3(-2, 5, 40));
			path.addPoint(new Vector3(3, -1, 60));
			path.addPoint(new Vector3(5, -1, 70));

			mCamAnim = new TranslateAnimation3D(path);
			mCamAnim.setDuration(20000);
			mCamAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			mCamAnim.setTransformable3D(getCurrentCamera());
			mCamAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			registerAnimation(mCamAnim);

			getCurrentCamera().setLookAt(new Vector3(0, 0, 30));
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			super.onSurfaceCreated(gl, config);
			mStartTime = System.currentTimeMillis();
			mCamAnim.play();
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mMaterial
					.setTime((System.currentTimeMillis() - mStartTime) / 1000f);
		}

	}

}
