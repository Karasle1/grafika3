#version 330
in vec4 outPosition;
in vec2 texCoord;
in float typeShape,attenuation,coTexOut;
//in vec2 outPosition;
out vec4 outColor; // output from the fragment shader
uniform sampler2D texturePavement;
in vec3 normala,lightDirection,viewDirection,color;


void main() {
    vec4 texturePavement = texture(texturePavement, texCoord);
    vec3 nNormala = normalize(normala);
    float f = dot(normalize(vec3(0.0, 1.0, 1.0)), nNormala);
    f = max(f, 0.0);


 if (coTexOut == 0){
        outColor =  vec4(nNormala.rgb, 1.0);
   }
    else
    {
        outColor = texturePavement;
    }
}