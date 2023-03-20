#version 330
in vec4 outPosition;
in vec2 texCoord;
flat in int surf;
flat in vec3 lightAnbientF;

in float typeShape,attenuation,coTexOut;
out vec4 outColor; // output from the fragment shader
uniform sampler2D textureFire;


in vec3 normala,lightDirection,viewDirection,color;
 vec3 res;



void main() {

    vec4 textureFire = texture(textureFire, texCoord);
    vec3 nNormala = normalize(normala);
    float f = dot(normalize(vec3(0.0, 1.0, 1.0)), nNormala);
    f = max(f, 0.0);
    vec3 ambient = 0.5 * lightAnbientF;

    vec4 position = normalize(outPosition);
    switch(surf) {
        case  0:  //textura
            res = ambient * vec3(textureFire.xyz);
        outColor = vec4(res,1.0f);
            break;
        case 1: //souradnice
            outColor =  vec4(position.rgb, 1.0);
            break;
        case  2: //vzdalenost od pozorovatele
            outColor =  vec4(position.r, 0.0,0.0,1.0);
            break;
        case  3: // barva povrchu
            outColor =  vec4(0.128,0.28,0.128,1.0);
          res = ambient * vec3(0.128,0.28,0.128);
            outColor =  vec4(res,1.0f);
            break;
        case  4: // normala
            outColor =  vec4(nNormala.rgb,1.0);
        vec3 res = ambient * nNormala.rgb;
            outColor =  vec4(res,1.0f);
            break;
        case  5: // souradnice do textury
            outColor =  vec4(texCoord.xy,0.0,1.0);
            break;
        case  6: //vzdalenost od svetla
            float dist =  distance(vec3(position.x,position.y,position.z), vec3(0.5,0.5,0.5));
            outColor =  vec4(dist,dist,dist,1.f);
            break;
        case  7: //osvetkeni
         //   outColor =  (ambient,1.0f) * outColor;
            break;


    }


}