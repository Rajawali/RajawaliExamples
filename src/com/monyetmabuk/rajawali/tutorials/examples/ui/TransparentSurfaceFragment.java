package com.monyetmabuk.rajawali.tutorials.examples.ui;

import java.io.ObjectInputStream;

import rajawali.BaseObject3D;
import rajawali.SerializedObject3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Vector3.Axis;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class TransparentSurfaceFragment extends AExampleFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		ImageView iv = new ImageView(getActivity());
		iv.setImageResource(R.drawable.flickrpics);

		mLayout.addView(iv, 0);

		return mLayout;
	}

	@Override
	protected AExampleRenderer createRenderer() {
		return new TransparentSurfaceRenderer(getActivity());
	}
	
	@Override
	protected boolean isTransparentSurfaceView() {
		return true;
	}

	private final class TransparentSurfaceRenderer extends AExampleRenderer {

		public TransparentSurfaceRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			DirectionalLight light = new DirectionalLight(0, 0, -1);
			light.setPower(1);
			getCurrentCamera().setPosition(0, 0, 16);

			try {
				ObjectInputStream ois = new ObjectInputStream(mContext
						.getResources().openRawResource(R.raw.monkey_ser));
				SerializedObject3D serializedMonkey = (SerializedObject3D) ois
						.readObject();
				ois.close();

				BaseObject3D monkey = new BaseObject3D(serializedMonkey);
				DiffuseMaterial material = new DiffuseMaterial();
				material.setUseSingleColor(true);
				monkey.setMaterial(material);
				monkey.addLight(light);
				monkey.setColor(0xffff8C00);
				monkey.setScale(2);
				addChild(monkey);

				RotateAnimation3D anim = new RotateAnimation3D(Axis.Y, 360);
				anim.setDuration(6000);
				anim.setRepeatMode(RepeatMode.INFINITE);
				anim.setInterpolator(new AccelerateDecelerateInterpolator());
				anim.setTransformable3D(monkey);
				registerAnimation(anim);
				anim.play();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// -- set the background color to be transparent
			// you need to have called setGLBackgroundTransparent(true); in the activity
			// for this to work.
			getCurrentScene().setBackgroundColor(0);
		}

	}

}
