#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
out vec4 outPosition;
out vec3 normala;
out vec2 texCoord;
uniform float typeCLSolid2;
uniform mat4 viewCLSolid2;
uniform  mat4 projectionCLSolid2;
uniform  mat4 scaleMCLSolid2;
uniform  mat4 rotateMCLSolid2;
vec3 finalPosition;
vec2 position;
float PI = 3.14159;

vec4 pos4;

vec3 getCLSolid2(vec2 vec){
    // carhesian to cylindirc
    float phi = 2 * PI * vec.x;
    float z = vec.y*5;
    // modification
    float r = 5 * sin(vec.x*100);
    //cylindric to carthesian
    float x = r * cos(phi); //* cos(phi);
    float y = r * sin(phi); //* cos(phi);

    normala = normalize(vec3(x,y,z));

    return vec3(x,y,z);

}

void main() {

    position = inPosition;
    finalPosition = vec3(getCLSolid2(position));
    texCoord = inPosition;
    vec4 pos4 = vec4(finalPosition,1.0);

    gl_Position = projectionCLSolid2 * viewCLSolid2 * scaleMCLSolid2 * rotateMCLSolid2 * pos4;
    outPosition = pos4;

}