package com.monyetmabuk.rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.Camera2D;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class Rajawali360ImagesRenderer extends RajawaliRenderer {
	private ATexture[] mTextures;
	private Plane mPlane;
	private int mFrameCount;
	private SimpleMaterial mMaterial;
	private final static int NUM_TEXTURES = 80;
	
	public Rajawali360ImagesRenderer(Context context) {
		super(context);
		setFrameRate(60);
		getCurrentScene().switchCamera(new Camera2D());
	}
	
	protected void initScene() {
    	getCurrentScene().setBackgroundColor(0xffffff);
    	
    	mMaterial = new SimpleMaterial();
    	
    	mPlane = new Plane(1.5f, 1, 1, 1);
		mPlane.setMaterial(mMaterial);
    	addChild(mPlane);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		if(mTextureManager != null) mTextureManager.reset();
		if(mMaterial != null) mMaterial.getTextureList().clear();
		super.onSurfaceCreated(gl, config);
		
		if(mTextures == null) {
			// -- create an array that will contain all TextureInfo objects
	    	mTextures = new ATexture[NUM_TEXTURES];
		}
		mFrameCount = 0;
    	for(int i=1; i<=NUM_TEXTURES; ++i) {
    		// -- load all the textures from the drawable folder
    		int resourceId = mContext.getResources().getIdentifier(i < 10 ? "m0" + i : "m" + i, "drawable", "com.monyetmabuk.rajawali.tutorials");
    		ATexture texture = new Texture(resourceId);
    		texture.setMipmap(false);
    		texture.shouldRecycle(true);
    		mTextures[i-1] = mTextureManager.addTexture(texture);
    	}
    	try {
			mMaterial.addTexture(mTextures[0]);
		} catch (TextureException e) {
			e.printStackTrace();
		}
		
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	
    public void onDrawFrame(GL10 glUnused) {
        super.onDrawFrame(glUnused);
        // -- get the texture info list and remove the previous TextureInfo object
        mMaterial.getTextureList().remove(mTextures[mFrameCount++ % NUM_TEXTURES]);
        // -- add a new TextureInfo object
        mMaterial.getTextureList().add(mTextures[mFrameCount % NUM_TEXTURES]);
    }
}
