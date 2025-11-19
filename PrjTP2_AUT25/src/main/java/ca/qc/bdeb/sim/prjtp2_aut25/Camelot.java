package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;


public class Camelot extends ObjetDuJeu {
    private boolean toucheLeSol;
    public static final double VITESSE_BASE = 400;
    private static final double VITESSE_MIN = 200;
    private static final double VITESSE_MAX = 600;
    public static final double ACCELERATION_CONTROLE = 300;
    private static final double VITESSE_SAUT = 500;

    public Camelot() {
        super(new Point2D(180, MainJavaFX.HEIGHT - 144),
                new Point2D(VITESSE_BASE, 0),
                new Point2D(172, 144),
                new Point2D(0, 1500));

        this.toucheLeSol = true;
    }

    @Override
    public void update(double deltaTemps) {

        //Gère les inputs (touches du clavier)
        gererInput();

        //Applique la physique (vélocité et position)
        super.updatePhysique(deltaTemps);

        //Limite la vitesse horizontale
        double vitesseX = Math.clamp(velocite.getX(), VITESSE_MIN, VITESSE_MAX);
        velocite = new Point2D(vitesseX, velocite.getY());

        //Vérifie si le camelot touche le sol
        toucheLeSol();

    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        var imgCamelot = ImageManager.getImage(choisirImageAAfficher());
        var coordoEcran = camera.coordoEcran(position);

        context.drawImage(
                imgCamelot,
                coordoEcran.getX(),
                coordoEcran.getY(),
                taille.getX(),
                taille.getY()
        );


    }

    public String choisirImageAAfficher() {
        double index;
        double tempsTotal = System.nanoTime() * 1e-9;
        index = Math.floor(tempsTotal * 4) % 2;
        if (index == 0) {
            return "camelot1.png";
        } else {
            return "camelot2.png";
        }

    }

    private void gererInput() {
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean sauter = Input.isKeyPressed(KeyCode.SPACE) || Input.isKeyPressed(KeyCode.UP);

        System.out.println("Gauche :" + gauche + " Droite : " + droite + " Vitesse : " + velocite.getX());

        //---Gestion accélération horizontale---

        if (gauche) {
            //Flèche gauche --> ralentir
            acceleration = new Point2D(-ACCELERATION_CONTROLE, acceleration.getY());
            System.out.println("RALENTIR ! Accélération: -300 ");
        } else if (droite) {
            //Flèche droite --> accélérer
            acceleration = new Point2D(ACCELERATION_CONTROLE, acceleration.getY());
            System.out.println("ACCÉLÉRATION ! : + 300");
        } else if (velocite.getX() < VITESSE_BASE - 1) {
            // En bas de 399 --> accélérer vers la vitesse de base
            acceleration = new Point2D(ACCELERATION_CONTROLE, acceleration.getY());
            System.out.println("Retour à 400 (trop lent)");
        } else if (velocite.getX() > VITESSE_BASE + 1) {
            //En haut de 401 ---> ralentir vers la vitesse de base
            acceleration = new Point2D(-ACCELERATION_CONTROLE, acceleration.getY());
            System.out.println("Retour à 400 (trop rapide)");
        } else {
            //Entre 399 et 401 --> Assez proche donc pas d'accélération
            acceleration = new Point2D(0, acceleration.getY());
            System.out.println("Stable à 400");

        }

        //---Gestion accélération verticale (saut)---

        if (toucheLeSol && sauter) {
            velocite = new Point2D(velocite.getX(), -VITESSE_SAUT);
            toucheLeSol = false;
            System.out.println("saut!!!!");
        }


    }

    private void toucheLeSol() {

        if (position.getY() >= MainJavaFX.HEIGHT - taille.getY()) {
            toucheLeSol = true;
            velocite = new Point2D(velocite.getX(), 0);
            position = new Point2D(position.getX(), MainJavaFX.HEIGHT - taille.getY());
        } else {
            toucheLeSol = false;
        }
    }


}

