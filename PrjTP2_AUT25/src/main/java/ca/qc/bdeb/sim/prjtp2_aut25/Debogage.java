package ca.qc.bdeb.sim.prjtp2_aut25;

import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Fenetre;
import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import com.sun.tools.javac.Main;
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
    public void setLimiteNiveau(ArrayList<Maison>maisons){
        var positionDerniereMaison = maisons.get(maisons.size()-1).getPositionX();
        this.limiteNiveau = positionDerniereMaison + 1.5* MainJavaFX.WIDTH;

    }


    public void draw(GraphicsContext context, Camera camera, ArrayList<Maison> maisons, ArrayList<Journal> journaux,ArrayList<Particule>particules) {

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
        if(modeDebugChamp){
            setLimiteNiveau(maisons);


            for (double x = 0; x <limiteNiveau; x += 50) {
                for (double y = 0; y < MainJavaFX.HEIGHT; y += 50) {
                    var positionMonde = new Point2D(x, y);
                    var positionEcran = camera.coordoEcran(positionMonde);
                    // TODO: Seulement faire ça si la position (x, y) est visible dans l'écran
                    if(positionEcran.getX()>0&&positionEcran.getX()< MainJavaFX.WIDTH&&
                            positionEcran.getY()>0&&positionEcran.getY()<MainJavaFX.HEIGHT) {
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

    }

    private void drawStroke(ObjetDuJeu objet, GraphicsContext context, Camera camera) {

        var positionEcran = camera.coordoEcran(objet.position);

        context.strokeRect(positionEcran.getX(), positionEcran.getY(),
                objet.taille.getX(), objet.taille.getY()
        );

    }

    public void update(Partie partie) {
        setLimiteNiveau(partie.getMaisons());

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
        //Gestion touche F : flèche du champ électrique
        if(Input.isKeyPressed(KeyCode.F)){
            if(!fDejaAppuyee){
                modeDebugChamp = !modeDebugChamp;
                fDejaAppuyee = true;
            }

        }else{
            fDejaAppuyee = false;
        }
        //Gestion touche I : test lignes de particules
        if(Input.isKeyPressed(KeyCode.I)){
            if(!iDejaAppuyee){
                //code ligne particule
                var particules = partie.getParticules();
                particules.clear();
                for(int x = 0;x<limiteNiveau;x+=50){
                    particules.add(new Particule(x,10));
                    particules.add(new Particule(x,MainJavaFX.HEIGHT-10));

                }
                partie.setParticules(particules);
                iDejaAppuyee = true;
            }
        }else{
            iDejaAppuyee = false;
        }

    }
}
