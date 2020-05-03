package gamecenter.gamecenter;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import gamecenter.gamecenter.MoleGame.MoleManager;
import gamecenter.gamecenter.SlidingGame.BoardManager;
import gamecenter.gamecenter.SnakeGame.SnakeManager;


// A class that only save file, excluded from coverage.
public class ReadLoadFileManager {

    private final String USER_MANAGER_FILE = "userManager.ser";

    private Context context;

    public ReadLoadFileManager(Context context){
        this.context = context;
    }

    //===============================Save, Read, toString==========================================


    /**
     * Load the board manager from fileName.
     */
    public void loadUsersOfUserManagerFromFile(UserManager userManager) {

        try {
            InputStream inputStream = this.context.openFileInput(USER_MANAGER_FILE);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userManager.setUsers( (HashMap<String, User>) input.readObject());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to the "".ser" fileName.
     */
    public void saveUsersOfUserManagerToFile(UserManager userManager) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.context.openFileOutput(USER_MANAGER_FILE, Context.MODE_PRIVATE));
            outputStream.writeObject(userManager.getUsers());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the saved board manager from fileName.
     */
    public void loadGameOfUserFromFile(UserManager userManager, User user, String gameName,  String gameManagerName) {
        try {
            InputStream inputStream = this.context.openFileInput(gameManagerName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                if (gameName.equals("sliding_file")) {
                    user.setCurrentGameManager("currentSliding", (BoardManager) input.readObject());
                } else if (gameName.equals("snake_file")) {
                    user.setCurrentGameManager("currentSnake", (SnakeManager) input.readObject());
                } else if (gameName.equals("mole_file")) {
                    user.setCurrentGameManager("currentMole", (MoleManager) input.readObject());
                }
                user.get_user_saved_games(gameName).remove(gameManagerName);
                saveUsersOfUserManagerToFile(userManager);
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName. Return 1 if save success, otherwise return 0.
     */
    public void saveGameOfUserToFile(User user, String gameName, String gameManagerName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.context.openFileOutput(gameManagerName, Context.MODE_PRIVATE));
            if (gameName.equals("sliding_file")) {
                if(!user.get_user_saved_games(gameName).contains(gameManagerName) && user.get_user_saved_games(gameName).size()<=5) {
                    user.get_user_saved_games(gameName).add(gameManagerName);
                    outputStream.writeObject(user.getCurrentGameManager("currentSliding"));
                }
            } else if (gameName.equals("snake_file")) {
                if(!user.get_user_saved_games(gameName).contains(gameManagerName) && user.get_user_saved_games(gameName).size()<=5) {
                    user.get_user_saved_games(gameName).add(gameManagerName);
                    outputStream.writeObject(user.getCurrentGameManager("currentSnake"));
                }
            } else if (gameName.equals("mole_file")) {
                if(!user.get_user_saved_games(gameName).contains(gameManagerName) && user.get_user_saved_games(gameName).size()<=5) {
                    user.get_user_saved_games(gameName).add(gameManagerName);
                    outputStream.writeObject(user.getCurrentGameManager("currentMole"));
                }
            }
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
