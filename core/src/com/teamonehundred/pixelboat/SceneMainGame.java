package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.*;

/**
 * Represents the Main Game Scene for when the boat race starts.
 *
 * @author William Walton
 * @author Umer Fakher
 * JavaDoc by Umer Fakher
 */
public class SceneMainGame implements Scene {
    protected int scene_id = 1;

    protected int leg_number = 0;

    protected int boats_per_race = 7;
    protected int groups_per_game = 3;

    protected PlayerBoat player;
    protected List<Boat> all_boats;

    protected Texture bg;

    protected BoatRace race;

    protected boolean last_run = false;

    // Added block of code for assessment 2
    protected Texture save_quit_button;
    protected Texture save_quit_hovered;
    protected Sprite save_quit_sprite;

    protected Texture resume_button;
    protected Texture resume_hovered;
    protected Sprite resume_sprite;

    protected boolean isPaused = false;

    public void setPaused(boolean paused) { isPaused = paused; }

    public int getLeg_number() { return leg_number; }

    public void setLeg_number(int leg_number) { this.leg_number = leg_number; }

    public int getBoats_per_race() { return boats_per_race; }

    public PlayerBoat getPlayer() {
        return player;
    }

    public BoatRace getRace() {
        return race;
    }

    public void set_start_time() {
        race.startTime = System.currentTimeMillis();
    }

    public long get_start_time() {
        return race.startTime;
    }
    // End of added block of code for assessment 2

    /**
     * Main constructor for a SceneMainGame.
     * <p>
     * Initialises a BoatRace, player's boat, AI boats and scene textures.
     *
     * @author William Walton
     */
    public SceneMainGame() {
        player = new PlayerBoat(-15, 0);
        player.setName("Player");
        all_boats = new ArrayList<>();

        // Added block of code for assessment 2
        save_quit_button = new Texture("SaveQuitUnselected.png");
        save_quit_hovered = new Texture("SaveQuitSelected.png");
        save_quit_sprite = new Sprite(save_quit_button);
        save_quit_sprite.setSize(512 / 2, 128 / 2);
        save_quit_sprite.setPosition(((float)Gdx.graphics.getWidth()/ 2 - (save_quit_sprite.getWidth() / 2)),
                                     ((float)Gdx.graphics.getHeight()/ 2) + (save_quit_sprite.getHeight() / 2));

        resume_button = new Texture("ResumeUnselected.png");
        resume_hovered = new Texture("ResumeSelected.png");
        resume_sprite = new Sprite(resume_button);
        resume_sprite.setSize(512 / 2, 128 / 2);
        resume_sprite.setPosition(((float)Gdx.graphics.getWidth()/ 2 - (resume_sprite.getWidth() / 2)),
                ((float)Gdx.graphics.getHeight()/ 2) + (resume_sprite.getHeight()) + 35);
        // End of added block of code for assessment 2

        all_boats.add(player);
        for (int i = 0; i < (boats_per_race * groups_per_game) - 1; i++) {
            // Modified block of code for assessment 2
            int spec_id = new Random().nextInt(5);
            all_boats.add(new AIBoat(0, 40, "boats/Boat" + (spec_id+1) + "/boat" + (spec_id + 1) + ".png"));
            all_boats.get(i + 1).setSpec(spec_id);
            all_boats.get(all_boats.size() - 1).setName("AI Boat " + Integer.toString(i));
            // End of modified block of code for assessment 2
        }
        Collections.swap(all_boats, 0, 3); // move player to middle of first group

        bg = new Texture("water_background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        race = new BoatRace(all_boats.subList(0, boats_per_race));
        leg_number++;
    }


    /**
     * Destructor disposes of this texture once it is no longer referenced.
     */
    protected void finalize() {
        bg.dispose();
    }


    /**
     * Draws SpriteBatch on display along with updating player camera and player overlay Using BoatRace.
     *
     * @param batch Spritebatch passed for drawing graphic objects onto screen.
     * @author William Walton
     */
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.getCamera().update();
        batch.setProjectionMatrix(player.getCamera().combined);

        batch.begin();

        batch.draw(bg, -10000, -2000, 0, 0, 1000000, 10000000);
        race.draw(batch);

        // Added block of code for assessment 2
        if (isPaused)
        {
            // Create a local bach and display the main menu button
            SpriteBatch pause_batch =  new SpriteBatch();
            pause_batch.begin();
            save_quit_sprite.draw(pause_batch);
            resume_sprite.draw(pause_batch);
            pause_batch.end();
        }
        // End of added block of code for assessment 2
        batch.end();
    }

