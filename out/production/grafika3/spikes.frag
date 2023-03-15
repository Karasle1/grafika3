#version 330
in vec4 outPosition;

in vec3 color;
in float attenuation,coTexOut;
flat in int typeShape;
out vec4 outColor; // output from the fragment shader
in vec3 normala,lightDirection,viewDirection;


void main() {



    if (typeShape == 1) {
        outColor.rgb = vec3(0.224f, 0.224f, 0.224f);
        //  outColor.rgb = vec3(color);
        outColor.a = 1.0;

    } else if (typeShape == 2){
        outColor.rgb = vec3(0.24f, 0.124f, 0.124f);
        //  outColor.rgb = vec3(color);
        outColor.a = 1.0;

    }

    else if (typeShape == 3){
        outColor.rgb = vec3(0.124f, 0.24f, 0.124f);
        //  outColor.rgb = vec3(color);
        outColor.a = 1.0;

    }

    else if (typeShape == 4){
        outColor.rgb = vec3(0.124f, 0.124f, 0.24f);
        //  outColor.rgb = vec3(color);
        outColor.a = 1.0;

    }

 //   outColor = vec4(color, 1);
}