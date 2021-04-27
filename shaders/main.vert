#version 150
in vec2 inPosition; // input from the vertex buffer
in vec3 lightPosition;
out vec2 outPosition;
out vec3 normala,lightDirection,viewDirection;
out vec2 texCoord;
out float typeShape,attenuation;
uniform float wave;
uniform float type;
uniform mat4 view;
uniform mat4 projection;
vec3 finalPosition,tecU, tecV;
vec2 position;
float a = 3,b=1, PI = 3.14159;

float getJuicer(vec2 vec){
	return  0.5*cos(sqrt(20*position.x*position.x + 20*position.y*position.y));
}

float getWave(vec2 vec){
//	position.y -= 5.0;

	//tecne vektory u a v
	float parcX=position.y;
	float parcY=position.x;

	return position.x*position.y;

}
vec3 getNormal(vec2 vec){

	vec3 normal;
	float dist = sqrt((position.x * position.x) + (position.y * position.y) );
	normal.x = 1 * PI * sin(dist/(dist*position.x));
	normal.y = 1 * PI * sin(dist/(dist*position.y));
	normal.z = 1.0;
	return normal;

}

vec3 getToroid(vec2 vec){
	float  x = cos(position.y)*(a + b*cos(position.x))-10.0;
	float  y = sin(position.y)*(a + b*cos(position.x));
	float  z = b*sin(position.x)+1.0;

	// tecne vektory pomoci parcialni derivace

	//tecU.x = dFdx(cos(position.y)*(a + b*cos(position.x))-10.0);
	//(-b * cos(position.y) * sin(position.x));
	//tecU.y = dFdx(sin(position.y)*(a + b*cos(position.x)));
	//(-b * sin(position.y) * sin(position.x));
//	tecU.z = dFdx(b*sin(position.x)+1.0);
	//(b * cos(position.x));

	//tecV.x = dFdy(cos(position.y)*(a + b*cos(position.x))-10.0);
	//(-sin(position.y)) * (a+b * cos(position.x));
	//tecV.y = dFdy(sin(position.y)*(a + b*cos(position.x)));
	// (a + (b*cos(position.x))) * cos(position.y);
//	tecV.z = dFdy(b*sin(position.x)+1.0);
	//0.f;
	// Normala u x v
//	normala = tecU * tecV;


	return vec3(x, y, z);
}

vec3 getSphere(vec2 vec){
	position.y += 5;
	float az = vec.x * PI;
	float ze = vec.y * PI;
	float r = 1;
	float x = r * cos(az)* cos(ze);
	float y = 1 *r * sin(az) * cos(ze);
	float z = 1 * r * sin(ze);
	return vec3(x,y,z);
}

void main() {
	outPosition = position;

	if (type ==  0){  											// grid
		position = inPosition*50;
		position.y -= 25;
		position.x -= 25;
		finalPosition = vec3(position,0.0);
	//	outPosition = vec2(1.0,0.0);
		typeShape = 0;

	}
	if (type ==  0){  											// grid
		position = inPosition*100;
		position.y -= 50;
		position.x -= 50;
		finalPosition = vec3(position,0.0);
		texCoord = inPosition;

//	outPosition = vec2(1.0,0.0);
		typeShape = 0;

	}
	else if (type == 1){										//wave
		 position = inPosition * 2 -1;

		finalPosition = vec3(position,getWave(position));
		finalPosition.x -=5;
		finalPosition.y +=7;
		outPosition = position;
		texCoord = inPosition;
		typeShape = 1;

	}
	else if (type == 2) {										//toroid
		position = inPosition * 7 -6;
		position.y -= 10;
		position.x -= 10;
		finalPosition = getToroid(position);
		normala = getNormal(position);
		outPosition = position;
		texCoord = inPosition;
		typeShape = 2;

	}
	else if (type == 3) {										//juicer

		position = inPosition * 2 -1;
		finalPosition = vec3(position, getJuicer(position));
		normala = getNormal(position);
		finalPosition.x -= 3;
		finalPosition.y -= 5;
		outPosition = position;
		texCoord = inPosition;
		typeShape = 3;

	}
	else if (type == 4) {										//ball

		position = inPosition * 2 -1;
		finalPosition = getSphere(position);
		normala = getNormal(position);
		finalPosition.x+=5;
		finalPosition.y+=5;
		outPosition = position;
		texCoord = inPosition;
		typeShape = 4;

	}else if (type == 6) {										//nevim este co

		position = inPosition * 2 -1;
		position.x -=5;
		position.y -=5;
		finalPosition = vec3(position, getJuicer(position));
		outPosition = position;
		texCoord = inPosition;
		typeShape = 0;

	}


//	viewDirection = (-finalPosition);
//	lightDirection = (lightPosition - finalPosition) ;
//	float dis = length(lightDirection);
//	float attenuation = (1.0 / (1.0 + (0.01 * distance * distance)));


	vec4 pos4 = vec4(finalPosition,1.0);
	gl_Position = projection * view * pos4;
} 
