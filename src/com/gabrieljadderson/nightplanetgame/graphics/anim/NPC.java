package com.gabrieljadderson.nightplanetgame.graphics.anim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.gabrieljadderson.nightplanetgame.ResourceCrawler;
import com.gabrieljadderson.nightplanetgame.graphics.Health;
import com.gabrieljadderson.nightplanetgame.graphics.SplashDamage;
import com.gabrieljadderson.nightplanetgame.map.npc.Hit;
import com.gabrieljadderson.nightplanetgame.map.npc.NpcDefinition;
import com.gabrieljadderson.nightplanetgame.spine.Skeleton;
import com.gabrieljadderson.nightplanetgame.spine.SkeletonData;
import com.gabrieljadderson.nightplanetgame.spine.SkeletonJson;
import com.gabrieljadderson.nightplanetgame.spine.SkeletonRenderer;
import com.gabrieljadderson.nightplanetgame.tests.ai.NPCAI;
import com.gabrieljadderson.nightplanetgame.utils.Dimension;
import com.gabrieljadderson.nightplanetgame.utils.Position;
import com.gabrieljadderson.nightplanetgame.utils.RandomGen;
import com.gabrieljadderson.nightplanetgame.utils.Stopwatch;

import java.util.concurrent.TimeUnit;


public class NPC
{
	
	Position pos_target = new Position();
	
	Stopwatch respawnTimer;
	Stopwatch attackTimer;
	
	NpcDefinition npcDef;
	
	float updateSpeed;
	
	Health health;
	
	Sound AttackSFX, DefenceSFX, DeathSFX;
	long ID_AttackSFX, ID_DefenceSFX, ID_DeathSFX;
	
	/**
	 * used for rendering the damage splash
	 */
	SplashDamage splashDamage;
	
	boolean assetsLoaded = false;
	
	boolean didDie = false;
	
	public void loadAssets(NpcDefinition npcDef)
	{
		if (assetsLoaded == false)
		{
			GameConstants.assetManager.loadFromCrawler(npcDef.getSkeletonAtlasPath(), TextureAtlas.class);
			GameConstants.assetManager.loadFromCrawler(npcDef.getAttackSoundPath(), Sound.class);
			GameConstants.assetManager.loadFromCrawler(npcDef.getDefenceSoundPath(), Sound.class);
			GameConstants.assetManager.loadFromCrawler(npcDef.getDeathSoundPath(), Sound.class);
			GameConstants.assetManager.finishLoading();
			GameConstants.assetManager.update();
			assetsLoaded = true;
		}
	}
	
	/**
	 * creates an npc from the given npcDefinition
	 *
	 * @param npcDef
	 */
	public void createNPC(NpcDefinition npcDef)
	{
		loadAssets(npcDef);
		
		this.npcDef = npcDef;
		this.updateSpeed = npcDef.getUpdateSpeed();
		npcDef.currentHealth = npcDef.getMaxHealth();
		
		npcDef.setSkeletonRenderer(new SkeletonRenderer());
		npcDef.getSkeletonRenderer().setPremultipliedAlpha(false);
		
		npcDef.setSkeletonAtlas(GameConstants.assetManager.get(ResourceCrawler.getResource(npcDef.getSkeletonAtlasPath())));
		
		SkeletonJson json = new SkeletonJson(npcDef.getSkeletonAtlas()); // This loads skeleton JSON data, which is stateless.
		json.setScale(npcDef.getSize()); // Load the skeleton at 50% the size it was in Spine.
		SkeletonData skeletonData = json.readSkeletonData(ResourceCrawler.getRes(npcDef.getSkeletonJsonPath()));
		npcDef.setAnimation(skeletonData.findAnimation(npcDef.getIdleAnimation()));
		
		npcDef.setSkeleton(new Skeleton(skeletonData)); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		npcDef.getSkeleton().setPosition(npcDef.getPosition().getX(), npcDef.getPosition().getY());
		npcDef.getSkeleton().updateWorldTransform();
		
		npcDef.setDimension(new Dimension(skeletonData.getWidth(), skeletonData.getHeight()));
		
		
		npcDef.setAI(new NPCAI(npcDef, null));
		
		health = new Health(npcDef.getMaxHealth());
		
		splashDamage = new SplashDamage();
		
		respawnTimer = new Stopwatch();
		attackTimer = new Stopwatch();
		
		AttackSFX = GameConstants.assetManager.get(ResourceCrawler.getResource(npcDef.getAttackSoundPath()), Sound.class);
		DefenceSFX = GameConstants.assetManager.get(ResourceCrawler.getResource(npcDef.getDefenceSoundPath()), Sound.class);
		DeathSFX = GameConstants.assetManager.get(ResourceCrawler.getResource(npcDef.getDeathSoundPath()), Sound.class);
		
	}
	
	public void setTarget()
	{
		Body targetBody = null;
		if (npcDef.TargetIsPlayer())
		{
			System.out.println("AI SET for player");
			targetBody = GameConstants.player.getBody();
		} else
		{
			if (npcDef.getTargetId() != 0)
			{
				targetBody = GameConstants.NPCs.get(npcDef.getTargetId()).getDefinition().getBody();
			}
		}
		assert targetBody != null;
		npcDef.getAI().setTarget(targetBody);
		npcDef.getAI().getCharacter().setBehavior(npcDef.getAI().getCharacter().ARRIVE_BEHAVIOR);
	}
	
	public void reset(World world)
	{
		npcDef.setRegistered(false);
//		TODO dispose correctly of everything.
		npcDef.getAI().dispose();
	}
	
