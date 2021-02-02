package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public abstract class PowerUp extends GameObject implements CollisionObject{

    PowerUp(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);
    }

    PowerUp(int x, int y, int w, int h, String texture_path, int frame_count) {
        super(x, y, w, h, texture_path, frame_count);
    }

    PowerUp(int x, int y, int w, int h, Texture texture, int frame_count) {
        super(x, y, w, h, texture, frame_count);
    }

    @Override
    public void hasCollided() {
        is_shown = false;
    }

    public float getRandomEffect() {
        Random random = new Random();
        float effect = random.nextFloat();
        // 20% chance to get each boost
//        if (effect <= 0.2)
            return 1;
//        else if (effect <= 0.4)
//            return 2;
//        else if (effect <= 0.6)
//            return 3;
//        else if (effect <= 0.8)
//            return 4;
//        else
//            return 5;
    }
}
