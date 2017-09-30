package com.gabrieljadderson.nightplanetgame.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;

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
 * @since 10/01/2016
 * @version 0.1
 * TODO: FIXME: FIX THIS CLASS ITS ABSOLUTELY DISGUSTING NO ONE SHOULD EVER CODE A CLASS LIKE THIS ONE. NEEDS FIXING OR JUST DELETE TBH THIS IS SO BAD.
 * shake my head..
 */
public class FadeCoordinator
{
	
	private static float FADE_IN_TIME = 2f;
	private static float FADE_OUT_IN_TIME = 2f;
	private float fadeElapsed = 0;
	private static float SUBTITLE_FADE_DELAY = 0.5f;
	private static float SUBTITLE_FADE_OUT_DELAY = 0.8f;
	
	float fadeIn;
	float fadeInSlow;
	float fadeOut;
	
	boolean FADE_OUT = false;
	boolean FADE_IN = false;
	boolean isDoneFading_IN = false;
	boolean isDoneFading_OUT = false;
	boolean canFadeOut = true;
	boolean canFadeIn = true;
	
	
	public void renderFade(BitmapFont font, Array<Sprite> sprite_array)
	{
		if (FADE_IN && canFadeIn)
		{
			isDoneFading_OUT = false;
			canFadeOut = false;
			fadeElapsed += Gdx.graphics.getDeltaTime();
			fadeIn = Interpolation.fade.apply(fadeElapsed / FADE_IN_TIME);
			fadeInSlow = Interpolation.fade.apply((fadeElapsed - SUBTITLE_FADE_DELAY) / FADE_IN_TIME);
			if (font != null) font.setColor(1, 1, 1, fadeIn);
			for (Sprite sprite : sprite_array)
			{
				sprite.setAlpha(fadeIn);
			}
			System.out.println(fadeIn);
		} else if (FADE_OUT && canFadeOut)
		{
			isDoneFading_IN = false;
			canFadeIn = false;
			fadeElapsed += Gdx.graphics.getDeltaTime();
			fadeOut = Interpolation.fade.apply((fadeElapsed - SUBTITLE_FADE_OUT_DELAY) / FADE_OUT_IN_TIME);
			if (font != null) font.setColor(1, 1, 1, 1 - fadeOut);
			for (Sprite sprite : sprite_array)
			{
				sprite.setAlpha(1 - fadeOut);
			}
			System.out.println(fadeOut);
		}
		if (fadeIn >= 1.0f)
		{
			System.out.println("done fading in");
			fadeIn = 0f;
			fadeElapsed = 0; //important
			isDoneFading_IN = true;
			FADE_IN = false;
			canFadeOut = true;
			canFadeIn = true;
		}
		if (fadeOut == 1.0f)
		{
			System.out.println("done fading out");
			fadeOut = 0f;
			fadeElapsed = 0; //important
			isDoneFading_OUT = true;
			FADE_OUT = false;
			canFadeOut = true;
			canFadeIn = true;
		}
		if (isDoneFading_IN)
		{
			font.setColor(1, 1, 1, 1);
			for (Sprite sprite : sprite_array)
			{
				sprite.setAlpha(1);
			}
		}
		if (isDoneFading_OUT)
		{
			font.setColor(1, 1, 1, 0);
			for (Sprite sprite : sprite_array)
			{
				sprite.setAlpha(0);
			}
		}
		
	}
	
	public float getFadeIn()
	{
		return fadeIn;
	}
	
	public float getFadeOut()
	{
		return fadeOut;
	}
	
	public void FadeIn()
	{
		if (canFadeIn) FADE_IN = true;
	}
	
	public void FadeOut()
	{
		if (canFadeOut) FADE_OUT = true;
	}
	
	public void FadeIn(float speed)
	{
		FADE_IN_TIME = speed;
		if (canFadeIn) FADE_IN = true;
	}
	
