package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Journal extends ObjetDuJeu {


    private final Point2D QUANTITE_MOUVEMENT_HAUT = new Point2D(900, -900);
    private final Point2D QUANTITE_MOUVEMENT_AVANT = new Point2D(150, -1100);
    private final double MULTIPLICATEUR_SHIFT = 1.5;
    private final double VITESSE_MAX = 1500;
    private double masse;
    private double charge;

    public Journal(Point2D position, double masse) {
        super(position, Point2D.ZERO, new Point2D(52, 31), new Point2D(0, 1500));
        this.masse = masse;
        this.charge = 900;
    }


    public void update(double deltaTemps) {
        acceleration =
        super.update(deltaTemps);

        //Limite la vitesse
        if (velocite.magnitude() >= VITESSE_MAX) {
            double max = VITESSE_MAX;
            velocite = velocite.multiply(max / velocite.magnitude());
        }

    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        var imgJournal = ImageManager.getImage("journal.png");
        var coordoEcran = camera.coordoEcran(position);
        context.drawImage(
                imgJournal,
                coordoEcran.getX(),
                coordoEcran.getY()
        );

    }

    //Calcule la vitesse initiale du journal selon sa masse, les touches enfoncées et la vitesse du camelot
    public void calculerVitesseInitiale(Camelot camelot) {

        boolean lancerHaut = Input.isKeyPressed(KeyCode.Z);
        boolean lancerAvant = Input.isKeyPressed(KeyCode.X);
        boolean lancerPlusFort = Input.isKeyPressed(KeyCode.SHIFT);

        System.out.println("Shift enfoncé : " + lancerPlusFort);
        //Détermine quelle quantité de mouvement utiliser
        Point2D quantiteMouvement = null;

        if (lancerHaut) {
            quantiteMouvement = QUANTITE_MOUVEMENT_HAUT;
        } else if (lancerAvant) {
            quantiteMouvement = QUANTITE_MOUVEMENT_AVANT;
        }

        //Calcule vitesse initiale selon la formule
        if (quantiteMouvement != null) {
            Point2D ajoutVitesse = new Point2D(
                    quantiteMouvement.getX() / masse,
                    quantiteMouvement.getY() / masse
            );

            //Si shift est enfoncé ---> applique le multiplicateur
            if (lancerPlusFort) {
                ajoutVitesse = ajoutVitesse.multiply(MULTIPLICATEUR_SHIFT);
            }

            velocite = camelot.getVelocite().add(ajoutVitesse);
        }


    }

    public void calculeraccelerationTotale(){
        /*var champElectriqueTotal = ;
        var forceElectrique = charge* champElectriqueTotal;
        var accelerationParticules = forceElectrique/masse;

         */


    }






}
