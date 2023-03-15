#version 330
in vec4 outPosition;
in vec2 texCoord;
in float typeShape,attenuation,coTexOut;
out vec4 outColor; // output from the fragment shader
uniform sampler2D textureFire;
in vec3 normala,lightDirection,viewDirection,color;


void main() {

    vec4 textureFire = texture(textureFire, texCoord);
    vec3 nNormala = normalize(normala);
    float f = dot(normalize(vec3(0.0, 1.0, 1.0)), nNormala);
    f = max(f, 0.0);
 //   outColor.rgb = vec3(nNormala.rgb);
  //  outColor.a = 1.0;

  if (coTexOut == 1){
      //  outColor =  vec4(outPosition.rgb, 1.0);
      outColor =  vec4(nNormala.rgb, 1.0);
   }
    else
    {
        outColor =  textureFire;
    }
}