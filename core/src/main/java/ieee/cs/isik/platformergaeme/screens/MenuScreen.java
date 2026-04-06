package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen implements Screen {
    private Stage stage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Vector2 playerPosition;
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */

    /**
     * Initializes the menu UI and input when this screen is shown.
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("favicon.jpg"));
        playerPosition = new Vector2(0, 0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture startbutton = new Texture(Gdx.files.internal("favicon.jpg"));
        Texture multiplayerbutton = new Texture(Gdx.files.internal("favicon.jpg")) ;
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = new TextureRegionDrawable(startbutton);
        style.down = new TextureRegionDrawable(startbutton);
        style.font = new BitmapFont();
        style.up = new TextureRegionDrawable(multiplayerbutton);
        style.down = new TextureRegionDrawable(multiplayerbutton);
        style.font = new BitmapFont();

        TextButton SinglePlayer = new TextButton("Single Player", style);
        TextButton MultiPlayer = new TextButton("Multi Player", style);
        MultiPlayer.setPosition(0, Gdx.graphics.getHeight()/3);
        MultiPlayer.setHeight(Gdx.graphics.getHeight() / 6);
        MultiPlayer.setWidth(Gdx.graphics.getWidth() / 5);
        SinglePlayer.setPosition(0, Gdx.graphics.getHeight()/2);
        SinglePlayer.setHeight(Gdx.graphics.getHeight() / 6);
        SinglePlayer.setWidth(Gdx.graphics.getWidth() / 5);
        stage.addActor(SinglePlayer);
        stage.addActor(MultiPlayer);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */

    /**
    * Change play button's position and Reorganize background +added 1 gif background+
     */
    @Override
    public void render(float delta) {
        camera.position.set(playerPosition.x, playerPosition.y, 0);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, Gdx.graphics.getWidth()/-2, Gdx.graphics.getHeight()/-2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth=width;
        camera.viewportHeight=height;
        camera.update();
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {
    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
