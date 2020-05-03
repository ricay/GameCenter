package gamecenter.gamecenter.MoleGame;

import java.util.Observable;

public class MoleController extends Observable {

    private MoleManager moleManager = null;

    public void setMoleManager(MoleManager moleManager){this.moleManager = moleManager;}

    public MoleManager getMoleManager(){return moleManager;}

    public void processMoleHit(int battleId, int next) {
        //get score
        if (battleId == next) {
            moleManager.getScoreBoard().setHitNum(moleManager.getScoreBoard().getHitNum() + 1);
            setChanged();
            notifyObservers("isScore");
        } else {
            // didn't get score
            setChanged();
            notifyObservers("notScore");
        }
    }
}
