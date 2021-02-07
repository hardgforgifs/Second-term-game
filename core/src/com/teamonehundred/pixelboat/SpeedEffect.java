package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpeedEffect extends Effect{

    public SpeedEffect() {
        this.isActive = true;
        this.duration = 5f;
        this.texture = new Texture("Buffs/Speed.png");
        this.sprite = new Sprite(texture);
    }

    public SpeedEffect(float duration) {
        this.duration = duration;
    }

    @Override
    public void applyEffect(Boat boat) {
        boat.speed = Math.max(10, boat.speed);
        boat.acceleration = .4f;
        boat.max_speed = 25f;

        super.applyEffect(boat);
    }
}
