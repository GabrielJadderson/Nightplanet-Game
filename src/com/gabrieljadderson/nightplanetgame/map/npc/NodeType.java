package com.gabrieljadderson.nightplanetgame.map.npc;

/**
 * The enumerated type whose elements represent the different types of
 * {@link Node} implementations.
 *
 * @author Gabriel Jadderson
 */
public enum NodeType
{
	
	/**
	 * The element used to represent the {@link ItemNode} implementation.
	 */
	ITEM,
	
	/**
	 * The element used to represent the {@link ObjectNode} implementation.
	 */
	OBJECT,
	
	/**
	 * The element used to represent the {@link Player} implementation.
	 */
	PLAYER,
	
	/**
	 * The element used to represent the {@link Npc} implementation.
	 */
	NPC
}