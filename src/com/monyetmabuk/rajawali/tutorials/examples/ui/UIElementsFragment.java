package com.monyetmabuk.rajawali.tutorials.examples.ui;

import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.PhongMaterial;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class UIElementsFragment extends AExampleFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.CENTER);

		TextView label = new TextView(getActivity());
		label.setText(R.string.ui_elements_fragment_text_view_halo_dunia);
		label.setTextSize(20);
		label.setGravity(Gravity.CENTER);
		label.setHeight(100);
		ll.addView(label);

		ImageView image = new ImageView(getActivity());
		image.setImageResource(R.drawable.rajawali_outline);
		ll.addView(image);

		mLayout.addView(ll);

		return mLayout;
	}

	@Override
	protected AExampleRenderer createRenderer() {
		return new UIElementsRenderer(getActivity());
	}

	private final class UIElementsRenderer extends AExampleRenderer {
		private DirectionalLight mLight;
		private Object3D mAndroid;

		public UIElementsRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(0, 0, -1);
			mLight.setPower(.8f);
			getCurrentCamera().setPosition(0, 0, 13);

			GZIPInputStream gzi;
			try {
				gzi = new GZIPInputStream(mContext.getResources()
						.openRawResource(R.raw.android));
				ObjectInputStream fis = new ObjectInputStream(gzi);
				SerializedObject3D serializedAndroid = (SerializedObject3D) fis
						.readObject();
				fis.close();

				mAndroid = new Object3D(serializedAndroid);
				mAndroid.addLight(mLight);
				addChild(mAndroid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			PhongMaterial material = new PhongMaterial();
			material.setUseSingleColor(true);
			mAndroid.setMaterial(material);
			mAndroid.setColor(0xff99C224);
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mAndroid.setRotY(mAndroid.getRotY() + 1);
		}
	}

}
