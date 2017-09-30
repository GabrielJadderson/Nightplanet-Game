package com.gabrieljadderson.nightplanetgame.graphics.renderpipeline;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.SpriteSheet;
import com.gabrieljadderson.nightplanetgame.graphics.anim.NPC;
import com.gabrieljadderson.nightplanetgame.graphics.objects.GObject;
import com.gabrieljadderson.nightplanetgame.map.items.ItemDefinition;
import com.gabrieljadderson.nightplanetgame.map.items.ItemObject;
import com.gabrieljadderson.nightplanetgame.player.Player;
import com.gabrieljadderson.nightplanetgame.utils.WaveMath;

import java.util.Map.Entry;

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
 * The RenderManager is responsible for all in-game rendering.
 *
 * @author Gabriel Jadderson
 * @version 0.1
 * TODO: RENDER PLAYERS AND SHADERS.
 * @since 18/03/2013
 */
public class RenderManager
{
	
	/**
	 *
	 */
	private int flatPass = 0;
	private int scanPass = 0;
	private int renderPass = 0;
	
	/**
	 * used to decrement the update loop
	 */
	float updateDecrementer = 1f;
	/**
	 * update speed
	 */
	float updateSpeed = 0.15f;
	
	/**
	 * proximity - the distance to scan
	 */
	private final float promixity = 15f;
	
	/**
	 * SpriteBatches
	 * TODO: put batches in here - all of them.
	 */
	Batch batch_NPC = new SpriteBatch();
	Batch batch_Players = new SpriteBatch();
	Batch batch_Objects = new SpriteBatch();
	Batch batch_Shaders = new SpriteBatch();
	Batch batch_Items = new SpriteBatch();
	
