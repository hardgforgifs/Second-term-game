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
    @Before
    public void init() {
        test_boat = new PlayerBoat(0, 0);
    }

    /** id: BoatTest01
     *  description: tests a boats stamina decreases when it accelerates
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

    @Test
    public void testStaminaRegen() {
        // Set the boat's stamina to 0
        test_boat.setStamina(0f);

        // Update the position and the stamina of the boat
        test_boat.updatePosition();

        // After the update, the stamina should have increased
        assertTrue(test_boat.getStamina() > 0);
    }

    /** id: BoatTest01
     *  description: tests the correct input processing for the player's boat
     *  input data: new instance of a PlayerBoat
     *  expected outcome: acceleration and rotation should be affected by the buttons
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

    @Test
    public void testTurningRight() {
        // Save the current rotation
        float rotation = test_boat.getSprite().getRotation();

        // Rotate right
        test_boat.turn(-1);

        // The rotation of the boat should be smaller than the initial rotation
        assertTrue(test_boat.getSprite().getRotation() < rotation);
    }

    @Test
    public void testTurningLeft() {
        // Save the current rotation
        float rotation = test_boat.getSprite().getRotation();

        // Rotate right
        test_boat.turn(1);

        // The rotation of the boat should be greater than the initial rotation
        assertTrue(test_boat.getSprite().getRotation() > rotation);
    }

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

//    @Test
//    public void testSpeedBoostDuration() {
//        // Add a speed boost to the boat
//        // The speed boost should have a very low duration so we can if it disappear after it's timer reaches 0
//        Float[] effect = new Float[] {1f, 0.001f};
//        test_boat.getEffects().add(effect);
//
//        // Apply the effect to the boat
//        test_boat.updateBoostEffect();
//
//        // Simulate a frame
//        test_boat.getEffects().get(0)[1] -= Gdx.graphics.getDeltaTime();
//
//
//    }

}
