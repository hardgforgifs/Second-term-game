package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PowerUp extends GameObject implements CollisionObject{

    public PowerUp(int x, int y){ super(x, y, 60, 60, "PowerUp/PowerUp.png");}

    @Override
    public void hasCollided() {
        is_shown = false;
    }

    /**
     * Returns a random effect, with each type of effect having 20% chance to be returned
     * @return Effect object
     * @author Dragos Stoican
     */
    public Effect getRandomEffect() {
        Random random = new Random();
        float effect = random.nextFloat();
        // 20% chance to get each effect
        if (effect <= 0.2)
            return new SpeedEffect();
        else if (effect <= 0.4)
            return new RepairEffect();
        else if (effect <= 0.6)
            return new ManeuverabilityEffect();
        else if (effect <= 0.8)
            return new StaminaEffect();
        else
           return new InvulnerabilityEffect();
    }

    @Override
    public CollisionBounds getBounds() {
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle main_rect = new Rectangle(
                sprite.getX() + (0.2f * sprite.getWidth()),
                sprite.getY() + (0.06f * sprite.getHeight()),
                0.6f * sprite.getWidth(), sprite.getHeight());
        my_bounds.addBound(main_rect);

        my_bounds.setOrigin(new Vector2(
                sprite.getX() + (sprite.getWidth() / 2),
                sprite.getY() + (sprite.getHeight() / 2)));
        my_bounds.setRotation(sprite.getRotation());

        return my_bounds;
    }
}
