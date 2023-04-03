#version 330
in vec4 outPosition;
in vec2 texCoord;
in vec3 normala;
uniform int surfaceSP1;
uniform vec3 lightSPSolid1;
uniform vec3 lightAmbSP1;
uniform vec3 viewPositionSPSolid1;
uniform sampler2D texturePavement;
uniform int phongPartsSP1;
uniform int reflectorSP1;
uniform float reflectorSP1Angle;
out vec4 outColor; // output from the fragment shader
vec3 res;
float theta;

void main() {
    vec4 position = normalize(outPosition);
    vec4 texturePavement = texture(texturePavement, texCoord);
    vec3 nNormala = normalize(normala);
    //   nNormala = texture(normalMap, texCoord;

    //   nNormala = normalize(nNormala * 2.0 - 1.0);

    float specularStrength = 0.5;
    vec3 viewDir = normalize(normalize(viewPositionSPSolid1) - position.rgb);
    vec3 reflectDir = reflect(lightSPSolid1, nNormala);
    vec3 lightDir = normalize(lightSPSolid1 - position.rgb);
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightSPSolid1,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
    ///blinn phong
    vec3 specular = specularStrength * spec * lightAmbSP1;
    vec3 ambient = 0.5 * lightAmbSP1;
    vec3 diffuse = diff * lightAmbSP1;
    ///reflector
    float cutOff = cos(radians(90.f+reflectorSP1Angle));
    float cutOffOut = cos(radians(100.f+reflectorSP1Angle));
    theta = dot(lightDir, normalize(-viewDir));
    //rozmaznuti okraju
    float epsilon   = cutOff - cutOffOut;
    float intensity = clamp((theta - cutOffOut) / epsilon, 0.0, 1.0);

    switch(surfaceSP1) {
        case 0:  //textura

            if (reflectorSP1 == 0) {
                if (phongPartsSP1 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(texturePavement.xyz);
                }else if(phongPartsSP1 == 1) {
                    res = (ambient) * vec3(texturePavement.xyz);
                }else if(phongPartsSP1 == 2) {
                    res = (diffuse) * vec3(texturePavement.xyz);
                }else if(phongPartsSP1 == 3) {
                    res = (specular) * vec3(texturePavement.xyz);
                }
                outColor = vec4(res,1.0f);
            }
            else {

                if (theta > cutOffOut)
                {
                    diffuse *= intensity;
                    specular *= intensity;
                    res = attenuation * (ambient + diffuse + specular) * vec3(texturePavement.xyz);
                } else
                {

                    res = (ambient) * vec3(texturePavement.xyz);
                }
                outColor = vec4(res, 1.0f);
            }
            break;

        case 1: //souradnice
            outColor =  vec4(position.rgb, 1.0);
            break;

        case  2: //vzdalenost od pozorovatele
            outColor = vec4(vec3(gl_FragCoord.z), 1.0);
            break;

        case  3: // barva povrchu
            if (reflectorSP1 == 0) {
                if (phongPartsSP1 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsSP1 == 1) {
                    res = (ambient) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsSP1 == 2) {
                    res = (diffuse) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsSP1 == 3) {
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
            float dist =  distance(vec3(position.xyz), normalize(lightSPSolid1));
            outColor =  vec4(dist,dist,dist,1.f);
            break;

        case  7: //osvetkeni
            outColor =  vec4(reflectorSP1Angle*20,0.f,0.f,1.0f);
            break;
    }
}