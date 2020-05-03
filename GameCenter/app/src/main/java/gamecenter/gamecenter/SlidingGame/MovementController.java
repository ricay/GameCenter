package gamecenter.gamecenter.SlidingGame;

/**
 * the controller class for Sliding Game
 **/


public class MovementController  {

    /**
     * The boardManager.
     */
    private BoardManager boardManager = null;

    public MovementController() {
    }

    /**
     * set board manager
     * @param boardManager BoardManager
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Return the board manager
     * @return the board manager
     */
    public BoardManager getBoardManager(){return boardManager;}

    /**
     * Return information to users when they win the game or touch an invalid tile.
     * @param position int
     */
    public void processTapMovement(int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
        }
    }
}
