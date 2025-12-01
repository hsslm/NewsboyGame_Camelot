package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
/**
 * Classe BarreAffichage : gère l'affichage de la barre d'état
 */
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

    public void setNbJournaux(int nbJournaux) {
        this.nbJournaux = nbJournaux;
    }
    public void ajouterArgent(int sommeAAjouter){
        argent+=sommeAAjouter;
    }
    public void retirerArgent(int sommeARetirer){
        argent-=sommeARetirer;
    }
    public int getArgentTotal(){
        return argent;
    }


    public void draw(GraphicsContext context, Camera camera){

        //Fond gris semi-transparent
        Color grisTransparent = Color.gray(0.2,0.5);
        context.setFill(grisTransparent);
        context.fillRect(0, 0,MainJavaFX.WIDTH,50);

        journalIcone(context);
        argentIcone(context);
        maisonsIcone(context);

    }

    /**
     * Dessine l'icône de maison et les adresses abonnées
     */
    private void maisonsIcone(GraphicsContext context) {
        context.drawImage(imgMaison,155,8);
        context.setFont(new Font("Comic sans MS",20));
        var index = 0;
        for(Integer i : adresses){
            context.fillText(String.valueOf(i),215+index,25);
            index+=45;
        }
    }
    /**
     * Dessine l'icône d'argent et le montant accumulé
     */
    private void argentIcone(GraphicsContext context) {
        context.drawImage(imgArgent,80,10);
        context.fillText(String.valueOf(argent),140,25);
    }

    /**
     * Dessine l'icône de journal et le nombre de journaux restants
     */
    private void journalIcone(GraphicsContext context) {
        context.drawImage(imgJournal,5,8);
        context.setFill(Color.WHITE);
        context.setFont(new Font("Comic sans MS",25));
        context.fillText(String.valueOf(nbJournaux),60,25);
    }

}
