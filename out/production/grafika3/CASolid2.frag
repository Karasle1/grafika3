#version 330
in vec4 outPosition;
in vec2 texCoord;
in vec3 normala;
uniform int surfaceCA2;
uniform vec3 lightCASolid2;
uniform vec3 lightAmbCA2;
uniform vec3 viewPositionCASolid2;
uniform sampler2D textureFire;
in float typeShape;
out vec4 outColor; // output from the fragment shader
vec3 res;

void main() {
    vec4 position = normalize(outPosition);
    vec4 textureFire = texture(textureFire, texCoord);
    vec3 nNormala = normalize(normala);
    float specularStrength = 0.5;
    vec3 viewDir = normalize(normalize(viewPositionCASolid2) - position.rgb);
    vec3 reflectDir = reflect(lightCASolid2, nNormala);
    vec3 lightDir = normalize(lightCASolid2 - position.rgb);
    float diff = max(dot(nNormala, lightDir),0.0);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 0.32);
    vec3 specular = specularStrength * spec * lightAmbCA2;

    vec3 ambient = 0.3 * lightAmbCA2;
    vec3 diffuse = diff * lightAmbCA2;

    switch(surfaceCA2) {
        case  0:  //textura
            res = (ambient+diffuse+specular) * vec3(textureFire.xyz);
            outColor = vec4(res,1.0f);
            break;
        case 1: //souradnice
            outColor =  vec4(position.rgb, 1.0);
            break;
        case  2: //vzdalenost od pozorovatele
            float distp =  distance(viewPositionCASolid2, position.rgb);
            outColor =  normalize(vec4(distp,distp,distp,1.f));

            break;
        case  3: // barva povrchu
            outColor =  vec4(0.128,0.28,0.128,1.0);
            res = (ambient+diffuse+specular) * vec3(0.128,0.28,0.128);
            outColor =  vec4(res,1.0f);
            break;
        case  4: // normala
            outColor =  vec4(nNormala.rgb,1.0);
            break;
        case  5: // souradnice do textury
            outColor =  vec4(texCoord.xy,0.0,1.0);
            break;
        case  6: //vzdalenost od svetla
            float dist =  distance(vec3(position.x,position.y,position.z), lightCASolid2);
            outColor =  vec4(dist,dist,dist,1.f);
            break;
        case  7: //osvetkeni
            outColor =  vec4(lightCASolid2,1.0f);
            break;
    }
}