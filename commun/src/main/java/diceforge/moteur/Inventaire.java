package diceforge.moteur;


import java.util.Random;

public class Inventaire {

	private Random rand;
	private int numero;
	private De de1;
	private De de2;
	private int pointgloire;
	private int or;
	private int fragmentsLunaires;
	private int fragmentsSolaires;
	private int maxOr;
	private int maxFragmentsLunaires;
	private int maxFragmentsSolaires;
	private Carte[] cartesDuJoueur;
	private int nbDeCartes;
	private int x3;

	public Plateau plateau;
	public Forge forge;

	private StatistiquePartie stats;

	public Inventaire(){
		this.pointgloire = 0;
		this.or = 0;
		this.fragmentsLunaires = 0;
		this.maxOr = 12;
		this.maxFragmentsLunaires = 6;
		this.fragmentsSolaires = 0;
		this.maxFragmentsSolaires = 6;
		this.cartesDuJoueur = new Carte[20];
		this.nbDeCartes = 0;
	}

	public Inventaire(int numero, Random rand, Plateau plateau, Forge forge, StatistiquePartie stats) {
		this.numero = numero;
		this.de1 = new De(rand);
		this.de2 = new De(rand);
		this.pointgloire = 0;
		this.or = 0;
		this.fragmentsLunaires = 0;
		this.maxOr = 12;
		this.maxFragmentsLunaires = 6;
		this.fragmentsSolaires = 0;
		this.maxFragmentsSolaires = 6;
		this.cartesDuJoueur = new Carte[20];
		this.nbDeCartes = 0;
		this.rand = rand;
		this.plateau = plateau;
		this.forge = forge;
		this.stats = stats;
		this.x3 = 1;
	}

	/**
	 * Effectue le lancement du De et effectue une demande d'incrementation des ressources (nessessaire)
	 * @param de le dé a lancer
	 */
	public void lancementDe(De de) {
		Face faceRetourner = de.lancer();
		this.stats.ajoutDéLancer(this.numero);
		switch (faceRetourner.getType()) {
			case "or":
				this.incrementOr(faceRetourner.getPoint()*this.x3);
				Log.println("Le joueur "+this.numero+" gagne "+faceRetourner.getPoint()*this.x3+" or.");
				this.x3 = 1;
				break;
			case "lune":
				this.incrementFragmentsLunaires(faceRetourner.getPoint()*this.x3);
				Log.println("Le joueur "+this.numero+" gagne "+faceRetourner.getPoint()*this.x3+" fragments de lune.");
				this.x3 = 1;
				break;
			case "soleil":
				this.incrementFragmentsSolaires(faceRetourner.getPoint()*this.x3);
				Log.println("Le joueur "+this.numero+" gagne "+faceRetourner.getPoint()*this.x3+" fragments de soleil.");
				this.x3 = 1;
				break;
			case "exploit":
				this.incrementPointGloire(faceRetourner.getPoint()*this.x3);
				Log.println("Le joueur "+this.numero+" gagne "+faceRetourner.getPoint()*this.x3+" points de gloire.");
				this.x3 = 1;
				break;
			case "x3":
				this.x3 = 3;
				break;
			case "all":
				this.incrementFragmentsLunaires(faceRetourner.getPoint()*this.x3);
				this.incrementFragmentsSolaires(faceRetourner.getPoint()*this.x3);
				this.incrementOr(faceRetourner.getPoint()*this.x3);
				this.incrementPointGloire(faceRetourner.getPoint()*this.x3);
				Log.println("Le joueur "+this.numero+" gagne "+faceRetourner.getPoint()*this.x3+" de chaque ressource.");
				this.x3 = 1;
				break;
		}
	}

