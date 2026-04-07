package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import ieee.cs.isik.platformergaeme.AssetPair;

public class MenuScreen implements Screen {
    private Stage stage = new Stage(new FillViewport(16 * 40,9*40));
    public final AssetManager assets = new AssetManager();

    private SpriteBatch debugBatch = new SpriteBatch();
    private BitmapFont debugIndicator = new BitmapFont();

    private boolean isStageBuild = false;
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     * Initializes the menu UI and input when this screen is shown.
     */
    @Override
    public void show() {
        // Set default background color
        Gdx.gl.glClearColor(0, 0, 0, 1);

        // Set current input processor to the stage
        Gdx.input.setInputProcessor(stage);


        for(AssetPair pair: getAssets())
            if(!assets.isLoaded(pair.assetPath, pair.assetClass))
                assets.load(pair.assetPath, pair.assetClass);
    }

    // Initialize the stage when new instance of MenuScreen created
    private void buildStage() {
        Texture backgroundTexture = assets.get("favicon.jpg", Texture.class);

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(backgroundImage);


        Texture singlePlayerButtonTexture = assets.get("favicon.jpg", Texture.class);
        Texture multiPlayerButtonTexture = assets.get("favicon.jpg", Texture.class);
        TextButton.TextButtonStyle singlePlayerButtonStyle = new TextButton.TextButtonStyle(),
                                    multiplayerButtonStyle = new TextButton.TextButtonStyle();

        singlePlayerButtonStyle.up = new TextureRegionDrawable(singlePlayerButtonTexture);
        singlePlayerButtonStyle.down = new TextureRegionDrawable(singlePlayerButtonTexture);
        singlePlayerButtonStyle.font = new BitmapFont();

        multiplayerButtonStyle.up = new TextureRegionDrawable(multiPlayerButtonTexture);
        multiplayerButtonStyle.down = new TextureRegionDrawable(multiPlayerButtonTexture);
        multiplayerButtonStyle.font = new BitmapFont();

        TextButton singlePlayerButton = new TextButton("Single Player", singlePlayerButtonStyle);
        TextButton multiPlayerButton = new TextButton("Multi Player", multiplayerButtonStyle);

        multiPlayerButton.setPosition(0, stage.getHeight()/3);
        multiPlayerButton.setHeight(stage.getHeight() / 6);
        multiPlayerButton.setWidth(stage.getWidth() / 5);

        singlePlayerButton.setPosition(0, stage.getHeight()/2);
        singlePlayerButton.setHeight(stage.getHeight() / 6);
        singlePlayerButton.setWidth(stage.getWidth() / 5);

        stage.addActor(singlePlayerButton);
        stage.addActor(multiPlayerButton);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if(assets.update()) { // returns true if all assets loaded
            if(!isStageBuild) {
                buildStage();
                isStageBuild = true;
            }
        } else {
            // Indicate that screen is loading.
            debugBatch.begin();
            debugIndicator.draw(debugBatch, "Loading", 0, 0);
            debugBatch.end();
        }
        /*
         * Clear previous frame
         * This will paint entire screen to the default color that we decided in show() with Gdx.gl20.glClearColor function
         */
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw GUI
        stage.act(delta);
        stage.draw();
    }

    /** Called when screen resized or when {@link Game#setScreen(Screen)} get called
     *
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /** Called when application/screen paused
     *
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
    }

    /** Called when application/screen resumed
     *
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
        assets.dispose();
    }

    public AssetPair[] getAssets() {
        return new AssetPair[] {
            new AssetPair("favicon.jpg", Texture.class)
        };
    }
}
