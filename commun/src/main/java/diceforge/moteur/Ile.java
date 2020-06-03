package diceforge.moteur;

public class Ile {

    private int numero;
    private int JoueurOccupant;
    private Carte[] tableauCarteIle;

    public Ile(){
        JoueurOccupant = -1;
        tableauCarteIle = null;
    }

    /**
     * constructeur des îles
     * @param num numéro de l'île
     */
    public Ile(int num){
        JoueurOccupant = -1;
        if(num == 1){
            tableauCarteIle = new Carte[2];
            tableauCarteIle[0] = Carte.MARTEAU;
            tableauCarteIle[1] = Carte.COFFRE;
        }else if(num == 2){
            tableauCarteIle = new Carte[2];
            tableauCarteIle[0] = Carte.SABOT;
            tableauCarteIle[1] = Carte.SATYRES;
        }else if(num == 3){
            tableauCarteIle = new Carte[2];
            tableauCarteIle[0] = Carte.MONSTREGLUANT;
            tableauCarteIle[1] = Carte.CASQUE;
        }else if(num == 4){
            tableauCarteIle = new Carte[2];
            tableauCarteIle[0] = Carte.SCORPION;
            tableauCarteIle[1] = Carte.SAGE;
        }else if(num == 5){
            tableauCarteIle = new Carte[3];
            tableauCarteIle[0] = Carte.HERBES;
            tableauCarteIle[1] = Carte.CHOUETTE;
            tableauCarteIle[2] = Carte.MEDUSE;
        }else if(num == 6){
            tableauCarteIle = new Carte[2];
            tableauCarteIle[0] = Carte.MINOTAURE;
            tableauCarteIle[1] = Carte.CHOUETTE;
        }else if(num == 7){
            tableauCarteIle = new Carte[3];
            tableauCarteIle[0] = Carte.MIROIR;
            tableauCarteIle[1] = Carte.SPHINX;
            tableauCarteIle[2] = Carte.HYDRE;
        }else{
            System.out.println("Probleme creation d'ile");
        }
    }

    /**
     * cette methode permet de trouver précisémment une carte
     * @param carteATrouve la carte passé en paramètre
     * @return retourne soit la carte si elle existe sinon null
     */
    public Carte trouverUneCarte(Carte carteATrouve){
        for(int i = 0; i < tableauCarteIle.length; i++){
            if(tableauCarteIle[i] == carteATrouve){
                return tableauCarteIle[i];
            }
        }
        return null;
    }

    /**
     * trouve la carte la plus forte
     * @return la variable c qui contient la carte la plus forte
     */
    public Carte trouverCarteLaPlusForte(){
        Carte c = null;
        int puissanceMax = 0;
        for(int i = 0; i < tableauCarteIle.length; i++){
            if(tableauCarteIle[i].getMontant() > puissanceMax){
                c = tableauCarteIle[i];
                puissanceMax = tableauCarteIle[i].getMontant();
            }
        }
        return c;
    }

    public int getPortail(){
        return JoueurOccupant;
    }

    public void setPortail(int v){
        this.JoueurOccupant = v;
    }

    public Carte[] getTableauCarteIle() {
        return this.tableauCarteIle;
    }
}
