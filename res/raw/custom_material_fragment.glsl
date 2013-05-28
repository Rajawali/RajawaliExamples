precision mediump float;

uniform float uTime;
varying vec2 vTextureCoord;

void main() {
	float x = vTextureCoord.s;
	float y = vTextureCoord.t;
	float time = 2.0 + sin(uTime);
	float v1 = sqrt(y * y + x * x);
	float color = sin(x * cos(time*0.666) * 120.0) - cos(y * sin(time*0.1) * 120.0) + sin(v1 * 10.0);
	color += cos(y * sin(time * 3.0)) + sin(time * 2.0);
	gl_FragColor = vec4(cos(v1 * sin(x)), cos(color * time), 0, 1.0);
}
