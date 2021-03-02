package com.mazej.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mazej.game.TheFalcon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=TheFalcon.WIDTH;
		config.height=TheFalcon.HEIGHT;
		config.title=TheFalcon.TITLE;
		new LwjglApplication(new TheFalcon(), config);
	}
}
