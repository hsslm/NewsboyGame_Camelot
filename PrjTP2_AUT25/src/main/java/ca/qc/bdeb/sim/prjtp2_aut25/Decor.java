package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Decor extends ObjetDuJeu {

    private Point2D tailleBrique = new Point2D(192, 96);
    private Image imageBrique = new Image("brique.png");

    public Decor() {
        super(Point2D.ZERO,
                new Point2D(-400, 0),
                new Point2D(192, 96),
                Point2D.ZERO
        );
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);

        if (gauche) {
            acceleration = new Point2D(Camelot.ACCELERATION_CONTROLE, acceleration.getY());
        } else if (droite) {
            acceleration = new Point2D(-Camelot.ACCELERATION_CONTROLE, acceleration.getY());
        } else if (velocite.getX() < -Camelot.VITESSE_BASE) {
            acceleration = new Point2D(Camelot.ACCELERATION_CONTROLE, acceleration.getY());
        } else if(velocite.getX() > -Camelot.VITESSE_BASE){
            acceleration = new Point2D(-Camelot.ACCELERATION_CONTROLE, acceleration.getY());
        }

        super.update(deltaTemps);

        //Limite la vitesse du décor (selon le camelot)
        double vitesseX = Math.clamp(velocite.getX(), -600,-200);
        velocite = new Point2D(vitesseX,velocite.getY());

    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {


        for (double y = position.getY(); y < MainJavaFX.HEIGHT; y += taille.getY()) {
            for (double x = position.getX(); x < MainJavaFX.WIDTH; x += taille.getX()) {
                context.drawImage(new Image("brique.png"), x, y);
            }
        }
    }


}
