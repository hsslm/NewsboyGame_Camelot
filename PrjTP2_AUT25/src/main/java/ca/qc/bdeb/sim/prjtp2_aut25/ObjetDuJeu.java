package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetDuJeu {
    protected Point2D position;
    protected Point2D velocite;
    protected Point2D taille;
    protected Point2D acceleration;

    public ObjetDuJeu(Point2D position, Point2D velocite, Point2D taille, Point2D acceleration) {
        this.position = position;
        this.velocite = velocite;
        this.taille = taille;
        this.acceleration = acceleration;
    }

    protected ObjetDuJeu() {
    }

    public void update(double deltaTemps){
        updatePhysique(deltaTemps);
    }
    protected void updatePhysique(double deltaTemps){
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

    }


    public boolean testCollision(Journal journal){
        if(journal.getDroite()>getGauche()&&journal.getDroite()<getDroite()
                &&journal.getHaut()<getBas()&&journal.getHaut()>getHaut()){


            return true;

        }else {
            return false;
        }

    }
    public abstract void draw(GraphicsContext contexte,Camera camera);

    public double getHaut() {
        return position.getY();
    }
    public double getBas() {
        return position.getY() + taille.getY();
    }
    public double getGauche() {
        return position.getX();
    }
    public double getDroite() {
        return position.getX() + taille.getX();
    }
    public Point2D getCentre() {
        return position.add(taille.multiply(1/2.0));
    }
    public Point2D getVelocite(){ return getVelocite();}

}