	public void FadeOut(float speed)
	{
		FADE_OUT_IN_TIME = speed;
		if (canFadeOut) FADE_OUT = true;
	}
	
	
	public void fadeIn_SPRITE(Sprite s)
	{
		FADE_IN = true;
		canFadeIn = true;
		
		FADE_OUT = false;
		canFadeOut = false;
		
		if (FADE_IN && canFadeIn)
		{
			isDoneFading_OUT = false;
			canFadeOut = false;
			fadeElapsed += Gdx.graphics.getDeltaTime();
			fadeIn = Interpolation.fade.apply(fadeElapsed / FADE_IN_TIME);
			fadeInSlow = Interpolation.fade.apply((fadeElapsed - SUBTITLE_FADE_DELAY) / FADE_IN_TIME);
			s.setAlpha(fadeIn);
			System.out.println(fadeIn);
		} else if (FADE_OUT && canFadeOut)
		{
			isDoneFading_IN = false;
			canFadeIn = false;
			fadeElapsed += Gdx.graphics.getDeltaTime();
			fadeOut = Interpolation.fade.apply((fadeElapsed - SUBTITLE_FADE_OUT_DELAY) / FADE_OUT_IN_TIME);
			s.setAlpha(1 - fadeOut);
			System.out.println(fadeOut);
		}
		if (fadeIn >= 1.0f)
		{
			System.out.println("done fading in");
			fadeIn = 0f;
			fadeElapsed = 0; //important
			isDoneFading_IN = true;
			FADE_IN = false;
			canFadeOut = true;
			canFadeIn = true;
		}
		if (fadeOut == 1.0f)
		{
			System.out.println("done fading out");
			fadeOut = 0f;
			fadeElapsed = 0; //important
			isDoneFading_OUT = true;
			FADE_OUT = false;
			canFadeOut = true;
			canFadeIn = true;
		}
		if (isDoneFading_IN)
		{
			s.setAlpha(1);
		}
		if (isDoneFading_OUT)
		{
			s.setAlpha(0);
		}
		
	}
	
	public void fadeOut_SPRITE(Sprite s)
	{
		FADE_IN = false;
		canFadeIn = false;
		
		FADE_OUT = true;
		canFadeOut = true;
		
		if (FADE_IN && canFadeIn)
		{
			isDoneFading_OUT = false;
			canFadeOut = false;
			fadeElapsed += Gdx.graphics.getDeltaTime();
			fadeIn = Interpolation.fade.apply(fadeElapsed / FADE_IN_TIME);
			fadeInSlow = Interpolation.fade.apply((fadeElapsed - SUBTITLE_FADE_DELAY) / FADE_IN_TIME);
			s.setAlpha(fadeIn);
			System.out.println(fadeIn);
		} else if (FADE_OUT && canFadeOut)
		{
			isDoneFading_IN = false;
			canFadeIn = false;
			fadeElapsed += Gdx.graphics.getDeltaTime();
			fadeOut = Interpolation.fade.apply((fadeElapsed - SUBTITLE_FADE_OUT_DELAY) / FADE_OUT_IN_TIME);
			s.setAlpha(1 - fadeOut);
			System.out.println(fadeOut);
		}
		if (fadeIn >= 1.0f)
		{
			System.out.println("done fading in");
			fadeIn = 0f;
			fadeElapsed = 0; //important
			isDoneFading_IN = true;
			FADE_IN = false;
			canFadeOut = true;
			canFadeIn = true;
		}
		if (fadeOut == 1.0f)
		{
			System.out.println("done fading out");
			fadeOut = 0f;
			fadeElapsed = 0; //important
			isDoneFading_OUT = true;
			FADE_OUT = false;
			canFadeOut = true;
			canFadeIn = true;
		}
		if (isDoneFading_IN)
		{
			s.setAlpha(1);
		}
		if (isDoneFading_OUT)
		{
			s.setAlpha(0);
		}
	}
	
	
}
