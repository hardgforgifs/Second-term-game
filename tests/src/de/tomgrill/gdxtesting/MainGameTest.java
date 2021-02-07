package de.tomgrill.gdxtesting;

import com.teamonehundred.pixelboat.Boat;
import com.teamonehundred.pixelboat.BoatRace;
import com.teamonehundred.pixelboat.SceneMainGame;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(GdxTestRunner.class)
public class MainGameTest extends TestCase {
    SceneMainGame testSceneMainGame;

    /**
     * This method is called before each test to create a new instance of SceneMainGame to run the tests on
     */
    @Before
    public void init() {
        testSceneMainGame = new SceneMainGame();
    }

    /** id: SceneMainGame01
     *  description: tests if boats stop moving when the game is paused
     *  input data: new instance of a SceneMainGame
     *  expected outcome: the position of each boat should be the same if the game is paused after we run a race step
     *  requirements: UR_PAUSE_MENU
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testPausedGameBoatsNotMoving() {
        // Save a list of the boats locations so we can compare later
        List<Float[]> boats_locations = new ArrayList<>();
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            float x = testSceneMainGame.getAllBoats().get(i).getSprite().getX();
            float y = testSceneMainGame.getAllBoats().get(i).getSprite().getY();
            boats_locations.add(new Float[]{x, y});
        }
        // Set the game to be pause
        testSceneMainGame.setPaused(true);

        // Call the update method
        testSceneMainGame.update();

        // The position of the boats shouldn't change
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            assertTrue(testSceneMainGame.getAllBoats().get(i).getSprite().getX() == boats_locations.get(i)[0]
                    && testSceneMainGame.getAllBoats().get(i).getSprite().getY() == boats_locations.get(i)[1]);
        }
    }

    /** id: SceneMainGame02
     *  description: tests if the total frames raced increase when updating the scene. The number of frames raced
     *      *               has to increase for the timer to increase
     *  input data: new instance of a SceneMainGame
     *  expected outcome: the number of frames raced should increase after updating the scene
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testTotalFramesIncrease() {
        // Call the update method
        testSceneMainGame.update();

        assertTrue(testSceneMainGame.getRace().getTotal_frames() > 0);
    }

    /** id: SceneMainGame03
     *  description: tests if the total frames raced stop increasing when the game is pause. The number of frames raced
     *               has to stop increasing for the timer to stop increase
     *  input data: new instance of a SceneMainGame
     *  expected outcome: the number of frames raced shouldn't increase after updating the scene
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testPausedGameTimerStops() {
        // Set the game to be pause
        testSceneMainGame.setPaused(true);

        // Call the update method
        testSceneMainGame.update();

        assertTrue(testSceneMainGame.getRace().getTotal_frames() == 0);
    }
}
