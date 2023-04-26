#version 330
in vec4 outPosition;
in vec2 texCoord;
in vec3 normala;
in vec4 viewPos;
in vec4 lightPositionView;
uniform int surfaceCL2;
uniform vec3 lightCLSolid2;
uniform vec3 lightAmbCL2;
uniform vec3 viewPositionCLSolid2;
uniform sampler2D textureMosaic;
//uniform sampler2D textureBricksn;
uniform int phongPartsCL2;
uniform int reflectorCL2;
out vec4 outColor; // output from the fragment shader
vec3 res;
float theta;

void main() {
    vec4 position = normalize(outPosition);
    vec4 textureMosaic = texture(textureMosaic, texCoord);
 //   vec4 textureBricksn = texture(textureBricksn, texCoord);
       vec3 nNormala = normalize(normala);
//    vec3 nNormala = textureBricksn.xyz;

    nNormala = normalize(nNormala * 2.0 - 1.0);

    float specularStrength = 0.5;
    vec3 viewDir = (viewPos.xyz - position.xyz);
    vec3 reflectDir = reflect(lightPositionView.xyz, nNormala);
    vec3 lightDir = (lightPositionView.xzy - viewPos.xyz);
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightPositionView.xyz,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
    ///blinn phong
    vec3 specular = specularStrength * spec * lightAmbCL2;
    vec3 ambient = 0.5 * lightAmbCL2;
    vec3 diffuse = diff * lightAmbCL2;
    ///reflector
    float cutOff = cos(radians(90.f));
    float cutOffOut = cos(radians(100.f));
    theta = dot(lightDir, normalize(-viewDir));
    //rozmaznuti okraju
    float epsilon   = cutOff - cutOffOut;
    float intensity = clamp((theta - cutOffOut) / epsilon, 0.0, 1.0);

    switch(surfaceCL2) {
        case 0:  //textura

            if (reflectorCL2 == 0) {
                if (phongPartsCL2 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(textureMosaic.xyz);
                }else if(phongPartsCL2 == 1) {
                    res = (ambient) * vec3(textureMosaic.xyz);
                }else if(phongPartsCL2 == 2) {
                    res = (diffuse) * vec3(textureMosaic.xyz);
                }else if(phongPartsCL2 == 3) {
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
            outColor =  vec4(position.xyz, 1.0);
            break;

        case  2: //vzdalenost od pozorovatele
            float distp =  distance(vec3(position.xyz), normalize(viewPositionCLSolid2));
            outColor = vec4(distp,distp,distp,1.f);
            break;

        case  3: // barva povrchu
            if (reflectorCL2 == 0) {
                if (phongPartsCL2 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCL2 == 1) {
                    res = (ambient) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCL2 == 2) {
                    res = (diffuse) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCL2 == 3) {
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
         //   outColor =  vec4(attenuation*20,0.f,0.f,1.0f);
            break;
    }
}