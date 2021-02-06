package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Main class for the PixelBoat game.
 * <p>
 * Extends Libgdx ApplicationAdapter.
 *
 * @author William Walton
 * @author James Frost
 * @author Umer Fakher
 * JavaDoc by Umer Fakher
 */
public class PixelBoat extends ApplicationAdapter {
    protected Scene[] all_scenes;  // stores all game scenes and their data
    protected SpriteBatch batch;  // thing that draws the sprites

    // id of current game state
    // 0 = start menu
    // 1 = game
    // 2 = options
    // 3 = tutorial
    // 4 = results
    // 5 = boat selection
    protected int scene_id = 0;

    // Added block of code for assessment 2
    protected Preferences pref;

    public Scene[] getAll_scenes() { return all_scenes; }

    public void setAll_scenes(Scene[] all_scenes) { this.all_scenes = all_scenes; }

    public void setPref(Preferences pref) { this.pref = pref; }

    public void saveGame(SceneMainGame game_state) {
        pref.clear();
        // Mark that a save exists in preferences
        pref.putString("save", "save exists");
        pref.putInteger("leg_number", game_state.leg_number);
//        pref.putInteger("player_spec_id", ((SceneMainGame)all_scenes[1]).player.getSpec_id());
        pref.putFloat("camera_x", game_state.player.getCamera().position.x);
        pref.putFloat("camera_y", game_state.player.getCamera().position.y);
        pref.putLong("race_start_time", game_state.race.startTime);
        pref.putLong("race_duration", game_state.race.time);
        for (int k = 0; k < game_state.race.obstacles.size(); k++) {
            // Don't save the lane walls
            if (!((Obstacle) game_state.race.obstacles.get(k)).getClass().getName().equals("com.teamonehundred.pixelboat.ObstacleLaneWall")) {
                pref.putFloat("obstacle" + k + " x", ((Obstacle)game_state.race.obstacles.get(k)).getSprite().getX());
                pref.putFloat("obstacle" + k + " y", ((Obstacle)game_state.race.obstacles.get(k)).getSprite().getY());
                pref.putString("obstacle" + k + " class", ((Obstacle)game_state.race.obstacles.get(k)).getClass().getName());
            }

        }

        for (int k = 0; k < game_state.race.powerups.size(); k++) {
            pref.putFloat("powerup" + k + " x", game_state.race.powerups.get(k).getSprite().getX());
            pref.putFloat("powerup" + k + " y", game_state.race.powerups.get(k).getSprite().getY());
        }


        for (int i = 0; i < game_state.boats_per_race; i++) {
            pref.putFloat("boat" + i + " x", game_state.all_boats.get(i).sprite.getX());
            pref.putFloat("boat" + i + " y", game_state.all_boats.get(i).sprite.getY());
            pref.putFloat("boat" + i + " rotation", game_state.all_boats.get(i).sprite.getRotation());
            pref.putInteger("boat" + i + " spec_id", game_state.all_boats.get(i).getSpec_id());
            pref.putFloat("boat" + i + " speed", game_state.all_boats.get(i).speed);
            pref.putFloat("boat" + i + " stamina", game_state.all_boats.get(i).stamina);
            pref.putLong("boat" + i + " start_time", game_state.all_boats.get(i).start_time);
            pref.putLong("boat" + i + " end_time", game_state.all_boats.get(i).end_time);
            pref.putLong("boat" + i + " time_to_add", game_state.all_boats.get(i).time_to_add);
            pref.putLong("boat" + i + " frames_raced", game_state.all_boats.get(i).frames_raced);
            pref.putBoolean("boat" + i + " has_started_leg", game_state.all_boats.get(i).has_started_leg);
            pref.putBoolean("boat" + i + " has_finished_leg", game_state.all_boats.get(i).has_finished_leg);

            // Save the list of effects
            for (int k = 0; k < game_state.getAllBoats().get(i).effects.size(); k++) {
                Float[] effect = game_state.getAllBoats().get(i).effects.get(k);
                pref.putFloat("effect" + i + k + " type", effect[0]);
                pref.putFloat("effect" + i + k + " time", effect[1]);
            }
            for (int j = 0; j < game_state.all_boats.get(i).leg_times.size(); j++) {
                pref.putLong("leg_time" + i + j, game_state.all_boats.get(i).leg_times.get(j));
            }
        }
    }