	/**
	 * Rendering Arrays, the arrays contain only the elements that require rendering.
	 * NOTE: the capacity of each array. that is the capacity of each element in the proximity given.
	 * some elements will be deleted as the player moves away from them, while in turn others get added.
	 */
	private Array<NPC> NPCs_ToRender = new Array<NPC>(10);
	private Array<GObject> Objects_ToRender = new Array<GObject>(10);
	private Array<ItemObject> Items_ToRender = new Array<ItemObject>(10);
	private Array<Player> Players_ToRender = new Array<Player>(10);
//	private Array<Shader> Shaders_ToRender = new Array<Shader>(10);
	
	
	//test
	public ShaderProgram currentShader;
	public ShaderProgram dayShader;
	public ShaderProgram nightShader;
	public ShaderProgram lightShader;
	public ShaderProgram finalShader;
	//values passed to the shader
	public static final float ambientIntensity = 0.7f;
	public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f); //the ambient color //randomize the colors depending on server day/night time.
	//
	public boolean lightMove = false;
	public boolean lightOscillate = false;
	public FrameBuffer fbo;
	public Texture lighttex;
	private WaveMath wave = new WaveMath();
	//test
	
	public RenderManager()
	{
		
		//constructors
		
		//test
		ShaderProgram.pedantic = false;
		dayShader = GameConstants.shaderManager.get("DayShader");
		nightShader = GameConstants.shaderManager.get("NightShader");
		lightShader = GameConstants.shaderManager.get("LightShader");
		finalShader = GameConstants.shaderManager.get("FinalShader");
		
		nightShader.begin();
		nightShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y, ambientColor.z, ambientIntensity);
		nightShader.end();
		
		lightShader.begin();
		lightShader.setUniformi("u_lightmap", 1);
		lightShader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		lightShader.end();
		
		finalShader.begin();
		finalShader.setUniformi("u_lightmap", 1);
		finalShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y, ambientColor.z, ambientIntensity);
		finalShader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		finalShader.end();
		
		fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		lighttex = SpriteSheet.loadPNG_tex(ResourceCrawler.getRes("light.png"));
		//test
		
	}
	
	
	public void startWorldRendering()
	{
		
		scan();
	}
	
	public void updateWorldRendering(float delta)
	{
		
		//TODO: when a player approaches or an npc approaches then the scan has to restart. so we can get them rendered. 
		if (GameConstants.player.isMoving() || scanPass < 5)
		{ //scanpass < 5 allows us to perform 5 quick scans upon intiation that way we can quickly scan the area on startup.
			if (updateDecrementer <= 0)
			{
				scan();
				scanPass++;
				updateDecrementer = 1f;
//				System.out.println("shaders to render: " + Shaders_ToRender.size);
				System.out.println("players to render: " + Players_ToRender.size);
				System.out.println("items to render: " + Items_ToRender.size);
				System.out.println("npcs to render: " + NPCs_ToRender.size);
				System.out.println("objects to render: " + Objects_ToRender.size);
				System.out.println("scanPass: " + scanPass);
			} else
			{
				updateDecrementer = updateDecrementer * updateSpeed;
			}
		}
	}
	
	public void renderWorld(Batch MAIN_BATCH)
	{
		
		//test
		Vector3 mouse = GameConstants.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		
		fbo.begin();
//		batch.setProjectionMatrix(camera.combined);
		MAIN_BATCH.setShader(dayShader);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		MAIN_BATCH.begin();
		MAIN_BATCH.setProjectionMatrix(GameConstants.camera.combined);
		float lightSize = lightOscillate ? (3.75f + 0.15f * (float) Math.sin(wave.sineWave(70000, 30, false))) : 5.0f;
		MAIN_BATCH.draw(lighttex, mouse.x - lightSize * 0.5f + 0.5f, mouse.y + 0.5f - lightSize * 0.5f, lightSize, lightSize);
		MAIN_BATCH.end();
		fbo.end();
		
		
		MAIN_BATCH.begin();
		
		renderObjects(); //objects	1
		renderItems(); //items 		2
		
		renderNPCs(); //npcs 		3
		renderPlayers(); //players	4

		/* shader rendering disabled */
//		renderShaders(); //shaders 	5
		
		MAIN_BATCH.end();
//		
//		
//		//draw the actual scene
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		MAIN_BATCH.setProjectionMatrix(GameConstants.camera.combined);
		MAIN_BATCH.setShader(currentShader);
		MAIN_BATCH.begin();
		fbo.getColorBufferTexture().bind(1); //this is important! bind the FBO to the 2nd texture unit // fbo = 2 lighttex = 0
		lighttex.bind(0); //we force the binding of a texture on first texture unit to avoid artefacts
//					   //this is because our default and ambiant shader dont use multi texturing...
//					   //youc can basically bind anything, it doesnt matter
////		tilemap.render(batch, dt);
		MAIN_BATCH.end();
		//test
		
		
	}
	
	/**
	 * shaders dont need their own batch for rendering, each shader initiates its own batch
	 * TODO: fix shaders work on getting this to work for all shaders in the world.
	 */
	private void renderShaders()
	{
//		for (Shader shader : Shaders_ToRender) {
//			shader.renderShader(null, 0f, 0f);
//		}
	}
	
	private void renderNPCs()
	{
		batch_NPC.setProjectionMatrix(GameConstants.camera.combined); //set projection
		
		batch_NPC.begin();
		
		for (NPC npc : NPCs_ToRender)
		{
			if (npc.getDefinition().isActive())
			{
				npc.render(batch_NPC);
			}
		}
		
		batch_NPC.end();
		
		//render the health of each npc, TODO: -> possible refactoring of this method is required.
		for (NPC npc : NPCs_ToRender)
		{
			if (npc.getDefinition().isRegistered())
			{
				npc.getHealth().render(npc.getDefinition().getBody().getPosition().x, npc.getDefinition().getBody().getPosition().y);
			}
		}
		
	}
	
	private void renderItems()
	{
		batch_Items.setProjectionMatrix(GameConstants.camera.combined);
		
		batch_Items.begin();
		
		for (ItemObject item : Items_ToRender)
		{
			if (item.getUserData() != null)
			{
				item.render(batch_Items);
			}
		}
		
		batch_Items.end();
	}
	
	private void renderObjects()
	{
		batch_Objects.setProjectionMatrix(GameConstants.camera.combined);
		
		
		batch_Objects.begin();
		
		for (GObject gObject : Objects_ToRender)
		{
			if (gObject.isActive())
			{
				gObject.render(batch_Objects);
			}
		}
		
		batch_Objects.end();
		
	}
	
	/**
	 * TODO: render all players, currently render only the main player
	 */
	private void renderPlayers()
	{
		batch_Players.setProjectionMatrix(GameConstants.camera.combined);
		
		batch_Players.begin();
		
		GameConstants.player.render(batch_Players);
		
		batch_Players.end();

//		for (Player player : Players_ToRender) {
//			
//		}
		
		
	}
	
	/**
	 * scans the proximity for npcs, objects, players, shaders etc.
	 */
	private void scan()
	{
		//scan...
		
		//scanning for npcs
		for (Entry<Integer, NPC> set : GameConstants.NPCs.entrySet())
		{
			NPC npc = set.getValue();
			if (npc.getDefinition().isActive())
			{
				if (npc.getDefinition().getPosition().withinDistance(GameConstants.player.getPosition(), promixity))
				{
					if (!NPCs_ToRender.contains(npc, true))
					{
						NPCs_ToRender.add(npc);
					} else
					{
						continue;
					}
				} else
				{
					if (NPCs_ToRender.contains(npc, true))
					{
						if (NPCs_ToRender.removeValue(npc, true))
						{
							continue;
						} else
						{
							System.err.println("NPC NOT REMOVED FROM RENDER MANGAER - MAJOR ERROR");
						}
					}
				}
			}
		}
		
		//scanning for items
		for (Entry<ItemDefinition, ItemObject> set2 : GameConstants.items.entrySet())
		{
			if (set2.getValue().isActive())
			{
				if (set2.getValue().getPosition().withinDistance(GameConstants.player.getPosition(), promixity))
				{
					if (!Items_ToRender.contains(set2.getValue(), true))
					{
						Items_ToRender.add(set2.getValue());
					} else
					{
						continue;
					}
				} else
				{
					if (Items_ToRender.contains(set2.getValue(), true))
					{
						if (Items_ToRender.removeValue(set2.getValue(), true))
						{
							continue;
						} else
						{
							System.err.println("ITEM NOT REMOVED FROM RENDER MANAGER - MAJOR ERROR");
						}
					}
				}
			}
		}
		
		//scaning for objects.
		for (Entry<Integer, GObject> set3 : GameConstants.GameObjects.entrySet())
		{
			if (set3.getValue().getObjectDef().getPosition().withinDistance(GameConstants.player.getPosition(), promixity))
			{
				if (!Objects_ToRender.contains(set3.getValue(), true))
				{
					Objects_ToRender.add(set3.getValue());
				} else
				{
					continue;
				}
			} else
			{
				if (Objects_ToRender.contains(set3.getValue(), true))
				{
					if (Objects_ToRender.removeValue(set3.getValue(), true))
					{
						continue;
					} else
					{
						System.err.println("OBJECT NOT REMOVED FROM RENDER MANAGER - MAJOR ERROR");
					}
				}
			}
		}
		
		//TODO: scan for players
		
		//TODO: scan for shaders.
		
		
	}
	
	
	public void updateAllCameras()
	{
		GameConstants.camera.update();
		GameConstants.hudCamera.update();
	}
	
	//--------------------------------- GET / SET -----------------------------------------------------------------------------
	public float getProximity()
	{
		return this.promixity;
	}
	
}
