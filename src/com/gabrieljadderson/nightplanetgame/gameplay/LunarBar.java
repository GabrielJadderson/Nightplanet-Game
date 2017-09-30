package com.gabrieljadderson.nightplanetgame.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gabrieljadderson.nightplanetgame.graphics.SpineObject;
import com.gabrieljadderson.nightplanetgame.utils.Position;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gabriel Jadderson
 */
public class LunarBar
{
	
	final float MAX_X = 20f;
	final float MAX_Y = -5f;
	float half_width;
	float half_height;
	
	
	SpineObject bar;
	
	SpineObject sun;
	SpineObject moon;
	
	Position position;
	
	private AtomicInteger currentPower = new AtomicInteger(0);
	
	boolean freeze;
	
	int currentPhase = 1; //0 for sun 1 for moon
	
	
	public LunarBar()
	{
		bar = new SpineObject("LUNAR_BAR.atlas", "LUNAR_BAR.json", 0.42f, 1.0f, true);
		bar.setPosition(new Position((Gdx.graphics.getWidth() / 2f) + 35, Gdx.graphics.getHeight() - 28));
		bar.setAnimation(0, "LUNAR_FULL", false);
		
		sun = new SpineObject("Sun.atlas", "Sun.json", 0.03f, 0.5f, true);
		sun.setPosition(new Position((Gdx.graphics.getWidth() / 2f) + 163, Gdx.graphics.getHeight() - 28));
		sun.setAnimation(0, "animation", true);

//		moon = new SpineObject("", "", 0f, 0f, false);
//		moon.setPosition(new Position((Gdx.graphics.getWidth()/2f)+15, Gdx.graphics.getHeight()-28));
//		moon.setAnimation(0, "", false);
		
		
	}
	
	public void update()
	{
		float delta = Gdx.graphics.getDeltaTime();
		bar.update(delta);
		sun.update(delta);


//		
		//TODO: update
	}
	
	
	public void render(Batch batch)
	{
		float parallax_x = createParallax_X();
		float parallax_y = createParallax_Y();
		
		bar.setPosition(new Position(((Gdx.graphics.getWidth() / 2f) + 35) - parallax_x, (Gdx.graphics.getHeight() - 28) + parallax_y));
		bar.render(batch);
		
		sun.setPosition(new Position(((Gdx.graphics.getWidth() / 2f) + 163) - parallax_x, (Gdx.graphics.getHeight() - 28) + parallax_y));
		sun.render(batch);
		//TODO: rendering stuff..

//		stage.draw();
	}
	
	
	private float createParallax_X()
	{
		float d = half_width - Gdx.input.getX();
		return MAX_X - d / 35;
//		return half_width-Gdx.input.getX();
	}
	
	private float createParallax_Y()
	{
		float d = half_height - Gdx.input.getY();
		return MAX_Y - d / 45;
	}
	
	
	private void setMoonPhase()
	{
		bar.getSkeleton().setFlipX(false);
//		System.err.println("MOON PHASE SET");
		//TODO: more stuff in the moon phase?
	}
	
	private void setSunPhase()
	{
		bar.getSkeleton().setFlipX(true);
//		System.err.println("SUN PHASE SET");

//		TODO: more stuff in the sun phase??
	}
	
	/**
	 * power must be under 50 and above 0 or equal to 0
	 *
	 * @param power
	 */
	public void setPower(float power)
	{
		if (power < 50f && power >= 0f)
		{
			float rounded = 5 * (Math.round(power / 5)); //rounds for 5 each time. 5, 10, 15, 20
			bar.setAnimation(0, "LUNAR_" + rounded, false);
			int state = (int) (rounded / 5);
			if (state % 50 < 10)
			{ //unnecessary,  but an extra check.
				currentPower.set((int) (rounded / 5));
			}
		}
	}
	
	public boolean increase()
	{
		if (currentPower.get() < 10)
		{ //stops at 10
			bar.setAnimation(0, "LUNAR_" + currentPower.incrementAndGet() * 5, false);
//			System.out.println("increased " + currentPower.get());
			return true;
		} else
		{
//			System.out.println("ABOVE  MAX WTF MAN increased");
			return false;
		}
	}
	
	public boolean decrease()
	{
		if (currentPower.get() > 1)
		{ //stops at 0
			bar.setAnimation(0, "LUNAR_-" + currentPower.decrementAndGet() * 5, false);
//			System.out.println("decreased " + currentPower.get());
			return true;
		} else
		{
			currentPower.set(0);
			if (isSun())
			{
				setMoonPhase();
				currentPhase = 1;
			} else if (isMoon())
			{
				setSunPhase();
				currentPhase = 0;
			}
			increase();
			return false;
		}
	}
	
	public boolean isMoon()
	{
		return currentPhase == 1 ? true : false;
	}
	
	public boolean isSun()
	{
		return currentPhase == 0 ? true : false;
	}
	
}
