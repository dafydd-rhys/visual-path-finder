package pathfinder.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import pathfinder.entity.Board;
import pathfinder.entity.PathFinder;
import pathfinder.entity.Tile;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UIController {

    @FXML
    private Canvas canvas;
    @FXML
    private Label mode;

    private GraphicsContext context;
    private final int ySpacing = 120;
    private final int xSpacing = 20;

    private final Board board = new Board(80, 40);
    private Tile starting;
    private Tile goal;
    private ArrayList<Tile> goals = new ArrayList<>();

    private boolean multi = false;

    @FXML
    public void initialize() {
        context = canvas.getGraphicsContext2D();

        Timer ticker = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    updateCanvas();

                    canvas.getScene().setOnKeyPressed(keyEvent -> {
                        if (keyEvent.getCode() == KeyCode.ALT) {
                            reset();
                            setMulti(!multi);
                        } else if (keyEvent.getCode() == KeyCode.R) {
                            reset();
                        }
                    });
                });
            }
        };

        int millisInSecond = 1000;
        int framesPerSecond = 60;
        ticker.schedule(task, millisInSecond / framesPerSecond, millisInSecond / framesPerSecond);
    }

    private void reset() {
        starting = null;
        goal = null;
        goals = new ArrayList<>();
        board.resetTilesToDefault();
    }

    public void setMulti(boolean multi) {
        this.multi = multi;

        if (multi) {
            mode.setText("PathFinder Mode: Multiple");
        } else {
            mode.setText("PathFinder Mode: Single");
        }
    }

    private void updateCanvas() {
        if (getThisStage() == null || canvas == null) {
            return;
        }
        updateCanvasSize();
        clearCanvas();

        //board dimensions
        double width = board.getTiles()[0].length;
        double height = board.getTiles().length;

        double boardDrawHeight;
        double boardDrawWidth;
        double boardBorderDrawHeight;
        double boardBorderDrawWidth;
        double boardBorderSize = 10;
        double tileBorderWidth = 0.5;
        double cellSpacing = 1;
        double distanceFromEdge = 0.15;

        boardBorderDrawWidth = (int) canvas.getWidth() - ((width / height) * distanceFromEdge * getThisStage().getWidth());
        boardDrawWidth = boardBorderDrawWidth - ((width / height) * boardBorderSize);

        double cellSize = (boardDrawWidth - ((width + cellSpacing) * tileBorderWidth)) / width;
        boardDrawHeight = cellSize * height + ((height + cellSpacing) * tileBorderWidth);
        boardBorderDrawHeight = boardDrawHeight + ((width / height) * boardBorderSize);

        // Draw border around board.
        context.setFill(Paint.valueOf("#ffffff"));
        double borderLeft = (canvas.getWidth() - boardBorderDrawWidth) / (width / height);
        double borderTop = (canvas.getHeight() - boardBorderDrawHeight) / (width / height);
        context.fillRect(borderLeft, borderTop, boardBorderDrawWidth, boardBorderDrawHeight);

        // Draw board.
        double boardLeft = borderLeft + boardBorderSize;
        double boardTop = borderTop + boardBorderSize;
        context.setFill(Paint.valueOf("#000000"));
        context.fillRect(boardLeft, boardTop, boardDrawWidth, boardDrawHeight);

        canvas.setOnMouseClicked(e -> {
            double mX = e.getX() - canvas.getLayoutX() - boardLeft - boardBorderSize + xSpacing;
            double mY = e.getY() - canvas.getLayoutY() - boardTop - boardBorderSize + ySpacing;

            double currentBoardWidth = boardBorderDrawWidth - boardBorderSize * 2;
            double currentBoardHeight = boardBorderDrawHeight - boardBorderSize * 2;
            double tileWidthSize = currentBoardWidth / width;
            double tileHeightSize = currentBoardHeight / height;

            int tileX = (int) Math.floor(mX / tileWidthSize);
            int tileY = (int) Math.floor(mY / tileHeightSize);

            if (tileX >= 0 && tileX < width && tileY >= 0 && tileY < height) {
                Tile clickedTile = board.getTiles()[tileY][tileX];
                board.resetTilesStatus(true);

                if (e.getButton() == MouseButton.PRIMARY) {
                    board.resetTilesForMulti();

                    //purple "#c895e6"
                    if (starting != null) {
                        starting.setColour("#c7d9f0");
                    }
                    starting = clickedTile;
                    starting.setBlocking(false);
                    clickedTile.setColour("#c895e6");

                    if (!multi) {
                        if (goal != null) {
                            new PathFinder(board, starting, goal);
                            starting.setColour("#c895e6");
                        }
                    } else {
                        if (!goals.isEmpty()) {
                            if (goals.size() > 1) {
                                new PathFinder(board, starting, goals.get(0));

                                for (int i = 1; i < goals.size(); i++) {
                                    new PathFinder(board, goals.get(i - 1), goals.get(i));
                                }
                            } else {
                                new PathFinder(board, starting, goals.get(0));
                            }
                            starting.setColour("#c895e6");
                        }
                    }
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    board.resetTilesForMulti();

                    //red "#e69f95"
                    if (!multi) {
                        if (goal != null) {
                            goal.setColour("#c7d9f0");
                        }
                        goal = clickedTile;
                    } else {
                        if (starting != null || goals.isEmpty()) {
                            goals.add(clickedTile);
                        }
                    }

                    clickedTile.setBlocking(false);
                    clickedTile.setColour("#e69f95");

                    if (!multi) {
                        if (starting != null) {
                            new PathFinder(board, starting, goal);
                            starting.setColour("#c895e6");
                        }
                    } else {
                        if (starting != null) {
                            if (!goals.isEmpty()) {
                                if (goals.size() > 1) {
                                    new PathFinder(board, starting, goals.get(0));

                                    for (int i = 1; i < goals.size(); i++) {
                                        new PathFinder(board, goals.get(i - 1), goals.get(i));
                                    }
                                } else {
                                    new PathFinder(board, starting, goals.get(0));
                                }
                            }
                            starting.setColour("#c895e6");
                        }
                    }
                } else {
                    clickedTile.setBlocking(false);
                    redraw();
                }
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.MIDDLE) {
                double mX = e.getX() - canvas.getLayoutX() - boardLeft - boardBorderSize + xSpacing;
                double mY = e.getY() - canvas.getLayoutY() - boardTop - boardBorderSize + ySpacing;

                double currentBoardWidth = boardBorderDrawWidth - boardBorderSize * 2;
                double currentBoardHeight = boardBorderDrawHeight - boardBorderSize * 2;
                double tileWidthSize = currentBoardWidth / width;
                double tileHeightSize = currentBoardHeight / height;

                int tileX = (int) Math.floor(mX / tileWidthSize);
                int tileY = (int) Math.floor(mY / tileHeightSize);

                if (tileX >= 0 && tileX < width && tileY >= 0 && tileY < height) {
                    Tile clickedTile = board.getTiles()[tileY][tileX];
                    clickedTile.setBlocking(true);
                    redraw();
                }
            }
        });

        // Draw tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double tileTop = boardTop + (y * cellSize) + ((y + cellSpacing) * tileBorderWidth);
                double tileLeft = boardLeft + (x * cellSize) + ((x + cellSpacing) * tileBorderWidth);

                context.setFill(board.getTiles()[y][x].getColour());
                context.fillRect(tileLeft, tileTop, cellSize, cellSize);
            }
        }
    }

    private void redraw() {
        if (starting != null) {
            if (!multi) {
                if (goal != null) {
                    new PathFinder(board, starting, goal);
                    starting.setColour("#c895e6");
                }
            } else {
                if (!goals.isEmpty()) {
                    if (goals.size() > 1) {
                        new PathFinder(board, starting, goals.get(0));

                        for (int i = 1; i < goals.size(); i++) {
                            new PathFinder(board, goals.get(i - 1), goals.get(i));
                        }
                    } else {
                        new PathFinder(board, starting, goals.get(0));
                    }
                }
                starting.setColour("#c895e6");
            }
        }
    }

    private void updateCanvasSize() {
        canvas.setWidth(getThisStage().getWidth() - xSpacing);
        canvas.setHeight(getThisStage().getHeight() - ySpacing);
    }

    private void clearCanvas() {
        double nullValue = 0;

        context.clearRect(nullValue, nullValue, canvas.getWidth(), canvas.getHeight());
    }

    private Stage getThisStage() {
        return (Stage) canvas.getScene().getWindow();
    }

}