package gamecenter.gamecenter.SlidingGameTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import gamecenter.gamecenter.*;
import gamecenter.gamecenter.SlidingGame.Board;
import gamecenter.gamecenter.SlidingGame.BoardManager;
import gamecenter.gamecenter.SlidingGame.MovementController;
import gamecenter.gamecenter.SlidingGame.SlidingScoreBoard;
import gamecenter.gamecenter.SlidingGame.Tile;

import org.junit.Rule;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.MockitoJUnit;


import static org.junit.Assert.*;

public class BoardAndTileTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private SlidingScoreBoard slidingScoreBoard3 = new SlidingScoreBoard(3, "A");
    private SlidingScoreBoard slidingScoreBoard4 = new SlidingScoreBoard(4 ,"A");
    private SlidingScoreBoard slidingScoreBoard5 = new SlidingScoreBoard(5, "A");
    private BoardManager boardManager3 = new BoardManager(3,3);
    private BoardManager boardManager4 = new BoardManager(4,3);
    private BoardManager boardManager5 = new BoardManager(5,3);
    private Board board3;
    private Board board4;
    private Board board5;
    private Tile testTile;
    private MovementController movementController;

    // =====  setUp  =====
    private List<Tile> makeTiles(int numTiles) {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        return tiles;
    }

    @Before
    public void setUpTile(){
        testTile = new Tile(1);
    }

    // =====  tests for Tile =====
    @Test
    public void getIdTest() {
        assertEquals(1, testTile.getId());
    }

    @Test
    public void testSetId() {
        testTile.setId(2);
        assertEquals(2, testTile.getId());
    }

    @Test
    public void testTilecompareTo() {
        Tile testTile1 = new Tile(3);
        assertTrue(testTile.compareTo(testTile1) > 0);
    }


    // =====  tests for board  =====
    @Before
    public void setUpBoard(){
        board3 = new Board(makeTiles(3*3), boardManager3, 3);
        board4 = new Board(makeTiles(4*4), boardManager4, 4);
        board5 = new Board(makeTiles(5*5), boardManager5, 5);
        boardManager3.setBoard(board3);
        boardManager4.setBoard(board4);
        boardManager5.setBoard(board5);
        boardManager3.setScoreBoard(slidingScoreBoard3);
        boardManager4.setScoreBoard(slidingScoreBoard4);
        boardManager5.setScoreBoard(slidingScoreBoard5);
        movementController = new MovementController();
    }

    @Test
    public void getSlidingScoreBoardTest() {
        assertEquals(slidingScoreBoard3, boardManager3.getScoreBoard());
        assertEquals(slidingScoreBoard4, boardManager4.getScoreBoard());
        assertEquals(slidingScoreBoard5, boardManager5.getScoreBoard());
    }

    @Test
    public void iteratorTest() {
        int i = 0;
        int j = 0;
        int k = 0;
        Iterator<Tile> iter3 = board3.iterator();
        while(iter3.hasNext()){
            assertEquals(i, iter3.next().getId());
            i++;
        }
        Iterator<Tile> iter4 = board4.iterator();
        while(iter4.hasNext()){
            assertEquals(j, iter4.next().getId());
            j++;
        }
        Iterator<Tile> iter5 = board5.iterator();
        while(iter5.hasNext()){
            assertEquals(k, iter5.next().getId());
            k++;
        }
    }

    @Test
    public void getTileTest() {
        assertEquals(0, board3.getTile(0,0).getId());
        assertEquals(1, board3.getTile(0,1).getId());
        assertEquals(14, board4.getTile(3,2).getId());
        assertEquals(15, board4.getTile(3,3).getId());
        assertEquals(23, board5.getTile(4,3).getId());
        assertEquals(24, board5.getTile(4,4).getId());
    }

    @Test
    public void getLevelTest() {
        assertEquals(3,board3.getLevel());
        assertEquals(4,board4.getLevel());
        assertEquals(5,board5.getLevel());
    }

    @Test
    public void swapTilesTest() {
        board4.swapTiles(0,0,0,1);
        assertEquals(1, board4.getTile(0,0).getId());
        assertEquals(0, board4.getTile(0,1).getId());
    }

    @Test
    public void undoSwap() {
        board4.swapTiles(0,0,0,1);
        board4.undoSwap();
        assertEquals(0,board4.getTile(0,0).getId());
        assertEquals(1,board4.getTile(0,1).getId());
    }


    // ===== tests for board manager =====
    @Test
    public void getBoardTest() {
        assertEquals(board4, boardManager4.getBoard());
    }

    @Test
    public void getAndSetPicNameTest() {
        boardManager4.setPicName("oo");
        assertEquals("oo", boardManager4.getPicName());
    }

    @Test
    public void setAndGetCurrentUserTest() {
        User user = new User("A", "123");
        boardManager4.setCurrentUser(user);
        assertEquals(user, boardManager4.getCurrentUser());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTapTest() {
        assertTrue(boardManager3.isValidTap(7));
        assertTrue(boardManager3.isValidTap(5));
        assertFalse(boardManager3.isValidTap(0));
    }

        /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwoTest() {
        int id1 = boardManager4.getBoard().getTile(3,2).getId();
        int id2 = boardManager4.getBoard().getTile(3,3).getId();
        boardManager4.getBoard().swapTiles(3,3,3,2);
        assertEquals(id1, boardManager4.getBoard().getTile(3,3).getId());
        assertEquals(id2,boardManager4.getBoard().getTile(3,2).getId());
    }

        /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwoTest() {
        int id1 = boardManager4.getBoard().getTile(0,0).getId();
        int id2 = boardManager4.getBoard().getTile(0,1).getId();
        boardManager4.getBoard().swapTiles(0,0,0,1);
        assertEquals(id1, boardManager4.getBoard().getTile(0,1).getId());
        assertEquals(id2,boardManager4.getBoard().getTile(0,0).getId());
    }

        /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolvedTest() {
        assertTrue(boardManager4.puzzleSolved());
        boardManager4.getBoard().swapTiles(0,0,0,1);
        assertFalse(boardManager4.puzzleSolved());
    }

    // =====  tests for SlidingScoreBoard  =====
    @Test
    public void calculateScore() {
        board3.swapTiles(0,0,1,1);
        boardManager3.getScoreBoard().setTime(2);
        boardManager3.getScoreBoard().calculateScore();
        assertEquals(496, boardManager3.getScoreBoard().getScore());
    }

    @Test
    public void compareToTest() {
        SlidingScoreBoard s = new SlidingScoreBoard(3, "A");
        s.setStep(3);
        assertEquals(0,boardManager3.getScoreBoard().compareTo(s));
    }

    @Test
    public void getStepTest() {
        assertEquals(0,boardManager3.getScoreBoard().getStep());
    }

    @Test
    public void setStepTest() {
        assertEquals(0,boardManager3.getScoreBoard().getStep());
        boardManager3.getScoreBoard().setStep(2);
        assertEquals(2,boardManager3.getScoreBoard().getStep());
    }

    @Test
    public void updateStepTest() {
        assertEquals(0,boardManager3.getScoreBoard().getStep());
        board3.swapTiles(0,0,1,1);
        assertEquals(1,boardManager3.getScoreBoard().getStep());
    }

    @Test
    public void toStringTest(){
        String s = "                    Game Level:    " +  boardManager3.getScoreBoard().getLevel() + "\n"
                + "                    Total score:         " + 500 + "\n"
                + "                              " + "\n"
                + "                    Total Step:         " + 0 + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + 0 + "\n";
        assertEquals(boardManager3.getScoreBoard().toString() , boardManager3.getScoreBoard().toString());
    }

    @Test
    public void noRecordStepTest(){
        assertNull(boardManager3.getLastStep());
        assertNull(boardManager4.getLastStep());
        assertNull(boardManager5.getLastStep());
    }


    @Test
    public void oneRecordStepTest() {
        board3.swapTiles(0,0,0,1);
        board4.swapTiles(3,3,2,3);
        board5.swapTiles(2,1,3,1);
        assertEquals(1, boardManager3.getRecordedSteps().size());
        assertEquals(1, boardManager4.getRecordedSteps().size());
        assertEquals(1, boardManager5.getRecordedSteps().size());
    }

    @Test
    public void fullRecordStepTest() {
        assertNull(boardManager3.getLastStep());
        board3.swapTiles(0,0,0,1);
        assertEquals(1,boardManager3.getRecordedSteps().size());
        board3.swapTiles(1,1,2,1);
        assertEquals(2,boardManager3.getRecordedSteps().size());
        board3.swapTiles(2,1,2,2);
        assertEquals(3,boardManager3.getRecordedSteps().size());
        board3.swapTiles(1,0,2,0);
        assertEquals(3,boardManager3.getRecordedSteps().size());
        board3.swapTiles(1,1,2,1);
        assertEquals(3,boardManager3.getRecordedSteps().size());
    }


    @Test
    public void getLastStepTest(){
        assertNull(boardManager3.getLastStep());
        assertNull(boardManager4.getLastStep());
        assertNull(boardManager5.getLastStep());
    }


    // tests for MovementController
    @Test
    public void setBoardManagerTest() {
        movementController.setBoardManager(boardManager3);
        assertEquals(boardManager3, movementController.getBoardManager());
    }

    @Test
    public void processTapMovementAboveAndBelowTest() {
        movementController.setBoardManager(boardManager3);
        boardManager3.setBoard(board3);
        movementController.processTapMovement( 0);
        assertEquals(0, board3.getTile(0,0).getId());
        movementController.processTapMovement(5);
        assertEquals(8, board3.getTile(1, 2).getId());
        movementController.processTapMovement(8);
        assertEquals(8, board3.getTile(2,2).getId());
    }

    @Test
    public void processTapMovementLeftAndRightTest() {
        movementController.setBoardManager(boardManager3);
        boardManager3.setBoard(board3);
        movementController.processTapMovement( 7);
        assertEquals(8, board3.getTile(2, 1).getId());
        movementController.processTapMovement(8);
        assertEquals(8, board3.getTile(2,2).getId());

    }


}