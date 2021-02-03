package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

// generic boat class, never instantiated

/**
 * Base class for all boat types. Contains all functionality for moving, taking damage and collision
 *
 * @author William Walton
 * @author Umer Fakher
 */
public abstract class Boat extends MovableObject implements CollisionObject {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected String name = "default boat name";

    protected float durability = 1.f;  // from 0 to 1
    protected float durability_per_hit = .1f;

    protected float stamina = 1.f;  // from 0 to 1, percentage of stamina max
    protected float stamina_usage = 0.005f;  //todo change this after testing
    protected float stamina_regen = .002f;

//    protected float maneuverability = 1f;

    protected List<Long> leg_times = new ArrayList<>();  // times for every previous leg
    protected long start_time = -1;
    protected long end_time = -1;  // ms since epoch when starting and finishing current leg
    protected long frames_raced = 0;  // number of frames taken to do current leg
    protected long time_to_add = 0;  // ms to add to the end time for this leg. Accumulated by crossing the lines

    protected int frames_to_animate = 0;
    protected int current_animation_frame = 0;
    protected int frames_elapsed = 0;

    protected boolean has_finished_leg = false;
    protected boolean has_started_leg = false;

    // Added block of code for assessment 2
    protected int spec_id;

    protected List<Float[]> effects = new ArrayList<>();

    public float getStamina() { return stamina; }

    public void setStamina(float stamina) { this.stamina = stamina; }

    public int getSpec_id() { return spec_id; }
    // End of added block of code for assessment 2


    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    //default specs

    /**
     * Construct a Boat object at point (x,y) with default size, texture and animation.
     *
     * @param x int coordinate for the bottom left point of the boat
     * @param y int coordinate for the bottom left point of the boat
     * @author William Walton
     */
    Boat(int x, int y) {
        super(x, y, 80, 100, "boat.png", 4);
    }

    // Added block of code for assessment 2
    Boat(int x, int y, String texture_path) {
        super(x, y, 80, 100, texture_path, 4);
    }
    // End of added block of code for assessment 2

    /**
     * Construct a Boat object with at point (x,y) with width and height and texture path
     * with default stats (stamina usage, durability, etc).
     *
     * @param x            int coordinate for the bottom left point of the boat
     * @param y            int coordinate for the bottom left point of the boat
     * @param w            int width of the new boat
     * @param h            int height of the new boat
     * @param texture_path String relative path from the core/assets folder of the boats texture image
     * @author William Walton
     */
    Boat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path, 4);
    }

    //specify specs

    /**
     * Construct a Boat object with all parameters specified.
     *
     * @param x                  int coordinate for the bottom left point of the boat
     * @param y                  int coordinate for the bottom left point of the boat
     * @param w                  int width of the new boat
     * @param h                  int height of the new boat
     * @param texture_path       String relative path from the core/assets folder of the boats texture image
     * @param durability_per_hit float percentage (0-1) of the max durability taken each hit
     * @param name               String of the boat seen when the game ends
     * @param stamina_regen      float percentage of stamina regenerated each frame (0-1)
     * @param stamina_usage      float percentage of stamina used each frame when accelerating (0-1)
     * @author William Walton
     */
    Boat(int x, int y, int w, int h, String texture_path, String name,
         float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, 4);

        this.name = name;
        this.durability_per_hit = durability_per_hit;
        this.stamina_usage = stamina_usage;
        this.stamina_regen = stamina_regen;
    }

    /* ################################### //
                    METHODS
    // ################################### */

    // Added block of code for assessment 2
    /**
     * Sets the spec type of boat.
     * <p>
     *
     * @param spec_id int for boat spec
     */
    public void setSpec(int spec_id) {
        this.spec_id = spec_id;
        setStats();
//        setTexture();
    }

