package ca.qc.bdeb.sim.prjtp2_aut25;


import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Fenetre;
import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

//est ce que cest correct tt sois en rose
public class Partie {
    //Constantes :
    private static final Random RANDOM = new Random();
    private static final int NB_MAISONS = 12;
    private static final int JOURNAUX_PAR_NIVEAUX = 12;
    private static final double DELAI_ENTRE_LANCERS = 0.5;

    //Objets du jeu :
    private Decor decor;
    private Camelot camelot;
    private Camera camera;
    private ArrayList<Maison> maisons;
    private ArrayList<Integer> adresses;
    private ArrayList<Journal> journaux;
    private ArrayList<Particule> particules;
    private BarreAffichage barreAffichage;
    private Debogage debogage;


    //État du jeu :
    private int niveauActuel;
    private int nbJournaux;
    private int nbJournauxRestants;
    private double masseJournaux;
    private boolean chargementEnCours;
    private EcranDeChargement ecranDeChargement;
    private long tempsApresLancer;
    private boolean finDePartie;


    public Partie() {
        this.journaux = new ArrayList<>();
        this.particules = new ArrayList<>();
        this.debogage = new Debogage();
        this.finDePartie = false;
        this.chargementEnCours = true;

        initialiserNiveau(1, 0);
    }

    public void initialiserNiveau(int niveauActuel, int nbJournauxRestants) {

        this.niveauActuel = niveauActuel;
        this.nbJournaux = JOURNAUX_PAR_NIVEAUX + nbJournauxRestants;
        this.nbJournauxRestants = 0;
        this.tempsApresLancer = 0;
        this.masseJournaux = RANDOM.nextDouble(1, 2);

        this.camelot = new Camelot();
        this.decor = new Decor();
        this.camera = new Camera();
        genererMaisons();
        this.barreAffichage = new BarreAffichage(nbJournaux, adresses, 0);
        this.ecranDeChargement = new EcranDeChargement("Niveau " + this.niveauActuel);
        if (niveauActuel >= 2) {
            genererParticules();
        }
    }

    private void genererMaisons() {
        maisons = new ArrayList<>();
        adresses = new ArrayList<>();

        //Adresse de la première maison : entre 100 et 950
        int premiereAdresse = RANDOM.nextInt(100, 951);

        //Écart de 2 entre les adresses des maisons et écart de 1300 entre les positions des maisons
        for (int i = 0; i < NB_MAISONS; i++) {
            int adresse = premiereAdresse + (i * 2);

            double positionX = 1300 + (i * 1300);
            maisons.add(new Maison(adresse, positionX));
        }
        //ajoute les adresses des maisons abonnées à la liste
        for (Maison maison : maisons) {
            if (maison.estAbonneeAuJournal()) {
                adresses.add(maison.getAdresse());
            }
        }
    }

    public void update(double deltaTemps) {
        if (!chargementEnCours) {

            camelot.update(deltaTemps);
            camera.suivreCamelot(camelot);

            //Interactions de l'utilisateur avec le clavier
            gererLancementJournaux();

            //Supprime les journaux sortis de l'écran
            for (var journal : journaux) {
                journal.update(deltaTemps, particules);
                journaux.removeIf(journal1 ->
                        journal.getBas() > MainJavaFX.HEIGHT
                                || journal.getHaut() < 0
                                || journal.getGauche() < 0
                );

            }

            //Pour que debogage puisse agir sur partie on transmet l'instance
            debogage.update(this);

            //Vérifie si le camelot a depassé la limite de position de fin de niveau
            niveauEstTermine();
            //Vérifie si le camelot a toujours des journaux sinon c'est la fin de partie
            finDePartie();


        }
    }

    private void niveauEstTermine() {

        var positionDerniereMaison = (maisons.get(maisons.size() - 1)).getPositionX();
        var limiteFinNiveau = positionDerniereMaison + 1.5 * MainJavaFX.WIDTH;

        if (camelot.getPositionX() > limiteFinNiveau) {
            niveauSuivant();
        }


    }

