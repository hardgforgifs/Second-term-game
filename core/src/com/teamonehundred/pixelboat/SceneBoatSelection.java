package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Represents the Boat Selection Scene for when the player wants to select which boat to play with before the race
 * starts.
 *
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
public class SceneBoatSelection implements Scene {
    protected int scene_id = 5;

    protected boolean is_new_click = false;

    protected int spec_id = 0;

    protected Texture bg;
    protected Sprite bg_sprite;

    protected Texture[] boat_options;
    protected Sprite[] boat_option_sprites;
    // Added block of code for assessment 2
    protected int num_specs = 5;
    protected int difficulty_level = 1;
    protected Texture[] boat_options_hovered;

    protected Texture stats_bg;
    protected Texture[] stats_boats;
    protected Sprite[] stats_bg_sprite;

    protected Texture back;
    protected Texture back_hovered;
    protected Texture easy;
    protected Texture easy_hovered;
    protected Texture normal;
    protected Texture normal_hovered;
    protected Texture hard;
    protected Texture hard_hovered;
    protected Texture cont;
    protected Texture cont_hovered;

    protected Sprite easy_sprite;
    protected Sprite normal_sprite;
    protected Sprite hard_sprite;
    protected Sprite back_sprite;
    protected Sprite cont_sprite;
    // End of added block of code for assessment 2

    protected Viewport fill_viewport;
    protected OrthographicCamera fill_camera;

