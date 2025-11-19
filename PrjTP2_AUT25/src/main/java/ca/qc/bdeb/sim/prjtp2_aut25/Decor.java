package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Decor extends ObjetDuJeu {

    public Decor() {
        super(Point2D.ZERO,
                new Point2D( 0, 0),
                new Point2D(192, 96),
                Point2D.ZERO
        );
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        var coordoCam = camera.coordoEcran(position);
        for (double y = coordoCam.getY(); y < MainJavaFX.HEIGHT; y += taille.getY()) {
            for (double x = coordoCam.getX(); x < MainJavaFX.WIDTH; x += taille.getX()) {
                context.drawImage(ImageManager.getImage("brique.png"), x, y);
            }
        }
    }


}
