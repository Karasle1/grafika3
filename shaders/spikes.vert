#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
in int gl_vertexID;

out vec2 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float typeSpikes,scale,coTex,time;
uniform int tim;
uniform mat4 viewSpikes;
uniform  mat4 projectionSpikes;
vec3 finalPosition,tecU, tecV;
vec2 position;
float a = 3,b=1, PI = 3.14159, scale1;
mat3 modelView;
vec4 pos4;

vec2 getWave(vec2 vec){
    	position.y += 5;

    //tecne vektory u a v
    float tecUecU=position.y;
    float tecVY=position.x;
    normala = cross (tecU,tecV);
    position = inPosition;

    return position;

}

void main() {
    outPosition = position;

    if (typeSpikes == 2){					//Spikes
      if (scale <= 0)
      {scale1 = 0;}
      else {scale1 = scale;}

      position = inPosition; //* 2 * scale1;
          //finalPosition = inPosition  * 2 * scale1;
      finalPosition = vec3(position,getWave(position));
     if ( gl_vertexID > 102 && (gl_vertexID % 2) == 0  ){
        finalPosition.y == 200;
          }
                                         //  finalPosition.x -=5;
                                        //   finalPosition.y +=7;
      outPosition = position;
      texCoord = inPosition;
                                           //     typeShape = 1.0f;

    }

    coTexOut = coTex;
    vec4 pos4 = vec4(finalPosition,1.0);


    gl_Position = projectionSpikes * viewSpikes * pos4;

}