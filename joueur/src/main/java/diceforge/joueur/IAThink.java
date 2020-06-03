package diceforge.joueur;

import diceforge.moteur.Face;
import diceforge.moteur.Inventaire;
import diceforge.moteur.Log;

import static diceforge.echange.Protocole.*;

public class IAThink extends IA {

    public IAThink(){
        super();
        this.nbManche = 0;
    }

    private int nbManche;

    @Override
    public void joue(Inventaire inventaire, Joueur joueur) {
        nbManche++;
        String[] tableauCarte = getTableauCarteAchetable(inventaire);
        String[] tableauFace = getTableauFaceForgeable(inventaire);

        if(Integer.parseInt(tableauCarte[0]) > 0 && nbManche > 4) {
            String randomCarte;
            if(Integer.parseInt(tableauCarte[0]) == 1){
                randomCarte = tableauCarte[1];
            }else {
                randomCarte = tableauCarte[inventaire.getRand().nextInt(Integer.parseInt(tableauCarte[0]) - 1) + 1];
            }
            joueur.getClient().transfereMessage(DEMANDER_AU_SERVEUR_DACHETER_UNE_CARTE, new Identite(randomCarte));
        }else if(Integer.parseInt(tableauFace[0]) > 0){
            String randomFace;
            if(Integer.parseInt(tableauCarte[0]) == 1){
                randomFace = tableauFace[1];
            }else {
                randomFace = tableauFace[inventaire.getRand().nextInt(Integer.parseInt(tableauFace[0]) - 1) + 1];
            }
            String randomFaceARemplacer = getFaceARemplacer(inventaire);
            joueur.getClient().transfereMessage(DEMANDER_AU_SERVEUR_DE_FORGER_UNE_FACE, new Identite(randomFaceARemplacer+":"+randomFace));
        }else{
            Log.println("Le joueur "+joueur.getNumero()+" ne fait rien car il ne peut rien faire.");
        }
        joueur.getClient().transfereMessage(DIRE_QUE_LE_JOUEUR_A_FINI, joueur.getIdentite());
    }

    @Override
    public String getFaceARemplacer(Inventaire inventaire) {
        String faceARemplacer = inventaire.getRand().nextInt(2)+";"+inventaire.getRand().nextInt(6);

        return faceARemplacer;
    }
}
