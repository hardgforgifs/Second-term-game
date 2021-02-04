package de.tomgrill.gdxtesting;


import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(GdxTestRunner.class)
public class BoatRaceTest extends TestCase {
    /** id: BoatRaceTest01
     *  description: tests the correct instantiation of obstacles
     *  input data: new instance of BoatRace
     *  expected outcome: non-empty list of obstacles
     *  requirements: UR_OBSTACLE, FR_OBSTACLES
     *  category: white box testing
     *  author: Dragos Stoican
     */
    List<Boat> all_boats;
    SceneMainGame testSceneMainGame;
    BoatRace testRace;

    @Before
    public void init() {
        testSceneMainGame = new SceneMainGame();
        testRace = new BoatRace(testSceneMainGame.getAllBoats());
    }

    @Test
    public void testBoatRaceObstacleCreation() {
        assertFalse(testRace.getObstacles().isEmpty());
    }

    @Test
    public void testBoatRaceBoatsCreation() {
        assertFalse(testRace.getBoats().isEmpty());
    }


    /** id: BoatRaceTest02
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


    @Test
    public void testBoostDuration() {
        // Add a speed boost to one of the boats boat
        // The speed boost should have a very low duration so we can test if it disappears after it's timer reaches 0
        Float[] effect = new Float[] {1f, 0.001f};
        testRace.getBoats().get(0).getEffects().add(effect);

        // Run a step of the race
        testRace.runStep();

        assertTrue(testRace.getBoats().get(0).getEffects().isEmpty());
    }
}