package gamecenter.gamecenter;

import org.junit.Test;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.*;

import java.util.Objects;

import gamecenter.gamecenter.SlidingGame.*;

import static org.junit.Assert.*;

public class UserAndUserManagerTest {
    private User testUser;
    private UserManager userManager;
    private ScoreBoardManager scoreBoardManager;

    @Before
    public void setUpUser(){
        testUser = new User("A", "123");
        userManager = UserManager.getUserManager();
        userManager.signUpUser("A","123");
        scoreBoardManager = new ScoreBoardManager();
    }

    @Test
    public void testGetUserName() {
        assertEquals("A", testUser.getUserName());
    }

    @Test
    public void testGetUserPassword() {
        assertEquals("123", testUser.getUserPassword());
    }


    @Test
    public void testSetUserPassword() {
        testUser.setUserPassword("321");
        assertEquals("321", testUser.getUserPassword());
    }

    @Test
    public void testGetCurrentGameManager() {
        assertNull(testUser.getCurrentGameManager("currentSliding"));
        assertNull(testUser.getCurrentGameManager("currentSnake"));
    }

    @Test
    public void testToString() {
        assertEquals("User: A     Password: 123", testUser.toString());
    }

    @Test
    public void testEquals() {
        User newUser = new User("A", "123");
        assertEquals(newUser, testUser);
    }

    @Test
    public void get_user_saved_games_Test(){
        assertEquals(0, testUser.get_user_saved_games("sliding_file").size());
        testUser.get_user_saved_games("sliding_file").add("A.ser");
        assertEquals("A.ser", testUser.get_user_saved_games("sliding_file").get(0));
    }

    @Test
    public void getScoreBoardManagerTest(){
        ScoreBoardManager scoreBoardManager = testUser.getScoreBoardManager("SlidingScoreManager");
        assertEquals(scoreBoardManager, testUser.getScoreBoardManager("SlidingScoreManager"));
    }

    @Test
    public void setCurrentGameManagerTest(){
        assertNull(testUser.getCurrentGameManager("currentSliding"));
        BoardManager b = new BoardManager(3,3);
        testUser.setCurrentGameManager("currentSliding", b);
        assertEquals(b, testUser.getCurrentGameManager("currentSliding"));
    }


    //===== tests for UserManager ======

    @Test
    public void testSignUpUser() {
        assertEquals(1, userManager.signUpUser("B", "123"));
        assertEquals(0, userManager.signUpUser("B","123"));
    }

    @Test
    public void testSignUpUserInvalidTyping(){
        assertEquals(2, userManager.signUpUser("acsbjvbjvdksnvmsnvm,nbfjkvhbdfkjbv kjdnd d", "dkvmkjbjvvkjdbsd vkjhbejkqhvbkjasv ,s dj jka vba kv sjds"));
    }

    @Test
    public void getExistUserTest() {
        assertEquals("A", userManager.getUser("A").getUserName());
    }

    @Test
    public void getNonUserTest() {
        assertNull(userManager.getUser("K"));}

    @Test
    public void testUserLogInTest() {
        assertEquals(1, userManager.userLogIn("A", "123"));
        assertEquals(0, userManager.userLogIn("E", "123"));
        assertEquals("A", (Objects.requireNonNull(userManager.getUsers().get("A"))).getUserName());
    }

    @Test
    public void setCurrentUserTest() {
        userManager.signUpUser("C", "123");
        userManager.setCurrentUser(userManager.getUser("C"));
        assertEquals("C", userManager.getCurrentUser().getUserName());
    }

    // ===== tests for ScoreBoard and ScoreBoardManager ======

    @Test
    public void getAndSetLevelTest() {
        SlidingScoreBoard s = new SlidingScoreBoard(3, "A");
        assertEquals(3, s.getLevel());
        s.setLevel(4);
        assertEquals(4,s.getLevel());
    }

    @Test
    public void setTimetest(){
        SlidingScoreBoard s = new SlidingScoreBoard(3, "A");
        s.setTime(10);
        assertEquals(10, s.getTime());
    }

    @Test
    public void minusScoreTest(){
        SlidingScoreBoard s = new SlidingScoreBoard(3,"A");
        s.setScore(20);
        s.minusScore(10);
        assertEquals(10, s.getScore());
    }

    @Test
    public  void toStringTest(){
        SlidingScoreBoard s = new SlidingScoreBoard(3,"A");
        String string = "                    User: " + "A" + "\n"
                + "                    Game Level:    " +  3 + "\n"
                + "                    Total score:         " + 500 + "\n"
                + "                              " + "\n"
                + "                    Total Step:         " + 0 + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + 0 + "\n";
        assertEquals(string, s.toString());
    }

    @Test
    public void addScoreBoardTest() {
        SlidingScoreBoard b = new SlidingScoreBoard(3, "A");
        assertEquals(0, scoreBoardManager.getScoreBoardHistory().size());
        scoreBoardManager.addScoreBoard(b);
        assertEquals(b, scoreBoardManager.getScoreBoardHistory().get(0));
        scoreBoardManager.addScoreBoard(b);
        scoreBoardManager.addScoreBoard(b);
        scoreBoardManager.addScoreBoard(b);
        scoreBoardManager.addScoreBoard(b);
        scoreBoardManager.addScoreBoard(b);
        assertEquals(5,scoreBoardManager.getScoreBoardHistory().size());
    }

    @Test
    public void ScoreManagerToStringTest() {
        String s = "     =======  Scores Ranking  =======" + "\n";
        SlidingScoreBoard b = new SlidingScoreBoard(3, "A");
        scoreBoardManager.addScoreBoard(b);
        assertEquals(scoreBoardManager.toString(), scoreBoardManager.toString());

    }

}