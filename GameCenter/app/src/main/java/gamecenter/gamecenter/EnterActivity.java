package gamecenter.gamecenter;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gamecenter.gamecenter.Activities.SelectGameActivity;

/**
 * The enter activity.Users log in
 * in this view.
 */

// Excluded from Test Coverage since it only contain View
public class EnterActivity extends AppCompatActivity {

    /**
     * The userManager.
     */
    private UserManager userManager;
    /**
     * The readLoadFileManager
     */
    private ReadLoadFileManager readLoadFileManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        addLogInButtonListener();
        addSignUpButtonListener();
        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
    }


    /**
     * Activate the SignUp button.Users switch to SignUp View by
     * clicking this button
     */
    public void addSignUpButtonListener() {
        Button SignUpButton = findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    /**
     * Activate the LogIn button.
     */
    public void addLogInButtonListener() {
        Button LogInButton = findViewById(R.id.LogInButton);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String logUserName = ((EditText) findViewById(R.id.LogUserName)).getText().toString();
                String logUserPassword = ((EditText) findViewById(R.id.LogUserPassword)).getText().toString();
                //user log in
                if(userManager.userLogIn(logUserName, logUserPassword)==1) {
                    userManager.setCurrentUser(userManager.getUser(logUserName));
                        readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
                        makeToastValidUserText();
                        //go to sliding game
                            switchToSelectGameActivity();
                        }else{ makeToastInValidUserText();}
                ((EditText) findViewById(R.id.LogUserName)).setText("");
                ((EditText) findViewById(R.id.LogUserPassword)).setText("");

            }
        });
    }

    /**
     * Display that the user logged in successfully.
     */
    private void makeToastValidUserText() {
        Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the user doesn't exist.
     */
    private void makeToastInValidUserText() {
        Toast.makeText(this, "User doesn't exist or incorrect password", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    public void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the Game Main view.
     */
    public void switchToSelectGameActivity() {
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }
}