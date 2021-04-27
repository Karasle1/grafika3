#version 150
in vec2 outPosition;
in vec2 texCoord;
in float typeShape,attenuation;
in vec3 normala,lightDirection,viewDirection;
out vec4 outColor; // output from the fragment shader
uniform sampler2D textureMosaic,textureBall8,textureBricks,textureFire,texturePavement;
void main() {
	vec4 textureFire = texture(textureFire, texCoord);
	vec4 textureBricks = texture(textureBricks, texCoord);
	vec4 textureMosaic = texture(textureMosaic, texCoord);
	vec4 textureBall8 = texture(textureBall8, texCoord);
	vec4 texturePavement = texture(texturePavement, texCoord);

	if (typeShape == 0) {
		outColor = texturePavement;
	} else if
	(typeShape == 1) {
		outColor = textureBricks;
	} else if
	(typeShape == 2) {
	vec3 nNormala = normalize(normala);
		float f = dot(normalize(vec3(0.0,1.0,1.0)),nNormala);
		f = max(f,0.0);


		outColor.rgb = vec3(f);//textureMosaic;
		outColor.a = 1.0;
		outColor = outColor * textureMosaic;

	}else if
	(typeShape == 3) {

		vec3 nNormala = normalize(normala);
		float f = dot(normalize(vec3(0.0,1.0,1.0)),nNormala);
		f = max(f,0.0);


		outColor.rgb = vec3(f);
		outColor.a = 1.0;
		outColor = outColor * textureFire;

		// outColor = textureFire;
	}else if
	(typeShape == 4) {

		vec3 nNormala = normalize(normala);
		float f = dot(normalize(vec3(0.0,1.0,1.0)),nNormala);
		f = max(f,0.0);


		outColor.rgb = vec3(f);
		outColor.a = 1.0;
		outColor = outColor + textureBall8;
		//outColor = textureBall8;
	}
	else {outColor = vec4(0.0, 0.0, 1.0, 1.0); }
} 
