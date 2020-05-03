package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import gamecenter.gamecenter.*;

public class SnakeManager extends Observable implements UndoableGameManager, Serializable{
    /**
     * The snake.
     */
    private Snake snake = new Snake();
    /**
     * The speedLevel.
     */
    private int speedLevel;
    /**
     * The snakeScoreBoard.
     */
    private SnakeScoreBoard snakeScoreBoard;
    /**
     * The recordedSteps.
     */
    private ArrayList<Snake> recordedSteps = new ArrayList<>();
    /**
     * The recordLimit.
     */
    private int recordLimit;

    /**
     * construct a SnakeManager.
     * @param userName the userName
     * @param recordLimit the recordLimit
     */
    public SnakeManager(int level, String userName, int recordLimit) {
        this.speedLevel = level;
        snakeScoreBoard = new SnakeScoreBoard(speedLevel, userName);
        this.recordLimit = recordLimit*3;
        Point head = new Point(Math.round(Grid.getGrid().getGridNumPerRow() / 2), Math.round(Grid.getGrid().getGridNumPerCol() / 2));
        snake.getSnakeBody().add(head);
    }

    /**
     * get the SpeedLevel.
     * @return the SpeedLevel
     */
    public int getSpeedLevel() {
        return speedLevel;
    }

    /**
     * get the Snake.
     * @return the Snake
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     *  set the snake
     * @param snake the snake
     */
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    /**
     *  check if the snake touch the constraint of screen
     * @return if the snake touch the constraint of screen
     */
    public boolean checkIsFailed(){
        //yes, Game failed
        if (snake.getSnakeBody().get(0).getY() == 0 && snake.getCurrentDirection() == Control.UP ) {
            return true;
        } else if ( snake.getSnakeBody().get(0).getX()  == 0 && snake.getCurrentDirection() == Control.LEFT) {
            return true;
        } else if (snake.getSnakeBody().get(0).getY() == Grid.getGrid().getGridNumPerCol() - 1 && snake.getCurrentDirection() == Control.DOWN  ) {
            return true;
        } else if(snake.getSnakeBody().get(0).getX() == Grid.getGrid().getGridNumPerRow() - 1 && snake.getCurrentDirection() == Control.RIGHT){
            return true;
        }

        //no,  Game continue
            return  false;
    }

    /**
     *  check if the snake grow success or eat itself
     * @return if the snake grow success or eat itself
     */
    public boolean checkGrowSnake(boolean isAdd){
        return snake.grow(isAdd) || checkIsFailed();
    }

    /**
     * get the RecordedSteps.
     * @return the RecordedSteps
     */
    public ArrayList<Snake> getRecordedSteps(){return recordedSteps;}

    @Override
    public void recordStep(Object recordSnake) {
        recordedSteps.add((Snake) recordSnake);
        if(recordedSteps.size()==recordLimit+1){
            recordedSteps.remove(0);
        }
    }

    @Override
    public Snake getLastStep() {
        int i = 3;
        while (i > 0) {
            if (recordedSteps.size() != 0) {
                recordedSteps.remove(recordedSteps.size() - 1);
            }
            i--;
        }
        if (recordedSteps.size() != 0) {
            Snake snake = recordedSteps.get(recordedSteps.size() - 1);
            recordedSteps.remove(recordedSteps.size() - 1);
            return snake;
        }
        return null;
    }


    @Override
    public void setScoreBoard(Object scoreBoard) {
        snakeScoreBoard = (SnakeScoreBoard)scoreBoard;
    }

    @Override
    public SnakeScoreBoard getScoreBoard() {
        return snakeScoreBoard;
    }
}
