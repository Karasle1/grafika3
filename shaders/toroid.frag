#version 330
in vec2 outPosition;
out vec4 outColor; // output from the fragment shader
void main() {
    outColor = vec4(outPosition, 1.0, 1.0);
}

