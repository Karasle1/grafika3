#version 330
in vec2 inPosition; // input from the vertex buffer
uniform float coTex;
uniform mat4 viewCASolid1;
uniform  mat4 projectionCASolid1;
uniform  mat4 scaleMCASolid1;
uniform  mat4 rotateMCASolid1;
uniform vec3 lightAmbCA1;
uniform vec3 lightCASolid1;
uniform int phongPartsCA1;
out float coTexOut;
out vec3 normala;
out vec2 texCoord;
out vec4 outPosition;

vec3 finalPosition;
vec2 position;
vec4 pos4;

vec3 getNormal(vec2 vec){
    vec3 normal;
    normal = vec3( (1.0f/(sqrt((50.0f)*vec.x*vec.x + (50.0f)*vec.y*vec.y))*(25.0f*vec.x*sin(sqrt((50.0f)*vec.x*vec.x + (50.0f)*vec.y*vec.y)))),
                   (1.0f/(sqrt((50.0f)*vec.x*vec.x + (50.0f)*vec.y*vec.y))*(25.0f*vec.y*sin(sqrt((50.0f)*vec.x*vec.x + (50.0f)*vec.y*vec.y)))),1.0f);
    return normal;
}
vec3 getCASolid1(vec2 vec){
    float z =  0.5*cos(sqrt((50.0)*vec.x*vec.x + (50.0)*vec.y*vec.y));
    return vec3(vec.x,vec.y,z);
}

void main() {

    position = inPosition * 2 -1;
    finalPosition = vec3(getCASolid1(position))*5;
    coTexOut = coTex;
    texCoord = inPosition;
    normala = getNormal(position);
    normala = mat3(transpose(inverse(mat3(viewCASolid1 * scaleMCASolid1 * rotateMCASolid1)))) * normala;

    vec4 pos4 = vec4(finalPosition,1.0);

    gl_Position = projectionCASolid1 * viewCASolid1 * scaleMCASolid1 * rotateMCASolid1 * pos4;
    outPosition = pos4;

}