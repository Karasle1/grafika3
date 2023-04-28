#version 330
in vec2 inPosition; // input from the vertex buffer
out vec4 outPosition;
out vec3 normala;
out vec4 viewPos;
out vec4 lightPositionView;
out vec2 texCoord;
uniform mat4 viewSPSolid1;
uniform  mat4 projectionSPSolid1;
uniform  mat4 scaleMSPSolid1;
uniform  mat4 rotateMSPSolid1;
uniform vec3 lightSPSolid1;
vec3 finalPosition;
vec2 position;
float PI = 3.14159, scale1;
vec4 pos4;


vec3 getSPSolid1(vec2 vec){

    float phi = -PI/2 + PI * vec.x;
    float theta = 2 * PI * vec.y;
    float rs = 0.2;

    float x = rs * cos(theta) * cos(phi);
    float y = rs * sin(theta) * cos(phi);
    float z = rs * sin(phi);

  //  normala = normalize(vec3(x,y,z));

 //   float rsp = sqrt((x*x)+(y*y));
 //   rsp = rsp * (cos(10.0f * theta)+1);
    //rsp = 3.0 * sqrt((5.0f*theta)+1);
 //   x= sin(theta)*rsp;
 //   y = cos(theta)*rsp;

    return vec3(x,y,z);
}
vec3 getNormal(vec2 pos) {

    vec3 u = getSPSolid1(pos + vec2(0.0001, 0.)) - getSPSolid1(pos- vec2(0.0001, 0.));
    vec3 v = getSPSolid1(pos + vec2(0., 0.0001)) - getSPSolid1(pos- vec2(0., 0.0001));
    return cross(u, v);
}

void main() {

    position = inPosition;
    texCoord = inPosition;
    finalPosition = vec3(getSPSolid1(position));
    normala = vec3(getNormal(position));
    normala = mat3(transpose(inverse(mat3(viewSPSolid1 * scaleMSPSolid1 * rotateMSPSolid1)))) * normala;
    vec4 pos4 = vec4(finalPosition,1.0);
    lightPositionView = normalize(viewSPSolid1 * vec4(lightSPSolid1,0.));
    viewPos = normalize(viewSPSolid1 *  pos4);


    gl_Position = projectionSPSolid1 * viewSPSolid1 * scaleMSPSolid1 * rotateMSPSolid1 * pos4;
    outPosition = pos4;

}