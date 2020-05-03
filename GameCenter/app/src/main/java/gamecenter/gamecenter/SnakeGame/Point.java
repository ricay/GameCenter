package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;

public class Point implements Serializable {
    /**
     * The x.
     */
        private int x;
    /**
     * The y.
     */
        private int y;

    /**
     * construct a Point.
     * @param x the x
     * @param y the y
     */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

    /**
     * get the x.
     * @return the x
     */
        public int getX() {
            return x;
        }

    /**
     *  set the x.
     * @param x the x
     */
        public void setX(int x) {
            this.x = x;
        }

    /**
     * get the y.
     * @return the y
     */
    public int getY() {
            return y;
        }

    /**
     *  set the y.
     * @param y the y
     */
        public void setY(int y) {
            this.y = y;
        }
}
