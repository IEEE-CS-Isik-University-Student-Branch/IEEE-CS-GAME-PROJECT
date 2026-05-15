package ieee.cs.isik.platformergaeme.engine;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import ieee.cs.isik.platformergaeme.player.PlayerController;
import ieee.cs.isik.platformergaeme.player.PlayerFactory;

public class GameEngine implements Disposable {
    private static final Vector2 GRAVITY = new Vector2(0, -20f);
    private static final int VELOCITY_ITERATIONS = 8;
    private static final int POSITION_ITERATIONS = 3;

    private final World physicsWorld;
    private final Body playerBody;
    private final PlayerController playerController;

    public GameEngine(TiledMap map, float playerStartX, float playerStartY) {
        physicsWorld = new World(GRAVITY, true);
        playerBody = PlayerFactory.create(physicsWorld, playerStartX, playerStartY);
        playerController = new PlayerController(playerBody);
        physicsWorld.setContactListener(new GameContactListener(playerController));
        MapBodyBuilder.buildBodies(map, physicsWorld);
    }

    public void update(float delta) {
        playerController.handleInput();
        physicsWorld.step(delta, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    public World getPhysicsWorld() { return physicsWorld; }
    public Body getPlayerBody() { return playerBody; }
    public PlayerController getPlayerController() { return playerController; }

    @Override
    public void dispose() {
        physicsWorld.dispose();
    }
}
