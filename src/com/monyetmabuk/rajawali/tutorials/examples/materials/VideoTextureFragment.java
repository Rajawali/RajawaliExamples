package com.monyetmabuk.rajawali.tutorials.examples.materials;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.PointLight;
import rajawali.materials.PhongMaterial;
import rajawali.materials.VideoMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.VideoTexture;
import rajawali.math.Vector3;
import rajawali.primitives.Plane;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.media.MediaPlayer;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class VideoTextureFragment extends AExampleFragment {

	@Override
	protected AExampleRenderer createRenderer() {
		return new VideoTextureRenderer(getActivity());
	}

	private final class VideoTextureRenderer extends AExampleRenderer {
		private MediaPlayer mMediaPlayer;
		private VideoTexture mVideoTexture;

		public VideoTextureRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			PointLight pointLight = new PointLight();
			pointLight.setPower(1);
			pointLight.setPosition(-1, 1, 4);

			getCurrentScene().setBackgroundColor(0xff040404);

			GZIPInputStream gzi;
			try {
				gzi = new GZIPInputStream(mContext.getResources()
						.openRawResource(R.raw.android));
				ObjectInputStream fis = new ObjectInputStream(gzi);

				BaseObject3D android = new BaseObject3D(
						(SerializedObject3D) fis.readObject());
				PhongMaterial material = new PhongMaterial();
				material.setUseSingleColor(true);
				android.setMaterial(material);
				android.addLight(pointLight);
				android.setColor(0xff99C224);
				getCurrentScene().addChild(android);
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			mMediaPlayer = MediaPlayer.create(getContext(),
					R.raw.sintel_trailer_480p);
			mMediaPlayer.setLooping(true);

			mVideoTexture = new VideoTexture("sintelTrailer", mMediaPlayer);
			VideoMaterial material = new VideoMaterial();
			try {
				material.addTexture(mVideoTexture);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			Plane screen = new Plane(3, 2, 2, 2);
			screen.setMaterial(material);
			screen.setX(.1f);
			screen.setY(-.2f);
			screen.setZ(1.5f);
			addChild(screen);

			getCurrentCamera().setLookAt(0, 0, 0);

			// -- animate the spot light

			TranslateAnimation3D lightAnim = new TranslateAnimation3D(
					new Vector3(-3, 3, 10), // from
					new Vector3(3, 1, 3)); // to
			lightAnim.setDuration(5000);
			lightAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			lightAnim.setTransformable3D(pointLight);
			lightAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			registerAnimation(lightAnim);
			lightAnim.play();

			// -- animate the camera

			EllipticalOrbitAnimation3D camAnim = new EllipticalOrbitAnimation3D(
					new Vector3(3, 2, 10), new Vector3(1, 0, 8), 0, 359);
			camAnim.setDuration(20000);
			camAnim.setRepeatMode(RepeatMode.INFINITE);
			camAnim.setTransformable3D(getCurrentCamera());
			registerAnimation(camAnim);
			camAnim.play();

			mMediaPlayer.start();
		}

		@Override
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mVideoTexture.update();
		}

		public void onVisibilityChanged(boolean visible) {
			super.onVisibilityChanged(visible);
			if (!visible)
				if (mMediaPlayer != null)
					mMediaPlayer.pause();
				else if (mMediaPlayer != null)
					mMediaPlayer.start();
		}

		public void onSurfaceDestroyed() {
			super.onSurfaceDestroyed();
			mMediaPlayer.stop();
			mMediaPlayer.release();
		}

	}

}
