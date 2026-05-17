package ieee.cs.isik.platformergaeme.game.mapmanagers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ieee.cs.isik.platformergaeme.game.MapManager;

public class TestMap implements MapManager {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera camera;

    public TestMap(TiledMap map, OrthographicCamera camera) {
        this.map = map;
        renderer = new OrthogonalTiledMapRenderer(map);
        this.camera = camera;
    }


    @Override
    public void render(float delta) {
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
