package diceforge.moteur;

public class StatistiquePartie {

    private int laPartie;
    private int tableauStat[][];

    public StatistiquePartie(int partieP){
        this.laPartie = partieP;
        this.tableauStat = new int[this.laPartie + 1][8];
    }

    /**
     * Permet d'ajouer une statistique de victoire a un joueur
     * @param numeroJoueur le numero du joueur
     */
    public final void ajoutStatVictoire(int numeroJoueur){
        this.tableauStat[numeroJoueur][0] = this.tableauStat[numeroJoueur][0]+1;
    }

    /**
     * Permet d'ajouer une statistique de lancement de dé a un joueur
     * @param numeroJoueur le numero du joueur
     */
    public final void ajoutDéLancer(int numeroJoueur){
        this.tableauStat[numeroJoueur][1] = this.tableauStat[numeroJoueur][1]+1;
    }

    /**
     * Incremente la statistique concernant le nombre de carte acheter par un joueur
     * @param numeroJoueur le numero du joueur
     */
    public final void ajoutCarteAcheter(int numeroJoueur){
        this.tableauStat[numeroJoueur][2] = this.tableauStat[numeroJoueur][2]+1;
    }

    /**
     * Incremente la statistique concernant le nombre de face forger par un joueur
     * @param numeroJoueur le numero du joueur
     */
    public final void ajoutFaceForger(int numeroJoueur){
        this.tableauStat[numeroJoueur][3] = this.tableauStat[numeroJoueur][3]+1;
    }

    /**
     * Incremente la statistique concernant le nombre total d'or obtenu (lors des lancement de dé) par un joueur
     * @param numeroJoueur le numero du joueur
     * @param montantToAdd le montant a incrementer
     */
    public final void ajoutOrTotal(int numeroJoueur, int montantToAdd){
        this.tableauStat[numeroJoueur][4] = this.tableauStat[numeroJoueur][4]+montantToAdd;
    }

    /**
     * Incremente la statistique concernant le nombre total de fragments lunaires obtenu (lors des lancement de dé) par un joueur
     * @param numeroJoueur le numero du joueur
     * @param montantToAdd le montant a incrementer
     */
    public final void ajoutFragmentsLunairesTotal(int numeroJoueur, int montantToAdd){
        this.tableauStat[numeroJoueur][5] = this.tableauStat[numeroJoueur][5]+montantToAdd;
    }

    /**
     * Incremente la statistique concernant le nombre total de fragments solaire obtenu (lors des lancement de dé) par un joueur
     * @param numeroJoueur le numero du joueur
     * @param montantToAdd le montant a incrementer
     */
    public final void ajoutFragmentsSolairesTotal(int numeroJoueur, int montantToAdd){
        this.tableauStat[numeroJoueur][6] = this.tableauStat[numeroJoueur][6]+montantToAdd;
    }

    /**
     * Incremente la statistique concernant le nombre total de points de gloire obtenu par un joueur
     * @param numeroJoueur le numero du joueur
     * @param montantToAdd le montant a incrementer
     */
    public final void ajoutPointsDeGloireTotal(int numeroJoueur, int montantToAdd){
        this.tableauStat[numeroJoueur][7] = this.tableauStat[numeroJoueur][7]+montantToAdd;
    }

    /**
     * Permet d'afficher les statistiques recolté lors d'une partie
     * @return une chaine de charactere correspondant aux statistiques
     */
    @Override
    public String toString(){
        String res = "Statistiques de la Partie: \n";
        for(int i = 0; i < this.tableauStat.length-1; i++){
            res += "Joueur numero: " + i + "\n";
            res += "\tNombre de victoire: " + this.tableauStat[i][0] + "\n";
            res += "\tNombre de points de gloire: " + this.tableauStat[i][7] + "\n";
            res += "\tNombre de dés lancé: " + this.tableauStat[i][1] + "\n";
            res += "\tNombre de cartes acheté: " + this.tableauStat[i][2] + "\n";
            res += "\tNombre de faces forgé: " + this.tableauStat[i][3] + "\n";
            res += "\tNombre total d'or recolté: " + this.tableauStat[i][4] + "\n";
            res += "\tNombre total de fragments lunaires recolté: " + this.tableauStat[i][5] + "\n";
            res += "\tNombre total de fragments solaires recolté: " + this.tableauStat[i][6] + "\n";
        }
        return res;
    }

    public int[][] getTableauStat() {
        return tableauStat;
    }
}
