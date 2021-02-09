package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SceneSettings implements Scene{
	
	
	private Stage stage;
    private Texture background_img;
    private Skin skin;
    private TextureAtlas atlas;
    private Slider master_slider;
    private Slider music_slider;
    private Slider sound_slider;
    private Image bg_img;
    private Label label_master_vol;
    private Label label_music_vol;
    private Label label_sound_vol;
    private BitmapFont bitmapfont;
    private Button back_setting;
    private Texture back;
    private Texture back_clicked;
    private int scene_id = 2;
	private final Music[] musics;
    
	

    
	@Override
	public void draw(SpriteBatch batch) {
    	if(scene_id == 0) {
    		scene_id = 2;
    	}
    }
	@Override
	public int update() {
//		Gdx.app.debug("TAG", scene_id+"");
		stage.act();
		stage.draw();
		return scene_id;
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {

	}


	/**
	 * Creates the scene with the music objects
	 * @param music list of music objects
	 */
	public SceneSettings( Music[] music) {
		this.musics = music;
    	
    	final Preferences prefs = Gdx.app.getPreferences("setting/gamesetting");
		float mastervolume = prefs.getFloat("MasterVolume", 0.5f);
    	float musicvolume = prefs.getFloat("MusicVolume",0.5f);
    	float soundvolume = prefs.getFloat("SoundVolume",0.5f);
    	
    	stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Label.LabelStyle style = new Label.LabelStyle();
		bitmapfont = new BitmapFont(Gdx.files.internal("setting/setting.fnt"));
		
		style.font = bitmapfont;
		style.fontColor = new Color(0,0,0,1);
		label_master_vol = new Label("MasterVolume",style);
		label_master_vol.setPosition(100,480);
		label_master_vol.setFontScale(0.6f);
		
		label_music_vol = new Label("MusicVolume",style);
		label_music_vol.setPosition(100,280);
		label_music_vol.setFontScale(0.6f);
		
		label_sound_vol = new Label("Sound effect",style);
		label_sound_vol.setPosition(100,80);
		label_sound_vol.setFontScale(0.6f);
		
		background_img = new Texture(Gdx.files.internal("setting/bg_settings.png"));
		bg_img = new Image(new TextureRegion(background_img));
		bg_img.setSize(1280, 720);
		bg_img.setZIndex(0);
		
		back = new Texture(Gdx.files.internal("setting/back_clicked.png"));
		back_clicked = new Texture(Gdx.files.internal("setting/back.png"));
        Button.ButtonStyle style_1 = new Button.ButtonStyle();
		style_1.up = new TextureRegionDrawable(new TextureRegion(back));
		style_1.down = new TextureRegionDrawable(new TextureRegion(back_clicked));
		back_setting = new Button(style_1);
		back_setting.setPosition(950, 100);
		back_setting.addListener(new ClickListener(){
            @Override
			public void clicked(InputEvent event, float x, float y) {
            	scene_id = 0;
			}
		});
		
		
		skin = new Skin(Gdx.files.internal("setting/setting_slider/slider.json"));
		atlas = new TextureAtlas("setting/setting_slider/slider.atlas");
		master_slider = new Slider(0 ,100 ,1 ,false ,skin);
		master_slider.setSize(554,19);
		master_slider.setPosition(350, 500);
		master_slider.setZIndex(1);
		
		master_slider.setValue(mastervolume*100);
		master_slider.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
				float mastervol = master_slider.getValue()/100;
				for(Music music:musics) {
					music.setVolume(mastervol);
				};
				prefs.putFloat("MasterVolume", mastervol);
				prefs.flush();
			}
		});
		
		music_slider = new Slider(0 ,100 ,1 ,false ,skin);
		music_slider.setSize(554,19);
		music_slider.setPosition(350, 300);
		music_slider.setZIndex(1);
		
		music_slider.setValue(musicvolume*100);
		music_slider.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
				float mastervol = master_slider.getValue()/100;
				float musicvol = music_slider.getValue()/100;
				for(Music music:musics) {
					music.setVolume(mastervol*musicvol);
				};
				prefs.putFloat("MusicVolume", musicvol);
				prefs.flush();
			}
		});
		
		sound_slider = new Slider(0 ,100 ,1 ,false ,skin);
		sound_slider.setSize(554,19);
		sound_slider.setPosition(350, 100);
		sound_slider.setZIndex(1);
		
		sound_slider.setValue(soundvolume*100);
		sound_slider.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
				float soundvol = sound_slider.getValue()/100;
				prefs.putFloat("SoundVolume", soundvol);
				prefs.flush();
			}
		});
		
		stage.addActor(bg_img);
		stage.addActor(master_slider);
		stage.addActor(music_slider);
		stage.addActor(sound_slider);
		stage.addActor(label_master_vol);
		stage.addActor(label_music_vol);
		stage.addActor(label_sound_vol);
		stage.addActor(back_setting);
    }
}
