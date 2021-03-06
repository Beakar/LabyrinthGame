package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.GamePlayer;
import group2.cs301.labyrinthgame.Game.actionMsg.GameAction;

/**
 * @author G. Emily Nitzberg, Brendan Thomas, Ben Rumptz, Andrew Williams
 * @version November 9, 2015
 */
public class InsertTileAction extends GameAction {

    private int x;
    private int y;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public InsertTileAction(GamePlayer player, int xx, int yy) {
        super(player);
        x = xx;
        y = yy;
    }//ctor

    public int getX(){ return x; }
    public int getY(){ return y; }
}
