import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Divers.Pays;
import Divers.StatutEntreprise;

/**
 * Class principal de la Simulation !
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class Simulation {
	private Equipe eqFrance; // Not used first
	private Equipe eqRoumanie;
	private ResAndDev prFrance; // Not used first
	private ResAndDev prRoumanie;
	private int temps;
	private int masseSalarial = 0;
	
	private int TEMPSMAX = 180; 
	
	private boolean DEBUG = false; // Add comments

	/****************************************/
	/*  		 Getter && Setter 	 		*/
	/****************************************/
	
	public int getMasseSalarial() {
		return masseSalarial;
	}
	private void setMasseSalarial(int masseSalarial) {
		this.masseSalarial = masseSalarial;
	}
	public void resetMasseSalarial(){
		setMasseSalarial(0);
	}

	public int getTemps() {
		return temps;
	}
	private void setTemps(int temps) {
		this.temps = temps;
	}
	public void resetTemps(){
		setTemps(0);
	}

	public Equipe getEqFrance() {
		return eqFrance;
	}
	private void setEqFrance(Equipe eqFrance) {
		this.eqFrance = eqFrance;
	}
	
	public Equipe getEqRoumanie() {
		return eqRoumanie;
	}
	private void setEqRoumanie(Equipe eqRoumanie) {
		this.eqRoumanie = eqRoumanie;
	}
	
	public ResAndDev getPrFrance() {
		return prFrance;
	}
	private void setPrFrance(ResAndDev prFrance) {
		this.prFrance = prFrance;
	}
	
	public ResAndDev getPrRoumanie() {
		return prRoumanie;
	}
	private void setPrRoumanie(ResAndDev prRoumanie) {
		this.prRoumanie = prRoumanie;
	}
	
	
	public boolean isDEBUG() {
		return DEBUG;
	}
	public void setDEBUG() {
		DEBUG = true;
	}
	public void unSetDEBUG() {
		DEBUG = false;
	}
	/****************************************/
	/*  	INITIALISATION : Etat inital  	*/
	/****************************************/
	
	public void initial(){		
		// -------- Creation de l'équipe
//		eqFrance = new Equipe(Pays.FRANCE);
		eqRoumanie = new Equipe(Pays.ROUMANIE);
		
		// Ajout des membres - Salariés
		// Expartiés - Expert
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERIMENTE));
		
		// Roumain - Debutant
		eqRoumanie.addMember(new Salarie(Pays.ROUMANIE));
		eqRoumanie.addMember(new Salarie(Pays.ROUMANIE));
		eqRoumanie.addMember(new Salarie(Pays.ROUMANIE));
		eqRoumanie.addMember(new Salarie(Pays.ROUMANIE));		
		
		
		// -------- Creation projet
		// Start without project
		prRoumanie = new ResAndDev(eqRoumanie);
		
		saveTitre(eqRoumanie, new Projet());
	}

	public void initial(int nbDEBUTANT, int nbINTERMEDIAIRE, int nbEXPERIMENTE, int nbEXPERT){		
		// -------- Creation de l'équipe
//		eqFrance = new Equipe(Pays.FRANCE);
		eqRoumanie = new Equipe(Pays.ROUMANIE);
		
		// Ajout des membres - Salariés
		for(int i = 0 ; i< nbDEBUTANT; i++){
			eqRoumanie.addMember(new Salarie(Pays.ROUMANIE));
		}
		
		for(int i = 0 ; i< nbINTERMEDIAIRE; i++){
			eqRoumanie.addMember(new Salarie(Pays.FRANCE,StatutEntreprise.INTERMEDIARE));
		}

		for(int i = 0 ; i< nbEXPERIMENTE; i++){
			eqRoumanie.addMember(new Salarie(Pays.FRANCE,StatutEntreprise.EXPERIMENTE));
		}
		
		for(int i = 0 ; i< nbEXPERT; i++){
			eqRoumanie.addMember(new Salarie(Pays.FRANCE,StatutEntreprise.EXPERT));
		}
		
		// -------- Creation projets
		// Start without project
		prRoumanie = new ResAndDev(eqRoumanie);
		
		saveTitre(eqRoumanie, new Projet());
	}
	

	/****************************************/
	/*  			Simulation   			*/
	/****************************************/
	
	public void unMois(){
		
		// Création de nouveau projet si possible
		prRoumanie.oneMonth();
		
		// Designer des tuteurs si possible
		eqRoumanie.repartirTuteur();
				
		// Augmenter l'experience si possible
		// + Faire augmenter le statut de toutes personnes étant capable de progresser.
		eqRoumanie.moisSuivant();
		
		// Calcul de la masse salarial
		if(DEBUG) System.out.println("Repartition salarial à l'instant " + temps + " est : " + eqRoumanie.toString());
//		System.out.println("Masse salarial à l'instant " + temps + " est de : " + eqRoumanie.salaireMois());
		masseSalarial += eqRoumanie.salaireMois();
		
		
		// Risque psychosociaux ?
		
		
		// Embauche ?
//		eqRoumanie.gestionEmbauche(enviINTERMEDIAIRE, enviEXPERIMENTE) // déjà fait dans moisSuivant()
		prRoumanie.embauche();
		
		// Sauvegarde des paramètres importantes en fin de mois
		saveEquipe(eqRoumanie, temps);
		saveProjet(prRoumanie, temps);
	}
	
	
	public boolean Simu(){
		System.out.println("Configuration Initial : ");
		System.out.println(" Nb DEBUTANT : " + getEqRoumanie().nbParStatut(StatutEntreprise.DEBUTANT) +
				" - Nb INTERMEDIARE : " + getEqRoumanie().nbParStatut(StatutEntreprise.INTERMEDIARE) +
				" - Nb EXPERIMENTE : " + getEqRoumanie().nbParStatut(StatutEntreprise.EXPERIMENTE) +
				" - Nb EXPERT : " + getEqRoumanie().nbParStatut(StatutEntreprise.EXPERT));
		
		temps = 0;
		while(temps < TEMPSMAX && eqRoumanie.nbParStatut(StatutEntreprise.EXPERIMENTE) <= 15){
			temps ++;
			unMois();
		}
		System.out.print("[SIMU] Etat Final en " + temps + " est : ");
		System.out.println(eqRoumanie.toString());
		
//		return eqRoumanie.numberOfMember() >= 30;
		return true;
	}
	
	
	public void Reset(){
		if(DEBUG) System.out.println("[SIMU] RESET ! ");
//		eqFrance = new Equipe(Pays.FRANCE);
//		prFrance = new ResAndDev(eqFrance);
		
		eqRoumanie = new Equipe(Pays.ROUMANIE); 
		prRoumanie = new ResAndDev(eqRoumanie);
		
		resetMasseSalarial();
		resetTemps();
	}

	/****************************************/
	/*  			SAUVEGARDE		 	 	*/
	/****************************************/
	
	public void saveTitre(Equipe eq, Projet pr){		
		try {
			FileWriter aEcrire=new FileWriter("sauvegardeEquipe.csv", true);
			aEcrire.write("Temps ; " + eq.titretoString());
			aEcrire.write("\n");
			aEcrire.close();
			
//			aEcrire=new FileWriter("sauvegardeProjet.csv", true);
//			aEcrire.write("Temps ; " + pr.titretoString());
//			aEcrire.write("\n");
//			aEcrire.close();
		}
		catch (IOException e){
			System.out.println("[MAIN] Probleme dans la sauvegarde de l'equipe");
			e.getStackTrace();
		}		
	}
	
	public void saveEquipe(Equipe eq, int time){		
		try {
			FileWriter aEcrire=new FileWriter("sauvegardeEquipe.csv", true);
			aEcrire.write("" + time + ";" + eq.toString());
			aEcrire.write("\n");
			aEcrire.close();
		}
		catch (IOException e){
			System.out.println("[MAIN] Probleme dans la sauvegarde de l'equipe");
			e.getStackTrace();
		}		
	}
	
	public void saveProjet(ResAndDev pr, int time){		
		try {
			FileWriter aEcrire=new FileWriter("sauvegardeProjet.csv", true);
			aEcrire.write("" + time + ";" + pr.toString());
			aEcrire.write("\n");
			aEcrire.close();
		}
		catch (IOException e){
			System.out.println("[MAIN] Probleme dans la sauvegarde du projet");
			e.getStackTrace();
		}		
	}
	
	
	
//	
//	public static void main(String[] args) {
//		Simulation m = new Simulation();
//		
////		m.initial();
//		m.initial(16,0,0,4);
//		m.Simu();
//		
//		
//	}
	
	
}
