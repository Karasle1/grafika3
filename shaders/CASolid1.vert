#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
in vec3 lightAmb;
out vec4 outPosition;
out vec3 lightAmbientF;
out vec3 normala,lightDirection,viewDirection;
out vec2 texCoord;
out float attenuation,coTexOut;
flat out int surf;
uniform float time;
uniform float coTex;
uniform mat4 viewCASolid1;
uniform  mat4 projectionCASolid1;
uniform  mat4 scaleMCASolid1;
uniform  mat4 rotateMCASolid1;
uniform  int surface;

vec3 finalPosition;
vec2 position;

vec4 pos4;

vec3 getCASolid1(vec2 vec){
    float z =  0.5*cos(sqrt((50.0)*vec.x*vec.x + (50.0)*vec.y*vec.y));

 //   normala = normalize(vec3(vec.x,vec.y,z));

    return vec3(vec.x,vec.y,z);

}


vec3 getNormal(vec3 vec){

    vec3 normal;
    float dist = sqrt((position.x * position.x) + (position.y * position.y) );

    normal.xy = normalize(vec.xy);
   // normal.y = vec.y;
    normal.z = (1.0/(sqrt((50.0)*vec.x*vec.x + (50.0)*vec.y*vec.y)))*(25.0*vec.x*sin(sqrt((50.0)*vec.x*vec.x + (50.0)*vec.y*vec.y)));

    return normal;

}


void main() {

   position = inPosition * 2 -1;

    finalPosition = vec3(getCASolid1(position))*5;
    coTexOut = coTex;
    texCoord = inPosition;
    surf = surface;
   lightAmbientF = lightAmb;
    normala = getNormal(finalPosition);

    vec4 pos4 = vec4(finalPosition,1.0);

    gl_Position = projectionCASolid1 * viewCASolid1 * scaleMCASolid1 * rotateMCASolid1 * pos4;
    outPosition = pos4;

}