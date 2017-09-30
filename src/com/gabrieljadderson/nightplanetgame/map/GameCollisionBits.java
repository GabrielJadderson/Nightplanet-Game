package com.gabrieljadderson.nightplanetgame.map;

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
 * An Abstract Class 'Containing' all of the short-Bits used for collision detection in the game.
 *
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO: describe each bit and group with comments.
 * @since 24/01/2016
 */
public abstract class GameCollisionBits
{
	
	public static final short NOTHING_BIT = 0;                    // 0000 0000 0000 0000  (HEX: 0xFFFF)
	public static final short WALL_COLLISION_BIT = 1;            // 0000 0000 0000 0001 (shifted 0 times) 16-bit short (HEX: 0x0001)
	public static final short PLAYER_COLLISION_BIT = 1 << 1;    // 0000 0000 0000 0010 (shifted 1 times) 16-bit short (HEX: 0x0002)
	public static final short NPC_COLLISION_BIT = 1 << 2;        // 0000 0000 0000 0100 (shifted 2 times) 16-bit short (HEX: 0x0004)
	public static final short LIGHT_BIT = 1 << 3;                // 0000 0000 0000 1000 (shifted 3 times) 16-bit short (HEX: 0x0008)
	public static final short BULLET_BIT = 1 << 4;                // 0000 0000 0001 0000 (shifted 4 times) 16-bit short (HEX: 0x00010)
	public static final short OBJECT_BIT = 1 << 5;                // 0000 0000 0010 0000 (shifted 5 times) 16-bit short (HEX: 0x00020)
	public static final short NOT_USED = 1 << 6;                // 0000 0000 0100 0000 (shifted 5 times) 16-bit short (HEX: 0x00020)
	public static final short NOT_USED2 = 1 << 7;                // 0000 0000 1000 0000 (shifted 5 times) 16-bit short (HEX: 0x00020)
	public static final short NOT_USED3 = 1 << 8;                // 0000 0001 0000 0000 (shifted 5 times) 16-bit short (HEX: 0x00020)
	
	
	public static final short GROUP_INDEX_NPC = -2;
	public static final short GROUP_INDEX_PLAYER = -1;
	public static final short GROUP_INDEX_WALL = -3;
	public static final short GROUP_INDEX_LIGHT = -4;
	public static final short GROUP_INDEX_BULLET = -5;
	public static final short GROUP_INDEX_OBJECT = -6;
	
	
	public static final short NPC_MASK_BIT = PLAYER_COLLISION_BIT | WALL_COLLISION_BIT | BULLET_BIT;
	public static final short WALL_MASK_BIT = NPC_COLLISION_BIT | LIGHT_BIT | PLAYER_COLLISION_BIT | BULLET_BIT;
	public static final short LIGHT_MASK_BIT = NPC_COLLISION_BIT | PLAYER_COLLISION_BIT | WALL_COLLISION_BIT;
	public static final short BULLET_MASK_BIT = NPC_COLLISION_BIT | WALL_COLLISION_BIT;
	public static final short PLAYER_MASK_BIT = NPC_COLLISION_BIT | WALL_COLLISION_BIT | LIGHT_BIT | BULLET_BIT;
	public static final short OBJECT_MASK_BIT = NPC_COLLISION_BIT | WALL_COLLISION_BIT | BULLET_BIT | PLAYER_COLLISION_BIT;
	
	
}
