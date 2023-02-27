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

float getJuicer(vec2 vec){
	return  0.5*cos(sqrt((5+time*10)*position.x*position.x + (5+time*10)*position.y*position.y));


}

//float getWave(vec2 vec){
//	position.y += 5;

	//tecne vektory u a v
//	float tecUecU=position.y;
//	float tecVY=position.x;
//	normala = cross (tecU,tecV);

//	return position.x*position.y;

//}
vec3 getNormal(vec2 vec){

	vec3 normal;
	float dist = sqrt((position.x * position.x) + (position.y * position.y) );

	normal.x = 1 * PI * sin(dist/(dist*position.x));
	normal.y = 1 * PI * sin(dist/(dist*position.y));
	normal.z = 1.0;

	return normal;

}

//vec3 getToroid(vec2 vec){

//	a = (a+time) * scale;
//	b = b * scale;
//	float  x = cos(position.y)*(a + b*cos(position.x))-10.0;
//	float  y = sin(position.y)*(a + b*cos(position.x));
//	float  z = b*sin(position.x)+1.0;

	// tecne vektory pomoci parcialni derivace

//	tecU.x = (-b * cos(position.y) * sin(position.x));
//	tecU.y = (-b * sin(position.y) * sin(position.x));
//	tecU.z = (b * cos(position.x));
//
//	tecV.x = (-sin(position.y)) * (a+b * cos(position.x));
//	tecV.y = (a + (b*cos(position.x))) * cos(position.y);
//	tecV.z = 0.f;

//	normala = cross(tecU, tecV);

//	return vec3(x, y, z);
//}

vec3 getSphere(vec2 vec){
	position.y += 5;
	float az = vec.x * PI;
	float ze = vec.y * PI;
	float r = 1 + time + scale;
	float x = r * cos(az)* cos(ze);
	float y = 1 * r * sin(az) * cos(ze);
	float z = 0.f;

	tecU.x = (-r * sin(az) * cos(ze));
	tecU.y = (r * cos(az) * cos(ze));
	tecU.z = (r * cos(ze));

	tecV.x = (-r * cos(az) * sin(ze));
	tecV.y = (-r * sin(az) * sin(ze));
	tecV.z = (r * cos(ze));

	normala = cross(tecU, tecV);


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
//	else if (type == 1){					//wave
//		if (scale <= 0)
//		{scale1 = 0;}
//		else {scale1 = scale;}
//		position = inPosition * 2 * scale1;
//		finalPosition = vec3(position,getWave(position));
//		finalPosition.x -=5;
//		finalPosition.y +=7;
//		outPosition = position;
//		texCoord = inPosition;
//		typeShape = 1;

//	}
//	else if (type == 2) {										//toroid
//		position = inPosition * 7 -6;
//		position.y -= 10;
//		position.x -= 10;
//		finalPosition = getToroid(position).xyz;
	//	normala = getNormal(inPosition);
//		outPosition = inPosition;
//		texCoord = inPosition;
//		typeShape = 2;



//	}
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
	//	normala = getNormal(position);
		finalPosition.x+=5;
		finalPosition.y+=5;
		outPosition = position;
		texCoord = inPosition;
		typeShape = 4;

	}
	//else if (type == 6) {										//nevim este co

	//	position = inPosition * 2 -1;
	//	position.x -=5;
	//	position.y -=5;
	//	finalPosition = vec3(position, getJuicer(position));
	//	outPosition = position;
	//	texCoord = inPosition;
	//	typeShape = 0;

//	}


//	viewDirection = (-finalPosition);
//	lightDirection = (lightPosition - finalPosition) ;
//	float dis = length(lightDirection);
//	float attenuation = (1.0 / (1.0 + (0.01 * distance * distance)));

	coTexOut = coTex;
	vec4 pos4 = vec4(finalPosition,1.0);
	//gl_NormalMatrix  = view * ;
//	normala = transpose(inverse(normal))*normala;
	gl_Position = projection * view * pos4;
} 
