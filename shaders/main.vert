#version 330
in vec2 inPosition; // input from the vertex buffer

uniform mat4 view;
uniform  mat4 projection;
uniform vec3 light;

out vec4 outPosition;

vec3 finalPosition;
vec2 position;
vec4 pos4;
float PI = 3.14159;


vec3 getLight(vec2 vec){

	//vec = vec + light.xy;
	float az = vec.x  * PI;
	float ze = vec.y  * PI;
	float r = 1;
	float x = r * cos(az)* cos(ze);
	float y = r * sin(az) * cos(ze);
	float z = r * sin(ze);

	return vec3(x,y,z);

}


void main() {


	finalPosition = getLight(inPosition);
	vec4 pos4 = vec4(finalPosition + light,1.0);

	gl_Position = projection * view * pos4;
	outPosition = gl_Position;


}
