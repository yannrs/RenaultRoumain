import java.util.ArrayList;

import Divers.StatutEntreprise;

/**
 * Class définissant les éléments importants constituants un projet
 * Une équipe + le temps déjà écoulé depuis le début du projet
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class Projet {
	private ArrayList<Salarie> equipe; // TODO diviser l'équipe en 2 parties (Niveau 2 et Niveau 3/4)
	private int tempsEcoule;
	private static final int TempsMaxProjet = 30;
	
	private boolean DEBUG = false;

	/****************************/
	/*  	  CONSTRUCTEUR  	*/
	/****************************/
	
	public Projet(){
		this(new ArrayList<Salarie>(), 0);
	}
	
	public Projet(ArrayList<Salarie> equipe){
		this(equipe, 0);
	}

	public Projet(ArrayList<Salarie> equipe, int tempsEcoule) {
		super();
		this.equipe = equipe;
		this.tempsEcoule = tempsEcoule;
	}


	/****************************/
	/*  	GETTER & SETTER  	*/
	/****************************/

	public ArrayList<Salarie> getEquipe() {
		return equipe;
	}
	public void setEquipe(ArrayList<Salarie> equipe) {
		this.equipe = equipe;
	}

	public int getTempsEcoule() {
		return tempsEcoule;
	}
	private void setTempsEcoule(int tempsEcoule) {
		this.tempsEcoule = tempsEcoule;
	}


	/****************************/
	/*  		METHODES  		*/
	/****************************/

	/*******Gestion Equipe********/
	public void addMember(Salarie sal){
		this.equipe.add(sal);
	}

	public void deleteMember(Salarie sal){
		this.equipe.remove(sal);
	}

	public int numberOfMember(){
		return this.equipe.size();
	}

	public boolean isEmpty(){
		return numberOfMember()==0;
	}

	public Salarie getMember(int i){
		return (i<numberOfMember() && i>=0 ? this.equipe.get(i) : null);
	}
	
	public boolean isEnoughPeople(){
		boolean b = true;		
		b &= nbParStatut(StatutEntreprise.EXPERIMENTE) + nbParStatut(StatutEntreprise.EXPERT) >= 1;
		b &= nbParStatut(StatutEntreprise.INTERMEDIARE) >= 4;
		b &= this.getEquipe().size() >= 5;
		return b;
	}

	/*******Comptage équipe********/
	public int nbParStatut(StatutEntreprise statut){
		int i = 0;
		for(Salarie s : this.equipe){
			if(s.getNiveau().getStatut() == statut){
				i++;
			}
		}		
		return i;
	}

	/*******Gestion projet********/
	public boolean moisSuivant(){
		// Check if the project can continue
		if(isEnoughPeople()){		
			// Increase the time
			setTempsEcoule(getTempsEcoule()+1);

			// Add experience for each person
//			addExperience();

			// Verify if it's the end
			if(isFinished()){
				addExperienceFinProjet();				
			}
			
			return true;
		}
		else{
			if(DEBUG) System.out.println("[Projet] Le projet ne peut pas continuer : manque de personnel");
			return false;
		}
	}

	public boolean isFinished(){
		return getTempsEcoule() == TempsMaxProjet;
	}

	/*******Gestion Expérience de l'équipe********/
	public void addExperienceFinProjet(){
		for(Salarie s : this.equipe){
			s.getNiveau().inccNbProjet();
		}
	}

	// Déjà fait dans equipe
//	public void addExperience(){
//		for(Salarie s : this.equipe){
//			s.getNiveau().inccExperience();
//			s.getPerso().inccAGEMois();
//		}
//	}
	
	/*******Gestion sauvegarede Projet********/
	public String titretoString(){
		String s = ";;";

		s += "TempsEcoulé" + ";";
		s += "Composition" + ";";
		//s += this.equipe.get(0).titretoString();
		
		return s;		
	}
	public String toString(){
		String s = this.getTempsEcoule() + ";{;";
		for(Salarie sal : this.getEquipe()){
			s += sal.toString() + ";";
		}
		s+= ";}";
		
		return s;
	}
	
}
