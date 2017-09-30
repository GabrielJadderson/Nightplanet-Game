package com.gabrieljadderson.nightplanetgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import com.gabrieljadderson.nightplanetgame.utils.Position;

import java.util.HashMap;
import java.util.Map.Entry;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;

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
 * @author Gabriel Jadderson
 * @since 19/02/2016
 * @version 0.1
 * TODO: 
 */
public class GDialog extends Dialog
{
	
	public static float SIZE_XXSmall = .1f;
	public static float SIZE_XSmall = .2f;
	public static float SIZE_Small = .3f;
	public static float SIZE_Medium = .4f;
	public static float SIZE_XMedium = .5f;
	public static float SIZE_XXMedium = .6f;
	public static float SIZE_Large = .7f;
	public static float SIZE_XLarge = .8f;
	public static float SIZE_XXLarge = .9f;
	public static float SIZE_XXXLarge = 1;
	float currentSize;
	float sizeMultiplier;
	
	public static short POSITION_Center = 1;
	public static short POSITION_East = 2;
	public static short POSITION_West = 3;
	public static short POSITION_NorthWest = 4;
	public static short POSITION_NorthEast = 5;
	public static short POSITION_SouthWest = 6;
	public static short POSITION_SouthEast = 7;
	public static short POSITION_Top = 8;
	public static short POSITION_Bottom = 9;
	
	TextButton t_btn1;
	TextButton t_btn2;
	TextButton t_btn3;
	
	ImageButton i_btn1;
	ImageButton i_btn2;
	ImageButton i_btn3;
	
	Label dialogText;
	
	Position spineObjectPosition;
	SpineObject loadingBar;
	boolean isClicked = false;
	boolean isLoading = false;
	Label fader;
	
	boolean closeable = true;
	
