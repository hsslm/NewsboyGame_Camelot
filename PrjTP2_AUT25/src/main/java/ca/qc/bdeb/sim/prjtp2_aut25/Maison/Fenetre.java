package ca.qc.bdeb.sim.prjtp2_aut25.Maison;

import ca.qc.bdeb.sim.prjtp2_aut25.Camera;
import ca.qc.bdeb.sim.prjtp2_aut25.ObjetDuJeu;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Fenetre extends ObjetDuJeu {
    public Fenetre(Point2D position, Point2D velocite, Point2D taille, Point2D acceleration) {
        super(position, velocite, taille, acceleration);
    }

    @Override
    public void draw(GraphicsContext contexte, Camera camera) {

    }
}
