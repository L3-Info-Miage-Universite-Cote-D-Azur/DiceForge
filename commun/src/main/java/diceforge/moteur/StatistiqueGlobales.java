package diceforge.moteur;

public class StatistiqueGlobales {

    private int nbPartie;
    private int nbJoueurs = 0;
    private int i = 0;
    private StatistiquePartie[] stats;

    public StatistiqueGlobales(int nbPartieP){
        this.nbPartie = nbPartieP;
        stats = new StatistiquePartie[this.nbPartie];
    }

    /**
     * Permet de mettre a jour le nombre de joueur dans les statistiques
     */
    public void trouverLeNombreDeJoueurs(){
        this.nbJoueurs = stats[0].getTableauStat().length;
    }

    /**
     * Retourne le pourcentage de victoire d'un joueur
     * @param numeroJoueur le numero du joueur
     * @return le pourcentage
     */
    public int calculePourcentageVictoire(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][0];
        }
        //return (res/this.nbPartie)*100;
        return res;
    }

    /**
     * Retourne de nombre de points de gloire total obtenue par un joueur
     * @param numeroJoueur le numero du joueur
     * @return le nombre de points de gloire total
     */
    public int calculePointDeGloireTotal(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][7];
        }
        return res;
    }

    /**
     * Retourne le nombre de dés lancer aux total par un joueur
     * @param numeroJoueur le numero du joueur
     * @return le nombre total de dés lancer
     */
    public int calculeDeLancerTotal(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][1];
        }
        return res;
    }

    /**
     * Retourne le nombre total de carte acheter par un joueur
     * @param numeroJoueur le numero du joueur
     * @return le nombre total de carte acheter
     */
    public int calculeCarteAcheterTotal(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][2];
        }
        return res;
    }

    /**
     * Retourne le nombre total de faces forger par un joueur
     * @param numeroJoueur le numero du joueur
     * @return le nombre total de faces forger
     */
    public int calculeFaceForgerTotal(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][3];
        }
        return res;
    }

    /**
     * Retourne le nombre d'or total obtenue par un joueur
     * @param numeroJoueur le numero du joueur
     * @return le nombre d'or total obtenue
     */
    public int calculeOrTotal(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][4];
        }
        return res;
    }

    /**
     * Retourne le nombre de fragments lunaire total obtenue par un joueur
     * @param numeroJoueur le numero du joueur
     * @return le nombre de fargments lunaires total obtenue
     */
    public int calculeFragmentLunaireTotal(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][5];
        }
        return res;
    }

    /**
     * Retourne le nombre de fragments solaire total obtenue par un joueur
     * @param numeroJoueur le numero du joueur
     * @return le nombre de fragments solaire total obtenue
     */
    public int calculeFragmentSolaireTotal(int numeroJoueur){
        int res = 0;
        for(int i = 0; i < this.nbPartie; i++){
            res += this.stats[i].getTableauStat()[numeroJoueur][6];
        }
        return res;
    }

    /**
     * Permet d'afficher les statistiques recolté lors d'une serie de partie
     * @return une chaine de charactere correspondant aux statistiques
     */
    @Override
    public String toString(){
        trouverLeNombreDeJoueurs();
        String res = "Statistiques des "+ this.nbPartie +" Parties : \n";
        for(int i = 0; i < this.nbJoueurs-1; i++){
            res += "Joueur numero: " + i + "\n";
            res += "\tTaux de victoire: " + calculePourcentageVictoire(i) + " sur " + this.nbPartie + "\n";
            res += "\tNombre total de points de gloire obtenue: " + calculePointDeGloireTotal(i) + " (" + calculePointDeGloireTotal(i)/this.nbPartie +"/partie)" + "\n";
            res += "\tNombre total de dés lancé: " + calculeDeLancerTotal(i) + " (" + calculeDeLancerTotal(i)/this.nbPartie +"/partie)" + "\n";
            res += "\tNombre total de cartes acheté: " + calculeCarteAcheterTotal(i) + " (" + calculeCarteAcheterTotal(i)/this.nbPartie +"/partie)" + "\n";
            res += "\tNombre total de faces forgé: " + calculeFaceForgerTotal(i) + " (" + calculeFaceForgerTotal(i)/this.nbPartie +"/partie)" + "\n";
            res += "\tNombre total d'or recolté: " + calculeOrTotal(i) + " (" + calculeOrTotal(i)/this.nbPartie +"/partie)" + "\n";
            res += "\tNombre total de fragments lunaires recolté: " + calculeFragmentLunaireTotal(i) + " (" + calculeFragmentLunaireTotal(i)/this.nbPartie +"/partie)" + "\n";
            res += "\tNombre total de fragments solaires recolté: " + calculeFragmentSolaireTotal(i) + " (" + calculeFragmentSolaireTotal(i)/this.nbPartie +"/partie)" + "\n";
        }
        return res;
    }

    public void setStats(StatistiquePartie stats) {
        this.stats[i] = stats;
        i++;
    }
}
