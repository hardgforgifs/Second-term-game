package de.tomgrill.gdxtesting;

import com.badlogic.gdx.graphics.Texture;
import com.teamonehundred.pixelboat.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class CollisionsTest extends TestCase {
    Boat test_boat;
    ObstacleBranch obstacle;
    ObstacleDuck duck;
    ObstacleLaneWall lane_wall;
    PowerUp power_up;

    /**
     * This method is called before each test and creates a boat that is colliding with both an obstacle and a powerup
     */
    @Before
    public void init() {
        test_boat = new PlayerBoat(0, 0);
        obstacle = new ObstacleBranch(0, 0);
        duck = new ObstacleDuck(0, 0);
        lane_wall = new ObstacleLaneWall(0, 0);
        power_up = new PowerUp(0, 0 );
    }

    @After
    public void dispose() {
        test_boat.dispose();
        obstacle.dispose();
        duck.dispose();
        lane_wall.dispose();
        power_up.dispose();
    }

    /** id: CollisionsTest01
     *  description: tests if a duck obstacle collides properly with a boat
     *  input data: new instance of a PlayerBoat, new instance of duck obstacle
     *  expected outcome: the bounds if the two objects should collide
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatIsCollidingWithDuck() {
        assertTrue(test_boat.getBounds().isColliding(duck.getBounds()));
    }

    /** id: CollisionsTest02
     *  description: tests if a branch obstacle collides properly with a boat
     *  input data: new instance of a PlayerBoat, new instance of branch obstacle
     *  expected outcome: the bounds if the two objects should collide
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatIsCollidingWithBranch() {
        assertTrue(test_boat.getBounds().isColliding(obstacle.getBounds()));
    }

    /** id: CollisionsTest03
     *  description: tests if a lane wall obstacle collides properly with a boat
     *  input data: new instance of a PlayerBoat, new instance of lane wall obstacle
     *  expected outcome: the bounds if the two objects should collide
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatIsCollidingWithLaneWall() {
        assertTrue(test_boat.getBounds().isColliding(lane_wall.getBounds()));
    }

    /** id: CollisionsTest03
     *  description: tests if a powerup collides properly with a boat
     *  input data: new instance of a PlayerBoat, new instance of powerup
     *  expected outcome: the bounds if the two objects should collide
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testBoatIsCollidingWithPowerUp() {
        assertTrue(test_boat.getBounds().isColliding(power_up.getBounds()));
    }

    /** id: CollisionsTest03
     *  description: tests if boat's speed is reduced when colliding with an obstacle
     *  input data: new instance of a PlayerBoat, new instance of branch obstacle
     *  expected outcome: the speed of the boat should be lower after colliding
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testCheckCollisionWithObstacleReducesSpeed() {
        // Set the speed of the boat to a non-zero value
        test_boat.setSpeed(5f);

        // Check for collisions with the obstacle
        test_boat.checkCollisions(obstacle);

        // The speed of the boat should be reduced
        assertTrue(test_boat.getSpeed() < 5f);
    }

    /** id: CollisionsTest03
     *  description: tests if boat's durability is reduced when colliding with an obstacle
     *  input data: new instance of a PlayerBoat, new instance of branch obstacle
     *  expected outcome: the durability of the boat should be lower after colliding
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testCheckCollisionWithObstacleReducesDurability() {
        // Check for collisions with the obstacle
        test_boat.checkCollisions(obstacle);

        // The durability of the boat should be reduced from the initial value of 1f
        assertTrue(test_boat.getDurability() < 1f);
    }

    /** id: CollisionsTest03
     *  description: tests if boat receives a new effect after colliding with a powerup
     *  input data: new instance of a PlayerBoat, new instance of powerup
     *  expected outcome: list of effects of the player should be non-empty
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testCheckCollisionWithPowerupCreatesEffects() {
        // Check for collisions with the obstacle
        test_boat.checkCollisions(power_up);

        // The durability of the boat should be reduced from the initial value of 1f
        assertFalse(test_boat.getEffects().isEmpty());
    }

    /** id: CollisionsTest03
     *  description: tests if an obstacle disappears after it collides with a boat
     *  input data: new instance of a PlayerBoat, new instance of branch obstacle
     *  expected outcome: the obstacle's isShown attribute should be false after colliding
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testCheckObstacleDisappearsAfterCollision() {
        // Check for collisions with the obstacle
        test_boat.checkCollisions(obstacle);

        // The durability of the boat should be reduced from the initial value of 1f
        assertFalse(obstacle.isShown());
    }

    /** id: CollisionsTest03
     *  description: tests if an powerup disappears after it collides with a boat
     *  input data: new instance of a PlayerBoat, new instance of powerup
     *  expected outcome: the powerup's isShown attribute should be false after colliding
     *  requirements:
     *  category: white box testing
     *  @author: Dragos Stoican
     */
    @Test
    public void testCheckPowerUpDisappearsAfterCollision() {
        // Check for collisions with the obstacle
        test_boat.checkCollisions(obstacle);

        // The durability of the boat should be reduced from the initial value of 1f
        assertFalse(obstacle.isShown());
    }
}
