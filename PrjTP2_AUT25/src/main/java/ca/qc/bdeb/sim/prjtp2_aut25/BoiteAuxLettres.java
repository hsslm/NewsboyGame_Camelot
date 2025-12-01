package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoiteAuxLettres extends ObjetDuJeu {

    private boolean estAbonnee;
    private boolean aRecuJournal;
    private Image image;

    public BoiteAuxLettres(Point2D position, boolean estAbonnee) {
        super(
                position,
                Point2D.ZERO,
                new Point2D(81, 76),
                Point2D.ZERO
        );
        this.estAbonnee = estAbonnee;
        this.aRecuJournal = false;
        this.image = ImageManager.getImage("boite-aux-lettres.png");
    }


    public void enCollisionJournal(Journal journal, BarreAffichage barreAffichage) {
        if (!aRecuJournal&&testCollision(journal)) {

            //Change l'image selon si abonnée ou non
            if (estAbonnee) {
                image = ImageManager.getImage("boite-aux-lettres-vert.png");
                barreAffichage.ajouterArgent(1);


            } else {
                image = ImageManager.getImage("boite-aux-lettres-rouge.png");
            }
            aRecuJournal = true;
        }


    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        var positionEcran = camera.coordoEcran(position);
        context.drawImage(image,
                positionEcran.getX(),
                positionEcran.getY()
        );
    }


}
