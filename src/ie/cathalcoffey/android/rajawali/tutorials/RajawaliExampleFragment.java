package ie.cathalcoffey.android.rajawali.tutorials;

import com.monyetmabuk.rajawali.tutorials.R;

import rajawali.RajawaliFragment;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class RajawaliExampleFragment extends RajawaliFragment {
	protected ImageView mLoaderGraphic;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void showLoader() {
		mLayout.post(new Runnable() {
			public void run() {
				mLoaderGraphic.setId(1);
				mLoaderGraphic.setScaleType(ScaleType.CENTER);
				mLoaderGraphic.setImageResource(R.drawable.loader);
				if(mLoaderGraphic.getParent() == null)
				{
					mLoaderGraphic = new ImageView(getActivity());
					mLayout.addView(mLoaderGraphic);
				}
				AnimationSet animSet = new AnimationSet(false);
		
				RotateAnimation anim1 = new RotateAnimation(360, 0,
						Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
						.5f);
				anim1.setRepeatCount(Animation.INFINITE);
				anim1.setDuration(2000);
				animSet.addAnimation(anim1);
		
				AlphaAnimation anim2 = new AlphaAnimation(0, 1);
				anim2.setRepeatCount(0);
				anim2.setDuration(1000);
				animSet.addAnimation(anim2);
		
				mLoaderGraphic.setAnimation(animSet);
			}
		});
	}

	public void hideLoader() {
		mLayout.post(new Runnable() {
			public void run() {
				AlphaAnimation anim = new AlphaAnimation(1, 0);
				anim.setRepeatCount(0);
				anim.setDuration(500);
				anim.setAnimationListener(new AnimationListener() {
					public void onAnimationStart(Animation animation) {
					}

					public void onAnimationRepeat(Animation animation) {
					}

					public void onAnimationEnd(Animation animation) {
						mLoaderGraphic.setVisibility(View.INVISIBLE);
						mLayout.removeView(mLoaderGraphic);
						
					}
				});
				((AnimationSet) mLoaderGraphic.getAnimation())
						.addAnimation(anim);
			}
		});
	}
}
