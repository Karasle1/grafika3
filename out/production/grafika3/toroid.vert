#version 330
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;

out vec2 outPosition;
out vec3 normala,lightDirection,viewDirection,color;
out vec2 texCoord;
out float typeShape,attenuation,coTexOut;
uniform float type,scale,coTex,time;
uniform int tim;
uniform mat4 view;
uniform mat4 projection;
vec3 finalPosition,tecU, tecV;
vec2 position;
float a = 3,b=1, PI = 3.14159, scale1;
mat3 modelView;
vec4 pos4;

void main() {

    gl_Position = projection * view * pos4;

    vec3 getToroid(vec2 vec){

    	a = (a+time) * scale;
    	b = b * scale;
    	float  x = cos(position.y)*(a + b*cos(position.x))-10.0;
    	float  y = sin(position.y)*(a + b*cos(position.x));
    	float  z = b*sin(position.x)+1.0;

    	// tecne vektory pomoci parcialni derivace

    	tecU.x = (-b * cos(position.y) * sin(position.x));
    	tecU.y = (-b * sin(position.y) * sin(position.x));
    	tecU.z = (b * cos(position.x));

    	tecV.x = (-sin(position.y)) * (a+b * cos(position.x));
    	tecV.y = (a + (b*cos(position.x))) * cos(position.y);
    	tecV.z = 0.f;

    	normala = cross(tecU, tecV);

    	return vec3(x, y, z);

}
