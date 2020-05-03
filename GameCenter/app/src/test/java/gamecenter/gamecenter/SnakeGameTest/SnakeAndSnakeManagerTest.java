package gamecenter.gamecenter.SnakeGameTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gamecenter.gamecenter.SnakeGame.Control;
import gamecenter.gamecenter.SnakeGame.Grid;
import gamecenter.gamecenter.SnakeGame.Point;
import gamecenter.gamecenter.SnakeGame.Snake;
import gamecenter.gamecenter.SnakeGame.SnakeGameController;
import gamecenter.gamecenter.SnakeGame.SnakeManager;
import gamecenter.gamecenter.SnakeGame.SnakeScoreBoard;

import static org.junit.Assert.*;

public class SnakeAndSnakeManagerTest {

    private SnakeManager snakeManager;
    private Snake snake;
    private Grid grid;
    private SnakeGameController snakeGameController;
    private SnakeScoreBoard snakeScoreBoard = new SnakeScoreBoard(3, "A");

    @Before
    public void setUp(){
        snakeManager = new SnakeManager(3, "A", 3);
        snake = snakeManager.getSnake();
        grid = Grid.getGrid();
        snakeGameController = new SnakeGameController();
        snakeGameController.setSnakeManager(snakeManager);
        snakeManager.setScoreBoard(snakeScoreBoard);
    }

    @Test
    public void getSnakeBodyTest() {
        assertEquals(1, snake.getSnakeBody().size());
    }

    @Test
    public void coordinateOfSnakeTest(){
        Point head = snake.getSnakeBody().getFirst();
        head.setX(0);
        head.setY(0);
        assertEquals(0, head.getX());
        assertEquals(0, head.getY());
    }

    @Test
    public void addBody() {
        Point body = new Point(0,0);
        snake.addBody(body);
        assertEquals(2,snake.getSnakeBody().size());
    }

    @Test
    public void getCurrentDirection() {
        Assert.assertEquals(Control.UP, snake.getCurrentDirection());
    }

    @Test
    public void setCurrentDirection() {
        snake.setCurrentDirection(Control.DOWN);
        assertEquals(Control.DOWN, snake.getCurrentDirection());
    }

    @Test
    public void getSnakeScoreBoardTest() {
        assertEquals(0, snakeManager.getScoreBoard().getScore());
    }

    // ===== tests for SnakeManager =====

    @Test
    public void getSpeedLevelTest() {
        assertEquals(3, snakeManager.getScoreBoard().getLevel());
    }

    @Test
    public void setAndGetSnake() {
        Snake s = new Snake();
        snakeManager.setSnake(s);
        assertEquals(s, snakeManager.getSnake());
    }

    @Test
    public void isFailedGoUpTest(){
        assertFalse(snakeManager.checkIsFailed());
        int i = 25 - Math.round(Grid.getGrid().getGridNumPerCol() / 2) -1;
        while(i >0) {
            snakeManager.checkGrowSnake(true);
            i--;
        }
        assertTrue(snakeManager.checkIsFailed());
    }

    @Test
    public void isFailedGoDownTest(){
        assertFalse(snakeManager.checkIsFailed());
        snakeManager.getSnake().setCurrentDirection(Control.DOWN);
        int i = 24 - Math.round(Grid.getGrid().getGridNumPerCol() / 2)-1;
        while(i >0) {
            snakeManager.checkGrowSnake(true);
            i--;
        }
        assertTrue(snakeManager.checkIsFailed());
    }

    @Test
    public void isFailedGoLeftTest(){
        assertFalse(snakeManager.checkIsFailed());
        snakeManager.getSnake().setCurrentDirection(Control.LEFT);
        int i = 17 - Math.round(Grid.getGrid().getGridNumPerRow() / 2)-1;
        while(i >0) {
            snakeManager.checkGrowSnake(true);
            i--;
        }
        System.out.println(snakeManager.getSnake().getSnakeBody().get(0).getX());
        assertTrue(snakeManager.checkIsFailed());
    }

    @Test
    public void isFailedGoRightTest(){
        assertFalse(snakeManager.checkIsFailed());
        snakeManager.getSnake().setCurrentDirection(Control.RIGHT);
        int i = 17 - Math.round(Grid.getGrid().getGridNumPerRow() / 2)-1;
        while(i >0) {
            snakeManager.checkGrowSnake(false);
            i--;
        }
        assertTrue(snakeManager.checkIsFailed());
    }

