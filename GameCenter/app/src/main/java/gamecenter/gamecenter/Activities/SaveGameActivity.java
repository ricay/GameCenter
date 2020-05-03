package gamecenter.gamecenter.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import gamecenter.gamecenter.MoleGame.MoleStartingActivity;
import gamecenter.gamecenter.R;
import gamecenter.gamecenter.ReadLoadFileManager;
import gamecenter.gamecenter.SlidingGame.SlidingStartingActivity;
import gamecenter.gamecenter.SnakeGame.SnakeStartingActivity;
import gamecenter.gamecenter.UserManager;
import gamecenter.gamecenter.SlidingGame.*;
import gamecenter.gamecenter.MoleGame.*;
import gamecenter.gamecenter.SnakeGame.*;


// Excluded from Test Coverage since it only contain View
public class SaveGameActivity extends AppCompatActivity {

    /**
     * The userManager.
     */
    UserManager userManager;
    /**
     * The readLoadFileManager.
     */
    private ReadLoadFileManager readLoadFileManager;
    /**
     * The savedType.
     */
    String savedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        savedType = Objects.requireNonNull(getIntent().getExtras()).getString("savedType");
        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addSaveButtonListener();
    }

    /**
     * Activate the SignUp button.Users switch to SignUp View by
     * clicking this button
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.confirm_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText savedName = findViewById(R.id.saved_game_name);
                String fileName = savedName.getText().toString() + ".ser";

                if(savedType.equals("Sliding")) {
                    readLoadFileManager.saveGameOfUserToFile(userManager.getCurrentUser(), "sliding_file", fileName);
                    userManager.getCurrentUser().setCurrentGameManager("currentSliding", null);
                }else if(savedType.equals("Snake")){
                    readLoadFileManager.saveGameOfUserToFile(userManager.getCurrentUser(), "snake_file", fileName);
                    userManager.getCurrentUser().setCurrentGameManager("currentSnake", null);
                }else if(savedType.equals("Mole")){
                    //save current game into .ser file
                    readLoadFileManager.saveGameOfUserToFile(userManager.getCurrentUser(), "mole_file", fileName);
                    //remove current game
                    userManager.getCurrentUser().setCurrentGameManager("currentMole", null);
                }
                readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
                switchToStarting();
            }
        });
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToStarting() {
        Intent tmp = null;
        if(savedType.equals("Sliding")) {
            tmp = new Intent(this, SlidingStartingActivity.class);
        }else if(savedType.equals("Snake")){
            tmp = new Intent(this, SnakeStartingActivity.class);
        }else if(savedType.equals("Mole")){
            tmp = new Intent(this, MoleStartingActivity.class);
        }

        assert tmp != null;
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        Intent tmp = null;
        if(savedType.equals("Sliding")) {
            tmp = new Intent(this, SlidingMainActivity.class);
        }else if(savedType.equals("Snake")){
            tmp = new Intent(this, SnakeMainActivity.class);
        }else if(savedType.equals("Mole")){
            tmp = new Intent(this, MoleMainActivity.class);
        }
        assert tmp != null;
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

}
