package es.enylrad.flappy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import es.enylrad.flappy.FlappyMain;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = FlappyMain.WIDTH;
        config.height = FlappyMain.HEIGHT;
        config.title = FlappyMain.TITLE;
        new LwjglApplication(new FlappyMain(), config);
    }
}
