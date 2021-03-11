package com.mazej.game.dodatki;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Tomaž on 2. 03. 2018.
 */

public class Ovira {

    public static final int OVIRA_SIRINA = 52;
    private static final int FLUKTURA = 130;
    private static final int LUKNJA = 100;
    private static final int NAJNIZJA_TOCKA = 120;

    public Texture zgrOvira, spdOvira;
    private Vector2 posZgrOvira, posSpdOvira;
    private Rectangle ZgrMeja, SpdMeja;
    private Random rand;

    private int check;
    private Rectangle scoreCounter;

    public Ovira(float x) {
        zgrOvira = new Texture("ttube.png");
        spdOvira = new Texture("btube.png");
        rand = new Random();

        posZgrOvira = new Vector2(x, rand.nextInt(FLUKTURA) + LUKNJA + NAJNIZJA_TOCKA);
        posSpdOvira = new Vector2(x, posZgrOvira.y - LUKNJA - spdOvira.getHeight());

        ZgrMeja = new Rectangle(posZgrOvira.x, posZgrOvira.y, zgrOvira.getWidth(), zgrOvira.getHeight());
        SpdMeja = new Rectangle(posSpdOvira.x, posSpdOvira.y, spdOvira.getWidth(), spdOvira.getHeight());

        scoreCounter = new Rectangle(posSpdOvira.x + spdOvira.getWidth(), posSpdOvira.y, 1, spdOvira.getHeight() + zgrOvira.getHeight() + LUKNJA);
        check = 0;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int value) {
        check = value;
    }

    public boolean counter(Rectangle player) {
        return player.overlaps(scoreCounter);
    }

    public Texture getTopTube() {
        return zgrOvira;
    }

    public Texture getBottomTube() {
        return spdOvira;
    }

    public Vector2 getPosTopTube() {
        return posZgrOvira;
    }

    public Vector2 getPosBotTube() {
        return posSpdOvira;
    }

    public void reposition(float x) {

        posZgrOvira.set(x, rand.nextInt(FLUKTURA) + LUKNJA + NAJNIZJA_TOCKA);
        posSpdOvira.set(x, posZgrOvira.y - LUKNJA - spdOvira.getHeight());
        ZgrMeja.setPosition(posZgrOvira.x, posZgrOvira.y);
        SpdMeja.setPosition(posSpdOvira.x, posSpdOvira.y);
        check = 0;
        scoreCounter.setPosition(posSpdOvira.x + spdOvira.getWidth(), posSpdOvira.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(ZgrMeja) || player.overlaps(SpdMeja);
    }

    public void dispose() {

        zgrOvira.dispose();
        spdOvira.dispose();
    }
}