	/**
	 * Effectue un "achat" de carte et l'ajoute dans la liste de carte present dans l'inventaire du joueur
	 * @param carte est la carte a acheter
	 */
	public void acheterUneCarte(Carte carte) {
		for (int i = 0; i < plateau.listeIles.length; i++) {
			if (plateau.listeIles[i].trouverUneCarte(carte) == carte) {
				if (plateau.listeIles[i].trouverUneCarte(carte).getMonnaie().equals("lune")) {
					if (plateau.listeIles[i].trouverUneCarte(carte).getMontant() <= this.getFragmentsLunaires()) {
						if(plateau.listeIles[i].trouverUneCarte(carte).getNombreAchetable() > 0) {
							if (plateau.listeIles[i].getPortail() == -1) {
								plateau.listeIles[i].setPortail(this.getNumero());
							} else if (this.numero != plateau.listeIles[i].getPortail()) {
								Log.println("Le joueur numero : " + this.numero + " prend la place du joueur " + plateau.listeIles[i].getPortail() + " sur l'ile " + (i + 1));

								plateau.listeIles[i].setPortail(this.getNumero());
							}
							this.cartesDuJoueur[this.nbDeCartes] = plateau.listeIles[i].trouverUneCarte(carte);
							if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.COFFRE) {
								this.maxFragmentsLunaires = this.maxFragmentsLunaires + 3;
								this.maxFragmentsSolaires = this.maxFragmentsSolaires + 3;
								this.maxOr = this.maxOr + 4;
							} else if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.SCORPION) {
								this.lancementDe(this.de1);
								this.lancementDe(this.de2);
								this.lancementDe(this.de1);
								this.lancementDe(this.de2);
							} else if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.CASQUE) {
								if (this.de1.faceExist(Face.OR1)) {
									this.de1.remplacerFace(Face.OR1, Face.x3);
								} else if (this.de1.faceExist(Face.OR3)) {
									this.de1.remplacerFace(Face.OR3, Face.x3);
								} else {
									this.de1.remplacerFace(this.de1.faces[this.getRand().nextInt(6)], Face.x3);
								}
							} else if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.HYDRE) {
								this.fragmentsSolaires -= this.getFragmentsSolaires();
							}
							this.incrementPointGloire(plateau.listeIles[i].trouverUneCarte(carte).getPointgloire());
							this.fragmentsLunaires -= plateau.listeIles[i].trouverUneCarte(carte).getMontant();
							carte.setNombreAchetable(carte.getNombreAchetable()-1);
							this.nbDeCartes++;
							Log.println("Le joueur numero : " + this.numero + " a acheter la carte " + carte.getNom());
							Log.println("Nombre de carte achetable : "+carte.getNombreAchetable());
							stats.ajoutCarteAcheter(this.numero);
						}else{
							Log.println("Le joueur numero : "+ this.numero + " n'a pas pu acheter la carte "+ carte.getNom() + " car il n'y a plus de carte de ce type disponible.");
						}
					}
					else {
						Log.println("Le joueur numero : " + this.numero + " n'a pas pu acheter la carte " + carte.getNom() + " car il dispose pas des fonds nécessaires.");
					}
				}
				else{
					if (plateau.listeIles[i].trouverUneCarte(carte).getMontant() <= this.getFragmentsSolaires()) {
						this.cartesDuJoueur[this.nbDeCartes] = plateau.listeIles[i].trouverUneCarte(carte);
						if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.MEDUSE) {
						}else if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.CHOUETTE) {
						}else if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.MINOTAURE) {
						}else if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.SPHINX) {
							if(this.getRand().nextInt(2) == 0){
								this.lancementDe(this.getDe1());
								this.lancementDe(this.getDe1());
								this.lancementDe(this.getDe1());
								this.lancementDe(this.getDe1());
							}
							else{
								this.lancementDe(this.getDe2());
								this.lancementDe(this.getDe2());
								this.lancementDe(this.getDe2());
								this.lancementDe(this.getDe2());
							}
						}else if (plateau.listeIles[i].trouverUneCarte(carte) == Carte.HERBES) {
							this.incrementOr(3);
							this.incrementFragmentsLunaires(3);
						}
						this.incrementPointGloire(plateau.listeIles[i].trouverUneCarte(carte).getPointgloire());
						this.nbDeCartes++;
						this.fragmentsSolaires -= plateau.listeIles[i].trouverUneCarte(carte).getMontant();
						carte.setNombreAchetable(carte.getNombreAchetable()-1);
						Log.println("Le joueur numero : " + this.numero + " a acheter la carte " + carte.getNom());
						Log.println("Nombre de carte achetable : "+carte.getNombreAchetable());
						stats.ajoutCarteAcheter(this.numero);
					}
					else {
						Log.println("Le joueur numero : " + this.numero + " n'a pas pu acheter la carte " + carte.getNom() + " car il dispose pas des fonds nécessaires. ");
					}
				}
			}
		}
	}

	/**
	 * Incremente le montant d'or de "valeur"
	 *
	 * @param valeur
	 */
	public void incrementOr(int valeur) {
		this.stats.ajoutOrTotal(this.numero, valeur);
		if ((this.or + valeur) <= maxOr) {
			this.or += valeur;
		} else {
			this.or = maxOr;
		}
	}

	/**
	 * Incremente le montant de frangments lunaires  de "valeur"
	 *
	 * @param valeur
	 */
	public void incrementFragmentsLunaires(int valeur) {
		this.stats.ajoutFragmentsLunairesTotal(this.numero, valeur);
		if ((this.fragmentsLunaires + valeur) <= maxFragmentsLunaires) {
			this.fragmentsLunaires += valeur;
		} else {
			this.fragmentsLunaires = maxFragmentsLunaires;
		}
	}
	/**
	 * Incremente le montant de frangments solaire  de "valeur"
	 *
	 * @param valeur
	 */
	public void incrementFragmentsSolaires(int valeur) {
		this.stats.ajoutFragmentsSolairesTotal(this.numero, valeur);
		if ((this.fragmentsSolaires + valeur) <= maxFragmentsSolaires) {
			this.fragmentsSolaires += valeur;
		} else {
			this.fragmentsSolaires = maxFragmentsSolaires;
		}
	}

	/**
	 * Incremente le montant de points de gloire  de "valeur"
	 *
	 * @param valeur
	 */
	public void incrementPointGloire(int valeur) {
		this.stats.ajoutPointsDeGloireTotal(this.numero, valeur);
		this.pointgloire += valeur;
	}

	/**
	 * permet de changer la face d'un dé
	 * @param deP le dé dont une face va etre remplacer
	 * @param faceARemplacer la face du dé qui va etre remplacer
	 * @param faceRemplacente la face qui va remplacer la faceARemplacer
	 */
	public void changerFace(De deP, Face faceARemplacer, Face faceRemplacente){
		boolean found = false;
		for(int i = 0; i < this.forge.zoneF.length; i++){
			for(int j = 0; j < this.forge.zoneF[i].getFaceZone().length; j++){
				if(this.forge.zoneF[i].getFaceZone()[j] == faceRemplacente){
					if(this.or >= this.forge.zoneF[i].getPrixDansZone() && !found){
						found = true;
						this.forge.zoneF[i].enleverFace(faceRemplacente);
						deP.remplacerFace(faceARemplacer, faceRemplacente);
						incrementOr(-(this.forge.zoneF[i].getPrixDansZone()));
						stats.ajoutFaceForger(this.numero);
						Log.println("Le joueur "+this.numero+" change la face "+faceARemplacer.getType() +" "+faceARemplacer.getPoint()+" par la face "+faceRemplacente.getType() +" "+faceRemplacente.getPoint()+".");
					}
				}
			}
		}

	}

	/**
	 * Getter du premier dé
	 * @return le dé numero 1
	 */
	public De getDe1() {
		return de1;
	}

	/**
	 * Getter du second dé
	 * @return le dé numero 2
	 */
	public De getDe2() {
		return de2;
	}

	public void setDe1(De de) {
		this.de1 = de;
	}

	public void setDe2(De de) {
		this.de2 = de;
	}

	/**
	 * Retourne le numero du joueur auquel appartient l'inventaire
	 *
	 * @return le numero du joueur
	 */
	public int getNumero() {
		return numero;
	}

	public void setNumero() {
		this.numero = numero;
	}

	/**
	 * Retourne la quantité d'or présent dans l'inventaire du joueur
	 *
	 * @return la quantité d'or du joueur
	 */
	public int getOr() {
		return or;
	}

	public void setOr(int or) {
		this.or = or;
	}

	/**
	 * Retourne la quantité de frangments lunaires présents dans l'inventaire du joueur
	 *
	 * @return la quantité de fragment lunaire du joueur
	 */
	public int getFragmentsLunaires() {
		return fragmentsLunaires;
	}

	public void setFragmentsLunaires(int fragmentsLunaires) {
		this.fragmentsLunaires = fragmentsLunaires;
	}

	/**
	 * Retourne la quantité de fragments solaires présents dans l'inventaire du joueur
	 *
	 * @return la quantité de fragment solaire du joueur
	 */
	public int getFragmentsSolaires() {
		return fragmentsSolaires;
	}

	public void setFragmentsSolaires(int fragmentsSolaires) {
		this.fragmentsSolaires = fragmentsSolaires;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Retourne le score du joueur auquel appartient l'inventaire
	 *
	 * @return les point du joueur
	 */
	public int getPointgloire() {
		return pointgloire;
	}

	/**
	 * Setter point de gloire du joueur
	 * @param pointgloire point de gloire a metre au joueur (pas a ajouté)
	 */
	public void setPointgloire(int pointgloire) {
		this.pointgloire = pointgloire;
	}

	/**
	 * Retourne la liste de cartes que le joueur a acheté
	 *
	 * @return la liste de carte que possede le joueur
	 */
	public Carte[] getCartesDuJoueur() {
		return cartesDuJoueur;
	}

	public void setCartesDuJoueur(Carte[] cartes) {
		this.cartesDuJoueur = cartes;
	}

	public int getNbDeCartes() {
		return nbDeCartes;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}
}

