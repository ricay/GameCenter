package gamecenter.gamecenter.MoleGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import gamecenter.gamecenter.*;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import java.util.Objects;


/**
 * Activity for user to chose the game level for MoleGame.
 */

// Excluded from Test Coverage since it only contain View
public class SelectMoleGameLevelActivity extends AppCompatActivity{

    private UserManager userManager;
    private ReadLoadFileManager readLoadFileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mole_game_level);

        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addListenerOnStartButton();
    }

    /**
     * Add listener for MoleGame start button.
     */
    public void addListenerOnStartButton() {
        Button startButton = findViewById(R.id.start_mole);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = findViewById(R.id.moleRatingBar);
                int level = Math.round(ratingBar.getRating());
                switchToMoleGame(level);
            }
        });
    }

    /**
     * Switch to the SlidingMainActivity view to play the game.
     */
    private void switchToMoleGame(int level) {
        //create a new mole manager.
        MoleManager newMole = new MoleManager(level, userManager.getCurrentUser().getUserName());
        userManager.getCurrentUser().setCurrentGameManager("currentMole", newMole);
        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
        Intent tmp = new Intent(this, MoleMainActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Switvh to mole starting page.
     */
    private void switchToStartingActivity() {
        Intent tmp = new Intent(this, MoleStartingActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        switchToStartingActivity();
    }

}
