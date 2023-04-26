#version 330
in vec4 outPosition;
in vec2 texCoord;
in vec3 normala;
in vec4 viewPos;
in vec4 lightPositionView;
uniform int surfaceCA2;
uniform vec3 lightCASolid2;
uniform vec3 lightAmbCA2;
uniform vec3 viewPositionCASolid2;
uniform sampler2D textureBricks;
uniform sampler2D textureBricksn;
uniform int phongPartsCA2;
uniform int reflectorCA2;
uniform int nMapingCA2;
in float typeShape;
out vec4 outColor; // output from the fragment shader
vec3 res;
float theta;
vec3 nNormala;

void main() {
    vec4 position = normalize(outPosition);
    vec4 textureBricks = texture(textureBricks, texCoord);
    vec4 textureBricksn = texture(textureBricksn, texCoord);
 //
   if (nMapingCA2 == 1){
        nNormala = textureBricksn.xyz;
        nNormala = normalize(nNormala * 2.0 - 1.0);
    }else {
      nNormala = normalize(normala);
        };
    float specularStrength = 0.5;
    vec3 viewDir = (viewPos.xyz - position.xyz);
    vec3 reflectDir = reflect(lightPositionView.xyz, nNormala);
    vec3 lightDir = (lightPositionView.xzy - viewPos.xyz);;
    float diff = max(dot(nNormala, lightDir),0.0);
    float  attenuation = clamp( 10.0 /distance(lightPositionView.xyz,vec3(position.xyz)), 0.0, 1.0);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(nNormala, halfwayDir), 0.0), 0.32);
    ///blinn phong
    vec3 specular = specularStrength * spec * lightAmbCA2;
    vec3 ambient = 0.5 * lightAmbCA2;
    vec3 diffuse = diff * lightAmbCA2;
    ///reflector
    float cutOff = cos(radians(90.f));
    float cutOffOut = cos(radians(100.f));
    theta = dot(lightDir, normalize(-viewDir));
    //rozmaznuti okraju
    float epsilon   = cutOff - cutOffOut;
    float intensity = clamp((theta - cutOffOut) / epsilon, 0.0, 1.0);

    switch(surfaceCA2) {
        case 0:  //textura

            if (reflectorCA2 == 0) {
                if (phongPartsCA2 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(textureBricks.xyz);
                }else if(phongPartsCA2 == 1) {
                    res = (ambient) * vec3(textureBricks.xyz);
                }else if(phongPartsCA2 == 2) {
                    res = (diffuse) * vec3(textureBricks.xyz);
                }else if(phongPartsCA2 == 3) {
                    res = (specular) * vec3(textureBricks.xyz);
                }
                outColor = vec4(res,1.0f);
            }
            else {

                if (theta > cutOffOut)
                {
                    diffuse *= intensity;
                    specular *= intensity;
                    res = attenuation * (ambient + diffuse + specular) * vec3(textureBricks.xyz);
                } else
                {

                    res = (ambient) * vec3(textureBricks.xyz);
                }
                outColor = vec4(res, 1.0f);
            }
            break;

        case 1: //souradnice
            outColor =  vec4(-position.x*10,-position.y*10,position.z,1.f);
            break;

        case  2: //vzdalenost od pozorovatele
    //    outColor = vec4(vec3(gl_FragCoord.z),1.f);
            outColor =  vec4(vec3(position.rgb-(normalize(viewPositionCASolid2))),1.f);

            break;

        case  3: // barva povrchu
            if (reflectorCA2 == 0) {
                if (phongPartsCA2 == 0) {
                    res = attenuation * (ambient + diffuse + specular) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCA2 == 1) {
                    res = (ambient) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCA2 == 2) {
                    res = (diffuse) * vec3(0.128, 0.28, 0.128);
                } else if (phongPartsCA2 == 3) {
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
                    res = attenuation *  (ambient + diffuse + specular ) * vec3(0.128, 0.28, 0.128);
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
         //   outColor =  vec4(nMapingCA2*20,0.f,0.f,1.0f);
            break;
    }
}