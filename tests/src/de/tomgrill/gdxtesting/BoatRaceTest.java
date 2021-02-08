package de.tomgrill.gdxtesting;


import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


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
        testRace = testSceneMainGame.getRace();
    }

    @After
    public void dispose() {
        testSceneMainGame.dispose();
        testSceneMainGame = null;
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

    /** id: BoatRaceTest06
     *  description: tests if boats are marked to have started the leg when they pass the start line
     *  input data: new instance of BoatRace with the position above the start line
     *  expected outcome: boat should have the attribute has_started_leg true
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testBoatsStartsLeg() {
        // Set the position of each boat above the start line
        for (Boat b: testRace.getBoats())
            b.getSprite().setPosition(0f, testRace.getStart_y() + 100f);

        // Run a step of the race so that status of boats is updated
        testRace.runStep();

        // All boats should be marked to have started the leg
        for (Boat b : testRace.getBoats())
            assertTrue(b.hasStartedLeg());
    }

    /** id: BoatRaceTest07
     *  description: tests if boats are marked to have ended the leg when they pass the finish line
     *  input data: new instance of BoatRace with the position above the finish line
     *  expected outcome: boat should have the attribute has_finished_leg true
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testBoatsEndsLeg() {
        // Set the position of each boat above the finish line
        for (Boat b: testRace.getBoats())
            b.getSprite().setPosition(0f, testRace.getEnd_y() + 100);

        // Run a step of the race so that status of boats is updated
        testRace.runStep();

        // All boats should be marked to have ended the leg and their leg times should be updated
        for (Boat b : testRace.getBoats()) {
            assertTrue(b.hasFinishedLeg());
            assertFalse(b.getLegTimes().isEmpty());
        }
    }

    /** id: BoatRaceTest07
     *  description: tests if the race ends after too much time by disqualifying all boats
     *  input data: new instance of BoatRace with the number of frames raced above the dnf time
     *  expected outcome: boat should have the attribute has_finished_leg true
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testDNFAfter2mins() {
        // Set the total frames high enough to dnf all boats
        testRace.setTotal_frames(testRace.getDnf_time() + 1);

        // Run a step of the race
        testRace.runStep();

        assertTrue(testRace.isFinished());
        // All boats should be marked to have ended the leg and their leg times should be updated
        for (Boat b : testRace.getBoats()) {
            assertTrue(b.hasFinishedLeg());
            assertFalse(b.getLegTimes().isEmpty());
        }
    }

    /** id: BoatRaceTest08
     *  description: tests if the getSprites method returns a valid list
     *  input data: new instance of BoatRace
     *  expected outcome: the getSprite method shouldn't return an empty list
     *  requirements: UR_OBSTACLE, FR_OBSTACLES
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testGetSpritesReturnsNonEmpty() {
        assertFalse(testRace.getSprites().isEmpty());
    }
}