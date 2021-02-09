package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ManeuverabilityEffect extends Effect{

    /**
     * Creates a new maneuverability effect with the default sprite
     * @author Dragos Stoican
     */
    public ManeuverabilityEffect() {
        this.texture = new Texture("Buffs/Maneuverability.png");
        this.sprite = new Sprite(texture);
    }

    /**
     * Creates a new maneuverability effect with the default sprite, custom duration
     * @author Dragos Stoican
     */
    public ManeuverabilityEffect(float duration) {
        this();
        this.duration = duration;
    }

    /**
     * Changes the maneuverability of the boat
     * @param boat The boat to apply the effect to
     * @author Dragos Stoican
     */
    @Override
    public void applyEffect(Boat boat) {
        boat.maneuverability = 3.5f;

        super.applyEffect(boat);
    }
}
