package de.tomgrill.gdxtesting;


import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(GdxTestRunner.class)
public class BoatRaceTest extends TestCase {

    SceneMainGame testSceneMainGame;
    BoatRace testRace;

    /**
     * This method is called before each test to create a new instance of
     * SceneMainGame and BoatRace to be used in testing
     */
    @Before
    public void init() {
        testSceneMainGame = new SceneMainGame();
        testRace = new BoatRace(testSceneMainGame.getAllBoats());
    }


    /** id: BoatRaceTest01
     *  description: tests the correct instantiation of obstacles
     *  input data: new instance of BoatRace
     *  expected outcome: non-empty list of obstacles
     *  requirements: UR_OBSTACLE, FR_OBSTACLES
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testBoatRaceObstacleCreation() {
        assertFalse(testRace.getObstacles().isEmpty());
    }

    /** id: BoatRaceTest02
     *  description: tests the correct instantiation of boats
     *  input data: new instance of BoatRace
     *  expected outcome: non-empty list of boats
     *  requirements: UR_BOATS_NO, FR_BOATS_NO
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testBoatRaceBoatsCreation() {
        assertFalse(testRace.getBoats().isEmpty());
    }


    /** id: BoatRaceTest03
     *  description: tests if a boat out of lane receives penalties, while a boat inside its lane is not penalised
     *  input data: new instance of BoatRace and 2 boats
     *  expected outcome: the penalty attribute of the boat should  be >0
     *  requirements: UR_LANE_PENALTY, FR_LANE_PENALTY
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testBoatOutOfLane() {
        // Move a boat to a position that is definitely outside the lane
        testRace.getBoats().get(0).getSprite().setPosition(-testRace.getLane_width() * 2, testRace.getEnd_y() / 2);
        // Run a step of the race
        testRace.runStep();

        // After the step is ran, the first boat should be penalised, while the second one shouldn't
        assertTrue(testRace.getBoats().get(0).getTimeToAdd() > 0);
        assertTrue(testRace.getBoats().get(1).getTimeToAdd() == 0);
    }


    /** id: BoatRaceTest04
     *  description: tests if the boosts duration decreases, and if the boosts effects disappear after the 5s duration
     *  input data: new instance of BoatRace, an effect with a very low remaining duration
     *  expected outcome: boat shouldn't have any remaining effects after a boatRace step
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testBoostDurationDecreases() {
        // Add a speed boost to one of the boats boat
        // The speed boost should have a very low duration so we can test if it disappears after it's timer reaches 0
        Float[] effect = new Float[] {1f, 1f};
        testRace.getBoats().get(0).getEffects().add(effect);

        // Run a step of the race
        testRace.runStep();

        // The boost's duration should have decreased, it could also have decreased below 0
        assertTrue(testRace.getBoats().get(0).getEffects().isEmpty() ||
                testRace.getBoats().get(0).getEffects().get(0)[1] < 1f);
    }

    @Test
    public void testBoostEnds() {
        // Add a speed boost to one of the boats boat
        // The speed boost should have a very low duration so we can test if it disappears after it's timer reaches 0
        Float[] effect = new Float[] {1f, 0f};
        testRace.getBoats().get(0).getEffects().add(effect);

        // Run a step of the race
        testRace.runStep();

        // The boost should be removed since it's duration expired
        assertTrue(testRace.getBoats().get(0).getEffects().isEmpty());
    }
}