package ieee.cs.isik.platformergaeme.engine;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;

public class MapBodyBuilder {

    /**
     * Parses every TiledMapTileLayer in the given map and creates a static Box2D body
     * for each solid tile.  Local tile IDs 0-2 (brick, platform, exclamation) are solid;
     * local ID 3 (coin) is visual-only and is skipped.
     */
    public static void buildBodies(TiledMap map, World world) {
        for (MapLayer layer : map.getLayers()) {
            if (!(layer instanceof TiledMapTileLayer)) continue;
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
            int width  = tileLayer.getWidth();
            int height = tileLayer.getHeight();

            for (int col = 0; col < width; col++) {
                for (int row = 0; row < height; row++) {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(col, row);
                    if (cell == null || cell.getTile() == null) continue;

                    // In libGDX the TiledMapTileLayer has row=0 at BOTTOM, so world coords
                    // map directly: (col+0.5, row+0.5) is the centre of the tile.
                    float worldX = col + 0.5f;
                    float worldY = row + 0.5f;

                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(worldX, worldY);
                    Body body = world.createBody(bodyDef);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(0.5f, 0.5f);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.friction = 0.5f;
                    body.createFixture(fixtureDef);
                    shape.dispose();
                }
            }
        }
    }
}
