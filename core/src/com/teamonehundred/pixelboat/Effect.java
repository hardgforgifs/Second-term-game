package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Effect {
    protected float duration;
    protected Texture texture;
    protected Sprite sprite;

    protected boolean isActive;

    public boolean isActive() { return isActive; }

    public void applyEffect(Boat boat){
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0f) {
            isActive = false;
        }
    }

    public Sprite getSprite(PlayerBoat boat, int count) {
        sprite.setPosition(boat.sprite.getX() - boat.ui_bar_width / 2  + boat.sprite.getWidth() / 2 + count * 50,
                boat.sprite.getY() - 100);
        sprite.setSize(40, 40);
        return sprite;
    }
}
