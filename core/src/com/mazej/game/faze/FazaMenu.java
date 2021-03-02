package com.mazej.game.faze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mazej.game.TheFalcon;

import java.awt.Button;

/**
 * Created by Tomaž on 27. 02. 2018.
 */

public class FazaMenu extends Faza {

    private Texture ozadje;
    private Texture gumb;
    private Texture title;

    public FazaMenu(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, TheFalcon.WIDTH/2, TheFalcon.HEIGHT/2); //omejimo vidno polje
        ozadje=new Texture("meni2.png"); //ozadje
        gumb=new Texture("button.png"); //gumb
        title=new Texture("title.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){  //ce se dotaknemo enkrana(z misko, klikom...)
            Vector3 tmp=new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(tmp);
            Rectangle textureBounds = new Rectangle(cam.position.x-gumb.getWidth()/2, cam.position.y -20, gumb.getWidth(),gumb.getHeight());
            if(textureBounds.contains(tmp.x,tmp.y)) {
                gsm.set(new FazaIgranja(gsm)); //nas preusmeri v fazo igranja
                dispose(); //se znebi kar ne potrebujemo da sprostimo spomin
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(ozadje, 0,0); //nastavi ozadje enako okvirju
        sb.draw(gumb, cam.position.x-gumb.getWidth()/2, cam.position.y -20); //gumb nastavi na sredino
        //sb.draw(title, cam.position.x-title.getWidth()/2, cam.position.y + title.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        ozadje.dispose();
        gumb.dispose();
        title.dispose();
        System.out.println("Faza Menu Disposed");
    }
}
