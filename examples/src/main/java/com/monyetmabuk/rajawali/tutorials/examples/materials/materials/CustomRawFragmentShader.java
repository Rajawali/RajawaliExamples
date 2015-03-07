package com.monyetmabuk.rajawali.tutorials.examples.materials.materials;

import rajawali.materials.shaders.FragmentShader;
import rajawali.util.RawShaderLoader;
import android.opengl.GLES20;

import com.monyetmabuk.rajawali.tutorials.R;

public class CustomRawFragmentShader extends FragmentShader {
	private int muTextureInfluenceHandle;
	
	public CustomRawFragmentShader()
	{
		super();
		mNeedsBuild = false;
		initialize();
	}
	
	@Override
	public void initialize()
	{
		mShaderString = RawShaderLoader.fetch(R.raw.custom_frag_shader);
	}
	
	@Override
	public void main() {

	}
	
	@Override
	public void setLocations(final int programHandle)
	{
		super.setLocations(programHandle);
		muTextureInfluenceHandle = getUniformLocation(programHandle, "uInfluencemyTex");
	}
	
	@Override
	public void applyParams() 
	{
		super.applyParams();
		GLES20.glUniform1f(muTextureInfluenceHandle, .5f);
	}
}
