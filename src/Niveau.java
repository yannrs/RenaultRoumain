import javax.swing.DebugGraphics;

import Divers.StatutEntreprise;


public class Niveau {
	private int experience;
	private StatutEntreprise statut;
	private int nbProjet = 0;


	/****************************/
	/*  	  CONSTRUCTEUR  	*/
	/****************************/
	public Niveau() {
		super();
		this.experience = 0;
		this.statut = StatutEntreprise.DEBUTANT;
	}
	
	public Niveau(StatutEntreprise statut) {
		super();
		this.statut = statut;
		initialize(statut);
	}
	
	public Niveau(int experience, StatutEntreprise statut) {
		super();
		this.statut = statut;
		initialize(statut);
		this.experience = (experience > this.experience ? experience : this.experience);
	}

	public Niveau(int experience, StatutEntreprise statut, int nbProjet) {
		super();
		this.statut = statut;
		initialize(statut);
		this.nbProjet = (nbProjet > this.nbProjet ? nbProjet : this.nbProjet);
	}

	/****************************/
	/*  	GETTER & SETTER  	*/
	/****************************/
	public int getExperience() {
		return experience;
	}
	private void setExperience(int experience) {
		this.experience = experience;
	}

	public StatutEntreprise getStatut() {
		return statut;
	}
	private void setStatut(StatutEntreprise statut) {
		this.statut = statut;
	}

	public int getNbProjet() {
		return nbProjet;
	}
	private void setNbProjet(int nbProjet) {
		this.nbProjet = nbProjet;
	}


	/****************************/
	/*  		METHODES  		*/
	/****************************/
	
	public void inccExperience(){
		setExperience(getExperience()+1);
	}
	
	
	
	private void initialize(StatutEntreprise statut){

		switch(this.getStatut()){
		case DEBUTANT:
			this.setExperience(0);
			this.setNbProjet(0);
			break;
		case INTERMEDIARE: 	
			this.setExperience(6);
			this.setNbProjet(0);
			break;
		case EXPERIMENTE: 
			this.setExperience((int)(6 + 6.5*12 + 2.5*12));
			this.setNbProjet(1);			
			break;
		case EXPERT:
			this.setExperience((int)(6 + 6.5*12 + 2.5*12 + 2*2.5*12));
			this.setNbProjet(3);
			break;
		default:
			System.out.println("[NIVEAU][ERROR] Init : Statut UNKNOWN");
			break;
		}			
	}
	
	/**
	 * Another project done
	 */
	public void inccNbProjet(){
		this.setNbProjet(getNbProjet()+1);
	}

	/**
	 * Try to climb the ladder ;)
	 * @param Environnement
	 * @param tuteur
	 * @return
	 */
	public boolean inccStatut(boolean Environnement, Salarie tuteur){

		switch(this.getStatut()){
		case DEBUTANT:
			// ONLY need 6 months of practice
			if(this.getExperience()  >= 6){
				setStatut(StatutEntreprise.INTERMEDIARE);
				return true;
			}
			break;
		case INTERMEDIARE: 	
			// Ici l'environnement doit être composé d'au moins 3 personnes de niveau 2 : Intermédiare
			// Doit avoir fait 4 prestations d'une durée de 2 ans pour la 1ère puis 1.5 pour les suivantes
			// Puis doit avoir fait un projet  de 2.5 ans
			// 1 Tuteur de niveau 3 ou plus : Experimenté ou Expert
			// Ressource d'autre métier	TODO
			if(tuteur != null && 
			(tuteur.getNiveau().getStatut() == StatutEntreprise.EXPERIMENTE || tuteur.getNiveau().getStatut() == StatutEntreprise.EXPERT) &&
					Environnement &&
					this.getExperience() >= (6 + 6.5*12 + 2.5*12) &&
					this.getNbProjet()>=1){
				setStatut(StatutEntreprise.EXPERIMENTE);
				return true;			
			}
			break;
		case EXPERIMENTE: 
			// Ici Soutient d'un salarié de niveau 4 : Expert
			// Environnement comprenant au moins 3 salariés de niveau 3 : Expérimenté
			// Liens avec d'autre metier	TODO
			// 3 ou 4 projets réalisés
			if(tuteur != null && 
					tuteur.getNiveau().getStatut() == StatutEntreprise.EXPERT &&
					Environnement &&
					this.getNbProjet()>=3){
				setStatut(StatutEntreprise.EXPERT);
				return true;			
			}
			break;
		case EXPERT: 	
			System.out.println("[NIVEAU] Personne déjà Experte");
		default:
			System.out.println("[NIVEAU][ERROR] Statut UNKNOWN");
			break;
		}	

		return false;
	}

	
	public String toString(){
		return this.getStatut() + "," + this.getExperience() + "," + this.getNbProjet();
	}
	
}
