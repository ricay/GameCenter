package gamecenter.gamecenter.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;
import gamecenter.gamecenter.*;
import gamecenter.gamecenter.SlidingGame.*;
import gamecenter.gamecenter.MoleGame.*;
import gamecenter.gamecenter.SnakeGame.SelectSnakeGameLevelActivity;
import gamecenter.gamecenter.SnakeGame.SnakeMainActivity;

// Excluded from Test Coverage since it only contain View
public class ContinuePreviousActivity extends AppCompatActivity {

    /**
     * The userManager.
     */
    private UserManager userManager;
    /**
     * The readLoadFileManager.
     */
    private ReadLoadFileManager readLoadFileManager;
    /**
     * The gameType
     */
    private String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_previous_snake);

        //init a new user manager, auto load from .ser to get all the data
        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        gameType = Objects.requireNonNull(getIntent().getExtras()).getString("continueGameType");
        addContinueGameButtonListener();
        addNotContinueGameButtonListener();
    }

    /**
     * Activate ContinueGame button.
     */
    private void addContinueGameButtonListener() {
        Button continueButton = findViewById(R.id.continueSnakeButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(gameType);
            }
        });
    }

    /**
     * Activate NotContinueGame button.
     */
    private void addNotContinueGameButtonListener() {
        Button notContinueButton = findViewById(R.id.NotContinueSnakeButton);
        notContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to game main menu page

                if(gameType.equals("Snake")) {
                    userManager.getCurrentUser().setCurrentGameManager("currentSnake", null);
                }else if(gameType.equals("Sliding")){
                    userManager.getCurrentUser().setCurrentGameManager("currentSliding", null);
                }else if(gameType.equals("Mole")) {
                    userManager.getCurrentUser().setCurrentGameManager("currentMole", null);
                }
                readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
                switchToSelectGameLevel();
            }
        });
    }

    /**
     * Switch to the Game view.
     */
    private void switchToGame(String gameType) {
        Intent tmp = null;
        if(gameType.equals("Snake")) {
            tmp = new Intent(this, SnakeMainActivity.class);
        }else if(gameType.equals("Sliding")){
            tmp = new Intent(this, SlidingMainActivity.class);
        }else if(gameType.equals("Mole")) {
            tmp = new Intent(this, MoleMainActivity.class);
        }
        assert tmp != null;
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Switch to the SelectGameLevel view.
     */
    private void switchToSelectGameLevel() {
        Intent tmp = null;
        if(gameType.equals("Snake")) {
            tmp = new Intent(this, SelectSnakeGameLevelActivity.class);
        }else if(gameType.equals("Sliding")){
            tmp = new Intent(this, SelectSlidingGameLevelActivity.class);
        }else if(gameType.equals("Mole")) {
            tmp = new Intent(this, SelectMoleGameLevelActivity.class);
        }
        assert tmp != null;
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

}