//    private void setTexture() {
//        this.texture =
//    }

    public void updateBoostEffect() {
        for (int i = 0; i < effects.size(); i++) {
            Float[] effect = effects.get(i);
            // Apply speed boost effect
            if (effect[0] == 1) {
                speed = Math.max(10, speed);
                acceleration = .4f;
                max_speed = 20;
                effect[1] -= Gdx.graphics.getDeltaTime();
                if (effect[1] <= 0f){
                    setStats();
                    effects.remove(i);
                }

            }

            else if (effect[0] == 2) {
                durability += .2f;
                effects.remove(i);
            }

            else if (effect[0] == 3) {
                maneuverability = 4f;
                effect[1] -= Gdx.graphics.getDeltaTime();
                if (effect[1] <= 0f){
                    setStats();
                    effects.remove(i);
                }
            }

            else if (effect[0] == 4) {
                durability += .5f;
                effects.remove(i);
            }

            else if (effect[0] == 5) {
                durability_per_hit = 0f;
                effect[1] -= Gdx.graphics.getDeltaTime();
                if (effect[1] <= 0f){
                    setStats();
                    effects.remove(i);
                }
            }
        }
//        if (boostType == 1 && boostTimeRemaining <= 0f) {
//            speed = Math.max(10, speed);
//            acceleration += .2f;
//            max_speed += 5;
//        }
//
//        if (boostTimeRemaining < 0f) {
//            boostType = 0;
//            acceleration -= .2f;
//            max_speed -= 5;
//            boostTimeRemaining = 0f;
//        }
//
//        else if (boostTimeRemaining > 0f)
//            boostTimeRemaining -= Gdx.graphics.getDeltaTime();
    }
    // End of added block of code for assessment 2

    /**
     * Function called when this boat collides with another object
     *
     * @author William Walton
     */
    public void hasCollided() {
        // Modified block of code for assessment 2
        durability -= durability - durability_per_hit <= 0 ? 0 : durability_per_hit;
        speed = speed - 5f;
        // End of modified block of code for assessment 2
    }

    /**
     * Function called when the boat accelerates
     *
     * @author William Walton
     */
    @Override
    public void accelerate() {
        stamina = stamina - stamina_usage <= 0 ? 0 : stamina - stamina_usage;
        if (stamina > 0) {
            super.accelerate();
            frames_to_animate += 1;
        }

        if (frames_to_animate > 0) {
            setAnimationFrame(current_animation_frame);
            frames_elapsed++;
            if (frames_elapsed % 15 == 0)
                current_animation_frame++;
            frames_to_animate--;
        } else {
            // reset everything
            setAnimationFrame(0);
            current_animation_frame = 0;
            frames_elapsed = 0;
            frames_to_animate = 0;
        }
    }

    /**
     * Function called every frame when the game updates all objects positions
     *
     * @author William Walton
     */
    @Override
    public void updatePosition() {
        super.updatePosition();
        stamina = stamina + stamina_regen >= 1 ? 1.f : stamina + stamina_regen;
    }

    // Getter and Setter methods for attributes

    public long getFramesRaced() {
        return frames_raced;
    }

    public void setFramesRaced(long frames_raced) {
        this.frames_raced = frames_raced;
    }

    public void addFrameRaced() {
        frames_raced++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the start time of a boat in milliseconds.
     * E.g. Pass use System.currentTimeMillis() to get current system time and pass this long into this method.
     *
     * @param start_time long value which is start time of the boat.
     * @author Umer Fakher
     */
    public void setStartTime(long start_time) {
        this.start_time = start_time;
    }

    /**
     * Returns the long value start time of the boat.
     *
     * @param inSeconds boolean to decide if the time should be returned in seconds or in milliseconds.
     * @return the long value start time
     * @author Umer Fakher
     */
    public long getStartTime(boolean inSeconds) {
        if (inSeconds) {
            return this.start_time / 1000; // Milliseconds to Seconds conversion 1000:1
        }
        return this.start_time;
    }


    /**
     * Sets the end time of a boat in milliseconds.
     * E.g. Pass use System.currentTimeMillis() to get current system time and pass this long into this method.
     *
     * @param end_time long value which is end time of the boat.
     * @author Umer Fakher
     */
    public void setEndTime(long end_time) {
        this.end_time = end_time;
    }

    /**
     * Returns the long value end time of the boat.
     *
     * @param inSeconds boolean to decide if the time should be returned in seconds or in milliseconds.
     * @return the long value end time
     * @author Umer Fakher
     */
    public long getEndTime(boolean inSeconds) {
        if (inSeconds) {
            return this.end_time / 1000; // Milliseconds to Seconds conversion 1000:1
        }
        return this.end_time;
    }

    /**
     * Returns the difference between the end time and start time in milliseconds.
     *
     * @return long value time difference
     * @author Umer Fakher
     */
    public long getCalcTime() {
        return time_to_add + (this.end_time - this.start_time);
    }

    /**
     * Adds the difference between end time and start time into the leg times list as a long value.
     *
     * @author Umer Fakher
     */
    public void setLegTime() {
        this.leg_times.add(this.getCalcTime());
    }

    /**
     * Returns recorded leg times of this boat.
     *
     * @return List of Long Returns a list of long types in milliseconds.
     * @author Umer Fakher
     */
    public List<Long> getLegTimes() {
        return leg_times;
    }

    /**
     * Returns the time penalties to be added this boat accumulated by crossing the lines.
     *
     * @return Returns a long time in milliseconds.
     */
    public long getTimeToAdd() {
        return time_to_add;
    }

    /**
     * Sets the time penalties to be added by this boat accumulated by crossing the lines.
     *
     * @param time_to_add Recorded long time in milliseconds.
     */
    public void setTimeToAdd(long time_to_add) {
        this.time_to_add = time_to_add;
    }

    /**
     * Checks to see if the this boat has collided with the other CollisionObject object passed.
     *
     * @param object The CollisionObject that will be checked to see if it has hit this boat.
     * @author Umer Fakher
     */
    public void checkCollisions(CollisionObject object) {
        if (object instanceof Obstacle && !(
                ((Obstacle) object).getSprite().getY() > sprite.getY() - 200 &&
                        ((Obstacle) object).getSprite().getY() < sprite.getY() + 200 &&
                        ((Obstacle) object).getSprite().getX() > sprite.getX() - 200 &&
                        ((Obstacle) object).getSprite().getX() < sprite.getX() + 200))
            return;
        if (this.getBounds().isColliding(object.getBounds())) {
            // Added block of code for assessment 2
            if (object instanceof PowerUp) {
                // 0 = no boost
                // 1 = speed boost
                // 2 = robustness boost
                // 3 = maneuverability boost
                // 4 = stamina boost
                // 5 = invulnerability boost
                float boostType = ((PowerUp) object).getRandomEffect();
                // Add the effect to the list of ongoing boosts
                effects.add(new Float[]{boostType, 5f});
            }
            // End of added block of code for assessment 2
            else if (!(object instanceof ObstacleLaneWall))
                hasCollided();
            object.hasCollided();
        }
    }

    /**
     * Used to return the CollisionBounds object representing this boat. Used for collision detection
     *
     * @author William Walton
     */
    @Override
    public CollisionBounds getBounds() {
        // create a new collision bounds object representing my current position
        // see the collision bounds visualisation folder in assets for a visual representation
        CollisionBounds my_bounds = new CollisionBounds();
        Rectangle main_rect = new Rectangle(
                sprite.getX() + (0.32f * sprite.getWidth()),
                sprite.getY() + (0.117f * sprite.getHeight()),
                0.32f * sprite.getWidth(),
                0.77f * sprite.getHeight());
        my_bounds.addBound(main_rect);

        my_bounds.setOrigin(new Vector2(
                sprite.getX() + (sprite.getWidth() / 2),
                sprite.getY() + (sprite.getHeight() / 2)));
        my_bounds.setRotation(sprite.getRotation());

        return my_bounds;
    }

    // Getters and Setters for has_started_leg and has_finished_leg

    public boolean hasFinishedLeg() {
        return has_finished_leg;
    }

    public void setHasFinishedLeg(boolean has_finished_leg) {
        this.has_finished_leg = has_finished_leg;
    }

    public boolean hasStartedLeg() {
        return has_started_leg;
    }

    public void setHasStartedLeg(boolean has_started_leg) {
        this.has_started_leg = has_started_leg;
    }

    // Modified block of code for assessment 2
    /**
     * Sets the stats of the boat based on the spec_id that was allocated to it
     */
    public void setStats() {
        switch (spec_id) {
            case 0:
                durability_per_hit = .2f;
                max_speed = 16;
                acceleration = .2f;
                maneuverability = 2f;
                break;
            case 1:
                durability_per_hit = .25f;
                max_speed = 20;
                acceleration = .2f;
                maneuverability = 1.5f;
                break;
            case 2:
                durability_per_hit = .1f;
                max_speed = 18;
                acceleration = .1f;
                maneuverability = 1.5f;
                break;
            case 3:
                durability_per_hit = .15f;
                max_speed = 14;
                acceleration = .2f;
                maneuverability = 3f;
                break;
            case 4:
                durability_per_hit = .25f;
                max_speed = 16;
                acceleration = .3f;
                maneuverability = 1f;
                break;
            default:
                break;
        }
    }
    // End of modified block of code for assessment 2

    /**
     * Gets current best time for boat from its list of leg_times.
     *
     * @return long time in milliseconds.
     */
    public long getBestTime() {
        long current_best = -1;

        for (long time : leg_times) {
            if (time > current_best)
                current_best = time;
        }

        return current_best;
    }
}
