package ca.qc.bdeb.sim.prjtp2_aut25;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;

//est ce que cest correct que tt sois en rose

/**
 * Classe Partie : gère l'état global du jeu
 */
public class Partie {
    //Constantes
    private static final Random RANDOM = new Random();
    private static final int NB_MAISONS = 12;
    private static final int JOURNAUX_PAR_NIVEAUX = 12;
    private static final double DELAI_ENTRE_LANCERS = 0.5;

    //Objets du jeu
    private Decor decor;
    private Camelot camelot;
    private Camera camera;
    private ArrayList<Maison> maisons;
    private ArrayList<Integer> adresses;
    private ArrayList<Journal> journaux;
    private ArrayList<Particule> particules;
    private BarreAffichage barreAffichage;
    private Debogage debogage;


    //État du jeu
    private int niveauActuel;
    private int nbJournaux;
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

    // Getters et setters
    public void setParticules(ArrayList<Particule> particules) {
        this.particules = particules;
    }

    public ArrayList<Maison> getMaisons() {
        return maisons;
    }

    public ArrayList<Particule> getParticules() {
        return particules;
    }

    /**
     * Initialise un niveau avec les paramètres de base du jeu
     *
     * @param niveauActuel       numéro du niveau en cours
     * @param nbJournauxRestants journaux restants du niveau précédent
     */
    private void initialiserNiveau(int niveauActuel, int nbJournauxRestants) {

        this.niveauActuel = niveauActuel;
        masseJournaux = RANDOM.nextDouble(1, 2);

        nbJournaux = JOURNAUX_PAR_NIVEAUX + nbJournauxRestants;
        tempsApresLancer = 0;

        initialiserObjetsJeu();
        initialiserAffichage();

        //Génère des particules uniquement à partir du niveau 2
        if (niveauActuel >= 2) {
            genererParticules();
        }
    }

    /**
     * Initialise les éléments d'affichage (barre et écran de chargement)
     */
    private void initialiserAffichage() {
        barreAffichage = new BarreAffichage(nbJournaux, adresses, 0);
        ecranDeChargement = new EcranDeChargement("Niveau " + niveauActuel);
    }

    /**
     * Initialise les objets principaux du jeux
     */
    private void initialiserObjetsJeu() {
        this.camelot = new Camelot();
        this.decor = new Decor();
        this.camera = new Camera();
        genererMaisons();
    }

    /**
     * Génère les maisons du niveau avec leurs adresses et positions
     */
    private void genererMaisons() {
        maisons = new ArrayList<>();
        adresses = new ArrayList<>();

        var premiereAdresse = RANDOM.nextInt(100, 951);

        for (int i = 0; i < NB_MAISONS; i++) {
            var adresse = premiereAdresse + (i * 2);
            var positionX = 1300 + (i * 1300);

            var maison = new Maison(adresse, positionX);
            maisons.add(maison);


            if (maison.estAbonneeAuJournal()) {
                adresses.add(maison.getAdresse());
            }
        }
    }

    /**
     * Met à jour l'état du jeu à chaque frame
     *
     * @param deltaTemps temps écoulé depuis la dernière mise à jour
     */
    public void update(double deltaTemps) {
        if (!chargementEnCours) {
            camelot.update(deltaTemps);
            camera.suivreCamelot(camelot);

            gererLancementJournaux();
            updateJournaux(deltaTemps);

            //Pour que debogage puisse agir sur partie on transmet l'instance
            debogage.update(this);

            niveauEstTermine();
            ecranDeChargementFinDePartie();

            quitterProgrammeTouche();

        }
    }

    /**
     * Met à jour les journaux et supprime ceux sortis de l'écran
     */
    private void updateJournaux(double deltaTemps) {
        for (var journal : journaux) {
            journal.update(deltaTemps, particules);
        }
        supprimerJournauxHorsEcran();
    }

    /**
     * Supprime les journaux sortis de l'écran
     */
    private void supprimerJournauxHorsEcran() {
        journaux.removeIf(journal1 ->
                journal1.getBas() > MainJavaFX.HEIGHT ||
                        journal1.getHaut() < 0 ||
                        journal1.getGauche() < 0
        );
    }

    /**
     * Vérifie si la touche Échap est préssées --> quitte le programme
     */
    private void quitterProgrammeTouche() {
        if (Input.isKeyPressed(KeyCode.ESCAPE)) {
            System.exit(0);
        }
    }

    /**
     * Vérifie si le camelot a dépassée la dernière maison --> passe au niveau suivant
     */
    private void niveauEstTermine() {
        var limiteFinNiveau = calculerLimiteNiveau();

        if (camelot.getPositionX() > limiteFinNiveau) {
            niveauSuivant();
        }
    }

    /**
     * @return la limite de fin de niveau
     */
    public double calculerLimiteNiveau() {
        var positionDerniereMaison = (maisons.get(maisons.size() - 1)).getPositionX();
        return positionDerniereMaison + 1.5 * MainJavaFX.WIDTH;
    }

