package ieee.cs.isik.platformergaeme.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerController {
    private static final float MOVE_SPEED = 7f;
    private static final float JUMP_IMPULSE = 10f;

    private final Body body;
    private boolean onGround = false;
    private int groundContactCount = 0;

    public PlayerController(Body body) {
        this.body = body;
    }

    public void handleInput() {
        float velX = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            velX = -MOVE_SPEED;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            velX = MOVE_SPEED;
        body.setLinearVelocity(velX, body.getLinearVelocity().y);

        if (onGround && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                || Gdx.input.isKeyJustPressed(Input.Keys.UP)
                || Gdx.input.isKeyJustPressed(Input.Keys.W))) {
            body.applyLinearImpulse(0, JUMP_IMPULSE,
                    body.getWorldCenter().x, body.getWorldCenter().y, true);
            onGround = false;
        }
    }

    public void onGroundContact() {
        groundContactCount++;
        onGround = true;
    }

    public void onGroundLeave() {
        groundContactCount = Math.max(0, groundContactCount - 1);
        onGround = groundContactCount > 0;
    }

    public boolean isOnGround() { return onGround; }
    public Body getBody() { return body; }
}
