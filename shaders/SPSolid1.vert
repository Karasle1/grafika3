#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
out vec4 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float typeSPSolid1,scale,coTex,time;
uniform int tim;
uniform mat4 viewSpikes;
uniform  mat4 projectionSpikes;
vec3 finalPosition,tecU, tecV;
vec2 position;
float a = 3,b=1, PI = 3.14159, scale1;
mat3 modelView;
vec4 pos4;

vec3 getSPSolid1(vec2 vec){
    //	position.y += 5;


    float z = 0.1f;
    float r = sqrt((vec.x*vec.x)+(vec.y*vec.y)+(z*z));
    float f = atan(vec.y,vec.x);
    float th = acos(z/r);

    if ((th >= 0 && th <= PI) && (f >= 0 && f <= 2 * PI))
    {
    r = 3 * sqrt(4 * f);
    }

    return vec3(r,f,th);
}

void main() {
  //  outPosition = position;
    position = inPosition;

    if (typeSPSolid1 == 5){					//Spikes
      if (scale <= 0)
      {scale1 = 0;}
      else {scale1 = scale;}


      finalPosition = vec3(getSPSolid1(position));

      typeShape = 6.0f;

    }

    coTexOut = coTex;
    vec4 pos4 = vec4(finalPosition,1.0);


    gl_Position = projectionSpikes * viewSpikes * pos4;
    outPosition = gl_Position;

}