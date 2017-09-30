package com.gabrieljadderson.nightplanetgame.tests.net.signup;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
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
 * this class !!!! HAS TO BE IDENTICAL TO THE ONE ON THE CLIENT, AND VICE VERSA !!!! must be identical or it will not work. the order that the kryo serialization-objects MATTER!!
 * they have to be exact, can't stress this enough. 
 * @author Gabriel Jadderson
 * @since 20/02/2016
 * @version 0.1
 * TODO:
 */
public class SignUpNetwork
{
	
	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint)
	{
		Kryo kryo = endPoint.getKryo();
		kryo.register(RegistrationRequired.class);
		kryo.register(byte[].class);
		kryo.register(Register.class);
		kryo.register(RegistrationComplete.class);
		kryo.register(RegistrationFailed.class);
	}
	
	static public class RegistrationRequired
	{
	}
	
	static public class Register
	{
		String UIN;
		String SID;
		String name;
		String email;
		byte[] salt;
		byte[] password;
	}
	
	static public class RegistrationComplete
	{
	}
	
	static public class RegistrationFailed
	{
	}
	
}
