package com.monyetmabuk.rajawali.tutorials;

import android.opengl.GLES20;
import rajawali.materials.SimpleMaterial;

public class CustomMaterial extends SimpleMaterial {
	protected static final String mCustomFShader = 
			"precision mediump float;\n" +

			"uniform float uTime;\n" +
			"varying vec2 vTextureCoord;\n" +

			"void main() {\n" +
			"   vec4 newColor = vec4(1.0, 0, 0, 1.0);\n" +
			"	float x = min(vTextureCoord.s, 1.0 - vTextureCoord.s);" +
			"	float y = vTextureCoord.t;" + 
			"	newColor.g = sin(x * cos(uTime/15.0) * 120.0) + " + 
			"   	cos(y * sin(uTime/10.0) * 120.0) + " +
			"		sin(sqrt(y * y + x * x) * 40.0);\n" +
			"	gl_FragColor = newColor;\n" +
			"}\n";
	
	protected int muTimeHandle;
	
	public CustomMaterial() {
		super(mVShader, mCustomFShader);
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