    /**
     * Main constructor for a SceneBoatSelection.
     * <p>
     * Initialises a Scene textures for Boat Selection and camera.
     *
     * @author William Walton
     * @author Quenthin Rothman
     * @author Samuel Plane
     * @author Dragos Stoican
     */
    public SceneBoatSelection() {
        fill_camera = new OrthographicCamera();
        fill_viewport = new FillViewport(1280, 720, fill_camera);
        fill_viewport.apply();
        fill_camera.position.set(fill_camera.viewportWidth / 2, fill_camera.viewportHeight / 2, 0);
        fill_viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bg = new Texture("TitleScreen/background_screen.png");
        bg_sprite = new Sprite(bg);
        bg_sprite.setPosition(0, 0);
        bg_sprite.setSize(1280, 720);

        // Added block of code for assessment 2
        stats_bg = new Texture("boats/stats_background.png");
        
        
        stats_boats = new Texture[num_specs];
        stats_bg_sprite = new Sprite[num_specs];
        
        stats_boats[0] = new Texture("boats/boat1_stats.png");
        stats_boats[1] = new Texture("boats/boat2_stats.png");
        stats_boats[2] = new Texture("boats/boat3_stats.png");
        stats_boats[3] = new Texture("boats/boat4_stats.png");
        stats_boats[4] = new Texture("boats/boat5_stats.png");
        
        for(int i = 0; i < num_specs; i++) {
        	stats_bg_sprite[i] = new Sprite(stats_bg);
        	stats_bg_sprite[i].setPosition((fill_camera.viewportWidth / 2) - (stats_bg_sprite[i].getWidth() / 2), (float) ((fill_camera.viewportHeight / 1.35) - (stats_bg_sprite[i].getHeight() / 2)));
        	stats_bg_sprite[i].setSize(stats_bg_sprite[i].getWidth(), stats_bg_sprite[i].getHeight());
        }
        boat_options = new Texture[num_specs];
        boat_option_sprites = new Sprite[num_specs];

        boat_options_hovered = new Texture[num_specs];

        boat_options[0] = new Texture("boats/boat1.png");
        boat_options[1] = new Texture("boats/boat2.png");
        boat_options[2] = new Texture("boats/boat3.png");
        boat_options[3] = new Texture("boats/boat4.png");
        boat_options[4] = new Texture("boats/boat5.png");
        
        boat_options_hovered[0] = new Texture("boats/boat1_hovered.png");
        boat_options_hovered[1] = new Texture("boats/boat2_hovered.png");
        boat_options_hovered[2] = new Texture("boats/boat3_hovered.png");
        boat_options_hovered[3] = new Texture("boats/boat4_hovered.png");
        boat_options_hovered[4] = new Texture("boats/boat5_hovered.png");
        
        back = new Texture("TitleScreen/back.png");
        back_hovered = new Texture("TitleScreen/back_hovered.png");
        easy = new Texture("Easy.png");
        easy_hovered = new Texture("EasySelected.png");
        normal = new Texture("Medium.png");
        normal_hovered = new Texture("MediumSelected.png");
        hard = new Texture("Hard.png");
        hard_hovered = new Texture("HardSelected.png");
        cont = new Texture("ContinueUnselected.png");
        cont_hovered = new Texture("ContinueSelected.png");

        back_sprite = new Sprite(back);
        back_sprite.setSize(512 / 2, 128 / 2);
        back_sprite.setPosition((fill_camera.viewportWidth / 8) - (back_sprite.getWidth() / 2), (fill_camera.viewportHeight / 8) - (back_sprite.getHeight() / 2));

        easy_sprite = new Sprite(easy);
        easy_sprite.setSize(512 / 4, 128 / 4);
        easy_sprite.setPosition((13 * (fill_camera.viewportWidth / 16)) - (easy_sprite.getWidth() / 2), (fill_camera.viewportHeight / 8) - (easy_sprite.getHeight() / 2));

        normal_sprite = new Sprite(normal_hovered);
        normal_sprite.setSize(512 / 4, 128 / 4);
        normal_sprite.setPosition((13 * (fill_camera.viewportWidth / 16)) - (normal_sprite.getWidth() / 2), (2 * (fill_camera.viewportHeight / 8)) - (normal_sprite.getHeight() / 2));

        hard_sprite = new Sprite(hard);
        hard_sprite.setSize(512 / 4, 128 / 4);
        hard_sprite.setPosition((15 * (fill_camera.viewportWidth / 16)) - (hard_sprite.getWidth() / 2), (2 * (fill_camera.viewportHeight / 8)) - (hard_sprite.getHeight() / 2));

        cont_sprite = new Sprite(back);
        cont_sprite.setSize(512 / 4, 128 / 4);
        cont_sprite.setPosition((15 * (fill_camera.viewportWidth / 16)) - (cont_sprite.getWidth() / 2), (fill_camera.viewportHeight / 8) - (hard_sprite.getHeight() / 2));

        // End of added block of code for assessment 2

        for (int i = 0; i < num_specs; i++) {
            boat_option_sprites[i] = new Sprite(boat_options[i]);
            // Added block of code for assessment 2
            boat_option_sprites[i].setSize((boat_option_sprites[i].getHeight() / 3), (boat_option_sprites[i].getWidth() / 3));
            
            if(i == 0) {
		        boat_option_sprites[i].setPosition(
		                ((fill_camera.viewportWidth / 20) - (boat_option_sprites[i].getWidth() / 2)),
		                (float) ((fill_camera.viewportHeight / 2) - (boat_option_sprites[i].getHeight() / 2)));
            }else if(i == 1) {
                boat_option_sprites[i].setPosition(
                        (float) ((fill_camera.viewportWidth / 3.75) - (boat_option_sprites[i].getWidth() / 2)),
                        ((fill_camera.viewportHeight / 3) - (boat_option_sprites[i].getHeight() / 2)));
            }else if(i == 2) {
                boat_option_sprites[i].setPosition(
                        ((fill_camera.viewportWidth / 2) - (boat_option_sprites[i].getWidth() / 2)),
                        ((fill_camera.viewportHeight / 5) - (boat_option_sprites[i].getHeight() / 2)));
            }else if(i == 3) {
                boat_option_sprites[i].setPosition(
                        (float) ((fill_camera.viewportWidth / 1.375) - (boat_option_sprites[i].getWidth() / 2)),
                        ((fill_camera.viewportHeight / 3) - (boat_option_sprites[i].getHeight() / 2)));
            }else if(i == 4) {
                boat_option_sprites[i].setPosition(
                		(float) ((fill_camera.viewportWidth / 1.05) - (boat_option_sprites[i].getWidth() / 2)),
                        (float) ((fill_camera.viewportHeight / 2) - (boat_option_sprites[i].getHeight() / 2)));
            }
            // End of added block of code for assessment 2
        }
    }


