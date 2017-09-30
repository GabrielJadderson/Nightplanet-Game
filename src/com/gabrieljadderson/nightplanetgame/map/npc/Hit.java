package com.gabrieljadderson.nightplanetgame.map.npc;

/**
 * The container class that represents a hit that can be dealt on a
 * {@link CharacterNode}.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class Hit {

    /**
     * The amount of damage within this hit.
     */
    private final float damage;

    /**
     * The hit type represented by this hit.
     */
    private final HitType type;

    /**
     * Creates a new {@link Hit}.
     *
     * @param f
     *            the amount of damage within this hit.
     * @param type
     *            the hit type represented by this hit.
     */
    public Hit(float f, HitType type) {
        if (f == 0 && type == HitType.NORMAL) {
            type = HitType.BLOCKED;
        } else if (f > 0 && type == HitType.BLOCKED) {
            f = 0;
        } else if (f < 0) {
            f = 0;
        }
        this.damage = f;
        this.type = type;
    }

    /**
     * Creates a new {@link Hit} with a {@code type} of {@code NORMAL}.
     *
     * @param damage
     *            the amount of damage within this hit.
     */
    public Hit(float damage) {
        this(damage, HitType.NORMAL);
    }

    /**
     * A substitute for {@link Object#clone()} that creates another 'copy' of
     * this instance. The created copy is <i>safe</i> meaning it does not hold
     * <b>any</b> references to the original instance.
     *
     * @return a reference-free copy of this instance.
     */
    public Hit copy() {
        return new Hit(damage, type);
    }

    /**
     * Gets the amount of damage within this hit.
     *
     * @return the amount of damage within this hit.
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Gets the hit type represented by this hit.
     *
     * @return the hit type represented by this hit.
     */
    public HitType getType() {
        return type;
    }
}
