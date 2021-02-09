package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(GdxTestRunner.class)
public class PixelBoatTest extends TestCase {
    static PixelBoat testGame;
    SceneMainGame testSceneMainGame;
    static Random random;

    @BeforeClass
    public static void initialise() {
        testGame = new PixelBoat();
        testGame.setPref(Gdx.app.getPreferences("saveTest"));
        random = new Random();
    }

    /**
     * This method is called before each test to initialize a new game to be tested
     */
    @Before
    public void init () {
        // Create a new instance of the game
        testSceneMainGame = new SceneMainGame();
    }

    @After
    public void dispose() {
        testSceneMainGame.dispose();
    }

    /**
     * This method saves the game, then loads the game using the same variables
     * The contents of testSceneMainGame after calling this method is the loaded game state
     */
    private void reload() {
        // Save the game state
        testGame.saveGame(testSceneMainGame);

        // Reset the game
        dispose();
        testSceneMainGame = new SceneMainGame();

        // Load the game state that was saved
        testSceneMainGame = testGame.loadGame();
    }

    /** id: PixelBoatTest01
     *  description: tests if the leg number persists after save
     *  input data: a random leg number <5
     *  expected_outcome: the leg number that is saved is the same as the one that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveLegNumber() {
        // Assign a random leg number
        int legNumber = random.nextInt(5);

        testSceneMainGame.setLeg_number(legNumber);

        // Save and load the game state
        reload();

        // The leg number from the newly loaded game state should be the same as the one that was saved
        int loadedLegNumber = testSceneMainGame.getLeg_number();
        assertEquals(loadedLegNumber, legNumber);

    }

    /** id: PixelBoatTest02
     *  description: tests if the boat specification persists after save
     *  input data: a spec_id to give the player boat
     *  expected outcome: the spec_id that is saved is the same as the one that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatsSpecID() {
        // Assign a random specID for each boat
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            int specID = random.nextInt(5);
            testSceneMainGame.getAllBoats().get(i).setSpec(specID);
        }
        // Save the boats so their specs can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();
        // The specs of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getSpec_id(),
                    boats.get(i).getSpec_id());
        }
    }

    /** id: PixelBoatTest03
     *  description: tests if the camera position persists after save
     *  input data: a random position to give the camera
     *  expected outcome: the camera position that is saved is the same as the one that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveCameraPosition() {
        // Assign a random camera position, with x in y in range of (0,100)
        float cameraX = random.nextFloat() * 100f;
        float cameraY = random.nextFloat() * 100f;

        testSceneMainGame.getPlayer().getCamera().position.x = cameraX;
        testSceneMainGame.getPlayer().getCamera().position.y = cameraY;

        // Save and load the game state
        reload();

        // The camera position from the loaded game should be the same as the one that was saved
        float loadedCameraX = testSceneMainGame.getPlayer().getCamera().position.x;
        float loadedCameraY = testSceneMainGame.getPlayer().getCamera().position.y;
        assertTrue(loadedCameraX == cameraX && loadedCameraY == cameraY);
    }

    /** id: PixelBoatTest04
     *  description: tests if the boat durability persists after save
     *  input data: a random durability value to give to each boat
     *  expected outcome: the durability that is saved is the same as the one that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatsDurability() {
        // Assign a random durability for each boat
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            float durability = random.nextFloat();
            testSceneMainGame.getAllBoats().get(i).setDurability(durability);
        }
        // Save the boats so their specs can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The durability of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getDurability(),
                    boats.get(i).getDurability());
        }
    }

    /** id: PixelBoatTest05
     *  description: tests if the boat time to recover persists after save
     *  input data: a random time to recover value to give to each boat
     *  expected outcome: the time to recover that is saved is the same as the one that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatsTimeToRecover() {
        // Assign a random time to recover for each boat
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            int time_to_recover = random.nextInt(500);
            testSceneMainGame.getAllBoats().get(i).setTime_to_recover(time_to_recover);
        }
        // Save the boats so their specs can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The time to recover of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getTime_to_recover(),
                    boats.get(i).getTime_to_recover());
        }
    }

    /** id: PixelBoatTest06
     *  description: tests if the boats location persists after save
     *  input data: random location for each boat
     *  expected outcome: the boats location that is saved is the same as the ones that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatPosition() {
        // Assign a random location for each boat, with a limit of 100f for both x and y
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            float x = random.nextFloat() * 100f;
            float y = random.nextFloat() * 100f;
            testSceneMainGame.getAllBoats().get(i).getSprite().setPosition(x, y);
        }
        // Save the boats so their positions can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The location of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getSprite().getX(),
                    boats.get(i).getSprite().getX());
            assertEquals(testSceneMainGame.getAllBoats().get(i).getSprite().getY(),
                    boats.get(i).getSprite().getY());
        }

    }

    /** id: PixelBoatTest07
     *  description: tests if the boats rotation persists after save
     *  input data: random rotation for each boat
     *  expected outcome: the boats rotation that is saved is the same as the ones that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatRotation() {
        // Assign a random rotation for each boat, with a limit of 100f for both x and y
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            float rotation = random.nextFloat();
            testSceneMainGame.getAllBoats().get(i).getSprite().setRotation(rotation);
        }
        // Save the boats so their rotations can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The rotation of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getSprite().getRotation(),
                    boats.get(i).getSprite().getRotation());
        }
    }

    /** id: PixelBoatTest08
     *  description: tests if the boats speed persists after save
     *  input data: random speed for each boat
     *  expected outcome: the boats speed that is saved is the same as the ones that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatSpeed() {
        // Assign a random speed for each boat, with a limit of 100f
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            float speed = random.nextFloat() * 100f;
            testSceneMainGame.getAllBoats().get(i).setSpeed(speed);
        }
        // Save the boats so their rotations can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The rotation of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getSpeed(),
                    boats.get(i).getSpeed());
        }
    }

    /** id: PixelBoatTest09
     *  description: tests if the boats stamina persists after save
     *  input data: random stamina for each boat
     *  expected outcome: the boats stamina that is saved is the same as the ones that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveStamina() {
        // Assign a random stamina value for each boat (range 0 to 1)
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            float stamina = random.nextFloat();
            testSceneMainGame.getAllBoats().get(i).setStamina(stamina);
        }
        // Save the boats so their stamina can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The stamina of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getStamina(), boats.get(i).getStamina());
        }
    }

    /** id: PixelBoatTest10
     *  description: tests if the boats penalty time persists after save
     *  input data: random penalty time for each boat
     *  expected outcome: the boats penalty time that is saved is the same as the ones that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatPenaltyTime() {
        // Assign a penalty time value for each boat
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            long time_to_add = random.nextLong();
            testSceneMainGame.getAllBoats().get(i).setTimeToAdd(time_to_add);
        }
        // Save the boats so their times can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The penalty time of each boat from the newly loaded game state should be the same as the ones that were saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getTimeToAdd(), boats.get(i).getTimeToAdd());
        }
    }

    /** id: PixelBoatTest11
     *  description: tests if the boats frames raced persists after save
     *  input data: random frames raced for each boat
     *  expected outcome: the boats frames raced that is saved is the same as the ones that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatFramesRaced() {
        // Assign a random raced frames value for each boat
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            long frames_raced = random.nextLong();
            testSceneMainGame.getAllBoats().get(i).setFramesRaced(frames_raced);
        }
        // Save the boats so they can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The frames raced of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getFramesRaced(), boats.get(i).getFramesRaced());
        }
    }

    /** id: PixelBoatTest12
     *  description: tests if the boats leg status persists after save
     *  input data: true values for both has_started_leg and has_finished_leg
     *  expected outcome: both has_started_leg and has_finished_leg should be true after loading
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveLegStatus() {
        // Assign both has_finished_leg and has_started_leg to true, which is the non-default value
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            testSceneMainGame.getAllBoats().get(i).setHasStartedLeg(true);
            testSceneMainGame.getAllBoats().get(i).setHasFinishedLeg(true);
        }

        // Save and load the game state
        reload();

        // Both has_finished_leg and has_started_leg of each boat should be true after loading
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertTrue(testSceneMainGame.getAllBoats().get(i).hasStartedLeg());
            assertTrue(testSceneMainGame.getAllBoats().get(i).hasFinishedLeg());

        }
    }

    /** id: PixelBoatTest13
     *  description: tests if the boats leg times persists after save
     *  input data: random leg times for each boat
     *  expected outcome: the boats leg times that are saved are the same as the ones that are loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveLegTimes() {
        // Assign random leg times for each boat
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            for (int k = 0; k < 3; k++) {
                // To set the leg time we need to generate end_time and start_time
                long startTime = random.nextLong();
                long endTime = random.nextLong();
                testSceneMainGame.getAllBoats().get(i).setStartTime(startTime);
                testSceneMainGame.getAllBoats().get(i).setEndTime(endTime);
                testSceneMainGame.getAllBoats().get(i).setLegTime();
            }

        }
        // Save the boats so they can be compared later
        List<Boat> boats = testSceneMainGame.getAllBoats();

        // Save and load the game state
        reload();

        // The leg times of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertEquals(testSceneMainGame.getAllBoats().get(i).getLegTimes(), boats.get(i).getLegTimes());
        }
    }

    /** id: PixelBoatTest14
     *  description: tests if the boat stamina delay persists after save
     *  input data: a random stamina delay value to give to each boat
     *  expected outcome: the stamina delay that is saved is the same as the one that is loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
//    @Test
//    public void testSaveBoatsStaminaDelay() {
//        // Assign a random stamina delay for each boat
//        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
//            int stamina_delay = random.nextInt(500);
//            testSceneMainGame.getAllBoats().get(i).setStamina_delay(stamina_delay);
//        }
//
//        // Save the boats so their specs can be compared later
//        List<Boat> boats = testSceneMainGame.getAllBoats();
//
//        // Save and load the game state
//        reload();
//
//        // The time to recover of each boat from the newly loaded game state should be the same as the one that was saved
//        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
//            assertEquals(testSceneMainGame.getAllBoats().get(i).getStamina_delay(),
//                    boats.get(i).getStamina_delay());
//        }
//    }

    /** id: PixelBoatTest15
     *  description: tests if the boat recovery status delay persists after save
     *  input data: recovery status is set to true, the non-default value
     *  expected outcome: the recovery of all boats should be true after loading
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testSaveBoatsRecovering() {
        // Assign a recovery to true for each boat
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            testSceneMainGame.getAllBoats().get(i).setRecovering(true);
        }
        // Save and load the game state
        reload();

        // The time to recover of each boat from the newly loaded game state should be the same as the one that was saved
        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
            assertTrue(testSceneMainGame.getAllBoats().get(i).isRecovering());
        }
    }

    /** id: PixelBoatTest16
     *  description: tests if the boats difficulty persists after save
     *  input data: random leg times for each boat
     *  expected outcome: the boats leg times that are saved are the same as the ones that are loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
//    @Test
//    public void testSaveDifficulty() {
//        // Assign a difficulty value for the player boat boat
//        int difficulty = random.nextInt(3);
//        testSceneMainGame.getPlayer().setDifficulty(difficulty);
//
//        // Save and load the game state
//        reload();
//
//        // The difficulty of the player boat from the newly loaded game state
//        // should be the same as the one that was saved
//        assertEquals(testSceneMainGame.getPlayer().getDifficulty(), difficulty);
//    }

    /** id: PixelBoatTest17
     *  description: tests if the obstacles persists after save
     *  input data: no input other than the newly started game
     *  expected outcome: the obstacles that are saved are the same as the ones that are loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
//    @Test
//    public void testSaveObstacleLocationAndType() {
//        // Save the list of the obstacles so they can be compared later
//        List<CollisionObject> obstacles = new ArrayList<>(testSceneMainGame.getRace().getObstacles());
//
//        // Remove the lane separators since those aren't saved, so we shouldn't compare them
//        for (int k = 0; k < obstacles.size(); k++) {
//            Obstacle obstacle = (Obstacle) obstacles.get(k);
//            if(obstacle.getClass().getName().equals("com.teamonehundred.pixelboat.ObstacleLaneWall")) {
//                obstacles.remove(k);
//            }
//
//        }
//
//        // Save and load the game state
//        reload();
//        List<CollisionObject> loadedObstacles = new ArrayList<>(testSceneMainGame.getRace().getObstacles());
//
//        // Remove the lanewalls from the list of loaded obstacles since those aren't loaded from the savefile
//        for (int k = 0; k < loadedObstacles.size(); k++) {
//            Obstacle obstacle = (Obstacle) loadedObstacles.get(k);
//            if(obstacle.getClass().getName().equals("com.teamonehundred.pixelboat.ObstacleLaneWall")) {
//                loadedObstacles.remove(k);
//            }
//
//        }
//
//        // For every obstacles, the x and y position, as well as the type(class)
//        // should be the same as the one that was saved
//        for (int k = 0; k < obstacles.size(); k++) {
//            assertEquals(((Obstacle) obstacles.get(0)).getSprite().getX(),
//                    ((Obstacle) loadedObstacles.get(0)).getSprite().getX());
//
//            assertEquals(((Obstacle) obstacles.get(0)).getSprite().getY(),
//                    ((Obstacle) loadedObstacles.get(0)).getSprite().getY());
//
//            assertSame(((Obstacle) obstacles.get(0)).getClass(),
//                    ((Obstacle) loadedObstacles.get(0)).getClass());
//        }
//    }

    /** id: PixelBoatTest18
     *  description: tests if the boats start time and end time persists after save
     *  input data: random start time and end time for each boat
     *  expected outcome: the boats start time and end time that are saved are the same as the ones that are loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
//    @Test
//    public void testSaveBoatRaceTimes() {
//        // Assign a start and end time value for each boat (range 0 to 1)
//        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
//            long startTime = random.nextLong();
//            long endTime = random.nextLong();
//            testSceneMainGame.getAllBoats().get(i).setStartTime(startTime);
//            testSceneMainGame.getAllBoats().get(i).setEndTime(endTime);
//        }
//        // Save the boats so their times can be compared later
//        List<Boat> boats = testSceneMainGame.getAllBoats();
//
//        // Save and load the game state
//        reload();
//
//        // The start time and end time of each boat from the newly loaded game state
//        // should be the same as the one that was saved
//        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
//            assertEquals(testSceneMainGame.getAllBoats().get(i).getStartTime(false),
//                    boats.get(i).getStartTime(false));
//            assertEquals(testSceneMainGame.getAllBoats().get(i).getEndTime(false),
//                    boats.get(i).getEndTime(false));
//        }
//    }

    /** id: PixelBoatTest19
     *  description: tests if the powerups persists after save
     *  input data: no input other than the newly started game
     *  expected outcome: the powerups that are saved are the same as the ones that are loaded
     *  requirements: UR_SAVE_GAME, FR_SAVE_GAME
     *  category: white box testing
     *  @author: Dragos Stoican
     */
//    @Test
//    public void testSavePowerupsLocation() {
//        // Save the list of the powerups so they can be compared later
//        List<PowerUp> powerups = new ArrayList<>(testSceneMainGame.getRace().getPowerups());
//
//        // Save and load the game state
//        reload();
//        List<PowerUp> loadedPowerups = new ArrayList<>(testSceneMainGame.getRace().getPowerups());
//
//        // For every powerup, the x and y position should be the same as the one that was saved
//        for (int k = 0; k < powerups.size(); k++) {
//            assertEquals(powerups.get(0).getSprite().getX(), loadedPowerups.get(0).getSprite().getX());
//            assertEquals(powerups.get(0).getSprite().getY(), loadedPowerups.get(0).getSprite().getY());
//        }
//    }

//    @Test
//    public void testSaveBoatEffects() {
//        // Assign random effects for each boat
//        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
//            // Generate 3 random effects
//            for (int k = 0; k < 3; k++) {
//                Effect random_effect = new PowerUp(0, 0).getRandomEffect();
//                testSceneMainGame.getAllBoats().get(i).getEffects().add(random_effect);
//            }
//
//        }
//        // Save the boats so they can be compared later
//        List<Boat> boats = testSceneMainGame.getAllBoats();
//
//        // Save and load the game state
//        reload();
//
//        // The current effects of each boat from the newly loaded game state should be the same
//        // as the one that was saved
//        for (int i = 0; i < testSceneMainGame.getBoats_per_race(); i++) {
//            for (int j = 0; j < boats.get(i).getEffects().size(); j++)
//                assertTrue(testSceneMainGame.getAllBoats().get(i).getEffects().get(j).equals(boats.get(i).getEffects().get(j)));
//        }
//    }
}
