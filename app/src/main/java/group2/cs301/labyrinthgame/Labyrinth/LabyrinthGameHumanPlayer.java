package group2.cs301.labyrinthgame.Labyrinth;

import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import group2.cs301.labyrinthgame.Game.GameHumanPlayer;
import group2.cs301.labyrinthgame.Game.GameMainActivity;
import group2.cs301.labyrinthgame.Game.infoMsg.GameInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.IllegalMoveInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.NotYourTurnInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.TimerInfo;
import group2.cs301.labyrinthgame.R;

/**
 * @author Created by Andrew Williams
 * @version 11/18/15.
 */
public class LabyrinthGameHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener {


    private GameMainActivity myActivity; // the current activity

    private LabyrinthGameState labyrinthGameState;

    private LabyrinthSurfaceView surfView;
    private ImageView extraTileBase;
    private ImageView extraTileTreasure;
    private ImageView extraTileHighlight;
    private ImageButton extraTileButton;
    private TextView playerTurnDisplay;
    private TextView targetCountDisplay;
    private ImageView targetDisplay;
    private Button nextTurnButton;
    private int curTreasure;

    private int[] treasuresResources;

    public LabyrinthGameHumanPlayer(String name) {
        super(name);

        treasuresResources = new int[25];
        treasuresResources[0] = R.drawable.tile_blank;
        treasuresResources[1] = R.drawable.treasure_alarm_bell;
        treasuresResources[2] = R.drawable.treasure_anchor;
        treasuresResources[3] = R.drawable.treasure_bear;
        treasuresResources[4] = R.drawable.treasure_beer;
        treasuresResources[5] = R.drawable.treasure_black_board;
        treasuresResources[6] = R.drawable.treasure_books;
        treasuresResources[7] = R.drawable.treasure_bow;
        treasuresResources[8] = R.drawable.treasure_briefcase;
        treasuresResources[9] = R.drawable.treasure_cake;
        treasuresResources[10] = R.drawable.treasure_candy;
        treasuresResources[11] = R.drawable.treasure_cash_register;
        treasuresResources[12] = R.drawable.treasure_clock;
        treasuresResources[13] = R.drawable.treasure_dna;
        treasuresResources[14] = R.drawable.treasure_dog;
        treasuresResources[15] = R.drawable.treasure_eagle;
        treasuresResources[16] = R.drawable.treasure_grapes;
        treasuresResources[17] = R.drawable.treasure_microscope;
        treasuresResources[18] = R.drawable.treasure_piggy_bank;
        treasuresResources[19] = R.drawable.treasure_pile_of_gold;
        treasuresResources[20] = R.drawable.treasure_pill;
        treasuresResources[21] = R.drawable.treasure_spaceship;
        treasuresResources[22] = R.drawable.treasure_squirrel;
        treasuresResources[23] = R.drawable.treasure_table_lamp;
        treasuresResources[24] = R.drawable.treasure_telescope;


    }//constructor

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if(info instanceof LabyrinthGameState) {
            labyrinthGameState = (LabyrinthGameState)info;
            updateGUI();
        }
    }

    private void updateGUI() {
        surfView.setBoardToDraw(labyrinthGameState.getGameBoard());
        surfView.setPlayerData(labyrinthGameState.getPlayers());

        String playerColor;

        if(labyrinthGameState.getCurrentPlayer() == 0)
        {
            playerColor = "red";

        }
        else if (labyrinthGameState.getCurrentPlayer() == 1)
        {
            playerColor = "blue";
        }
        else if(labyrinthGameState.getCurrentPlayer() == 2)
        {
            playerColor = "orange";
        }
        else
        {
            playerColor = "green";
        }

        playerTurnDisplay.setText("It is the " + playerColor + " player's turn.");

        curTreasure = labyrinthGameState.getPlayers().get(labyrinthGameState.getCurrentPlayer()).getCurrentTreasure();



        targetDisplay.setImageResource(treasuresResources[curTreasure]);

        int remainingTreasures = labyrinthGameState.getCurrentPlayerData().getRemainingTreasures();

        targetCountDisplay.setText("You have " + remainingTreasures + " to collect.");

    }


    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        surfView = (LabyrinthSurfaceView) myActivity.findViewById(R.id.GameBoard);
        extraTileBase = (ImageView) myActivity.findViewById(R.id.extra_tile_base);
        extraTileHighlight = (ImageView) myActivity.findViewById(R.id.extra_tile_highlight);
        extraTileTreasure = (ImageView) myActivity.findViewById(R.id.extra_tile_treasure);
        extraTileButton = (ImageButton) myActivity.findViewById(R.id.extra_tile_button);
        playerTurnDisplay = (TextView) myActivity.findViewById(R.id.turnView);
        targetCountDisplay = (TextView) myActivity.findViewById(R.id.targetCountView);
        targetDisplay = (ImageView) myActivity.findViewById(R.id.targetView);
        nextTurnButton = (Button) myActivity.findViewById(R.id.NextTurnButton);

        nextTurnButton.setOnClickListener(this);
        extraTileButton.setOnClickListener(this);
        surfView.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == nextTurnButton) {
            game.sendAction(new NextTurnAction(this));
        }
        else if(v == extraTileButton) {
            game.sendAction(new RotateTileAction(this));
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(v == surfView) {

            int height = v.getHeight();
            int width = v.getWidth();

            int touchY = (int)(( ((double)event.getY())/((double)height) ) * 9 ) ;
            int touchX = (int)(( ((double)event.getX())/((double)width) ) * 9 ) ;

            Log.println(Log.VERBOSE, "", ""+ touchY + ", " + touchX);

            if(touchY == 0 || touchY == 8 || touchX == 0 || touchX == 8) {
                return false;
            }

            touchY--;
            touchX--;
            //0-6

            if(labyrinthGameState.getStage() == LabyrinthGameState.INSERTING) {
                if(labyrinthGameState.getGameBoard().getTile(touchX, touchY).isHighlighted()) {
                    game.sendAction(new InsertTileAction(this, touchX, touchY));
                }
            }
            else if(labyrinthGameState.getStage() == LabyrinthGameState.MOVING) {
                if(labyrinthGameState.getGameBoard().getTile(touchX, touchY).isHighlighted()) {
                    game.sendAction(new MoveAction(this, touchX, touchY));
                }
            }
            else {
                return false;
            }

            return true;
        }

        return false;
    }
}















