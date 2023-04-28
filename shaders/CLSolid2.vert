#version 330
in vec2 inPosition; // input from the vertex buffer

out vec4 outPosition;
out vec3 normala;
out vec2 texCoord;
out vec4 viewPos;
out vec4 lightPositionView;
uniform float typeCLSolid2;
uniform mat4 viewCLSolid2;
uniform  mat4 projectionCLSolid2;
uniform  mat4 scaleMCLSolid2;
uniform  mat4 rotateMCLSolid2;
uniform vec3 lightCLSolid2;
vec3 finalPosition;
vec2 position;
float PI = 3.14159;

vec4 pos4;



vec3 getCLSolid2(vec2 vec){
    // carhesian to cylindirc
    float phi = 2 * PI * vec.x;
    float z = vec.y*5;
    // modification
    float r = 5 * sin(vec.x*100);
    //cylindric to carthesian
    float x = r * cos(phi); //* cos(phi);
    float y = r * sin(phi); //* cos(phi);

    return vec3(x,y,z);
}
vec3 getNormal(vec2 pos) {

    vec3 u = getCLSolid2(pos + vec2(0.0001, 0.)) - getCLSolid2(pos- vec2(0.0001, 0.));
    vec3 v = getCLSolid2(pos + vec2(0., 0.0001)) - getCLSolid2(pos- vec2(0., 0.0001));
    return cross(u, v);
}
void main() {

    position = inPosition;
    finalPosition = vec3(getCLSolid2(position*0.1));
    normala = getNormal(finalPosition.xy);
    normala = mat3(transpose(inverse(mat3(viewCLSolid2 * scaleMCLSolid2 * rotateMCLSolid2)))) * normala;
    vec4 pos4 = vec4(finalPosition,1.0);
    texCoord = inPosition;
    lightPositionView = normalize(viewCLSolid2 * vec4(lightCLSolid2,0.));
    viewPos = normalize(viewCLSolid2 *  pos4);

    gl_Position = projectionCLSolid2 * viewCLSolid2 * scaleMCLSolid2 * rotateMCLSolid2 * pos4;
    outPosition = pos4;

}