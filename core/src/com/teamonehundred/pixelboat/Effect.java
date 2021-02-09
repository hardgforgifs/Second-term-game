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

    /**
     * Creates a new default effect with duration 5
     * @author Dragos Stoican
     */
    public Effect() {
        this.isActive = true;
        this.duration = 5f;
    }

    /**
     * Applies the effect to a boat and reduces the duration of the effect
     * @param boat The boat to apply the effect to
     * @author Dragos Stoican
     */
    public void applyEffect(Boat boat){
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0f) {
            boat.setStats(boat.difficulty);
            isActive = false;
        }
    }

    /**
     * Sets the position and size of the sprite to match the location of the boat, then returns the sprite
     * @param boat Boat to adjust position to
     * @param count Number of effects that affect the same boat
     * @return Adjusted sprite
     * @author Dragos Stoican
     */
    public Sprite getSprite(PlayerBoat boat, int count) {
        sprite.setPosition(boat.sprite.getX() - boat.ui_bar_width / 2  + boat.sprite.getWidth() / 2 + count * 50,
                boat.sprite.getY() - 100);
        sprite.setSize(40, 40);
        return sprite;
    }

    /**
     * Checks if two effects are equal based on their duration active status and class
     * @param o another effect
     * @return true if the effects are equal, false otherwise
     * @author Dragos Stoican
     */
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

    public void dispose() {
        texture.dispose();
    }
}
