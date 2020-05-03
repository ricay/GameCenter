package gamecenter.gamecenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class ScoreBoardManager extends Observable implements Serializable {

    /**
     * The score history list.
     */
    private ArrayList<ScoreBoard> scoreBoardHistory;

    /**
     * construct a ScoreBoardManager
     */
    ScoreBoardManager(){
        scoreBoardHistory = new ArrayList<>();
    }

    /**
     * add score into score history list
     * @param scoreBoard the score that need to be added
     */
    public void addScoreBoard(ScoreBoard scoreBoard) {
        // user's score history is full (limit is 5)
        if(scoreBoardHistory.size() == 5){
            Collections.sort(scoreBoardHistory);
            scoreBoardHistory.add(scoreBoard);
            Collections.sort(scoreBoardHistory);
            scoreBoardHistory.remove(0);
        } else { // not full
            scoreBoardHistory.add(scoreBoard);
            Collections.sort(scoreBoardHistory);
        }

    }

    /**
     * get the score history list
     * @return the score hostory list
     */
    public ArrayList<ScoreBoard> getScoreBoardHistory() {
        return scoreBoardHistory;
    }

    @NonNull
    @Override
    public String toString(){
        String s = "     =======  Scores Ranking  =======" + "\n";
        int i = scoreBoardHistory.size()-1;
        while (i>=0 && scoreBoardHistory.get(i) != null){
            s += "Score Ranking:    " + (scoreBoardHistory.size()-i) + "\n"
                    + "Level: " + scoreBoardHistory.get(i).getLevel() + "\n"
                    + "Total Score:  " + scoreBoardHistory.get(i).getScore() + "\n"
                    +"\n" + "\n";
            i--;
        }
        return s;
    }

}
