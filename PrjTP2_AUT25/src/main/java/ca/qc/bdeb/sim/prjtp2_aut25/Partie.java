package ca.qc.bdeb.sim.prjtp2_aut25;


import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Partie {
    private Decor decor;
    private Camelot camelot;
    private ArrayList<Journal> journaux;
    private Camera camera;
    private EcranDeChargement ecranDeChargement;
    private boolean chargementEnCours;
    private int niveauActuel;

    public Partie(){
        this.niveauActuel = 1;
        //Création des objets nécéssaires pour le début d'une partie
        demarrerNiveau();
    }

    public void demarrerNiveau(){
        this.camelot = new Camelot();
        this.decor = new Decor();
        this.camera = new Camera();
        this.journaux = new ArrayList<>(12);
        this.chargementEnCours = true;
    }

    public void creerEcranChargement(GraphicsContext context){
        this.ecranDeChargement = new EcranDeChargement("Niveau " + niveauActuel);
    }

    public void update(double deltaTemps) {
        if(!chargementEnCours){
            camelot.update(deltaTemps);
            camera.suivreCamelot(camelot);
        }
    }

    public void draw(GraphicsContext context){
        if(chargementEnCours){
            ecranDeChargement.draw(context);
            if (ecranDeChargement.estTermine()){
                chargementEnCours = false;
            }
        }else {
            context.clearRect(0,0,MainJavaFX.WIDTH,MainJavaFX.HEIGHT);
            decor.draw(context, camera);
            camelot.draw(context,camera);

        }

    }

    public void niveauSuivant (GraphicsContext context){
        niveauActuel++;
        demarrerNiveau();
        creerEcranChargement(context);
    }

}
