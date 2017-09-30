package com.gabrieljadderson.nightplanetgame.graphics.anim;

import java.util.Map.Entry;

import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
 * This class handles all the NPC rendering
 * @author Gabriel Howard Jadderson
 * @since 26/01/2016
 * @version 0.1
 * TODO:
 */
public class NpcRenderer {
	
	SpriteBatch batch;
	
	public NpcRenderer() {
		batch = new SpriteBatch(100); //100 npcs
	}
	
	public void render() {
		batch.setProjectionMatrix(GameConstants.camera.combined); //set projection

		batch.begin();//begin rendering npcs

		for (Entry<Integer, NPC> entries : GameConstants.NPCs.entrySet()) {
			NPC npc = entries.getValue();

			if (npc.getDefinition().isActive()) {
				npc.render(batch);
			}
		}

		batch.end();
		
		//begin rendering npc health and etc.
		for (Entry<Integer, NPC> entries : GameConstants.NPCs.entrySet()) {
			NPC npc = entries.getValue();

			if (npc.getDefinition().isRegistered()) {
				npc.health.render(npc.getDefinition().getBody().getPosition().x, npc.getDefinition().getBody().getPosition().y); 
			}
		}
		
		
			
	}

	
	public void createAI() {
		if (GameConstants.NPCs.size() > 0) {
			for (Entry<Integer, NPC> entries : GameConstants.NPCs.entrySet()) {
				NPC npc = entries.getValue();
				if (npc != null && npc.getDefinition() != null) {
					npc.setTarget();
				}
			}
		}
	}
	


}
