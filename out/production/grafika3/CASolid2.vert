#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
out vec4 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float typeCASolid2,scale,coTex,time;
uniform int tim;
uniform mat4 viewCASolid2;
uniform  mat4 projectionCASolid2;
uniform  mat4 scaleMCASolid2;
uniform  mat4 rotateMCASolid2;
vec3 finalPosition;
vec2 position;
float e = 2.71828182846;

vec4 pos4;

vec3 getNormal(vec2 vec){

    normala = vec3(-((4.0 * cos(4.0*vec.x)))/5.0,-(sin(5.0*vec.y)),1.0);
    return normala;
}

vec3 getCASolid2(vec2 vec){
    float z =  (sin(4.0*vec.x)-cos(5.0*vec.y))/5.0;
    return vec3(vec.x,vec.y,z);
}

void main() {
    position = inPosition * 2 -1;
    finalPosition = vec3(getCASolid2(position))*5;
    coTexOut = coTex;
    texCoord = inPosition;
    normala = getNormal(position);
    normala = mat3(transpose(inverse(viewCASolid2 * scaleMCASolid2 * rotateMCASolid2))) * normala;
  //  mat3 ModelView = mat3(viewCASolid2 * scaleMCASolid2 * rotateMCASolid2);
   // normala = mat3(transpose(inverse(ModelView))) * normala;

    vec4 pos4 = vec4(finalPosition,1.0);
    gl_Position = projectionCASolid2 * viewCASolid2 * scaleMCASolid2 * rotateMCASolid2 * pos4;
    outPosition = gl_Position;

}