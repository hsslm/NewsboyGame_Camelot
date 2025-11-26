package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class BarreAffichage {
    private int nbJournaux;
    private int argent;
    private ArrayList<Integer> adresses;
    private Image imgJournal;
    private Image imgArgent;
    private Image imgMaison;


    public BarreAffichage(int nbJournaux, ArrayList<Integer> adresses,int argent) {
        this.nbJournaux = nbJournaux;
        this.adresses = adresses;
        this.argent = argent;
        this.imgJournal = ImageManager.getImage("icone-journal.png");
        this.imgArgent = ImageManager.getImage("icone-dollar.png");
        this.imgMaison = ImageManager.getImage("icone-maison.png");
    }

    public void setAdresses(ArrayList<Integer> adresses) {
        this.adresses = adresses;
    }

    public void setNbJournaux(int nbJournaux) {
        this.nbJournaux = nbJournaux;
    }

    public void ajouterArgent(int sommeAAjouter){
        argent+=sommeAAjouter;
    }
    public void retirerArgent(int sommeARetirer){
        argent-=sommeARetirer;
    }


    public void draw(GraphicsContext context, Camera camera){

         //Icone de journal et quantité de journaux

        context.drawImage(imgJournal,5,8);
        context.setFill(Color.WHITE);
        context.setFont(new Font("Comic sans MS",25));
        context.fillText(String.valueOf(nbJournaux),60,25);

        //Icone d'argent et quantité

        context.drawImage(imgArgent,80,10);
        context.fillText(String.valueOf(argent),140,25);

        //Icone de maison et adresses de livraison
        context.drawImage(imgMaison,155,8);
        context.setFont(new Font("Comic sans MS",20));
        var index = 0;
        for(Integer i : adresses){
            context.fillText(String.valueOf(i),215+index,25);
            index+=45;
        }

        //Fond gris et opacité

        Color grisTransparent = Color.gray(0.2,0.5);
        context.setFill(grisTransparent);
        context.fillRect(0, 0,MainJavaFX.WIDTH,50);

    }

}
