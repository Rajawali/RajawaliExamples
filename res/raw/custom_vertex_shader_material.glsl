// -- normalized axis vectors. we'll use these to
//    get the angles
const vec3 cXaxis = vec3(1.0, 0.0, 0.0);
const vec3 cYaxis = vec3(0.0, 1.0, 0.0);
const vec3 cZaxis = vec3(0.0, 0.0, 1.0);
// -- the amplitude of the 'wave' effect
const float cStrength = 0.5;
	
uniform mat4 uMVPMatrix;
uniform float uTime;

attribute vec4 aPosition;
attribute vec2 aTextureCoord;

varying vec2 vTextureCoord;
varying vec4 vColor;
	
void main() {
	// -- normalized direction from the origin (0,0,0)
	vec3 directionVec = normalize(vec3(aPosition));
	// -- the angle between this vertex and the x, y, z angles
	float xangle = dot(cXaxis, directionVec) * 5.0;
	float yangle = dot(cYaxis, directionVec) * 6.0;
	float zangle = dot(cZaxis, directionVec) * 4.5;
	vec4 timeVec = aPosition;
	float time = uTime * .05;
	// -- cos & sin calculations for each of the angles
	//    change some numbers here & there to get the 
	//    desired effect.
	float cosx = cos(time + xangle);
	float sinx = sin(time + xangle);
	float cosy = cos(time + yangle);
	float siny = sin(time + yangle);
	float cosz = cos(time + zangle);
	float sinz = sin(time + zangle);
	// -- multiply all the parameters to get the final
	//    vertex position
	timeVec.x += directionVec.x * cosx * siny * cosz * cStrength;
	timeVec.y += directionVec.y * sinx * cosy * sinz * cStrength;
	timeVec.z += directionVec.z * sinx * cosy * cosz * cStrength;
	gl_Position = uMVPMatrix * timeVec;
	vTextureCoord = aTextureCoord;
	// -- use the (normalized) direction vector as the
	//    vertex color to get a nice colorful effect
	vColor = vec4(directionVec, 1.0);
}