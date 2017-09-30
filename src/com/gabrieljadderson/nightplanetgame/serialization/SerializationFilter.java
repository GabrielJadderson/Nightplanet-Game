package com.gabrieljadderson.nightplanetgame.serialization;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.reflect.Modifier;

/**
 * The serialization exclusion strategy that will filter certain fields from
 * being serialized by the player serializer.
 *
 * @author Gabriel Jadderson
 */
public class SerializationFilter implements ExclusionStrategy {
	
	/**
	 * class skipping ? 
	 */
    @Override
    public boolean shouldSkipClass(Class<?> c) {
        return false;
    }
    
    /**
     * skips static modifier and transient modifier fields or if the annotation serializationExclude is passed.
     */
    @Override
    public boolean shouldSkipField(FieldAttributes attr) {
//        return attr.hasModifier(Modifier.STATIC) || attr.hasModifier(Modifier.TRANSIENT) || attr.getAnnotation(SerializationExclude.class) != null;
        return attr.hasModifier(Modifier.TRANSIENT) || attr.getAnnotation(SerializationExclude.class) != null;
    }
}
