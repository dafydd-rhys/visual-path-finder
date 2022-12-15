package pathfinder.entity;

import javafx.scene.paint.Paint;

public class Tile {

    private Paint colour = Paint.valueOf("#c7d9f0");
    private final int x;
    private final int y;

    private boolean blocking = false;
    private boolean checked = false;
    private boolean open = false;
    private boolean onPath = false;

    private Tile parent;
    private int gCost;
    private int hCost;
    private int fCost;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    }

    public void toDefault(boolean includeColours) {
        this.checked = false;
        this.open = false;
        this.parent = null;

        if (includeColours) {
            if (!this.blocking && this.onPath) {
                colour = Paint.valueOf("#77e698");
            } else if (!this.blocking) {
                colour = Paint.valueOf("#c7d9f0");
            } else if (this.onPath) {
                this.onPath = false;
                colour = Paint.valueOf("#808080");
            }
        }
    }

    public void resetForMulti() {
        this.checked = false;
        this.open = false;
        this.parent = null;
        this.onPath = false;

        if (!this.blocking) {
            colour = Paint.valueOf("#c7d9f0");
        }
    }

    public void reset() {
        this.checked = false;
        this.open = false;
        this.blocking = false;
        this.parent = null;
        this.onPath = false;

        colour = Paint.valueOf("#c7d9f0");
    }

    public Tile getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColour(String hex) {
        this.colour = Paint.valueOf(hex);
    }

    public Paint getColour() {
        return colour;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;

        if (checked) {
            this.colour = Paint.valueOf("#edda79");
        } else {
            this.colour = Paint.valueOf("#c7d9f0");
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getGCost() {
        return gCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setFCost(int fCost) {
        this.fCost = fCost;
    }

    public int getFCost() {
        return fCost;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;

        if (blocking) {
            this.colour = Paint.valueOf("#808080");
        } else {
            this.colour = Paint.valueOf("#c7d9f0");
        }
    }

    public boolean isBlocking() {
        return !blocking;
    }

    public void setOnPath(boolean onPath) {
        this.onPath = onPath;
    }

    public boolean isOnPath() {
        return onPath;
    }

}
