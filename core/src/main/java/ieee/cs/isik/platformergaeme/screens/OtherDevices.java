package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class OtherDevices implements ApplicationListener {
    private float scaleFactor;

    @Override
    public void create() {
        detectPlatformAndConfigure();
    }

      private void detectPlatformAndConfigure() {

        ApplicationType type = Gdx.app.getType();

        if (type == ApplicationType.Android) {
            scaleFactor = Math.max(1f, Gdx.graphics.getDensity());

        } else if (type == ApplicationType.iOS) {
            scaleFactor = Math.max(1f, Gdx.graphics.getDensity());

        } else if (type == ApplicationType.Desktop) {
            scaleFactor = 1f;

        } else {
            scaleFactor = 1f;

        }
        Gdx.app.log("Platform", "Scale factor: " + scaleFactor);

    }

    @Override
    public void render() {
    }

    @Override
    public void resize(int width, int height) {}
    
    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() { }

}