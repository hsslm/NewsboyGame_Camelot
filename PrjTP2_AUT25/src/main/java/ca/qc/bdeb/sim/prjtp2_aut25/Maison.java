package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Maison extends ObjetDuJeu{
    public Maison() {
        super(new Point2D(0,0), new Point2D(0,0), new Point2D(192,96), new Point2D(0,0));
    }

    @Override
    public void draw(GraphicsContext contexte, Camera camera) {


        for(double y = 0;y<MainJavaFX.HEIGHT;y+= taille.getY()){
            for(double x = 0;x<MainJavaFX.WIDTH;x+=taille.getX()){
                contexte.drawImage(new Image("brique.png"),x,y);
            }
        }


    }
}
