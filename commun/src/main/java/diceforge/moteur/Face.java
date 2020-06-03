package diceforge.moteur;

public enum Face {
    OR1 ("or",1),
    OR3 ("or",3),
    OR4 ("or",4),
    OR6 ("or",6),
    LUNE1 ("lune",1),
    LUNE2 ("lune",2),
    SOLEIL1 ("soleil",1),
    SOLEIL2 ("soleil",2),
    EXPLOIT3 ("exploit",3),
    EXPLOIT4 ("exploit",4),
    x3("x3",0),
    ALL ("all",1);

    private String type;
    private int valeur;
    Face(String type , int valeur){
        this.type = type;
        this.valeur = valeur;
    }
    public int getPoint(){
        return this.valeur;
    }
    public String getType(){
        return this.type;
    }
}