package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Effect {

    protected float duration;
    protected Texture texture;
    protected Sprite sprite;

    protected boolean isActive;

    public void setActive(boolean active) { isActive = active; }

    public void setDuration(float duration) { this.duration = duration; }

    public float getDuration() { return duration; }

    public boolean isActive() { return isActive; }

    public Effect() {
        this.isActive = true;
        this.duration = 5f;
    }

    // TODO consider difficulty
    public void applyEffect(Boat boat){
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0f) {
            boat.setStats(0);
            isActive = false;
        }
    }

    public Sprite getSprite(PlayerBoat boat, int count) {
        sprite.setPosition(boat.sprite.getX() - boat.ui_bar_width / 2  + boat.sprite.getWidth() / 2 + count * 50,
                boat.sprite.getY() - 100);
        sprite.setSize(40, 40);
        return sprite;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Effect))
            return false;
        else {
            Effect e = (Effect) o;
            if (this.isActive == e.isActive &&
                this.duration == e.duration &&
                this.getClass() == e.getClass())
                return true;
            return false;
        }
    }
}
