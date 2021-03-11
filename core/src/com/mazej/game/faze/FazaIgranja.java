package com.mazej.game.faze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.mazej.game.TheFalcon;
import com.mazej.game.TheFalcon;
import com.mazej.game.dodatki.Ladja;
import com.mazej.game.dodatki.Ovira;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.Label;
import java.lang.reflect.Array;
import java.util.IllegalFormatWidthException;

import static com.mazej.game.TheFalcon.adService;


/**
 * Created by Tomaž on 27. 02. 2018.
 */

public class FazaIgranja extends Faza {

    private static final int OVIRE_RAZMIK = 125;
    private static final int OVIRE_STEVILO = 4; // Ima 4 tube v igri
    private static final int GROUND_Y_OFFSET = -50;

    private Ladja ladja; // Ladja
    private Texture bg; // Ozadje
    private Texture tla; // Podn
    private Vector2 tlaPos1, tlaPos2;
    private com.badlogic.gdx.utils.Array<Ovira> ovire;

    private int counter;
    private int counter2;

    public String Score;
    public String HighScore;
    private SpriteBatch batch;
    private Preferences prefs = Gdx.app.getPreferences("game preferences");
    private BitmapFont yourBitmapFontName;

    private int r;
    public static boolean pause;
    private boolean first = true;
    private float totalTime = 4;

    public FazaIgranja(GameStateManager gsm) {
        super(gsm);
        pause = false;
        batch = new SpriteBatch();
        Score = "0";
        counter2 = prefs.getInteger("HIGHSCORE");
        HighScore = "HIGHSCORE: " + counter2;
        counter = 0;
        prefs.putInteger("SCORE", counter);
        prefs.flush();
        yourBitmapFontName = generatefont();

        String[] tab = {"meni.png", "hoth.png", "tattoine2.png", "cloud.png", "mustafar3.png", "geonosis.png", "endor.png"};
        r = (int) (Math.random() * (7));

        ladja = new Ladja(50, 230); //koordinate ladje na zacetku
        cam.setToOrtho(false, TheFalcon.WIDTH / 2, TheFalcon.HEIGHT / 2); //omejimo vidno polje

        bg = new Texture(tab[r]);

        if (r == 0)
            tla = new Texture("tla.png");
        if (r == 1)
            tla = new Texture("tla7.png");
        if (r == 2)
            tla = new Texture("tla8.png");
        if (r == 3)
            tla = new Texture("tla3.png");
        if (r == 4)
            tla = new Texture("tla5.png");
        if (r == 5)
            tla = new Texture("tla9.png");
        if (r == 6)
            tla = new Texture("tla10.png");

        tlaPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        tlaPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + tla.getWidth(), GROUND_Y_OFFSET);

        ovire = new com.badlogic.gdx.utils.Array<Ovira>();

        for (int i = 1; i <= OVIRE_STEVILO; i++) {
            ovire.add(new Ovira(i * (OVIRE_RAZMIK + Ovira.OVIRA_SIRINA))); //ustvari 4 ovire
        }
        Ladja.PREMIKANJE = 100;
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) { // Ko se dotaknemo ekrana naredi skok
            ladja.jump();
        }
    }

    @Override
    public void update(float dt) {

        if (pause) {
            first = true;
            totalTime = 4;
        } else {
            handleInput();
            updateGround();

            // Ce je prvic se naredi timer 3s
            if (first) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        first = false;
                        totalTime = 4;
                    }
                }, 4);
            } else {
                if (dt <= 0) {
                    dt = .001f;
                }
                ladja.update(dt);
            }
            cam.position.x = ladja.getPosition().x + 80;

            for (int i = 0; i < ovire.size; i++) {
                Ovira ovira = ovire.get(i);

                if (cam.position.x - (cam.viewportWidth / 2) > ovira.getPosTopTube().x + ovira.getTopTube().getWidth()) { // Ce je ovira na levi strani enkrana
                    ovira.reposition(ovira.getPosTopTube().x + ((Ovira.OVIRA_SIRINA + OVIRE_RAZMIK) * OVIRE_STEVILO));
                }

                if (ovira.collides(ladja.getBounds())) { // Ce se ladja zaleti v oviro
                    adService.showInterstitial();
                    gsm.set(new KoncnaFaza(gsm));
                    r = (int) (Math.random() * (4 - 0));
                }

                if (ovira.counter(ladja.getBounds()) && ovira.getCheck() == 0) {
                    if (counter % 10 == 0 && counter != 0)
                        Ladja.PREMIKANJE = Ladja.PREMIKANJE + 5;
                    counter += 1;
                    Score = "" + counter;
                    prefs.putInteger("SCORE", counter);
                    prefs.flush();
                    ovira.setCheck(1);
                    System.out.println(counter);
                }

                if (counter > counter2) {
                    counter2 = counter;
                    HighScore = "HIGHSCORE: " + counter2;

                    prefs.putInteger("HIGHSCORE", counter2);
                    prefs.flush();
                }
            }

            if (ladja.getPosition().y <= tla.getHeight() + GROUND_Y_OFFSET) { // Ce ladja pade na tla
                gsm.set(new KoncnaFaza(gsm));
            }
            cam.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        // Countdown
        float deltaTime = Gdx.graphics.getRawDeltaTime();
        totalTime -= deltaTime;
        int seconds = ((int) totalTime) % 60;

        // Velikost texta s fontom
        GlyphLayout glyphLayout = new GlyphLayout();
        String item = Score;
        glyphLayout.setText(yourBitmapFontName, item);
        float w = glyphLayout.width;

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0); // Narisemo ozadje
        sb.draw(ladja.getTexture(), ladja.getPosition().x, ladja.getPosition().y);  // Ga narisemo na koordinate
        for (Ovira ovira : ovire) {
            sb.draw(ovira.getTopTube(), ovira.getPosTopTube().x, ovira.getPosTopTube().y);
            sb.draw(ovira.getBottomTube(), ovira.getPosBotTube().x, ovira.getPosBotTube().y);
        }
        sb.draw(tla, tlaPos1.x, tlaPos1.y);
        sb.draw(tla, tlaPos2.x, tlaPos2.y);
        sb.end();

        batch.begin();
        if (first) {
            yourBitmapFontName.draw(batch, "" + seconds, Gdx.graphics.getWidth() / 2 - w / 2, Gdx.graphics.getHeight() / 2 + 400);
        } else {
            yourBitmapFontName.draw(batch, Score, Gdx.graphics.getWidth() / 2 - w / 2, 150);
        }
        batch.end();
    }

    private void updateGround() {

        if (cam.position.x - (cam.viewportWidth / 2) > tlaPos1.x + tla.getWidth()) {
            tlaPos1.add(tla.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > tlaPos2.x + tla.getWidth()) {
            tlaPos2.add(tla.getWidth() * 2, 0);
        }
    }

    public BitmapFont generatefont() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 150;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        BitmapFont font32 = generator.generateFont(parameter); // font size 32 pixels
        generator.dispose();
        return font32;
    }

    @Override
    public void dispose() {

        bg.dispose();
        ladja.dispose();
        tla.dispose();
        for (Ovira ovira : ovire)
            ovira.dispose();
        System.out.println("Faza Igranja Disposed");
    }
}
