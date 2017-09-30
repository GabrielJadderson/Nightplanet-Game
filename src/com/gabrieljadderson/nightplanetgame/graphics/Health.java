package com.gabrieljadderson.nightplanetgame.graphics;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;

/**
 * @author Gabriel Jadderson
 */
public class Health
{
	//the rendering shader
//	Shader shader;
	
	//the maximum health amount to check for. 
	float MAX_HEALTH = 0f;
	float currentHealth = 0f;
	
	//the textures of the heart
	private Texture texture_diffuse;
	private Texture texture_normal;
	
	private TextureRegion HEART_TEX;
	
	private Sprite HEART_SPRITE_0;
	private Sprite HEART_SPRITE_1;
	private Sprite HEART_SPRITE_2;
	
	public Array<Sprite> HEART_SPRITES;
	
	public Health(float MAX_HEALTH)
	{
		this.MAX_HEALTH = MAX_HEALTH;
		
		texture_diffuse = SpriteSheet.loadPNG_tex(ResourceCrawler.getRes("heart_med.png"));
		texture_normal = SpriteSheet.loadPNG_tex(ResourceCrawler.getRes("heart_med_n.png"));
		
		HEART_TEX = new TextureRegion(texture_diffuse);
		
		HEART_SPRITE_0 = new Sprite(HEART_TEX);
		HEART_SPRITE_1 = new Sprite(HEART_TEX);
		HEART_SPRITE_2 = new Sprite(HEART_TEX);
		HEART_SPRITES = new Array<Sprite>(2);
		
		HEART_SPRITE_0.setPosition(1f, -0.4f);
		HEART_SPRITE_0.setScale(1f - 0.55f, 1f - 0.7f);
		
		HEART_SPRITE_1.setPosition(+0.50f, -0.4f);
		HEART_SPRITE_1.setScale(1f - 0.55f, 1f - 0.7f);
		
		HEART_SPRITE_2.setPosition(-0.0f, -0.4f);
		HEART_SPRITE_2.setScale(1f - 0.55f, 1f - 0.7f);
		HEART_SPRITES.add(HEART_SPRITE_0);
		HEART_SPRITES.add(HEART_SPRITE_1);
		HEART_SPRITES.add(HEART_SPRITE_2);

//		shader = new Shader(texture_diffuse,  texture_normal);
		
	}
	
	public void update(float currHealth)
	{
		if (currHealth != currentHealth)
		{
			updateHealth(currHealth);
		} else
		{
			return;
		}
	}
	
	public void render(float bodyX, float bodyY)
	{
//		shader.renderShader(HEART_SPRITES, bodyX, bodyY);
	}
	
	/**
	 * sets the health
	 *
	 * @param amount
	 */
	private void updateHealth(float curHealth)
	{
		if (curHealth == MAX_HEALTH)
		{
			reset();
		} else if (curHealth <= 0f)
		{
//			shader.dz = -1f;
			currentHealth = 0f;

//			shader.getLightcolor().x = 0.0f;
//			shader.getLightcolor().y = 0.0f;
//			shader.getLightcolor().z = 0.0f;

//			shader.getAmbientcolor().x = 0.0f;
//			shader.getAmbientcolor().y = 0.0f;
//			shader.getAmbientcolor().z = 0.0f;
		} else if (curHealth < MAX_HEALTH)
		{
			float percent = ((curHealth * 100) / MAX_HEALTH);
			float maxX = 200; // the amount of x that we can move the lightposition until all the hearts turn off lighting.
			float shaderPercent = ((percent * maxX) / 100);
			float shaderX = maxX - shaderPercent;
//			shader.dx = shaderX;
			currentHealth = curHealth;
//			shader.getAmbientcolor().z -= 0.15f;
			return;
		}
	}
	
	/**
	 * resets the health to the MAX VALUE
	 */
	public void reset()
	{
//		shader.resetLight();
		currentHealth = MAX_HEALTH;
	}
	
}
