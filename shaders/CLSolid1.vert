#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
out vec4 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float typeCLSolid1,scale,coTex,time;
uniform int tim;
uniform mat4 viewCLSolid1;
uniform  mat4 projectionCLSolid1;
uniform  mat4 scaleMCLSolid1;
uniform  mat4 rotateMCLSolid1;
vec3 finalPosition;
vec2 position;
float PI = 3.14159, scale1;
// mat3 modelView;
vec4 pos4;

vec3 getCLSolid1(vec2 vec){
    // carhesian to cylindirc
    float phi = 2 * PI * vec.x;
    float z = vec.y*5;
    // modification
  //  float r = 5 * sin(vec.x*100);
    //cylindric to carthesian
    float x = 3 * cos(phi); //* cos(phi);
    float y = 3 * sin(phi); //* cos(phi);

    normala = normalize(vec3(x,y,z));

  return vec3(x,y,z);

}

void main() {

    position = inPosition;

    finalPosition = vec3(getCLSolid1(position));
    coTexOut = coTex;
    texCoord = inPosition;
    vec4 pos4 = vec4(finalPosition,1.0);

    gl_Position = projectionCLSolid1 * viewCLSolid1 * scaleMCLSolid1 * rotateMCLSolid1 * pos4;
    outPosition = gl_Position;

}