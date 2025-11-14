package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;

public class Camera {
    private Point2D positionCamera = Point2D.ZERO;

    public Point2D getPositionCamera() {
        return positionCamera;
    }

    public Point2D coordoEcran(Point2D positionMonde) {

        // Caméra :  x=10 000
        // Camelot : x=10 200
        // écran = ???? ==> 200

        return positionMonde.subtract(positionCamera);
    }

    public void suivreCamelot(Camelot camelot){
        positionCamera = new Point2D(camelot.position.getX(), 0);

    }
}
