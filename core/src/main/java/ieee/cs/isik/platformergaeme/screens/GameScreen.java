package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ieee.cs.isik.platformergaeme.game.CharacterEntity;
import ieee.cs.isik.platformergaeme.game.Pack16Character;
import ieee.cs.isik.platformergaeme.GameManager;
import java.util.LinkedList;

public class GameScreen implements Screen {

    /** This {@link World} object is part of the physics engine 'box2d'
     * @see World
     */

    TiledMap harita = new TmxMapLoader().load("adsız.tmx");;
    OrthogonalTiledMapRenderer map = new OrthogonalTiledMapRenderer(harita);
    OrthographicCamera camera = new OrthographicCamera();
    {
        camera.zoom = 1.5f;
        camera.update();
    }

    public final World physicsWorld = new World(
        new Vector2(0, -9.8f), // Default gravity of the World, 9.8 m / s^2 to the down
        true // Allow sleep state, this will ignore in active bodies which is going to improve  game performance
    );


    final LinkedList<ieee.cs.isik.platformergaeme.game.Entity> entities = new LinkedList<ieee.cs.isik.platformergaeme.game.Entity>();

    SpriteBatch batch = new SpriteBatch();

    AssetManager assets = new AssetManager();
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        // Set default background color
        Gdx.gl20.glClearColor(0, 0, 0, 1);

        for(ieee.cs.isik.platformergaeme.AssetPair pair: getAssets())
            if(!assets.isLoaded(pair.assetPath, pair.assetClass))
                assets.load(pair.assetPath, pair.assetClass);

        var prop = map.getMap().getProperties();
        float meters2PixelsRatio = prop.get("tileheight", Integer.class);
        GameManager.setMeter2PixelsRatio(meters2PixelsRatio);

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render/frame.
     */
    @Override
    public void render(float delta) {
        if(!assets.update()) {
            /* Loading */
            return;
        } else {
            if(entities.isEmpty()) {
                CharacterEntity myChar = addMainChar();
                myChar.body.getPosition().set(100f, 100f);
            }
        }

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

        map.setView(camera);
        map.render();

        batch.setProjectionMatrix(camera.projection);
        batch.begin();
        for(ieee.cs.isik.platformergaeme.game.Entity entity: entities) {
            ieee.cs.isik.platformergaeme.game.Material mat = entity.material;
            mat.act(delta);
            final Vector2 pos = entity.body.getPosition();
            TextureRegion texture = mat.getFrame();

            float whRatio = (float)texture.getRegionWidth()  / texture.getRegionHeight();

            float height = GameManager.getCharacterHeightInPixels();
            float width = height * whRatio;

            batch.draw(mat.getFrame(), pos.x * GameManager.getMeter2PixelsRatio(), pos.y * GameManager.getMeter2PixelsRatio(), width, height);
        }
        batch.end();
    }

    /** Called when screen resized or when {@link Game#setScreen(Screen)} get called
     *
     * @param width is Width of the screen in pixels
     * @param height is Height of the screen in pixels
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
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

    public CharacterEntity addMainChar() {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body body = physicsWorld.createBody(def);

        Fixture Fix = body.createFixture(new CircleShape(), 1f);

        CharacterEntity myChar = Pack16Character.C_PUNPKIN.loadEntity(
            assets,
            0,
            body
        );

        entities.add(myChar);

        return myChar;
    }

    public ieee.cs.isik.platformergaeme.AssetPair[] getAssets() {
        return new ieee.cs.isik.platformergaeme.AssetPair[] {
            new ieee.cs.isik.platformergaeme.AssetPair("16_Character_Pack/PunpKin.png", Texture.class)
        };
    }
}
