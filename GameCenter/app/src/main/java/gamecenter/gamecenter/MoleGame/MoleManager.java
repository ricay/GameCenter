package gamecenter.gamecenter.MoleGame;

import java.io.Serializable;

import gamecenter.gamecenter.GameManager;

/**
 * Class for MoleManager which implements the GameManager.
 */

public class MoleManager implements GameManager ,Serializable {
    /**
     * Initialize the variables for methods
     */
    private int level;
    private MoleScoreBoard moleScoreBoard;

    /**
     * Initialize MoleManager.
     * @param level    level of this game
     * @param username username of current user
     */
    public MoleManager(int level, String username) {
        this.level = level;
        moleScoreBoard = new MoleScoreBoard(level,username);
    }

    /**
     * Return the level
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Return if game finished
     * @return true if finish
     */
    public boolean isFinished(){
        return getScoreBoard().getTime() == 0;
    }


    @Override
    public void setScoreBoard(Object scoreBoard) {
        moleScoreBoard = (MoleScoreBoard) scoreBoard;
    }

    @Override
    public MoleScoreBoard getScoreBoard() {
        return moleScoreBoard;
    }
}
