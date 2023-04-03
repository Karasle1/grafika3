#version 330
in vec2 inPosition; // input from the vertex buffer
uniform float typeCLSolid1;
uniform mat4 viewCLSolid1;
uniform  mat4 projectionCLSolid1;
uniform  mat4 scaleMCLSolid1;
uniform  mat4 rotateMCLSolid1;
out vec4 outPosition2;
out vec3 normala;
out vec2 texCoord;
vec3 finalPosition;
float PI = 3.14159;
vec4 pos4;

vec3 getNormal(vec3 vec){

    float z = vec.z;
    float x = cos(vec.x); //* cos(phi);
    float y = vec.y; //* cos(phi);

    normala = normalize(vec3(x,y,z));

    return normala;
}

vec3 getCLSolid1(vec2 vec){
    // carhesian to cylindirc
    float phi = 2 * PI * vec.x;

    float z = vec.y*5;

    //cylindric to carthesian
    float x = 3 * cos(phi); //* cos(phi);
   float y = 3 * sin(phi); //* cos(phi);

  return vec3(x,y,z);

}

void main() {



    finalPosition = vec3(getCLSolid1(inPosition));
    texCoord = inPosition;
    vec4 pos4 = vec4(finalPosition,1.0);

    normala = getNormal(finalPosition);
    normala = mat3(transpose(inverse(mat3(viewCLSolid1 * scaleMCLSolid1 * rotateMCLSolid1)))) * normala;

    gl_Position = projectionCLSolid1 * viewCLSolid1 * scaleMCLSolid1 * rotateMCLSolid1 * pos4;
    outPosition2 = pos4;

}