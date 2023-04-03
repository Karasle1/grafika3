#version 330
in vec2 inPosition; // input from the vertex buffer
out vec4 outPosition;
out vec3 normala;
out vec2 texCoord;
uniform float typeSPSolid2;
uniform mat4 viewSPSolid2;
uniform  mat4 projectionSPSolid2;
uniform  mat4 scaleMSPSolid2;
uniform  mat4 rotateMSPSolid2;
vec3 finalPosition;
vec2 position;
float PI = 3.14159, scale1;
vec4 pos4;

vec3 getSPSolid2(vec2 vec){

	float phi = -PI/2 + PI * vec.x;
	float theta = 2 * PI * vec.y;
	float rs = 1.0 * sqrt((5.0f*theta)+1);

	float x = rs * cos(theta) * cos(phi);
	float  y = rs * sin(theta) * cos(phi);
	float z = rs * sin(phi);

    normala = normalize(vec3(x,y,z));
     return vec3(x,y,z);

}

void main() {
    position = inPosition;

    finalPosition = vec3(getSPSolid2(position));
    texCoord = inPosition;
    vec4 pos4 = vec4(finalPosition,1.0);

    gl_Position = projectionSPSolid2 * viewSPSolid2 * scaleMSPSolid2 * rotateMSPSolid2 * pos4;
    outPosition = pos4;

}