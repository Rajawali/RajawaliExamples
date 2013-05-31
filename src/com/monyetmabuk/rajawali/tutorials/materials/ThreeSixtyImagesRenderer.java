package com.monyetmabuk.rajawali.tutorials.materials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.SimpleMaterial;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.ScreenQuad;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class ThreeSixtyImagesRenderer extends RajawaliRenderer {
	private ATexture[] mTextures;
	private ScreenQuad mScreenQuad;
	private int mFrameCount;
	private SimpleMaterial mMaterial;
	private final static int NUM_TEXTURES = 80;
	
	public ThreeSixtyImagesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene() {
    	getCurrentScene().setBackgroundColor(0xffffff);
    	
    	mMaterial = new SimpleMaterial();
    	
    	mScreenQuad = new ScreenQuad();
		mScreenQuad.setMaterial(mMaterial);
    	addChild(mScreenQuad);
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

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		
		for(int i=1; i<=NUM_TEXTURES; ++i) {
    		// -- load all the textures from the drawable folder
    		int resourceId = mContext.getResources().getIdentifier(i < 10 ? "m0" + i : "m" + i, "drawable", "com.monyetmabuk.rajawali.tutorials");
    		
    		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);
    		
    		ATexture texture = new Texture("bm" + i, bitmap);
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
