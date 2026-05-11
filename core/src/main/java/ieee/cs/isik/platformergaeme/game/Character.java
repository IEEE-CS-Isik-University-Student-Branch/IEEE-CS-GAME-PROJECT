package ieee.cs.isik.platformergaeme.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import org.jetbrains.annotations.NotNull;

public enum Character {
    C_12("12"),
    C_ALIEN("Alien"),
    C_GANDAL("Gandal");

    final String skin_name;

    Character(@NotNull String skin_name) {
        this.skin_name = skin_name;
    }

    public CharacterEntity loadEntity(final AssetManager assets, final int id, @NotNull Body body) {

        Material mat = getMaterial(assets);

        return new CharacterEntity(id, 0, "Character", 100f, 100f, body, mat, null, null);
    }

    private Material getMaterial(final AssetManager assets) {
        Texture atlas = assets.get(String.format("/16_Charter_Game/%1$s/%1$s.png", skin_name), Texture.class);
        int frameWidth = atlas.getWidth() / 4;
        int frameHeight = atlas.getHeight() / 4;
        TextureRegion[][] frames = new TextureRegion(atlas).split(frameWidth, frameHeight);

        TextureRegion[] idleFrames = frames[0];
        TextureRegion[] walkFrames = frames[1];
        TextureRegion jumpFrame = frames[0][1];

        Animation<TextureRegion> idleAnimation = new Animation<>(1f, idleFrames);
        AnimationMaterial idleMat = new AnimationMaterial(idleAnimation);

        Animation<TextureRegion> walkAnimation = new Animation<>(1f, walkFrames);
        AnimationMaterial walkMat = new AnimationMaterial(walkAnimation);

        TextureMaterial jumpMat = new TextureMaterial(jumpFrame);

        StateMaterial stateMaterial = new StateMaterial();
        stateMaterial.materials.add(idleMat);
        stateMaterial.materials.add(walkMat);
        stateMaterial.materials.add(jumpMat);

        for(int i=0; i < 3; i++)
            stateMaterial.stateIndexes.put(i, i);

        return stateMaterial;
    }
}
