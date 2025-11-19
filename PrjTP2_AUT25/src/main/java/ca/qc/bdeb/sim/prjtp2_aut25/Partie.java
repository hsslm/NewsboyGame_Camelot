package ca.qc.bdeb.sim.prjtp2_aut25;


import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;

public class Partie {
    private Decor decor;
    private Camelot camelot;
    private int nbJournaux;
    private int nbJournauxRestants = 0;
    private ArrayList<Maison> maisons;
    private Camera camera;
    private EcranDeChargement ecranDeChargement;
    private boolean chargementEnCours;
    private int niveauActuel;
    private double masseJournaux;
    private static Random random = new Random();


    public Partie() {
        this.niveauActuel = 1;
        //Création des objets nécéssaires pour le début d'une partie
        demarrerNiveau();
    }

    public void demarrerNiveau() {
        this.camelot = new Camelot();
        this.decor = new Decor();
        this.camera = new Camera();
        this.chargementEnCours = true;
        this.nbJournaux = 12+nbJournauxRestants;

        this.masseJournaux = random.nextDouble(1,2);

        genererMaisons();
    }

    private void genererMaisons() {
        maisons = new ArrayList<>();

        //Adresse de la première maison : entre 100 et 950

        int premiereAdresse = random.nextInt(100, 950);

        //Écart de 2 entre les adresses des maisons et écart de 1300 entre les positions des maisons
        for (int i = 0; i < 12; i++) {
            int adresse = premiereAdresse + (i * 2);
            double positionX = 1300 + (i * 1300);
            maisons.add(new Maison(adresse, positionX));
        }
    }


    public void creerEcranChargement(GraphicsContext context) {
        this.ecranDeChargement = new EcranDeChargement("Niveau " + niveauActuel);
    }

    public void update(double deltaTemps) {
        if (!chargementEnCours) {

            camelot.update(deltaTemps);
            camera.suivreCamelot(camelot);


        }
    }

    public void draw(GraphicsContext context) {
        if (chargementEnCours) {
            ecranDeChargement.draw(context);
            if (ecranDeChargement.estTermine()) {
                chargementEnCours = false;
            }
        } else {
            context.clearRect(0, 0, MainJavaFX.WIDTH, MainJavaFX.HEIGHT);

            //Dessin du décor
            decor.draw(context, camera);
            //Dessin des maisons
            for (var maison : maisons) {
                maison.draw(context, camera);
            }
            //Dessin du camelot
            camelot.draw(context, camera);
            if((Input.isKeyPressed(KeyCode.Z)||Input.isKeyPressed(KeyCode.X))&&nbJournaux>0){
                //keyPressed ne marche pas pour lancer l'objet
                var journal = new Journal(camelot.getCentre(),Point2D.ZERO,masseJournaux);
                journal.lancerJournal(context,camelot,camera);
            }





        }

    }


    public void niveauSuivant(GraphicsContext context) {
        niveauActuel++;
        //calcule le nombre de journeaux restants pour le prochain niveau
        nbJournauxRestants = nbJournaux;
        nbJournaux = 0;
        demarrerNiveau();
        creerEcranChargement(context);
    }

}
