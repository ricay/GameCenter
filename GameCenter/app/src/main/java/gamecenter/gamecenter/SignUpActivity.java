
package gamecenter.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import gamecenter.gamecenter.Activities.SelectGameActivity;


/**
 * The sign up activity.Users sign up
 * in this view.
 */

// Excluded from Test Coverage since it only contain View
public class SignUpActivity extends AppCompatActivity  {

    /**
     * The userManager.
     */
    private UserManager userManager;
    /**
     * The readLoadFileManager.
     */
    private ReadLoadFileManager readLoadFileManager;
    /**
     * The currentUser.
     */
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        readLoadFileManager = new ReadLoadFileManager(this);
        userManager = UserManager.getUserManager();
        readLoadFileManager.loadUsersOfUserManagerFromFile(userManager);
        addConfirmSignUpButtonListener();
    }

    /**
     * Activate the ConfirmSignUp button.
     */
    private void addConfirmSignUpButtonListener() {
        Button ConfirmSignUpButton = findViewById(R.id.ConfirmSignUpButton);
        ConfirmSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = ((EditText) findViewById(R.id.UserName)).getText().toString();
                String newPassword = ((EditText) findViewById(R.id.UserPassword)).getText().toString();

                //check if a user already exists? If no, add username-password
                //to the username-password HashMap.
                if (userManager.signUpUser(newName, newPassword) == 1) {
                    //Enter into the game
                    currentUser = newName;
                    readLoadFileManager.saveUsersOfUserManagerToFile(userManager);
                    switchToSelectGameActivity();
                } else if(userManager.signUpUser(newName, newPassword) == 2) {
                    makeToastInValidNewAccountText();
                } else {
                    makeToastUserExistText();
                    switchToLogIn();
                }
                ((EditText) findViewById(R.id.UserName)).setText("");
                ((EditText) findViewById(R.id.UserPassword)).setText("");
            }

            });
    }

    /**
     * Display that the limit of creating a New Account.
     */
    private void makeToastInValidNewAccountText() {
        Toast.makeText(this, "max UserName length : 30 \n max UserPassword length : 30", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a user exists.
     */
    private void makeToastUserExistText() {
        Toast.makeText(this, "User already exists, please log in", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the SelectGame view.
     */
    private void switchToSelectGameActivity() {
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * Switch to LogIn.
     */
    private void switchToLogIn() {
        Intent tmp = new Intent(this, EnterActivity.class);
        startActivity(tmp);
    }

}

