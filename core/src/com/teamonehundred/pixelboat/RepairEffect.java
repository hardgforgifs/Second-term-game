package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RepairEffect extends Effect{

    public RepairEffect() {
        this.isActive = true;
        this.duration = 0f;
        this.texture = new Texture("Buffs/Repair.png");
        this.sprite = new Sprite(texture);
    }

    public RepairEffect(float duration) {
        this.duration = duration;
    }

    @Override
    public void applyEffect(Boat boat) {
        boat.durability += Math.min(.2f, 1f - boat.durability);

        super.applyEffect(boat);
    }
}
