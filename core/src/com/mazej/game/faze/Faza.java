package com.mazej.game.faze;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Tomaž on 27. 02. 2018.
 */

public abstract class Faza {

    protected OrthographicCamera cam; //locira pozicijo v svetu
    protected Vector3 mouse; //XYZ koordinate
    protected GameStateManager gsm;
    public Texture zgrOvira, spdOvira;

    protected Faza(GameStateManager gsm){ //konstruktor
        this.gsm=gsm;
        cam=new OrthographicCamera();
        mouse=new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt); //Razlika med urejenimi Frejmi(FPS)
    public abstract void render(SpriteBatch sb); //Sprite batch uredi vse na ekran
    public abstract void dispose();
}
