package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    protected float stamina_usage = .002f;
    protected float stamina_regen = .003f;

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
    protected int difficulty;
    protected int spec_id;

    protected boolean recovering = false;
    protected int stamina_delay = 0;
    protected int time_to_recover= 100;

    public int getDifficulty() { return difficulty; }

    public boolean isRecovering() { return recovering; }

    public void setRecovering(boolean recovering) { this.recovering = recovering; }

    public void setTime_to_recover(int time_to_recover) { this.time_to_recover = time_to_recover; }

    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public int getStamina_delay() { return stamina_delay; }

    public void setStamina_delay(int stamina_delay) { this.stamina_delay = stamina_delay; }

    public int getTime_to_recover() { return time_to_recover; }

    protected List<Effect> effects = new ArrayList<>();

    public List<Effect> getEffects() { return effects; }

    public float getStamina() { return stamina; }

    public void setStamina(float stamina) { this.stamina = stamina; }

    public int getSpec_id() { return spec_id; }

    public float getDurability() { return durability; }

    public void setDurability(float durability) { this.durability = durability; }

    public float getDurability_per_hit() { return durability_per_hit; }
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
    /**
     * Construct a Boat object at point (x,y) with default size, custom texture
     *
     * @param x int coordinate for the bottom left point of the boat
     * @param y int coordinate for the bottom left point of the boat
     * @param texture_path Internal path to the texture of the boat
     * @author Dragos Stoican
     */
    Boat(int x, int y, String texture_path) {
        super(x, y, 80, 100, texture_path, 4);
    }
    // End of added block of code for assessment 2

    /* ################################### //
                    METHODS
    // ################################### */

    // Added block of code for assessment 2

    /**
     * Sets the texture of an existing boat, updating animations as necessary
     *
     * @param texture_path
     * @param w
     * @param h
     * @author Dragos Stoican
     */
    public void setTexture(String texture_path, float w, float h) {
        this.texture = new Texture(texture_path);
        animation_regions = new TextureRegion[4];
        float texture_width = 1f / (4);
        for (int i = 0; i < 4; i++) {
            animation_regions[i] = new TextureRegion(texture, i * texture_width, 0f, (i + 1) * texture_width, 1f);
        }

        sprite = new Sprite(animation_regions[0]);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }
    /**
     * Sets the spec id of boat.
     * <p>
     *
     * @param spec_id int for boat spec
     * @author Dragos Stoican
     */
    public void setSpec(int spec_id) {
        this.spec_id = spec_id;
        setTexture("boats/Boat" + (spec_id + 1) + "/boat" + (spec_id + 1) + ".png",
                80, 100);
        setStats(difficulty);
    }

    /**
     * Applies the effects from the list of effects to the boat
     */
    public void updateEffects() {
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect.isIs_active())
                effect.applyEffect(this);
            else
                effects.remove(effect);
        }
    }
    // End of added block of code for assessment 2

    // Modified block of code for assessment 2
    /**
     * Function called when this boat collides with another object
     *
     * @author William Walton, Dragos Stoican
     */
    public void hasCollided() {
        durability -= durability - durability_per_hit <= 0 ? durability : durability_per_hit;
        speed -= speed - 5f <= 0f ? speed : 5f;
    }
    // End of modified block of code for assessment 2

    /**
     * Function called when the boat accelerates
     *
     * @author William Walton, Samuel Plane
     */
    @Override
    public void accelerate() {
        // Modified block of code for assessment 2
        if (stamina > stamina_usage & recovering == false) {
            stamina = stamina - stamina_usage <= 0 ? 0 : stamina - stamina_usage;
            super.accelerate();
            frames_to_animate += 1;
            //Stamina being regained is delayedto stop players spamming w key to exploit stamina
            stamina_delay = 0;
        } else {
            //Sets recovering variable to true if the boat has run out of stamina,
            //temporarily stopping the boat from recovering
            recovering = true;
        }
        // End of modified block of code for assessment 2

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
     * @author William Walton, Samuel Plane
     */
    @Override
    public void updatePosition() {
        //Modified block of code for assessment 2
        super.updatePosition();

        if (stamina_delay >= time_to_recover) {
            //Boat recovers stamina with each frame if enough time has passed since it has used stamina
            stamina = stamina + stamina_regen >= 1 ? 1.f : stamina + stamina_regen;
            //Boat leaves the recovering phase once they have enough stamina, allowing them to row again
            if (stamina > 0.4) {
                recovering = false;
            }
        } else {
            //Decreases the time left until a boat can regain stamina
            stamina_delay += 1;
        }
        //End of modified block of code for assessment 2
    }

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
     * @author Umer Fakher, Dragos Stoican, Bowen Lyu
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
            // Play sounds if colliding with an obstacle that is not a lane seprator
            if (this instanceof PlayerBoat && object instanceof Obstacle && !(object instanceof ObstacleLaneWall)) {
                final Preferences prefs = Gdx.app.getPreferences("setting/gamesetting");
                float soundvolume = prefs.getFloat("SoundVolume",0.5f);
                float mastervolume = prefs.getFloat("MasterVolume",0.5f);
                ((PlayerBoat)this).collisionsound.play(soundvolume*mastervolume);
            }
            // Add a new random effect to the list of effects when colliding with a powerup
            if (object instanceof PowerUp) {
                effects.add(((PowerUp) object).effect);
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

    // Added block of code for assessment 2
    /**
     * Sets the stats of the boat based on the spec_id that was allocated to it
     * @author Dragos Stoican
     */
    public void setStats(int difficulty) {
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
                acceleration = .15f;
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
        switch (difficulty) {
            case 0:
                time_to_recover = 30;
                break;
            case 1:
                time_to_recover = 60;
                break;
            case 2:
                time_to_recover = 90;
                break;
        }
    }

    /**
     * This method deals with resetting the boats after each leg
     * @author Dragos Stoican
     */
    public void reset() {
        speed = 0f;
        effects.clear();
        durability = 1f;
        stamina = 1f;
        time_to_add = 0;
    }
    // End of added block of code for assessment 2


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