    @Test
    public void recordStep() {
        assertNull(snakeManager.getLastStep());
        Snake s1 = new Snake();
        Snake s2 = new Snake();
        Snake s3 = new Snake();
        Snake s4 = new Snake();
        snakeManager.recordStep(s1);
        snakeManager.recordStep(s2);
        snakeManager.recordStep(s3);
        snakeManager.recordStep(s4);
        snakeManager.recordStep(s1);
        assertEquals(0, snakeManager.getLastStep().getSnakeBody().size());
    }

    @Test
    public void speedLevelTest(){
        assertEquals(3, snakeManager.getSpeedLevel());
    }

    @Test
    public void toStringTest(){
        String s = "                    Game Level:    " + snakeManager.getScoreBoard().getLevel()  + "\n"
                + "                    Total score:         " + 0 + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + 0 + "\n";
        assertEquals(snakeManager.getScoreBoard().toString(), snakeManager.getScoreBoard().toString());
    }


    // ====== tests for SnakeGameController ======

    @Test
    public void setSnakeManagerTest(){
        assertEquals(snakeManager, snakeGameController.getSnakeManager());
    }

    @Test
    public void processSnakeMovementTest(){
        snakeGameController.processSnakeMovement(null, false, false);
        assertEquals(Control.UP, snake.getCurrentDirection());
        snakeGameController.processSnakeMovement(Control.LEFT, false, false);
        assertEquals(Control.LEFT, snake.getCurrentDirection());
        snakeManager.recordStep(snake);
        snakeManager.recordStep(snake);
        snakeManager.recordStep(snake);
        snakeManager.recordStep(snake);
        snakeGameController.processSnakeMovement(null, false, true);
        snakeGameController.processSnakeMovement(null, true, false);
        assertEquals(0, snakeManager.getRecordedSteps().size());
    }

    @Test
    public void clickPositionProcessorTurnRightAndDownTest(){
        int[] originXY = new int[2];
        originXY[0] = 0;
        originXY[1] = 0;
        int[] stopXY = new int[2];
        stopXY[0] = 10;
        stopXY[1] = 0;
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        assertEquals(Control.RIGHT, snakeManager.getSnake().getCurrentDirection());
        originXY[0] = 0;
        originXY[1] = 0;
        stopXY[0] = 0;
        stopXY[1] = 10;
        snakeGameController.clickPositionProcessor(originXY,stopXY);
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        assertEquals(Control.DOWN, snakeManager.getSnake().getCurrentDirection());
    }

    @Test
    public void clickPositionProcessorTurnLeftAndUpTest(){
        int[] originXY = new int[2];
        originXY[0] = 10;
        originXY[1] = 10;
        int[] stopXY = new int[2];
        stopXY[0] = 0;
        stopXY[1] = 10;
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        assertEquals(Control.LEFT, snakeManager.getSnake().getCurrentDirection());
        originXY[0] = 10;
        originXY[1] = 10;
        stopXY[0] = 10;
        stopXY[1] = 0;
        snakeGameController.clickPositionProcessor(originXY,stopXY);
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        assertEquals(Control.UP, snakeManager.getSnake().getCurrentDirection());
    }

    @Test
    public void clickPositionProcessorSaveTest(){
        int[] originXY = new int[2];
        int[] stopXY = new int[2];
        originXY[0] = 0;
        originXY[1] = 1400;
        stopXY[0] = 0;
        stopXY[1] = 1400;
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        assertEquals(Control.UP, snakeManager.getSnake().getCurrentDirection());
        originXY[0] = 0;
        originXY[1] = 1550;
        stopXY[0] = 0;
        stopXY[1] = 1550;
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        assertEquals(Control.UP, snakeManager.getSnake().getCurrentDirection());
        originXY[0] = 600;
        originXY[1] = 1550;
        stopXY[0] = 600;
        stopXY[1] = 1550;
        snakeGameController.clickPositionProcessor(originXY, stopXY);
        assertEquals(Control.UP, snakeManager.getSnake().getCurrentDirection());
    }


    // ====== GridBeanTest =======

    @Test
    public void getGridNumPerRowAndCol() {
        assertEquals(17, grid.getGridNumPerRow());
        assertEquals(24, grid.getGridNumPerCol());
    }

    @Test
    public void getHeightAndWidthLineLength() {
        assertEquals(1540, grid.getHeightLineLength());
        assertEquals(1070, grid.getWidthLineLength());
    }

    @Test
    public void getBeanWidth() {
        assertEquals(1540/24, grid.getBeanWidth());
    }

}