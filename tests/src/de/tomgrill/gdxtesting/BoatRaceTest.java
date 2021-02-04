package de.tomgrill.gdxtesting;


import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
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
    @Test
    public void testBoatRaceObstacleCreation() {
        List<Boat> all_boats = new ArrayList<>();
        BoatRace testRace = new BoatRace(all_boats);
        assertFalse(testRace.getObstacles().isEmpty());
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
        List<Boat> all_boats = new ArrayList<>();
        // Create the two boats and add them to the list
        Boat testBoat = new PlayerBoat(0, 0);
        Boat testBoat2 = new PlayerBoat(0, 0);
        all_boats.add(testBoat);
        all_boats.add(testBoat2);

        // Create the race instance, this will position the boats on their lanes
        BoatRace testRace = new BoatRace(all_boats);
        // Move a boat to a position that is definitely outside the lane
        all_boats.get(0).getSprite().setPosition(-testRace.getLane_width() * 2, testRace.getEnd_y() / 2);
        // Run a step of the race
        testRace.runStep();

        // After the step is ran, the first boat should be penalised, while the second one shouldn't
        assertTrue(all_boats.get(0).getTimeToAdd() > 0);
        assertTrue(all_boats.get(1).getTimeToAdd() == 0);
    }

    public void testRunStep() {

    }
}