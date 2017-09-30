package com.gabrieljadderson.nightplanetgame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * A batch which supports multiple texture units, for example to allow for
 * blending between two textures. The batch can be used as a generic batch
 * that sends no texcoords or color attributes, and generic vertex attributes
 * can be provided by a subclass.
 *
 * @author Gabriel Jadderson
 */
public class GenericBatch extends AbstractQuadBatch
{
	
	/**
	 * The base name for texcoord sampler2D uniforms, "u_texture" -- suffixed by an integer
	 * in the shader to denote its texture unit (e.g. "u_texture0").
	 */
	public static final String U_TEXTURE = "u_texture";
	
	/**
	 * Creates the list of attributes for our texture batch
	 */
	private static VertexAttribute[] createAttributes(int numTexCoords, boolean hasColor, VertexAttribute... generics)
	{
		int genLen = generics != null ? generics.length : 0;
		int len = hasColor ? 2 : 1;
		VertexAttribute[] attr = new VertexAttribute[numTexCoords + len + genLen];
		int off = 0;
		attr[off++] = new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE);
		if (hasColor)
			attr[off++] = new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE);
		for (int i = 0; i < numTexCoords; i++) //add texcoords
			attr[off++] = new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + i);
		if (generics != null)
			for (VertexAttribute a : generics) //add generics
				attr[off++] = a;
		return attr;
	}
	
	/**
	 * temp vert position
	 */
	private static VertexPosition VERT = new VertexPosition();
	/**
	 * temp UVs array
	 */
	private static final float[] uvs = new float[8];
	
	/**
	 * The texture coordinates for each texture unit; the length of this array
	 * is the number of available texture units for this batch.
	 */
	protected TextureRegion[] textures;
	/**
	 * Temp texture regions; allows user to pass Texture for convenience.
	 */
	private TextureRegion[] tmpReg;
	
	/**
	 * The total number of texture coordinates sent to the shader.
	 */
	protected int texCoordCount;
	
	/**
	 * The generic attributes given to the constructor.
	 */
	protected VertexAttribute[] genericAttribs;
	
	/**
	 * Whether this batch has color and is sending the information to the shader.
	 */
	protected boolean hasColor = true;
	
	/**
	 * Creates a new multi-texture batch that supports N texture units. Each texture unit has its own
	 * corresponding texCoord attribute that will be sent to the shader. A more optimized solution would
	 * be to allow multiple texture units but only send limited texCoord attributes to the shader;
	 * i.e. by organizing texture atlases so that the sprites (for example: diffuse + normal) line up
	 * across them. This can be achieved with the more detailed constructors.
	 * <p>
	 * This batch is created with an initial size of 500. Distribution code should benchmark the memory
	 * usage and determine a more suitable size.
	 *
	 * @param textureCount the number of texture units (and texCoord attributes) supported by this batch
	 */
	public GenericBatch(int textureUnits)
	{
		this(textureUnits, 500);
	}
	
	/**
	 * Creates a new multi-texture batch that supports N texture units. Each texture unit has its own
	 * corresponding texCoord attribute that will be sent to the shader. A more optimized solution would
	 * be to allow multiple texture units but only send limited texCoord attributes to the shader;
	 * i.e. by organizing texture atlases so that the sprites (for example: diffuse + normal) line up
	 * across them. This can be achieved with the more detailed constructors.
	 * <p>
	 * A batch with 0 textureUnits will not send any texture information to the shader, i.e. if texturing
	 * is not needed by the shader.
	 *
	 * @param textureCount the number of texture units (and texCoord attributes) supported by this batch
	 * @param size         the size of the batch, in sprites (quads)
	 */
	public GenericBatch(int textureUnits, int size)
	{
		this(textureUnits, textureUnits, size, true);
	}
	
	/**
	 * Creates a new multi-texture batch with the given number of texture units (sampler2D) and texture coordinates
	 * (vec2). The size represents the number of sprites (quads) that will fit in a single batch. The color information
	 * is included and sent to the mesh as a packed float, which is then unpacked by GL into a 4-component vector (vec4)
	 * in the vertex shader.
	 * <p>
	 * 0 can be used for texCoordCount to send no texCoord attributes to the shaders; likewise, 0 can be used for
	 * textureUnits if no texturing is desired.
	 * <p>
	 * The value of texCoordCount must be equal or less than textureUnits.
	 * <p>
	 * Most systems will support at least 8 texture units.
	 *
	 * @param textureUnits  the number of texture units for this batch
	 * @param texCoordCount the number of texture coordinate attributes to send to the vertex shader
	 * @param size          the size of this batch, in sprites (quads)
	 */
	public GenericBatch(int textureUnits, int texCoordCount, int size)
	{
		this(textureUnits, texCoordCount, size, true);
	}
	
	/**
	 * Creates a new multi-texture batch with the given number of texture units (sampler2D) and texture coordinates
	 * (vec2). The size represents the number of sprites (quads) that will fit in a single batch. If <tt>hasColor</tt>
	 * is false, then no color information will be sent to the mesh or shader, and setColor/getColor will be effectively
	 * useless.
	 * <p>
	 * 0 can be used for texCoordCount to send no texCoord attributes to the shaders; likewise, 0 can be used for
	 * textureUnits if no texturing is desired.
	 * <p>
	 * The value of texCoordCount must be equal or less than textureUnits.
	 * <p>
	 * Most systems will support at least 8 texture units.
	 *
	 * @param textureUnits  the number of texture units for this batch
	 * @param texCoordCount the number of texture coordinate attributes to send to the vertex shader
	 * @param size          the size of this batch, in sprites
	 * @param hasColor      true if this batch should send color information to the shader
	 */
	public GenericBatch(int textureUnits, int texCoordCount, int size, boolean hasColor)
	{
		this(textureUnits, texCoordCount, size, hasColor, (VertexAttribute[]) null);
	}
	
	/**
	 * An advanced constructor for subclasses that wish to add generic attributes. In addition to initializing
	 * a batch with a list of generic VertexAttributes, subclasses will also need to subclass the <tt>vertex</tt>
	 * method to push the attribute(s) into the mesh.
	 * <p>
	 * 0 can be used for texCoordCount to send no texCoord attributes to the shaders; likewise, 0 can be used for
	 * textureUnits if no texturing is desired.
	 * <p>
	 * The value of texCoordCount must be equal or less than textureUnits.
	 * <p>
	 * The vertex attributes are constructed like so:
	 * <p>
	 * <pre>
	 *     attr.add( new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE) );
	 *     if (hasColor):
	 *     		attr.add( new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE) );
	 *     for (int N=0; N<texCoordCount; N++)
	 *     		attr.add( new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + N) );
	 *     for (VertexAttribute a : generic)
	 *     		attr.add ( a );
	 * </pre>
	 *
	 * @param textureUnits  the number of texture units for this batch
	 * @param texCoordCount the number of texture coordinate attributes to send to the vertex shader
	 * @param size          the size of this batch, in sprites
	 * @param hasColor      true if this batch should send color information to the shader
	 * @param generic       a list of VertexAttributes that define the generic vertex attributes
	 */
	protected GenericBatch(int textureUnits, int texCoordCount, int size, boolean hasColor, VertexAttribute... generic)
	{
		super(size, createAttributes(texCoordCount, hasColor, generic));
		this.hasColor = hasColor;
		this.textures = new TextureRegion[textureUnits];
		this.texCoordCount = texCoordCount;
		this.genericAttribs = generic;
	}
	
	/**
	 * Returns the number of texCoord attributes that are sent to the shader with this batch.
	 */
	public int getTexCoordCount()
	{
		return texCoordCount;
	}
	
	/**
	 * Returns the number of texture units supported by this batch (i.e. sampler2D uniforms)
	 *
	 * @return the number of texture units
	 */
	public int getTextureCount()
	{
		return textures.length;
	}
	
	/**
	 * Sets the texture coordinates for the given texture unit.
	 * <p>
	 * If the batch is currently drawing and the given texture does not match the old texture, the batch will first
	 * be flushed.
	 *
	 * @param index  the unit, e.g. texture unit 0, must be less than getTexCoordCount()
	 * @param coords the texture region for the texture coordinates
	 */
	public void setTexture(int index, TextureRegion coords)
	{
		//if we are drawing and the new texcoords TEXTURE doesn't match the old one
		if (drawing && this.textures[index] != null && this.textures[index].getTexture() != coords.getTexture())
			flush();
		this.textures[index] = coords;
	}
	
	/**
	 * Sets the texture coordinates for the given texture unit, using a full texture region.
	 * <p>
	 * If the batch is currently drawing and the given texture does not match the old texture, the batch will first
	 * be flushed.
	 *
	 * @param index   the unit, e.g. texture unit 0, must be less than getTexCoordCount()
	 * @param texture the texture which will be used for this sampler
	 */
	public void setTexture(int index, Texture texture)
	{
		if (tmpReg == null)
			tmpReg = new TextureRegion[textures.length];
		if (tmpReg[index] == null) //minimize object creation since a lot of users will use this every frame
			tmpReg[index] = new TextureRegion();
		tmpReg[index].setRegion(texture);
		setTexture(index, tmpReg[index]);
	}
	
	public void draw(Texture tex0, float x, float y)
	{
		draw(tex0, x, y, tex0.getWidth(), tex0.getHeight());
	}
	
	public void draw(Texture tex0, float x, float y, float width, float height)
	{
		setTexture(0, tex0);
		draw(x, y, width, height);
	}
	
	public void draw(TextureRegion tex0, float x, float y)
	{
		draw(tex0, x, y, tex0.getRegionWidth(), tex0.getRegionHeight());
	}
	
	public void draw(TextureRegion tex0, float x, float y, float width, float height)
	{
		setTexture(0, tex0);
		draw(x, y, width, height);
	}
	
	public void draw(
			float x, float y,
			float width, float height)
	{
		draw(x, y, 0, 0, width, height, 1f, 1f, 0f);
	}
	
	public void draw(
			float x, float y,
			float originX, float originY,
			float width, float height,
			float scaleX, float scaleY,
			float rotationDeg)
	{
		if (!drawing)
			throw new IllegalStateException("begin() must be called before draw");
		if (idx >= vertices.length)
			flush();
		
		//VertexPosition utility will transform corners on CPU
		VERT.transform(x, y, originX, originY, width, height, scaleX, scaleY, rotationDeg);
		
		//top left, bottom left, bottom right, top right
		vertex(VERT.x1, VERT.y1, 0);
		vertex(VERT.x2, VERT.y2, 1);
		vertex(VERT.x3, VERT.y3, 2);
		vertex(VERT.x4, VERT.y4, 3);
	}
	
	/**
	 * Pushes the texture region's U/V and U2/V2 values to the given "out" array.
	 * If the texture region is null (i.e. not specified with setTexture), full
	 * texture coordinates 0.0 to 1.0 will be used.
	 */
	protected void pushTexCoords(TextureRegion tex, float[] out)
	{
		int i = 0;
		if (tex == null)
		{
			//if null, just use full texcoords [0 - 1]
			//allows them to be used generically for crazy shader tinkering
			out[0] = out[2] = out[3] = out[5] = 0f;
			out[1] = out[4] = out[6] = out[7] = 1f;
			return;
		}
		out[i++] = tex.getU(); //TL
		out[i++] = tex.getV2();
		out[i++] = tex.getU(); //BL
		out[i++] = tex.getV();
		out[i++] = tex.getU2(); //BR
		out[i++] = tex.getV();
		out[i++] = tex.getU2(); //TR
		out[i++] = tex.getV2();
	}
	
	/**
	 * Adds a vertex to the batch. This adds the attributes Position, Color and TexCoord0..N
	 * using the format <tt>{x, y, color, u, v, u2, v2, ...}</tt>. The values are added to
	 * the <tt>vertices</tt> array, and the <tt>idx</tt> is incremented with each element.
	 * <p>
	 * If the batch was initialized with <tt>false</tt> given to <tt>hasColor</tt>, then the
	 * color attribute will be ignored and not included in the mesh.
	 * <p>
	 * Subclasses with generic attributes should extend this with
	 * the following format:
	 * <pre>
	 *    super.vertex(x, y, cornerIndex); //add Position, Color, TexCoord0..N
	 *    vertices[idx++] = attrib;
	 * </pre>
	 *
	 * @param x           the x position for this vertex
	 * @param y           the y position for this vertex
	 * @param cornerIndex
	 */
	protected void vertex(float x, float y, int cornerIndex)
	{
		vertices[idx++] = x;
		vertices[idx++] = y;
		if (hasColor)
			vertices[idx++] = color;
		//push tex coord attribs
		for (int i = 0; i < texCoordCount; i++)
		{
			pushTexCoords(textures[i], uvs);
			vertices[idx++] = uvs[cornerIndex * 2 + 0]; //U
			vertices[idx++] = uvs[cornerIndex * 2 + 1]; //V
		}
	}
	
	/**
	 * Called during begin() to prepare the render state by binding (in reverse order)
	 * each of the non-null textures to their given unit.
	 */
	protected void prepareRenderState()
	{
		super.prepareRenderState();
		
		//Bind back to front, so that the last bound unit is (ideally) texture unit 0
		for (int i = getTexCoordCount() - 1; i >= 0; i--)
		{
			if (textures[i] != null)
			{
				textures[i].getTexture().bind(i);
			}
		}
	}
	
	/**
	 * Called when the batch is started to update the uniforms; sends the projection matrix
	 * to the shader as well as the samplers in the form U_TEXTURE + N (result: "u_texture0").
	 */
	protected void updateUniforms()
	{
		super.updateUniforms();
		for (int i = 0; i < getTexCoordCount(); i++)
		{
			int loc = shader.getUniformLocation(U_TEXTURE + i);
			if (loc != -1)
				shader.setUniformi(loc, i);
		}
	}
}