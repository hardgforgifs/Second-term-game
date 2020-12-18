package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a lane wall obstacle.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
class ObstacleLaneWall extends Obstacle {
    // Class attributes shared by all instances
    public static int texture_height = 64;

    /**
     * A constructor for an lane wall obstacle taking its position (x and y).
     * <p>
     * <p>
     * Image is taken by default from C:\...\ENG1-Team-12\Implementation\core\assets.
     *
     * @author James Frost
     * @author William Walton
     */
    ObstacleLaneWall(int x, int y, Texture t) {
        super(x, y, 32, texture_height, t, 2);
        setAnimationFrame(0);
    }

    public void setAnimationFrame(int i) {
        super.setAnimationFrame(i);
    }

    @Override
    public void hasCollided() {
        setAnimationFrame(1);
    }
}
