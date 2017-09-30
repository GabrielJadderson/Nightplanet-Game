package com.gabrieljadderson.nightplanetgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;
import com.gabrieljadderson.nightplanetgame.ui.GDialog;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import com.gabrieljadderson.nightplanetgame.utils.Position;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Copyright (c) 2016-onwards NightPlanet /vGabriel Howard Jadderson
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * <p>
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p>
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p>
 * * Neither the name of 'Shaven Puppy' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * This Screen is invoked when a new character is created and thus need to be registered
 * @author Gabriel Jadderson
 * @since 23/02/2016
 * @version 0.1
 * TODO:
 */
public class CharacterSelectionScreen extends DefaultScreen
{
	SpriteBatch batch;
	Stage stage = null;
	GDialog characterDisplay;
	ImageButton left;
	ImageButton right;
	TextButton select;
	Label playerName;
	Label exhibitName;
	Label fader;
	Skin skin;
	Dimension screenSize;
	
	SpineObject test;
	
	public CharacterSelectionScreen(Game game)
	{
		super(game);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void show()
	{
		if (stage == null)
		{
			stage = new Stage();
			batch = new SpriteBatch(10);
			skin = new Skin(ResourceCrawler.getRes("uiskin.json"));
			screenSize = new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			LabelStyle labelStyle = skin.get("tintedDialog", LabelStyle.class);
			
			playerName = new Label(GameConstants.player.PLAYER_NAME, labelStyle);
			playerName.setWrap(true);
//			playerName.setSize(playerName.getText().length, 38);
			playerName.setPosition((screenSize.getWidth() / 2) - (playerName.getWidth() / 2), (screenSize.getHeight() / 2) + 310);
			playerName.setColor(playerName.getColor().r, playerName.getColor().g, playerName.getColor().b, 0);
			playerName.addAction(sequence(fadeIn(0.2f)));
			stage.addActor(playerName);
			
			select = new TextButton("Select", skin);
			select.setSize(145, 50);
			select.setPosition((screenSize.getWidth() / 2) - (select.getWidth() / 2), (screenSize.getHeight() / 2) - 350);
			select.setColor(select.getColor().r, select.getColor().g, select.getColor().b, 0);
			select.addAction(sequence(fadeIn(0.3f)));
			select.addListener(new ClickListener()
			{
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					invokeSelect();
				}
				
				;
			});
			stage.addActor(select);
			
			SpineObject displayObject = new SpineObject("hero_exhibit.atlas", "hero_exhibit.json", 1.0f, 1f, false);
			displayObject.setPosition(new Position((screenSize.getWidth() / 2), (screenSize.getHeight() / 2) - 65));
			displayObject.setAnimation(0, "Idle", true);
			
			characterDisplay = new GDialog("", skin, GDialog.POSITION_Center, GDialog.SIZE_Large, Align.center, "display");
			characterDisplay.setSize(380f, 400f);
			characterDisplay.setPosition((screenSize.getWidth() / 2) - (characterDisplay.getWidth() / 2), (screenSize.getHeight() / 2) - 110);
//			characterDisplay.setLoader();
			characterDisplay.addSpineObject(displayObject);
			characterDisplay.setModal(false);
			characterDisplay.setMovable(false);
			characterDisplay.setCloseable(false);
//			characterDisplay.addSize(0, 0);
			stage.addActor(characterDisplay);
			
			left = new ImageButton(skin, "right");
			left.setSize(67, 67);
			left.setPosition((screenSize.getWidth() / 2) - (left.getWidth() / 2) - 30 - 5, (screenSize.getHeight() / 2) - 155);
			stage.addActor(left);
			
			right = new ImageButton(skin, "left");
			right.setSize(67, 67);
			right.setPosition((screenSize.getWidth() / 2) - (right.getWidth() / 2) + 30 + 5, (screenSize.getHeight() / 2) - 155);
			stage.addActor(right);
			
			exhibitName = new Label("EXHIBIT NAME", labelStyle);
			exhibitName.setWrap(true);
			exhibitName.setPosition((screenSize.getWidth() / 2) - (exhibitName.getWidth() / 2), (screenSize.getHeight() / 2) - 210);
			exhibitName.setColor(exhibitName.getColor().r, exhibitName.getColor().g, exhibitName.getColor().b, 0);
			exhibitName.addAction(sequence(fadeIn(0.2f)));
			stage.addActor(exhibitName);
			
			fader = new Label("", skin);
			fader.setColor(Color.CLEAR);
			fader.addAction(fadeOut(5f));
			stage.addActor(fader);
		}
		GameConstants.inputMultiplexer.addProcessor(stage);
		
		test = new SpineObject("Loading_Bar.atlas", "Loading_Bar.json", 0.500f, 1.00f, false);
		test.setPosition(new Position(200, 200));
		test.setAnimation(0, "animation", true);
		
	}
	
	@Override
	public void render(float delta)
	{
//		Gdx.gl.glClearColor(230, 230, 230, 0);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(delta, 0.020f));
		stage.draw();
		
		test.update(delta);
		
		
		batch.begin();
		test.render(batch);
		batch.end();
		
		test.getSkeleton().getData().setWidth(20);
	}
	
	public void invokeLeft()
	{
		//TODO: implement
	}
	
	public void invokeRight()
	{
		//TODO IMPLEMENT
	}
	
	public void invokeSelect()
	{
		//TODO select char
	}
	
	
	@Override
	public void hide()
	{
		GameConstants.inputMultiplexer.removeProcessor(stage);
	}
	
	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
