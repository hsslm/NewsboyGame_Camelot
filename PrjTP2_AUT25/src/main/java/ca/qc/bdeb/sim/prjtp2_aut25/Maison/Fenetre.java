package ca.qc.bdeb.sim.prjtp2_aut25.Maison;

import ca.qc.bdeb.sim.prjtp2_aut25.Camera;
import ca.qc.bdeb.sim.prjtp2_aut25.ObjetDuJeu;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fenetre extends ObjetDuJeu {
    private boolean estAbonnee;
    private boolean estBrisee;
    private Image image;
    public Fenetre(Point2D position, boolean estAbonnee) {
        super(position, Point2D.ZERO, new Point2D(159,130), Point2D.ZERO);
        this.estAbonnee = estAbonnee;
        this.image = new Image("fenetre.png");
        this.estBrisee = false;
    }


    private void enCollisionJournal(){
        if(estBrisee){
            if(estAbonnee){
                image = new Image("fenetre-brisee-rouge.png");
            }
            else{
                image = new Image("fenetre-brisee-vert.png");
            }
        }

    }

    @Override
    public void draw(GraphicsContext contexte, Camera camera) {

        var positionEcran = camera.coordoEcran(position);
        contexte.drawImage(image,positionEcran.getX(), positionEcran.getY());



    }
}
