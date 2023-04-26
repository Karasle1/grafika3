#version 330
in vec4 outPosition;
in vec3 texCoord;
uniform samplerCube textureSky;
out vec4 outColor; // output from the fragment shader


void main() {
	vec4 textureSky = texture(textureSky, texCoord);
//	outColor.rgb = vec3(outPosition.xyz);
	outColor.rgb = textureSky.xyz;

	outColor.a = 1.0;
}
