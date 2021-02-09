package com.teamonehundred.pixelboat;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Represents the AI's boat.
 *
 * @author James Frost
 * JavaDoc by Umer Fakher
 */
public class AIBoat extends Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected float number_of_rays;
    protected float ray_angle_range;
    protected float ray_range;
    protected float ray_step_size;

    // Added block of code for assessment 2
    protected boolean regen;

    public boolean isRegen() {return regen; }

    public void setRegen(boolean regen) { this.regen = regen; }
    // End of added block of code for assessment 2

    /* ################################### //
              CONSTRUCTORS
    // ################################### */

    // Modified block of code for assessment 2
    /**
     * Construct a AIBoat object at point (x,y) with default size and a custom texture.
     *
     * @param x int coordinate for the bottom left point of the boat
     * @param y int coordinate for the bottom left point of the boat
     * @param texture_path internal path to the texture of the boat
     * @author Dragos Stoican
     */
    public AIBoat(int x, int y, String texture_path) {
        super(x, y, texture_path);

        initialise();
    }

    /**
     * Construct a AIBoat object at point (x,y) with default size and default texture.
     *
     * @param x int coordinate for the bottom left point of the boat
     * @param y int coordinate for the bottom left point of the boat
     * @author Dragos Stoican
     */
    public AIBoat(int x, int y) {
        super(x, y);

        initialise();
    }
    // End of modified block of code for assessment 2

    /**
     * Shared initialisation functionality among all constructors.
     * <p>
     * Initialises the ray properties. Rays are used to help the AI control the boat based on visual feedback
     * of its environment i.e. obstacles such as movable obstacles and static lane wall obstacles.
     *
     * @author James Frost
     */
    public void initialise() {
        number_of_rays = 4; // how many rays are fired from the boat
        ray_angle_range = 145; // the range of the angles that the boat will fire rays out at
        ray_range = 30; // the range of each ray
        ray_step_size = (float) 10;
        regen = false;
    }

    // Modified block of code for assessment 2
    /**
     * Updates position of objects AIBoat based on acceleration and stamina.
     * <p>
     * Checks if AIBoat can turn and updates position accordingly based on any collision objects that may overlap.
     *
     * @param collidables List of Collision Objects
     * @author James Frost
     * @author Samuel Plane
     */
    public void updatePosition(List<CollisionObject> collidables) {
        double start = 0;
        if (!regen) {
            this.accelerate();
            if (stamina <= 0.2) {
                regen = true;
            }
        } else {
            //We calculate a random number here so that we can add some variety
            //to the AI boat's behaviour
            while (start <= 0.3 ) {
                start = Math.random();
            }
            if (stamina >= start ) {
                regen = false;
            }
        }
        // todo fix this, it takes too long
        this.check_turn(collidables);
        super.updatePosition();

    }
    // End of modified block of code for assessment 2

    /**
     * Returns true if AIBoat should exist on the screen.
     *
     * @return boolean parent isShown
     * @author James Frost
     */
    @Override
    public boolean isShown() {
        return super.isShown();
    }

    /**
     * Return centre coordinates of point where ray is fired.
     *
     * @return Vector2 of coordinates
     * @author James Frost
     */
    protected Vector2 get_ray_fire_point() {
        Vector2 p = new Vector2(
                sprite.getX() + (sprite.getWidth() / 2),
                sprite.getY() + (sprite.getHeight()));

        Vector2 p1 = p.rotateAround(new Vector2(
                        sprite.getX() + (sprite.getWidth() / 2),
                        sprite.getY() + (sprite.getHeight() / 2)),
                sprite.getRotation());

        return p1;
    }

    /**
     * Fire a number of rays with limited distance out the front of the boat, select a ray that
     * isn't obstructed by an object, preference the middle (maybe put a preference to side as well)
     * if every ray is obstructed either (keep turning [left or right] on the spot until one is,
     * or choose the one that is obstructed furthest away the second option
     * (choose the one that is obstructed furthest away) is better
     *
     * @param collidables List of Collision Objects
     * @author James Frost
     */
    protected void check_turn(List<CollisionObject> collidables) {
        //Firing rays

        //select an area of 180 degrees (pi radians)
        boolean cheeky_bit_of_coding = true; // this is a very cheeky way of solving the problem, but has a few benefits
        Vector2 start_point = get_ray_fire_point();
        for (int ray = 0; ray <= number_of_rays; ray++) {
            if (cheeky_bit_of_coding) {
                ray--;
                float ray_angle = sprite.getRotation() + ((ray_angle_range / (number_of_rays / 2)) * ray);
                cheeky_bit_of_coding = false;
            } else {
                float ray_angle = sprite.getRotation() - ((ray_angle_range / (number_of_rays / 2)) * ray);
                cheeky_bit_of_coding = true;
            }

            float ray_angle = ((ray_angle_range / number_of_rays) * ray) + sprite.getRotation();

            for (float dist = 0; dist <= ray_range; dist += ray_step_size) {

                double tempx = (Math.cos(Math.toRadians(ray_angle)) * dist) + (start_point.x);
                double tempy = (Math.sin(Math.toRadians(ray_angle)) * dist) + (start_point.y);
                //check if there is a collision hull (other than self) at (tempx, tempy)
                for (CollisionObject collideable : collidables) {
                    // very lazy way of optimising this code. will break if the collidable isn't an obstacle
                    if (collideable.isShown() &&
                            ((Obstacle) collideable).getSprite().getY() > sprite.getY() - 200 &&
                            ((Obstacle) collideable).getSprite().getY() < sprite.getY() + 200 &&
                            ((Obstacle) collideable).getSprite().getX() > sprite.getX() - 200 &&
                            ((Obstacle) collideable).getSprite().getX() < sprite.getX() + 200)
                        for (Shape2D bound : collideable.getBounds().getShapes()) {
                            if (bound.contains((float) tempx, (float) tempy)) {
                                if (cheeky_bit_of_coding) {
                                    turn(-1);
                                    return;
                                } else {
                                    turn(1);
                                    return;
                                }

                            }
                        }

                }
            }
        }
    }
}
