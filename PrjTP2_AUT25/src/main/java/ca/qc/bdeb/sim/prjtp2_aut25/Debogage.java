package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Debogage {

    private boolean modeDebug;
    private boolean modeDebugChamp;
    private boolean dDejaAppuyee;
    private boolean qDejaAppuyee;
    private boolean kDejaAppuyee;
    private boolean lDejaAppuyee;
    private boolean fDejaAppuyee;
    private boolean iDejaAppuyee;
    private double limiteNiveau;


    public Debogage() {
        this.modeDebug = false;
        this.modeDebugChamp = false;
        this.dDejaAppuyee = false;
        this.qDejaAppuyee = false;
        this.kDejaAppuyee = false;
        this.lDejaAppuyee = false;
        this.fDejaAppuyee = false;
        this.iDejaAppuyee = false;

    }


    public void setLimiteNiveau(Partie partie) {
        this.limiteNiveau = partie.calculerLimiteNiveau();
    }

    /**
     * Dessine le débogage général et le débogage du champ électrique
     */
    public void draw(GraphicsContext context, Camera camera, ArrayList<Maison> maisons, ArrayList<Journal> journaux, ArrayList<Particule> particules, Partie partie) {

        if (modeDebug) {
            //Ligne verticale jaune à 20% de l'écran
            context.setStroke(Color.YELLOW);
            context.strokeLine(0.2 * MainJavaFX.WIDTH, MainJavaFX.HEIGHT, 0.2 * MainJavaFX.WIDTH, 0);
            context.setLineWidth(2);

            drawRectangleJauneMaison(context, camera, maisons);
            drawRectangleJauneJournal(context, camera, journaux);

        }
        if (modeDebugChamp) {
            setLimiteNiveau(partie);
            drawVecteurParticule(context, camera, particules);
        }

    }
    /**
     * Dessine le débogage général pour les journaux (contour jaune)
     */
    private void drawRectangleJauneJournal(GraphicsContext context, Camera camera, ArrayList<Journal> journaux) {
        if (!journaux.isEmpty()) {
            for (var journal : journaux) {
                drawStroke(journal, context, camera);
            }
        }
    }
    /**
     * Dessine le débogage général pour les maisons (contour jaune)
     */
    private void drawRectangleJauneMaison(GraphicsContext context, Camera camera, ArrayList<Maison> maisons) {
        for (Maison maison : maisons) {
            drawStroke(maison.getBoiteAuxLettres(), context, camera);

            if (maison.isaDesFenetres()) {
                for (var fenetre : maison.getFenetres()) {

                    drawStroke(fenetre, context, camera);

                }
            }
        }
    }
    /**
     * Dessine les vecteurs du champ éléctrique des particules
     */
    private void drawVecteurParticule(GraphicsContext context, Camera camera, ArrayList<Particule> particules) {
        for (double x = 0; x < limiteNiveau; x += 50) {
            for (double y = 0; y < MainJavaFX.HEIGHT; y += 50) {
                var positionMonde = new Point2D(x, y);
                var positionEcran = camera.coordoEcran(positionMonde);

                if (positionEcran.getX() > 0 && positionEcran.getX() < MainJavaFX.WIDTH &&
                        positionEcran.getY() > 0 && positionEcran.getY() < MainJavaFX.HEIGHT) {
                    Point2D force = Particule.champElectriqueTotal(particules, positionMonde);
                    UtilitairesDessins.dessinerVecteurForce(
                            positionEcran,
                            force,
                            context
                    );
                }
            }
        }
    }

    /**
     * Dessine le débogage général pour des objets du jeu (contour jaune)
     */
    private void drawStroke(ObjetDuJeu objet, GraphicsContext context, Camera camera) {

        var positionEcran = camera.coordoEcran(objet.position);

        context.strokeRect(positionEcran.getX(), positionEcran.getY(),
                objet.taille.getX(), objet.taille.getY()
        );

    }

    public void update(Partie partie) {

        // Limite d'apparition des particules et de leur champ
        setLimiteNiveau(partie);

        // Touche de débogage
        gestionToucheD();

        // Touches de débogage de la logique du jeu
        gestionToucheQ(partie);
        gestionToucheK(partie);
        gestionToucheL(partie);

        // Touches de débogage du champ éléctrique
        gestionToucheF();
        gestionToucheI(partie);

    }

    /**
     * Gestion touche I : particules de test sur l’écran
     */
    private void gestionToucheI(Partie partie) {
        if (Input.isKeyPressed(KeyCode.I)) {
            if (!iDejaAppuyee) {

                var particules = partie.getParticules();
                particules.clear();
                for (int x = 0; x < limiteNiveau; x += 50) {
                    particules.add(new Particule(x, 10));
                    particules.add(new Particule(x, MainJavaFX.HEIGHT - 10));

                }
                partie.setParticules(particules);
                iDejaAppuyee = true;
            }
        } else {
            iDejaAppuyee = false;
        }
    }

    /**
     * Gestion touche F : visualisation
     * du champ électrique des particules avec des flèches
     */
    private void gestionToucheF() {
        if (Input.isKeyPressed(KeyCode.F)) {
            if (!fDejaAppuyee) {
                modeDebugChamp = !modeDebugChamp;
                fDejaAppuyee = true;
            }

        } else {
            fDejaAppuyee = false;
        }
    }

    /**
     * Gestion touche L : prochain niveau ( écran de chargement du prochain niveau)
     */
    private void gestionToucheL(Partie partie) {
        if (Input.isKeyPressed(KeyCode.L)) {
            if (!lDejaAppuyee) {
                partie.niveauSuivant();
                lDejaAppuyee = true;
            }
        } else {
            lDejaAppuyee = false;
        }
    }

    /**
     * Gestion touche K : met la quantité de journaux à 0 (fin de la partie)
     */
    private void gestionToucheK(Partie partie) {
        if (Input.isKeyPressed(KeyCode.K)) {
            if (!kDejaAppuyee) {
                partie.mettreStockJournauxAZero();
                kDejaAppuyee = true;
            }
        } else {
            kDejaAppuyee = false;
        }
    }

    /**
     * Gestion touche Q : ajout de 10 journaux
     */
    private void gestionToucheQ(Partie partie) {
        if (Input.isKeyPressed(KeyCode.Q)) {
            if (!qDejaAppuyee) {
                partie.ajouterJournaux(10);
                qDejaAppuyee = true;
            }
        } else {
            qDejaAppuyee = false;
        }
    }

    /**
     * Gestion touche D : contour des fenêtres et boîtes aux lettres en jaune
     */
    private void gestionToucheD() {
        if (Input.isKeyPressed(KeyCode.D)) {
            if (!dDejaAppuyee) {
                modeDebug = !modeDebug;
                dDejaAppuyee = true;
            }
        } else {
            dDejaAppuyee = false;
        }
    }
}
