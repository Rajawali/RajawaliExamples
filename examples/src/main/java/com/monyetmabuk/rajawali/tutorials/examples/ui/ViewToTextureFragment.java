package com.monyetmabuk.rajawali.tutorials.examples.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;

public class ViewToTextureFragment extends AExampleFragment {

    CustomFragment mCustomFragment;
    Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        mCustomFragment = new CustomFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.content_frame, mCustomFragment, "custom").commit();
        return mLayout;
    }

    @Override
    public AExampleRenderer createRenderer() {
        return new CanvasTextRenderer(getActivity());
    }

    @Override
    protected void onBeforeApplyRenderer() {
        super.onBeforeApplyRenderer();
    }

    private final class CanvasTextRenderer extends AExampleRenderer {
        private Texture mTimeTexture;
        private Bitmap mTimeBitmap;
        private Canvas mTimeCanvas;
        private int mFrameCount;
        private boolean mShouldUpdateTexture;

        private Object3D mObject3D;

        public CanvasTextRenderer(Context context) {
            super(context);
        }

        @Override
        public void initScene() {
            DirectionalLight light = new DirectionalLight(.1f, .1f, -1);
            light.setPower(2);
            getCurrentScene().addLight(light);

            Cube parentCube = null;

            for (int i = 0; i < 1; i++) {
                Cube timeCube = new Cube(3.0f);
                timeCube.setColor(0);

                if (parentCube == null) {
                    timeCube.setPosition(0, 0, -3);
                    timeCube.setRenderChildrenAsBatch(true);
                    parentCube = timeCube;
                    mObject3D = timeCube;
                } else {
                    timeCube.setX(-3 + (float) (Math.random() * 6));
                    timeCube.setY(-3 + (float) (Math.random() * 6));
                    timeCube.setZ(-3 + (float) (Math.random() * 6));
                    parentCube.addChild(timeCube);
                }

                int direction = Math.random() < .5 ? 1 : -1;

                RotateOnAxisAnimation anim = new RotateOnAxisAnimation(Vector3.Axis.Y, 0,
                    360 * direction);
                anim.setRepeatMode(Animation.RepeatMode.INFINITE);
                anim.setDurationMilliseconds(i == 0 ? 12000
                    : 4000 + (int) (Math.random() * 4000));
                anim.setTransformable3D(timeCube);
                getCurrentScene().registerAnimation(anim);
                anim.play();
            }
        }

            final Runnable mUpdateTexture = new Runnable() {
                public void run() {
                    if (mTimeCanvas == null) {
                        Material timeSphereMaterial = new Material();
                        timeSphereMaterial.enableLighting(true);
                        timeSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
                        timeSphereMaterial.setColorInfluence(0);
                        mTimeBitmap = Bitmap.createBitmap(mCustomFragment.getView().getWidth(),
                            mCustomFragment.getView().getHeight(), Bitmap.Config.ARGB_8888);
                        mTimeTexture = new Texture("timeTexture", mTimeBitmap);
                        try {
                            timeSphereMaterial.addTexture(mTimeTexture);
                        } catch (ATexture.TextureException e) {
                            e.printStackTrace();
                        }

                        mTimeCanvas = new Canvas(mTimeBitmap);
                        mObject3D.setMaterial(timeSphereMaterial);
                        getCurrentScene().addChild(mObject3D);
                    }
                    // -- Draw the time on the canvas
                    mCustomFragment.getView().draw(mTimeCanvas);
                    // -- Indicates that the texture should be updated on the OpenGL thread.
                    mShouldUpdateTexture = true;
                }
            };

        @Override
        protected void onRender(long ellapsedRealtime, double deltaTime) {
            // -- not a really accurate way of doing things but you get the point :)
            if (mFrameCount++ >= 2*mFrameRate) {
                mFrameCount = 0;
                mHandler.post(mUpdateTexture);
            }
            // -- update the texture because it is ready
            if (mShouldUpdateTexture) {
                mTimeTexture.setBitmap(mTimeBitmap);
                mTextureManager.replaceTexture(mTimeTexture);
                mShouldUpdateTexture = false;
            }
            super.onRender(ellapsedRealtime, deltaTime);
        }

    }

    public static final class CustomFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.view_to_texture, container, false);
            final WebView webView = (WebView) view.findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://plus.google.com/communities/116529974266844528013");
            view.setVisibility(View.INVISIBLE);
            return view;
        }
    }
}
