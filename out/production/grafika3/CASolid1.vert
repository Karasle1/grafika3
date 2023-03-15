#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;

out vec4 outPosition;
out vec3 normala,lightDirection,viewDirection;
out vec2 texCoord;
out float attenuation,coTexOut;
uniform float time;
uniform float coTex;
uniform mat4 viewCASolid1;
uniform  mat4 projectionCASolid1;
uniform  mat4 scaleMCASolid1;
uniform  mat4 rotateMCASolid1;
vec3 finalPosition;
vec2 position;

vec4 pos4;

vec3 getCASolid1(vec2 vec){
    float z =  0.5*cos(sqrt((5+time*10)*vec.x*vec.x + (5+time*10)*vec.y*vec.y));

    normala = normalize(vec3(vec.x,vec.y,z));

    return vec3(vec.x,vec.y,z);

}

void main() {

   position = inPosition * 2 -1;

    finalPosition = vec3(getCASolid1(position))*5;
    coTexOut = coTex;
    texCoord = inPosition;

    vec4 pos4 = vec4(finalPosition,1.0);

    gl_Position = projectionCASolid1 * viewCASolid1 * scaleMCASolid1 * rotateMCASolid1 * pos4;
    outPosition = gl_Position;

}