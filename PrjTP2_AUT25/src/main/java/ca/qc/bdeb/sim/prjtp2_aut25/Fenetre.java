package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fenetre extends ObjetDuJeu {
    private boolean estAbonnee;
    private boolean estBrisee;
    private Image image;

    public Fenetre(Point2D position, boolean estAbonnee) {
        super(position, Point2D.ZERO, new Point2D(159, 130), Point2D.ZERO);
        this.estAbonnee = estAbonnee;
        this.image = ImageManager.getImage("fenetre.png");
        this.estBrisee = false;

    }

    public void enCollisionJournal(Journal journal, BarreAffichage barreAffichage) {

        //On ne réapplique pas l'effet si la fenêtre est déjà cassée
        if (!estBrisee && testCollision(journal)) {
            estBrisee = true;

            if (estAbonnee) {
                image = ImageManager.getImage("fenetre-brisee-rouge.png");
                barreAffichage.retirerArgent(2);
                System.out.println("retrait 2$");
            } else {
                image = ImageManager.getImage("fenetre-brisee-vert.png");
                barreAffichage.ajouterArgent(2);
                System.out.println("ajout 2$");
            }
        }

    }


    @Override
    public void draw(GraphicsContext contexte, Camera camera) {
        var positionEcran = camera.coordoEcran(position);
        contexte.drawImage(image, positionEcran.getX(), positionEcran.getY());
    }
}
