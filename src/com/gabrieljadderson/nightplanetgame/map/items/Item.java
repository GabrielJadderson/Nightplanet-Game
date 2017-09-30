package com.gabrieljadderson.nightplanetgame.map.items;

import com.gabrieljadderson.nightplanetgame.GameConstants;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Item {
	
    private ItemDefinition itemDef;
    private TextureRegion sprite;
    private Body body;
    private Vector2 size;
    
    protected Item() {
    }
    
    protected void init(ItemDefinition def, Body body, float pixelsPerMeter) {
        this.itemDef = def;
        this.body = body;
        
        this.body.setUserData("id:" + def.getId() + ":name:" + def.getName());
        
        size = new Vector2(def.getV_size().x / pixelsPerMeter, def.getV_size().y / pixelsPerMeter);
    }
    
    public void reset(World world) {
        itemDef = null;
        sprite = null;
        size = null;
      
        world.destroyBody(body);
        body = null;
    }
    
    public void update() {
    }
    
    public void render(Batch batch) {
        if (sprite != null) {
            float x = getX();
            float y = getY();
            float width = size.x;
            float height = size.y;
            float originX = width / 2f;
            float originY = height / 2f;
            float scaleX = 4f;
            float scaleY = 2.4f;
            float rotation = getAngle() * MathUtils.radDeg;
            batch.draw(sprite, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
//            batch.draw(, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
            
        }
    }
    
    public TextureRegion getSprite() {
        return sprite;
    }
    
    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }
    
    public float getX() {
        return body.getPosition().x;
    }
    
    public float getY() {
        return body.getPosition().y;
    }
    
    public float getAngle() {
        return body.getAngle();
    }
    
    public float getWidth() {
        return size.x;
    }
    
    public float getHeight() {
        return size.y;
    }
    
	public int getID() {
		return itemDef.getId(); 
	}
    
    public Object getUserData() {
        return body.getUserData();
//    	return null; //DEBUG
    }
    
    public void setUserData(Object val) {
    	body.setUserData(val);;
    }
    
    // ----- Static Part of the Class ----- \\
    
    @SuppressWarnings("unchecked")
	public static <T extends Item> T create(ItemDefinition def, World world, float pixelsPerMeter) {
        Item itemi = null;
    	if (GameConstants.items.containsKey(def)) {
    		itemi = GameConstants.items.get(def);
    	} else {
    		itemi = new ItemObject();
    	}
        
        if (def.getV_size().x == -1f && def.getV_size().y == -1f) {
            def.getV_size().set(pixelsPerMeter, pixelsPerMeter);
        }
        
        if (def.bounds.x == -1f && def.bounds.y == -1f && def.bounds.width == -1f && def.bounds.height == -1f) {
            def.bounds.set(0f, 0f, pixelsPerMeter - 2.5f, pixelsPerMeter - 2.5f);
        }
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.bullet = def.isBullet();
        bodyDef.fixedRotation = def.isFixedRotation();
        bodyDef.position.set(def.getV_position().x / pixelsPerMeter, def.getV_position().y / pixelsPerMeter);
        bodyDef.type = def.getBodyType();
        
        PolygonShape shape = new PolygonShape();
        float width = def.getBounds().width / pixelsPerMeter;
        float height = def.getBounds().height / pixelsPerMeter;
        float x = ((def.getV_size().x / pixelsPerMeter) - width) / 2f;
        float y = ((def.getV_size().y / pixelsPerMeter) - height) / 2f;
        shape.setAsBox(width / 2f, height / 2f, new Vector2(x + width / 2f, y + height / 2f), 0f); //check it out 
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = def.getDensity();
        fixtureDef.friction = def.getFriction();
        fixtureDef.restitution = def.getRestitution();
        fixtureDef.shape = shape;
        
        Body body = world.createBody(bodyDef);
//        body.setUserData(def.getID()); // moved to item.init
        body.createFixture(fixtureDef);
        itemi.init(def, body, pixelsPerMeter);
        return (T) itemi;
    }
    

    public static ItemDefinition destroy(Item item) {
    	item.body.setUserData("item:delete:" + String.valueOf(item.getID()));
        return item.itemDef;
    }

}
