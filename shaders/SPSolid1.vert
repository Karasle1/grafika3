#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
out vec4 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float typeSPSolid1,scale,coTex,time;
uniform int tim;
uniform mat4 viewSPSolid1;
uniform  mat4 projectionSPSolid1;
vec3 finalPosition,tecU, tecV;
vec2 position;
float a = 3,b=1, PI = 3.14159, scale1;
mat3 modelView;
vec4 pos4;

vec3 getSPSolid1(vec2 vec){
    //	position.y += 5;
    position.xy -= 0,5;
    position.xy *= 2;

    float z = 0.1f;
    float r = sqrt((vec.x*vec.x)+(vec.y*vec.y)+(z*z));
    float f = atan(vec.y,vec.x);
    float th = acos(z/r);

    if ((th >= 0 && th <= PI) && (f >= 0 && f <= 2 * PI))
    {
    r = 3 * sqrt(4 * f);
    }

    return vec3(r*sin(th)*cos(f),r*sin(th)*sin(f),r*cos(th));


}

void main() {
  //  outPosition = position;
    position = inPosition;

  //  if (typeSPSolid1 == 5){					//Spikes
  //    if (scale <= 0)
  //    {scale1 = 0;}
   //   else {scale1 = scale;}


      finalPosition = vec3(getSPSolid1(position));
 //finalPosition = vec3(position,0.0f);

      typeShape = 5.0f;

    //}

    coTexOut = coTex;
    vec4 pos4 = vec4(finalPosition,1.0);


    gl_Position = projectionSPSolid1 * viewSPSolid1 * pos4;
    outPosition = gl_Position;

}