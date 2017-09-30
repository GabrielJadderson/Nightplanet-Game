package com.gabrieljadderson.nightplanetgame.map.items;

import com.badlogic.gdx.graphics.Color;

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
 * the enumerated type that holds the all the available qualities an item can have.
 * @author Gabriel Howard Jadderson
 * @since 15/12/2015
 * @version 0.1
 */
public enum ItemQuality {
	
    COMMON(0, Color.CLEAR),
    SUPERIOR(1, Color.SKY),
    RARE(2, Color.RED),
    EPIC(3, Color.LIME),
    SET_PIECE(4, Color.YELLOW);

    /**
     * The identification for this update flag.
     */
    private final int id;
    
    /**
     * The identification for this update flag.
     */
    private final Color color;

    /**
     * Creates a new {@link Flag}.
     *
     * @param id
     *            the identification for this update flag.
     */
    private ItemQuality(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    /**
     * Gets the identification for this update flag.
     *
     * @return the identification for this update flag.
     */
    public final int getId() {
        return id;
    }
    
    /**
     * gets the color for this enum flag.
     * 
     * @return the color that this enum flag holds.
     */
    public final Color getColor() {
    	return color;
    }
    
    /**
     * gets the enum that contains this int value. 
     * 
     * @param val the int value to search for.
     * @return the enum that holds that value. 
     */
    public static ItemQuality getByValue(int val) { 
    	for (ItemQuality c : values()) {
    		if (c.getId() == val) {
    			return c;
    		}
    	}
    	return null;
    } 

    /**
     * Gets the size of this enumerated type.
     *
     * @return the size.
     */
    public static int size() {
        return ItemQuality.values().length;
    }

}
