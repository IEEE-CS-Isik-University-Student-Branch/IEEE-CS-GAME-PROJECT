package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import ieee.cs.isik.platformergaeme.engine.GameEngine;

public class GameScreen implements Screen {

    // World units == tiles (1 world unit = 1 tile = 128 px)
    private static final float TILE_SIZE      = 128f;
    private static final float VIEWPORT_WIDTH  = 20f;   // tiles visible horizontally
    private static final float VIEWPORT_HEIGHT = 15f;   // tiles visible vertically
    private static final float MAP_WIDTH       = 80f;   // total map width in tiles
    private static final float MAP_HEIGHT      = 69f;   // total map height in tiles

    // Player spawn position (world units)
    private static final float PLAYER_START_X = 15.0f;
    private static final float PLAYER_START_Y = 2.0f;

    // ------- core objects -------
    private final TiledMap                    map;
    private final OrthogonalTiledMapRenderer  mapRenderer;
    private final OrthographicCamera          camera;
    private final GameEngine                  gameEngine;
    private final SpriteBatch                 spriteBatch;
    private final Box2DDebugRenderer          debugRenderer;

    // ------- player sprite -------
    private final Texture                     playerTexture;
    private final Animation<TextureRegion>    idleAnimation;
    private final Animation<TextureRegion>    walkAnimation;
    private final TextureRegion               jumpFrame;

    private float   animTime     = 0f;
    private boolean facingRight  = true;

    public GameScreen() {
        // ---- map ----
        map         = new TmxMapLoader().load("adsız.tmx");
        // unit scale: 1 world unit = TILE_SIZE pixels  →  1/128
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / TILE_SIZE);

        // ---- camera ----
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        // ---- physics + player ----
        gameEngine = new GameEngine(map, PLAYER_START_X, PLAYER_START_Y);

        // ---- rendering ----
        spriteBatch   = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();

        // ---- character sprite sheet ----
        // Alien.png: 4 columns (frames) x 2 rows  (row 0 = idle, row 1 = walk)
        playerTexture = new Texture(Gdx.files.internal("16_Character_Pack/Alien.png"));
        int frameW = playerTexture.getWidth()  / 4;
        int frameH = playerTexture.getHeight() / 2;
        TextureRegion[][] split = TextureRegion.split(playerTexture, frameW, frameH);

        idleAnimation = new Animation<>(0.2f, split[0]);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        walkAnimation = new Animation<>(0.1f, split[1]);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        jumpFrame = split[0][1];
    }

    // -------------------------------------------------------------------------

    @Override
    public void show() {
        // Sky-blue background
        Gdx.gl.glClearColor(0.4f, 0.7f, 1.0f, 1f);
    }

    @Override
    public void render(float delta) {
        // --- update ---
        gameEngine.update(delta);
        animTime += delta;

        // --- camera follow (clamped to map bounds) ---
        Vector2 playerPos = gameEngine.getPlayerBody().getPosition();
        float camX = Math.max(VIEWPORT_WIDTH  / 2f,
                     Math.min(playerPos.x, MAP_WIDTH  - VIEWPORT_WIDTH  / 2f));
        float camY = Math.max(VIEWPORT_HEIGHT / 2f,
                     Math.min(playerPos.y, MAP_HEIGHT - VIEWPORT_HEIGHT / 2f));
        camera.position.set(camX, camY, 0);
        camera.update();

        // --- draw ---
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();

        drawPlayer(playerPos);

        // Debug overlay – comment out for release builds
        debugRenderer.render(gameEngine.getPhysicsWorld(), camera.combined);
    }

    private void drawPlayer(Vector2 pos) {
        Vector2 vel     = gameEngine.getPlayerBody().getLinearVelocity();
        boolean onGround = gameEngine.getPlayerController().isOnGround();

        // track facing direction
        if      (vel.x >  0.1f) facingRight = true;
        else if (vel.x < -0.1f) facingRight = false;

        // choose frame
        TextureRegion baseFrame;
        if (!onGround) {
            baseFrame = jumpFrame;
        } else if (Math.abs(vel.x) > 0.1f) {
            baseFrame = walkAnimation.getKeyFrame(animTime);
        } else {
            baseFrame = idleAnimation.getKeyFrame(animTime);
        }

        // copy so we don't mutate the cached region's flip state
        TextureRegion drawFrame = new TextureRegion(baseFrame);
        boolean shouldFlip = !facingRight;
        if (shouldFlip != drawFrame.isFlipX()) {
            drawFrame.flip(true, false);
        }

        // sprite is 0.7 × 0.95 world units, centred on the body
        float w = 0.7f;
        float h = 0.95f;

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(drawFrame,
                pos.x - w / 2f,
                pos.y - h / 2f,
                w, h);
        spriteBatch.end();
    }

    // -------------------------------------------------------------------------

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.update();
    }

    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        gameEngine.dispose();
        spriteBatch.dispose();
        debugRenderer.dispose();
        playerTexture.dispose();
    }
}
