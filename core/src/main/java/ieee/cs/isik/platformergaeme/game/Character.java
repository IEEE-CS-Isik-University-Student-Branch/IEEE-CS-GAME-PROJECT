package ieee.cs.isik.platformergaeme.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import org.jetbrains.annotations.NotNull;

public interface Character {
    public CharacterEntity loadEntity(final AssetManager assets, final int id, @NotNull Body body);
}
