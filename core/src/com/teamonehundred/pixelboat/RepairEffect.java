package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RepairEffect extends Effect{

    /**
     * Creates a new Repair effect with the default sprite
     * @author Dragos Stoican
     */
    public RepairEffect() {
        this.texture = new Texture("Buffs/Repair.png");
        this.sprite = new Sprite(texture);
    }

    /**
     * Creates a new Repair effect with the default asset and a custom duration
     * @author Dragos Stoican
     */
    public RepairEffect(float duration) {
        this();
        this.duration = duration;
    }

    /**
     * Modifies the durability of the boat
     * @param boat The boat to apply the effect to
     * @author Dragos Stoican
     */
    @Override
    public void applyEffect(Boat boat) {
        if (duration == 5f)
            boat.durability += Math.min(.2f, 1f - boat.durability);

        super.applyEffect(boat);
    }
}
