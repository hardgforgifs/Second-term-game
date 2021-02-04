package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PowerUp extends GameObject implements CollisionObject{

    PowerUp(int x, int y){ super(x, y, 60, 60, "PowerUp/PowerUp.png");}
    PowerUp(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);
    }

    @Override
    public void hasCollided() {
        is_shown = false;
    }

    public float getRandomEffect() {
        Random random = new Random();
        float effect = random.nextFloat();
        // 20% chance to get each boost
        if (effect <= 0.2)
            return 1;
        else if (effect <= 0.4)
            return 2;
        else if (effect <= 0.6)
            return 3;
        else if (effect <= 0.8)
            return 4;
        else
            return 5;
    }

    @Override
    public CollisionBounds getBounds() {
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle main_rect = new Rectangle(
                sprite.getX() + (0.3f * sprite.getWidth()),
                sprite.getY() + (0.06f * sprite.getHeight()),
                0.4f * sprite.getWidth(), sprite.getHeight());
        my_bounds.addBound(main_rect);

        my_bounds.setOrigin(new Vector2(
                sprite.getX() + (sprite.getWidth() / 2),
                sprite.getY() + (sprite.getHeight() / 2)));
        my_bounds.setRotation(sprite.getRotation());

        return my_bounds;
    }
}
