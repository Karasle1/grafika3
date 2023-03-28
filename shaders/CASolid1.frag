#version 330
in vec4 outPosition;
in vec2 texCoord;
in vec3 normala;
uniform int surfaceCA1;
uniform vec3 lightCASolid1;
uniform vec3 lightAmbCA1;
uniform vec3 viewPositionCASolid1;
uniform sampler2D textureFire;
uniform int phongPartsCA1;
uniform int reflectorCA1;
in float typeShape;
out vec4 outColor; // output from the fragment shader
 vec3 res;
float theta;

void main() {
    vec4 position = normalize(outPosition);
    vec4 textureFire = texture(textureFire, texCoord);
    vec3 nNormala = normalize(normala);
 //   nNormala = texture(normalMap, texCoord;

 //   nNormala = normalize(nNormala * 2.0 - 1.0);

    float specularStrength = 0.5;
    vec3 viewDir = normalize(normalize(viewPositionCASolid1) - position.rgb);
    vec3 reflectDir = reflect(lightCASolid1, nNormala);
    vec3 lightDir = normalize(lightCASolid1 - position.rgb);
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightCASolid1,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
///blinn phong
    vec3 specular = specularStrength * spec * lightAmbCA1;
    vec3 ambient = 0.5 * lightAmbCA1;
    vec3 diffuse = diff * lightAmbCA1;
///reflector
    float cutOff = cos(radians(90.f));
    float cutOffOut = cos(radians(100.f));
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
            outColor =  vec4(position.rgb, 1.0);
            break;

        case  2: //vzdalenost od pozorovatele
            float distp =  distance(vec3(position.xyz), normalize(viewPositionCASolid1));
            outColor = vec4(distp,distp,distp,1.f);
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
        res = (ambient + diffuse ) * vec3(0.128, 0.28, 0.128);
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
            float dist =  distance(vec3(position.xyz), normalize(lightCASolid1));
            outColor =  vec4(dist,dist,dist,1.f);
            break;

        case  7: //osvetkeni
            outColor =  vec4(attenuation*20,0.f,0.f,1.0f);
            break;
    }
}