    private void finDePartie() {

        if (nbJournaux == 0 && journaux.isEmpty()) {
            ecranDeChargement = new EcranDeChargement(
                    "Rupture de stocks \nArgent collecté : " + barreAffichage.getArgentTotal() + "$"
            );
            chargementEnCours = true;
            finDePartie = true;
        }

    }

    private void gererLancementJournaux() {
        if (journalPeutEtreLance() && nbJournaux > 0) {
            if (Input.isKeyPressed(KeyCode.Z) || Input.isKeyPressed(KeyCode.X)) {

                var journal = new Journal(camelot.getCentre(), masseJournaux);
                journal.calculerVitesseInitiale(camelot);
                journaux.add(journal);
                nbJournaux--;
                barreAffichage.setNbJournaux(nbJournaux);
                tempsApresLancer = System.nanoTime();
            }

        }
    }

    public void draw(GraphicsContext context) {
        if (chargementEnCours) {
            ecranDeChargement.draw(context);
            if (ecranDeChargement.estTermine()) {
                chargementEnCours = false;

                if (finDePartie) {
                    recommencerPartie();
                    finDePartie = false;
                }
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

            //Dessin des journaux
            for (var journal : journaux) {
                journal.draw(context, camera);
            }
            //Activation du mode debogage selon la touche D
            debogage.draw(context, camera, maisons, journaux);

            for (var particule : particules) {
                particule.draw(context, camera);
            }
            //Dessin barre d'affichage
            barreAffichage.draw(context, camera);


        }

    }

    public void enCollisionJournal() {

        for (Maison maison : maisons) {
            var objetTouche = false;


            for (Journal journal : journaux) {


                if (maison.isaDesFenetres()) {
                    for (Fenetre fenetre : maison.getFenetres()) {
                        fenetre.enCollisionJournal(journal, barreAffichage);
                        if (!objetTouche) {
                            if (fenetre.testCollision(journal)) {
                                objetTouche = true;
                            }
                        }

                    }
                }
                maison.getBoiteAuxLettres().enCollisionJournal(journal, barreAffichage);
                if (!objetTouche) {
                    if (maison.getBoiteAuxLettres().testCollision(journal)) {
                        objetTouche = true;
                    }
                }
                if (objetTouche) {
                    journaux.remove(journal);
                }

            }


        }
    }

    private void genererParticules() {
        particules = new ArrayList<>();
        int nbParticules = Math.min((niveauActuel - 1) * 30, 400);

        for (int i = 0; i < nbParticules; i++) {

            // * Nb maisons

            //Toute la largeur du niveau inclu la largeur de l'écran mais aussi le nombre de maisons
            double positionX = RANDOM.nextDouble(0, MainJavaFX.WIDTH * NB_MAISONS);
            double positionY = RANDOM.nextDouble(0, MainJavaFX.HEIGHT);
            double teinte = RANDOM.nextDouble(0, 360);
            Color couleur = Color.hsb(teinte, 1, 1);

            particules.add(new Particule(positionX, positionY, couleur));

        }

    }


    public void niveauSuivant() {
        niveauActuel++;
        initialiserNiveau(niveauActuel, nbJournaux);
        journaux.clear();
        chargementEnCours = true;
    }

    private boolean journalPeutEtreLance() {
        double tempsEcoule = (System.nanoTime() - tempsApresLancer) * 1e-9;
        return tempsEcoule >= DELAI_ENTRE_LANCERS;
    }

    private void recommencerPartie() {
        journaux.clear();
        initialiserNiveau(1, 0);
        finDePartie = false;
        chargementEnCours = true;
    }

    public void ajouterJournaux(int ajout) {
        nbJournaux += ajout;
        barreAffichage.setNbJournaux(nbJournaux);
    }

    public  void mettreStockJournauxAZero(){
        nbJournaux = 0;
        barreAffichage.setNbJournaux(nbJournaux);
    }


}




