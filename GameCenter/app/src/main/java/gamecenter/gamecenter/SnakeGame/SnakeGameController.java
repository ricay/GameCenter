package gamecenter.gamecenter.SnakeGame;
import java.util.Observable;

// The controller Class for Snake Game , Use MVC design pattern
public class SnakeGameController extends Observable {

    /**
     * The snakeManager.
     */
    private SnakeManager snakeManager = null;

    /**
     *  set the snakeManager
     * @param snakeManager the snakeManager
     */
    public void setSnakeManager(SnakeManager snakeManager){this.snakeManager = snakeManager;}

    /**
     *  get SnakeManager
     * @return the SnakeManager
     */
    public SnakeManager getSnakeManager(){return snakeManager;}

    /**
     *  process the different condition (undo, save, change direction) for snake game
     * @param direction the snakeManager
     * @param save if save
     * @param undo if undo
     */
    public void processSnakeMovement( Control direction, boolean undo, boolean save){
        if(!snakeManager.checkIsFailed()) {
            if (direction == null) {
                if (undo) {
                    Snake newSnake = snakeManager.getLastStep();
                    if (newSnake != null) {
                        snakeManager.setSnake(newSnake);
                    }
                }
            } else {
                snakeManager.getSnake().setCurrentDirection(direction);
            }

            if(save){
                setChanged();
                notifyObservers("save");
            }
        }
    }

    /**
     *  process the click position
     * @param originXY the origin x, y
     * @param stopXY the click stop x, y
     */
    public void clickPositionProcessor(int[] originXY, int[] stopXY){
        Control newControl;
        int originX = originXY[0];
        int originY = originXY[1];
        int stopX = stopXY[0];
        int stopY = stopXY[1];
        if (Math.abs(stopX - originX) > Math.abs(stopY - originY)) {
            if (stopX > originX) {
                newControl = Control.RIGHT;
                //Change direction to right
                changeToRightOrLeft(newControl);
            }
            if (stopX < originX) {
                newControl = Control.LEFT;
                //Change direction to left
                changeToRightOrLeft(newControl);
            }
        } else if (Math.abs(stopX - originX) < Math.abs(stopY - originY)) {
            if (stopY < originY) {
                newControl = Control.UP;
                //Change direction to up
                changeToUpOrDown(newControl);
            }
            if (stopY > originY) {
                newControl = Control.DOWN;
                //Change direction to down
                changeToUpOrDown(newControl);
            }
        } else if (originY <= 1500){
            // nothing happen
            processSnakeMovement(null, false, false);
            // Undo game
        }else if ( originX <= 550){
            processSnakeMovement(null, true, false);
            // save game
        }else {
            processSnakeMovement(null, false,  true);
        }
    }

    /**
     *  check and change to new direction
     * @param newControl the new direction
     */
    private void changeToRightOrLeft(Control newControl){
        Control originControl = snakeManager.getSnake().getCurrentDirection();
        if(originControl==Control.UP|| originControl==Control.DOWN) {
            processSnakeMovement(newControl, false, false);
        }else{ processSnakeMovement(null, false, false);}
    }

    /**
     *  check and change to new direction
     * @param newControl the new direction
     */
    private void changeToUpOrDown(Control newControl){
        Control originControl = snakeManager.getSnake().getCurrentDirection();
        if(originControl==Control.LEFT|| originControl==Control.RIGHT) {
            processSnakeMovement(newControl, false, false);
        }else{ processSnakeMovement(null, false, false);}
    }

}
