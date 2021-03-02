package com.mazej.game.faze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mazej.game.dodatki.AdService;

import java.util.Stack;

/**
 * Created by Tomaž on 27. 02. 2018.
 */

public class GameStateManager {

  private Stack<Faza> faze;

  public GameStateManager(){
    faze=new Stack<Faza>();
  }

  public void push(Faza faza){ //porine
    faze.push(faza);
  }

  public void pop(){  //prikaze
    faze.pop().dispose();
  }

  public void set(Faza faza){
    faze.pop().dispose();
    faze.push(faza);
  }
  public void update(float dt){ //posodobi
    faze.peek().update(dt);
  }

  public void render(SpriteBatch sb){ //uredi
    faze.peek().render(sb);
  }
}
