package diceforge.moteur;

import diceforge.serveur.Serveur;
import java.util.Random;


public class Manche {

    private Inventaire[] inventaires;
    private Serveur serveur;
    public int currentJoueur;
    Random rand;

    /**
     *
     * @param rand objet random
     * @param serveur le serveur de la partie
     * @param inventaires les inventaires des joueurs
     */
    public Manche(Random rand, Serveur serveur, Inventaire[] inventaires){
        this.rand = rand;
        this.serveur = serveur;
        this.inventaires = inventaires;
        this.currentJoueur = 0;
    }

    /**
     * permet de jouer et faire lancer les dés au Joueur
     */
    public void jouer(){
        for (int i = 0; i < inventaires.length; i++) {
            lanceMesDes(i);
        }
        serveur.transfereDemandeDeJouer(currentJoueur);
    }

    /**
     * Permet lancer les de du joueur et d'acheter une carte
     */
    public void lanceMesDes(int i) {

        Log.println("Le joueur " + i +" lance les dés");

        inventaires[i].lancementDe(inventaires[i].getDe1());
        inventaires[i].lancementDe(inventaires[i].getDe2());


    }

    /**
     * Rend le joueur qui joue actuellement
     * @return currentJoueur
     */
    public int getCurrentJoueur() {
        return currentJoueur;
    }
}