    public SceneMainGame loadGame() {
        SceneMainGame game_state = new SceneMainGame();
        game_state.isPaused = true;
        game_state.leg_number = pref.getInteger("leg_number");
//        game_state.getPlayer().setSpec(pref.getInteger("player_spec_id"));
        game_state.race.startTime = pref.getLong("race_start_time");
        game_state.race.time = pref.getLong("race_duration");

        int k = 0;
        float x_obstacle = pref.getFloat("obstacle" + k + " x", -1);
        while (x_obstacle != -1) {
            float y_obstacle = pref.getFloat("obstacle" + k + " y");
            String className = pref.getString("obstacle" + k + " class");
            if (className.equals("com.teamonehundred.pixelboat.ObstacleBranch"))
                game_state.race.obstacles.set(k, new ObstacleBranch((int) x_obstacle, (int) y_obstacle));
            else if (className.equals("com.teamonehundred.pixelboat.ObstacleDuck"))
                game_state.race.obstacles.set(k, new ObstacleDuck((int) x_obstacle, (int) y_obstacle));
            else if (className.equals("com.teamonehundred.pixelboat.ObstacleFloatingBranch"))
                game_state.race.obstacles.set(k, new ObstacleFloatingBranch((int) x_obstacle, (int) y_obstacle));
            k++;
            x_obstacle = pref.getFloat("obstacle" + k + " x", -1);
        }

        k = 0;
        float x_powerup = pref.getFloat("powerup" + k + " x", -1);
        while (x_powerup != -1) {
            float y_powerup = pref.getFloat("powerup" + k + " y");
            game_state.race.powerups.set(k++, new PowerUp((int) x_powerup, (int) y_powerup));
            x_powerup = pref.getFloat("powerup" + k + " x", -1);
        }

        float camera_x = pref.getFloat("camera_x");
        float camera_y = pref.getFloat("camera_y");
        game_state.player.camera.position.set(camera_x, camera_y, 0);
        for (int i = 0; i < game_state.boats_per_race; i++) {
            float x = pref.getFloat("boat" + i + " x");
            float y = pref.getFloat("boat" + i + " y");
            float rotation = pref.getFloat("boat" + i + " rotation");
            int spec_id = pref.getInteger("boat" + i + " spec_id");
            game_state.all_boats.get(i).setSpec(spec_id);
            game_state.all_boats.get(i).sprite.setPosition(x, y);
            game_state.all_boats.get(i).sprite.setRotation(rotation);
            game_state.all_boats.get(i).speed = pref.getFloat("boat" + i + " speed");
            game_state.all_boats.get(i).stamina = pref.getFloat("boat" + i + " stamina");
            game_state.all_boats.get(i).start_time = pref.getLong("boat" + i + " start_time");
            game_state.all_boats.get(i).end_time = pref.getLong("boat" + i + " end_time");
            game_state.all_boats.get(i).time_to_add = pref.getLong("boat" + i + " time_to_add");
            game_state.all_boats.get(i).frames_raced = pref.getLong("boat" + i + " frames_raced");
            game_state.all_boats.get(i).has_started_leg = pref.getBoolean("boat" + i + " has_started_leg");
            game_state.all_boats.get(i).has_finished_leg = pref.getBoolean("boat" + i + " has_finished_leg");
            int j = 0;
            while (pref.getLong("leg_time" + i + j, -1) != -1) {
                game_state.all_boats.get(i).leg_times.add(pref.getLong("leg_time" + i + j));
                j++;
            }

            // Load the current effects of powerups
            j = 0;
            float type = pref.getFloat("effect" + i + j + " type", -1);
            while(type != -1) {
                float time  = pref.getFloat("effect" + i + j + " time");
                game_state.getAllBoats().get(i).effects.add(new Float[]{type, time});
                j++;
                type = pref.getFloat("effect" + i + j + " type", -1);
            }
        }
        return game_state;
    }
    // End of added block of code for assessment 2

    /**
     * Create method runs when the game starts.
     * <p>
     * Runs every scene in Game.
     */
    @Override
    public void create() {
        // Added block of code for assessment 2
        pref = Gdx.app.getPreferences("save");
        // End of added block of code for assessment 2

        all_scenes = new Scene[6];
        all_scenes[0] = new SceneStartScreen();
        all_scenes[1] = new SceneMainGame();
        all_scenes[2] = new SceneOptionsMenu();
        all_scenes[3] = new SceneTutorial();
        all_scenes[4] = new SceneResultsScreen();
        all_scenes[5] = new SceneBoatSelection();

        // Added block of code for assessment 2
        // Mark if a save already exists from a previous session
        if (!pref.getString("save", "no save exists").equals("no save exists"))
            ((SceneStartScreen)all_scenes[0]).is_saved_game = true;
        batch = new SpriteBatch();
        // End of added block of code for assessment 2
    }

    /**
     * Render function runs every frame.
     * <p>
     * Controls functionality of frame switching.
     */
    @Override
    public void render() {
        // run the current scene
        int new_scene_id = all_scenes[scene_id].update();
        all_scenes[scene_id].draw(batch);


        if (scene_id != new_scene_id) {
            // special case updates
            if (new_scene_id == 4)
                ((SceneResultsScreen) all_scenes[4]).setBoats(((SceneMainGame) all_scenes[1]).getAllBoats());
            else if (new_scene_id == 3 && scene_id == 5){
                // Added block of code for assessment 2
                all_scenes[1] = new SceneMainGame();
                // End of added block of code for assessment 2
                ((SceneMainGame) all_scenes[1]).getPlayer().setSpec(((SceneBoatSelection) all_scenes[5]).getSpecID());
            }

            // Added block of code for assessment 2
            else if (new_scene_id == 1){
                ((SceneMainGame) all_scenes[1]).set_start_time();
                ((SceneMainGame) all_scenes[1]).resetBoats();
            }

            else if (new_scene_id == 6){
                all_scenes[1] = loadGame();
                new_scene_id = 1;
            }
            else if (new_scene_id == 7) {
                saveGame((SceneMainGame) all_scenes[1]);
                // Mark that a save file now exists so we can load it from the main menu
                ((SceneStartScreen)all_scenes[0]).is_saved_game = true;
                new_scene_id = 0;
            }
            // End of added block of code for assessment 2

            // check if we need to change scene
            scene_id = new_scene_id;
        }

    }

    /**
     * Disposes unneeded SpriteBatch and exits application.
     * <p>
     * Runs when the game needs to close.
     */
    @Override
    public void dispose() {
        batch.dispose();
        // Added block of code for assessment 2
        // Using the flush method assures the persistence of the save file
        pref.flush();
        // End of added block of code for assessment 2
        Gdx.app.exit();
        System.exit(0);
    }

    /**
     * Resize used and passed to resize method of each scene based on width and height attributes.
     *
     * @param width  int for scene
     * @param height int for scene
     */
    @Override
    public void resize(int width, int height) {
        all_scenes[scene_id].resize(width, height);
    }
}
