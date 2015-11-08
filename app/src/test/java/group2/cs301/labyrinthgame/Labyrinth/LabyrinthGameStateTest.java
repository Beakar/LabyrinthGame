package group2.cs301.labyrinthgame.Labyrinth;

import junit.framework.Assert.*;
import junit.framework.TestCase;

/**
 * Created by Andrew Williams on 11/7/15.
 */
public class LabyrinthGameStateTest extends TestCase {

    //test insert into (0,1)
    public void testInsertTile() throws Exception {
        LabyrinthGameState gs = new LabyrinthGameState(4);
        //x and y should be 0 here
        int lastXBeforeInsert = gs.getLastXInserted();
        int lastYBeforeInsert = gs.getLastYInserted();
        //test that our lastX,lastY are correct
        assertEquals(0, lastXBeforeInsert);
        assertEquals(0, lastYBeforeInsert);
        Tile startExtraTile = gs.getGameBoard().getExtraTile();
        Tile initFirstTile = gs.getGameBoard().getTile(0, 1);
        Tile initSecondTile = gs.getGameBoard().getTile(1, 1);
        Tile initThirdTile = gs.getGameBoard().getTile(2, 1);
        Tile initFourthTile = gs.getGameBoard().getTile(3, 1);
        Tile initFifthTile = gs.getGameBoard().getTile(4, 1);
        Tile initSixthTile = gs.getGameBoard().getTile(5, 1);
        Tile initSeventhTile = gs.getGameBoard().getTile(6, 1);
        //call insert here
        gs.insertTile(0,1);
        //check that it worked below here
        int lastXAfterInsert = gs.getLastXInserted();
        int lastYAfterInsert = gs.getLastYInserted();
        //test that our lastX,lastY are correct
        assertEquals(0, lastXAfterInsert);
        assertEquals(1, lastYAfterInsert);
        Tile finalExtraTile = gs.getGameBoard().getExtraTile();
        Tile finalFirstTile = gs.getGameBoard().getTile(0, 1);
        Tile finalSecondTile = gs.getGameBoard().getTile(1, 1);
        Tile finalThirdTile = gs.getGameBoard().getTile(2, 1);
        Tile finalFourthTile = gs.getGameBoard().getTile(3, 1);
        Tile finalFifthTile = gs.getGameBoard().getTile(4, 1);
        Tile finalSixthTile = gs.getGameBoard().getTile(5, 1);
        Tile finalSeventhTile = gs.getGameBoard().getTile(6, 1);
        //test that our tiles shifted correctly
        assertEquals(startExtraTile, finalFirstTile);
        assertEquals(initFirstTile, finalSecondTile);
        assertEquals(initSecondTile, finalThirdTile);
        assertEquals(initThirdTile, finalFourthTile);
        assertEquals(initFourthTile, finalFifthTile);
        assertEquals(initFifthTile, finalSixthTile);
        assertEquals(initSixthTile, finalSeventhTile);
        assertEquals(initSeventhTile, finalExtraTile);
    }

    public void testRotateTile() throws Exception {

        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(4);
        Tile origTile = labyrinthGameState.getGameBoard().getExtraTile();

        //assertEquals(origTile.tickTile(), labyrinthGameState.rotateTile());
    }

    public void testMove() throws Exception {

    }
}