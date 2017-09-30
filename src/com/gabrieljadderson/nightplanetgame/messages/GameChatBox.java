package com.gabrieljadderson.nightplanetgame.messages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.SpriteSheet;

/**
 * @author Gabriel Jadderson
 */
public class GameChatBox
{
	
	private String PLAYER_MESSAGE = "HELLO FROM THE OTHER SIEDE ";
	private String PLAYER_MESSAGE2 = "TEXTY";
	
	//private Texture CHATBOX_BKG = SpriteSheet.loadPNG_tex(Gdx.files.classpath("graphics/chatbox_bkg.png"));
	
	private Texture CHATBOX_BKG;
	private Sprite CHATBOX_SPRITE;
	
	private Array<Sprite> sprite_array = new Array<Sprite>(20);
	
	private BitmapFont font;
//	private GlyphLayout metrics;
	
	final float x = 50f;//center
	final float y = 50f; //center
	
	public FadeCoordinator FadeCoord;
	
	public GameChatBox()
	{
		CHATBOX_BKG = SpriteSheet.loadPNG_tex(ResourceCrawler.getRes("chatbox_bkg.png"));
		CHATBOX_SPRITE = new Sprite(CHATBOX_BKG);
		
		FadeCoord = new FadeCoordinator();
		
		font = new BitmapFont();
//		metrics = new GlyphLayout(font, PLAYER_MESSAGE);
		System.out.println("ChatBox created.");
		
		CHATBOX_SPRITE.setBounds(x - 35, y - 30, CHATBOX_BKG.getWidth() / 4.5f, CHATBOX_BKG.getHeight() / 4.5f);
		CHATBOX_SPRITE.setAlpha(0);
		sprite_array.add(CHATBOX_SPRITE);
		
	}
	
	public void Render(Batch batch)
	{
		
		//defaults
		font.setColor(1, 1, 1, 0);
		
		FadeCoord.renderFade(font, sprite_array);
		
		for (Sprite sprite : sprite_array)
		{
			sprite.draw(batch);
		}
		
		CHATBOX_SPRITE.setAlpha(FadeCoord.getFadeIn());
		
		font.draw(batch, PLAYER_MESSAGE, x, y);
		font.draw(batch, PLAYER_MESSAGE2, x, y + 100f);
		
	}
	
	/**
	 * sets a new msg, note this does not render the msg use the FlushChat to update and
	 *
	 * @param newMSG
	 */
	public void sendPlayerMessage(String newMSG)
	{
		//TODO change the rendering method and fade in if not visible already.
//		PLAYER_MESSAGE = newMSG;
//		hide = !hide;
//		fade = 0f;
//		fade2 = 0f;
//		font.setColor(1, 1, 1, fade);
//		font.draw(batch, PLAYER_MESSAGE, x, y);
//		font.setColor(1, 1, 1, fade);
//		font.draw(batch, PLAYER_MESSAGE2, x, y+100f);
	}
	
	public void clearPlayerMessage()
	{
		if (PLAYER_MESSAGE != null) PLAYER_MESSAGE = "";
	}
	
	
}
