package gamecenter.gamecenter.SnakeGame;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import gamecenter.gamecenter.R;

@SuppressLint("ViewConstructor")
// Excluded from Test Coverage since it contain only View
public class GameView extends View {

    /**
     * The paint.
     */
    private Paint paint = new Paint();
    /**
     * The grid.
     */
    private Grid grid;
    /**
     * The snakeManager.
     */
    private SnakeManager snakeManager;
    /**
     * The snakeGameController.
     */
    private SnakeGameController snakeGameController;

    /**
     * construct a GameView.
     * @param context the context
     */
    public GameView(Context context) {
        super(context);
        init();
    }
    private void init() {
        // init the grid
        grid = Grid.getGrid();
        snakeGameController = new SnakeGameController();

    }

    /**
     * get the SnakeManager.
     * @return SnakeManager
     */
    public SnakeManager getSnakeManager(){return snakeManager;}

    /**
     * set the SnakeManager.
     * @param snakeManager the SnakeManager
     */
    public void setSnakeManager(SnakeManager snakeManager){
        this.snakeManager = snakeManager;
        snakeGameController.setSnakeManager(snakeManager);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //show grid
        if (grid != null) {
            paint.setColor(Color.WHITE);
            drawGrid(canvas);
        }

        //show snake
        if (snakeManager.getSnake() != null) {
            drawSnake(canvas);
        }

        drawSetUp(canvas);
    }

    /**
     * draw the setup background for snake game.
     * @param canvas the canvas
     */
    private void drawSetUp(Canvas canvas) {

        paint.setTextSize(35);
        paint.setColor(Color.WHITE);
        canvas.drawText("level: "+ snakeManager.getSpeedLevel()+"     Time: " + String.valueOf(snakeManager.getScoreBoard().getTime()) + "s"
                + "      Score: " + snakeManager.getScoreBoard().getScore(), 10, 1500, paint);

        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 1500,550, 1600 , paint);

        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        canvas.drawText("Undo", 200, 1550, paint);

        paint.setColor(Color.WHITE);
        canvas.drawRect(550,1500, 1100, 1600, paint);

        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        canvas.drawText("Save Game", 700, 1550, paint);

    }

    /**
     * draw the sake for snake game.
     * @param canvas the canvas
     */
    private void drawSnake(Canvas canvas) {
        Snake snake = snakeManager.getSnake();
        for (Point point : snake.getSnakeBody()) {
            int startX = grid.getBeanWidth() * point.getX();
            int startY = grid.getBeanWidth() * point.getY();
            Bitmap snakeBody = BitmapFactory.decodeResource(getResources(), R.drawable.tile_9);
            canvas.drawBitmap(snakeBody, startX, startY, null);
        }

        //record previous snake
        Snake recordSnake = new Snake();
        for(Point snakeBody: snakeManager.getSnake().getSnakeBody()){
            Point newBody = new Point(snakeBody.getX(), snakeBody.getY());
            recordSnake.addBody(newBody);
        }
        recordSnake.setCurrentDirection(snakeManager.getSnake().getCurrentDirection());
        snakeManager.recordStep(recordSnake);
    }

    /**
     * draw the grid for snake game.
     * @param canvas the canvas
     */
    private void drawGrid(Canvas canvas) {
        //vertical line
        for (int i = 0; i <= grid.getGridNumPerRow(); i++) {
            int startX = grid.getBeanWidth() * i;
            int startY = 0;
            int stopY = grid.getHeightLineLength();//
            canvas.drawLine(startX, startY, startX, stopY, paint);
        }
        //horiontal line
        for (int i = 0; i <= grid.getGridNumPerCol(); i++) {
            int startX = 0;
            int stopX =  grid.getWidthLineLength();
            int startY = grid.getBeanWidth() * i;
            canvas.drawLine(startX, startY, stopX, startY, paint);
        }
    }


    int x;
    int y;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) { //movement direction
        int action = event.getAction() & MotionEvent.ACTION_MASK;

        if (action == KeyEvent.ACTION_DOWN) {
            x = (int) (event.getX());
            y = (int) (event.getY());
        }
            if (action == KeyEvent.ACTION_UP) {
                int x = (int) (event.getX());
                int y = (int) (event.getY());
                int[] originXY = new int[2];
                int[] stopXY = new int[2];
                originXY[0] = this.x;
                originXY[1] = this.y;
                stopXY[0] = x;
                stopXY[1] = y;

                //process click by controller
                snakeGameController.clickPositionProcessor(originXY, stopXY);

            }

        return super.onTouchEvent(event);
    }

    /**
     * get the SnakeGameController.
     * @return SnakeGameController
     */
    SnakeGameController getSnakeGameController(){return snakeGameController;}

}
