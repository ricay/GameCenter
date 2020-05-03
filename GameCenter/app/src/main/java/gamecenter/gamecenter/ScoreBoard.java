package gamecenter.gamecenter;

import java.io.Serializable;

public abstract class ScoreBoard implements Serializable, Comparable<ScoreBoard>   {
    /**
     * The time.
     */
    private int time;
    /**
     * The score.
     */
    private int score;
    /**
     * The level.
     */
    private int level;

    private String userName;

    /**
     * construct a ScoreBoard.
     * @param level the level
     */
    public ScoreBoard(int level, String userName){
        this.userName = userName;
        this.level = level;
    }

    /**
     * get the level.
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     *  set the level
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * get the name
     * @return the name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * get the score.
     * @return the score
     */
    public int getScore(){return score;}

    /**
     *  set the score
     * @param score the score
     */
    public void setScore(int score){this.score = score;}

    /**
     * get the time
     * @return the time
     */
    public int getTime() { return time; }

    /**
     *  set the time
     * @param time the time
     */
    public void setTime(int time) { this.time = time; }

    public abstract void calculateScore();

    /**
     * return positive if greater , negative if less, 0 if equal
     * @param o the other ScoreBoard
     * @return the integer, positive if greater , negative if less, 0 if equal
     */
    public int compareTo(ScoreBoard o) {
        return this.getScore() - o.getScore();
    }

    /**
     *  minus a given score
     * @param minus minus from score
     */
    public void minusScore(int minus){score -= minus;}

    public String getType(){return "";}

}
