package de.tomgrill.gdxtesting;

import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class EffectTest extends TestCase {
    SpeedEffect test_speed;
    RepairEffect test_repair;
    ManeuverabilityEffect test_maneuverability;
    StaminaEffect test_stamina;
    InvulnerabilityEffect test_invulnerability;
    Boat test_boat;

    /**
     * This method is called before each test to create new instances of effects
     */
    @Before
    public void init() {
        test_speed = new SpeedEffect();
        test_repair = new RepairEffect();
        test_maneuverability = new ManeuverabilityEffect();
        test_stamina = new StaminaEffect();
        test_invulnerability = new InvulnerabilityEffect();
        test_boat = new PlayerBoat(0, 0);
    }

    /**
     * This method is called after each test to dispose of assets
     */
    @After
    public void dispose() {
        test_speed.dispose();
        test_repair.dispose();
        test_maneuverability.dispose();
        test_stamina.dispose();
        test_invulnerability.dispose();
        test_boat.dispose();
    }


    /** id: EffectsTest01
     *  description: tests if the duration of each effect decreases
     *  input data: new instance of each effect, with the default duration value of 5
     *  expected outcome: each effect's duration is lower than 5
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testEffectDurationDecreases() {
        // Apply each of the effect to update their timer
        test_speed.applyEffect(test_boat);
        test_repair.applyEffect(test_boat);
        test_maneuverability.applyEffect(test_boat);
        test_stamina.applyEffect(test_boat);
        test_invulnerability.applyEffect(test_boat);

        // The timer of each effect should be reduced
        assertTrue(test_speed.getDuration() < 5f);
        assertTrue(test_repair.getDuration() < 5f);
        assertTrue(test_maneuverability.getDuration() < 5f);
        assertTrue(test_stamina.getDuration() < 5f);
        assertTrue(test_invulnerability.getDuration() < 5f);
    }

    /** id: EffectsTest02
     *  description: tests if the effects become inactive after the end of the duration
     *  input data: new instance of each effect, with duration 0
     *  expected outcome: each effect becomes inactive after applying the effect
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testEffectBecomesInactive() {
        // Set the duration of each boost to 0
        test_speed.setDuration(0f);
        test_repair.setDuration(0f);
        test_maneuverability.setDuration(0f);
        test_stamina.setDuration(0f);
        test_invulnerability.setDuration(0f);

        // Apply each of the effect to update their timer
        test_speed.applyEffect(test_boat);
        test_repair.applyEffect(test_boat);
        test_maneuverability.applyEffect(test_boat);
        test_stamina.applyEffect(test_boat);
        test_invulnerability.applyEffect(test_boat);

        // All boosts should now be inactive
        assertFalse(test_speed.isIs_active());
        assertFalse(test_repair.isIs_active());
        assertFalse(test_maneuverability.isIs_active());
        assertFalse(test_stamina.isIs_active());
        assertFalse(test_invulnerability.isIs_active());
    }

    /** id: EffectsTest03
     *  description: tests if the speed boost correctly affects a boat
     *  input data: new instance of a PlayerBoat that is affected by a speed boost
     *  expected outcome: max speed and acceleration should be increased
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testSpeedBoostEffect() {
        // Save the old values of the boat's stats
        float old_max_speed = test_boat.getMax_speed();
        float old_acceleration = test_boat.getAcceleration();

        // Apply the effect to the boost
        test_speed.applyEffect(test_boat);

        // Now the stats of the boat should be better
        assertTrue(test_boat.getMax_speed() > old_max_speed);
        assertTrue(test_boat.getAcceleration() > old_acceleration);
    }

    /** id: EffectsTest04
     *  description: tests if the durability boost correctly affects a boat
     *  input data: new instance of a PlayerBoat, with low durability, that is affected by a durability boost
     *  expected outcome: the boats durability should increase
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testRepairBoostEffect() {
        // Set the durability of the boost really low so we can test if it increases
        test_boat.setDurability(0.1f);

        // Apply the effect to the boost
        test_repair.applyEffect(test_boat);

        // Now the durability of the boat should be higher
        assertTrue(test_boat.getDurability() > 0.1f);
    }

    /** id: EffectsTest05
     *  description: tests if the maneuverability boost correctly affects a boat
     *  input data: new instance of a PlayerBoat that is affected by a maneuverability boost
     *  expected outcome: maneuverability should be increased
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testManeuverabilityBoostEffect() {
        // Save the old value of maneuverability
        float old_maneuverability = test_boat.getManeuverability();

        // Apply the effect to the boost
        test_maneuverability.applyEffect(test_boat);

        // Now the stats of the boat should be better
        assertTrue(test_boat.getManeuverability() > old_maneuverability);
    }

    /** id: EffectsTest06
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

        // Apply the effect to the boost
        test_stamina.applyEffect(test_boat);

        // Now the stamina of the boat should be higher
        assertTrue(test_boat.getStamina() > 0.1f);
    }

    /** id: EffectsTest07
     *  description: tests if the invulnerability boost correctly affects a boat
     *  input data: new instance of a PlayerBoat that is affected by a invulnerability boost
     *  expected outcome: durability lost per hit should be 0
     *  requirements: UR_PICKUP_BOOSTS, FR_PICKUP_BOOSTS
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testInvulnerabilityBoostEffect() {
        // Apply the effect to the boost
        test_invulnerability.applyEffect(test_boat);

        // Now the durability lost per hit of the boat should be 0
        assertEquals(test_boat.getDurability_per_hit(), 0f);
    }

    /** id: EffectTest08
     *  description: tests if the stats of a boat are correctly reset after the end of each effect
     *  input data: new instance of a PlayerBoat, new effects
     *  expected outcome: there stats of the boat should return to normal after a boost ends
     *  requirements: UR_PICKUP_BOOST, FR_PICKUP_BOOST
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testResetBoatAfterEffects() {
        // Set the duration of each boost to 0
        test_speed.setDuration(0f);
        test_repair.setDuration(0f);
        test_maneuverability.setDuration(0f);
        test_stamina.setDuration(0f);
        test_invulnerability.setDuration(0f);

        // Save the stats of the boat
        test_boat.setStats(0);
        float maneuverability = test_boat.getManeuverability();
        float max_speed = test_boat.getMax_speed();
        float acceleration = test_boat.getAcceleration();

        // Apply a each effect and test if the boat is still affected even thought the effect has ended
        test_speed.applyEffect(test_boat);
        assertEquals(test_boat.getMax_speed(), max_speed);
        assertEquals(test_boat.getAcceleration(), acceleration);

        test_maneuverability.applyEffect(test_boat);
        assertEquals(test_boat.getManeuverability(), maneuverability);

        test_invulnerability.applyEffect(test_boat);
        assertTrue(test_boat.getMax_speed() > 0f);
    }

    /** id: EffectTest09
     *  description: tests if the boat is affected only active effects
     *  input data: new instance of a PlayerBoat, new effects
     *  expected outcome: only the active effects should affect the boat
     *  requirements: UR_PICKUP_BOOST, FR_PICKUP_BOOST
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testOnlyActiveEffectsApply() {
        // Add two effects to the boat, one of which i inactive
        test_invulnerability.setIs_active(false);
        test_boat.getEffects().add(test_maneuverability);
        test_boat.getEffects().add(test_invulnerability);

        float old_maneuverability = test_boat.getManeuverability();

        // Update the effects
        test_boat.updateEffects();

        // Only the maneuverability effect should be applied
        assertTrue(test_boat.getManeuverability() > old_maneuverability);
        assertTrue(test_boat.getDurability_per_hit() > 0f);
    }

    /** id: EffectTest10
     *  description: tests if the effects on a boat get removed from the list after they become inactive
     *  input data: new instance of a PlayerBoat, new effects
     *  expected outcome: only the active effects should affect the boat
     *  requirements: UR_PICKUP_BOOST, FR_PICKUP_BOOST
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testEffectsGetRemoved() {
        // Add an inactive effect to the boat
        test_invulnerability.setIs_active(false);
        test_boat.getEffects().add(test_invulnerability);

        // Update the effects
        test_boat.updateEffects();

        // The list of effects should be empty
        assertTrue(test_boat.getEffects().isEmpty());
    }
}
