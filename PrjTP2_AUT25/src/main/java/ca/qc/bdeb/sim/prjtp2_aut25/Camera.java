package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
/**
 * Gère la caméra et le suivi du Camelot.
 */

public class Camera {
    private Point2D positionCamera = Point2D.ZERO;

    public Point2D coordoEcran(Point2D positionMonde) {
        return positionMonde.subtract(positionCamera);
    }

    public void suivreCamelot(Camelot camelot){
        positionCamera = new Point2D(camelot.position.getX()-180, 0);
    }

}
