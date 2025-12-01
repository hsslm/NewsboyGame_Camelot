package ca.qc.bdeb.sim.prjtp2_aut25;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;
/**
 * Gère l'état des touches du clavier.
 */
public class Input {

    private static Set<KeyCode> touches = new HashSet<>();

    public static boolean isKeyPressed(KeyCode code) {
        return touches.contains(code);
    }

    public static void setKeyPressed(KeyCode code, boolean appuie) {
        if (appuie)
            touches.add(code);
        else
            touches.remove(code);
    }
}