    // Modified block of code for assessment 2
    /**
     * Update function for SceneBoatSelection. Ends SceneBoatSelection based on user input otherwise stays in scene.
     * <p>
     * Returns an specified integer when you want to exit the screen else return scene_id if you want to stay in scene.
     *
     * @return returns an integer which is the scene_id of which screen is next (either this screen still or another)
     * @author William Walton
     * @author Quenthin Rothman
     * @author Samuel Plane
     * @author Dragos Stoican
     */
    public int update() {
        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            is_new_click = true;

        Vector3 mouse_pos = fill_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        for (int i = 0; i < num_specs; i++) {

            if (boat_option_sprites[i].getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && is_new_click) {
                    stats_bg_sprite[i].setTexture(stats_boats[i]);
                    boat_option_sprites[i].setTexture(boat_options_hovered[i]);
                    spec_id = i;
                    return scene_id;
                }
            } else if (spec_id != i){
                boat_option_sprites[i].setTexture(boat_options[i]);
                stats_bg_sprite[i].setTexture(stats_bg);
            }

        }

        if (back_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            back_sprite.setTexture(back_hovered);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                is_new_click = false;
                return 0;
            }
        } else
            back_sprite.setTexture(back);

        if (easy_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                easy_sprite.setTexture(easy_hovered);
                is_new_click = false;
                difficulty_level = 0;
            }
        } else if (difficulty_level != 0)
            easy_sprite.setTexture(easy);

        if (normal_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                normal_sprite.setTexture(normal_hovered);
                is_new_click = false;
                difficulty_level = 1;
            }
        } else if (difficulty_level != 1)
            normal_sprite.setTexture(normal);

        if (hard_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                hard_sprite.setTexture(hard_hovered);
                is_new_click = false;
                difficulty_level = 2;

            }
        } else if (difficulty_level != 2)
            hard_sprite.setTexture(hard);

        if (cont_sprite.getBoundingRectangle().contains(mouse_pos.x, mouse_pos.y)) {
            cont_sprite.setTexture(cont_hovered);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                is_new_click = false;
                return 3;
            }
        } else
            cont_sprite.setTexture(cont);


        return scene_id;
    }

    // End of modified block of code for assessment 2


    /**
     * Draw function for SceneBoatSelection.
     * <p>
     * Draws BoatSelection for the PixelBoat game.
     *
     * @param batch SpriteBatch used for drawing to screen.
     * @author William Walton
     * @author Samuel Plane
     */
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(fill_camera.combined);
        batch.begin();
        bg_sprite.draw(batch);

        // Modified block of code for assessment 2
        for (int i = 0; i < num_specs; i++) {
            boat_option_sprites[i].draw(batch);
            stats_bg_sprite[i].draw(batch);
        }
        back_sprite.draw(batch);
        easy_sprite.draw(batch);
        normal_sprite.draw(batch);
        hard_sprite.draw(batch);
        cont_sprite.draw(batch);
        // End of modified block of code for assessment 2

        batch.end();
    }

    /**
     * Temp resize method if needed for camera extension.
     *
     * @param width  Integer width to be resized to
     * @param height Integer height to be resized to
     * @author Umer Fakher
     */
    public void resize(int width, int height) {
    }

    /**
     * Getter method for the specified boat's spec_id.
     *
     * @return boat's spec id
     * @author William Walton
     */
    public int getSpecID() {
        return spec_id;
    }


    public int getDifficulty_level() {
        return difficulty_level;
    }

    // Added block of code for assessment 2
    /**
     * Destructor disposes of textures once it is no longer referenced.
     */
    public void dispose() {
        bg.dispose();
        stats_bg.dispose();
        back.dispose();
        back_hovered.dispose();
        easy.dispose();
        easy_hovered.dispose();
        normal.dispose();
        normal_hovered.dispose();
        hard.dispose();
        hard_hovered.dispose();
        cont.dispose();
        cont_hovered.dispose();
    }
    // End of added block of code for assessment 2
}
