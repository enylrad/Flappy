package es.enylrad.flappy.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import es.enylrad.flappy.FlappyMain;

public class PlayState extends State {
    private Texture bird;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Texture("bird.png");
        cam.setToOrtho(false, FlappyMain.WIDTH / 2, FlappyMain.HEIGHT / 2);
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bird, 50, 50);
        sb.end();
    }

    @Override
    public void disponse() {

    }
}
