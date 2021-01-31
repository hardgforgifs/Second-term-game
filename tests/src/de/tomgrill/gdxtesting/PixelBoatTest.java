package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.teamonehundred.pixelboat.PixelBoat;
import com.teamonehundred.pixelboat.Scene;
import com.teamonehundred.pixelboat.SceneBoatSelection;
import com.teamonehundred.pixelboat.SceneMainGame;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Random;

@RunWith(GdxTestRunner.class)
public class PixelBoatTest extends TestCase {

    /**
     * This method is required to create a new instance of PixelBoat that we can then modify use to save and load
     * data into
     * @return new instance of PixelBoat
     */
    private PixelBoat createNewGame() {
        PixelBoat testGame = new PixelBoat();
        testGame.setAll_scenes(new Scene[7]);
        testGame.setPref(Gdx.app.getPreferences("saveTest"));
        testGame.getAll_scenes()[1] = new SceneMainGame();
        testGame.getAll_scenes()[5] = new SceneBoatSelection();


        return testGame;
    }

    /** id: PixelBoatTest01
     *  description: tests if the leg number is saved properly
     *  input data: new instance of PixelBoat, which is our main game class
     *  expected outcome: the leg number that is saved is the same as the one that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testSaveLegNumber() {
        // Create a new instance of the game
        PixelBoat testGame = createNewGame();

        // Assign a random leg number
        Random random = new Random();
        int legNumber = random.nextInt(4);
        ((SceneMainGame)testGame.getAll_scenes()[1]).setLeg_number(legNumber);

        // Save the game state
        testGame.saveGame((SceneMainGame) testGame.getAll_scenes()[1]);

        // Reset the game
        testGame = createNewGame();

        // Load the game state that was saved
        testGame.getAll_scenes()[1] = testGame.loadGame();

        // The leg number from the newly loaded game state should be the same as the one that was saved
        int loadedLegNumber = ((SceneMainGame)testGame.getAll_scenes()[1]).getLeg_number();
        assertTrue(loadedLegNumber == legNumber);

    }

    @Test
    public void testSaveSpecID() {
        // Create a new instance of the game
        PixelBoat testGame = createNewGame();

        // Assign a random specID
        Random random = new Random();
        int specID = random.nextInt(6);
        ((SceneMainGame)testGame.getAll_scenes()[1]).setPlayerSpec(specID);

        // Save the game state
        testGame.saveGame((SceneMainGame) testGame.getAll_scenes()[1]);

        // Reset the game
        testGame = createNewGame();

        // Load the game state that was saved
        testGame.getAll_scenes()[1] = testGame.loadGame();

        // The specID from the newly loaded game state should be the same as the one that was saved
        int loadedSpecID = ((SceneMainGame)testGame.getAll_scenes()[1]).getPlayer().getSpecID();
        assertTrue(loadedSpecID == specID);
    }

    @Test
    public void testSaveCameraPosition() {
        // Create a new instance of the game
        PixelBoat testGame = createNewGame();

        // Assign a random camera position, with x in y in range of (0,100)
        Random random = new Random();
        float cameraX = random.nextFloat() * 100f;
        float cameraY = random.nextFloat() * 100f;
        ((SceneMainGame)testGame.getAll_scenes()[1]).getPlayer().getCamera().position.x = cameraX;
        ((SceneMainGame)testGame.getAll_scenes()[1]).getPlayer().getCamera().position.y = cameraY;

        // Save the game state
        testGame.saveGame((SceneMainGame) testGame.getAll_scenes()[1]);

        // Reset the game
        testGame = createNewGame();

        // Load the game state that was saved
        testGame.getAll_scenes()[1] = testGame.loadGame();

        // The camera position from the loaded game should be the same as the one that was saved
        float loadedCameraX = ((SceneMainGame)testGame.getAll_scenes()[1]).getPlayer().getCamera().position.x;
        float loadedCameraY = ((SceneMainGame)testGame.getAll_scenes()[1]).getPlayer().getCamera().position.y;
        assertTrue(loadedCameraX == cameraX && loadedCameraY == cameraY);
    }

    @Test
    public void testSaveRaceStartTime() {
        // Create a new instance of the game
        PixelBoat testGame = createNewGame();

        // Assign the start time based on System.currentTimeMillis
        ((SceneMainGame)testGame.getAll_scenes()[1]).set_start_time();
        long startTime = ((SceneMainGame)testGame.getAll_scenes()[1]).get_start_time();

        // Save the game state
        testGame.saveGame((SceneMainGame) testGame.getAll_scenes()[1]);

        // Reset the game
        testGame = createNewGame();

        // Load the game state that was saved
        testGame.getAll_scenes()[1] = testGame.loadGame();

        // The start time from the newly loaded game state should be the same as the one that was saved
        long loadedStartTime = ((SceneMainGame)testGame.getAll_scenes()[1]).get_start_time();
        assertTrue(loadedStartTime == startTime);
    }

    @Test
    public void testSaveRaceDuration() {
        // Create a new instance of the game
        PixelBoat testGame = createNewGame();

        // Assign a random specID
        Random random = new Random();
        long raceDuration = random.nextLong();
        ((SceneMainGame)testGame.getAll_scenes()[1]).getRace().setTime(raceDuration);

        // Save the game state
        testGame.saveGame((SceneMainGame) testGame.getAll_scenes()[1]);

        // Reset the game
        testGame = createNewGame();

        // Load the game state that was saved
        testGame.getAll_scenes()[1] = testGame.loadGame();

        // The specID from the newly loaded game state should be the same as the one that was saved
        long loadedRaceDuration = ((SceneMainGame)testGame.getAll_scenes()[1]).getRace().getTime();
        assertTrue(loadedRaceDuration == raceDuration);
    }
}
