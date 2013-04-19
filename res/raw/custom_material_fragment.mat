precision mediump float;

uniform float uTime;
varying vec2 vTextureCoord;

void main() {
   vec4 newColor = vec4(1.0, 0, 0, 1.0);
	float x = min(vTextureCoord.s, 1.0 - vTextureCoord.s);
	float y = vTextureCoord.t;
	newColor.g = sin(x * cos(uTime*0.0666) * 120.0) + cos(y * sin(uTime*0.1) * 120.0) + sin(sqrt(y * y + x * x) * 40.0);
	gl_FragColor = newColor;
}