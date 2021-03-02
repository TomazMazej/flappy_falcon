package com.mazej.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mazej.game.dodatki.AdService;
import com.mazej.game.faze.GameStateManager;
import com.mazej.game.faze.FazaMenu;

public class TheFalcon extends ApplicationAdapter {

	public static final int WIDTH=480; //sirina okna
	public static final int HEIGHT=800; //visina okna
	public static final String TITLE = "Flappy Falcon"; //naslov

	private GameStateManager gsm;
	private SpriteBatch batch;

	private Music music;

	public Texture zgrOvira, spdOvira;

	public static AdService adService;

	public TheFalcon(AdService ads){
		adService=ads;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music= Gdx.audio.newMusic(Gdx.files.internal("glasba2.mp3")); //dodajanje glasbe
		music.setLooping(true); //se ponavlja
		music.setVolume(0.5f); //glasnost glasbe
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new FazaMenu(gsm)); //najprej potisne naprej menu
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //pobrise okno in narise vse se enkrat
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	public void dispose(){
		super.dispose();
	}
}