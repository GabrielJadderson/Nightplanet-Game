package com.gabrieljadderson.nightplanetgame.map.npc;

import com.badlogic.gdx.math.Vector2;
import com.gabrieljadderson.nightplanetgame.utils.JsonLoader;
import com.gabrieljadderson.nightplanetgame.utils.Position;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Objects;

/**
 * The {@link JsonLoader} implementation that loads all npc nodes.
 *
 * @author Gabriel Jadderson
 */
public final class NpcNodeLoader extends JsonLoader
{
	
	/**
	 * Creates a new {@link NpcNodeLoader}.
	 */
	public NpcNodeLoader()
	{
		super("npc_nodes.json");
	}
	
	@Override
	public void load(JsonObject reader, Gson builder)
	{
		int id = reader.get("id").getAsInt();
		Position position = Objects.requireNonNull(builder.fromJson(reader.get("position").getAsJsonObject(), Position.class));
		Vector2 size = new Vector2(reader.get("position X").getAsFloat(), reader.get("position Y").getAsFloat());
		boolean coordinate = reader.get("random-walk").getAsBoolean();
		int radius = reader.get("walk-radius").getAsInt();
		Preconditions.checkState(!(coordinate && radius == 0));
		Preconditions.checkState(!(!coordinate && radius > 0));
//        Npc npc = new Npc(id, position, size);
//        npc.setOriginalRandomWalk(coordinate);
//        npc.getMovementCoordinator().setCoordinate(coordinate);
//        npc.getMovementCoordinator().setRadius(radius);
//        npc.setRespawn(false);
//        npc.setRespawn(true);
//        if (!World.getNpcs().add(npc))
//            throw new IllegalStateException("NPC could not be added to the " + "world!");
	}
}