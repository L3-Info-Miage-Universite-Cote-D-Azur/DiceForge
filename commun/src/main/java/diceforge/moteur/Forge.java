package diceforge.moteur;

public class Forge {

    public ZoneForge[] zoneF;
    public static final int nombreFaceForge = 32;

    Forge(){
        zoneF = resetForge();
    }

    public ZoneForge[] resetForge() {
        ZoneForge[] z = new ZoneForge[7];

        z[0] = new ZoneForge(2,creationTableauFaceForge(Face.OR3, Face.OR3, Face.OR3, Face.OR3, Face.LUNE1, Face.LUNE1, Face.LUNE1, Face.LUNE1));
        z[1] = new ZoneForge(3,creationTableauFaceForge(Face.OR4, Face.OR4, Face.OR4, Face.OR4, Face.SOLEIL1, Face.SOLEIL1, Face.SOLEIL1, Face.SOLEIL1));
        z[2] = new ZoneForge(4,creationTableauFaceForge(Face.OR6));
        z[3] = new ZoneForge(5,creationTableauFaceForge(Face.ALL)); //Mauvaise ressource, car pas encore creer !!!
        z[4] = new ZoneForge(6,creationTableauFaceForge(Face.LUNE2, Face.LUNE2, Face.LUNE2, Face.LUNE2));
        z[5] = new ZoneForge(8,creationTableauFaceForge(Face.SOLEIL2, Face.SOLEIL2, Face.SOLEIL2, Face.SOLEIL2, Face.EXPLOIT3, Face.EXPLOIT3, Face.EXPLOIT3, Face.EXPLOIT3));
        z[6] = new ZoneForge(12,creationTableauFaceForge(Face.ALL, Face.EXPLOIT4));

        return z;
    }

    /**
     * Permet de générer le tableau de face des differentes zoneDeForge
     * @param args les face a ajouter a la zoneDeForge
     * @return le tableau generer
     */
    public Face[] creationTableauFaceForge(Face ... args){
        Face[] tabF = new Face[args.length];

        for(int i = 0; i < args.length; i++){
            tabF[i] = args[i];
        }

        return tabF;
    }

}
