import java.util.ArrayList;

import org.w3c.dom.ls.LSInput;

import Divers.Salaire;
import Divers.StatutEntreprise;


public class ResAndDev {
	private ArrayList<Projet> ensembleProjet;
	private Equipe refPersonnel;


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

	public ArrayList<Salarie> getFreePersonal(){
		ArrayList<Salarie> free = new ArrayList<Salarie>();
		for(Salarie sal : this.refPersonnel.getEquipe()){
			if(!sal.isOnOneProject()) free.add(sal);
		}
		return free;
	}

	/*******Gestion Projet********/	
	public Salarie getOneStatut(StatutEntreprise statut, ArrayList<Salarie> eq){
		for(Salarie sal : eq){
			if(sal.getNiveau().getStatut() == statut) return sal;
		}
		return null;
	}

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

			Salarie sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			sal = getOneStatut(StatutEntreprise.INTERMEDIARE, free);
			if(sal != null) pr.addMember(sal); free.remove(sal); nbINTERMEDIAIRE--; sal.setOnOneProject();
			
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

	public void oneMonth(){
		// Add one month to every project
		for(Projet pr : this.ensembleProjet){
			pr.moisSuivant();
		}
		
		// Verify if one project is ended
		for(Projet pr : this.ensembleProjet){
			if(pr.isFinished()){
				for(Salarie sal : pr.getEquipe()){
					sal.setOnNoOneProject();
				}
//				this.ensembleProjet.remove(pr);
			}
		}
		for(Projet pr : this.ensembleProjet){
			if(pr.isFinished()){
				this.ensembleProjet.remove(pr);
			}
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

	
	public String toString(){
		String s = "[;";
		for(Projet pr : this.ensembleProjet){
			s += pr.toString() + ";";
		}		
		s+= ";]";
		
		return s;
	}

}
