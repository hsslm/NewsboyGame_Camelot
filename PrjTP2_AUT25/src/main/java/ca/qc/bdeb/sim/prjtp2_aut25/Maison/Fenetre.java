package ca.qc.bdeb.sim.prjtp2_aut25.Maison;

import ca.qc.bdeb.sim.prjtp2_aut25.Camera;
import ca.qc.bdeb.sim.prjtp2_aut25.ImageManager;
import ca.qc.bdeb.sim.prjtp2_aut25.Journal;
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
        this.image = ImageManager.getImage("fenetre.png");
        this.estBrisee = false;
    }

    public boolean isEstBrisee() {
        return estBrisee;
    }

    public void enCollisionJournal(Journal journal){
        if(testCollision(journal)&&!estBrisee){
            if(estAbonnee){
                image = ImageManager.getImage( "fenetre-brisee-rouge.png");
            }
            else{
                image = ImageManager.getImage("fenetre-brisee-vert.png");
            }
            estBrisee = true;


        }


    }


    @Override
    public void draw(GraphicsContext contexte, Camera camera) {

        var positionEcran = camera.coordoEcran(position);
        contexte.drawImage(image,positionEcran.getX(), positionEcran.getY());



    }
}
