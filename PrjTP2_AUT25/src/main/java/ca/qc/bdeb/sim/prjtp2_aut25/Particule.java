package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;



public class Particule extends ObjetDuJeu {
    private static final double CHARGE = 900;
    private static final Random RANDOM = new Random() ;
    private final double CONSTANTE_COULOMB = 90;
    private Color teinte;


    public Particule(double positionX, double positionY) {

        super(new Point2D(positionX, positionY), Point2D.ZERO, new Point2D(20, 20), Point2D.ZERO);
        double randomTeinte = RANDOM.nextDouble(0, 360);
        Color couleur = Color.hsb(randomTeinte, 1, 1);
        this.teinte = couleur;
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        var positionEcran = camera.coordoEcran(position);

        context.setFill(teinte);
        context.fillOval(
                positionEcran.getX() - (taille.getX() / 2),
                positionEcran.getY() - (taille.getY() / 2),
                taille.getX(),
                taille.getY()
        );


    }

    public Point2D calculerChampElectrique(Point2D point) { //point correspond au centre de la position du journal
        var distancePoint = position.distance(point);
        if (distancePoint < 1) {
            //évite division par 0
            distancePoint = 1;
        }
        var moduleChampElectrique = CONSTANTE_COULOMB * CHARGE / (distancePoint * distancePoint);
        var directionChampElectrique = point.subtract(position).normalize();
        return directionChampElectrique.multiply(moduleChampElectrique);
    }

    public static Point2D champElectriqueTotal(ArrayList<Particule> particules, Point2D point) {
        Point2D champElectriqueTotal = Point2D.ZERO;
        for (Particule particule : particules) {
           champElectriqueTotal = champElectriqueTotal.add(particule.calculerChampElectrique(point));
        }
        return champElectriqueTotal;
    }


}
