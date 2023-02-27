#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;

out vec2 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float typeWave,scale,coTex,time;
uniform int tim;
uniform mat4 viewWave;
uniform  mat4 projectionWave;
vec3 finalPosition,tecU, tecV;
vec2 position;
float a = 3,b=1, PI = 3.14159, scale1;
mat3 modelView;
vec4 pos4;

float getWave(vec2 position){
    //	position.y += 5;

    //tecne vektory u a v
    float tecUecU=position.y;
    float tecVY=position.x;
    normala = cross (tecU,tecV);

    return position.x*position.y;

}

void main() {
  //  outPosition = position;

    if (typeWave == 1){					//wave
        if (scale <= 0)
        {scale1 = 0;}
        else {scale1 = scale;}
         position = inPosition;
         position = position * 2 * scale1;
         finalPosition = vec3(position,getWave(position));
         finalPosition.x -=5;
         finalPosition.y +=7;
         outPosition = position;
         texCoord = inPosition;
    //     typeShape = 1.0f;

    }

    coTexOut = coTex;
    vec4 pos4 = vec4(finalPosition,1.0);


    gl_Position = projectionWave * viewWave * pos4;

}