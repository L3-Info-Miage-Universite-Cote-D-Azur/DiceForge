package diceforge.moteur;

import java.util.Random;


public class De {
	private final Random rand;

	Face[] faces = {Face.OR1, Face.OR1, Face.OR1, Face.OR1, Face.OR1, Face.LUNE1};

	public De() {
		this.rand = null;
	}
	public De(Random rand) {
		this.rand = rand;
	}

	/**
	 * Lancer le dé
	 * @return La face sur laquelle est tombé le dé
	 */
	public Face lancer() {
		int result = rand.nextInt(6);
		return faces[result];
	}

	/**
	 * Remplacer une face par une autre
	 * @param faceARemplacer La face a remplacer
	 * @param faceRemplacente La face qui remplace
	 */
	public void remplacerFace(Face faceARemplacer, Face faceRemplacente){

		for(int i = 0; i < faces.length; i++){
			if(faces[i] == faceARemplacer){
				faces[i] = faceRemplacente;
			}
		}

	}

	/**
	 * renvoie si une face existe ou non sur le dé
	 * @param face la face a trouver sur le dé
	 * @return true ou false
	 */
	public boolean faceExist(Face face){
		for(int i=0; i<this.faces.length;i++){
			if( face == this.faces[i]){
				return true;
			}
		}
		return false;
	}

	public int getFace(Face face){
		for(int i=0; i<this.faces.length;i++){
			if( face == this.faces[i]){
				return 1;
			}
		}
		return -1;
	}

	public Face[] getFaces() {
		return faces;
	}
}
