package com.teamonehundred.pixelboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
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
    private Slider slider;
    private Image bg_img;
    private Label label_vol;
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


	
    
    public SceneSettings( Music[] music) {
    	
    	
    	this.musics = music;
    	
    	final Preferences prefs = Gdx.app.getPreferences("setting\\gamesetting");
		float volume = prefs.getFloat("Volume", 0.5f);
    	
    	stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Label.LabelStyle style = new Label.LabelStyle();
		bitmapfont = new BitmapFont(Gdx.files.internal("setting\\setting.fnt"));
		
		style.font = bitmapfont;
		style.fontColor = new Color(0,0,0,1);
		label_vol = new Label("MusicsVolume",style);
		label_vol.setPosition(100,480);
		label_vol.setFontScale(1.0f);
		
		background_img = new Texture(Gdx.files.internal("setting\\bg_settings.png"));
		bg_img = new Image(new TextureRegion(background_img));
		bg_img.setSize(1280, 720);
		bg_img.setZIndex(0);
		
		back = new Texture(Gdx.files.internal("setting\\back_clicked.png"));
		back_clicked = new Texture(Gdx.files.internal("setting\\back.png"));
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
		
		
		skin = new Skin(Gdx.files.internal("setting\\setting_slider/slider.json"));
		atlas = new TextureAtlas("setting\\setting_slider/slider.atlas");
		slider = new Slider(0 ,100 ,1 ,false ,skin);
		slider.setSize(554,19);
		slider.setPosition(300, 500);
		slider.setZIndex(1);
		
		slider.setValue(volume*100);
		slider.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
				float vol = slider.getValue()/100;
				for(Music music:musics) {
					music.setVolume(vol);
				};
				prefs.putFloat("Volume", vol);
				prefs.flush();
			}
		});
		
		stage.addActor(bg_img);
		stage.addActor(slider);
		stage.addActor(label_vol);
		stage.addActor(back_setting);
    }
}
