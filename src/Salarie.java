import java.util.ArrayList;
import java.util.Date;

import Divers.Pays;
import Divers.Salaire;
import Divers.StatutEntreprise;

/**
 * Regroupement de l'ensemble des caract�ristiques utiles d'un Salari� dans une entreprise
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class Salarie {
	
	/**Caracteristiques personnelle**/
	private int id = Integer.parseInt(("" + (new Date()).getTime()).substring(0, 7));
	private Personne perso;	// Object
	private Niveau niveau; // Object
	
	/**Param�tres simulation**/
	// Tuteur
	private Salarie tuteur = null; // Object
	private ArrayList<Salarie> eleve = new ArrayList<Salarie>();
	
	// Projet
	private boolean isOnOneProject = false;
	
	

	/****************************/
	/*  	  CONSTRUCTEUR  	*/
	/****************************/

	public Salarie() {
		this(new Niveau());
	}

	public Salarie(StatutEntreprise statut) {
		this(new Niveau(statut));
	}
	
	public Salarie(Pays origine) {
		this(new Personne(origine), new Niveau());
	}
	
	public Salarie(Pays origine, StatutEntreprise statut) {
		this(new Personne(origine), new Niveau(statut));
	}
	
	public Salarie(Niveau niveau) {
		this(new Personne(), niveau);
	}

	public Salarie(int age, Niveau niveau) {
		this(new Personne(age), niveau);
	}

	public Salarie(Personne perso, Niveau niveau) {
		this(perso, niveau, null);
	}

	public Salarie(Personne perso, Niveau niveau, Salarie tuteur) {
		super();
		this.perso = perso;
		this.niveau = niveau;
		this.tuteur = tuteur;
	}


	/****************************/
	/*  	GETTER & SETTER  	*/
	/****************************/

	public Personne getPerso() {
		return perso;
	}
	private void setPerso(Personne perso) {
		this.perso = perso;
	}

	public Salaire getSalaire(Pays localisation) {		
		switch(localisation){
		case FRANCE:
			System.out.println("[Warning] Salaire : Roumain en France");
			return (getPerso().getOrigine() == Pays.FRANCE ? Salaire.FRANCAIS : Salaire.ROUMAIN);  
		case ROUMANIE:
			return (getPerso().getOrigine() == Pays.FRANCE ? Salaire.EXPATRIE : Salaire.ROUMAIN);
		default:
			return Salaire.ROUMAIN;
		}
	}

	public Niveau getNiveau() {
		return niveau;
	}
	private void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Salarie getTuteur() {
		return tuteur;
	}
	public void setTuteur(Salarie tuteur) {
		this.tuteur = tuteur;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Salarie> getEleve() {
		return eleve;
	}
	public void setEleve(ArrayList<Salarie> eleve) {
		this.eleve = eleve;
	}

	public boolean isOnOneProject() {
		return isOnOneProject;
	}
	public void setOnOneProject() {
		this.isOnOneProject = true;
	}	
	public void setOnNoOneProject() {
		this.isOnOneProject = false;
	}

	public boolean isOneTutorPresent(){
		return this.getTuteur() != null;
	}
	
	
	/****************************/
	/*  		METHODES  		*/
	/****************************/
	
	/*******Gestion tutorat********/
	public void deleteEleve(Salarie sal){
		this.getEleve().remove(sal);
	}
	
	public void addEleve(Salarie sal){
		this.getEleve().add(sal);
	}
	
	public int nbEleve(){
		return this.getEleve().size();
	}
	
	public boolean isOneEleveCanAdd(){
		return nbEleve() < Simulation.NBMAXELEVE;
	}
	
	
	/*******Evolution********/
	/**
	 * Augmentation du grade de la personne si possible
	 * @param Environnement
	 * @param tuteur
	 */
	public void inccStatut(boolean Environnement, Salarie tuteur){
		if(this.getNiveau().inccStatut(Environnement, tuteur) && this.isOneTutorPresent()){
			this.tuteur.deleteEleve(this);
			this.tuteur = null;
		}
	}
	
	
	/*******Simulation********/
	/**
	 * Si l'environnement est bon, on augmente l'exp�rience et l'age, sinon que l'age
	 * @param profitable
	 */
	public void moisSuivant(boolean profitable){
		// Ajout de l'exp�rience acquise par une personne
		if(profitable) this.getNiveau().inccExperience();
		else this.getPerso().inccTempsInactif();
		
		// Devient plus vieux ...
		this.getPerso().inccAGEMois();
	}
	
	
	/*******Gestion TurnOver********/
	/**
	 * Calcul le turnOver associ� � une personne provoqu� par l'environnement
	 * @param envi
	 * @return
	 */
	public double turnOver(double envi){
		switch(this.getNiveau().getStatut()){
		case DEBUTANT:
			return this.getPerso().etatPsycho(envi*1.5);
		case INTERMEDIARE:
			return this.getPerso().etatPsycho(envi*2);	// Plus d'influence pour les niveaux Interm�diaires
		case EXPERIMENTE:
			return this.getPerso().etatPsycho(envi);
		case EXPERT:
			return this.getPerso().etatPsycho(0.01); // Tr�s faible	pour les experts
		default:
			return 0.0;
		}		
	}
	
	/*******Sauvegarde Salari�********/
	public String titretoString(){		
		String s = "";

		s += "Niveau" + ",";
		s += "Salaire" + ",";

		return s;	
	}
	
	public String toString(){
		return "{" + this.getNiveau() + "," + this.getSalaire(Pays.ROUMANIE) + "}";
	}
	
	public String toString(Pays localisation){
		return "{" + this.getNiveau() + "," + this.getSalaire(localisation) + "}";
	}
}
