package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Journal extends ObjetDuJeu{
    private double masse;
    private int nbJournaux;
    private final Point2D QUANTITE_HAUT = new Point2D(900,-900);
    private final Point2D QUANTITE_AVANT = new Point2D(150,-1100);
    public Journal(Point2D position, Point2D velocite, double masse, int nbJournaux) {
        super(position, velocite,new Point2D(52,31) , new Point2D(0,1500));
        this.masse = masse;
        this.nbJournaux = nbJournaux;
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);

        



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
    public Point2D calculerVitesseInitiale(){
        boolean lancerHaut = Input.isKeyPressed(KeyCode.Z);
        boolean lancerAvant = Input.isKeyPressed(KeyCode.X);
        boolean lancerPlusFort = Input.isKeyPressed(KeyCode.SHIFT);

        Point2D vitesseInitiale = Point2D.ZERO;
        if(lancerHaut&&lancerPlusFort) {
            vitesseInitiale = velocite.add(new Point2D(QUANTITE_HAUT.getX() / masse, QUANTITE_HAUT.getY() / masse));
            vitesseInitiale = vitesseInitiale.multiply(1.5);

        }
        else if(lancerAvant&&lancerPlusFort){
            vitesseInitiale = velocite.add(new Point2D(QUANTITE_AVANT.getX() / masse, QUANTITE_AVANT.getY() / masse));
            vitesseInitiale = vitesseInitiale.multiply(1.5);

        }
        else if(lancerHaut){
            vitesseInitiale = velocite.add(new Point2D(QUANTITE_HAUT.getX() / masse, QUANTITE_HAUT.getY() / masse));

        }
        else if(lancerAvant){
            vitesseInitiale = velocite.add(new Point2D(QUANTITE_AVANT.getX() / masse, QUANTITE_AVANT.getY() / masse));
        }

        return vitesseInitiale;


    }


}
