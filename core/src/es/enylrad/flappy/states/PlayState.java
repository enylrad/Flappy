package es.enylrad.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import es.enylrad.flappy.FlappyMain;
import es.enylrad.flappy.sprites.Bird;
import es.enylrad.flappy.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 100;
    private static final int TUBE_COUNT = 8;
    private static final int GROUND_Y_OFFSET = -30;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private int score;
    private String scoreText;
    private BitmapFont textPoints;

    private ArrayList<Tube> tubes;
    private Sound gameOver;
    private Sound scoreWin;
    private boolean tubePass;

    protected PlayState(GameStateManager gsm) {
        super(gsm);

        score = 0;
        scoreText = "score: 0";
        textPoints = new BitmapFont();

        bird = new Bird(50, 300);
        cam.setToOrtho(false, FlappyMain.WIDTH / 2, FlappyMain.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        gameOver = Gdx.audio.newSound(Gdx.files.internal("game_over.wav"));
        scoreWin = Gdx.audio.newSound(Gdx.files.internal("score_win.wav"));
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new ArrayList<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < tubes.size(); i++) {
            Tube tube = tubes.get(i);

            //Delete tube left screen
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosBotTube().x + (Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT);
                tubePass = false;
            }

            //Count bird points when it pass the tube
            if (!tubePass && ((int) tube.getPosTopTube().x + tube.getTopTube().getWidth() < (int) bird.getPosition().x)) {
                score++;
                scoreText = "score: " + score;
                tubePass = true;
                scoreWin.play(0.8f);
            }

            //Game Over
            if (tube.collides(bird.getBounds())) {
                gameOver();
            }

        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gameOver();
        }
        cam.update();
    }

    private void gameOver() {
        gsm.set(new MenuState(gsm));
        gameOver.play(0.8f);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);

        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        textPoints.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        textPoints.draw(sb, scoreText, cam.position.x + 50, 25);

        sb.end();
    }

    @Override
    public void disponse() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play State Disposed");
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
