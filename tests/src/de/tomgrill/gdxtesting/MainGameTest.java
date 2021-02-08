package de.tomgrill.gdxtesting;

import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.After;
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

    @After
    public void dispose() { testSceneMainGame.dispose(); }

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

    /** id: SceneMainGame04
     *  description: tests if the number of obstacles increases after each leg
     *  input data: new instance of a SceneMainGame
     *  expected outcome: the number of obstacles should increase after a leg
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testNumberObstacleIncrease() {
        // Save the current number of obstacles
        int obstacles_nr = testSceneMainGame.getRace().getObstacles().size();

        // Go to the next leg
        testSceneMainGame.getRace().setIs_finished(true);
        testSceneMainGame.update();

        // The number of obstacles is larger than what it used to be
        assertTrue(testSceneMainGame.getRace().getObstacles().size() > obstacles_nr);
    }

    /** id: SceneMainGame06
     *  description: tests if the speed of obstacles increases after each leg
     *  input data: new instance of a SceneMainGame
     *  expected outcome: the speed of obstacles should increase after a leg
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testObstacleSpeedIncreases() {
        // Save the current obstacles
        List<CollisionObject> obstacles = new ArrayList<>();
        obstacles.addAll(testSceneMainGame.getRace().getObstacles());

        // Go to the next leg
        testSceneMainGame.getRace().setIs_finished(true);
        testSceneMainGame.update();

        // The speed of obstacles is larger than what it used to be
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles instanceof ObstacleDuck)
                assertTrue(((ObstacleDuck)obstacles.get(i)).getSpeed() >
                        ((ObstacleDuck)testSceneMainGame.getRace().getObstacles().get(i)).getSpeed());
            if (obstacles instanceof ObstacleFloatingBranch)
                assertTrue(((ObstacleFloatingBranch)obstacles.get(i)).getSpeed() >
                        ((ObstacleFloatingBranch)testSceneMainGame.getRace().getObstacles().get(i)).getSpeed());
        }
    }
}
