#version 330
in vec2 outPosition;
out vec4 outColor; // output from the fragment shader

void main() {
	outColor.rgb = vec3(1.f, 1.f, 1.f);
	outColor.a = 1.0;
}