	public void update()
	{
		if (npcDef.isRegistered())
		{
			npcDef.getAI().update();
			
			updateOrientation();
			
			if (npcDef.currentHealth != 0f)
			{
				health.update(npcDef.currentHealth);
			} else if (npcDef.currentHealth == 0f)
			{
//				System.err.println("NPC DEAD NIGGA HE DEAd");
				die();
				respawnTimer.reset();
			}
			
			/* scans the proximity for the target */
			if (npcDef.getAI().getTarget().getBody() != null && !didDie)
			{
				pos_target.setVector2Position(npcDef.getAI().getTarget().getBody().getWorldCenter());
				if (npcDef.getPosition().withinDistance(pos_target, 1.3f))
				{ //aggro Distance in floats TODO: add a float for this in npcDef.
					//TODO: change target for aggro
					//TEST
					if (attackTimer.elapsed(npcDef.getAttackSpeed(), TimeUnit.MILLISECONDS))
					{
						GameConstants.player.decrementHealth(new Hit(new RandomGen().floatRandom(npcDef.getMaxHit())));
						ID_AttackSFX = AttackSFX.play(0.1f);
						attackTimer.reset();
					}
				}
			}
			
		} else if (didDie)
		{
			if (npcDef.Respawns())
			{
//				System.out.println("Time Dead: " + respawnTimer.elapsedTime(TimeUnit.SECONDS));
				if (respawnTimer.elapsed((long) npcDef.respawnTime, TimeUnit.SECONDS))
				{
					reSpawn();
					return;
				}
				return;
			}
			return;
		}
		
		/* updating the splash Damage  */
		splashDamage.update(Gdx.graphics.getDeltaTime(), npcDef.getBody(), didDie);
	}
	
	public void render(Batch batch)
	{
//		float delta = Gdx.graphics.getDeltaTime();
//		float remaining = delta;
//		while (remaining > 0) {
//			float d = Math.min(updateSpeed, remaining);
//			update();
//			npcDef.animationTime += d;
//			remaining -= d;
//		}
		
		if (npcDef.isRegistered())
		{
			
			if (npcDef.getAnimation() != null)
			{
				npcDef.getAnimation().apply(npcDef.getSkeleton(), npcDef.animationTime, npcDef.animationTime, true, npcDef.getAnimationEvents());
				npcDef.getSkeleton().setX(npcDef.getAI().getPosition().getX() - 0.05f);
				npcDef.getSkeleton().setY(npcDef.getAI().getPosition().getY() - 0.25f);
				npcDef.getSkeleton().updateWorldTransform();
				npcDef.getSkeletonRenderer().draw(batch, npcDef.getSkeleton());
				
				splashDamage.render(batch, npcDef.getBody());
				
			} else
			{
				//TODO: draw a picture for bug finding, telling that the animation was not found.
			}
		}
		
	}
	
	public void updateOrientation()
	{
		if (npcDef.getBody().getAngle() > 0)
		{
			if (!npcDef.getSkeleton().getFlipX())
			{
				npcDef.getSkeleton().setFlipX(true);
			}
		} else
		{
			if (npcDef.getSkeleton().getFlipX())
			{
				npcDef.getSkeleton().setFlipX(false);
			}
		}
	}
	
	/**
	 * heals the character the amount of heal
	 *
	 * @param heal
	 */
	public void heal(int heal)
	{
		if ((npcDef.currentHealth + heal) > npcDef.maxHealth)
		{
			npcDef.currentHealth = npcDef.maxHealth;
			return;
		} else
		{
			npcDef.currentHealth += heal;
		}
	}
	
	/**
	 * takes in a hit, and decrements the health based on that hit and returns the hit instance.
	 *
	 * @param hit
	 * @return the hit instance itself again. to be reused or whatever
	 */
	public Hit decrementHealth(Hit hit)
	{
		ID_DefenceSFX = DefenceSFX.play(0.1f);
		splashDamage.newSplash(hit);
		if (hit.getDamage() > npcDef.getCurrentHealth())
			hit = new Hit(npcDef.getCurrentHealth(), hit.getType());
		npcDef.currentHealth -= hit.getDamage();
		return hit;
	}
	
	
	/**
	 * gets the Definition of this NPC
	 *
	 * @return the Npc Definition
	 */
	public NpcDefinition getDefinition()
	{
		return npcDef;
	}
	
	public void moveTo(int x, int y)
	{
		npcDef.getBody().setTransform(x, y, 0);
		npcDef.getPosition().setX(x);
		npcDef.getPosition().setY(y);
	}
	
	private void die()
	{
		if (!didDie)
		{
			npcDef.getAI().getCharacter().setBehavior(npcDef.getAI().getCharacter().IDLE_BEHAVIOR);
			npcDef.getAI().setTarget(null);
			npcDef.getAI().getCharacter().getBody().setActive(false);
			npcDef.setRegistered(false);
			ID_DeathSFX = DeathSFX.play(0.1f);
			didDie = true;
		}
	}
	
	private void reSpawn()
	{
		if (!npcDef.isRegistered())
		{
			npcDef.currentHealth = npcDef.maxHealth;
			health.reset();
			npcDef.getAI().getCharacter().getBody().setActive(true);
			npcDef.getAI().setTarget(GameConstants.player.getBody());
			npcDef.getAI().getCharacter().setBehavior(npcDef.getAI().getCharacter().ARRIVE_BEHAVIOR);
			npcDef.setRegistered(true);
			//TODO: play respawn sound?
			didDie = false;
		}
	}
	
	public Health getHealth()
	{
		return health;
	}
	
	
}
