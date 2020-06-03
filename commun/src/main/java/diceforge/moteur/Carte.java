package diceforge.moteur;


public enum Carte {

    MARTEAU("Le Marteau",0,1,"lune"),
    COFFRE("Le Coffre du Forgeron",2, 1,"lune"),
    SABOT("Les Sabots d'argent" ,2,2,"lune"),
    SATYRES("Les Satyres",6,3,"lune"),
    MONSTREGLUANT("Le Monstre Gluant",12,4,"lune"),
    CASQUE("Le Casque",4,5,"lune"),
    SCORPION("Le Scorpion",8,6,"lune"),
    SAGE("Le Sage",0,1,"soleil"),
    HERBES("Les Herbes",2,1,"soleil"),
    CHOUETTE("La Chouette",4,2,"soleil"),
    MINOTAURE("Le Minotaure",8,3,"soleil"),
    MEDUSE("Méduse",14,4,"soleil"),
    MIROIR("Le Miroir",10,5,"soleil"),
    SPHINX("L'énigme du Sphinx",10,6,"soleil"),
    HYDRE("L'Hydre",26,5,"lune");

    private String nom;
    private int pointgloire;
    private int montant;
    private String monnaie;
    private int nombreAchetable;

    static public final int nombreCartes = 15;

    Carte(String nom,int pointgloire, int montant,String monnaie){
        this.nom = nom;
        this.pointgloire = pointgloire;
        this.montant = montant;
        this.monnaie= monnaie;
        this.nombreAchetable = 4;
    }

    public int getPointgloire() {
        return pointgloire;
    }
    public String getNom() {return nom;}
    public int getMontant(){return montant;}
    public String getMonnaie(){return monnaie;}

    public int getNombreAchetable() {
        return nombreAchetable;
    }

    public void setNombreAchetable(int nombreAchetable) {
        this.nombreAchetable = nombreAchetable;
    }

    public static void resetCartes() {
        Carte.MARTEAU.setNombreAchetable(4);
        Carte.COFFRE.setNombreAchetable(4);
        Carte.SABOT.setNombreAchetable(4);
        Carte.SATYRES.setNombreAchetable(4);
        Carte.MONSTREGLUANT.setNombreAchetable(4);
        Carte.CASQUE.setNombreAchetable(4);
        Carte.SCORPION.setNombreAchetable(4);
        Carte.SAGE.setNombreAchetable(4);
        Carte.HERBES.setNombreAchetable(4);
        Carte.MINOTAURE.setNombreAchetable(4);
        Carte.MEDUSE.setNombreAchetable(4);
        Carte.MIROIR.setNombreAchetable(4);
        Carte.SPHINX.setNombreAchetable(4);
        Carte.HYDRE.setNombreAchetable(4);
    }
}