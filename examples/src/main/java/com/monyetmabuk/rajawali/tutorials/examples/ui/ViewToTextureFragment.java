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
import android.widget.FrameLayout;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;
import org.rajawali3d.util.RajLog;

public class ViewToTextureFragment extends AExampleFragment {

    FragmentToDraw mFragmentToDraw;
    Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());

        final FrameLayout fragmentFrame = new FrameLayout(getActivity());
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(512, 512);
        fragmentFrame.setLayoutParams(params);
        fragmentFrame.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        fragmentFrame.setId(R.id.view_to_texture_frame);
        fragmentFrame.setVisibility(View.INVISIBLE);
        mLayout.addView(fragmentFrame);

        mFragmentToDraw = new FragmentToDraw();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.view_to_texture_frame, mFragmentToDraw, "custom").commit();
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
        private Texture mViewTexture;
        private Bitmap mViewBitmap;
        private Canvas mViewCanvas;
        private int mFrameCount;
        private volatile boolean mShouldUpdateTexture;

        private Object3D mObject3D;

        public CanvasTextRenderer(Context context) {
            super(context);
        }

        @Override
        public void initScene() {
            mObject3D = new Cube(3.0f);
            mObject3D.setColor(0);

            mObject3D.setPosition(0, 0, -3);
            mObject3D.setRenderChildrenAsBatch(true);

            Material viewMaterial = new Material();
            viewMaterial.setColorInfluence(0);
            mViewBitmap = Bitmap.createBitmap(mFragmentToDraw.getView().getWidth(),
                mFragmentToDraw.getView().getHeight(), Bitmap.Config.ARGB_8888);
            mViewTexture = new Texture("viewTexture", mViewBitmap);
            try {
                viewMaterial.addTexture(mViewTexture);
            } catch (ATexture.TextureException e) {
                e.printStackTrace();
            }

            mViewCanvas = new Canvas(mViewBitmap);
            mObject3D.setMaterial(viewMaterial);
            getCurrentScene().addChild(mObject3D);

            RotateOnAxisAnimation anim = new RotateOnAxisAnimation(Vector3.Axis.Y, 0,
                360);
            anim.setRepeatMode(Animation.RepeatMode.INFINITE);
            anim.setDurationMilliseconds(12000);
            anim.setTransformable3D(mObject3D);
            getCurrentScene().registerAnimation(anim);
            anim.play();
        }

        final Runnable mUpdateTexture = new Runnable() {
            public void run() {
                // -- Draw the view on the canvas
                mFragmentToDraw.getView().draw(mViewCanvas);
                // -- Indicates that the texture should be updated on the OpenGL thread.
                mShouldUpdateTexture = true;
            }
        };

        @Override
        protected void onRender(long ellapsedRealtime, double deltaTime) {
            // -- not a really accurate way of doing things but you get the point :)
            if (mFrameCount++ >= 10) { //mFrameRate) {
                mFrameCount = 0;
                mHandler.post(mUpdateTexture);
            }
            // -- update the texture because it is ready
            if (mShouldUpdateTexture) {
                RajLog.i("Updating texture (" + ellapsedRealtime + ")");
                mViewTexture.setBitmap(mViewBitmap);
                mTextureManager.replaceTexture(mViewTexture);
                mShouldUpdateTexture = false;
            }
            super.onRender(ellapsedRealtime, deltaTime);
        }
    }

    public static final class FragmentToDraw extends Fragment {

        WebView mWebView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.view_to_texture, container, false);
            mWebView = (WebView) view.findViewById(R.id.webview);
            mWebView.setWebViewClient(new WebViewClient());
            // Load the Rajawali Repo commit activity graph
            //webView.loadUrl("https://github.com/Rajawali/Rajawali/graphs/commit-activity");
            mWebView.loadUrl("https://plus.google.com/communities/116529974266844528013");
            mWebView.setInitialScale(50);
            mWebView.setScrollY(0);
            view.setVisibility(View.INVISIBLE);
            return view;
        }
    }
}
