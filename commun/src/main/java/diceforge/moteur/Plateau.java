package diceforge.moteur;

public class Plateau {

    public Ile[] listeIles;

    Plateau(){
        genererIles();
    }

    /**
     * Génère la liste des îles disponible sur le plateau
     */
    public void genererIles(){
        listeIles  = new Ile[7];

        for(int i = 0; i < listeIles.length; i++){
            listeIles[i] = new Ile(i+1);
        }
    }
}
