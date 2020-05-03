
package gamecenter.gamecenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

// Singleton Class
public class UserManager implements Serializable {

    /**
     * The userManager.
     */
    private static final UserManager userManager = new UserManager();

    /**
     * The user list.
     */
    private HashMap<String, User> users;

    /**
     * The current user.
     */
    private User currentUser;

    /**
     * Constructor for UserManager
     */
    private UserManager() {
        users = new HashMap<>();
    }

    /**
     * get UserManager
     * @return the user manager
     */
    public static UserManager getUserManager(){return userManager;}

    /**
     * Add User to UserManager once a user signed up.
     *
     * @param userName String representing the user name
     * @param password the user password
     * @return return 1 if user was added into users, 0 if user already exists
     */
    int signUpUser(String userName, String password) {
        //if the user did not exist before, and its username length and password length <= 30
        if (!users.containsKey(userName) && userName.length() <= 30 && password.length() <= 30) {
            User newUser = new User(userName, password);
            //create a new user, add it to the users(the hash map), and save users
            //to "userManager.ser"
            users.put(userName, newUser);
            return 1;
            // typing is not valid
        } else if (!users.containsKey(userName)) {
            return 2;
        }
        //user already exists
        return 0;
    }

    /**
     * Gets User.
     *
     * @param userName the User's name
     * @return the user
     */
    public User getUser(String userName) {
        if (users.keySet().contains(userName)) {
            return users.get(userName);
        }
        return null;
    }

    /**
     * Check whether a username has already existed
     *
     * @param name     a username
     * @param password a user password
     * @return 1 if a username exists and logged in, 0 otherwise
     */
    int userLogIn(String name, String password) {
        for (String userName : users.keySet()) {
            if (Objects.requireNonNull(users.get(userName)).getUserName().equals(name) && Objects.requireNonNull(users.get(userName)).getUserPassword().equals(password)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Set a current user
     * @param currentUser the current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Get the user who are currently logging in
     *
     * @return the user who are currently logging in
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * get user list
     * @return user list
     */
    HashMap<String, User> getUsers(){return users;}

    /**
     * setuser list
     * @param  users the user list
     */
    void setUsers(HashMap<String, User> users){this.users = users;}

}
