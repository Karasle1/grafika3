#version 330
in vec3 inPosition; // input from the vertex buffer
uniform mat4 view;
uniform  mat4 projection;
uniform vec3 light;
out vec4 outPosition;
out vec3 texCoord;
vec3 finalPosition;
vec2 position;
vec4 pos4;
float PI = 3.14159;


//vec3 getLight(vec2 vec){

	//vec = vec + light.xy;
//	float az = vec.x  * PI;
	//float ze = vec.y  * PI;
//	float r = 1;
	//float x = r * cos(az)* cos(ze);
	//float y = r * sin(az) * cos(ze);
	//float z = r * sin(ze);
	//return vec3(x,y,z);

//}

void main() {

	finalPosition = inPosition*2-1;
	texCoord = finalPosition;
//	vec4 pos4 = vec4(finalPosition +  light,1.0);
	vec4 pos4 = vec4(finalPosition,1.0);
	gl_Position = projection * view * pos4;
//	gl_Position = pos4;
	outPosition = pos4;
}
