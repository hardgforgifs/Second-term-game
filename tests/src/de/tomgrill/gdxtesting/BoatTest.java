package de.tomgrill.gdxtesting;

import com.badlogic.gdx.math.Vector3;
import com.teamonehundred.pixelboat.Boat;
import com.teamonehundred.pixelboat.PlayerBoat;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class BoatTest extends TestCase {

    PlayerBoat test_boat;

    /**
     * This method is called before each test to create a new boat to apply the tests to
     */
    @Before
    public void init() {
        test_boat = new PlayerBoat(0, 0);
    }

    /**
     * This method is called after each test to dispose of assets
     */
    @After
    public void dispose() { test_boat.finalize(); }

    /** id: BoatTest01
     *  description: tests if boat's stamina decreases when it accelerates
     *  input data: new instance of a boat
     *  expected outcome: stamina after accelerating should be lower than before accelerating
     *  requirements: FR_STAM_USAGE
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testStaminaDecrease() {
        // Save the starting stamina
        float startStamina = test_boat.getStamina();

        // Accelerate
        test_boat.accelerate();

        // After accelerating, the stamina should decrease
        assertTrue(startStamina > test_boat.getStamina());
    }

    /** id: BoatTest02
     *  description: tests if boat's stamina regen when it stops accelerating
     *  input data: new instance of a boat, with 0 stamina
     *  expected outcome: stamina after updating the boat should be higher than before
     *  requirements: FR_STAM_USAGE
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testStaminaRegen() {
        // Set the boat's stamina to 0
        test_boat.setStamina(0f);
        test_boat.setStamina_delay(test_boat.getTime_to_recover() + 10);

        // Update the position and the stamina of the boat
        test_boat.updatePosition();

        // After the update, the stamina should have increased
        assertTrue(test_boat.getStamina() > 0);
    }

    /** id: BoatTest03
     *  description: tests the correct input processing for the player's boat when the W button is pressed
     *  input data: new instance of a PlayerBoat
     *  expected outcome: speed should be affected by the button
     *  requirements: UR_PLAYABILITY, FR_PLAYABILITY
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testBoatAccelerate() {
        // Save the starting speed
        float speed = test_boat.getSpeed();

        // Accelerate
        test_boat.accelerate();

        // The speed of the boat should be greater after accelerating
        assertTrue(test_boat.getSpeed() > speed);
    }

    /** id: BoatTest04
     *  description: tests the correct input processing for the player's boat when the D button is pressed
     *  input data: new instance of a PlayerBoat
     *  expected outcome: rotation should be affected by the button
     *  requirements: UR_PLAYABILITY, FR_PLAYABILITY
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testTurningRight() {
        // Save the current rotation
        float rotation = test_boat.getSprite().getRotation();

        // Rotate right
        test_boat.turn(-1);

        // The rotation of the boat should be smaller than the initial rotation
        assertTrue(test_boat.getSprite().getRotation() < rotation);
    }

    /** id: BoatTest05
     *  description: tests the correct input processing for the player's boat when the A button is pressed
     *  input data: new instance of a PlayerBoat
     *  expected outcome: rotation should be affected by the button
     *  requirements: UR_PLAYABILITY, FR_PLAYABILITY
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testTurningLeft() {
        // Save the current rotation
        float rotation = test_boat.getSprite().getRotation();

        // Rotate right
        test_boat.turn(1);

        // The rotation of the boat should be greater than the initial rotation
        assertTrue(test_boat.getSprite().getRotation() > rotation);
    }

    /** id: BoatTest06
     *  description: tests if the camera stays centered on the player
     *  input data: new instance of a PlayerBoat
     *  expected outcome: the difference between the position of the boat and the position of the player should stay
     *                    the same after moving the player
     *  requirements: FR_POV
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testCameraPOV() {
        // Save the position of the camera
        Vector3 camera_position = ((PlayerBoat)test_boat).getCamera().position;

        // Save the difference between the position of the camera and the position of the player
        float x_diff = camera_position.x - test_boat.getSprite().getX();
        float y_diff = camera_position.y - test_boat.getSprite().getY();

        // Accelerate and move the boat
        test_boat.accelerate();
        test_boat.updatePosition();

        // The difference between the camera and the player should still be the same
        camera_position = ((PlayerBoat)test_boat).getCamera().position;
        float new_x_diff = camera_position.x - test_boat.getSprite().getX();
        float new_y_diff = camera_position.y - test_boat.getSprite().getY();

        assertEquals(x_diff, new_x_diff);
        assertEquals(y_diff, new_y_diff);
    }

    /** id: BoatTest07
     *  description: tests if the speed of a boat is correctly reset
     *  input data: new instance of a PlayerBoat
     *  expected outcome: the speed of the boat should be 0 after resetting
     *  requirements: UR_BOAT_SPEC
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatResetSpeed() {
        // Add random values for speed
        test_boat.setSpeed(100);

        // Reset the boat
        test_boat.reset();

        // The speed should now be 0
        assertTrue(test_boat.getSpeed() == 0);
    }

    /** id: BoatTest08
     *  description: tests if the durability of a boat is correctly reset
     *  input data: new instance of a PlayerBoat
     *  expected outcome: the durability of the boat should be 0 after resetting
     *  requirements: UR_BOAT_SPEC
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatResetDurability() {
        // Add random values for durability
        test_boat.setDurability(100);

        // Reset the boat
        test_boat.reset();

        // The durability should now be 0
        assertTrue(test_boat.getDurability() == 1f);
    }

    /** id: BoatTest09
     *  description: tests if the stamina of a boat is correctly reset
     *  input data: new instance of a PlayerBoat
     *  expected outcome: the stamina of the boat should be 0 after resetting
     *  requirements: UR_BOAT_SPEC
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatResetStamina() {
        // Add random values for stamina
        test_boat.setStamina(100);

        // Reset the boat
        test_boat.reset();

        // The stamina should now be 0
        assertTrue(test_boat.getStamina() == 1f);
    }

    /** id: BoatTest10
     *  description: tests if the added time of a boat is correctly reset
     *  input data: new instance of a PlayerBoat
     *  expected outcome: the added time of the boat should be 0 after resetting
     *  requirements: UR_BOAT_SPEC
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatResetAddedTime() {
        // Add random values for added time
        test_boat.setTimeToAdd(100);

        // Reset the boat
        test_boat.reset();

        // The added time should now be 0
        assertTrue(test_boat.getTimeToAdd() == 0);
    }

    /** id: BoatTest11
     *  description: tests if the boat's getBestTime method returns the right time
     *  input data: new instance of a PlayerBoat, new values for leg times
     *  expected outcome: the ethod should return the greatest leg time
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testGetBestLeg() {
        // Add leg times to the boat's list of leg times
        test_boat.getLegTimes().add(100L);
        test_boat.getLegTimes().add(200L);
        test_boat.getLegTimes().add(50L);

        assertEquals(50L, test_boat.getBestTime());
    }
}