	/**
	 * this is the preferred usage of this class.
	 * @param title the title of this dialog
	 * @param skin the skin of it 
	 * @param Position the position use the positions within this class
	 * @param Size use the size options within this class.
	 * @param normalTextAllignment the Allignment for the breadText
	 * @param type: AVAILABLE TYPES: 'mini' & 'display'
	 */
	public GDialog(String title, Skin skin, short Position, float Size, int normalTextAllignment, String type)
	{
		super(title, skin, type); //the mini is the only supported atm. the others look like shit. 
		setPaneSize(Size);
		setPosition(Position);
		setModal(true);
//		align(Align.center);
		
		isClicked = false;
		
		getTitleLabel().setAlignment(Align.center);
		
		padTop(23);
		
		padBottom(20);
		
		
		dialogText = new Label("", skin);
		dialogText.setWrap(true);
		dialogText.setAlignment(normalTextAllignment);
		getContentTable().add(dialogText).width(getWidth() - (getWidth() * 0.15f)).row();
		getContentTable().padTop(8);
//		getContentTable().padBottom(10);
//		getContentTable().padRight(50);
//		getContentTable().padLeft(15);
//		getButtonTable().padTop(20);
		getButtonTable().defaults().height(50); //button size
		getButtonTable().defaults().width(50); //button size
		getButtonTable().padRight(5);
		
		Button close = new Button(skin);
		close.setSize(30, 30);
		close.setColor(0, 0, 0, 0);
		close.setPosition(getWidth() - close.getWidth(), getHeight() - close.getHeight());
		close.addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				if (closeable)
				{
					hide();
					result(false);
				}
			}
		});
		addActor(close);
		
		invalidateHierarchy();
		invalidate();
		layout();
	}
	
	@Override
	protected void result(Object object)
	{
		super.result(object);
		isClicked = true;
		if (isLoading)
		{
			fader.addAction(fadeOut(0.25f));
		}
	}
	
	
	public void update()
	{
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		
		if (isLoading)
		{
			if (isDragging())
			{
				spineObjectPosition.setX(getX() + getWidth() / 2);
				spineObjectPosition.setY(getY() + (getHeight() / 2) - (loadingBar.getSkeleton().getData().getHeight() / 2.5f));
				loadingBar.setPosition(spineObjectPosition);
			}
			
			loadingBar.update(Gdx.graphics.getDeltaTime());
			loadingBar.render(batch);
			
			if (isClicked)
			{
				loadingBar.getSkeleton().setColor(new Color(loadingBar.getSkeleton().getColor().r, loadingBar.getSkeleton().getColor().g, loadingBar.getSkeleton().getColor().b, fader.getColor().a));
			}
		}
		
		
	}
	
	public void setLoader()
	{
		loadingBar = new SpineObject(
				"Loading_Animation.atlas",
				"Loading_Animation.json",
				0.8f - (0.035f * sizeMultiplier),
				1.5f,
				false);
		spineObjectPosition = new Position((getX() + getWidth() / 2), getY() + (getHeight() / 2) - (loadingBar.getSkeleton().getData().getHeight() / 2.5f));
		loadingBar.setPosition(spineObjectPosition);
		
		loadingBar.setAnimation(0, "animation", true);
		isLoading = true;
		fader = new Label("", getSkin());
		add(fader);
	}
	
	public void removeLoader()
	{
		isLoading = false;
	}
	
	
	public void addSpineObject(SpineObject spine)
	{
		this.loadingBar = spine;
		isLoading = true;
		fader = new Label("", getSkin());
		add(fader);
	}
	
	public void removeSpineObject()
	{
		removeLoader(); //simple hack. not too proud of it though.
	}
	
	public SpineObject getSpineObject()
	{
		return loadingBar;
	}
	
	public Position getSpineObjectPosition()
	{
		return spineObjectPosition;
	}
	
	
	public void setCloseable(boolean closeable)
	{
		this.closeable = closeable;
	}
	
	/**
	 * takes in a hashmap of keys and their boolean values and sets them to dominate the Gdialog box
	 * @param map a map containing keystrokes and booleans 
	 */
	public void setKeys(HashMap<Integer, Boolean> map)
	{
		for (Entry<Integer, Boolean> key : map.entrySet())
		{
			key(key.getKey(), key.getValue());
		}
	}
	
	/**
	 * sets the message to be displayed in the message box.
	 * @param msg
	 */
	public void setMessage(String msg)
	{
		dialogText.setText(msg);
	}
	
	public TextButton setTextButton_1(String text, boolean op)
	{
		t_btn1 = new TextButton(text, getSkin());
		button(t_btn1, op);
		return t_btn1;
	}
	
	public TextButton setTextButton_2(String text, boolean op)
	{
		t_btn2 = new TextButton(text, getSkin());
		button(t_btn2, op);
		return t_btn2;
	}
	
	public TextButton setTextButton_3(String text, boolean op)
	{
		t_btn3 = new TextButton(text, getSkin());
		button(t_btn3, op);
		return t_btn3;
	}
	
	public ImageButton setImageButton_1(String imageDrawableName, boolean op)
	{
		i_btn1 = new ImageButton(getSkin());
		ButtonStyle bs = getSkin().get(imageDrawableName, ImageButtonStyle.class);
		i_btn1.setStyle(bs);
		button(i_btn1, op);
		return i_btn1;
	}
	
	public ImageButton setImageButton_2(String imageDrawableName, boolean op)
	{
		i_btn2 = new ImageButton(getSkin());
		ButtonStyle bs = getSkin().get(imageDrawableName, ImageButtonStyle.class);
		i_btn2.setStyle(bs);
		button(i_btn2, op);
		return i_btn2;
	}
	
	public ImageButton setImageButton_3(String imageDrawableName, boolean op)
	{
		i_btn3 = new ImageButton(getSkin());
		ButtonStyle bs = getSkin().get(imageDrawableName, ImageButtonStyle.class);
		i_btn3.setStyle(bs);
		button(i_btn3, op);
		return i_btn3;
	}
	
	/**
	 * sets the size of the pane 
	 * SIZE CANNOT BE 0 and bigger than 1
	 * @param Size
	 */
	public void setPaneSize(float Size)
	{
		if (Size <= 0 || Size > 1)
			return;
		Dimension fullSize = new Dimension(695f, 348f);
		setSize(fullSize.w * Size, fullSize.h * Size);
		currentSize = Size;
		float d = Size * 10;
		sizeMultiplier = 10 - d;
	}
	
	public void addSize(float w, float h)
	{
		setSize(getWidth() + w, getHeight() + h);
	}
	
	/**
	 * changes the position to the selector position.
	 * @param selector
	 */
	public void setPosition(short Position)
	{
		Dimension screenSize = new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		switch (Position)
		{
			case 1:
				setPosition(screenSize.w / 2 - getWidth() / 2, screenSize.h / 2 - getHeight() / 2);
				break;
			case 2:
				setPosition((screenSize.w / 2 + getWidth()), screenSize.h / 2 - getHeight() / 2);
				break;
			case 3:
				setPosition((screenSize.w / 2 - getWidth()), screenSize.h / 2 - getHeight() / 2);
				break;
			case 4:
				setPosition((screenSize.w / 2 - getWidth()), screenSize.h - (getHeight() + 20));
				break;
			case 5:
				setPosition(screenSize.w / 2 + getWidth() / 2, screenSize.h - (getHeight() + 20));
				break;
			case 6:
				setPosition((screenSize.w / 2 - getWidth()), (screenSize.h / 2 / getHeight() / 2) + 15);
				break;
			case 7:
				setPosition(screenSize.w / 2 + getWidth() / 2, (screenSize.h / 2 / getHeight() / 2) + 15);
				break;
			case 8:
				setPosition(screenSize.w / 2 - getWidth() / 2, screenSize.h - (getHeight() + 20));
				break;
			case 9:
				setPosition(screenSize.w / 2 - getWidth() / 2, (screenSize.h / 2 / getHeight() / 2) + 15);
				break;
			default:
				break;
		}
	}
	
}
