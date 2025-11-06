package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Camelot extends ObjetDuJeu{
    public Camelot(Point2D position, Point2D velocite, Point2D taille, Point2D acceleration) {
        super(new Point2D(180,MainJavaFX.HEIGHT-144), new Point2D(400,0), new Point2D(172,144), new Point2D(0,1500));
    }

    @Override
    public void draw(GraphicsContext contexte, Camera camera) {
        var imgCamelot1 = new Image("camelot1.png");
        var imgCamelot2 = new Image("camelot2.png");


        contexte.drawImage(imgCamelot1, position.getX(), position.getY());




    }
}
