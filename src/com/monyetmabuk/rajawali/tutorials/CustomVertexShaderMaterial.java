package com.monyetmabuk.rajawali.tutorials;

import rajawali.materials.SimpleMaterial;
import android.opengl.GLES20;

public class CustomVertexShaderMaterial extends SimpleMaterial {
	protected static final String mVShader = 
			// -- normalized axis vectors. we'll use these to
			//    get the angles
			"const vec3 cXaxis = vec3(1.0, 0.0, 0.0);\n" +
			"const vec3 cYaxis = vec3(0.0, 1.0, 0.0);\n" +
			"const vec3 cZaxis = vec3(0.0, 0.0, 1.0);\n" +
			// -- the amplitude of the 'wave' effect
			"const float cStrength = 0.5;\n" +
			
			"uniform mat4 uMVPMatrix;\n" +
			"uniform float uTime;\n" +

			"attribute vec4 aPosition;\n" +
			"attribute vec2 aTextureCoord;\n" +
			"attribute vec4 aColor;\n" +

			"varying vec2 vTextureCoord;\n" +
			"varying vec4 vColor;\n" +		
			
			"void main() {\n" +
			// -- normalized direction from the origin (0,0,0)
			"	vec3 directionVec = normalize(vec3(aPosition));\n" +
			// -- the angle between this vertex and the x, y, z angles
			"	float xangle = dot(cXaxis, directionVec) * 5.0;\n" +
			"	float yangle = dot(cYaxis, directionVec) * 6.0;\n" +
			"	float zangle = dot(cZaxis, directionVec) * 4.5;\n" +
			"	vec4 timeVec = aPosition;\n" +
			"	float time = uTime * .05;\n" +
			// -- cos & sin calculations for each of the angles
			//    change some numbers here & there to get the 
			//    desired effect.
			"	float cosx = cos(time + xangle);\n" +
			"	float sinx = sin(time + xangle);\n" +
			"	float cosy = cos(time + yangle);\n" +
			"	float siny = sin(time + yangle);\n" +
			"	float cosz = cos(time + zangle);\n" +
			"	float sinz = sin(time + zangle);\n" +
			// -- multiply all the parameters to get the final
			//    vertex position
			"	timeVec.x += directionVec.x * cosx * siny * cosz * cStrength;\n" +
			"	timeVec.y += directionVec.y * sinx * cosy * sinz * cStrength;\n" +
			"	timeVec.z += directionVec.z * sinx * cosy * cosz * cStrength;\n" +
			"	gl_Position = uMVPMatrix * timeVec;\n" +
			"	vTextureCoord = aTextureCoord;\n" +
			// -- use the (normalized) direction vector as the
			//    vertex color to get a nice colorful effect
			"	vColor = vec4(directionVec, 1.0);\n" +
			"}\n";
	
	protected int muTimeHandle;
	protected float mTime;
	
	public CustomVertexShaderMaterial() {
		super(mVShader, SimpleMaterial.mFShader);
	}
	
	public CustomVertexShaderMaterial(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}
	
	public void setShaders(String vertexShader, String fragmentShader)
	{
		super.setShaders(vertexShader, fragmentShader);
		muTimeHandle = getUniformLocation("uTime");
	}
	
	public void useProgram() {
		super.useProgram();
		// -- make sure that time updates every frame
		GLES20.glUniform1f(muTimeHandle, mTime);
	}
	
	public void setTime(float time) {
		mTime = time;
	}
}
