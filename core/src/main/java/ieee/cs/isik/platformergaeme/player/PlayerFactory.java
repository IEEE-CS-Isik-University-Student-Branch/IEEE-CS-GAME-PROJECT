package ieee.cs.isik.platformergaeme.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PlayerFactory {
    public static Body create(World world, float startX, float startY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startX, startY);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.35f, 0.45f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);
        shape.dispose();

        PolygonShape footSensor = new PolygonShape();
        footSensor.setAsBox(0.25f, 0.05f, new Vector2(0, -0.45f), 0);
        FixtureDef footFixture = new FixtureDef();
        footFixture.shape = footSensor;
        footFixture.isSensor = true;
        body.createFixture(footFixture).setUserData("foot");
        footSensor.dispose();

        return body;
    }
}
