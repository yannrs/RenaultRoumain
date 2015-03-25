import java.util.ArrayList;
import java.util.Iterator;

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

	// Gestion du maximum de personne embauché en même temps

	private boolean METHOD1 = true;
	private boolean METHOD2 = false;

	private float turnOver = (float) 0.2;

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
//
//	public int getMaxSizeEquipe() {
//		return maxSizeEquipe;
//	}
//	public void setMaxSizeEquipe(int maxSizeEquipe) {
//		this.maxSizeEquipe = maxSizeEquipe;
//	}
//
//
	public boolean isEnableMaxSizeEquipe() {
		return Simulation.enableMaxSizeEquipe;
	}
//	public void EnableMaxSizeEquipe() {
//		this.enableMaxSizeEquipe = true;
//	}
//	public void DisableMaxSizeEquipe() {
//		this.enableMaxSizeEquipe = false;
//	}

	public int getMaxEmbaucheMmTemps() {
		return Simulation.maxEmbaucheMmTemps;
	}
//	public void setMaxEmbaucheMmTemps(int maxEmbaucheMmTemps) {
//		this.maxEmbaucheMmTemps = maxEmbaucheMmTemps;
//	}


	public boolean isMETHOD1() {
		return METHOD1;
	}
	public void setMETHOD1() {
		METHOD1 = true;
	}
	public void unsetMETHOD1() {
		METHOD1 = false;
	}

	public boolean isMETHOD2() {
		return METHOD2;
	}
	public void setMETHOD2() {
		METHOD2 = true;
	}
	
	public void unsetMETHOD2() {
		METHOD2 = false;
	}
	
	public float getTurnOver() {
		return turnOver;
	}
	public void setTurnOver(float turnOver) {
		this.turnOver = turnOver;
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
	
	public void embauche(StatutEntreprise status){
		addMember(new Salarie(getLocalisation(), status));
	}

	/**
	 * Embauche de nb personne en même temps si possible (fct des contraintes d
	 * @param nb
	 */
	public void embauche(int nb){
		if(Simulation.enableMaxSizeEquipe){
			int nbActuel = numberOfMember();

			if(nb > getMaxEmbaucheMmTemps()) nb = getMaxEmbaucheMmTemps();

			if(Simulation.maxSizeEquipe >= nbActuel + nb){
				for(int i = 0; i<nb; i++){
					addMember(new Salarie(getLocalisation()));
				}
			}
			else{
				for(int i = 0; i<(Simulation.maxSizeEquipe - nbActuel); i++){
					addMember(new Salarie(getLocalisation()));
				}
			}
		}
		else{
			for(int i = 0; i<nb; i++){
				addMember(new Salarie(getLocalisation()));
			}
		}
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

	/**
	 * Estime et applique le turnover d'un groupe de personne
	 * @param envi : Uniquement utile en mode 1 : 
	 * true -> environnement bon -> faible turnOver
	 * false -> environnement défavorable -> proba plus importante
	 */
	public void turnOver(boolean envi){
		
		if(METHOD1){
			Iterator<Salarie> listep = this.getEquipe().iterator();
			while(listep.hasNext()){
				Salarie sal = listep.next();
				if(sal.turnOver((envi ? (double)(Math.random()*0.5) : (double)(Math.random()*2) )) > (envi ? 0.95 : 0.80)){
					System.out.println("Une personne en moins ...");
					listep.remove();
				}
			}
		}

		if(METHOD2){
			// Compte le nombre de personne présente dans l'équipe par niveau.
			int nbDEBUTANT = 0;
			int nbINTERMEDIAIRE = 0;
			int nbEXPERIMENTE = 0;
			int nbEXPERT = 0;
			int nbTUTEUR = 0;

			for( Salarie sal : this.getEquipe()){
				switch(sal.getNiveau().getStatut()){
				case DEBUTANT:
					nbDEBUTANT ++;
					break;
				case INTERMEDIARE:
					nbINTERMEDIAIRE ++;
					break;
				case EXPERIMENTE:
					nbEXPERIMENTE ++;
					break;
				case EXPERT:
					nbEXPERT ++;
					break;
				default:
					break;
				}
			}

			// Applique le turnOver sur la population
			nbDEBUTANT = (int) (nbDEBUTANT*getTurnOver());
			nbINTERMEDIAIRE = (int) (nbINTERMEDIAIRE*getTurnOver()/2);
			nbEXPERIMENTE = (int) (nbEXPERIMENTE*getTurnOver()/4);
			nbEXPERT = (int) (nbEXPERT*getTurnOver());

			Iterator<Salarie> listep = this.getEquipe().iterator();
			while(listep.hasNext()){
				Salarie sal = listep.next();
				switch(sal.getNiveau().getStatut()){
				case DEBUTANT:
					if(nbDEBUTANT > 0) listep.remove();
					nbDEBUTANT --;
					break;
				case INTERMEDIARE:
					if(nbINTERMEDIAIRE > 0) listep.remove();
					nbINTERMEDIAIRE --;
					break;
				case EXPERIMENTE:
					if(nbEXPERIMENTE > 0) listep.remove();
					nbEXPERIMENTE --;
					break;
				case EXPERT:
					nbEXPERT --;
					break;
				default:
					break;
				}
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

	public int salaireMois(int temps, float taux){
		int s = 0;
		for(Salarie sal : this.equipe){
			s += sal.getSalaire(getLocalisation()).getMontant(temps, taux);
		}		
		return s;
	}


	/*******Etat équipe********/
	public int isSomebodyNoWorking(){
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


		// --- Mise en pratique
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

		turnOver(enviINTERMEDIAIRE);
		gestionEmbauche(enviINTERMEDIAIRE, enviEXPERIMENTE);
	}


	/*******Gestion des Embauches********/
	public boolean gestionEmbauche(boolean enviINTERMEDIAIRE, boolean enviEXPERIMENTE){

		// -- S'il n'y a pas assez de personne de niveau Intermédiaire pour faire progresser les autres
		// FIXME : pb de temps (6 mois pour que les nouveaux arrivent)
		if(!enviINTERMEDIAIRE){
			// Embauche si pas assez de monde (niveau debutant)
			if(nbParStatut(StatutEntreprise.DEBUTANT)<4) {
				embauche(4-nbParStatut(StatutEntreprise.DEBUTANT));
			}
			// else les recrues vont bientôt arriver!
		}
		
		return true;
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

		s += "Localisation" + ";";
		s += "Nombre de Membre" + ";";
		s += "DEBUTANT" + ";";
		s += "INTERMEDIARE" + ";";
		s += "EXPERIMENTE" + ";";
		s += "EXPERT" + ";";
//		s += "salaireMois" + ";";

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
