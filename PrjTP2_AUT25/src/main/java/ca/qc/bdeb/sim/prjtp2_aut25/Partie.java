package ca.qc.bdeb.sim.prjtp2_aut25;


import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Fenetre;
import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;

public class Partie {

    //Constantes :
    private static final Random RANDOM = new Random();
    private final int NB_MAISONS = 12;
    private final int JOURNAUX_PAR_NIVEAUX = 12;
    private final double DELAI_ENTRE_LANCERS = 0.5;

    //Objets du jeu :
    private Decor decor;
    private Camelot camelot;
    private Camera camera;
    private ArrayList<Maison> maisons;
    private ArrayList<Integer> adresses;
    private ArrayList<Journal> journaux;
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


    public Partie() {
        this.niveauActuel = 1;
        this.journaux = new ArrayList<>();
        this.nbJournauxRestants = 0;
        this.tempsApresLancer = 0;
        this.barreAffichage = new BarreAffichage(nbJournaux, new ArrayList<>(), 0);
        this.debogage = new Debogage();
        //Création des objets nécéssaires pour le début d'une partie
        demarrerNiveau();
    }

    public void demarrerNiveau() {

        //Objets
        this.camelot = new Camelot();
        this.decor = new Decor();
        this.camera = new Camera();

        //État du niveau
        this.chargementEnCours = true;
        this.nbJournaux = JOURNAUX_PAR_NIVEAUX + nbJournauxRestants;
        this.masseJournaux = RANDOM.nextDouble(1, 2);
        genererMaisons();
        barreAffichage.setAdresses(adresses);
        barreAffichage.setNbJournaux(nbJournaux);
    }

    private void genererMaisons() {
        maisons = new ArrayList<>();
        adresses = new ArrayList<>();

        //Adresse de la première maison : entre 100 et 950
        int premiereAdresse = RANDOM.nextInt(100, 950);

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


    public void creerEcranChargement() {
        this.ecranDeChargement = new EcranDeChargement("Niveau " + niveauActuel);
    }

    public void update(double deltaTemps) {
        if (!chargementEnCours) {

            camelot.update(deltaTemps);
            camera.suivreCamelot(camelot);
            gererLancementJournaux();

            for (var journal : journaux) {
                journal.update(deltaTemps);
                if (journal.getBas() > MainJavaFX.HEIGHT || journal.getHaut() < 0 || journal.getGauche() < 0) {
                    journaux.remove(journal);
                }
            }

            debogage.update(deltaTemps);


        }
    }

    public void gererLancementJournaux() {
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

    public void niveauSuivant() {
        niveauActuel++;
        //Calcule le nombre de journeaux restants pour le prochain niveau
        nbJournauxRestants = nbJournaux;
        nbJournaux = 0;
        journaux.clear();
        demarrerNiveau();
        creerEcranChargement();
    }

    public boolean journalPeutEtreLance() {
        double tempsEcoule = (System.nanoTime() - tempsApresLancer) * 1e-9;
        return tempsEcoule >= DELAI_ENTRE_LANCERS;
    }
}




