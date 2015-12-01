package group2.cs301.labyrinthgame.Labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * @author Brendan Thomas
 * @version December 1, 2015
 *
 * This class is the drawing surface for the game board
 */
public class LabyrinthSurfaceView extends SurfaceView {

    private int drawLeft;
    private int drawTop;
    private int drawSize;
    private int tileSize;
    private int borderSize = 5;
    private Board board;
    private ArrayList<PlayerData> players;
    private ShiftAnimation shiftAnim;
    private MoveAnimation moveAnim;
    private AnimationThread ticker;

    public LabyrinthSurfaceView(Context context) {
        super(context);
        init();
    }//ctor

    public LabyrinthSurfaceView(Context context, AttributeSet set) {
        super(context, set);
        init();
    }//ctor

    public LabyrinthSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }//ctor

    /**
     * init
     *
     * sets up several values that are used for future drawing of the view
     */
    private void init() {
        //allow drawing on the canvas
        setWillNotDraw(false);

        //set the background of the canvas to a nice brown
        setBackgroundColor(Color.rgb(112, 56, 13));

        //get values for the canvas size and tile size
        int canvasHeight = getHeight();
        int canvasWidth = getWidth();
        int widthBuffer;
        int heightBuffer;
        int genBuffer;

        //get what we need to draw the canvas as a square
        //canvasHeight - heightBuffer*2 should equal canvasWidth - widthBuffer*2
        if (canvasHeight < canvasWidth) {
            widthBuffer = (canvasWidth - canvasHeight) / 2;
            heightBuffer = 0;
        }
        else if (canvasHeight > canvasWidth) {
            heightBuffer = (canvasHeight - canvasWidth) / 2;
            widthBuffer = 0;
        }
        else {
            heightBuffer = 0;
            widthBuffer = 0;
        }

        //get the general space buffer around the square, at least 4 pixels
        //the difference of all the buffers should eb even divisible by 9
        if ((canvasHeight - heightBuffer * 2) % 9 < 5) {
            genBuffer = (canvasHeight - heightBuffer * 2) % 9 + 9;
        }
        else {
            genBuffer = (canvasHeight - heightBuffer * 2) % 9;
        }

        //find out how big to draw the tiles
        drawSize = (canvasHeight - heightBuffer - genBuffer);
        tileSize = drawSize / 9;
        drawTop = canvasHeight - genBuffer - heightBuffer;
        drawLeft = canvasWidth - genBuffer - widthBuffer;
        moveAnim = null;
        shiftAnim = null;
        ticker = null;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);

        drawNormalBoard(canvas);

        //there should never be a time when both animations are running
        if (moveAnim != null) {
            //moveAnim.tick();
            //TODO do everything required for player movement animation
        }
        else if (shiftAnim != null) {
            shiftAnim.tick(canvas, this);
            if(shiftAnim.isOver()) {
                ticker.setStopped();
                shiftAnim = null;
            }
        }
        else {
            drawHighlights(canvas);
            //TODO draw players
        }
    }//draw

    /**
     * drawHighlights
     *
     * draws the highlights on all of the tiles that need to be highlighted
     *
     * @param canvas - canvas to draw the highlights on
     */
    public void drawHighlights(Canvas canvas) {
        //TODO draw the highlights
    }

    /**
     * drawNormalBoard
     *
     * draws the board with all the tiles in their permanent spot
     *
     * @param canvas - canvas to draw the tiles on
     */
    public void drawNormalBoard(Canvas canvas) {
        for (int column = 1; column < 8; column ++) {
            for (int row = 1; row < 8; row++) {
                drawTile(board.getTile(column-1, row-1), column, row, 0, 0, canvas);
            }
        }
    }

    /**
     * drawTile
     *
     * draws a given tile at a given spot on the grid, with additional offset if it's being drawn
     * part of an animation.
     *
     * @param toDraw - The tile object to be drawn
     * @param column - The column to draw the tile in, 0-8
     * @param row - The row to draw the tile in, 0-8
     * @param columnOffset - The number of pixels to offset the tile's column by
     * @param rowOffset - the number of pixels to offset the tile's row by
     * @param canvas - the canvas to draw the tile on
     */
    public void drawTile(Tile toDraw, int column, int row, int rowOffset, int columnOffset, Canvas canvas) {
        int lightGrey = Color.rgb(200,200,200);
        int medGrey = Color.rgb(125,125,125);
        int darkGrey = Color.rgb(50,50,50);

        //find pixel boundaries to draw the tile in
        int tileLeft = drawLeft + column * tileSize + columnOffset + (borderSize / 2);
        int tileRight = drawLeft + (column + 1) * tileSize + columnOffset - (borderSize / 2);
        int tileTop = drawTop + row * tileSize + rowOffset + (borderSize / 2);
        int tileBottom = drawTop + (row + 1) * tileSize + rowOffset - (borderSize / 2);

        //draw the background
        for(int blockLeft = tileLeft; blockLeft < tileRight; blockLeft += 5) {
            for(int blockTop = tileTop; blockTop < tileBottom; blockTop += 5) {
                int color;
                int blockRight = blockLeft + 5;
                int blockBottom = blockTop + 5;

                //get the color to draw this background chunk
                int rand = (int)(Math.random() * 3);
                if(rand == 2) {
                    color = lightGrey;
                }
                else if (rand == 1) {
                    color = medGrey;
                }
                else {
                    color = darkGrey;
                }

                //keep the right and bottom from overflowing the tile's space
                if(blockRight > tileRight) {
                    blockRight = tileRight;
                }
                if(blockBottom > tileBottom) {
                    blockBottom = tileBottom;
                }

                Paint toColor = new Paint();
                toColor.setColor(color);
                canvas.drawRect(blockLeft, blockTop, blockRight, blockBottom, toColor);
            }
        }//draw background

        //draw the paths
        int centerTop = (tileSize / 3) + tileTop - (borderSize / 2);
        int centerLeft = (tileSize / 3) + tileLeft - (borderSize / 2);
        int centerRight = centerLeft + (tileSize / 3);
        int centerBottom = centerTop + (tileSize / 3);

        Paint lightOrange = new Paint();
        lightOrange.setColor(Color.rgb(236, 196, 34));
        Paint darkOrange = new Paint();
        darkOrange.setColor(Color.rgb(219,154,34));

        //center
        canvas.drawRect(centerLeft, centerTop, centerRight, centerBottom, lightOrange);

        //handle top side connection
        if(toDraw.isConnected(Tile.UP)) {
            canvas.drawRect(centerLeft - borderSize, tileTop, centerLeft,  centerTop, darkOrange);
            canvas.drawRect(centerLeft, tileTop, centerRight, centerTop, lightOrange);
            canvas.drawRect(centerRight, tileTop, centerRight + borderSize, centerTop, darkOrange);
        }
        else {
            canvas.drawRect(centerLeft - borderSize, centerTop - borderSize,centerRight + borderSize, centerTop, darkOrange);
        }

        //handle left side connection
        if(toDraw.isConnected(Tile.LEFT)) {
            canvas.drawRect(tileLeft, centerTop - borderSize, centerLeft, centerTop, darkOrange);
            canvas.drawRect(tileLeft, centerTop, centerLeft, centerBottom, lightOrange);
            canvas.drawRect(tileLeft, centerBottom, centerLeft, centerBottom + borderSize, darkOrange);
        }
        else {
            canvas.drawRect(centerLeft - borderSize, centerTop - borderSize, centerLeft, centerBottom + borderSize, darkOrange);
        }

        //handle right side connection
        if(toDraw.isConnected(Tile.RIGHT)) {
            canvas.drawRect(centerRight, centerTop - borderSize, tileRight, centerTop, darkOrange);
            canvas.drawRect(centerRight, centerTop, tileRight, centerBottom, lightOrange);
            canvas.drawRect(centerRight, centerBottom, tileRight, centerBottom + borderSize, darkOrange);
        }
        else {
            canvas.drawRect(centerRight, centerTop - borderSize, centerRight + borderSize, centerBottom + borderSize, darkOrange);
        }

        //handle bottom side connection
        if(toDraw.isConnected(Tile.DOWN)) {
            canvas.drawRect(centerLeft - borderSize, centerBottom, centerLeft, tileBottom, darkOrange);
            canvas.drawRect(centerLeft, centerBottom, centerRight,tileBottom, lightOrange);
            canvas.drawRect(centerRight, centerBottom, centerRight + borderSize, tileBottom, darkOrange);
        }
        else {
            canvas.drawRect(centerLeft - borderSize, centerBottom, centerRight + borderSize, centerBottom + borderSize, darkOrange);
        }

        //TODO draw treasures

    }//drawTile

    /**
     * setBoardToDraw
     *
     * takes a board and sets that to be drawn on the canvas
     *
     * @param toDraw - the board to be drawn
     */
    public void setBoardToDraw(Board toDraw) {
        board = toDraw;
    }//setBoardToDraw

    /**
     * setPlayerData
     *
     * gives a set of player data to the view for drawing
     *
     * @param list - the arraylist of playerdata to use
     */
    public void setPlayerData(ArrayList<PlayerData> list) {
        players = list;
    }//setPlayerData

    /**
     * startShiftAnimation
     *
     * starts the thread to animate a shifting row of tiles, the setBoardToDraw method
     * should have already been called to update the board to the post-move state
     *
     * @param insertColumn - column that the tile was inserted in
     * @param insertRow - row that the tile was inserted in
     * @return - returns true if an animation was started, false if it was not started
     */
    public boolean startShiftAnimation(int insertColumn, int insertRow) {
        if(animRunning()) {
            return false;
        }
        shiftAnim = new ShiftAnimation(insertColumn, insertRow, board, tileSize);
        ticker = new AnimationThread(this);
        return true;
    }//startShiftAnimation

    /**
     * animRunning
     *
     * tells if an animation is currently running by checking the animations
     *
     * @return - true if there is an animation going, false if not
     */
    private boolean animRunning() {
        return shiftAnim!= null || moveAnim != null;
    }//animRunning
}