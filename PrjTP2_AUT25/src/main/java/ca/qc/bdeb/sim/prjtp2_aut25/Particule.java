package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Particule extends ObjetDuJeu {
    private final double CHARGE = 900;
    private final double CONSTANTE_COULOMB = 90;
    private Point2D champElectrique;
    private double teinte;
    private final Random RANDOM = new Random();


    public Particule(Point2D position) {
        super(position, Point2D.ZERO, new Point2D(20, 20), Point2D.ZERO);

        this.teinte = RANDOM.nextDouble(0, 360);
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        var positionEcran = camera.coordoEcran(position);
        context.setFill(Color.hsb(teinte, 1, 1));
        context.fillOval(
                positionEcran.getX() - (taille.getX() / 2),
                positionEcran.getY() - (taille.getY() / 2),
                taille.getX(),
                taille.getY()
        );


    }

    public Point2D calculerChampElectrique(Point2D point) { //point correspond au centre de la position du journal
        var distancePoint = position.distance(point);
        var moduleChampElectrique = CONSTANTE_COULOMB * CHARGE / ((distancePoint * distancePoint));
        var directionChampElectrique = position.subtract(point).normalize();


        return directionChampElectrique.multiply(moduleChampElectrique);
    }


}
