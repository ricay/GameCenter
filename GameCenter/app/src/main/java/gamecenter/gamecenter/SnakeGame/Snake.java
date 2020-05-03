package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;
import java.util.LinkedList;

public class Snake implements Serializable {

    /**
     * The snakeBody list.
     */
    private LinkedList<Point> snakeBody = new LinkedList<>();
    /**
     * The direction.
     */
    private Control currentDirection = Control.UP;

    /**
     * get the snakeBody.
     * @return the snakeBody
     */
    public LinkedList<Point> getSnakeBody(){return snakeBody;}

    /**
     *  add body to snake.
     * @param body the new body
     */
    public void addBody(Point body){
        snakeBody.add(body);
    }

    /**
     *  add body to snake.
     * @param isAdd if the body grow
     * @return if the snake eat itself after grow
     */
    //true if grow success, false if failed
    public boolean grow(boolean isAdd){
        //the head of snake
        Point head = snakeBody.get(0);
        Point pointNew = null;

        //change direction of snake head
        if (currentDirection == Control.LEFT) {
            pointNew = new Point(head.getX() - 1, head.getY());
        } else if (currentDirection == Control.RIGHT) {
            pointNew = new Point(head.getX() + 1, head.getY());
        } else if (currentDirection == Control.UP) {
            pointNew = new Point(head.getX(), head.getY() - 1);
        } else if (currentDirection == Control.DOWN) {
            pointNew = new Point(head.getX(), head.getY() + 1);
        }

        //snake body grow or not
        if (pointNew != null) {
            if(!checkEatSelf(pointNew)) {
                snakeBody.add(0, pointNew);
                if (!isAdd) {
                    snakeBody.remove(snakeBody.get(snakeBody.size() - 1));
                }
                return false;
            }
        }
        // failed, snake eats itself
        return true;
    }

    /**
     *  check if snake eats itself
     * @param newBody the new body
     * @return if the snake eat itself
     */
    private boolean checkEatSelf(Point newBody){
        for(Point body: snakeBody){
            if(body.getX() == newBody.getX() && body.getY() == newBody.getY()){
                return true;
            }
        }
        return false;
    }

    /**
     *  get snake direction
     * @return the snake direction
     */
    public Control getCurrentDirection() {
        return this.currentDirection;
    }

    /**
     *  set the direction
     * @param currentDirection the direction
     */
    public void setCurrentDirection(Control currentDirection) {
        this.currentDirection = currentDirection;
    }

}
