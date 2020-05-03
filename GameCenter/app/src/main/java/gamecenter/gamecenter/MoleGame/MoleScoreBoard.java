package gamecenter.gamecenter.MoleGame;

import android.support.annotation.NonNull;

import java.io.Serializable;
import gamecenter.gamecenter.ScoreBoard;

/**
 * Class for MoleScoreBoard which is the ScoreBoard for MoleGame.
 */

public class MoleScoreBoard extends ScoreBoard implements Serializable {

    /**
     * Initialize the variables for MoleScoreBoard such as number of hits and time.
     */
    private int hitNum;
    private int time = 30;

    /**
     * Return number of successful hits.
     * @return number of hits
     */
    public int getHitNum() {
        return hitNum;
    }

    /**
     * Set the Number of hits.
     * @param hitNum the Number of hits
     */
    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

    /**
     * Initialize the MoleScoreBoard.
     * @param level the level
     * @param userName the userName
     */
    public MoleScoreBoard(int level, String userName){
        super(level, userName);
    }

    /**
     * Return the remaining time.
     * @return time
     */
    public int getTime(){return time;}

    /**
     * Set the time.
     * @param time the remaining time.
     */
    public void setTime(int time){this.time=time;}

    /**
     * Calculate the score for MoleGame.
     */
    @Override
    public void calculateScore() {
        super.setScore(hitNum*super.getLevel()*100 / 30);
    }

    /**
     * Return the score of
     * @return score
     */
    @Override
    public int getScore() {
        calculateScore();
        return super.getScore();
    }

    /**
     * Override toString
     * @return string of this MoleScoreBoard.
     */
    @NonNull
    @Override
    public String toString(){
        String scoreBoardInfo = "";
        scoreBoardInfo += "                    User: " + getUserName() + "\n"
                + "                    Game Level:    " +  getLevel() + "\n"
                + "                    Total score:         " + this.getHitNum() + "\n"
                + "                    Level:        " + this.getLevel();
        return scoreBoardInfo;
    }

}
