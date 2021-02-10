package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BoatRace. Call functionality for sprite objects such as boats and obstacles.
 *
 * @author William Walton
 * @author Umer Fakher
 * JavaDoc by Umer Fakher
 */
public class BoatRace {
    protected List<Boat> boats;

    protected BitmapFont font; //TimingTest
    protected Texture lane_sep;
    protected Texture start_banner;
    protected Texture bleachers_l;
    protected Texture bleachers_r;

    protected List<CollisionObject> obstacles;

    protected int start_y = 200;

    protected int end_y = 40000;

    protected int lane_width = 400;
    protected int penalty_per_frame = 1; // ms to add per frame when over the lane

    protected boolean is_finished = false;

    protected long total_frames = 0;



    // Added block of code for assessment 2
    protected long dnf_time;

    protected Texture waiting;

    protected List<PowerUp> powerups;

    public void setIs_finished(boolean is_finished) { this.is_finished = is_finished; }

    public long getTotal_frames() { return total_frames; }

    public long getDnf_time() { return dnf_time; }

    public void setTotal_frames(long total_frames) { this.total_frames = total_frames; }
    public int getStart_y() { return start_y; }

    public List<Boat> getBoats() { return boats; }

    public List<CollisionObject> getObstacles() { return obstacles; }

    public int getEnd_y() { return end_y; }

    public int getLane_width() {
        return lane_width;
    }
    // End of added block of code for assessment 2

