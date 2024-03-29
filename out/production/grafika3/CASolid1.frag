#version 330
in vec4 outPosition;
in vec2 texCoord;
in vec3 normala;
uniform int surfaceCA1;
uniform vec3 lightCASolid1;
uniform vec3 lightAmbCA1;
uniform vec3 viewPositionCASolid1;
uniform vec3 rMoveXY;
uniform sampler2D textureFire;
uniform int phongPartsCA1;
uniform int reflectorCA1;
uniform float reflectorCA1Angle;
out vec4 outColor; // output from the fragment shader
vec3 res;
float theta;

void main() {
    vec4 position = normalize(outPosition);
    vec4 textureFire = texture(textureFire, texCoord);
    vec3 nNormala = normalize(normala);

 //   if (nMapingCA2 == 1){
   //     nNormala = textureBricksn.xyz;
  //      nNormala = normalize(nNormala * 2.0 - 1.0);
 //   }else {
 //       nNormala = normalize(normala);
 //   };

    float specularStrength = 0.5;
    vec3 viewDir = normalize(normalize(viewPositionCASolid1) - position.rgb);
    vec3 reflectDir = reflect(lightCASolid1, nNormala);
    vec3 lightDir = normalize(lightCASolid1 - (position.rgb+rMoveXY));
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightCASolid1,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
///blinn phong
    vec3 specular = specularStrength * spec * lightAmbCA1;
    vec3 ambient = 0.5 * lightAmbCA1;
    vec3 diffuse = diff * lightAmbCA1;
///reflector
    float cutOff = cos(radians(90.f+reflectorCA1Angle));
    float cutOffOut = cos(radians(100.f+reflectorCA1Angle));
    theta = dot(lightDir, normalize(-viewDir));
//rozmaznuti okraju
    float epsilon   = cutOff - cutOffOut;
    float intensity = clamp((theta - cutOffOut) / epsilon, 0.0, 1.0);

    switch(surfaceCA1) {
        case 0:  //textura

        if (reflectorCA1 == 0) {
            if (phongPartsCA1 == 0) {
            res = attenuation * (ambient + diffuse + specular) * vec3(textureFire.xyz);
            }else if(phongPartsCA1 == 1) {
                res = (ambient) * vec3(textureFire.xyz);
            }else if(phongPartsCA1 == 2) {
                res = (diffuse) * vec3(textureFire.xyz);
            }else if(phongPartsCA1 == 3) {
                res = (specular) * vec3(textureFire.xyz);
            }
            outColor = vec4(res,1.0f);
                                }
        else {

            if (theta > cutOffOut)
            {
                diffuse *= intensity;
                specular *= intensity;
                res = attenuation * (ambient + diffuse + specular) * vec3(textureFire.xyz);
            } else
            {

                res = (ambient) * vec3(textureFire.xyz);
            }
            outColor = vec4(res, 1.0f);
        }
            break;

        case 1: //souradnice
            outColor =  vec4(position.rgb,1.f);
            break;

        case  2: //vzdalenost od pozorovatele
    //    outColor = vec4(vec3(gl_FragCoord.z),1.f);
        outColor =  vec4(vec3(position.rgb-(normalize(viewPositionCASolid1))),1.f);

        break;

        case  3: // barva povrchu
        if (reflectorCA1 == 0) {
            if (phongPartsCA1 == 0) {
                res = attenuation * (ambient + diffuse + specular) * vec3(0.128, 0.28, 0.128);
            } else if (phongPartsCA1 == 1) {
                res = (ambient) * vec3(0.128, 0.28, 0.128);
            } else if (phongPartsCA1 == 2) {
                res = (diffuse) * vec3(0.128, 0.28, 0.128);
            } else if (phongPartsCA1 == 3) {
                res = (specular) * vec3(0.128, 0.28, 0.128);
            }
            outColor = vec4(res, 1.0f);
        }
        else {
        float theta = dot(lightDir, normalize(-viewDir));

        if(theta > cutOffOut)
        {

            diffuse  *= intensity;
            specular *= intensity;
        res = (ambient + diffuse + specular ) * vec3(0.128, 0.28, 0.128);
        }else {

        res = (ambient) * vec3(0.128, 0.28, 0.128);
        }
        outColor = vec4(res,1.0f);
        }
                    break;

        case  4: // normala
            outColor =  vec4(nNormala.rgb,1.0);
            break;

        case  5: // souradnice do textury
            outColor =  vec4(texCoord.xy,0.0,1.0);
            break;

        case  6: //vzdalenost od svetla
            float dist =  distance(normalize(lightCASolid1),position.rgb);
            outColor =  vec4(dist,dist,dist,1.f);
            break;

        case  7: //osvetkeni
            outColor =  vec4(rMoveXY*20,1.0f);
            break;
    }
}