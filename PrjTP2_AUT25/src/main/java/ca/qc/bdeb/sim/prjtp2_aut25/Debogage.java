package ca.qc.bdeb.sim.prjtp2_aut25;

import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Fenetre;
import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Debogage {

    private boolean modeDebug;
    private boolean dDejaAppuyee;
    private boolean qDejaAppuyee;
    private boolean kDejaAppuyee;
    private boolean lDejaAppuyee;


    public Debogage() {
        this.modeDebug = false;
        this.dDejaAppuyee = false;
        this.qDejaAppuyee = false;
        this.kDejaAppuyee = false;
        this.lDejaAppuyee = false;
    }


    public void draw(GraphicsContext context, Camera camera, ArrayList<Maison> maisons, ArrayList<Journal> journaux) {

        if (modeDebug) {

            context.setStroke(Color.YELLOW);
            //Ligne verticale jaune à 20% de l'écran
            context.strokeLine(0.2 * MainJavaFX.WIDTH, MainJavaFX.HEIGHT, 0.2 * MainJavaFX.WIDTH, 0);
            context.setLineWidth(2);

            for (Maison maison : maisons) {
                drawStroke(maison.getBoiteAuxLettres(), context, camera);

                if (maison.isaDesFenetres()) {
                    for (Fenetre fenetre : maison.getFenetres()) {

                        drawStroke(fenetre, context, camera);

                    }
                }
            }
            if (!journaux.isEmpty()) {
                for (Journal journal : journaux) {
                    drawStroke(journal, context, camera);
                }
            }

        }

    }

    private void drawStroke(ObjetDuJeu objet, GraphicsContext context, Camera camera) {

        var positionEcran = camera.coordoEcran(objet.position);

        context.strokeRect(positionEcran.getX(), positionEcran.getY(),
                objet.taille.getX(), objet.taille.getY()
        );

    }

    public void update(Partie partie) {

        //Gestion touche D : contour des objets en jaune
        if (Input.isKeyPressed(KeyCode.D)) {
            if (!dDejaAppuyee) {
                modeDebug = !modeDebug;
                dDejaAppuyee = true;
            }
        } else {
            dDejaAppuyee = false;
        }


        //Gestion touche Q : ajout de 10 journaux
        if (Input.isKeyPressed(KeyCode.Q)) {
            if (!qDejaAppuyee) {
                partie.ajouterJournaux(10);
                qDejaAppuyee = true;
            }
        } else {
            qDejaAppuyee = false;
        }

        //Gestion touche K : met la quantité de journaux à 0 (fin de la partie)
        if (Input.isKeyPressed(KeyCode.K)) {
            if (!kDejaAppuyee) {
                partie.mettreStockJournauxAZero();
                kDejaAppuyee = true;
            }
        } else {
            kDejaAppuyee = false;
        }

        //Gestion touche L : prochain niveau ( écran de chargement du prochain niveau)
        if (Input.isKeyPressed(KeyCode.L)) {
            if (!lDejaAppuyee) {
                partie.niveauSuivant();
                lDejaAppuyee = true;
            }
        } else {
            lDejaAppuyee = false;
        }

    }
}
