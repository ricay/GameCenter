package gamecenter.gamecenter.SlidingGame;

import android.support.annotation.NonNull;

import java.io.Serializable;
import gamecenter.gamecenter.ScoreBoard;


public class SlidingScoreBoard extends ScoreBoard implements Serializable  {
    /**
     * The step.
     */
    private int step;

    /**
     * The constructor of SlidingScoreBoard.
     * @param level the level
     */
    public SlidingScoreBoard(int level, String userName){
        super(level, userName);
        if(level == 3) {
            //the init score for level 3 is 500
            super.setScore(500);
        }else if(level == 4){
            //the init score for level 4 is 1000
            super.setScore(1000);
        }else if(level == 5){
            //the init score for level 5 is 2000
            super.setScore(2000);
        }
    }

    @Override
    public void calculateScore() {
        setScore(getScore() - step - getTime());
    }

    /**
     * get the step.
     * @return step
     */
    public int getStep() {
        return step;
    }

    /**
     * set the step.
     * @param step the step
     */
    public void setStep(int step){this.step = step;}

    /**
     * update the step by add 1
     */
    void updateStep() {
        this.step += 1;
    }

    @NonNull
    @Override
    public String toString(){
        String scoreBoardInfo = "";
        scoreBoardInfo += "                    User: " + getUserName() + "\n"
                + "                    Game Level:    " +  getLevel() + "\n"
                + "                    Total score:         " + this.getScore() + "\n"
                + "                              " + "\n"
                + "                    Total Step:         " + this.getStep() + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + this.getTime() + "\n";
        return scoreBoardInfo;
    }

}
