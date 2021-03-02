package com.mazej.game.faze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mazej.game.TheFalcon;

/**
 * Created by Tomaž on 27. 02. 2018.
 */

public class KoncnaFaza extends Faza {

    private Texture ozadje;
    private Texture konec;
    private Texture gumb;

    private SpriteBatch batch;
    private BitmapFont yourBitmapFontName;
    private String HighScore;
    private String Score;
    private int counter;
    private int counter2;
    private Preferences prefs = Gdx.app.getPreferences("game preferences");

    public KoncnaFaza(GameStateManager gsm) {
        super(gsm);
        batch = new SpriteBatch();
        cam.setToOrtho(false, TheFalcon.WIDTH/2, TheFalcon.HEIGHT/2); //omejimo vidno polje

        ozadje = new Texture("koncnafaza.png"); //ozadje
        konec = new Texture("over2.png"); //slika za konec
        gumb = new Texture("button.png"); //gumb

        counter = prefs.getInteger("SCORE");
        counter2 = prefs.getInteger("HIGHSCORE");
        HighScore = "HIGHSCORE: " + counter2;
        Score = "SCORE: " +  counter;
        yourBitmapFontName = generatefont();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){  //ce se dotaknemo enkrana(z misko, klikom...)
            Vector3 tmp=new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(tmp);
            Rectangle textureBounds = new Rectangle(cam.position.x-gumb.getWidth()/2, cam.position.y-15, gumb.getWidth(),gumb.getHeight());
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
        sb.draw(gumb, cam.position.x-gumb.getWidth()/2, cam.position.y-15);
        sb.draw(konec, cam.position.x-konec.getWidth()/2, cam.position.y + konec.getHeight()/2 + 10);
        sb.end();

        GlyphLayout glyphLayout = new GlyphLayout();
        String item = HighScore;
        glyphLayout.setText(yourBitmapFontName,item);
        float w = glyphLayout.width;

        GlyphLayout glyphLayout2 = new GlyphLayout();
        String item2 = Score;
        glyphLayout.setText(yourBitmapFontName,item);
        float w2 = glyphLayout.width;

        batch.begin();
        //yourBitmapFontName.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //yourBitmapFontName.getData().setScale(4,4);
        yourBitmapFontName.setColor(Color.GOLD);
        yourBitmapFontName.draw(batch, Score, Gdx.graphics.getWidth()/2-w2/2, 300);
        yourBitmapFontName.draw(batch, HighScore, Gdx.graphics.getWidth()/2-w/2, 200);
        batch.end();
    }

    @Override
    public void dispose() {
        ozadje.dispose();
        konec.dispose();
        System.out.println("Faza Konec Disposed");
    }

    public BitmapFont generatefont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        BitmapFont font32 = generator.generateFont(parameter); // font size 32 pixels
        //font32.getData().setScale(4f);
        generator.dispose();
        return font32;
    }
}
