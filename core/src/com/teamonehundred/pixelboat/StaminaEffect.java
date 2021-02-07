package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StaminaEffect extends Effect{

    public StaminaEffect() {
        this.isActive = true;
        this.duration = 0f;
        this.texture = new Texture("Buffs/Stamina.png");
        this.sprite = new Sprite(texture);
    }

    public StaminaEffect(float duration) {
        this.duration = duration;
    }

    @Override
    public void applyEffect(Boat boat) {
        boat.stamina += Math.min(.5f, 1f - boat.stamina);

        super.applyEffect(boat);
    }
}
