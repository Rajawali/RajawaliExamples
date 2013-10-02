package com.monyetmabuk.rajawali.tutorials.examples.materials;

import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AnimatedGIFTexture;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Plane;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class AnimatedGIFTextureFragment extends AExampleFragment implements
		OnTouchListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.BOTTOM);

		TextView label = new TextView(getActivity());
		label.setText(R.string.animated_gif_texture_fragment_info);
		label.setTextSize(20);
		label.setGravity(Gravity.CENTER);
		label.setHeight(100);
		ll.addView(label);

		mLayout.addView(ll);

		return mLayout;
	}

	@Override
	protected AExampleRenderer createRenderer() {
		return new AnimatedGIFTextureRenderer(getActivity());
	}

	public boolean onTouch(View view, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			((AnimatedGIFTextureRenderer) mRenderer).nextGIF();
		}
		return true;
	}

	private final class AnimatedGIFTextureRenderer extends AExampleRenderer {
		private AnimatedGIFTexture mGIFTexture;
		private int[] mGifs;
		private int mCurrentGIF;
		private Plane mPlane;
		private boolean mUpdatePlaneSize;

		public AnimatedGIFTextureRenderer(Context context) {
			super(context);

			mGifs = new int[] { R.drawable.animated_gif_01,
					R.drawable.animated_gif_02, R.drawable.animated_gif_03,
					R.drawable.animated_gif_04, R.drawable.animated_gif_05 };
		}

		protected void initScene() {
			mPlane = new Plane(Axis.Z);
			Material material = new Material();
			try {
				mGIFTexture = new AnimatedGIFTexture("animGif", mGifs[0]);
				material.addTexture(mGIFTexture);
				material.setColorInfluence(0);
				mGIFTexture.rewind();
				mPlane.setScaleX((float) mGIFTexture.getWidth()
						/ (float) mGIFTexture.getHeight());
				mCurrentGIF++;
			} catch (TextureException e) {
				e.printStackTrace();
			}

			mPlane.setMaterial(material);
			getCurrentScene().addChild(mPlane);
			getCurrentCamera().setLookAt(0, 0, 0);

			EllipticalOrbitAnimation3D anim = new EllipticalOrbitAnimation3D(
					new Vector3(0, 0, 3), new Vector3(2, 1, 3), 0, 359);
			anim.setDuration(12000);
			anim.setRepeatMode(RepeatMode.INFINITE);
			anim.setTransformable3D(getCurrentCamera());
			registerAnimation(anim);
			anim.play();
		}

		public void nextGIF() {
			if (mCurrentGIF == mGifs.length)
				mCurrentGIF = 0;
			mGIFTexture.setResourceId(mGifs[mCurrentGIF++]);
			mUpdatePlaneSize = true;
			mPlane.setScaleX((float) mGIFTexture.getWidth()
					/ (float) mGIFTexture.getHeight());
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			if (mGIFTexture != null) {
				try {
					mGIFTexture.update();
				} catch (TextureException e) {
					e.printStackTrace();
				}
				if (mUpdatePlaneSize) {
					mPlane.setScaleX((float) mGIFTexture.getWidth()
							/ (float) mGIFTexture.getHeight());
					mUpdatePlaneSize = false;
				}
			}
		}

	}

}
