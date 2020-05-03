package gamecenter.gamecenter.MoleGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Message;

import gamecenter.gamecenter.R;
import gamecenter.gamecenter.Activities.SaveGameActivity;
import gamecenter.gamecenter.ReadLoadFileManager;
import gamecenter.gamecenter.UserManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * The main activity of MoleGame
 */


// Excluded from Test Coverage since it only contain View, and a few logic
public class MoleMainActivity extends AppCompatActivity implements Observer {

    /**
     * Initialize the variables that needed in the main game activity.
     */
    private UserManager userManager;
    private ReadLoadFileManager readLoadFileManager;
    private MoleManager moleManager;
    private MoleController moleController;

    public static final int THREAD_SLEEP_TIME = 500;
    private int time ;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    /**
     * Show the remaining time
     */
    private TextView countdownTime;

    MyClick click;
    Thread t;

    private Random random;
    /**
     * Next location the mole will show up.
     */
    int next;
    /**
     * Store the buttons and the indexes(ids) of corresponding buttons.
     */
    HashMap<Button, Integer> battle;
    HashMap<Integer, Button> nextMap;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_main);
        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addSaveBackButtonListener();

        String ifLoad = getIntent().getExtras().getString("ifLoad");
        if (ifLoad != null) {
            String loadGameName = getIntent().getExtras().getString("loadGameName");
            readLoadFileManager.loadGameOfUserFromFile(userManager, userManager.getCurrentUser(), "mole_file", loadGameName);
        }

        moleManager = (MoleManager) userManager.getCurrentUser().getCurrentGameManager("currentMole");
        moleController = new MoleController();
        moleController.setMoleManager(moleManager);
        moleController.addObserver(this);
        countdownTime = findViewById(R.id.countdownTime);

        battle = new HashMap<>();
        nextMap = new HashMap<>();
        initButton();
        initOnClick();
        initbattleMap();
        initNextMap();
        next = -1;
        random = new Random();

        if (t == null) {
            /**
             * Control the time of the game.
             */
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (!moleManager.isFinished()) {
                            Thread.sleep((1000));
                            moleManager.getScoreBoard().setTime(moleManager.getScoreBoard().getTime() - 1);
                            readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        t.start();
        sendControlMessage();
    }

    private void sendControlMessage(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(THREAD_SLEEP_TIME);
                changeUI();
            }
        },time);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (THREAD_SLEEP_TIME == msg.what) {
                //the next position of mole
                    next = random.nextInt(9) + 1;
                    //game speed
                    time = 500 + 300 / moleManager.getLevel();
        }
            if(moleManager.getScoreBoard().getTime()!=0){
                sendControlMessage();
            }else{showScorePopUpWindow(moleManager.getScoreBoard());}
        }
    };


    /**
     * Initialize two MediaPlayers for MoleGame.
     */
    MediaPlayer hit;
    MediaPlayer nothit;

    /**
     * Play hit sound.
     */
    public void playhit_sound() {
        if (hit == null) {
            hit = MediaPlayer.create(this, R.raw.hit);
        }
        hit.start();
    }

    /**
     * Play nothit sound.
     */
    public void playnothit_sound() {
        if (nothit == null) {
            nothit = MediaPlayer.create(this, R.raw.nothit);
        }
        nothit.start();
    }

    @SuppressLint("SetTextI18n")
    private void showScorePopUpWindow(MoleScoreBoard scoreBoard) {
        final ConstraintLayout mRelativeLayout = findViewById(R.id.mole_main);
        final Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.mole_win_score_page, null);
        Button backButton = customView.findViewById(R.id.moleBackToMainPage);
        TextView scoreInfo = customView.findViewById(R.id.moleScoreInfo);
        scoreInfo.setText("                         Game Finish           " + "\n" + "           " + "\n" + scoreBoard.toString());
        //record the ScoreBoard
        userManager.getCurrentUser().getScoreBoardManager("MoleScoreManager").addScoreBoard(moleManager.getScoreBoard());
        userManager.getCurrentUser().setCurrentGameManager("currentMole", null);
        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
        //init final win popup window
        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                //back to starting page
                switchToStartingActivity();
            }
        });
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
    }


    private void switchToStartingActivity() {
        Intent tmp = new Intent(this, MoleStartingActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Initialize the buttons of the game.
     */
    private void initButton() {
        one = findViewById(R.id.buttonone);
        two = findViewById(R.id.buttontwo);
        three = findViewById(R.id.buttonthree);
        four = findViewById(R.id.buttonfour);
        five = findViewById(R.id.buttonfive);
        six = findViewById(R.id.buttonsix);
        seven = findViewById(R.id.buttonseven);
        eight = findViewById(R.id.buttoneight);
        nine = findViewById(R.id.buttonnine);
    }

    /**
     * Add click events to the buttons.
     */
    private void initOnClick() {
        click = new MyClick();
        one.setOnClickListener(click);
        two.setOnClickListener(click);
        three.setOnClickListener(click);
        four.setOnClickListener(click);
        five.setOnClickListener(click);
        six.setOnClickListener(click);
        seven.setOnClickListener(click);
        eight.setOnClickListener(click);
        nine.setOnClickListener(click);
    }

    /**
     * The index of each button location points to.
     */
    private void initbattleMap() {
        battle.put(one, 1);
        battle.put(two, 2);
        battle.put(three, 3);
        battle.put(four, 4);
        battle.put(five, 5);
        battle.put(six, 6);
        battle.put(seven, 7);
        battle.put(eight, 8);
        battle.put(nine, 9);
    }

    /**
     * The relation of each index(id) with the button.
     */
    private void initNextMap() {
        nextMap.put(1, one);
        nextMap.put(2, two);
        nextMap.put(3, three);
        nextMap.put(4, four);
        nextMap.put(5, five);
        nextMap.put(6, six);
        nextMap.put(7, seven);
        nextMap.put(8, eight);
        nextMap.put(9, nine);
    }

    /**
     * Updating the time and location of mole exits next time as following.
     */
    @SuppressLint("SetTextI18n")
    private void changeUI() {
        // updating the remaining time of game

        if (60 % Math.pow(2, (moleManager.getLevel() - 1)) == 0) {
            countdownTime.setText("Hit number:  " + moleManager.getScoreBoard().getHitNum() + "   Time: " + moleManager.getScoreBoard().getTime()
                    + "          "
            );
        }

        if (next == -1)
            return;
        // refresh every time mole shows up
        reImageButton();
        // obtain the corresponding next button
        int num = next % 9;
        if (num == 0) {
            Button bt = nextMap.get(num + 1);
            assert bt != null;
            bt.setBackgroundResource(R.drawable.moleup);
        } else {
            Button bt = nextMap.get(num);
            assert bt != null;
            bt.setBackgroundResource(R.drawable.moleup);
        }
        // set picture of mole here
    }

    /**
     * Initialize the background.
     */
    private void reImageButton() {
        one.setBackgroundResource(R.drawable.molehole);
        two.setBackgroundResource(R.drawable.molehole);
        three.setBackgroundResource(R.drawable.molehole);
        four.setBackgroundResource(R.drawable.molehole);
        five.setBackgroundResource(R.drawable.molehole);
        six.setBackgroundResource(R.drawable.molehole);
        seven.setBackgroundResource(R.drawable.molehole);
        eight.setBackgroundResource(R.drawable.molehole);
        nine.setBackgroundResource(R.drawable.molehole);

    }

    private View tmpV;
    /**
     * Click event.
     */
    class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tmpV = v;
            int battleId = battle.get(tmpV);
            moleController.processMoleHit(battleId, next);
        }
    }

    @Override
    protected void onStop() {
        // stop the game as finished
        super.onStop();
        if (t != null) {
            t.interrupt();
            t = null;
        }
    }

    @Override
    public void onBackPressed() {
        switchToStartingActivity();
    }

    /**
     * Activate the Save and Back button.
     */
    private void addSaveBackButtonListener() {
        Button saveButton = findViewById(R.id.mole_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSaveGame();
            }
        });
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToSaveGame() {
        Intent tmp = new Intent(this, SaveGameActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        tmp.putExtra("savedType", "Mole");
        startActivity(tmp);
    }

    @Override
    public void update(Observable observable, Object o) {
      if(((String)o).equals("isScore")){
          tmpV.setBackgroundResource(R.drawable.molehit);
          playhit_sound();
          readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
        }else if(((String)o).equals("notScore")){
          tmpV.setBackgroundResource(R.drawable.molenothit);
          playnothit_sound();
      }
    }

}


