package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Decor extends ObjetDuJeu{
    public Decor() {
        super(new Point2D(0,0), new Point2D(-400,0), new Point2D(192,96), Point2D.ZERO);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);

    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {


        for(double y = position.getY();y<MainJavaFX.HEIGHT;y+= taille.getY()){
            for(double x = position.getX();x<MainJavaFX.WIDTH;x+=taille.getX()){
                context.drawImage(new Image("brique.png"),x,y);
            }
        }
    }

}
