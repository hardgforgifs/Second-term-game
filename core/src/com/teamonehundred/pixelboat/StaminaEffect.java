package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StaminaEffect extends Effect{

    /**
     * Creates a new Stamina effect with the default sprite
     * @author Dragos Stoican
     */
    public StaminaEffect() {
        this.texture = new Texture("Buffs/Stamina.png");
        this.sprite = new Sprite(texture);
    }

    /**
     * Creates a new Stamina effect with the default asset and a custom duration
     * @author Dragos Stoican
     */
    public StaminaEffect(float duration) {
        this();
        this.duration = duration;
    }

    /**
     * Adds stamina to the boat
     * @param boat The boat to apply the effect to
     * @author Dragos Stoican
     */
    @Override
    public void applyEffect(Boat boat) {
        if (duration == 5f)
            boat.stamina += 1f - boat.stamina;

        super.applyEffect(boat);
    }
}
