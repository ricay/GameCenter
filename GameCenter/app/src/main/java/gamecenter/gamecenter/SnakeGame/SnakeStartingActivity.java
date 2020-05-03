package gamecenter.gamecenter.SnakeGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import gamecenter.gamecenter.*;
import gamecenter.gamecenter.Activities.ContinuePreviousActivity;
import gamecenter.gamecenter.Activities.LoadSavedGameActivity;
import gamecenter.gamecenter.Activities.SelectGameActivity;

// Excluded from Test Coverage since it contain only View
public class SnakeStartingActivity extends AppCompatActivity {

    /**
     * The readLoadFileManager.
     */
    private ReadLoadFileManager readLoadFileManager;
    /**
     * The userManager.
     */
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_starting);

        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addStartButtonListener();
        addLoadButtonListener();
        addScoreHistoryListener();
        addBackToMainButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.snake_new_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userManager.getCurrentUser().getCurrentGameManager("currentSnake")!=null){
                    switchToContinuePreviousGameActivity();
                }else {
                    switchToSelectSnakeLevel();
                }
            }
        });
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.snake_load_saved);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
                if(userManager.getCurrentUser().get_user_saved_games("snake_file").size()!=0){
                    makeToastLoadedText();
                    switchToLoadSavedGame();
                }else{makeToastNoSavedGameText();}
            }
        });
    }

    /**
     * Activate the BackToMain button.
     */
    private void addBackToMainButtonListener() {
        Button startButton = findViewById(R.id.back_to_select_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelectGame();
            }
        });
    }

    /**
     * Switch to the ContinuePreviousGame Activity view.
     */
    private void switchToContinuePreviousGameActivity(){
        Intent tmp = new Intent(this, ContinuePreviousActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("continueGameType", "Snake");
        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
        startActivity(tmp);
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectSnakeLevel() {
        Intent tmp = new Intent(this, SelectSnakeGameLevelActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
        startActivity(tmp);
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectGame() {
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
        startActivity(tmp);
    }

    /**
     * Switch to the LoadSaved Activity view.
     */
    private void switchToLoadSavedGame(){
        Intent tmp = new Intent(this, LoadSavedGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("loadType", "snake_file");
        startActivity(tmp);
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastNoSavedGameText() {
        Toast.makeText(this, "No saved game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that no score history.
     */
    private void makeToastNoScoreHistoryText() {
        Toast.makeText(this, "No Score History", Toast.LENGTH_SHORT).show();
    }

    /**
     * active the score history page pop window.
     */
    private void addScoreHistoryListener(){
        final LinearLayout mRelativeLayout = findViewById(R.id.snake_starting);
        Button checkScoreButton = findViewById(R.id.snake_score_history);
        final Context mContext = getApplicationContext();
        // Initialize a new instance of popup window
        checkScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.snake_score_history,null);
                Button closeButton = customView.findViewById(R.id.closeSnakeHistory);
                TextView scoreHistory = customView.findViewById(R.id.snakeScoreHistory);
                if(userManager.getCurrentUser().getScoreBoardManager("SnakeScoreManager").getScoreBoardHistory().size()!=0) {
                    scoreHistory.setText(userManager.getCurrentUser().getScoreBoardManager("SnakeScoreManager").toString());
                }else{makeToastNoScoreHistoryText();}

                final PopupWindow mPopupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }

        });
    }

}