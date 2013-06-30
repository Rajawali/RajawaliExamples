package com.monyetmabuk.rajawali.tutorials.examples.general;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class CurvesFragment extends AExampleFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.TOP);

		TextView label = new TextView(getActivity());
		label.setText(R.string.curves_fragment_text_view_curve_types);
		label.setTextSize(14);
		label.setGravity(Gravity.CENTER);
		label.setHeight(100);
		ll.addView(label);

		mLayout.addView(ll);

		return mLayout;
	}

	@Override
	protected AExampleRenderer createRenderer() {
		return new CurvesRenderer(getActivity());
	}

	private final class CurvesRenderer extends AExampleRenderer {

		public CurvesRenderer(Context context) {
			super(context);
		}

	}

}
