#version 330
in vec2 inPosition; // input from the vertex buffer
out vec2 outPosition;

uniform mat4 view;
uniform mat4 projection;

float a = 3,b=1;

vec4 pos4;

void main() {
    vec2 position = inPosition * 7 -6;
    outPosition = position;


   float  x = cos(position.y)*(a + b*cos(position.x));
   float  y = sin(position.y)*(a + b*cos(position.x));
   float  z = b*sin(position.x);

     pos4 = vec4(x,y,z,1.0);


    gl_Position = projection * view * pos4;

}
