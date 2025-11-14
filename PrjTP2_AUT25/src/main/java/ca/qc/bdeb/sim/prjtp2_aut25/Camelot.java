package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Camelot extends ObjetDuJeu {
    public Camelot() {
        super(new Point2D(180, MainJavaFX.HEIGHT - 144), new Point2D(400, 0), new Point2D(172, 144), new Point2D(0, 1500));
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
        velocite = new Point2D(Math.clamp(velocite.getX(), 200,600), velocite.getY());
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);

        if(gauche){
            acceleration = new Point2D(-300,acceleration.getY());
        }
        else if(droite){
            acceleration  = new Point2D(300, acceleration.getY());
        }
        else if(!droite&&!gauche&& velocite.getX()!=400){
            int signe = velocite.getX() > 0 ? -1 : +1;
            acceleration = new Point2D(signe * 300, acceleration.getY());
        }


    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        var imgCamelot = new Image(choisirImageAAfficher());
        // On doit d'abord faire la transformation
        var coordoEcran = camera.coordoEcran(position);

        context.drawImage(
               imgCamelot, coordoEcran.getX(), coordoEcran.getY(),
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
