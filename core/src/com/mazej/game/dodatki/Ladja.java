package com.mazej.game.dodatki;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Tomaž on 27. 02. 2018.
 */

public class Ladja {

    private static final int GRAVITACIJA = -15; // Gravitacija
    public static int PREMIKANJE = 100; // Premikanje

    private Vector3 pozicija; // Pozicija
    private Vector3 hitrost; // Hitrost
    private Rectangle meje;

    private Texture ladja;

    public Ladja(int x, int y) {
        pozicija = new Vector3(x, y, 0);
        hitrost = new Vector3(0, 0, 0);
        ladja = new Texture("falcon.png");
        meje = new Rectangle(x, y, ladja.getWidth(), ladja.getHeight());
    }

    public void update(float dt) {

        if (pozicija.y > 0){
            hitrost.add(0, GRAVITACIJA, 0); // Pada s hitrostjo gravitacije
        }
        hitrost.scl(dt);
        pozicija.add(PREMIKANJE * dt, hitrost.y, 0);
        if (pozicija.y < 0){
            pozicija.y = 0; // Ne pade pod okno
        }
        hitrost.scl(1 / dt);
        meje.setPosition(pozicija.x, pozicija.y);
    }

    public Vector3 getPosition() {
        return pozicija;
    }

    public Texture getTexture() {
        return ladja;
    }

    public void jump() {
        hitrost.y = 250;
    }

    public Rectangle getBounds() {
        return meje;
    }

    public void dispose() {
        ladja.dispose();
    }
}
