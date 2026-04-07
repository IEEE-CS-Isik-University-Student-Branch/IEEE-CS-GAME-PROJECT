package ieee.cs.isik.platformergaeme;

public class AssetPair {
    public String assetPath;
    public Class<?> assetClass;

    public AssetPair(String assetPath, Class<?> assetClass) {
        this.assetPath = assetPath;
        this.assetClass = assetClass;
    }
}