    /**
     * Calls main runStep method for BoatRace which is repeatedly called for updating the game state.
     * <p>
     * The BoatRace runStep method checks for started or finished boats in a leg, calls update methods for
     * the movements for player boat and AI boats obstacles as well as checking for collisions.
     *
     * @author William Walton
     */
    public int update() {
        // Modified block of code for assessment 2
        // Stay in results after all legs done
        if (race.isFinished() && leg_number > 3) return 4;

        // Check if the pause button is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
        {
            isPaused = !isPaused;
        }

        if (!isPaused) {
            if (player.hasFinishedLeg()) {
                while (!race.isFinished()) race.runStep();
            }
            if (!race.isFinished()) race.runStep();
                // only run 3 guaranteed legs
            else if (leg_number < 3) {
                race = new BoatRace(all_boats.subList(0, boats_per_race));

                leg_number++;


                // generate some "realistic" times for all boats not shown
                for (int i = boats_per_race; i < all_boats.size(); i++) {
                    all_boats.get(i).setStartTime(0);
                    all_boats.get(i).setEndTime((long) (65000 + 10000 * Math.random()));
                    all_boats.get(i).setLegTime();
                }

                return 4;

            } else if (leg_number == 3) {
                // sort boats based on best time
                Collections.sort(all_boats, new Comparator<Boat>() {
                    @Override
                    public int compare(Boat b1, Boat b2) {
                        return (int) (b1.getBestTime() - b2.getBestTime());
                    }
                });

                race = new BoatRace(all_boats.subList(0, boats_per_race));
                last_run = true;
                leg_number++;

                return 4;
            }
        }
        else
        {
            Vector3 mouse_pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(save_quit_sprite.getBoundingRectangle().contains(mouse_pos.x, Gdx.graphics.getHeight() - mouse_pos.y)){
                save_quit_sprite.setTexture(save_quit_hovered);
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    return 7;
                }
            }
            else
                save_quit_sprite.setTexture(save_quit_button);

            if(resume_sprite.getBoundingRectangle().contains(mouse_pos.x, Gdx.graphics.getHeight() - mouse_pos.y)){
                resume_sprite.setTexture(resume_hovered);
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    isPaused = false;
                }
            }
            else
                resume_sprite.setTexture(resume_button);

        }
        return scene_id;
        // End of modified block of code for assessment 2
    }

    /**
     * Resize method if for camera extension.
     *
     * @param width  Integer width to be resized to
     * @param height Integer height to be resized to
     * @author Umer Fakher
     */
    public void resize(int width, int height) {
        player.getCamera().viewportHeight = height;
        player.getCamera().viewportWidth = width;
    }

    /**
     * Getter method for returning list of boats which contain all boats in scene.
     *
     * @return list of boats
     * @author Umer Fakher
     */
    public List<Boat> getAllBoats() {
        return all_boats;
    }

    public void resetBoats() {
        for (Boat b : all_boats) {
            b.reset();
        }
    }


    /**
     * RaceThread class for Multi-threading.
     *
     * @author William Walton
     * JavaDoc by Umer Fakher
     */
    private class RaceThread extends Thread {
        List<Boat> boats;
        BoatRace race;

        RaceThread(List<Boat> boats) {
            this.boats = new ArrayList<>();
            this.boats.addAll(boats);
            race = new BoatRace(this.boats);
        }

        /**
         * Main run method for RaceThread class.
         * <p>
         * Runs race until it has finished.
         *
         * @author William Walton
         */
        public void run() {
            while (!race.isFinished()) race.runStep();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
