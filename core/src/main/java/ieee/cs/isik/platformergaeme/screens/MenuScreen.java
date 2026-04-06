package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen implements Screen {
    private Stage stage;
    private Texture buttontexture;
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    /**
     * Initializes the menu UI and input when this screen is shown.
     */
    @Override
    public void show() {
        stage= new Stage();
        Gdx.input.setInputProcessor(stage);
        buttontexture= new Texture(Gdx.files.internal("Buttons.png"));

        TextureRegionDrawable drawable= new TextureRegionDrawable(buttontexture);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = drawable;
        style.down = drawable;
        style.font = new BitmapFont();

        TextButton button = new TextButton("Play", style);
        button.setPosition(200, 300);
        button.addListener(new ClickListener());
        stage.addActor(button);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

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

    }
}
