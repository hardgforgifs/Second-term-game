package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

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
    private Music startmusic;
    private Music mainmusic;
    private Music resultmusic;
    private Sound collisionsound;

    // id of current game state
    // 0 = start menu
    // 1 = game
    // 2 = options
    // 3 = tutorial
    // 4 = results
    // 5 =boat selection
    protected int scene_id = 0;
    
    protected boolean init = true;
	private Music[] musics;

    /**
     * Create method runs when the game starts.
     * <p>
     * Runs every scene in Game.
     */
    @Override
    public void create() {
    	//added block of code for assessment 2
    	 startmusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Funky_Full.mp3"));
         mainmusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Beyond The Win.mp3"));
         resultmusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/victory_fireworks.mp3"));
         collisionsound = Gdx.audio.newSound(Gdx.files.internal("sounds/wood_hit.mp3"));
         startmusic.setLooping(true);
         mainmusic.setLooping(true);
         resultmusic.setLooping(true);
       
        musics = new Music[]{startmusic,mainmusic,resultmusic};
      //added block of code for assessment 2
        
        all_scenes = new Scene[6];
        all_scenes[0] = new SceneStartScreen();
        all_scenes[1] = new SceneMainGame(collisionsound);
        all_scenes[2] = new SceneSettings(musics);
        all_scenes[3] = new SceneTutorial();
        all_scenes[4] = new SceneResultsScreen();
        all_scenes[5] = new SceneBoatSelection();

        batch = new SpriteBatch();
        
       
    }
  //added block of code for assessment 2
    private void stopMusic() {
    	for(Music music:musics) {
			music.stop();
		};
    }
  //added block of code for assessment 2
    
    
    
    /**
     * Render function runs every frame.
     * <p>
     * Controls functionality of frame switching.
     */
    @Override
    public void render() {
    	//added block of code for assessment 2
    	if (init) {
    		setMusicVol(startmusic);
        	startmusic.play();
        	init=false;
        }
    	//added block of code for assessment 2
        // run the current scene
        int new_scene_id = all_scenes[scene_id].update();
        all_scenes[scene_id].draw(batch);

        if (scene_id != new_scene_id) {
        	//added block of code for assessment 2
            if (new_scene_id == 4) {
            	stopMusic();
            	setMusicVol(resultmusic);
            	resultmusic.play();
            //added block of code for assessment 2
            	((SceneResultsScreen) all_scenes[4]).setBoats(((SceneMainGame) all_scenes[1]).getAllBoats());
            }
//                ((SceneResultsScreen) all_scenes[4]).setBoats(((SceneMainGame) all_scenes[1]).getAllBoats());
            
          //added block of code for assessment 2
            else if (new_scene_id == 0) {
            	stopMusic();
            	setMusicVol(startmusic);
            	startmusic.play();
            }
            else if (new_scene_id == 1) {
            	stopMusic();
            	setMusicVol(mainmusic);
            	mainmusic.play();
            }
          //added block of code for assessment 2
            else if (new_scene_id == 3 && scene_id == 5)
                ((SceneMainGame) all_scenes[1]).setPlayerSpec(((SceneBoatSelection) all_scenes[5]).getSpecID());


            // check if we need to change scene
            scene_id = new_scene_id;
        }
    }
  //added block of code for assessment 2
    private void setMusicVol(Music music) {
    	final Preferences prefs = Gdx.app.getPreferences("setting\\gamesetting");
    	float mastervolume = prefs.getFloat("MasterVolume", 0.5f);
    	float musicvolume = prefs.getFloat("MusicVolume",0.5f);
    	float r_musicvolume = mastervolume*musicvolume; 
		music.setVolume(r_musicvolume);
	}
  //added block of code for assessment 2
	/**
     * Disposes unneeded SpriteBatch and exits application.
     * <p>
     * Runs when the game needs to close.
     */
    @Override
    public void dispose() {
        batch.dispose();
        if (startmusic != null) {
        	startmusic.dispose();
        }
        if (mainmusic != null) {
        	mainmusic.dispose();
        }
        if (collisionsound != null) {
        	collisionsound.dispose();
        }
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
