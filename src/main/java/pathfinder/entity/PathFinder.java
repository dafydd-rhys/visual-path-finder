package pathfinder.entity;

import java.util.ArrayList;

public class PathFinder {

    private final Tile starting;
    private final Tile goal;
    private boolean goalReached = false;

    private final ArrayList<Tile> openList = new ArrayList<>();

    private final int width;
    private final int height;

    public PathFinder(Board board, Tile starting, Tile goal) {
        this.starting = starting;
        this.goal = goal;
        this.width = board.getTiles()[0].length;
        this.height = board.getTiles().length;

        board.resetTilesStatus(false);
        goal.setColour("#e69f95");

        setCostOnTile(board);
        search(board);
    }

    private void search(Board board) {
        Tile tile = board.getTiles()[starting.getY()][starting.getX()];

        boolean empty = false;
        while (!goalReached && !empty) {
            int x = tile.getX();
            int y = tile.getY();

            tile.setChecked(true);
            openList.remove(tile);

            if (x - 1 >= 0) {
                openTile(board.getTiles()[y][x - 1], tile);
            }
            if (x + 1 < width) {
                openTile(board.getTiles()[y][x + 1], tile);
            }
            if (y - 1 >= 0) {
                openTile(board.getTiles()[y - 1][x], tile);
            }
            if (y + 1 < height) {
                openTile(board.getTiles()[y + 1][x], tile);
            }

            int bestIndex = 0;
            int bestFCost = 999;
            for (int i = 0; i < openList.size(); i++) {
                Tile openTile = openList.get(i);

                if (openTile.getFCost() < bestFCost) {
                    bestIndex = i;
                    bestFCost = openTile.getFCost();
                } else if (openTile.getFCost() == bestFCost) {
                    if (openTile.getGCost() < openList.get(bestIndex).getGCost()) {
                        bestIndex = i;
                    }
                }
            }

            if (openList.size() > 0) {
                tile = board.getTiles()[openList.get(bestIndex).getY()][openList.get(bestIndex).getX()];

                if (tile == goal) {
                    goalReached = true;
                    Tile current = goal;

                    while (current != starting) {
                        current.setOnPath(true);
                        current = current.getParent();

                        if (current != starting) {
                            current.setColour("#77e698");
                        }
                    }
                }
            } else {
                empty = true;
            }
        }
    }

    private void openTile(Tile possibleTile, Tile currentTile) {
        if (!possibleTile.isOpen() && !possibleTile.isChecked() && possibleTile.isBlocking() && !possibleTile.isOnPath()) {
            possibleTile.setOpen(true);
            possibleTile.setParent(currentTile);

            openList.add(possibleTile);
        }
    }

    private void setCostOnTile(Board board) {
        int x = 0;
        int y = 0;

        while (x < width && y < height) {
            getCost(board.getTiles()[y][x]);
            y++;

            if (y == height) {
                y = 0;
                x++;
            }
        }
    }

    private void getCost(Tile tile) {
        int xDistance = Math.abs(tile.getX() - starting.getX());
        int yDistance = Math.abs(tile.getY() - starting.getY());
        tile.setGCost(xDistance + yDistance);

        xDistance = Math.abs(tile.getX() - goal.getX());
        yDistance = Math.abs(tile.getY() - goal.getY());
        tile.setHCost(xDistance + yDistance);

        tile.setFCost(tile.getGCost() + tile.getHCost());
    }

}
