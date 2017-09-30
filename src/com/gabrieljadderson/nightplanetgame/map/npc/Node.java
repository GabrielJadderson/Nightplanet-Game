package com.gabrieljadderson.nightplanetgame.map.npc;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.utils.Position;

/**
 * The parent class that represents anything that can be interacted with. This
 * includes {@link ItemNode}s, {@link ObjectNode}s, {@link Player}s, and
 * {@link Npc}s.
 * @author Gabriel Jadderson
 */
public abstract class Node
{
	
	/**
	 * The size of the node in the world.
	 */
	protected Vector2 size = new Vector2(-1f, -1f);
	;
	
	private Rectangle bounds = new Rectangle(-1f, -1f, -1f, -1f);
	
	/**
	 * The position of this node in the world.
	 */
	private Position position = new Position(0, 0);
	
	/**
	 * The type of node that this node is.
	 */
	private NodeType type;
	
	/**
	 * the id of the item, player or npc.
	 */
	private Object id;
	
	/**
	 * Determines if this node has been registered or not. TODO: make a new become registered when it is added in the world.
	 */
	private boolean registered;
	
	//NOT USED
	public Node()
	{
		//NOt USED?
	}
	
	protected void create(Object id, NodeType type, Vector2 position, Vector2 size)
	{
		this.id = id;
		this.type = type;
		this.size = size;
		this.position = new Position(position);
//        this.position = position;
		
		if (type == NodeType.PLAYER)
		{
			this.bounds.set(0f, 0f, GameConstants.pixelsPerMeter - 2.5f, GameConstants.pixelsPerMeter - 2.5f);
			this.size = new Vector2(size.x / GameConstants.pixelsPerMeter, size.y / GameConstants.pixelsPerMeter);
		}
	}
	
	/**
	 * Gets the position of this node in the world.
	 *
	 * @return the position of this node in the world.
	 */
	public Position getPosition()
	{
		return position;
	}
	
	/**
	 * Gets the size of this node in the world.
	 *
	 * @return the size of this node in the world.
	 */
	public Vector2 getSize()
	{
		return size;
	}
	
	/**
	 * Sets the value for {@link Node#size}.
	 *
	 * @param size the new value to set.
	 */
	public void setSize(Vector2 size)
	{
		this.size = size;
	}
	
	/**
	 * Sets the value for {@link Node#size}.
	 *
	 * @param size the new value to set.
	 */
	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	
	/**
	 * @return
	 */
	public Object getId()
	{
		return id;
	}
	
	/**
	 * @param nv
	 */
	public void setId(Object nv)
	{
		id = nv;
	}
	
	/**
	 * Gets the type of node that this node is.
	 *
	 * @return the type of node that this node is.
	 */
	public NodeType getType()
	{
		return type;
	}
	
	public boolean isRegistered()
	{
		return registered;
	}
	
	public void setRegistered(boolean registered)
	{
		this.registered = registered;
	}
	
}