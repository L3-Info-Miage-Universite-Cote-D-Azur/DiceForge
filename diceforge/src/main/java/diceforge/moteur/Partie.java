package diceforge.moteur;

import diceforge.joueur.Identite;
import diceforge.serveur.Serveur;
import java.util.Random;

public class Partie {

	private int numeroPartie;
	private StatistiquePartie statistique;
	private Serveur serveur;
	private Inventaire[] inventaires;
	private int nbInventaires;
	private int numeroMancheCourante;
	private int nombreDeMancheMax;
	private Plateau plateau;
	private Forge forge;
	private Manche manche;
	private Random rand;



	public Partie(int numero) {
		this.numeroPartie = numero;
		Log.println("La partie "+numeroPartie+" a ete creer.");
		this.rand = new Random();
		this.inventaires = new Inventaire[4];
		this.nbInventaires = 0;
		this.numeroMancheCourante = 0;
		Log.println("L'objet Random a ete creer.");

		this.plateau = new Plateau();
		this.forge = new Forge();
		this.manche = null;



		if(this.inventaires.length == 3){
			this.nombreDeMancheMax = 10;
			Log.println("Il y'a "+this.nombreDeMancheMax+" manches dans la partie");
		}else if(this.inventaires.length == 2 || this.inventaires.length == 4){
			this.nombreDeMancheMax = 9;
			Log.println("Il y'a "+this.nombreDeMancheMax+" manches dans la partie");
		}else{
			Log.println("Il n'y a pas un nombre correcte de joueurs pour lancer la partie");
		}
		statistique = new StatistiquePartie(this.inventaires.length);
		Carte.resetCartes();

		Serveur.statsGlobal.setStats(statistique);

	}

	/**
	 * permet de commencer la partie
	 */
	public void commencer() {
		Log.println("La partie commence.");
		Manche manche = new Manche(rand, serveur, inventaires);
		this.manche = manche;
		this.numeroMancheCourante = 1;
		Log.println("La manche "+ this.numeroMancheCourante +" commence.");
		this.manche.jouer();
	}

	/**
	 * permet de continuer la partie
	 */
	public void continuer() {
		this.manche.currentJoueur+=1;
		if(this.manche.getCurrentJoueur() < this.inventaires.length) {
			Log.println("La manche "+ this.numeroMancheCourante +" continue.");
			this.manche.jouer();
		}else if((this.manche.getCurrentJoueur() == this.inventaires.length) && (this.numeroMancheCourante < this.nombreDeMancheMax)){
			this.numeroMancheCourante+=1;
			Log.println("La manche "+ this.numeroMancheCourante +" commence.");
			this.manche = new Manche(rand, serveur, inventaires);
			this.manche.jouer();
		}else{
			this.statistique.ajoutStatVictoire(gagnantPartie());
			Log.println("La partie se termine.");
			Log.println(this.statistique.toString());
			this.terminer();
		}
	}

	private void terminer() {
		serveur.terminerPartie();
	}


	public void setServeur(Serveur serveur) {
		this.serveur = serveur;
	}

	public Manche getManche() {
		return this.manche;
	}

	public Inventaire[] getInventaires() {
		return inventaires;
	}

	public Inventaire getInventaire(int i){
		return inventaires[i];
	}

	public Plateau getPlateau(){
		return plateau;
	}

	public Forge getForge() {
		return forge;
	}

	/**
	 * affiche le joueur gagnant
	 */
	public int gagnantPartie() {
		int maxPoint = 0;
		int idGagnant = 0;
		for(int i = 0; i < this.inventaires.length; i++){
			if(this.inventaires[i].getPointgloire() > maxPoint){
				idGagnant = i;
				maxPoint = this.inventaires[i].getPointgloire();
			}
		}
		return idGagnant;
	}


	/**
	 * créee un inventaire pour les joueurs
	 */
	public void creationInventaire() {
		this.inventaires[nbInventaires] = new Inventaire(nbInventaires, rand,  plateau, forge, statistique);
		nbInventaires++;
	}



}