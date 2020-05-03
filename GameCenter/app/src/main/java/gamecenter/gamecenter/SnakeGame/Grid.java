package gamecenter.gamecenter.SnakeGame;
import java.io.Serializable;


// Singleton Class
public class Grid implements Serializable {

    /**
     * The grid.
     */
    private static final Grid grid = new Grid();
    /**
     * The gridNumPerCol.
     */
    private  int gridNumPerCol;
    /**
     * The gridNumPerRow.
     */
    private int gridNumPerRow;
    /**
     * The heightLineLength.
     */
    private int heightLineLength;
    /**
     * The widthLineLength.
     */
    private int widthLineLength;
    /**
     * The beanWidth.
     */
    private int beanWidth;//width of every bean

    /**
     * construct a Grid.
     */
    private Grid() {
             gridNumPerRow = 17;
            gridNumPerCol = 24;
            heightLineLength = 1540;
            widthLineLength = 1070;
            beanWidth = heightLineLength / gridNumPerCol;//num of bean
    }

    /**
     * get the grid.
     * @return the grid
     */
    public static Grid getGrid(){return grid;}

    /**
     * get the GridNumPerRow.
     * @return GridNumPerRow
     */
    public int getGridNumPerRow() {
        return gridNumPerRow;
        }

    /**
     * get the GridNumPerCol.
     * @return GridNumPerCol
     */
    public int getGridNumPerCol() {
        return gridNumPerCol;
    }

    /**
     * get the HeightLineLength.
     * @return HeightLineLength
     */
    public int getHeightLineLength() {
            return heightLineLength;
        }

    /**
     * get the WidthLineLength.
     * @return WidthLineLength
     */
    public int getWidthLineLength() {
        return widthLineLength;
    }

    /**
     * get the BeanWidth.
     * @return BeanWidth
     */
    public  int getBeanWidth() {
            return beanWidth;
        }
}
