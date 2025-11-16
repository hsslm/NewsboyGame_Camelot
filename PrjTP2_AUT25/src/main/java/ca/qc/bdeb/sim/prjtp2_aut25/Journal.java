package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Journal extends ObjetDuJeu{
    private double masse;
    public Journal(Point2D position, Point2D velocite) {
        super(position, velocite,new Point2D(52,31) , new Point2D(0,1500));
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        var imgJournal = new Image("journal.png");
        var coordoEcran = camera.coordoEcran(position);

        context.drawImage(
                imgJournal,
                coordoEcran.getX(),
                coordoEcran.getY(),
                taille.getX(),
                taille.getY()
        );

    }


}
