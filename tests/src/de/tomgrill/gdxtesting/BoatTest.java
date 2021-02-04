package de.tomgrill.gdxtesting;

import com.teamonehundred.pixelboat.Boat;
import com.teamonehundred.pixelboat.PlayerBoat;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class BoatTest extends TestCase {

    Boat test_boat;

    /**
     * This method is called before each test to create a new boat to apply the tests to
     */
    @Before
    public void init() {
        test_boat = new PlayerBoat(0, 0);
    }

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
     *  description: tests if the speed boost correctly affects a boat
     *  input data: new instance of a PlayerBoat that is affected by a speed boost
     *  expected outcome: max speed and acceleration should be increased
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testSpeedBoostEffect() {
        // Add a speed boost effect to the boat
        Float[] effect = new Float[] {1f, 5f};
        test_boat.getEffects().add(effect);
        float old_max_speed = test_boat.getMax_speed();
        float old_acceleration = test_boat.getAcceleration();

        // Apply the effect of the boost
        test_boat.updateBoostEffect();

        // Now the stats of the boat should be better
        assertTrue(test_boat.getMax_speed() > old_max_speed);
        assertTrue(test_boat.getAcceleration() > old_acceleration);
    }

    /** id: BoatTest07
     *  description: tests if the durability boost correctly affects a boat
     *  input data: new instance of a PlayerBoat, with low durability, that is affected by a durability boost
     *  expected outcome: the boats durability should increase
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testRobustnessBoostEffect() {
        // Set the durability of the boost really low so we can test if it increases
        test_boat.setDurability(0.1f);

        // Add a robustness boost effect to the boat
        Float[] effect = new Float[] {2f, 5f};
        test_boat.getEffects().add(effect);

        // Apply the effect of the boost
        test_boat.updateBoostEffect();

        // Now the durability of the boat should be higher
        assertTrue(test_boat.getDurability() > 0.1f);
    }

    /** id: BoatTest08
     *  description: tests if the maneuverability boost correctly affects a boat
     *  input data: new instance of a PlayerBoat that is affected by a maneuverability boost
     *  expected outcome: maneuverability should be increased
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testManeuverabilityBoostEffect() {
        // Add a maneuverability boost effect to the boat
        Float[] effect = new Float[] {3f, 5f};
        test_boat.getEffects().add(effect);
        float old_maneuverability = test_boat.getManeuverability();

        // Apply the effect of the boost
        test_boat.updateBoostEffect();

        // Now the stats of the boat should be better
        assertTrue(test_boat.getManeuverability() > old_maneuverability);
    }

    /** id: BoatTest09
     *  description: tests if the stamina boost correctly affects a boat
     *  input data: new instance of a PlayerBoat that is affected by a stamina boost
     *  expected outcome: stamina should be increased
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testStaminaBoostEffect() {
        // Set the stamina of the boost really low so we can test if it increases
        test_boat.setStamina(0.1f);

        // Add a stamina boost effect to the boat
        Float[] effect = new Float[] {4f, 5f};
        test_boat.getEffects().add(effect);

        // Apply the effect of the boost
        test_boat.updateBoostEffect();

        // Now the stamina of the boat should be higher
        assertTrue(test_boat.getStamina() > 0.1f);
    }

    /** id: BoatTest08
     *  description: tests if the invulnerability boost correctly affects a boat
     *  input data: new instance of a PlayerBoat that is affected by a invulnerability boost
     *  expected outcome: durability lost per hit should be 0
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testInvulnerabilityBoostEffect() {
        // Add a invulnerability boost effect to the boat
        Float[] effect = new Float[] {5f, 5f};
        test_boat.getEffects().add(effect);

        // Apply the effect of the boost
        test_boat.updateBoostEffect();

        // Now the durability lost per hit of the boat should be 0
        assertEquals(test_boat.getDurability_per_hit(), 0f);
    }

}
