package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.GamePlayer;
import group2.cs301.labyrinthgame.Game.actionMsg.GameAction;

/**
 * Created by Andrew Williams on 11/5/15.
 */
public class MoveAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public MoveAction(GamePlayer player) {
        super(player);
    }
}
