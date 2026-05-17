package ieee.cs.isik.platformergaeme.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public interface MapManager {
    public void render(float delta);
    public void dispose();
}
