package gamecenter.gamecenter.SlidingGame;

import java.util.Observable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The level.
     */
    private int level;
    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;
    /**
     * The boardManager of this board
     */
    private BoardManager boardManager;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     * @param level the level of game
     * @param newBoardManager the boardManager of this Board
     */
    public Board(List<Tile> tiles, BoardManager newBoardManager, int level) {
         this.tiles = new Tile[level][level];
         this.level = level;

        Iterator<Tile> iter = tiles.iterator();
        for (int row = 0; row != level; row++) {
            for (int col = 0; col != level; col++) {
                if(iter.hasNext()) {
                    this.tiles[row][col] = iter.next();
                }
            }
        }

        boardManager = newBoardManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator iterator() {
        return new BoardIterator();
    }


    /**
     * Iterate over the Tiles of a Board.
     */
    private class BoardIterator implements Iterator<Tile>, Serializable {

        /**
         * The row index of the next item in the class list.
         */
        private int nextRowIndex = 0;

        /**
         * The column index of the next item in the class list.
         */
        private int nextColIndex = 0;

        @Override
        public boolean hasNext() {
            return (nextRowIndex < level) && (nextColIndex < level)
                    && !(nextRowIndex == level - 1 && nextColIndex == level - 1);
        }

        @Override
        public Tile next() {
            Tile nextTile;
            nextTile = tiles[nextRowIndex][nextColIndex];
            if (nextColIndex < level - 1) {
                nextColIndex++;
            } else if (nextColIndex == level - 1) {
                nextColIndex = 0;
                nextRowIndex++;
            }
            return nextTile;
        }
    }


    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the level
     * @return the level
     */
    public int getLevel(){return level;}

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {

        Tile tempTile1 = tiles[row1][col1];
        Tile tempTile2 = tiles[row2][col2];

        tiles[row1][col1] = tempTile2;
        tiles[row2][col2] = tempTile1;
        //record this move into recordStack.
        boardManager.recordStep(invertCoordinateToArray(row1, col1, row2, col2));

        //score -1 for every swap
        boardManager.getScoreBoard().minusScore(1);
        boardManager.getScoreBoard().updateStep();

        setChanged();
        notifyObservers();
    }


    /**
     * Undo the Swapped tiles at (row1, col1) and (row2, col2)
     * @return return 1 if record is not empty, 0 otherwise
     */
    public int undoSwap() {
        int[][] swappedPairCoor = boardManager.getLastStep();
        if (swappedPairCoor!=null) {
            int row1 = swappedPairCoor[0][0];
            int col1 = swappedPairCoor[0][1];
            int row2 = swappedPairCoor[1][0];
            int col2 = swappedPairCoor[1][1];
            Tile tempTile1 = tiles[row1][col1];
            Tile tempTile2 = tiles[row2][col2];

            tiles[row1][col1] = tempTile2;
            tiles[row2][col2] = tempTile1;

            //score -2 for every undo
            boardManager.getScoreBoard().minusScore(2);
            boardManager.getScoreBoard().updateStep();
            setChanged();
            notifyObservers();
            return 1;
        }else{return 0;}
    }


    /**
     * a helper function, convert the coordinate to int[][].
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     * @return the int[][] of coordinate
     */
    private int[][] invertCoordinateToArray(int row1, int col1, int row2, int col2) {
        int[][] pair = new int[2][2];
        pair[0][0] = row1;
        pair[0][1] = col1;
        pair[1][0] = row2;
        pair[1][1] = col2;
        return pair;
    }


}