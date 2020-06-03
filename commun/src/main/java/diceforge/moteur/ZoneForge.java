package diceforge.moteur;

public class ZoneForge {

    private int prixDansZone;
    private Face[] faceZone;

    public ZoneForge(){
        this.prixDansZone = 0;
        this.faceZone = null;
    }

    public ZoneForge(int prixP, Face[] faceP){
        this.prixDansZone = prixP;
        this.faceZone = faceP;
    }

    /**
     * permet d'enever la face d'un dé forgé
     * @param laFaceAEnlever la face du dé qui devra être retiré du dé
     */
    public void enleverFace(Face laFaceAEnlever){
        int dejaTrouver = 0;
        int j = 0;
        Face[] newTabFace = new Face[(this.faceZone.length)-1];
        for(int i = 0; i < faceZone.length; i++){
            if(faceZone[i] == laFaceAEnlever && dejaTrouver == 0){
                dejaTrouver = 1;
            }else if(j != faceZone.length-1) {
                newTabFace[j] = faceZone[i];
                j++;
            }
        }
        if(dejaTrouver == 1) {
            this.faceZone = newTabFace;
        }
    }

    /**
     * Getter des face de la zoneDeForge
     * @return le tableau de face
     */
    public Face[] getFaceZone() {
        return this.faceZone;
    }

    /**
     * Getter du prix pour acheter une face dans la zoneDeForge
     * @return le prix dans la zoneDeForge
     */
    public int getPrixDansZone() {
        return this.prixDansZone;
    }
}
