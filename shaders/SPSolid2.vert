#version 330
in vec2 inPosition; // input from the vertex buffer
out vec4 outPosition;
out vec3 normala;
out vec4 viewPos;
out vec4 lightPositionView;
out vec2 texCoord;
uniform float typeSPSolid2;
uniform mat4 viewSPSolid2;
uniform  mat4 projectionSPSolid2;
uniform  mat4 scaleMSPSolid2;
uniform  mat4 rotateMSPSolid2;
uniform vec3 lightSPSolid2;
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

     return vec3(x,y,z);
}
vec3 getNormal(vec2 pos) {

    vec3 u = getSPSolid2(pos + vec2(0.0001, 0.)) - getSPSolid2(pos- vec2(0.0001, 0.));
    vec3 v = getSPSolid2(pos + vec2(0., 0.0001)) - getSPSolid2(pos- vec2(0., 0.0001));
    return cross(u, v);
}

void main() {
    position = inPosition;
    texCoord = inPosition;
    finalPosition = vec3(getSPSolid2(position));
    normala = getNormal(position);
    normala = mat3(transpose(inverse(mat3(viewSPSolid2 * scaleMSPSolid2 * rotateMSPSolid2)))) * normala;
    vec4 pos4 = vec4(finalPosition,1.0);
    lightPositionView = normalize(viewSPSolid2 * vec4(lightSPSolid2,0.));
    viewPos = normalize(viewSPSolid2 *  pos4);

    gl_Position = projectionSPSolid2 * viewSPSolid2 * scaleMSPSolid2 * rotateMSPSolid2 * pos4;
    outPosition = pos4;

}