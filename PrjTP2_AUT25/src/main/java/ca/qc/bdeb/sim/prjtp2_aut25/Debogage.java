package ca.qc.bdeb.sim.prjtp2_aut25;

import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Fenetre;
import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Debogage {

    private boolean modeDebug;
    private boolean dEtaitPresse;


    public Debogage() {
        this.dEtaitPresse = false;
        this.modeDebug = false;
    }


    public void draw(GraphicsContext context, Camera camera, ArrayList<Maison> maisons, ArrayList<Journal> journaux) {

        if (modeDebug) {

            context.setStroke(Color.YELLOW);
            context.strokeLine(0.2 * MainJavaFX.WIDTH, MainJavaFX.HEIGHT, 0.2 * MainJavaFX.WIDTH, 0);

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

    public void update(double deltaTemps) {

        boolean dPresse = Input.isKeyPressed(KeyCode.D);

        if (deltaTemps >= 0.004) {

            //desactivation
            if (dPresse && dEtaitPresse) {
                modeDebug = !modeDebug;
            }
            dEtaitPresse = dPresse;
        }


    }
}
