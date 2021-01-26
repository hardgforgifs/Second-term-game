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
    // 6 = loaded game
    protected int scene_id = 0;

    Preferences pref;

    public void saveGame(SceneMainGame game_state) {
        pref.clear();
        pref.putInteger("leg_number", game_state.leg_number);
        pref.putInteger("spec_id", ((SceneBoatSelection)all_scenes[5]).getSpecID());
        pref.putFloat("camera_x", game_state.player.getCamera().position.x);
        pref.putFloat("camera_y", game_state.player.getCamera().position.y);
        pref.putLong("race_start_time", game_state.race.startTime);
        pref.putLong("race_duration", game_state.race.time);

        for (int i = 0; i < game_state.boats_per_race; i++) {
            pref.putFloat("boat" + i + " x", game_state.all_boats.get(i).sprite.getX());
            pref.putFloat("boat" + i + " y", game_state.all_boats.get(i).sprite.getY());
            pref.putFloat("boat" + i + " rotation", game_state.all_boats.get(i).sprite.getRotation());
            pref.putFloat("boat" + i + " speed", game_state.all_boats.get(i).speed);
            pref.putFloat("boat" + i + " stamina", game_state.all_boats.get(i).stamina);
            pref.putLong("boat" + i + " start_time", game_state.all_boats.get(i).start_time);
            pref.putLong("boat" + i + " end_time", game_state.all_boats.get(i).end_time);
            pref.putLong("boat" + i + " time_to_add", game_state.all_boats.get(i).time_to_add);
            pref.putLong("boat" + i + " frames_raced", game_state.all_boats.get(i).frames_raced);
            pref.putBoolean("boat" + i + " has_started_leg", game_state.all_boats.get(i).has_started_leg);
            pref.putBoolean("boat" + i + " has_finished_leg", game_state.all_boats.get(i).has_finished_leg);
            for (int j = 0; j < game_state.all_boats.get(i).leg_times.size(); j++) {
                pref.putLong("leg_time" + i + j, game_state.all_boats.get(i).leg_times.get(j));
            }
        }
//        System.out.println(game_state.all_boats.get(0).leg_times.get(0));
    }

    public SceneMainGame loadGame() {
        SceneMainGame game_state = new SceneMainGame();
        game_state.isPaused = true;
        game_state.leg_number = pref.getInteger("leg_number");
        game_state.setPlayerSpec(pref.getInteger("spec_id"));
        game_state.race.startTime = pref.getLong("race_start_time");
        game_state.race.time = pref.getLong("race_duration");

        float camera_x = pref.getFloat("camera_x");
        float camera_y = pref.getFloat("camera_y");
        game_state.player.camera.position.set(camera_x, camera_y, 0);
        for (int i = 0; i < game_state.boats_per_race; i++) {
            float x = pref.getFloat("boat" + i + " x");
            float y = pref.getFloat("boat" + i + " y");
            float rotation = pref.getFloat("boat" + i + " rotation");
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


        }
//        System.out.println(pref.getLong("leg_time" + 0 + 0));
//        System.out.println(game_state.all_boats.get(0).leg_times.get(0));
        return game_state;
    }

    /**
     * Create method runs when the game starts.
     * <p>
     * Runs every scene in Game.
     */
    @Override
    public void create() {
         pref = Gdx.app.getPreferences("save");
//        kryo.register(SceneMainGame.class, new MainGameSerializer());

        all_scenes = new Scene[7];
        all_scenes[0] = new SceneStartScreen();
        all_scenes[1] = new SceneMainGame();
        all_scenes[2] = new SceneOptionsMenu();
        all_scenes[3] = new SceneTutorial();
        all_scenes[4] = new SceneResultsScreen();
        all_scenes[5] = new SceneBoatSelection();
        all_scenes[6] = null;

        batch = new SpriteBatch();
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
                all_scenes[1] = new SceneMainGame();
                ((SceneMainGame) all_scenes[1]).setPlayerSpec(((SceneBoatSelection) all_scenes[5]).getSpecID());
            }

            if (new_scene_id == 1){
                ((SceneMainGame) all_scenes[1]).set_start_time();
            }

            else if (new_scene_id == 6){
                all_scenes[1] = loadGame();
                new_scene_id = 1;
            }
            else if (new_scene_id == 7) {
                saveGame((SceneMainGame) all_scenes[1]);
                new_scene_id = 0;
            }
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
        pref.flush();
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
