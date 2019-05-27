#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColour[4];//light source's color (which can change)
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void){

 	vec4 blendMapColour = texture(blendMap, pass_textureCoordinates);
 	
 	float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
 	vec2 tiledCoords = pass_textureCoordinates * 40;
 	vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
 	vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
 	vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
 	vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;
 	vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;

	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 unitNormal  = normalize(surfaceNormal);

	vec3 totalDiffuse;
	vec3 totalSpecular;

	for (int i = 0; i < 4;i++){
		vec3 unitLightVector = normalize(toLightVector[i]);// pointing from the surface to the light source
		float nDot1 = dot(unitNormal, unitLightVector);
		float brightness = max(nDot1, 0.2);
		vec3 lightDirection = -unitLightVector;// pointing from the light source to the surface
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiffuse = brightness*lightColour[i];
		totalSpecular = dampedFactor * reflectivity * lightColour[i];

	}


	out_Color = vec4(diffuse, 1.0) * totalColour  + vec4(finalSpecular, 1.0);
	out_Color = mix(vec4(skyColour, 1.0), out_Color, visibility);
	
	
}