package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.teamonehundred.pixelboat.Boat;
import com.teamonehundred.pixelboat.PlayerBoat;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.*;
import java.awt.event.KeyEvent;

@RunWith(GdxTestRunner.class)
public class BoatTest extends TestCase {

    /** id: BoatTest01
     *  description: tests a boats stamina decreases when it accelerates
     *  input data: new instance of a boat
     *  expected outcome: stamina after accelerating should be lower than before accelerating
     *  requirements: FR_STAM_USAGE
     *  category: white box testing
     *  author: Dragos Stoican
     */
    @Test
    public void testStaminaDecrease() {
        // Create the player boat and save the starting stamina
        Boat testBoat = new PlayerBoat(0, 0);
        float startStamina = testBoat.getStamina();

        // Accelerate
        testBoat.accelerate();

        // After accelerating, the stamina should decrease
        assertTrue(startStamina > testBoat.getStamina());
    }

    /** id: BoatTest01
     *  description: tests the correct input processing for the player's boat
     *  input data: new instance of a PlayerBoat
     *  expected outcome: acceleration and rotation should be affected by the buttons
     *  requirements: UR_PLAYABILITY, FR_PLAYABILITY
     *  category: white box testing
     *  author: Dragos Stoican
     */
//    @Test
//    public void testControls() throws AWTException {
//        // Create thhe player boat and a robot to execute the key presses
//        PlayerBoat testPlayerBoat = new PlayerBoat(0, 0);
//        Robot r = new Robot();
//
//        // Save the initial speed of the boat
//        float startSpeed = testPlayerBoat.getSpeed();
//
//        // Make the robot press the W key
//        int keycode = KeyEvent.VK_W;
//        r.keyPress(keycode);
//        // Process the input
//        testPlayerBoat.updatePosition();
//        // The speed of the boat should be greater than before
//        assertTrue(testPlayerBoat.getSpeed() > startSpeed);
//
//        // Realease the W key
//        r.keyRelease(keycode);
//    }



}
