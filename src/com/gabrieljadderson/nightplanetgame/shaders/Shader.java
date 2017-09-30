package com.gabrieljadderson.nightplanetgame.shaders;

//public class Shader {
//	
//    SpriteBatch sb = new SpriteBatch(100);
//
//	Texture texture, texture_n;
//	boolean flipY;
//	Texture normalBase;
//	Matrix4 transform = new Matrix4();
//	Random rnd = new Random();
//
//	// position of our light
//	final Vector3 DEFAULT_LIGHT_POS = new Vector3(0f, 0f, 0.020f);
//	// the color of our light
////	final Vector3 DEFAULT_LIGHT_COLOR = new Vector3(1f, 0.7f, 0.6f);
//	Vector3 DEFAULT_LIGHT_COLOR = new Vector3(0.2f, 0.0f, 0.6f);
//	// the ambient color (color to use when unlit)
//	Vector3 DEFAULT_AMBIENT_COLOR = new Vector3(0.0f, 0.0f, 1f);
//	// the attenuation factor: x=constant, y=linear, z=quadratic
////	final Vector3 DEFAULT_ATTENUATION = new Vector3(0.4f, 3f, 20f);
//	final Vector3 DEFAULT_ATTENUATION = new Vector3(0.8f, 3f, 20f);
//	// the ambient intensity (brightness to use when unlit)
//	final float DEFAULT_AMBIENT_INTENSITY = 5.0f;
////	final float DEFAULT_AMBIENT_INTENSITY = 1.2f;
//	final float DEFAULT_STRENGTH = 0.075f;
////	final float DEFAULT_STRENGTH = 1.075f;
//
//	final Color NORMAL_VCOLOR = new Color(1f,1f,1f,DEFAULT_STRENGTH);
//
//	// the position of our light in 3D space
//	public Vector3 lightPos = new Vector3(DEFAULT_LIGHT_POS);
//	// the resolution of our game/graphics
//	Vector2 resolution = new Vector2();
//	// the current attenuation
//	Vector3 attenuation = new Vector3(DEFAULT_ATTENUATION);
//	// the current ambient intensity
//	float ambientIntensity = DEFAULT_AMBIENT_INTENSITY;
//	float strength = DEFAULT_STRENGTH;
//
//	// whether to use attenuation/shadows
//	boolean useShadow = true;
//
//	// whether to use lambert shading (with our normal map)
//	boolean useNormals = true;
//	
//	public float dx = 0f, dy = 0f, dz = 0f; //used for multiplying the light position in the render method
//
//	DecimalFormat DEC_FMT = new DecimalFormat("0.00000");
//
//	ShaderProgram program;
//
//	BitmapFont font;
//
//	private int texWidth, texHeight;
//	private Texture diffise, normalMap;
//	
//	float time = 0;
//
//	private Vector3 rndColor() {
//		return new Vector3(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
//	}
//	
//	public Shader(Texture diffuse, Texture normalMap) {
//		diffise = diffuse;
//		normalMap = normalMap;
//		
//		texture = diffise;
//		texture_n = normalMap;
//		
//		//we only use this to show what the strength-adjusted normal map looks like on screen
//		Pixmap pix = new Pixmap(1, 1, Format.RGB565); 
//		pix.setColor(0.5f, 0.5f, 1.0f, 1.0f); 
//		pix.fill();
//		normalBase = new Texture(pix);
//		
//		texWidth = texture.getWidth();
//		texHeight = texture.getHeight();
//		
//
//		// create our shader program...
//		program = createShader();
//
//		// now we create our sprite batch for our shader
////		fxBatch = new SpriteBatch(100, program);
//		// setShader is needed; perhaps this is a LibGDX bug?
////		sb.setProjectionMatrix(GameConstants.camera.combined);
////		sb.setTransformMatrix(transform);
////		sb.setProjectionMatrix(GameConstants.camera.combined);
//		sb.setShader(program);
////		fxBatch.setShader(program);
//		
//	}
//
//	private ShaderProgram createShader() {
//		// see the code here: http://pastebin.com/7fkh1ax8
//		// simple illumination model using ambient, diffuse (lambert) and attenuation
//		// see here: http://nccastaff.bournemouth.ac.uk/jmacey/CGF/slides/IlluminationModels4up.pdf
//		String vert = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
//				+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
//				+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
//				+ "uniform mat4 u_proj;\n" //
//				+ "uniform mat4 u_trans;\n" //
//				+ "uniform mat4 u_projTrans;\n" //
//				+ "varying vec4 v_color;\n" //
//				+ "varying vec2 v_texCoords;\n" //
//				+ "\n" //
//				+ "void main()\n" //
//				+ "{\n" //
//				+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
//				+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
//				+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
//				+ "}\n";
//
//		
//		String frag = "#ifdef GL_ES\n" +
//				"precision mediump float;\n" +
//				"#endif\n" +
//				"varying vec4 v_color;\n" +
//				"varying vec2 v_texCoords;\n" +
//				
//				"uniform sampler2D u_texture;\n" +
//				"uniform sampler2D u_normals;\n" +
//				"uniform vec3 light;\n" + 
//				"uniform vec3 ambientColor;\n" + 
//				"uniform float ambientIntensity; \n" + 
//				"uniform vec2 resolution;\n" + 
//				"uniform vec3 lightColor;\n" + 
//				"uniform bool useNormals;\n" + 
//				"uniform bool useShadow;\n" + 
//				"uniform vec3 attenuation;\n" + 
//				"uniform float strength;\n" +
//				"uniform bool yInvert;\n"+ 
//				"\n" + 
//				"void main() {\n" +
//				"	//sample color & normals from our textures\n" +
//				"	vec4 color = texture2D(u_texture, v_texCoords.st);\n" +
//				"	vec3 nColor = texture2D(u_normals, v_texCoords.st).rgb;\n\n" +
//				"	//some bump map programs will need the Y value flipped..\n" +
//				"	nColor.g = yInvert ? 1.0 - nColor.g : nColor.g;\n\n" +
//				"	//this is for debugging purposes, allowing us to lower the intensity of our bump map\n" +
//				"	vec3 nBase = vec3(0.5, 0.5, 1.0);\n" +
//				"	nColor = mix(nBase, nColor, strength);\n\n" +
//				"	//normals need to be converted to [-1.0, 1.0] range and normalized\n" +
//				"	vec3 normal = normalize(nColor * 2.0 - 1.0);\n\n" +
//				"	//here we do a simple distance calculation\n" +
//				"	vec3 deltaPos = vec3( (light.xy - gl_FragCoord.xy) / resolution.xy, light.z );\n\n" +
//				"	vec3 lightDir = normalize(deltaPos);\n" + 
//				"	float lambert = useNormals ? clamp(dot(normal, lightDir), 0.0, 1.0) : 1.0;\n" + 
//				"	\n" + 
//				"	//now let's get a nice little falloff\n" + 
//				"	float d = sqrt(dot(deltaPos, deltaPos));"+ 
//				"	\n" + 
//				"	float att = useShadow ? 1.0 / ( attenuation.x + (attenuation.y*d) + (attenuation.z*d*d) ) : 1.0;\n" + 
//				"	\n" + 
//				"	vec3 result = (ambientColor * ambientIntensity) + (lightColor.rgb * lambert) * att;\n" + 
//				"	result *= color.rgb;\n" + 
//				"	\n" + 
//				"	gl_FragColor = v_color * vec4(result, color.a);\n" + 
//				"}";
////		System.out.println("VERTEX PROGRAM:\n------------\n\n"+vert);
////		System.out.println("FRAGMENT PROGRAM:\n------------\n\n"+frag);
//		ShaderProgram program = new ShaderProgram(vert, frag);
//		// u_proj and u_trans will not be active but SpriteBatch will still try to set them...
//		program.pedantic = false;
//		if (program.isCompiled() == false)
//			throw new IllegalArgumentException("couldn't compile shader: " + program.getLog());
//
//		// set resolution vector
//		resolution.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//		// we are only using this many uniforms for testing purposes...!!
//		program.begin();
//		program.setUniformi("u_texture", 0);
//		program.setUniformi("u_texture", 0);
//		program.setUniformi("u_normals", 1);
//		program.setUniformf("light", lightPos);
//		program.setUniformf("strength", strength);
//		program.setUniformf("ambientIntensity", ambientIntensity);
//		program.setUniformf("ambientColor", DEFAULT_AMBIENT_COLOR);
//		program.setUniformf("resolution", resolution);
//		program.setUniformf("lightColor", DEFAULT_LIGHT_COLOR);
//		program.setUniformf("attenuation", attenuation);
//		program.setUniformi("useShadow", useShadow ? 1 : 0);
//		program.setUniformi("useNormals", useNormals ? 1 : 0);
//		program.setUniformi("yInvert", flipY ? 1 : 0);
//		program.end();
//		
//		return program;
//	}
//	
//	public void renderShader(Array<Sprite> sprites, float x,  float y) {
// 		final int IMG_Y = texHeight/2; 
// 		time += Gdx.graphics.getDeltaTime();
// 		
// 		sb.begin();
// 		
////		lightPos.x = Gdx.input.getX();
////		lightPos.y = Gdx.graphics.getHeight() - Gdx.input.getY();
//
////		lightPos.x = Gdx.graphics.getWidth()/2f; //because the player is centered, this makes sense
////		lightPos.y = Gdx.graphics.getHeight()/2f; //it makes sense to center the light position while other objects move around the screen.
//		
// 		
// 		Vector3 dd = GameConstants.camera.project(new Vector3(x,y,0));
//// 		Vector3 cd = MapUtils.screenPixelsToMeters(Gdx.input.getX(), Gdx.input.getY());
// 		
// 		lightPos.x = dd.x + dx;
// 		lightPos.y = dd.y + + dy;
//// 		lightPos.z = dz;
// 		
//		attenuation.x = 0;
//		attenuation.y = 0;
//		attenuation.z = 11.50000f;
//			
////		strength = 0.075f;
////		lightPos.z = 0.025f;
//		
//		// update our uniforms
//		program.setUniformf("ambientIntensity", ambientIntensity);
//		program.setUniformf("attenuation", attenuation);
//		program.setUniformf("light", lightPos);
//		program.setUniformf("lightColor", DEFAULT_LIGHT_COLOR);
//		program.setUniformf("ambientColor", DEFAULT_AMBIENT_COLOR);
//		program.setUniformi("useNormals", useNormals ? 1 : 0);
//		program.setUniformi("useShadow", useShadow ? 1 : 0);
//		program.setUniformf("strength", strength);
//		
//		
//		// bind the normal first at texture1
//		texture_n.bind(1);
//		
//		// bind the actual texture at texture0
//		texture.bind(0);
//		
//		// we bind texture0 second since draw(texture) will end up binding it at
//		// texture0...
////		sb.draw(texture, texWidth*2 + 20, IMG_Y);
//		
//		sb.setProjectionMatrix(GameConstants.camera.combined);
//		
//        float originX = 1 / 2f;
//        float originY = 1 / 2f;
//        
//        for (Sprite sprite : sprites) {
//        	sb.draw(sprite, x-sprite.getX(), y-sprite.getY(), originX, originY, 1, 1, sprite.getScaleX(), sprite.getScaleY(), 0);
//        }
//
////		sb.draw(texture, 44, 8);
////		sb.draw(texture, 1000, 1000, 580, 580);
//		
//		sb.end();
//	}
//	
//	
//	public void resetLight() {
//		DEFAULT_LIGHT_COLOR = new Vector3(0.2f, 0.0f, 0.6f);
//		DEFAULT_AMBIENT_COLOR = new Vector3(0.0f, 0.f, 1f);
//		
//		lightPos.z = DEFAULT_LIGHT_POS.z;
//		dx = 0f; dy = 0f;
//	}
//	
//	public Vector3 getLightcolor() {
//		return DEFAULT_LIGHT_COLOR;
//	}
//	
//	public Vector3 getAmbientcolor() {
//		return DEFAULT_AMBIENT_COLOR;
//	}
//	
//
//}
