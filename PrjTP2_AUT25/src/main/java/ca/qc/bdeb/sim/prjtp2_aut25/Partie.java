package ca.qc.bdeb.sim.prjtp2_aut25;


import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Partie {
    private Decor decor;
    private Camelot camelot;
    private ArrayList<Journal> journaux;
    private ArrayList<Maison> maisons;
    private Camera camera;
    private EcranDeChargement ecranDeChargement;
    private boolean chargementEnCours;
    private int niveauActuel;

    public Partie() {
        this.niveauActuel = 1;
        //Création des objets nécéssaires pour le début d'une partie
        demarrerNiveau();
    }

    public void demarrerNiveau() {
        this.camelot = new Camelot();
        this.decor = new Decor();
        this.camera = new Camera();
        this.journaux = new ArrayList<>(12);
        this.chargementEnCours = true;

        genererMaisons();
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
        }

    }

    public void niveauSuivant(GraphicsContext context) {
        niveauActuel++;
        demarrerNiveau();
        creerEcranChargement(context);
    }

}
