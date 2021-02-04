package de.tomgrill.gdxtesting;

import com.teamonehundred.pixelboat.Boat;
import com.teamonehundred.pixelboat.BoatRace;
import com.teamonehundred.pixelboat.SceneMainGame;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(GdxTestRunner.class)
public class MainGameTest extends TestCase {
    SceneMainGame testSceneMainGame;
//    BoatRace testRace;

    @Before
    public void init() {
        testSceneMainGame = new SceneMainGame();
//        testRace = new BoatRace(testSceneMainGame.getAllBoats());
    }

    @Test
    public void testPausedGameBoatsNotMoving() {
        // Save a list of the boats locations so we can compare later
        List<Float[]> boats_locations = new ArrayList<>();
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            float x = testSceneMainGame.getAllBoats().get(i).getSprite().getX();
            float y = testSceneMainGame.getAllBoats().get(i).getSprite().getY();
            boats_locations.add(new Float[]{x, y});
        }
        // Set the game to be pause
        testSceneMainGame.setPaused(true);

        // Call the update method
        testSceneMainGame.update();

        // The position of the boats shouldn't change
        for (int i = 0; i < testSceneMainGame.getAllBoats().size(); i++) {
            assertTrue(testSceneMainGame.getAllBoats().get(i).getSprite().getX() == boats_locations.get(i)[0]
                    && testSceneMainGame.getAllBoats().get(i).getSprite().getY() == boats_locations.get(i)[1]);
        }
    }
}
