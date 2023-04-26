#version 330
in vec4 outPosition;
in vec2 texCoord;
in vec3 normala;
in vec4 viewPos;
in vec4 lightPositionView;
uniform int surfaceSP2;
uniform vec3 lightSPSolid2;
uniform vec3 lightAmbSP2;
uniform vec3 viewPositionSPSolid2;
uniform sampler2D textureFire;
uniform int phongPartsSP2;
uniform int reflectorSP2;
uniform float reflectorSP2Angle;
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
    vec3 viewDir = (viewPos.xyz - position.xyz);
    vec3 reflectDir = reflect(lightPositionView.xyz, nNormala);
    vec3 lightDir = (lightPositionView.xzy - viewPos.xyz);
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightPositionView.xyz,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
    ///blinn phong
    vec3 specular = specularStrength * spec * lightAmbSP2;
    vec3 ambient = 0.5 * lightAmbSP2;
    vec3 diffuse = diff * lightAmbSP2;
    ///reflector
    float cutOff = cos(radians(90.f+reflectorSP2Angle));
    float cutOffOut = cos(radians(100.f+reflectorSP2Angle));
    theta = dot(lightDir, normalize(-viewDir));
    //rozmaznuti okraju
    float epsilon   = cutOff - cutOffOut;
    float intensity = clamp((theta - cutOffOut) / epsilon, 0.0, 1.0);

    switch(surfaceSP2) {
        case 0:  //textura

            if (reflectorSP2 == 0) {
                if (phongPartsSP2 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(textureFire.xyz);
                }else if(phongPartsSP2 == 1) {
                    res = (ambient) * vec3(textureFire.xyz);
                }else if(phongPartsSP2 == 2) {
                    res = (diffuse) * vec3(textureFire.xyz);
                }else if(phongPartsSP2 == 3) {
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
            outColor = vec4(gl_FragCoord);
            break;

        case  3: // barva povrchu
            if (reflectorSP2 == 0) {
                if (phongPartsSP2 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsSP2 == 1) {
                    res = (ambient) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsSP2 == 2) {
                    res = (diffuse) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsSP2 == 3) {
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
            float dist =  distance(lightPositionView.xyz,position.rgb);
            outColor =  vec4(dist,dist,dist,1.f);
            break;

        case  7: //test
         //   outColor =  vec4(reflectorSP2Angle*20,0.f,0.f,1.0f);
            break;
    }
}