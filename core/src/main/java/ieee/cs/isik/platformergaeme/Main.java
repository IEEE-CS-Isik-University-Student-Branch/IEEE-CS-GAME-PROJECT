package ieee.cs.isik.platformergaeme;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


/**
 * When Game get created, {@link Main#create()} function gets called. In this function we define {@link GameManager#game} to current {@link Main}
 *
 * This project use OpenGL 2.0
 * @see Game
 * @see GameManager
 **/
public class Main extends Game {
    @Override
    public void create() {
        /*
         * Set this 'Game' object as global 'Game' object of GameManager
         */
        GameManager.game = this;
        // Set process name (Name of the window in desktop/laptop or name of the app in mobile)
        Gdx.graphics.setTitle("IEEE CS Ekibi Mario");

        // Force window to fullscreen
        Gdx.graphics.setFullscreenMode(
            Gdx.graphics.getDisplayMode(
                Gdx.graphics.getMonitor()
            )
        );

        // Show default to GameScreen screen till we start MenuScreen
        GameManager.show(GameManager.ScreenType.MenuType);
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        // Force window to fullscreen
        Gdx.graphics.setFullscreenMode(
            Gdx.graphics.getDisplayMode(
                Gdx.graphics.getMonitor()
            )
        );
    }}
