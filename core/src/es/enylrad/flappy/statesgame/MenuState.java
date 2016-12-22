package es.enylrad.flappy.statesgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import es.enylrad.flappy.FlappyMain;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        cam.setToOrtho(false, FlappyMain.WIDTH / 2, FlappyMain.HEIGHT / 2);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
            disponse();
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
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(playBtn, cam.position.x - (cam.viewportWidth/5), cam.position.y);
        sb.end();
    }

    @Override
    public void disponse() {
        background.dispose();
        playBtn.dispose();
    }
}
