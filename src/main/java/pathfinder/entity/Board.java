package pathfinder.entity;

public class Board {

    private final Tile[][] tiles;

    public Board(int width, int height) {
        tiles = new Tile[height][width];

        // initiate tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(x, y);
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void resetTilesStatus(boolean colours) {
        for (Tile[] tile : tiles) {
            for (int x = 0; x < tiles[0].length; x++) {
                tile[x].toDefault(colours);
            }
        }
    }

    public void resetTilesToDefault() {
        for (Tile[] tile : tiles) {
            for (int x = 0; x < tiles[0].length; x++) {
                tile[x].reset();
            }
        }
    }

    public void resetTilesForMulti() {
        for (Tile[] tile : tiles) {
            for (int x = 0; x < tiles[0].length; x++) {
                tile[x].resetForMulti();
            }
        }
    }

}
