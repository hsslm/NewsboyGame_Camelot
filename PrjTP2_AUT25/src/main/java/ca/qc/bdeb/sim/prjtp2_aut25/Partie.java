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
    private ArrayList<Journal> journaux;
    private ArrayList<Journal> journauxLances;
    private int nbJournauxRestants = 0;
    private ArrayList<Maison> maisons;
    private Camera camera;
    private EcranDeChargement ecranDeChargement;
    private boolean chargementEnCours;
    private int niveauActuel;
    private int i=0;

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
        this.journauxLances = new ArrayList<>();

        genererMaisons();
        genererJournaux();
        System.out.println(journaux.size());

    }

    private void genererMaisons() {
        maisons = new ArrayList<>();

        //Adresse de la première maison : entre 100 et 950
        Random random = new Random();
        int premiereAdresse = random.nextInt(100, 950);

        //Écart de 2 entre les adresses des maisons et écart de 1300 entre les positions des maisons
        for (int i = 0; i < 12; i++) {
            int adresse = premiereAdresse + (i * 2);
            double positionX = 1300 + (i * 1300);
            maisons.add(new Maison(adresse, positionX));
        }
    }
    private void genererJournaux(){
        journaux = new ArrayList<>();

        Random gen = new Random();
        double masse = gen.nextDouble(1,2);
        for(int i =0;i<12+nbJournauxRestants;i++){
            journaux.add(new Journal(camelot.getCentre(),Point2D.ZERO,masse));
            System.out.println("bonjour");

        }

    }

    public void creerEcranChargement(GraphicsContext context) {
        this.ecranDeChargement = new EcranDeChargement("Niveau " + niveauActuel);
    }

    public void update(double deltaTemps) {
        if (!chargementEnCours) {

            camelot.update(deltaTemps);
            camera.suivreCamelot(camelot);
            for(Journal j : journauxLances) {// update les journaux qui ont été lancé seulement
                j.update(deltaTemps);
            }

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
            if(Input.isKeyPressed(KeyCode.Z)||Input.isKeyPressed(KeyCode.X)){ //keyPressed ne marche pas pour lancer l'objet
                journaux.get(0).lancerJournal(context,camelot,camera);//cela ne marche pas
                journauxLances.add(journaux.get(0));
                journaux.remove(0);
            }

           if(journauxLances.size()>0) { //journaux qu'il faut continuer à draw après avoir été lancé
               for (Journal j : journauxLances) {
                   j.draw(context, camera);
               }
           }



        }

    }


    public void niveauSuivant(GraphicsContext context) {
        niveauActuel++;
        //calcule le nombre de journeaux restants pour le prochain niveau
        nbJournauxRestants = journaux.size();
        demarrerNiveau();
        creerEcranChargement(context);
    }

}
