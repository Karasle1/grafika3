#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;


out vec4 outPosition;
out vec3 lightDirection,viewDirection,color;

out float attenuation,coTexOut;
out int typeShape;

uniform int  typeSpikes;
uniform mat4 viewSpikes;
uniform  mat4 projectionSpikes;
vec3 finalPosition;
vec2 position;
vec4 pos4;

vec3 getBox(vec2 vec){

    position = inPosition;

    return vec3(position.x * 15, position.y * 15, 0.1f);

}

vec3 getBox1(vec2 vec){

    position = inPosition;

    return vec3(position.x * 15, 0.0f, position.y * 15);

}

vec3 getBox2(vec2 vec){

    position = inPosition;

    return vec3(0.0f, position.x * 15, position.y * 15);

}

vec3 getBox3(vec2 vec){

    position = inPosition;

    return vec3(position.x * 15,15.0f,position.y * 15 );

}

void main() {


if(typeSpikes == 1) {
    position = inPosition;
    typeShape = 1;
    finalPosition = vec3(getBox(position));
} else if (typeSpikes == 2) {
    position = inPosition;
    typeShape = 2;
    finalPosition = vec3(getBox1(position));
} else if (typeSpikes == 3) {
    position = inPosition;
    typeShape = 3;
    finalPosition = vec3(getBox2(position));
} else if (typeSpikes == 4) {
    position = inPosition;
    typeShape = 4;
    finalPosition = vec3(getBox3(position));
}


    vec4 pos4 = vec4(finalPosition,1.0);

      gl_Position = projectionSpikes * viewSpikes * pos4;
    outPosition = gl_Position;
     color = finalPosition;

}