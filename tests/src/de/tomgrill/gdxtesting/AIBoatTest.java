package de.tomgrill.gdxtesting;

import com.teamonehundred.pixelboat.AIBoat;
import com.teamonehundred.pixelboat.CollisionObject;
import com.teamonehundred.pixelboat.Obstacle;
import com.teamonehundred.pixelboat.ObstacleBranch;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(GdxTestRunner.class)
public class AIBoatTest extends TestCase {

    List<CollisionObject> obstacles;
    AIBoat test_ai;
    @Before
    public void init() {
        obstacles = new ArrayList<>();
        test_ai = new AIBoat(0, 0);
    }

    @Test
    public void testAIBoatDodgeObstacle() {
        // Create an obstacle right in front of the AI boat and add it to a list
        Obstacle obstacle = new ObstacleBranch(0, 110);
        obstacles.add(obstacle);

        // Save the current rotation of the AI
        float rotation = test_ai.getSprite().getRotation();

        // Update the AI
        test_ai.updatePosition(obstacles);

        // The rotation of the AI should be modified
        assertTrue(test_ai.getSprite().getRotation() != rotation);
    }

    @Test
    public void testAIBoatDodgeObstacleRange() {
        // Create an obstacle outside of the AI's range and add it to a list
        Obstacle obstacle = new ObstacleBranch(0, 500);
        List<CollisionObject> obstacles = new ArrayList<>();
        obstacles.add(obstacle);

        // Save the current rotation of the AI
        float rotation = test_ai.getSprite().getRotation();

        // Update the AI
        test_ai.updatePosition(obstacles);

        // The rotation of the AI shouldn't change
        assertTrue(test_ai.getSprite().getRotation() == rotation);
    }

    @Test
    public void testAIBoatAccelerate() {
        // Save the current speed of the AI
        float speed = test_ai.getSpeed();

        // Update the AI
        test_ai.updatePosition(obstacles);

        // The speed of the AI should increase
        assertTrue(test_ai.getSpeed() > speed);
    }

    @Test
    public void testAIBoatStopsAccelerate() {
        // Set the stamina of the AI very low so it doesn't accelerate
        test_ai.setStamina(0.05f);

        // Save the current speed of the AI
        float speed = test_ai.getSpeed();

        // Update the AI
        test_ai.updatePosition(obstacles);

        // The AI's regen status should be true
        assertTrue(test_ai.isRegen());
    }

    @Test
    public void testAIBoatRegens() {
        // Set the regen variable to true and a non-zero speed
        test_ai.setRegen(true);
        test_ai.setSpeed(5f);

        // Update the AI
        test_ai.updatePosition(obstacles);

        // The AI's speed shouldn't be higher
        assertTrue(test_ai.getSpeed() <= 5f);
    }

    @Test
    public void testAIBoatRestartsAccelerating() {
        // Set the regen variable to true and a non-zero speed
        test_ai.setRegen(true);
        test_ai.setSpeed(5f);

        // Set the stamina to be higher than the threshold
        test_ai.setStamina(0.6f);

        // Update the AI
        test_ai.updatePosition(obstacles);

        // The AI's regen status should be false
        assertFalse(test_ai.isRegen());
    }
}
