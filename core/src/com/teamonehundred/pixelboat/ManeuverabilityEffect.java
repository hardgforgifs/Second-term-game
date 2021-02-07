package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ManeuverabilityEffect extends Effect{

    public ManeuverabilityEffect() {
        this.texture = new Texture("Buffs/Maneuverability.png");
        this.sprite = new Sprite(texture);
    }

    public ManeuverabilityEffect(float duration) {
        this();
        this.duration = duration;
    }

    @Override
    public void applyEffect(Boat boat) {
        boat.maneuverability = 3.5f;

        super.applyEffect(boat);
    }
}
