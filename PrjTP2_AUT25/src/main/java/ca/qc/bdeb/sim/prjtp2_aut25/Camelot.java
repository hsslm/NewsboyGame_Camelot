package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Camelot extends ObjetDuJeu {
    public Camelot() {
        super(new Point2D(180, MainJavaFX.HEIGHT - 144), new Point2D(400, 0), new Point2D(172, 144), new Point2D(0, 1500));
    }
    


    @Override
    public void draw(GraphicsContext context, Camera camera) {

        var imgCamelot = new Image(choisirImageAAfficher());

        // On doit d'abord faire la transformation
        var coordoEcran = camera.coordoEcran(position);

        context.drawImage(
                imgCamelot,coordoEcran.getX(), coordoEcran.getY(),
                taille.getX(), taille.getY()
        );
    }
    public String choisirImageAAfficher(){
        double index;
        double tempsTotal = System.nanoTime()*1e-9;
        index = Math.floor(tempsTotal * 4)%2;
        if(index==0){
           return "camelot1.png";
        }else{
            return "camelot2.png";
        }

    }
}
