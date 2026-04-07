package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class MenuScreen implements Screen {
    private Stage stage = new Stage();
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     * Initializes the menu UI and input when this screen is shown.
     */
    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.input.setInputProcessor(stage);

        stage.setViewport(new FillViewport(16 * 40,9*40));

        Texture backgroundTexture = new Texture(Gdx.files.internal("favicon.jpg"));

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(backgroundImage);


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

        MultiPlayer.setPosition(0, stage.getHeight()/3);
        MultiPlayer.setHeight(stage.getHeight() / 6);
        MultiPlayer.setWidth(stage.getWidth() / 5);

        SinglePlayer.setPosition(0, stage.getHeight()/2);
        SinglePlayer.setHeight(stage.getHeight() / 6);
        SinglePlayer.setWidth(stage.getWidth() / 5);
        stage.addActor(SinglePlayer);
        stage.addActor(MultiPlayer);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        stage.getViewport().update(width, height, true);
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
    }
}
