package ca.qc.bdeb.sim.prjtp2_aut25.Maison;

public class Portefeuille {

    private int montantTotal;

    public void perdMontant(int montant){
        montantTotal =-montant;
    }

    public void gagneMontant(int montant){
        montantTotal =+ montantTotal;
    }
}
