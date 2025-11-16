package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Maison extends ObjetDuJeu {
    private int adresse;
    private boolean abonneeAuJournal;
    private BoiteAuxLettres boiteAuxLettres;
    private Random random;

    public int getAdresse() {
        return adresse;
    }

    public boolean isAbonneeAuJournal() {
        return abonneeAuJournal;
    }

    public Maison(int adresse) {
        super(Point2D.ZERO, Point2D.ZERO, Point2D.ZERO, Point2D.ZERO);
        this.adresse = adresse;
        this.abonneeAuJournal = random.nextBoolean();
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {


    }
}
