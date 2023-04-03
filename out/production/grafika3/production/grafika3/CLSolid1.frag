#version 330
in vec4 outPosition2;
in vec2 texCoord;
in vec3 normala;
uniform int surfaceCL1;
uniform vec3 lightCLSolid1;
uniform vec3 lightAmbCL1;
uniform vec3 viewPositionCLSolid1;
uniform sampler2D textureMosaic;
uniform int phongPartsCL1;
uniform int reflectorCL1;
uniform float reflectorCL1Angle;
out vec4 outColor; // output from the fragment shader
vec3 res;
float theta;

void main() {
    vec4 position = normalize(outPosition2);
    vec4 textureMosaic = texture(textureMosaic, texCoord);
    vec3 nNormala = normalize(normala);
    //   nNormala = texture(normalMap, texCoord;

    //   nNormala = normalize(nNormala * 2.0 - 1.0);

    float specularStrength = 0.5;
    vec3 viewDir = normalize(normalize(viewPositionCLSolid1) - position.rgb);
    vec3 reflectDir = reflect(lightCLSolid1, nNormala);
    vec3 lightDir = normalize(lightCLSolid1 - position.rgb);
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightCLSolid1,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
    ///blinn phong
    vec3 specular = specularStrength * spec * lightAmbCL1;
    vec3 ambient = 0.5 * lightAmbCL1;
    vec3 diffuse = diff * lightAmbCL1;
    ///reflector
    float cutOff = cos(radians(90.f+reflectorCL1Angle));
    float cutOffOut = cos(radians(100.f+reflectorCL1Angle));
    theta = dot(lightDir, normalize(-viewDir));
    //rozmaznuti okraju
    float epsilon   = cutOff - cutOffOut;
    float intensity = clamp((theta - cutOffOut) / epsilon, 0.0, 1.0);

    switch(surfaceCL1) {
        case 0:  //textura

            if (reflectorCL1 == 0) {
                if (phongPartsCL1 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(textureMosaic.xyz);
                }else if(phongPartsCL1 == 1) {
                    res = (ambient) * vec3(textureMosaic.xyz);
                }else if(phongPartsCL1 == 2) {
                    res = (diffuse) * vec3(textureMosaic.xyz);
                }else if(phongPartsCL1 == 3) {
                    res = (specular) * vec3(textureMosaic.xyz);
                }
                outColor = vec4(res,1.0f);
            }
            else {

                if (theta > cutOffOut)
                {
                    diffuse *= intensity;
                    specular *= intensity;
                    res = attenuation * (ambient + diffuse + specular) * vec3(textureMosaic.xyz);
                } else
                {

                    res = (ambient) * vec3(textureMosaic.xyz);
                }
                outColor = vec4(res, 1.0f);
            }
            break;

        case 1: //souradnice
            outColor =  vec4(position.rgb,1.f);
            break;

        case  2: //vzdalenost od pozorovatele
    //    outColor = vec4(vec3(gl_FragCoord.z),1.f);
            outColor =  vec4(vec3(position.rgb-(normalize(viewPositionCLSolid1))),1.f);
            break;

        case  3: // barva povrchu
            if (reflectorCL1 == 0) {
                if (phongPartsCL1 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCL1 == 1) {
                    res = (ambient) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCL1 == 2) {
                    res = (diffuse) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCL1 == 3) {
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
                    res = attenuation * (ambient + diffuse + specular) * vec3(0.128, 0.28, 0.128);
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
            float dist =  distance(normalize(lightCLSolid1),position.rgb);
            outColor =  vec4(dist,dist,dist,1.f);
             break;

        case  7: //osvetkeni
     //       outColor =  vec4(reflectorCA1Angle*20,0.f,0.f,1.0f);
            break;
    }
}
