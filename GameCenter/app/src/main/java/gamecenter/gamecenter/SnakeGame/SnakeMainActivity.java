package gamecenter.gamecenter.SnakeGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import gamecenter.gamecenter.R;
import gamecenter.gamecenter.Activities.SaveGameActivity;
import gamecenter.gamecenter.ReadLoadFileManager;
import gamecenter.gamecenter.UserManager;
import gamecenter.gamecenter.ScoreBoard;

// Excluded from Test Coverage since it contain only View and a few logic
public class SnakeMainActivity extends AppCompatActivity implements Observer  {

    /**
     * The userManager.
     */
    private UserManager userManager;
    /**
     * The readLoadFileManager.
     */
    private ReadLoadFileManager readLoadFileManager;
    /**
     * The snakeManager.
     */
    private SnakeManager snakeManager;
    /**
     * The refresh time.
     */
    private static final int WHAT_REFRESH = 300;
    private int time = 300;// time between every refresh
    /**
     * The level.
     */
    private int level;
    /**
     * The count that increase the speed of snake.
     */
    private int count = 0 ;
    /**
     * The game view.
     */
    private GameView gameView;
    /**
     * The timer.
     */
    private Thread t;
    /**
     * Initialize two MediaPlayers for SnakeGame.
     */
    MediaPlayer music;
    /**
     *Play background sound.
     */
    public void playBackgroundMusic() {
            music = MediaPlayer.create(this, R.raw.spirted_away);
        music.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playBackgroundMusic();

        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));

        String ifLoad = getIntent().getExtras().getString("ifLoad");
        if(ifLoad!=null){
            String loadGameName = getIntent().getExtras().getString("loadGameName");
            readLoadFileManager.loadGameOfUserFromFile(userManager, userManager.getCurrentUser(), "snake_file", loadGameName);
        }

        snakeManager = (SnakeManager) userManager.getCurrentUser().getCurrentGameManager("currentSnake");
        snakeManager.addObserver(this);
        gameView = new GameView(this);
        gameView.setSnakeManager(snakeManager);
        gameView.getSnakeGameController().addObserver(this);
        level = snakeManager.getSpeedLevel();
        setContentView(gameView);

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.snake_game_background);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.getResources(), background);
        gameView.setBackground(bitmapDrawable);
        gameView.setClickable(true);

        t = new Thread(){
            @Override
            public void run(){
                while (!snakeManager.checkIsFailed()){
                    if(t.isInterrupted()){
                        break;
                    }
                    try{
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                gameView.getSnakeManager().getScoreBoard().setTime(gameView.getSnakeManager().getScoreBoard().getTime() + 1);
                            }
                        });
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        sendControlMessage();
    }

    /**
     * send game condition message
     */
    private void sendControlMessage(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(WHAT_REFRESH);
            }
        },time);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if(WHAT_REFRESH == msg.what){
                boolean isAdd = false;
                count ++ ;
                if(count % (20/level) == 0){
                    //snake grow
                    isAdd = true;
                    if(time - (20/level) >0){
                        time = time - (10/level);
                    }
                }
                gameView.invalidate();

                //Game Continue
                if(!snakeManager.checkGrowSnake(isAdd)) {
                    sendControlMessage();
                    userManager.getCurrentUser().setCurrentGameManager("currentSnake", gameView.getSnakeManager());
                    readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
                }
                // Game Failed
                else{
                    music.stop();
                    userManager.getCurrentUser().setCurrentGameManager("currentSnake", null);
                    showScorePopUpWindow(snakeManager.getScoreBoard());
                }

            }
        }
    };

    /**
     * active the score board page
     */
    @SuppressLint("SetTextI18n")
    private void showScorePopUpWindow(ScoreBoard scoreBoard){
        final Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.snake_win_score_page,null);
        Button backButton = customView.findViewById(R.id.backToSnakeMainPage);
        TextView scoreInfo = customView.findViewById(R.id.snakeScoreInfo);
        scoreInfo.setText("                         YOU  DIED           " + "\n" + "           " + "\n" + scoreBoard.toString());
        //record the ScoreBoard
        userManager.getCurrentUser().getScoreBoardManager("SnakeScoreManager").addScoreBoard(gameView.getSnakeManager().getScoreBoard());
        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
        //init final win popup window
        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(gameView, Gravity.CENTER,0,0);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                switchToSnakeStartingActivity(); }});
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
    }

    @Override
    public void onBackPressed() {
        switchToSnakeStartingActivity();
    }

    /**
     * Switch to the SnakeStarting view to sign up.
     */
    private void switchToSnakeStartingActivity() {
        music.stop();
        Intent tmp = new Intent(this, SnakeStartingActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Switch to the SaveGame view
     */
    private void switchToSaveGame() {
        music.stop();
        Intent tmp = new Intent(this, SaveGameActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        tmp.putExtra("savedType", "Snake");
        startActivity(tmp);
    }

    @Override
    public void update(Observable observable, Object condition) {
        if(condition.equals("save")) {
            handler.removeCallbacksAndMessages(null);
            switchToSaveGame();
        }
    }
}