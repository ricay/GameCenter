package gamecenter.gamecenter.SlidingGame;

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

import java.io.Serializable;
import java.util.Objects;

import gamecenter.gamecenter.Activities.ContinuePreviousActivity;
import gamecenter.gamecenter.Activities.LoadSavedGameActivity;
import gamecenter.gamecenter.Activities.SelectGameActivity;
import gamecenter.gamecenter.R;
import gamecenter.gamecenter.*;
/**
 * The initial activity for the sliding puzzle tile game.
 */

// Excluded from Test Coverage since it contain only View
public class SlidingStartingActivity extends AppCompatActivity implements Serializable {


    /**
     * The board manager.
     */
    private UserManager userManager;
    private ReadLoadFileManager readLoadFileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        setContentView(R.layout.activity_sliding_starting);
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addStartButtonListener();
        addLoadButtonListener();
        addScoreHistoryListener();
        addBackToSelectGameButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.GoButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
                if(userManager.getCurrentUser().getCurrentGameManager("currentSliding")!=null){
                    switchToContinuePreviousGameActivity();
                }else {
                    switchToSelectLevel();
                }
            }
        });
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
                if(userManager.getCurrentUser().get_user_saved_games("sliding_file").size()!=0){
                    makeToastLoadedText();
                    switchToLoadSavedGame();
                }else{makeToastNoSavedGameText();}
            }
        });
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
     * Activate the save button.
     */
    private void addBackToSelectGameButtonListener() {
        Button logOutButton = findViewById(R.id.back_to_selectgame);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelectGameActivity();
            }
        });
    }

    /**
     * Display that no score history.
     */
    private void makeToastNoScoreHistoryText() {
        Toast.makeText(this, "No Score History", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectLevel() {
        Intent tmp = new Intent(this, SelectSlidingGameLevelActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Switch to the LoadSavedGame Activity view.
     */
    private void switchToLoadSavedGame(){
        Intent tmp = new Intent(this, LoadSavedGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("loadType", "sliding_file");
        startActivity(tmp);
    }

    /**
     * Switch to the ContinuePreviousGameActivity view.
     */
    private void switchToContinuePreviousGameActivity(){
        Intent tmp = new Intent(this, ContinuePreviousActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("continueGameType", "Sliding");
        startActivity(tmp);
    }

    /**
     * Switch to the SelectGameActivity view.
     */
    private void switchToSelectGameActivity(){
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Activate the ScoreHistory button.
     */
    private void addScoreHistoryListener(){
        final LinearLayout mRelativeLayout = findViewById(R.id.sliding_starting);
        Button checkScoreButton = findViewById(R.id.checkScoreHistory);
        final Context mContext = getApplicationContext();
        // Initialize a new instance of popup window
        checkScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.sliding_score_history,null);
                Button closeButton = customView.findViewById(R.id.closeHistory);
                TextView scoreHistory = customView.findViewById(R.id.scoreHistory);
                if(userManager.getCurrentUser().getScoreBoardManager("SlidingScoreManager").getScoreBoardHistory().size()!=0) {
                    scoreHistory.setText(userManager.getCurrentUser().getScoreBoardManager("SlidingScoreManager").toString());
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
