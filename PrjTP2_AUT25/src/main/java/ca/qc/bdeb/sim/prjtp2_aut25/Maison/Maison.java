package ca.qc.bdeb.sim.prjtp2_aut25.Maison;

import ca.qc.bdeb.sim.prjtp2_aut25.Camera;
import ca.qc.bdeb.sim.prjtp2_aut25.ImageManager;
import ca.qc.bdeb.sim.prjtp2_aut25.MainJavaFX;
import ca.qc.bdeb.sim.prjtp2_aut25.ObjetDuJeu;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Random;

public class Maison extends ObjetDuJeu {
    private int adresse;
    private boolean abonneeAuJournal;
    private Image imagePorte;
    //position de la porte (coordonnée x de la maison)
    private Point2D position;
    private BoiteAuxLettres boiteAuxLettres;
    private Random random;
    private boolean aDesFenetres;
    private ArrayList<Fenetre> fenetres

    public int getAdresse() {
        return adresse;
    }

    public boolean isaDesFenetres() {
        return aDesFenetres;
    }

    public ArrayList<Fenetre> getFenetres() {
        return fenetres;
    }

    public boolean estAbonneeAuJournal() {
        return abonneeAuJournal;
    }
    public BoiteAuxLettres getBoiteAuxLettres() {
        return boiteAuxLettres;
    }

    public Maison(int adresse, double positionX) {
        this.random = new Random();
        this.imagePorte = ImageManager.getImage("porte.png");
        double porteY = MainJavaFX.HEIGHT - imagePorte.getHeight();
        this.position = new Point2D(positionX, porteY);
        this.abonneeAuJournal = random.nextBoolean();
        this.adresse = adresse;


        //Crée la boîte à lettres de cette maison
        creerBoiteAuxLettres();
        //crée les fenetres s'il y en a
        creerFenetres();
    }
    private void creerFenetres(){

        int nbFenetres = random.nextInt(0,3);

        if(nbFenetres==0){
            this.aDesFenetres=false;
        }else if(nbFenetres>0){
            this.aDesFenetres = true;
            fenetres = new ArrayList<>();


            for(int i = 0;i<=nbFenetres;i++){

                    fenetres.add(new Fenetre(new Point2D(position.getX()+ 300,50),abonneeAuJournal));
                 if(nbFenetres==2){
                    fenetres.add(new Fenetre(new Point2D(position.getX()+600,50),abonneeAuJournal));
                }
            }
        }



    }

    private void creerBoiteAuxLettres() {
        //Position en x : 200px à droite de la maison
        double boiteX = position.getX() + 200;

        //Position en y : entre 20% et 70% de la hauteur de l'écran
        double minY = MainJavaFX.HEIGHT * 0.20;
        double maxY = MainJavaFX.HEIGHT * 0.7;
        double boiteY = random.nextDouble(minY, maxY);

        this.boiteAuxLettres = new BoiteAuxLettres(new Point2D(boiteX, boiteY), abonneeAuJournal);
    }


    @Override
    public void draw(GraphicsContext context, Camera camera) {

        var coordoEcran = camera.coordoEcran(position);
        //Dessine l'image de la porte
        context.drawImage(imagePorte, coordoEcran.getX(), coordoEcran.getY());

        //À revoir pcq les adresses semblent pas claires
        //Affiche l'adresse sur la porte
        context.setFill(Color.YELLOW);
        context.setFont(Font.font("Comic Sans MS",24));
        context.setTextAlign(TextAlignment.CENTER);

        context.fillText(
                String.valueOf(adresse),
                coordoEcran.getX()+ (imagePorte.getWidth() / 2), //Centre de la porte
                coordoEcran.getY() + 30 //Position verticale selon l'image
        );

        boiteAuxLettres.draw(context, camera);
        if(aDesFenetres){
            for(Fenetre fenetre : fenetres){
                fenetre.draw(context, camera);
            }

        }

    }
}
