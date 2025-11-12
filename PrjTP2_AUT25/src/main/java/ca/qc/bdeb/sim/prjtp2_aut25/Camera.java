package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;

public class Camera {
    private Point2D positionCamera;

    public Point2D getPositionCamera() {
        return positionCamera;
    }

    public Point2D coordoEcran(Point2D positionMonde) {
        return positionMonde.subtract(positionCamera);
    }
    public void suivreCamelot(Camelot camelot){
        positionCamera = new Point2D(camelot.position.getX(), MainJavaFX.HEIGHT);

    }
}
