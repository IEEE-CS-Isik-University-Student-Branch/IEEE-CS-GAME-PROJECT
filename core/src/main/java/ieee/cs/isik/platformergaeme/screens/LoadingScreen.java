package ieee.cs.isik.platformergaeme.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import ieee.cs.isik.platformergaeme.AssetPair;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class LoadingScreen<T extends Screen & ieee.cs.isik.platformergaeme.IAssetfull> implements Screen {

    @NotNull
    T targetScreen;

    public LoadingScreen(@NotNull T targetScreen, List<AssetPair> extraAssets) {
        this.targetScreen = targetScreen;
        LinkedList<AssetPair> completeAssets = new LinkedList<>();
        completeAssets.addAll(targetScreen.getAssets());
        if(extraAssets != null)
            completeAssets.addAll(extraAssets);

        var assetManager = targetScreen.getAssetManager();
        for(AssetPair asset: completeAssets)
            if(!assetManager.isLoaded(asset.assetPath, asset.assetClass))
                assetManager.load(asset.assetPath, asset.assetClass);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.gl20.glClearColor(0f, 0f, 0f, 0f);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */

    static int screenCount = 0;
    {
        screenCount++;
    }
    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boolean loadStatus = targetScreen.getAssetManager().update(10);
        System.out.printf("Screen %d: %.2f%%\n", screenCount, targetScreen.getAssetManager().getProgress() * 100);

        if(loadStatus) {
            ieee.cs.isik.platformergaeme.GameManager.getGame().setScreen(targetScreen);
            return;
        }
    }

    /**
     * @param width
     * @param height
     * @see Screen#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see Screen#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see Screen#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
