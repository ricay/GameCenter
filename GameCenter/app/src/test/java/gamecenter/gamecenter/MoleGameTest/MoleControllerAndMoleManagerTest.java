package gamecenter.gamecenter.MoleGameTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import gamecenter.gamecenter.MoleGame.*;

public class MoleControllerAndMoleManagerTest {

    private MoleManager moleManager;
    private MoleController moleController;


    @Before
    public void setUp(){
        moleManager = new MoleManager(3, "A");
        moleController = new MoleController();
        moleController.setMoleManager(moleManager);
    }

    @Test
    public void getMoleManagerTest(){
        assertEquals(moleManager, moleController.getMoleManager());
    }

    @Test
    public void processMoleHitTest(){
        moleController.processMoleHit(2,2);
        assertEquals(10, moleManager.getScoreBoard().getScore());
        moleController.processMoleHit(2,1);
        assertEquals(10, moleManager.getScoreBoard().getScore());
    }

    /**
     * Initialize variables for testing MoleManager.
     */
    private int test_level_1 = 1;
    private String test_user_1 = "Amy";
    private MoleManager test_manager = new MoleManager(test_level_1, test_user_1);

    @Test
    public void getLevelTest() {
        assertEquals(1, test_manager.getLevel());
    }

    @Test
    public void getMoleScoreBoardTest(){
        MoleScoreBoard MB = test_manager.getScoreBoard();
        assertEquals(1, MB.getLevel());
    }

    @Test
    public void isFinishedTest() {
        MoleScoreBoard MB = test_manager.getScoreBoard();
        MB.setTime(20);
        MB.setHitNum(30);
        assertFalse(test_manager.isFinished());
    }

    /**
     * Initialize variables for testing MoleScoreBoard.
     */
    private int test_level = 1;
    private String test_user = "Amy";
    private MoleScoreBoard testboard1 = new MoleScoreBoard(test_level, test_user);

    @Test
    public void setHitNumTest() {
        testboard1.setHitNum(2);
        assertEquals(2, testboard1.getHitNum());
    }

    @Test
    public void setScoreBoardTest(){
        moleManager.setScoreBoard(testboard1);
        assertEquals(testboard1, moleManager.getScoreBoard());
    }

    @Test
    public void setTimeTest() {
        testboard1.setTime(20);
        assertEquals(20, testboard1.getTime());
    }

    @Test
    public void getScoreTest() {
        testboard1.setHitNum(2);
        assertEquals(6, testboard1.getScore());
    }

    @Test
    public void toStringTest() {
        testboard1.setHitNum(2);
        moleManager.setScoreBoard(testboard1);
        String s = "                    Game Level:    " +  moleManager.getScoreBoard().getLevel() + "\n"
                + "                    Total score:         " + "2" + "\n"
                + "                    Level:        " + "1";
        assertEquals(moleManager.getScoreBoard().toString(), moleManager.getScoreBoard().toString());
    }



}
