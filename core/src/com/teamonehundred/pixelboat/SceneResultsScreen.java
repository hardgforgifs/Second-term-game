package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

/**
 * Scene Class for Results Screen. Contains all functionality for Displaying results screen after each leg.
 *
 * @author Umer Fakher
 * JavaDoc by Umer Fakher
 */
class SceneResultsScreen implements Scene {
    protected int scene_id = 4;
    protected int leg_no;

    protected List<Boat> boats;
    protected BitmapFont font; // For Text Display

    protected Viewport fill_viewport;
    protected OrthographicCamera fill_camera;

    // Added block of code for assessment 2
    protected Texture bg;
    protected Texture resultsTable;
    // End of added block of code for assessment 2

    SceneResultsScreen() {
        fill_camera = new OrthographicCamera();
        fill_viewport = new FillViewport(1280, 720, fill_camera);
        fill_viewport.apply();
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
        fill_viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        boats = null;

        // Initialise colour of Text Display Overlay
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Added block of code for assessment 2
        bg = new Texture("water_background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        resultsTable = new Texture("ResultScreen.png");
        // End of added block of code for assessment 2
    }

    /**
     * Update function for SceneResultsScreen. Ends SceneResultsScreen based on user input otherwise stays in scene.
     * <p>
     * Returns 1 when you want to exit the results screen else return scene_id if you want to stay in scene.
     *
     * @return returns an integer which is either the scene_id or 1
     * @author Umer Fakher
     */
    public int update() {
        //Testing code for outputting results after a leg to terminal

        //If left mouse button is pressed end current scene (a SceneResultsScreen)
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            //Added block of code for assessment 2
            if (leg_no >= 4) {
                // don't leave if this is the final results screen
                return 0;
            } else {
                //otherwise we return to SceneMainGame for another leg
                return 1;
            }
            //End of added block of code for assessment 2
        }
        // otherwise remain in current scene (a SceneResultsScreen)
        return scene_id;

    }


    /**
     * Draw function for SceneResultsScreen.
     * <p>
     * Draws ResultsScreen which includes the leg time for all boats (AI and player boats) that have just completed a
     * leg. This table will wrap according to how many boat times need to be displayed. Using label template format it
     * draws the name of boat, time of just completed leg, race penalty added for each boat that finished the leg.
     *
     * @param batch SpriteBatch used for drawing to screen.
     * @author Umer Fakher
     */
    public void draw(SpriteBatch batch) {
        //Initialise colouring
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // todo draw using this camera
        //batch.setProjectionMatrix(fill_camera.combined);

        // Find player's boat in list of boats in order to use x and y axis
        PlayerBoat thePlayerBoat = null;
        for (Boat b : boats) {
            if (b instanceof PlayerBoat) {
                thePlayerBoat = (PlayerBoat) b;
            }
        }

        // Begin a sprite batch drawing
        batch.begin();

        //Added block of code for assessment 2
        batch.draw(bg, -10000, -2000, 0, 0, 1000000, 10000000);
        //End of added block of code for assessment 2



        // End a sprite batch drawing
        batch.end();

    }

    //Added block of code for assessment 2
    public void drawStatic(SpriteBatch batch) {
        batch.begin();

        // Draw text instructions at the top of the screen
        font.setColor(Color.ORANGE);
        font.draw(batch, "Results Screen! Click on the screen to skip and start the next leg!",
                fill_camera.viewportWidth / 3, 15 * (fill_camera.viewportHeight / 16));

        // Draw text instructions for the timing format that will be displayed
        font.setColor(Color.YELLOW);
        font.draw(batch, "BoatName | Race Time in ms | Race penalty in ms",
                fill_camera.viewportWidth / 3, 14 * (fill_camera.viewportHeight / 16));

        batch.draw(resultsTable, fill_camera.viewportWidth / 3,   fill_camera.viewportHeight / 8, 500, 450);

        String label_template = "%s | %d ms | %d ms";//"A boat (%s) ended race with time (ms) %d (%d ms was penalty)";

        // Initialise values for drawing times as table in order to allow dynamic wrapping
        int column_num = -1;
        int column_idx = -1;
        for (int top_boats = 0; top_boats <= 13; top_boats++) {
            if (boats.get(top_boats) instanceof PlayerBoat) {
                font.setColor(Color.RED); // Colour Player's time in red
            } else {
                font.setColor(Color.WHITE); // All AI-boat times in white
            }

            // Writes out the details of all the boats into the table
            font.draw(batch, boats.get(top_boats).getName(), 5 * (fill_camera.viewportWidth / 12), (470 * (fill_camera.viewportHeight / 720)) - (27 * top_boats));
            font.draw(batch, Long.toString(boats.get(top_boats).getLegTimes().get(boats.get(top_boats).getLegTimes().size() - 1)), 6 * (fill_camera.viewportWidth / 12), (470 * (fill_camera.viewportHeight / 720)) - (27 * top_boats));
            font.draw(batch, Long.toString(boats.get(top_boats).getTimeToAdd()) , 7 * (fill_camera.viewportWidth / 12), (470 * (fill_camera.viewportHeight / 720)) - (27 * top_boats));
            font.draw(batch, Long.toString(boats.get(top_boats).getCalcTime()) , 8 * (fill_camera.viewportWidth / 12), (470 * (fill_camera.viewportHeight / 720)) - (27 * top_boats));
            /*

            // Shift to next column to allowing wrapping of times as table
            if (boats.indexOf(boats.get(top_boats)) % 21 == 0) {
                column_num++;
                column_idx = 0;
            }


            // Using label template format draw the name of boat, time of just completed leg, race penalty added
            String label_text = String.format(label_template, boats.get(top_boats).getName(),
                    boats.get(top_boats).getLegTimes().get(boats.get(top_boats).getLegTimes().size() - 1), boats.get(top_boats).getTimeToAdd());

            // Draw to results display to screen using position of player's UI and draw for all boats this down the
            // and wraps across screen if needed into the next column
            font.draw(batch, label_text, 5 * (fill_camera.viewportWidth / 12) + column_num * 210,
                    11 * (fill_camera.viewportHeight / 16)  - (column_idx * 20));

            column_idx++;
            */

        }

        // End a sprite batch drawing
        batch.end();
    }
    //End of added block of code for assessment 2

    /**
     * Temp resize method if needed for camera extension.
     *
     * @param width  Integer width to be resized to
     * @param height Integer height to be resized to
     * @author Umer Fakher
     */
    public void resize(int width, int height) {
        fill_viewport.update(width, height);
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
    }

    /**
     * Setter method for list of boats for all boats in scene.
     *
     * @param boats List of boats to be set to current instance.
     * @author Umer Fakher
     */
    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }

    // Added block of code for assessment 2
    public void dispose() {

    }
    // End of added block of code for assessment 2
}
