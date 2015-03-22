import java.util.ArrayList;

import Divers.Pays;
import Divers.StatutEntreprise;

/**
 * Rassemblement de l'ensemble des Salariés travaillant dans une unité de Recherche et Developpement
 * @author yann
 *
 */
public class Equipe {
	private ArrayList<Salarie> equipe;
	private Pays localisation;


	/****************************/
	/*  	  CONSTRUCTEUR  	*/
	/****************************/
	public Equipe(Pays localisation) {
		this(new ArrayList<Salarie>(), localisation);
	}

	public Equipe(ArrayList<Salarie> equipe, Pays localisation) {
		super();
		this.equipe = equipe;
		this.localisation = localisation;
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

	public Pays getLocalisation() {
		return localisation;
	}
	public void setLocalisation(Pays localisation) {
		this.localisation = localisation;
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

	/*******Gestion augmentation équipe********/
	public void embauche(){
		addMember(new Salarie(getLocalisation()));
	}

	public void embauche(int nb){
		for(int i = 0; i<nb; i++){
			addMember(new Salarie(getLocalisation()));
		}
	}

	public void embauche(StatutEntreprise status){
		addMember(new Salarie(getLocalisation(), status));
	}


	/*******Gestion diminution équipe********/
	public void retraite(int ageMax, int expMax){
		// FIXME : I don't know if it works
		for(Salarie s : this.equipe){
			if(s.getPerso().getAge()>=ageMax || s.getNiveau().getExperience() >= expMax){
				deleteMember(s);
			}
		}
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

	public int salaireMois(){
		int s = 0;
		for(Salarie sal : this.equipe){
			s += sal.getSalaire(getLocalisation()).getMontant();
		}		
		return s;
	}

	
	/*******Etat équipe********/
	public int isSomebodyNoWorks(){
		int i = 0;
		for(Salarie sal : this.getEquipe()){
			if(!sal.isOnOneProject() && sal.getNiveau().getExperience()>=(6+6.5*12)) i++;
		}
		
		return i;
	}
	
	/*******Simulation********/
	/**
	 * Ajoute l'expérience et promeut si possible
	 */
	public void moisSuivant(){
		// Vérification de l'environnement
		boolean enviDEBUTANT = false;
		boolean enviINTERMEDIAIRE = false;
		boolean enviEXPERIMENTE = false;
		boolean enviEXPERT = false;

		// --- Niveau Debutant : 
		// pas de spécification particulière : ok 
		enviDEBUTANT = true;

		// --- Niveau Intermediaire :
		// 3 personnes du même niveau
		if(nbParStatut(StatutEntreprise.INTERMEDIARE) > 4) enviINTERMEDIAIRE = true;

		
		// --- Niveau Experimenté :
		// au moins 1 personnes de niveau 3 (Expérimenté) + 3 personnes de niveau 2 (Intermediaire)
		if(nbParStatut(StatutEntreprise.EXPERIMENTE) >= 1 &&
				nbParStatut(StatutEntreprise.INTERMEDIARE) >= 3 &&
				nbParStatut(StatutEntreprise.EXPERT) >= 1){
			enviEXPERIMENTE = true;
		}

		
		// -- Mise en pratique
		// Passage au mois suivant pour toutes les membres de l'équipe 
		for(Salarie sal : this.equipe){
			switch(sal.getNiveau().getStatut()){
			case DEBUTANT:
				sal.moisSuivant(enviDEBUTANT);
				sal.inccStatut(enviDEBUTANT, sal.getTuteur());
				break;
			case INTERMEDIARE:
				// Avec un tuteur au moins Experimenté (niveau 3)
				if(sal.isOneTutorPresent() &&
						(sal.getTuteur().getNiveau().getStatut() == StatutEntreprise.EXPERIMENTE || 
						sal.getTuteur().getNiveau().getStatut() == StatutEntreprise.EXPERT)){
					sal.moisSuivant(true);
					sal.inccStatut(true, sal.getTuteur());
				}
				else sal.moisSuivant(false);
				break;
			case EXPERIMENTE:
				sal.moisSuivant(enviEXPERIMENTE);
				sal.inccStatut(enviEXPERIMENTE, sal.getTuteur());
				break;
			case EXPERT:
				sal.moisSuivant(enviEXPERT);
				break;
			default:
				System.out.println("[WARNING] mois suivant switch");
				break;
			}
		}


		// -- Embauche ?
		if(!enviINTERMEDIAIRE){
			// Embauche si pas assez de monde (niveau debutant)
			if(nbParStatut(StatutEntreprise.DEBUTANT)<4) {
				embauche(4-nbParStatut(StatutEntreprise.DEBUTANT));
			}
			// else les recrues vont bientôt arriver!
		}
		if(!enviEXPERIMENTE){
			// Embauche si pas assez de monde (niveau debutant)
			if(nbParStatut(StatutEntreprise.INTERMEDIARE)<3) {
				embauche(3-nbParStatut(StatutEntreprise.INTERMEDIARE));
			}
			if(nbParStatut(StatutEntreprise.DEBUTANT)<3) {
				embauche(3-nbParStatut(StatutEntreprise.DEBUTANT));
			}
			// else les recrues vont bientôt arriver!
		}

//		int salNoWorks = isSomebodyNoWorks();
//		if(salNoWorks > 0 && nbParStatut(StatutEntreprise.DEBUTANT) < 15) embauche(salNoWorks);
		
	}

	

	/*******Gestion des tuteurs********/
	public void repartirTuteur(){
		// Find every free Expert and Experiment employee
		ArrayList<Salarie> tuteurEXPERT = new ArrayList<Salarie>();
		ArrayList<Salarie> tuteurEXPERIMENTE = new ArrayList<Salarie>();
		for(Salarie sal : this.equipe){
			if(sal.getNiveau().getStatut() == StatutEntreprise.EXPERT && sal.isOneEleveCanAdd()){
				tuteurEXPERT.add(sal);
			}
			if(sal.getNiveau().getStatut() == StatutEntreprise.EXPERIMENTE && sal.isOneEleveCanAdd()){
				tuteurEXPERIMENTE.add(sal);
			}
		}
		
		// Distribution des tuteurs libres
		// D'abord pour les employés Intermediaire
		for(Salarie sal : this.equipe){
			if(sal.getNiveau().getStatut() == StatutEntreprise.INTERMEDIARE && !sal.isOneTutorPresent()){
				if(!tuteurEXPERIMENTE.isEmpty()){
					sal.setTuteur(tuteurEXPERIMENTE.get(0));
					sal.getTuteur().addEleve(sal);
					tuteurEXPERIMENTE.remove(0);
				}
				else if(!tuteurEXPERT.isEmpty()){
					sal.setTuteur(tuteurEXPERT.get(0));
					sal.getTuteur().addEleve(sal);
					tuteurEXPERT.remove(0);
				}
				else return;
			}
		}
		
		// Puis pour les salariés Expérimenté
		for(Salarie sal : this.equipe){
			if(sal.getNiveau().getStatut() == StatutEntreprise.EXPERIMENTE && !sal.isOneTutorPresent()){
				if(!tuteurEXPERT.isEmpty()){
					sal.setTuteur(tuteurEXPERT.get(0));
					sal.getTuteur().addEleve(sal);
					tuteurEXPERT.remove(0);
				}
				else return;
			}
		}
		
	}
	
	

	/*******Sauvegarde équipe********/
	public String titretoString(){
		String s = "";

		s += "localisation" + ";";
		s += "numberOfMember()" + ";";
		s += "nbParStatut(StatutEntreprise.DEBUTANT)" + ";";
		s += "nbParStatut(StatutEntreprise.INTERMEDIARE)" + ";";
		s += "nbParStatut(StatutEntreprise.EXPERIMENTE)" + ";";
		s += "nbParStatut(StatutEntreprise.EXPERT)" + ";";
//		s += "salaireMois()" + ";";

		return s;		
	}


	public String toString(){		
		String s = "";

		s += localisation + ";";
		s += numberOfMember() + ";";
		s += nbParStatut(StatutEntreprise.DEBUTANT) + ";";
		s += nbParStatut(StatutEntreprise.INTERMEDIARE) + ";";
		s += nbParStatut(StatutEntreprise.EXPERIMENTE) + ";";
		s += nbParStatut(StatutEntreprise.EXPERT) + ";";
//		s += salaireMois() + ";";

		return s;
	}
}
