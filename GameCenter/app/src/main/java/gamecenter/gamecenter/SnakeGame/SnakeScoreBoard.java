package gamecenter.gamecenter.SnakeGame;

import android.support.annotation.NonNull;

import java.io.Serializable;

import gamecenter.gamecenter.ScoreBoard;

public class SnakeScoreBoard extends ScoreBoard implements Serializable {

    /**
     * construct a SnakeScoreBoard.
     * @param userName the userName
     * @param level the level
     */
    public SnakeScoreBoard(int level, String userName){
        super(level, userName);
    }

    @Override
    public int getScore(){
        calculateScore();
        return super.getScore();
    }

    @Override
    public void calculateScore() {
        super.setScore(super.getTime()*super.getLevel());
    }

    @NonNull
    @Override
    public String toString(){
        String scoreBoardInfo = "";
        scoreBoardInfo += "                    User: " + getUserName() + "\n"
                + "                    Game Level:    " +  getLevel() + "\n"
                + "                    Total score:         " + this.getScore() + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + this.getTime() + "\n";
        return scoreBoardInfo;
    }


}
