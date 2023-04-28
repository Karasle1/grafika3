#version 330
in vec4 outPosition;
in vec3 normala;
in vec4 viewPos;
in vec4 lightPositionView;
in vec2 texCoord;

uniform int surfaceCA1;
uniform vec3 lightCASolid1;
uniform vec3 lightAmbCA1;
uniform vec3 viewPositionCASolid1;
uniform vec3 rMoveXY;
//uniform sampler2D textureFire;
uniform samplerCube textureSky;
uniform int phongPartsCA1;
uniform int reflectorCA1;
uniform float reflectorCA1Angle;

out vec4 outColor; // output from the fragment shader
vec3 res;
float theta;

void main() {
    vec4 position = normalize(outPosition);
 //   vec4 textureFire = texture(textureFire, texCoord);
    vec3 nNormala = normalize(normala);

    vec3 viewVec = normalize(position.xyz - viewPos.xyz);
    vec3 textureVec = reflect(-viewVec, nNormala);
    vec4 textureS = texture(textureSky, textureVec);

    float specularStrength = 0.9;
    vec3 viewDir = (viewPos.xyz - position.xyz);
    vec3 reflectDir = reflect(lightPositionView.xyz, nNormala);
    vec3 lightDir = (lightPositionView.xzy - viewPos.xyz);
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightPositionView.xyz,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
///blinn phong
    vec3 specular = specularStrength * spec * lightAmbCA1;
    vec3 ambient = 0.5 * lightAmbCA1;
    vec3 diffuse = diff * lightAmbCA1;
///reflector
    float cutOff = cos(radians(10.f+reflectorCA1Angle));
    float cutOffOut = cos(radians(20.f+reflectorCA1Angle));
    theta = dot(lightDir, normalize(-viewDir));
//rozmaznuti okraju
    float epsilon   = cutOff - cutOffOut;
    float intensity = clamp((theta - cutOffOut) / epsilon, 0.0, 1.0);

    switch(surfaceCA1) {
        case 0:  //textura

        if (reflectorCA1 == 0) {
            if (phongPartsCA1 == 0) {
            res = attenuation * (ambient + diffuse + specular) * vec3(textureS.xyz);
            }else if(phongPartsCA1 == 1) {
                res = (ambient) * vec3(textureS.xyz);
            }else if(phongPartsCA1 == 2) {
                res = (diffuse) * vec3(textureS.xyz);
            }else if(phongPartsCA1 == 3) {
                res = (specular) * vec3(textureS.xyz);
            }
            outColor = vec4(res,1.0f);
                                }
        else {

            if (theta > cutOffOut)
            {
                diffuse *= intensity;
                specular *= intensity;
                res = attenuation * (ambient + diffuse + specular) * vec3(textureS.xyz);
            } else
            {

                res = (ambient) * vec3(textureS.xyz);
            }
            outColor = vec4(res, 1.0f);
        }
            break;

        case 1: //souradnice
            outColor =  vec4(position.rgb,1.f);
            break;

        case  2: //vzdalenost od pozorovatele
    //    outColor = vec4(vec3(gl_FragCoord.z),1.f);
        outColor =  vec4(vec3(position.rgb-(viewPos.xyz)),1.f);

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
            float dist = distance(lightPositionView.xyz,position.rgb);
            outColor =  vec4(dist,dist,dist,1.f);
            break;

        case  7: //test
       outColor = vec4((attenuation * (ambient + diffuse + specular) * vec3(textureS.xyz)),1.0f);
     //   outColor = textureS;
            break;
    }
}