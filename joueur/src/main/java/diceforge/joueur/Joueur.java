package diceforge.joueur;

import diceforge.client.Client;
import diceforge.moteur.Carte;
import diceforge.moteur.Inventaire;
import diceforge.moteur.Log;

import static diceforge.echange.Protocole.*;


public class Joueur{

    private final IA ia;
    private int numero;
    private Client client;
    private int score;
    private Identite identite;
    private boolean isDone = false;

    public Joueur(int numero, IA ia){
        this.numero = numero;
        this.ia = ia;
        setIdentite(new Identite(numero+""));
    }

	public int getNumero() {
        return numero;
	}

	public int getScore() {
        return score;
    }

    public void setClient(Client client) {
        this.client = client;
        this.client.connnexion();
        this.client.transfereMessage(IDENTIFICATION, getIdentite());

    }

    public void IArandom(Inventaire inventaire){

    }

    public void IAcalcule(Inventaire inventaire){
        int i,j;
        int maxMontant = 0;
        int carteLaPlusForteI = 0;
        int carteLaPlusForteJ = 0;
        Carte[] carte = inventaire.getCartesDuJoueur();
        for(int S = 0;S<inventaire.getNbDeCartes();S++) {
            if (carte[S] == Carte.SABOT) {
                inventaire.lancementDe(inventaire.getDe1());
            }
            if ((carte[S] == Carte.SAGE) && (inventaire.getOr() > 9)) {
                inventaire.incrementOr(-3);
                inventaire.setPointgloire(inventaire.getPointgloire() + 4);
            }
            if (carte[S] == Carte.CHOUETTE) {
                if(inventaire.getFragmentsLunaires() <= inventaire.getOr() && inventaire.getFragmentsLunaires() <= inventaire.getFragmentsSolaires()) {
                    inventaire.incrementFragmentsLunaires(1);
                }
                else if(inventaire.getFragmentsSolaires() <= inventaire.getOr() && inventaire.getFragmentsSolaires() <= inventaire.getFragmentsLunaires()) {
                    inventaire.incrementFragmentsSolaires(1);
                }
                else{
                    inventaire.incrementOr(1);
                }
            }
        }
        for (i = 0; i < inventaire.plateau.listeIles.length; i++) {
            for(j = 0; j < inventaire.plateau.listeIles[i].getTableauCarteIle().length; j++){
                if(inventaire.plateau.listeIles[i].getTableauCarteIle()[j].getMontant() < inventaire.getFragmentsLunaires()) {
                    if(maxMontant < inventaire.plateau.listeIles[i].getTableauCarteIle()[j].getMontant() ){
                        maxMontant = inventaire.plateau.listeIles[i].getTableauCarteIle()[j].getMontant() ;
                        carteLaPlusForteI = i;
                        carteLaPlusForteJ = j;
                    }
                }
                break;
            }
        }

        inventaire.acheterUneCarte(inventaire.plateau.listeIles[carteLaPlusForteI].getTableauCarteIle()[carteLaPlusForteJ]);

    }

    public Identite getIdentite() {
        return this.identite;
    }

    public Client getClient() {
        return client;
    }

    public void joue(Inventaire inventaire) {
        this.isDone = false;
        Log.println("Inventaire du joueur "+this.numero+" - Or: "+inventaire.getOr()+" Lune : "+inventaire.getFragmentsLunaires());
        ia.joue(inventaire, this);
        IArandom(inventaire);
    }

    public void setIdentite(Identite identite) {
        this.identite = identite;
    }

    public boolean isDone() {
        return isDone;
    }
}