    /**
     * Affiche l'écran de fin de partie si plus de journaux
     */
    private void ecranDeChargementFinDePartie() {
        if (nbJournaux == 0 && journaux.isEmpty()) {
            ecranDeChargement = new EcranDeChargement(
                    "Rupture de stocks \nArgent collecté : " + barreAffichage.getArgentTotal() + "$"
            );
            chargementEnCours = true;
            finDePartie = true;
        }
    }

    /**
     * Gère l'entrée utilisateur pour lancer un journal
     */
    private void gererLancementJournaux() {
        if (journalPeutEtreLance() && nbJournaux > 0 && toucheDeLancementActive()) {
            lancerJournal();
        }
    }

    /**
     * Crée et lance un journal, ensuite met à jour le stock et l'affichage
     */
    private void lancerJournal() {
        var journal = new Journal(camelot.getCentre(), masseJournaux);
        journal.calculerVitesseInitiale(camelot);
        journaux.add(journal);

        nbJournaux--;
        barreAffichage.setNbJournaux(nbJournaux);
        //Reset du timer
        tempsApresLancer = System.nanoTime();
    }

    /**
     * Vérifie si une touche de lancement est active (Z ou X)
     */
    private boolean toucheDeLancementActive() {
        return Input.isKeyPressed(KeyCode.Z) || Input.isKeyPressed(KeyCode.X);
    }

    /**
     * Valide le délai entre deux lancers
     */
    private boolean journalPeutEtreLance() {
        double tempsEcoule = (System.nanoTime() - tempsApresLancer) * 1e-9;
        return tempsEcoule >= DELAI_ENTRE_LANCERS;
    }

    /**
     * Dessine tous les éléments du jeu
     */
    public void draw(GraphicsContext context) {
        if (chargementEnCours) {
            //Affiche l'écran de chargement tant qu'il n'est pas terminé
            ecranDeChargement.draw(context);
            if (ecranDeChargement.estTermine()) {
                chargementEnCours = false;

                //Si on était en fin de partie, on redémarre au niveau 1
                if (finDePartie) {
                    redemarrage();
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
            debogage.draw(context, camera, maisons, journaux, particules,this);

            //Dessin des particules (niveau 2+)
            if (niveauActuel >= 2) {
                for (var particule : particules) {
                    particule.draw(context, camera);
                }
            }

            //Dessin barre d'affichage
            barreAffichage.draw(context, camera);

        }

    }

    /**
     * Gère les collisions entre journaux et objets des maisons (fenêtres + boîtes aux lettres)
     */
    public void enCollisionJournal() {

        var journauxASupprimer = new ArrayList<Journal>();

        for (var maison : maisons) {
            for (var journal : journaux) {
                var objetTouche = false;


                // Collisions avec les fenêtres
                if (maison.isaDesFenetres()) {
                    for (var fenetre : maison.getFenetres()) {
                        // Applique l'effet (couleur + argent)
                        fenetre.enCollisionJournal(journal, barreAffichage);

                        // Détecte la collision pour marquer le journal à supprimer
                        if (!objetTouche && fenetre.testCollision(journal)) {
                            objetTouche = true;
                        }
                    }
                }

                // Collision avec la boîte aux lettres
                var boite = maison.getBoiteAuxLettres();
                boite.enCollisionJournal(journal, barreAffichage);

                if (!objetTouche && boite.testCollision(journal)) {
                    objetTouche = true;
                }

                // Si un objet a été touché,il est marqué pour supression
                if (objetTouche) {
                    if (!journauxASupprimer.contains(journal)) {
                        journauxASupprimer.add(journal);
                    }
                }
            }
        }

        // Retire tous les journaux marqués
        journaux.removeAll(journauxASupprimer);

    }

    /**
     * Génère des particules selon le niveau
     */
    private void genererParticules() {
        particules = new ArrayList<>();
        int nbParticules = Math.min((niveauActuel - 1) * 30, 400);

        for (int i = 0; i < nbParticules; i++) {

            //Toute la largeur du niveau inclu la largeur de l'écran mais aussi le nombre de maisons
            double positionX = RANDOM.nextDouble(0, MainJavaFX.WIDTH * NB_MAISONS);
            double positionY = RANDOM.nextDouble(0, MainJavaFX.HEIGHT);
            particules.add(new Particule(positionX, positionY));
        }
    }


    /**
     * Passe au niveau suivant en conservant le nombre de journaux
     */
    public void niveauSuivant() {
        niveauActuel++;
        initialiserNiveau(niveauActuel, nbJournaux);
        journaux.clear();
        chargementEnCours = true;
    }

    /**
     * Redémarre une partie complète (retour au niveau 1, stock remis à zéro)
     */
    private void redemarrage() {
        journaux.clear();
        particules.clear();
        initialiserNiveau(1, 0);
        finDePartie = false;
        chargementEnCours = true;
    }

    /**
     * Ajoute des journaux au stock et met à jour l'affichage
     */
    public void ajouterJournaux(int ajout) {
        nbJournaux += ajout;
        barreAffichage.setNbJournaux(nbJournaux);
    }

    /**
     * Met le stock à zéro et déclenche la fin de partie
     */
    public void mettreStockJournauxAZero() {
        nbJournaux = 0;
        journaux.clear();
        particules.clear();
        barreAffichage.setNbJournaux(nbJournaux);
        ecranDeChargementFinDePartie();
    }


}




