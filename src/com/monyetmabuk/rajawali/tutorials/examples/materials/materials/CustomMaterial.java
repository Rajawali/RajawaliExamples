package com.monyetmabuk.rajawali.tutorials.examples.materials.materials;

import rajawali.materials.SimpleMaterial;
import android.opengl.GLES20;

import com.monyetmabuk.rajawali.tutorials.R;

public class CustomMaterial extends SimpleMaterial {
	
	protected int muTimeHandle;
	
	public CustomMaterial() {
		super(com.monyetmabuk.livewallpapers.photosdof.R.raw.simple_material_vertex, R.raw.custom_material_fragment);
	}
	
	public void setShaders(String vertexShader, String fragmentShader)
	{
		super.setShaders(vertexShader, fragmentShader);
		muTimeHandle = GLES20.glGetUniformLocation(mProgram, "uTime");
		if(muTimeHandle == -1) {
			throw new RuntimeException("Could not get uniform location for uTime");
		}
	}
	
	public void setTime(float time) {
		GLES20.glUniform1f(muTimeHandle, time);
	}
}
