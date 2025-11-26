package ca.qc.bdeb.sim.prjtp2_aut25;

import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Fenetre;
import ca.qc.bdeb.sim.prjtp2_aut25.Maison.Maison;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Debogage {





    public void draw(GraphicsContext context, Camera camera, ArrayList<Maison> maisons, ArrayList<Journal> journaux){

        context.setStroke(Color.YELLOW);

        context.strokeLine(0.2*MainJavaFX.WIDTH,MainJavaFX.HEIGHT,0.2*MainJavaFX.WIDTH,0);

        for(Maison maison : maisons){
            context.strokeRect(camera.coordoEcran(maison.getBoiteAuxLettres().position).getX(), camera.coordoEcran(maison.getBoiteAuxLettres().position).getY(),
                    maison.getBoiteAuxLettres().taille.getX(),maison.getBoiteAuxLettres().taille.getY());

           if(maison.isaDesFenetres()) {
               for (Fenetre fenetre : maison.getFenetres()) {
                   context.strokeRect(camera.coordoEcran(fenetre.position).getX(), camera.coordoEcran(fenetre.position).getY(),
                           fenetre.taille.getX(), fenetre.taille.getY());

               }
           }
        }
        if(!journaux.isEmpty()){
            for(Journal journal : journaux){
                context.strokeRect(camera.coordoEcran(journal.position).getX(), camera.coordoEcran(journal.position).getY(),
                        journal.taille.getX(),journal.taille.getY());
            }
        }

    }
}
