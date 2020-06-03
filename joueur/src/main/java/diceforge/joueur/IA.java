package diceforge.joueur;

import diceforge.moteur.Carte;
import diceforge.moteur.Forge;
import diceforge.moteur.Inventaire;

public abstract class IA {

    /**
     * Fonction qui permet à l'IA du joueur de faire des choix et donc de jouer
     * @param inventaire L'inventaire du joueur
     * @param joueur Le joueur qui joue
     */
    public abstract void joue(Inventaire inventaire, Joueur joueur);

    /**
     * Fonction qui permet à l'IA du joueur de choisir une face à remplacer lorsque qu'il forge une face
     * @param inventaire L'inventaire du joueur
     */
    public abstract String getFaceARemplacer(Inventaire inventaire);

    /**
     * Retourne le tableau de carte achetable avec l'inventaire actuel du joueur
     * @param inventaire L'inventaire du joueur
     * @return Tableau de carte achetable par le joueur
     */
    public String[] getTableauCarteAchetable(Inventaire inventaire){
        String[] tableauCarte = new String[Carte.nombreCartes+1];
        int nbCarteAchetable = 0;
        for(int i=0; i<inventaire.plateau.listeIles.length; i++){
            for(int j=0; j<inventaire.plateau.listeIles[i].getTableauCarteIle().length; j++){
                int ressourceDisponible;
                if(inventaire.plateau.listeIles[i].getTableauCarteIle()[j].getMonnaie() == "lune"){
                    ressourceDisponible = inventaire.getFragmentsLunaires();
                }else{
                    ressourceDisponible = inventaire.getFragmentsSolaires();
                }
                if(inventaire.plateau.listeIles[i].getTableauCarteIle()[j].getNombreAchetable() > 0 && inventaire.plateau.listeIles[i].getTableauCarteIle()[j].getMontant() <= ressourceDisponible){
                    nbCarteAchetable++;
                    tableauCarte[nbCarteAchetable] = i+";"+j;
                }
            }
        }
        tableauCarte[0] = ""+nbCarteAchetable;
        return tableauCarte;
    }

    /**
     * Fonction renvoyant le tableau des faces forgeable avec l'inventaire actuel du joueur
     * @param inventaire L'inventaire du joueur
     * @return Tableau de face forgeable par le joueur
     */
    public String[] getTableauFaceForgeable(Inventaire inventaire){
        String[] tableauFace = new String[Forge.nombreFaceForge+1];
        int nbFaceForgeable = 0;
        for(int i = 0; i < inventaire.forge.zoneF.length; i++){
            for(int j = 0; j < inventaire.forge.zoneF[i].getFaceZone().length; j++){
                if(inventaire.forge.zoneF[i].getPrixDansZone() <= inventaire.getOr()) {
                    nbFaceForgeable++;
                    tableauFace[nbFaceForgeable] = i+";"+j;
                }
            }
        }
        tableauFace[0] = ""+nbFaceForgeable;
        return tableauFace;
    }
}
