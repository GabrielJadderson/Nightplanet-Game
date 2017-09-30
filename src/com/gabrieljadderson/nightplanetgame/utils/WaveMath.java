package com.gabrieljadderson.nightplanetgame.utils;

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
 * * Neither the name of 'NightPlanet' nor the names of its contributors
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
 * @since 25/03/2016
 * @version 0.1
 */
public class WaveMath
{
	
	/**
	 * pi 3.14159265358979323846
	 */
	final float PI = 3.14159265358979323846f;
	/**
	 * 2pi  6.28318530718
	 */
	final float PI2 = PI * 2;
	
	/**
	 * the sample rate usually around 44100 cycles per second. Default: 50000 cycles per second.
	 */
	float SAMPLE_RATE = 50000f;
	
	/**
	 * the frequency in Hz. Default: 100 Hz
	 */
	float f = 100f;
	/**
	 * phase or  phi. Default: 0
	 */
	float phase = 0f;
	/**
	 * the Peak Amplitude
	 */
	float A = 10f;
	/**
	 * the Amplitude Value - (THE MAIN VALUE)
	 */
	float y = 0f;
	
	/**
	 * Generates a <b>Square</b> wave
	 * @param frequency the frequency in Hz.
	 * @param amplitude the peak/max amplitude of the wave.
	 * @param doubleSampled true if you the phase should be double sampled, false otherwise - double sampling increases speed of the wave and nothing else.
	 * @return The amplitude value of the square wave.
	 */
	public float squareWave(float frequency, float amplitude, boolean doubleSampled)
	{
		this.f = frequency;
		this.A = amplitude;
		if (doubleSampled)
			phase = phase + ((2 * (float) Math.PI * f) / SAMPLE_RATE);
		
		phase += (PI2 * f) / SAMPLE_RATE;
		while (phase > PI2)
			phase -= PI2;
		
		if (phase < (float) Math.PI)
			y = A;
		else
			y = -A;
		
		return y;
	}
	
	/**
	 * Generates a <b>Sine</b> wave
	 * @param frequency the frequency in Hz.
	 * @param amplitude the peak/max amplitude of the wave.
	 * @param doubleSampled true if you the phase should be double sampled, false otherwise - double sampling increases speed of the wave and nothing else.
	 * @return The amplitude value of the sine wave.
	 */
	public float sineWave(float frequency, float amplitude, boolean doubleSampled)
	{
		this.f = frequency;
		this.A = amplitude;
		if (doubleSampled)
			phase = phase + ((2 * (float) Math.PI * f) / SAMPLE_RATE);
		
		phase += (PI2 * f) / SAMPLE_RATE;
		while (phase > PI2)
			phase -= PI2;
		
		y = (float) (A * Math.sin(phase));
		
		return y;
	}
	
	/**
	 * Generates a <b>SawTooth</b> wave
	 * @param frequency the frequency in Hz.
	 * @param amplitude the peak/max amplitude of the wave.
	 * @param doubleSampled true if you the phase should be double sampled, false otherwise - double sampling increases speed of the wave and nothing else.
	 * @return The amplitude value of the sawtooth wave.
	 */
	public float sawToothWave(float frequency, float amplitude, boolean doubleSampled)
	{
		this.f = frequency;
		this.A = amplitude;
		if (doubleSampled)
			phase = phase + ((2 * (float) Math.PI * f) / SAMPLE_RATE);
		
		phase += (PI2 * f) / SAMPLE_RATE;
		while (phase > PI2)
			phase -= PI2;
		
		y = A - (A / (float) Math.PI * phase);
		
		return y;
	}
	
	/**
	 * Generates a <b>Triangle</b> wave
	 * @param frequency the frequency in Hz.
	 * @param amplitude the peak/max amplitude of the wave.
	 * @param doubleSampled true if you the phase should be double sampled, false otherwise - double sampling increases speed of the wave and nothing else.
	 * @return The amplitude value of the triangle wave.
	 */
	public float triangleWave(float frequency, float amplitude, boolean doubleSampled)
	{
		this.f = frequency;
		this.A = amplitude;
		if (doubleSampled)
			phase = phase + ((2 * (float) Math.PI * f) / SAMPLE_RATE);
		
		phase += (PI2 * f) / SAMPLE_RATE;
		while (phase > PI2)
			phase -= PI2;
		
		if (phase < (float) Math.PI)
			y = -A + (2 * A / (float) Math.PI) * phase;
		else
			y = 3 * A - (2 * A / (float) Math.PI) * phase;
		
		return y;
	}
	
	/**
	 * Sets a new Sample Rate, the Default {@link #SAMPLE_RATE} is 50000 cycles per second.
	 * <p>
	 * Setting a new {@link #SAMPLE_RATE} effects <b>ALL</b> wave types.
	 * @param newSampleRate
	 */
	public void setSampleRate(float newSampleRate)
	{
		SAMPLE_RATE = newSampleRate;
	}
	
	/**
	 * Gets the current Sample Rate
	 * @return The <b>{@link #SAMPLE_RATE}</b>
	 */
	public float getSampleRate()
	{
		return SAMPLE_RATE;
	}
	
	
}
