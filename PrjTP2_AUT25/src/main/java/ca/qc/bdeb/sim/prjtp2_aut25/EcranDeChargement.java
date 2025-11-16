package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javafx.scene.paint.Color;

public class EcranDeChargement {
    private String texte;
    private long tempsDebut;
    private boolean termine;

    public EcranDeChargement(String texte) {

        this.texte = texte;
        this.tempsDebut = System.nanoTime();
    }

    public void draw(GraphicsContext context) {

        //Fond noir
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, MainJavaFX.WIDTH, MainJavaFX.HEIGHT);

        //Texte vert affichant le niveau
        context.setFill(Color.HOTPINK);
        context.setFont(Font.font("Comic Sans MS", 55));
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);

        context.fillText(texte,
                MainJavaFX.WIDTH / 2,
                MainJavaFX.HEIGHT / 2
        );


    }

    public boolean estTermine() {
        //Vérifie si 3 secondes se sont écoulées
        long tempsActuel = System.nanoTime();
        double tempsEcoule = (tempsActuel - tempsDebut) * 1e-9;
        return tempsEcoule >= 3;
    }
}
