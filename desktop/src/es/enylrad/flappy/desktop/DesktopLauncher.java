package es.enylrad.flappy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import es.enylrad.flappy.MainFlappy;
import sun.applet.Main;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = MainFlappy.WIDTH;
        config.height = MainFlappy.HEIGHT;
        config.title = MainFlappy.TITLE;
        new LwjglApplication(new MainFlappy(), config);
    }
}
