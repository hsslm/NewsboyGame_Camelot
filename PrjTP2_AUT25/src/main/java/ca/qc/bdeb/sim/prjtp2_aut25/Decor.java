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
            acceleration = new Point2D(300, acceleration.getY());
        } else if (droite) {
            acceleration = new Point2D(-300, acceleration.getY());
        } else if (!droite && !gauche && velocite.getX() != -400) {
            int signe = velocite.getX() > 0 ? -1 : +1;
            acceleration = new Point2D(signe * -300, acceleration.getY());
        }


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
