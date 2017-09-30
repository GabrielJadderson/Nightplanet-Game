package com.gabrieljadderson.nightplanetgame.graphics.anim;

import com.badlogic.gdx.Gdx;

import box2dLight.Light;

/**
 * 
 * Copyright (c) 2016-onwards NightPlanet /vGabriel Howard Jadderson
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'Shaven Puppy' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
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
 * @author Gabriel Howard Jadderson
 * @since 14/12/2015
 * @version 0.1
 * TODO:
 * FIXME: this class needs DEEP CLEANING AND REWORK
 */
public class ItemAnimator {
	
	 private float elapsedTime = 0;
	 
	 float mSpeed=10.9f;
	 float mTargetX = 0.11f;
	 float mOriginalX=0.1f;
	 float mGange1 = 0.2f;
	 float mGange2 = 0.3f;
	 float mDistanceX=mTargetX-mOriginalX;
	 float mPositionX;
	 float AmPositionY = 0f;
	 float AmPositionX = 0f;
	 float aCircleDeg = 0f;
	 float aCircleRad = 0f;
	 float aCircleSpeed = 30f;
	 float aCircleX = 0f;
	 float aCircleY = 0f;
	 float aCircleWidth = 0f;
	 float aCircleHeight = 0f;
	 float lightX = 0f;
	 float lightY = 0f;
	 boolean animateLight = false;
	 boolean animateLight_repeats = false;
	 boolean animateLight_enableX = false;
	 boolean animateLight_enableY = false;
	 boolean animateLightCircle = false; //circle 
	 boolean animateLightCircle_repeats = false; //circle
	 //small lights animation

	 public ItemAnimator() {
		 this.elapsedTime += Gdx.graphics.getDeltaTime();

	 }


	 public void update() {
		 updateLightAnimation();
	 }

	 public void render() {

	 }
	 

	 public void animateLight(float speed, float Xtarget, float Xoriginal, float m1, float m2, float posX, float posY, boolean repeat) {
		 if (animateLightCircle) {
			 animateLightCircle = false;
		 }
		 mSpeed = speed;
		 mTargetX = Xtarget;
		 mOriginalX = Xoriginal;
		 mGange1 = m1;
		 mGange2 = m2;
		 if (posX != 0f) {
			 if (posX != -1f)
				 AmPositionX = posX;
			 animateLight_enableX = false;
			 AmPositionX = 0f;
		 } else {
			 animateLight_enableX = true;
		 }
		 if (posY != 0f) { 
			 if (posY != -1f)
				 AmPositionY = posY;
			 animateLight_enableY = false;
		 } else { 
			 animateLight_enableY = true;
		 }
		 animateLight_repeats = repeat;
		 animateLight = true;
	 }

	 public void animateLightCircle(float speed, float radius, boolean repeats) {
		 if (animateLight) {
			 animateLight = false;
		 }
		 aCircleRad = radius;
		 aCircleSpeed = speed;
		 animateLightCircle_repeats = repeats;
		 animateLightCircle = true;
	 }

	 public void removeLightAnimation() {
		 animateLight = false;
		 animateLightCircle = false;
	 }


	 private void updateLightAnimation() {
		 //small lights animation

		 mPositionX += mDistanceX/mSpeed; //Distance divided by speed
		 //	    mPositionY = (float) (0.1* Math.sin(Math.PI*(Math.abs(mPositionX-mOriginalX))/mDistanceX)); 
		 //	    AmPositionY = (float) (0.2* Math.sin(0.3*(Math.abs(mPositionX-mOriginalX))/mDistanceX)); 
		 if (animateLight_enableY) {
			 AmPositionY = (float) (mGange1* Math.sin(mGange2*(Math.abs(mPositionX-mOriginalX))/mDistanceX)); 
		 }
		 //Assuming you want it to go to pi degrees
		 //Divide current distance by total distance * total angle reqd
		 if (animateLight_enableX) {
			 AmPositionX = (float) (mGange1* Math.sin(mGange2*(Math.abs(mPositionX-mOriginalX))/mDistanceX)); 
		 }
		 //Assuming you want it to go to pi degrees
		 //Divide current distance by total distance * total angle reqd

		 aCircleDeg +=  Gdx.graphics.getDeltaTime()*aCircleSpeed;

		 aCircleX = aCircleRad * (float)Math.cos(Math.toRadians(aCircleDeg)) + lightX;
		 aCircleY = aCircleRad * (float)Math.sin(Math.toRadians(aCircleDeg)) + lightY;

	 }

	 public void flickerLights(Light f) {
		 animateLightCircle(1200f, 0.015f, true);
		 f.setColor(f.getColor().r, f.getColor().g, f.getColor().b, f.getColor().a-0.08f);
	 }

		
//==================================================== getters and setters =============
	
	 
	 public void setLightY(float newLightY) {
		 lightY = newLightY;
	 }
	 public void setLightX(float newLightX) {
		 lightX = newLightX;
	 }
	 public float getCircleX() {
		 if (!animateLight || !animateLightCircle) {
			 if (animateLight_repeats || animateLightCircle_repeats) {
				 return aCircleX;
			 } else {
				 if (elapsedTime < 2.5f) {
					 return aCircleX;
				 } else {
					 return lightX;
				 }
			 }
		 } else {
			 return lightX;
		 }
	 }
	 public float getCircleY() {
		 if (!animateLight || !animateLightCircle) {
			 if (animateLight_repeats || animateLightCircle_repeats) {
				 return aCircleY;
			 } else {
				 if (elapsedTime < 2.5f) {
					 return aCircleY;
				 } else {
					 return lightY;
				 }
			 }
		 } else {
			 return lightY;
		 }
	 }
	 public float getElapsedTime() {
		 return elapsedTime;
	 }

}
