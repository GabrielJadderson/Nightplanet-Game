
package com.gabrieljadderson.nightplanetgame.graphics.anim;

import com.gabrieljadderson.nightplanetgame.graphics.SpriteSheet;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class Animation extends com.badlogic.gdx.graphics.g2d.Animation {
    public static enum Type {
        WALK_NORTH, WALK_EAST, WALK_SOUTH, WALK_WEST;
    	
    }
    private float stateTime;
    
    public Animation(float frameDuration, Array<TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
        setPlayMode(PlayMode.LOOP);
    }
    
    public Animation(float frameDuration, Array<AtlasRegion> regions, boolean bool) {
        super(frameDuration, regions);
        if (bool) {
        	setPlayMode(PlayMode.LOOP);
        } else {
        	setPlayMode(PlayMode.NORMAL);
        }
	}


	public void update(float delta) {
        stateTime += delta;
    }
    
    public void reset() {
        stateTime = 0f;
    }
    
    public TextureRegion getKeyFrame() {
        return getKeyFrame(stateTime);
    }
    
    public float getStateTime() { 
    	return stateTime;
    }
    
    public int getKeyFrameIndex() {
        return getKeyFrameIndex(stateTime);
    }
    
    public boolean didFinish() {
    	return isAnimationFinished(stateTime);
    }
     
    /**
     * 
     * @param sheet
     * @param character value 0-77
     * @param male
     * @return
     */
    public static ObjectMap<Animation.Type, Animation> extract(SpriteSheet sheet, int character, boolean male) {
        final int gender = male ? 0 : 12;
        final int frames = 3;
        final int index = character * 3;
        
        ObjectMap<Animation.Type, Animation> animations = new ObjectMap<Animation.Type, Animation>();
        
        Array<TextureRegion> tempSpritesNorth = Animation.grabSprites(sheet, gender, index, frames);
        Array<TextureRegion> tempSpritesSouth = Animation.grabSprites(sheet, gender, index + 2, frames);
        Array<TextureRegion> tempSpritesWest = Animation.grabSprites(sheet, gender, index + 1, frames, true);
        Array<TextureRegion> tempSpritesEast = Animation.grabSprites(sheet, gender, index + 1, frames);
        
        Animation.addMiddleReversed(tempSpritesNorth, false);
        Animation.addMiddleReversed(tempSpritesSouth, false);
        Animation.addMiddleReversed(tempSpritesWest, false);
        Animation.addMiddleReversed(tempSpritesEast, false);
        
        animations.put(Animation.Type.WALK_NORTH, new Animation(1f / (frames * 2), tempSpritesNorth));
        animations.put(Animation.Type.WALK_SOUTH, new Animation(1f / (frames * 2), tempSpritesSouth));
        animations.put(Animation.Type.WALK_WEST, new Animation(1f / (frames * 2), tempSpritesWest));
        animations.put(Animation.Type.WALK_EAST, new Animation(1f / (frames * 2), tempSpritesEast));
        
        return animations;
    }
    
    private static Array<TextureRegion> grabSprites(SpriteSheet sheet, int startX, int y, int length) {
        return Animation.grabSprites(sheet, startX, y, length, false);
    }
    
    private static Array<TextureRegion> grabSprites(SpriteSheet sheet, int startX, int y, int length, boolean mirrored) {
        Array<TextureRegion> sprites = new Array<TextureRegion>();
        length += startX;
        for (int x = startX; x < length; x++) {
            sprites.add(sheet.getSprite(x, y, mirrored));
        }
        return sprites;
    }
    
    private static void addMiddleReversed(Array<TextureRegion> frames, boolean keepFirst) {
        if (frames.size < 3) {
            return;
        }
        Array<TextureRegion> middleReversed = new Array<TextureRegion>(frames);
        if (!keepFirst) {
            middleReversed.removeIndex(0);
        }
        middleReversed.removeIndex(middleReversed.size - 1);
        middleReversed.reverse();
        frames.addAll(middleReversed);
    }
    
}
