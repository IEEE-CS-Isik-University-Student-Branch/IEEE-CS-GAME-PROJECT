package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {

    /** This {@link World} object is part of the physics engine 'box2d'
     * @see World
     */
    public final World physicsWorld = new World(
        new Vector2(0, -9.8f), // Default gravity of the World, 9.8 m / s^2 to the down
        true // Allow sleep state, this will ignore in active bodies which is going to improve  game performance
    );

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        // Set default background color
        Gdx.gl20.glClearColor(0, 0, 0, 1);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render/frame.
     */
    @Override
    public void render(float delta) {
        /*
         * Clear previous frame
         * This will paint entire screen to the default color that we decided in show() with Gdx.gl20.glClearColor function
         */
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Iterate the physics world according to delta time
        physicsWorld.step(
            delta,
            6, // Since the game not going to have high speed entities this much of velocity iteration is enough
            2 // If entities gets conflict so much we must increase position iterations.
        );
    }

    /** Called when screen resized or when {@link Game#setScreen(Screen)} get called
     *
     * @param width is Width of the screen in pixels
     * @param height is Height of the screen in pixels
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

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
        physicsWorld.dispose();
    }
}
