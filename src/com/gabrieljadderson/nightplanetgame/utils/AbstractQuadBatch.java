package com.gabrieljadderson.nightplanetgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.NumberUtils;

/**
 * @author Gabriel Jadderson
 */
public abstract class AbstractQuadBatch
{
	
	/**
	 * The combined projection/transformation matrix sent to the shader.
	 */
	public static final String U_PROJECTION_MATRIX = "u_projTrans";
	
	protected Mesh mesh;
	protected float[] vertices;
	protected int idx;
	protected int spriteSize;
	protected boolean drawing;
	protected ShaderProgram shader;
	protected VertexAttribute[] attributes;
	
	protected Matrix4 u_projTrans;
	
	public int renderCalls;
	
	protected float color = Color.WHITE.toFloatBits();
	private Color tempColor = new Color(Color.WHITE);
	
	protected AbstractQuadBatch(int size, VertexAttribute... attributes)
	{
		
		this.attributes = attributes;
		
		//count number of components per vertex
		//ColorPacked is just 1 component
		int numComponents = 0;
		for (VertexAttribute a : attributes)
		{
			if (a.usage == Usage.ColorPacked)
				numComponents++;
			else
				numComponents += a.numComponents;
		}
		
		mesh = new Mesh(VertexDataType.VertexArray, false,
				size * 4, size * 6, attributes);
		
		short[] indices = new short[size * 6];
		for (int i = 0, j = 0; i < indices.length; i += 6, j += 4)
		{
			indices[i + 0] = (short) (j + 0);
			indices[i + 1] = (short) (j + 1);
			indices[i + 2] = (short) (j + 2);
			indices[i + 3] = (short) (j + 2);
			indices[i + 4] = (short) (j + 3);
			indices[i + 5] = (short) (j + 0);
		}
		mesh.setIndices(indices);
		
		//count number of elements in a sprite (4 verts per sprite, X components per vert)
		spriteSize = 4 * numComponents;
		vertices = new float[size * spriteSize];
		
		u_projTrans = new Matrix4();
		u_projTrans.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/**
	 * Sets the current projection butch, ending and beginning the batch if necessary in order
	 * to flush it fully (i.e. if this is called when the batch is drawing).
	 *
	 * @param mtx the new projection matrix
	 */
	public void setProjectionMatrix(Matrix4 matrix)
	{
		if (drawing)
			flush();
		u_projTrans = matrix;
		if (drawing)
			updateProjection();
	}
	
	/**
	 * The projection matrix. Modifying this won't impact the batch until next begin().
	 *
	 * @return
	 */
	public Matrix4 getProjectionMatrix()
	{
		return u_projTrans;
	}
	
	/**
	 * Subclasses may or may not choose to implement sprite coloring.
	 * <p>
	 * Sets the color used to tint images when they are added to the SpriteBatch. Default is {@link Color#WHITE}.
	 */
	public void setColor(Color tint)
	{
		color = tint.toFloatBits();
	}
	
	/**
	 * Subclasses may or may not choose to implement sprite coloring.
	 *
	 * @see #setColor(Color)
	 */
	public void setColor(float r, float g, float b, float a)
	{
		int intBits = (int) (255 * a) << 24 | (int) (255 * b) << 16 | (int) (255 * g) << 8 | (int) (255 * r);
		color = NumberUtils.intToFloatColor(intBits);
	}
	
	/**
	 * Subclasses may or may not choose to implement sprite coloring.
	 *
	 * @see #setColor(Color)
	 * @see Color#toFloatBits()
	 */
	public void setColor(float color)
	{
		this.color = color;
	}
	
	/**
	 * Subclasses may or may not choose to implement sprite coloring.
	 *
	 * @return the rendering color of this SpriteBatch. The returned instance is shared by
	 * subsequent calls to getColor and has no effect on the SpriteBatch rendering.
	 */
	public Color getColor()
	{
		int intBits = NumberUtils.floatToIntColor(color);
		Color color = this.tempColor;
		color.r = (intBits & 0xff) / 255f;
		color.g = ((intBits >>> 8) & 0xff) / 255f;
		color.b = ((intBits >>> 16) & 0xff) / 255f;
		color.a = ((intBits >>> 24) & 0xff) / 255f;
		return color;
	}
	
	public void begin(ShaderProgram shader)
	{
		if (drawing) throw new IllegalStateException("you have to call end() first");
		if (shader == null) throw new IllegalArgumentException("shader cannot be null");
		this.shader = shader;
		renderCalls = 0;
		idx = 0;
		drawing = true;
		shader.begin();
		updateUniforms();
	}
	
	public void end()
	{
		if (!drawing) throw new IllegalStateException("begin must be called before end.");
		if (idx > 0) flush();
		idx = 0;
		drawing = false;
		if (shader != null)
			shader.end();
	}
	
	public void flush()
	{
		if (idx > 0)
		{
			int spritesInBatch = idx / spriteSize;
			int count = spritesInBatch * 6;
			
			mesh.setVertices(vertices, 0, idx);
			mesh.getIndicesBuffer().position(0);
			mesh.getIndicesBuffer().limit(count);
			
			prepareRenderState();
			renderMesh(count);
			
			renderCalls++;
			idx = 0;
		}
	}
	
	/**
	 * Called after the shader is started, to upload matrices and other uniforms to the current shader.
	 */
	protected void updateUniforms()
	{
		updateProjection();
	}
	
	/**
	 * Called by updateUniforms to upload the matrices.
	 */
	protected void updateProjection()
	{
		shader.setUniformMatrix(U_PROJECTION_MATRIX, u_projTrans);
	}
	
	/**
	 * Called by flush() to prepare the render state before rendering (e.g. blend mode, textures).
	 */
	protected void prepareRenderState()
	{
	}
	
	/**
	 * Called to render the mesh with the current shader.
	 *
	 * @param primitiveCount the primitive count to send to Mesh.render
	 */
	protected void renderMesh(int primitiveCount)
	{
		mesh.render(shader, GL20.GL_TRIANGLES, 0, primitiveCount);
	}
	
	protected static class VertexPosition
	{
		float x1, y1; //top left
		float x2, y2; //bottom left
		float x3, y3; //bottom right
		float x4, y4; //top right
		
		public void transform(
				float x, float y,
				float originX, float originY,
				float width, float height,
				float scaleX, float scaleY,
				float rotationDeg)
		{
			// bottom left and top right corner points relative to origin
			final float worldOriginX = x + originX;
			final float worldOriginY = y + originY;
			float fx = -originX;
			float fy = -originY;
			float fx2 = width - originX;
			float fy2 = height - originY;
			
			// scale
			if (scaleX != 1 || scaleY != 1)
			{
				fx *= scaleX;
				fy *= scaleY;
				fx2 *= scaleX;
				fy2 *= scaleY;
			}
			
			// construct corner points, start from top left and go counter clockwise
			final float p1x = fx;
			final float p1y = fy;
			final float p2x = fx;
			final float p2y = fy2;
			final float p3x = fx2;
			final float p3y = fy2;
			final float p4x = fx2;
			final float p4y = fy;
			
			// rotate -- code from LibGDX's SpriteBatch
			if (rotationDeg != 0)
			{
				final float cos = MathUtils.cosDeg(rotationDeg);
				final float sin = MathUtils.sinDeg(rotationDeg);
				
				x1 = cos * p1x - sin * p1y;
				y1 = sin * p1x + cos * p1y;
				
				x2 = cos * p2x - sin * p2y;
				y2 = sin * p2x + cos * p2y;
				
				x3 = cos * p3x - sin * p3y;
				y3 = sin * p3x + cos * p3y;
				
				x4 = x1 + (x3 - x2);
				y4 = y3 - (y2 - y1);
			} else
			{
				x1 = p1x;
				y1 = p1y;
				x2 = p2x;
				y2 = p2y;
				x3 = p3x;
				y3 = p3y;
				x4 = p4x;
				y4 = p4y;
			}
			
			x1 += worldOriginX;
			y1 += worldOriginY;
			x2 += worldOriginX;
			y2 += worldOriginY;
			x3 += worldOriginX;
			y3 += worldOriginY;
			x4 += worldOriginX;
			y4 += worldOriginY;
		}
	}
}