package group2.cs301.labyrinthgame.Labyrinth;

import android.graphics.Color;

import java.io.Serializable;

/**
 * @author G. Emily Nitzberg, Ben Rumptz, Brendan Thomas, Andrew Williams
 * @version December 1, 2015
 */
public class PlayerData implements Serializable{

    private int Xposition;
    private int Yposition;
    private int Xstart;
    private int Ystart;
    // Treasure Info: 0-24 possible treasures
    // 0 == no treasure's remaining
    // 1-24 == treasure ID
    private int[] treasures;
    // current treasure to search for
    private int currentTreasure; //index of treasures array
    private int playerColor;
    private int playerID;

    public PlayerData(int X, int Y, int[] initTreasures, int initID) {
        Yposition = Y;
        Xposition = X;
        Ystart = Y;
        Xstart = X;
        if (X == 0 && Y == 0) {
            playerColor = Color.RED;
        }
        else if (X == 6 && Y == 0) {
            playerColor = Color.BLUE;
        }
        else if (X == 0 && Y == 6) {
            playerColor = Color.rgb(255,130,0);
        }
        else if (X == 6 && Y == 6) {
            playerColor = Color.GREEN;
        }
        treasures = initTreasures;
        currentTreasure = 0;
        playerID = initID;
    }

    public PlayerData(PlayerData toCopy) {
        this.Xposition = toCopy.Xposition;
        this.Yposition = toCopy.Yposition;

        this.treasures = new int[LabyrinthGameState.MAX_NUM_CARDS];
        for(int i = 0; i < LabyrinthGameState.MAX_NUM_CARDS; i++) {
            this.treasures[i] = toCopy.treasures[i];
        }

        Ystart = toCopy.Ystart;
        Xstart = toCopy.Xstart;
        if (Xstart == 0 && Ystart == 0) {
            playerColor = Color.RED;
        }
        else if (Xstart == 6 && Ystart == 0) {
            playerColor = Color.BLUE;
        }
        else if (Xstart == 0 && Ystart == 6) {
            playerColor = Color.rgb(255,130,0);
        }
        else if (Xstart == 6 && Ystart == 6) {
            playerColor = Color.GREEN;
        }

        this.playerID = toCopy.playerID;
        this.currentTreasure = toCopy.currentTreasure;
    }

    /**
     * getCurentTreasure
     * @return
     */
    public int getCurrentTreasure() {
        if(currentTreasure < LabyrinthGameState.MAX_NUM_CARDS) {
            return treasures[currentTreasure];
        }
        else { return 0; }
    }


    public int getRemainingTreasures() {
        return LabyrinthGameState.MAX_NUM_CARDS - currentTreasure;
    }


    /**
     * movePlayer
     *
     * @param initX - The X position to move the player to
     * @param initY - The X position to move the player to
     */
    public void movePlayer(int initX, int initY) {
        Xposition = initX;
        Yposition = initY;
    }


    /**
     * getTreasures
     *
     * @return returns the array of all treasures for this player
     */
    public int[] getTreasures() {
        return treasures;
    }


    /**
     * takeTreasure
     *
     * @param takenTreasure - the treasure to be captured
     * @return whether or not it took the treasure
     */
    public boolean takeTreasure(int takenTreasure) {
        try {
            if (currentTreasure >= LabyrinthGameState.MAX_NUM_CARDS) {
                return false;
            }
            if (treasures[currentTreasure] == takenTreasure) {
                currentTreasure++;
                return true;
            }
            return false;
        } catch(ArrayIndexOutOfBoundsException aioobe) {
            return false;
        }
    }

    public int getXposition() {
        return Xposition;
    }

    public int getYposition() {
        return Yposition;
    }

    public boolean hasWon() {
        if (currentTreasure >= LabyrinthGameState.MAX_NUM_CARDS && Xposition == Xstart && Yposition == Ystart) {
            return true;
        }
        return false;
    }

    public int getPlayerColor() {return playerColor;}

    public int getPlayerID() {return playerID;}

}
