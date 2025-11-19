package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Journal extends ObjetDuJeu{
    private double masse;

    private final Point2D QUANTITE_HAUT = new Point2D(900,-900);
    private final Point2D QUANTITE_AVANT = new Point2D(150,-1100);
    private long tempsDebut;
    private double tempsAuDernierLancer;
    public Journal(Point2D position, Point2D velocite, double masse) {
        super(position, Point2D.ZERO,new Point2D(52,31) , new Point2D(0,1500));
        this.masse = masse;
        this.tempsDebut = System.nanoTime();
    }


    public void update(double deltaTemps) {//marche
        super.update(deltaTemps);

        if(velocite.magnitude()>=1500){
            double max = 1500;
            velocite = velocite.multiply(max / velocite.magnitude());
        }

    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        var imgJournal = ImageManager.getImage("journal.png");
        var coordoEcran = camera.coordoEcran(position);
        //est-ce que c'est le milieu du camelot ? les journaux doivent partir du milieu du camelot

        context.drawImage(
                imgJournal,
                coordoEcran.getX(),
                coordoEcran.getY()
        );


    }
    public boolean estTermine() {
        //Vérifie si 0.5 seconde se sont écoulées
        long tempsActuel = System.nanoTime();

        double tempsEcoule = (tempsActuel - tempsDebut) * 1e-9;
        return tempsEcoule >= 0.5;
    }
    /*
    Gestion des journaux qui disparaissent; je ne suis pas certaine que ça marche + je ne suis pas sure où ça va


     if(getBas()>MainJavaFX.HEIGHT||getGauche()>MainJavaFX.WIDTH||getDroite()<0){
            contexte.clearRect(taille.getX(),taille.getY(),position.getX(),position.getY());
        }

     */





    //je suis pas sure de cette méthode, je voulais juste pas effacer ce que j'ai fait
    public void lancerJournal(GraphicsContext context,Camelot camelot, Camera camera, int nbJournaux){
        if((Input.isKeyPressed(KeyCode.Z)||Input.isKeyPressed(KeyCode.X))&&nbJournaux>0){
            //keyPressed ne marche pas pour lancer l'objet

            velocite = calculerVitesseInitiale(camelot);
            draw(context,camera);

            System.out.println(nbJournaux);
        }

    }


    //Calcule la vitesse initiale du journal selon sa masse, les touches enfoncées et la vitesse du camelot
    public Point2D calculerVitesseInitiale(Camelot camelot){
        boolean lancerHaut = Input.isKeyPressed(KeyCode.Z);
        boolean lancerAvant = Input.isKeyPressed(KeyCode.X);
        boolean lancerPlusFort = Input.isKeyPressed(KeyCode.SHIFT);

        Point2D vitesseInitiale = Point2D.ZERO;

        if(lancerHaut&&lancerPlusFort) {
            vitesseInitiale = camelot.velocite.add(new Point2D(QUANTITE_HAUT.getX() / masse, QUANTITE_HAUT.getY() / masse));
            vitesseInitiale = vitesseInitiale.multiply(1.5);

        }
        else if(lancerAvant&&lancerPlusFort){
            vitesseInitiale = camelot.velocite.add(new Point2D(QUANTITE_AVANT.getX() / masse, QUANTITE_AVANT.getY() / masse));
            vitesseInitiale = vitesseInitiale.multiply(1.5);

        }
        else if(lancerHaut){
            vitesseInitiale = camelot.velocite.add(new Point2D(QUANTITE_HAUT.getX() / masse, QUANTITE_HAUT.getY() / masse));

        }
        else if(lancerAvant){
            vitesseInitiale = camelot.velocite.add(new Point2D(QUANTITE_AVANT.getX() / masse, QUANTITE_AVANT.getY() / masse));
        }

        return vitesseInitiale;


    }
    public boolean enCollision(ObjetDuJeu objet){



        return false;
    }


}
