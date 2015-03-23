import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.ls.LSInput;

import Divers.Salaire;
import Divers.StatutEntreprise;

/**
 * Regroupement de l'ensemble des projets d'une unitée de Research and Development
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class ResAndDev {
	private ArrayList<Projet> ensembleProjet;
	private Equipe refPersonnel;

	// Methode d'embauche
	private boolean METHOD1 = true;
	private boolean METHOD2 = true;
	private boolean METHOD3 = true;
	
	/****************************/
	/*  	  CONSTRUCTEUR  	*/
	/****************************/

	public ResAndDev(Equipe refPersonnel) {
		super();
		this.ensembleProjet = new ArrayList<Projet>();
		this.refPersonnel = refPersonnel;
	}

	public ResAndDev(ArrayList<Projet> ensembleProjet, Equipe refPersonnel) {
		super();
		this.ensembleProjet = ensembleProjet;
		this.refPersonnel = refPersonnel;
	}

	/****************************/
	/*  	GETTER & SETTER  	*/
	/****************************/

	public ArrayList<Projet> getEnsembleProjet() {
		return ensembleProjet;
	}
	public void setEnsembleProjet(ArrayList<Projet> ensembleProjet) {
		this.ensembleProjet = ensembleProjet;
	}
	public Equipe getRefPersonnel() {
		return refPersonnel;
	}
	public void setRefPersonnel(Equipe refPersonnel) {
		this.refPersonnel = refPersonnel;
	}

	public boolean isMETHOD1() {
		return METHOD1;
	}
	public void setMETHOD1() {
		METHOD1 = true;
	}
	public void unSetMETHOD1() {
		METHOD1 = false;
	}

	public boolean isMETHOD2() {
		return METHOD2;
	}
	public void setMETHOD2() {
		METHOD2 = true;
	}
	public void unSetMETHOD2() {
		METHOD2 = false;
	}
	

	public boolean isMETHOD3() {
		return METHOD3;
	}
	public void setMETHOD3() {
		METHOD3 = true;
	}
	public void unSetMETHOD3() {
		METHOD3 = false;
	}
	/****************************/
	/*  		METHODES  		*/
	/****************************/

	/*******Gestion Projet********/	
	public void addProjet(Projet pr){
		this.getEnsembleProjet().add(pr);
	}

	public void deleteProjet(Projet pr){
		this.getEnsembleProjet().remove(pr);
	}

	public int numberOfProjet(){
		return this.getEnsembleProjet().size();
	}

	public boolean isEmpty(){
		return numberOfProjet()==0;
	}

	public boolean AreAllFull(){
		boolean b = true;
		for(Projet pr : this.getEnsembleProjet()){
			b &= pr.isEnoughPeople();			
		}
		return b;
	}

	public ArrayList<Projet> getFreeProject(){
		ArrayList<Projet> free = new ArrayList<Projet>();
		for(Projet pr : this.getEnsembleProjet()){
			if(!pr.isEnoughPeople()) free.add(pr);
		}
		return free;
	}
	
	/*******Gestion des projets non Complet********/
	public ArrayList<Projet> manageFreeProject(){
		ArrayList<Projet> listPr = getFreeProject();
		
		// HYPOTHÈSE : on ne peut finir un projet qu'avec une équipe invariante pendant 2.5 ans
		// Suppression de l'équipe et du projet pour être réintegré dans un nouveau projet
		for(Projet pr : listPr){
			for(Salarie sal : pr.getEquipe()){
				sal.setOnNoOneProject();
			}
		}
		
		return listPr;
	}
	
	/*******Gestion Personnel libre********/
	public ArrayList<Salarie> getFreePersonal(){
		ArrayList<Salarie> free = new ArrayList<Salarie>();
		for(Salarie sal : this.refPersonnel.getEquipe()){
			if(!sal.isOnOneProject() && sal.getNiveau().getExperience()>=(6+6.5*12)) free.add(sal);
		}
		return free;
	}
		
	public Salarie getOneStatut(StatutEntreprise statut, ArrayList<Salarie> eq){
		for(Salarie sal : eq){
			if(sal.getNiveau().getStatut() == statut) return sal;
		}
		return null;
	}

	/*******Placement dans un projet du Personnel libre********/
	public ArrayList<Projet> placeFreePeople(ArrayList<Salarie> free){
		ArrayList<Projet> listPr = new ArrayList<Projet>();

		// Compte du nombre de personne libre par Niveau
		int nbINTERMEDIAIRE = 0;
		int nbEXPERIMENTE = 0;
		int nbEXPERT = 0;

		for( Salarie sal : free){
			switch(sal.getNiveau().getStatut()){
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


		// Creation des projets si possible
		while(nbINTERMEDIAIRE >= 4 && (nbEXPERIMENTE + nbEXPERT) >= 1){
			Projet pr = new Projet();

			// 4 Intermediare
			Salarie sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			
			// Et 1 au moins expérimenté
			if(nbEXPERIMENTE>0){
				 sal = getOneStatut(StatutEntreprise.EXPERIMENTE, free);
				 if(sal != null) pr.addMember(sal); free.remove(sal); nbEXPERIMENTE--; sal.setOnOneProject();
			}
			else{
				sal = getOneStatut(StatutEntreprise.EXPERT, free);
				if(sal != null) pr.addMember(sal); free.remove(sal); nbEXPERT--; sal.setOnOneProject();
			}
			
			listPr.add(pr);
		}


		return listPr;
	}

	/*******Embauche : si possible********/
	public void embauche(){
		// Personne restante après la création des potentiels projets
		ArrayList<Salarie> freePersonne = getFreePersonal();
		int nbDEBUTANT = 0;
		int nbINTERMEDIAIRE = 0;
		int nbEXPERIMENTE = 0;
		int nbEXPERT = 0;
		int nbTUTEUR = 0;

		for( Salarie sal : freePersonne){
			switch(sal.getNiveau().getStatut()){
			case DEBUTANT:
				nbDEBUTANT ++;
				break;
			case INTERMEDIARE:
				nbINTERMEDIAIRE ++;
				break;
			case EXPERIMENTE:
				nbEXPERIMENTE ++;
				if(sal.isOneEleveCanAdd()) nbTUTEUR += sal.NBMAXELEVE - sal.nbEleve();
				break;
			case EXPERT:
				nbEXPERT ++;
				if(sal.isOneEleveCanAdd()) nbTUTEUR += sal.NBMAXELEVE - sal.nbEleve();
				break;
			default:
				break;
			}
		}

		// Si des equipes ne vont pas pouvoir se constituer pour former un projet, on embauche
		int nbEmbaucheDEBUTANT = 0;	
		
		if(METHOD1){
			// Si des tuteurs vont rester librent, on embauche
			if(nbTUTEUR - getRefPersonnel().nbParStatut(StatutEntreprise.DEBUTANT)> 0){
				nbEmbaucheDEBUTANT = (nbTUTEUR - getRefPersonnel().nbParStatut(StatutEntreprise.DEBUTANT));
//				System.out.println("Add !1 : " + nbEmbaucheDEBUTANT);
			}
		}

		if(METHOD2){
			// Suffisament de personnel de niveau intermédiaire pour occuper tout le monde
			if( nbINTERMEDIAIRE % 4 != 0 ||  ((nbINTERMEDIAIRE % 4) + nbDEBUTANT + nbEmbaucheDEBUTANT) % 4 != 0){
				nbEmbaucheDEBUTANT += 4 - (((nbINTERMEDIAIRE % 4) + nbDEBUTANT + nbEmbaucheDEBUTANT) % 4) ;
//				System.out.println("Add !2 : " + nbEmbaucheDEBUTANT);
//				System.out.println("normalement nbEmbaucheDebutant est un multiple de 4 : " + (nbEmbaucheDEBUTANT % 4 ) );
			}
		}
		
		if(METHOD3){
			if(nbEXPERIMENTE + nbEXPERT > 0 && 4*(nbEXPERIMENTE + nbEXPERT) - (nbDEBUTANT + nbEmbaucheDEBUTANT) >= 0){
				nbEmbaucheDEBUTANT += 4*(nbEXPERIMENTE + nbEXPERT) - (nbDEBUTANT + nbEmbaucheDEBUTANT);
//				System.out.println("Add !3 : " + nbEmbaucheDEBUTANT);
			}			
		}
		
		getRefPersonnel().embauche(nbEmbaucheDEBUTANT);		
	}
	
	
	/*******Simulation********/
	public void oneMonth(){
		// Add one month to every project
		for(Projet pr : this.ensembleProjet){
			pr.moisSuivant();
		}
		
		// Verify if one project is ended
		Iterator<Projet> lispr = this.ensembleProjet.iterator();
		while(lispr.hasNext()){
			Projet pr = lispr.next();
			if(pr.isFinished()){
				for(Salarie sal : pr.getEquipe()){
					sal.setOnNoOneProject();
				}
				lispr.remove();
			};
		}

		// Manage project without enough people -> deleted
		ArrayList<Projet> listpr = manageFreeProject();
		for(Projet pr : listpr){
			this.ensembleProjet.remove(pr);
		}
		
		// Verify if every Person works
		ArrayList<Salarie> freePersonne = getFreePersonal();

		if(!freePersonne.isEmpty()){
			ArrayList<Projet> Listpr = placeFreePeople(freePersonne);
			for(Projet pr : Listpr){
				this.ensembleProjet.add(pr);
			}
		}
		// else tout le monde travail = très bien !

	}

	

	/*******Sauvegarde/Affichage********/
	public String toString(){
		String s = "[;";
		for(Projet pr : this.ensembleProjet){
			s += pr.toString() + ";";
		}		
		s+= ";]";
		
		return s;
	}

}
