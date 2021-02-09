package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpeedEffect extends Effect{

    /**
     * Creates a new Speed effect with the default sprite
     * @author Dragos Stoican
     */
    public SpeedEffect() {
        this.texture = new Texture("Buffs/Speed.png");
        this.sprite = new Sprite(texture);
    }

    /**
     * Creates a new Speed effect with the default asset and a custom duration
     * @author Dragos Stoican
     */
    public SpeedEffect(float duration) {
        this();
        this.duration = duration;
    }

    /**
     * Modifies the max_speed, acceleration and speed of a boat
     * @param boat The boat to apply the effect to
     * @author Dragos Stoican
     */
    @Override
    public void applyEffect(Boat boat) {
        boat.speed = Math.max(10, boat.speed);
        boat.acceleration = .4f;
        boat.max_speed = 25f;

        super.applyEffect(boat);
    }
}