    /**
     * Main constructor for a BoatRace.
     * <p>
     * Initialises lists of boats and obstacles as well as the colour of the Time Elapsed Overlay.
     *
     * @param race_boats List of Boat A list of ai boats and the player boat.
     * @author William Walton
     * @author Umer Fakher
     * @author Dragos Stoican
     * @author Samuel Plane
     */
    public BoatRace(List<Boat> race_boats) {
        lane_sep = new Texture("lane_buoy.png");
        start_banner = new Texture("start_banner.png");
        bleachers_l = new Texture("bleachers_l.png");
        bleachers_r = new Texture("bleachers_r.png");
        // Added new block of code for assessment 2
        waiting = new Texture("WaitingGraphic.png");
        // End of added block of code for assessment 2

        boats = new ArrayList<>();
        boats.addAll(race_boats);

        for (int i = 0; i < boats.size(); i++) {
            boats.get(i).has_started_leg = false;
            boats.get(i).has_finished_leg = false;

            boats.get(i).reset_motion();
            boats.get(i).sprite.setPosition(getLaneCentre(i), 40);  // reset boats y and place in lane
            boats.get(i).setFramesRaced(0);
            boats.get(i).setStats(0);

            if (boats.get(i) instanceof PlayerBoat)
                ((PlayerBoat) boats.get(i)).resetCameraPos();
        }

        obstacles = new ArrayList<>();
        powerups = new ArrayList<>();

        // add some random obstacles
        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleBranch(
                    (int) (-(lane_width * boats.size() / 2) + Math.random() * (lane_width * boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y - start_y - 50))));

        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleFloatingBranch((int) (-(lane_width * boats.size() / 2) + Math.random() * (lane_width * boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y - start_y - 50))));

        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleDuck((int) (-(lane_width * boats.size() / 2) + Math.random() * (lane_width * boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y - start_y - 50))));

        addLaneSeparators();

        // Added block of code for assessment 2
        // Dnf after 2 mins
        dnf_time = 60 * 60 * 2;
        // Add random powerups
        for (int i = 0; i < 100; i++)
            powerups.add(new PowerUp((int) (-(lane_width * boats.size() / 2) + Math.random() * (lane_width * boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y - start_y - 50))));
        // End of added block of code for assessment 2

        // Initialise colour of Time Elapsed Overlay
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    /**
     * Returns the center of te lane at index boat_index
     * @param boat_index index the a lane
     * @return position of the center of the lane
     */
    private int getLaneCentre(int boat_index) {
        int race_width = boats.size() * lane_width;
        return (-race_width / 2) + (lane_width * (boat_index + 1)) - (lane_width / 2);
    }

    /**
     * Main method called for BoatRace.
     * <p>
     * This method is the main game loop that checks if any boats have started or finished a leg and
     * calls the update methods for the movements for player boat and AI boats obstacles.
     * Also this method checks for collisions.
     *
     * @author William Walton
     * @author Umer Fakher
     * @author Dragos Stoican
     * @author Samuel Plane
     */
    public void runStep() {
        // Any boat not finished after 2 minutes is automatically finished for the sake of pacing
        if (total_frames++ > dnf_time) {
            is_finished = true;
            for (Boat b : boats) {
                if (!b.hasFinishedLeg()) {
                    b.setStartTime(0);
                    b.setEndTime((long) (b.getStartTime(false) + ((1000.0 / 60.0) * b.getFramesRaced())));
                    b.setLegTime();
                    b.setHasFinishedLeg(true);
                }
            }
        }

        // Updates the positions of the collidable objects and resets their animation as necessary
        for (CollisionObject c : obstacles) {
            if (c instanceof Obstacle)
                ((Obstacle) c).updatePosition();
            if (c instanceof ObstacleLaneWall) {
                ((ObstacleLaneWall) c).setAnimationFrame(0);
            }
        }

        for (Boat boat : boats) {
            // check if any boats have finished
            if (!boat.hasFinishedLeg() && boat.getSprite().getY() > end_y) {
                // store the leg time in the object
                boat.setStartTime(0);
                boat.setEndTime((long) (boat.getStartTime(false) + ((1000.0 / 60.0) * boat.getFramesRaced())));
                boat.setLegTime();

                boat.setHasFinishedLeg(true);
            }
            // Added block of code for assessment 2
            // Check if any boats have broken, if so they are considered to be done with the leg
            // and given a time penalty of 5 mins
            else if (!boat.hasFinishedLeg() && boat.durability <= 0) {
                boat.setStartTime(0);
                boat.setEndTime(300000);
                boat.setLegTime();
                boat.setHasFinishedLeg(true);
            }
            // End of added block of code for assessment 2
            // check if any boats have started and set the start time accordingly
            else if (!boat.hasStartedLeg() && boat.getSprite().getY() > start_y) {
                boat.setStartTime(System.currentTimeMillis());
                boat.setHasStartedLeg(true);
                boat.setFramesRaced(0);
            } else {
                // if not start or end, must be racing
                boat.addFrameRaced();
            }
        }

        boolean not_finished = false;


        for (int i = 0; i < boats.size(); i++) {
            // all boats
            if (!boats.get(i).hasFinishedLeg()) not_finished = true;

            // update boat (handles inputs if player, etc)
            if (boats.get(i) instanceof AIBoat && !boats.get(i).has_finished_leg) {
                ((AIBoat) boats.get(i)).updatePosition(obstacles);
            } else if (boats.get(i) instanceof PlayerBoat && !boats.get(i).has_finished_leg) {
                boats.get(i).updatePosition();
            }

            // check for collisions
            for (CollisionObject obstacle : obstacles) {
                if (obstacle.isShown())
                    boats.get(i).checkCollisions(obstacle);
            }

            // Added block of code for assessment 2
            // check for boosts pickups
            for (PowerUp powerup : powerups) {
                if (powerup.isShown())
                    boats.get(i).checkCollisions(powerup);
            }

            // Apply the effect of the current boost
            boats.get(i).updateEffects();
            // End of added block of code for assessment 2

            // check if out of lane
            if (boats.get(i).getSprite().getX() > getLaneCentre(i) + lane_width / 2 ||
                    boats.get(i).getSprite().getX() < getLaneCentre(i) - lane_width / 2)
                boats.get(i).setTimeToAdd(boats.get(i).getTimeToAdd() + penalty_per_frame);
        }
        is_finished = !not_finished;
    }

    public boolean isFinished() {
        return is_finished;
    }

    /**
     * Returns a list of all sprites in the PixelBoat game including boats and obstacles.
     *
     * @return List of Sprites A list of all sprites in the PixelBoat game.
     * @author William Walton
     * @author Umer Fakher
     * @author Dragos Stoican
     */
    public List<Sprite> getSprites() {
        List<Sprite> all_sprites = new ArrayList<>();

        for (CollisionObject obs : obstacles) {
            // check if can be cast back up
            if (obs instanceof Obstacle && obs.isShown())
                all_sprites.add(((Obstacle) obs).getSprite());
        }

        // Added block of code for assessment 2
        for (PowerUp powerup : powerups) {
            if (powerup.is_shown)
                all_sprites.add(powerup.getSprite());
        }
        // End of added block of code for assessment 2

        for (Boat b : boats) {
            all_sprites.add(b.getSprite());
            if (b instanceof PlayerBoat)
                all_sprites.addAll(((PlayerBoat) b).getUISprites());
        }

        return all_sprites;
    }


    /**
     * Calculates and displays the Time Elapsed Overlay for player boat from the start of a leg.
     * <p>
     * The displayed time is updated in real-time and the position is consistent with the player hud (i.e. stamina
     * and durability bar positions).
     *
     * @param batch
     * @author Umer Fakher
     */
    public void draw(SpriteBatch batch) {

        // Modified block of code for assessment 2
        // Retrieves sprites and calls function recursively.
        for (Sprite sp : getSprites())
            sp.draw(batch);
        int race_width = boats.size() * lane_width;
        Texture temp = new Texture("object_placeholder.png");

        // Displays the bleachers at each side of the map from the start to the end of the race
        for (int i = -1000; i < end_y + 1000; i += 800)
            batch.draw(bleachers_r, race_width / 2 + 400, i, 400, 800);
        for (int i = -1000; i < end_y + 1000; i += 800)
            batch.draw(bleachers_l, -race_width / 2 - 800, i, 400, 800);

        //Draws the start line across the width of the race area
        for (int i = 0; i < boats.size(); i++)
            batch.draw(start_banner, (getLaneCentre(i)) - (lane_width / 2), start_y, lane_width, lane_width / 2);
        //Draws the finish line at the end of the race
        batch.draw(temp, -race_width / 2, end_y, race_width, 5);

        temp.dispose();
        // End of modified block of code for assessment 2
    }

    // Added block of code for assessment 2
    /**
     * Add extra obstacles based on the leg number, and increase their movement speed
     * @author Dragos Stoican
     */
    public void setLegDifficulty(int leg_number) {

        // Adds additional obstacles of each kind multiple times based on the leg number
        for (int i = 0; i < 20 * leg_number; i++) {
            obstacles.add(new ObstacleBranch(
                    (int) (-(lane_width * boats.size() / 2) + Math.random() * (lane_width * boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y - start_y - 50))));

            obstacles.add(new ObstacleFloatingBranch((int) (-(lane_width * boats.size() / 2) + Math.random() * (lane_width * boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y - start_y - 50))));

            obstacles.add(new ObstacleDuck((int) (-(lane_width * boats.size() / 2) + Math.random() * (lane_width * boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y - start_y - 50))));
        }

        // Increases the speed at which collidable objects move, with the exception of the lane walls
        for (CollisionObject object: obstacles) {
            Obstacle obstacle = (Obstacle) object;
            if (!obstacle.getClass().getName().equals("com.teamonehundred.pixelboat.ObstacleLaneWall")) {
                obstacle.speed += .02f * leg_number;
            }
        }
    }

    /**
     * Adds the lane separators objects on the map
     * @author Dragos Stoican
     */
    public void addLaneSeparators() {
        // add the lane separators
        for (int lane = 0; lane <= boats.size(); lane++) {
            for (int height = 0; height <= end_y; height += ObstacleLaneWall.texture_height) {
                obstacles.add(new ObstacleLaneWall(getLaneCentre(lane) - lane_width / 2, height, lane_sep));
            }
        }
    }

    /**
     * Draws UI elements on the static batch that is not projected on the camera
     * @param batch batch to draw on
     * @param player_boat the player's boat
     * @author Dragos Stoican
     */
    public void drawStatic(SpriteBatch batch, PlayerBoat player_boat) {
        //Calculate time elapsed from the start in milliseconds
        long curTime = (long) ((1000.0 / 60.0) * player_boat.getFramesRaced());
        //Displays and updates the time elapsed overlay
        if (player_boat.has_started_leg) {
            // Added block of text for assessment 2
            if (player_boat.getSprite().getX() > getLaneCentre(boats.indexOf(player_boat)) + lane_width / 2 ||
                    player_boat.getSprite().getX() < getLaneCentre(boats.indexOf(player_boat)) - lane_width / 2)
                drawTimeDisplay(batch, "     Penalty added!!!", curTime, (float) Gdx.graphics.getWidth() / 2 - 50,
                        0.9f * (float) Gdx.graphics.getHeight());
            else
                drawTimeDisplay(batch, "", curTime, (float) Gdx.graphics.getWidth() / 2 - 50,
                        0.9f * (float) Gdx.graphics.getHeight());
            // End of added block fo text for assessment 2
        }

        //Displays the graphic telling the player other boats are finishing once they have finished
        if (player_boat.hasFinishedLeg())
            batch.draw(waiting, (float) Gdx.graphics.getWidth() / 3, 0.5f * (float) Gdx.graphics.getHeight(), 500, 100);

        //Draws a leg time display on the screen when the given boat has completed a leg of the race.
        drawLegTimeDisplay(batch, player_boat);
    }
    // End of added block of code for assessment 2

    /**
     * Draws the a time display on the screen.
     *
     * @param batch      SpriteBatch instance
     * @param label_text label for text. If "" empty string passed in then default time display shown.
     * @param time       time to be shown in milliseconds
     * @param x          horizontal position of display
     * @param y          vertical position of display
     * @author Umer Fakher, Dragos Stoinca
     */
    public void drawTimeDisplay(SpriteBatch batch, String label_text, long time, float x, float y) {
        // Modified block of code for assessment 2
        // Ensures the penalty warning is not lost
        if (label_text.equals("     Penalty added!!!") || label_text.equals("")) {
            label_text = "Time (min:sec) = %02d:%02d" + label_text;
        }
        // Displays the current time the player has taken on the leg
        font.draw(batch, String.format(label_text, time / 60000, time / 1000 % 60), x, y);
        // End of modified block of code for assessment 2
    }

    /**
     * Draws a leg time display on the screen when the given boat has completed a leg of the race.
     * <p>
     * This function gets the leg times list for the given boat instance, gets the last updated leg time
     * and formats a leg time display string which shows which leg was completed and in what time.
     * The function then passes on the drawing of this formatted leg time display to drawTimeDisplay.
     *
     * @param batch SpriteBatch instance
     * @param b     Boat instance
     * @author Umer Fakher
     */
    public void drawLegTimeDisplay(SpriteBatch batch, Boat b) {
        if (b.getEndTime(false) != -1) {
            for (long l : b.getLegTimes()) {
                String label = String.format("Leg Time %d (min:sec) = ", b.getLegTimes().indexOf(l) + 1) + "%02d:%02d";
                drawTimeDisplay(batch, label, l, (float)Gdx.graphics.getWidth()/2 - 50,
                        0.9f * (float)Gdx.graphics.getHeight() - ((b.getLegTimes().indexOf(l) + 1) * 20));
            }

        }
    }

    // Added block of code for assessment 2
    public void dispose() {
        lane_sep.dispose();
        start_banner.dispose();
        bleachers_l.dispose();
        bleachers_r.dispose();
        waiting.dispose();
    }
    // End of added block of code for assessment 2
}
