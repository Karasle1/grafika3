#version 330

in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;

out vec4 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float type,scale,coTex,time;
uniform int tim;
uniform mat4 view;
uniform mat4 projection;
vec3 finalPosition,tecU, tecV;
vec2 position;
float a = 3,b=1, PI = 3.14159, scale1;
mat3 modelView;


vec3 getNormal(vec2 vec){

	vec3 normal;
	float dist = sqrt((position.x * position.x) + (position.y * position.y) );

	normal.x = 1 * PI * sin(dist/(dist*position.x));
	normal.y = 1 * PI * sin(dist/(dist*position.y));
	normal.z = 1.0;

	return normal;

}


/*vec3 getSphere(vec2 vec){
    float az = vec.x * PI;
	float ze = vec.y * PI;
	float r = 1 + time + scale;
	float x = r * cos(az)* cos(ze);
	float y = 1 * r * sin(az) * cos(ze);
	float z = r * sin(ze);

	tecU.x = (-r * sin(az) * cos(ze));
	tecU.y = (r * cos(az) * cos(ze));
	tecU.z = (r * cos(ze));

	tecV.x = (-r * cos(az) * sin(ze));
	tecV.y = (-r * sin(az) * sin(ze));
	tecV.z = (r * cos(ze));

	normala = cross(tecU, tecV);

	//	float rs = sqrt((x * x)+(y*y)*(z*z));
		float rs = 5;
    	float f = atan(y,x);
        float t = acos(z/rs);


	return vec3((rs * sin(t) * cos(f)),(rs*sin(t)*sin(f)),(rs*cos(t)));
//	return vec3(x,y,z);
} */


vec3 getBox(vec2 vec){

	position = inPosition;

	return vec3(position.x * 15, position.y * 15, 0.1f);

}

vec3 getBox1(vec2 vec){

	position = inPosition;

	return vec3(position.x * 15, 0.0f, position.y * 15);

}

vec3 getBox2(vec2 vec){

	position = inPosition;

	return vec3(0.0f, position.x * 15, position.y * 15);

}

vec3 getBox3(vec2 vec){

	position = inPosition;

	return vec3(position.x * 15,15.0f,position.y * 15 );

}

void main() {


	if(typeSpikes == 1) {
		position = inPosition;
		typeShape = 1;
		finalPosition = vec3(getBox(position));
	} else if (typeSpikes == 2) {
		position = inPosition;
		typeShape = 2;
		finalPosition = vec3(getBox1(position));
	} else if (typeSpikes == 3) {
		position = inPosition;
		typeShape = 3;
		finalPosition = vec3(getBox2(position));
	} else if (typeSpikes == 4) {
		position = inPosition;
		typeShape = 4;
		finalPosition = vec3(getBox3(position));
	}

	vec4 pos4 = vec4(finalPosition,1.0);

	gl_Position = projection * view * pos4;
	outPosition = gl_Position;
	color = finalPosition;

}




/*void main() {
	outPosition = position;

		position = inPosition*50;
		position.y -= 25;
		position.x -= 25;
		finalPosition = vec3(position,0.0);
	//	outPosition = vec2(1.0,0.0);


		position = inPosition*100;
		position.y -= 50;
		position.x -= 50;
		finalPosition = vec3(position,0.0);
		texCoord = inPosition;

	coTexOut = coTex;
	vec4 pos4 = vec4(finalPosition,1.0);
	//gl_NormalMatrix  = view * ;
//	normala = transpose(inverse(normal))*normala;
	gl_Position = projection * view * pos4;
} */
