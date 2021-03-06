package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the user player's boat.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
public class PlayerBoat extends Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected OrthographicCamera camera;

    protected Texture stamina_texture;
    protected Texture durability_texture;

    protected Sprite stamina_bar;
    protected Sprite durability_bar;

    protected int ui_bar_width = 500;

    // Added block of code for assessment 2
    protected Texture recovering_texture;
    protected Texture[] effect_textures;
    public Sound collisionsound;
    // End of added block of code for assessment 2



    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    /**
     * Construct a PlayerBoat object at point (x,y) with default size, texture and animation.
     *
     * @param x int coordinate for the bottom left point of the boat
     * @param y int coordinate for the bottom left point of the boat
     * @author William Walton
     */
    public PlayerBoat(int x, int y) {
        super(x, y);
        // Added block of code for assessment 2
        this.collisionsound = Gdx.audio.newSound(Gdx.files.internal("sounds/wood_hit.mp3"));
        // End of added block of code for assessment 2
        initialise();
    }

    /**
     * Destructor disposes of this texture once it is no longer referenced.
     */
    public void finalize() {
        stamina_texture.dispose();
        durability_texture.dispose();
        recovering_texture.dispose();
    }

    /* ################################### //
                    METHODS
    // ################################### */

    /**
     * Shared initialisation functionality among all constructors.
     * <p>
     * Sets stamina bar and durability bar textures and sprites.
     * Initialises the bars' size and position.
     * Initialises camera position.
     */
    public void initialise() {
        stamina_texture = new Texture("stamina_texture.png");
        durability_texture = new Texture("durability_texture.png");
        recovering_texture = new Texture("stamina_recovering.png");

        // Added block of code for assessment 2
        effect_textures = new Texture[5];
        effect_textures[0] = new Texture("Buffs/Speed.png");
        effect_textures[1] = new Texture("Buffs/Repair.png");
        effect_textures[2] = new Texture("Buffs/Maneuverability.png");
        effect_textures[3] = new Texture("Buffs/Stamina.png");
        effect_textures[4] = new Texture("Buffs/Invulnerable.png");

        // End of added block of code for assessment 2


        stamina_bar = new Sprite(stamina_texture);
        durability_bar = new Sprite(durability_texture);

        stamina_bar.setSize(ui_bar_width, 10);
        durability_bar.setSize(ui_bar_width, 10);

        stamina_bar.setPosition(-ui_bar_width / 2, 5);
        durability_bar.setPosition(-ui_bar_width / 2, 20);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, Gdx.graphics.getHeight() / 3, 0);
        camera.update();
    }

    /**
     * Updates the position based on the user's input.
     * <p>
     * 'W' key accelerates the boat.
     * 'A' Turns the boat to the left
     * 'D' Turns the boat to the right
     * <p>
     * Updates the x and y position of the sprite with new x and y according to which input has been requested.
     * The camera will follow the player's boat
     *
     * @author William Walton
     */
    @Override
    public void updatePosition() {
        // Accelerates boat if 'w' key is pressed
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.accelerate();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            // Nothing implemented here
        }

        // Turns boat based on input 'a' & 'd' keys
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.turn(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.turn(-1);
        }

        // Used to determine how the boat has moved, allowing camera to accurately follow
        float old_x = sprite.getX();
        float old_y = sprite.getY();

        super.updatePosition();

        // only follow player in x axis if they go off screen
        float dx = Math.abs(sprite.getX()) > Gdx.graphics.getWidth() / 3 ? sprite.getX() - old_x : 0;
        float dy = sprite.getY() - old_y;

        // move camera to follow player
        camera.translate(dx, dy, 0);
    }

    /**
     * Returns the all sprites for PlayerBoat UI.
     * <p>
     * This includes the stamina bar and durability bar.
     *
     * @return List of Sprites
     */
    public List<Sprite> getUISprites() {
        updateUISprites();

        List<Sprite> ret = new ArrayList<Sprite>();
        ret.add(stamina_bar);
        ret.add(durability_bar);
        // Added block of code for assessment 2
        for (int i = 0; i < effects.size(); i++) {
            ret.add(effects.get(i).getSprite(this, i));
        }
        // End of added block of code for assessment 2
        return ret;
    }

    /**
     * Getter for PlayerBoat Camera.
     *
     * @return OrthographicCamera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Resets PlayerBoat Camera position
     */
    public void resetCameraPos() {
        camera.position.set(sprite.getX(), Gdx.graphics.getHeight() / 3, 0);
        camera.update();
    }

    /**
     * Update the position and size of the UI elements (e.g. stamina bar and durability bar) according to their values.
     * <p>
     * The stamina decreases as player requests the boat to row and move. It increases when this is not the case.
     * Durability decreases according to the collisions with other obstacles.
     * Dynamically updates the size of the stamina bar and durability bar
     * based on the PlayerBoat attributes as they change.
     */
    private void updateUISprites() {

        //Moves UI bars along with the boat
        stamina_bar.setPosition(-ui_bar_width / 2 + sprite.getX() + sprite.getWidth() / 2, -50 + sprite.getY());
        durability_bar.setPosition(-ui_bar_width / 2 + sprite.getX() + sprite.getWidth() / 2, -35 + sprite.getY());

        // If the player is recovering, then use an orange texture to convey this to the user, otherwise
        // use the default yellow
        if (recovering == true) {
            stamina_bar.setTexture(recovering_texture);
        } else {
            stamina_bar.setTexture(stamina_texture);
        }

        // Changes the size of the bars to reflect the remaining durability and stamina
        stamina_bar.setSize((int) (ui_bar_width * stamina), 10);
        durability_bar.setSize((int) (ui_bar_width * durability), 10);
    }

}
