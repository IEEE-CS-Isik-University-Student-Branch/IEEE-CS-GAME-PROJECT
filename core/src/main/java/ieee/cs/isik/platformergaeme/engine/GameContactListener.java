package ieee.cs.isik.platformergaeme.engine;

import com.badlogic.gdx.physics.box2d.*;
import ieee.cs.isik.platformergaeme.player.PlayerController;

public class GameContactListener implements ContactListener {
    private final PlayerController playerController;

    public GameContactListener(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (isFootSensor(a) || isFootSensor(b)) playerController.onGroundContact();
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (isFootSensor(a) || isFootSensor(b)) playerController.onGroundLeave();
    }

    @Override public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override public void postSolve(Contact contact, ContactImpulse impulse) {}

    private boolean isFootSensor(Fixture fixture) {
        return fixture.isSensor() && "foot".equals(fixture.getUserData());
    }
}
