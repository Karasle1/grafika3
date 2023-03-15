#version 330
in vec2 outPosition;
in vec2 texCoord;
in float typeShape,attenuation,coTexOut;
in vec3 normala,lightDirection,viewDirection,color;
out vec4 outColor; // output from the fragment shader
//uniform int coTex;
uniform sampler2D textureMosaic,textureBall8,textureBricks,textureFire,texturePavement;
#version 330
in vec4 outPosition;

in vec3 color;
in float attenuation,coTexOut;
flat in int typeShape;
out vec4 outColor; // output from the fragment shader
in vec3 normala,lightDirection,viewDirection;


void main() {



	if (typeShape == 1) {
		outColor.rgb = vec3(0.224f, 0.224f, 0.224f);
		//  outColor.rgb = vec3(color);
		outColor.a = 1.0;

	} else if (typeShape == 2){
		outColor.rgb = vec3(0.24f, 0.124f, 0.124f);
		//  outColor.rgb = vec3(color);
		outColor.a = 1.0;

	}

	else if (typeShape == 3){
		outColor.rgb = vec3(0.124f, 0.24f, 0.124f);
		//  outColor.rgb = vec3(color);
		outColor.a = 1.0;

	}

	else if (typeShape == 4){
		outColor.rgb = vec3(0.124f, 0.124f, 0.24f);
		//  outColor.rgb = vec3(color);
		outColor.a = 1.0;

	}

	//   outColor = vec4(color, 1);
}

/*
void main() {
	vec4 textureFire = texture(textureFire, texCoord);
//	vec4 textureBricks = texture(textureBricks, texCoord);
//	vec4 textureMosaic = texture(textureMosaic, texCoord);
	vec4 textureBall8 = texture(textureBall8, texCoord);
	vec4 texturePavement = texture(texturePavement, texCoord);

	if (typeShape == 0) {
		outColor = texturePavement;
	}
	//else if
//	(typeShape == 1) {
//		vec3 nNormala = normalize(normala);
//		float f = dot(normalize(vec3(0.0, 1.0, 1.0)), nNormala);
//		f = max(f, 0.0);
//		outColor.rgb = vec3(f);
//		outColor.a = 1.0;

//		if (coTexOut == 0){
//			outColor =  outColor +  vec4(outPosition.rg, 0.0, 1.0);

//		}
//		else
//		{
//		outColor =  textureBricks;
//		}

//	} else if
//	(typeShape == 2) {
	//	vec3 nNormala = normalize(normala);
//		float f = dot(normalize(vec3(0.0, 1.0, 1.0)), nNormala);
//		f = max(f, 0.0);
//		outColor.rgb = vec3(f);
//		outColor.a = 1.0;

//		if (coTexOut == 0){
		//	outColor = outColor * vec4(outPosition.rg, 0.0, 1.0);
//			outColor = vec4(nNormala,1.0);
//		}
//		else
//		{
	//		outColor = outColor * textureMosaic;
//		}

//	}
else if
	(typeShape == 3) {

		vec3 nNormala = normalize(normala);
		float f = dot(normalize(vec3(0.0, 1.0, 1.0)), nNormala);
		f = max(f, 0.0);
		outColor.rgb = vec3(f);
		outColor.a = 1.0;

		if (coTexOut == 0){
			outColor = outColor * vec4(outPosition.rg, 0.0, 1.0);
		}
		else
		{
			outColor = outColor * textureFire;
		}

		// outColor = textureFire;
	} else if
	(typeShape == 4) {

		vec3 nNormala = normalize(normala);
		float f = dot(normalize(vec3(0.0, 1.0, 1.0)), nNormala);
		f = max(f, 0.0);


		outColor.rgb = vec3(f);
		outColor.a = 1.0;
		if (coTexOut == 0){
		//	outColor =  outColor +  vec4(outPosition.rg, 0.0, 1.0);
			outColor = vec4(nNormala,1.0);
		}
		else
		{
			outColor = outColor + textureBall8;
		}

	//outColor = textureBall8;

	}
	else {outColor = vec4(0.0, 0.0, 1.0, 1.0); }
} */
