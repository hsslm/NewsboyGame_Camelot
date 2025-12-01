package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.scene.image.Image;

import java.util.HashMap;
/**
 * Gère le chargement et les images du jeu.
 */

public class ImageManager {
    private static HashMap<String, Image> images = new HashMap<>();

    public static Image getImage(String path) {

        if(images.containsKey(path))
            return images.get(path);

        var image = new Image(path);

        images.put(path, image);

        return image;
    }
}
