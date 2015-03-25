import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Divers.Pays;
import Divers.StatutEntreprise;

/**
 * Class de la Simulation !
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class Simulation {
	private Equipe eqFrance; // Not used first
	private Equipe eqRoumanie;
	private ResAndDev prFrance; // Not used first
	private ResAndDev prRoumanie;
	private int temps;
	private int masseSalarial = 0; // initialement zero


	/*****  Paramétres de la simulation A MODIFIER *****/
	private int TEMPSMAX = 280; // En mois
	private boolean DEBUG = false; // Add comments

	// Cas du TurnOver
	private boolean ActiveTurnOver = true; // True
	private int TurnOverMode = 2; // Mode 1 = Avec etat psychologique | Mode 2 : Avec turnOver de la population globale 
	private float turnOver = (float) 0.05 ;

	// Taux d'actualisation de la masse salarial:
	private float tauxActu = 2/100; // taux : [0,1] 

	// Nombre de période de recrutement en un an
	private int nbPeriodRecrutement = 3;
	public final static int maxEmbaucheMmTemps = 5;

	public final static boolean enableMaxSizeEquipe = true;
	public final static int maxSizeEquipe = 30;

	// Tuteur
	public final static int NBMAXELEVE = 4;


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

	public float getTurnOver() {
		return turnOver;
	}
	public void setTurnOver(float turnOver) {
		this.turnOver = turnOver;
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

	public boolean isActiveTurnOver() {
		return ActiveTurnOver;
	}
	public void DesactiveTurnOver() {
		ActiveTurnOver = false;
	}
	public void ActiveTurnOver() {
		ActiveTurnOver = true;
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

		saveTitre(eqRoumanie, prRoumanie); // Pour la visualisation post Simulation
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

		saveTitre(eqRoumanie, prRoumanie); // Pour la visualisation post Simulation
	}


	/****************************************/
	/*  			Simulation   			*/
	/****************************************/

	public void unMois(){

		// Création de nouveau projet, si possible
		prRoumanie.oneMonth();

		// Designer des tuteurs, si possible
		eqRoumanie.repartirTuteur();

		// Augmenter l'experience, si possible
		// + Faire augmenter le statut de toutes personnes étant capable de progresser.
		eqRoumanie.moisSuivant();

		// Calcul de la masse salarial
		if(DEBUG) System.out.println("Repartition salarial à l'instant " + temps + " est : " + eqRoumanie.toString());
		//		System.out.println("Masse salarial à l'instant " + temps + " est de : " + eqRoumanie.salaireMois());
		masseSalarial += eqRoumanie.salaireMois(temps, tauxActu);


		// Risque psychosociaux ?
		if(ActiveTurnOver){
			switch(TurnOverMode){
			case 1: // Methode avec un raisonnement sur chaque personne : turnOver provenant de l'environnement + état psychologique
				eqRoumanie.setMETHOD1();
				eqRoumanie.unsetMETHOD2();
				// Estime que l'environnement est de manière équiprobable bonne ou mauvaise
				eqRoumanie.turnOver((Math.random() > 0.5 ? true: false));
				break;

			case 2:	// Méthode avec un turnOver s'appliquant au groupe
				eqRoumanie.unsetMETHOD1();
				eqRoumanie.setMETHOD2();
				eqRoumanie.setTurnOver(getTurnOver()); // Pourcentage de personne quittant l'entreprise
				eqRoumanie.turnOver(false); // false : inutile
				break;
			default:
				break;
			}
		}

		// Embauche ? : test tous les nbPeriodRecrutement fois par an
		//		eqRoumanie.gestionEmbauche(enviINTERMEDIAIRE, enviEXPERIMENTE) // déjà fait dans moisSuivant()
		if(temps % nbPeriodRecrutement == 0) prRoumanie.embauche();

		// Sauvegarde des paramètres importantes en fin de mois
		saveEquipe(eqRoumanie, temps);
		saveProjet(prRoumanie, temps);
	}


	public boolean Simu(){
		System.out.println("[SIMU] Configuration Initial : ");
		System.out.println(" Nb DEBUTANT : " + getEqRoumanie().nbParStatut(StatutEntreprise.DEBUTANT) +
				" - Nb INTERMEDIARE : " + getEqRoumanie().nbParStatut(StatutEntreprise.INTERMEDIARE) +
				" - Nb EXPERIMENTE : " + getEqRoumanie().nbParStatut(StatutEntreprise.EXPERIMENTE) +
				" - Nb EXPERT : " + getEqRoumanie().nbParStatut(StatutEntreprise.EXPERT));

		temps = 0;
		while(temps < TEMPSMAX && eqRoumanie.nbParStatut(StatutEntreprise.EXPERIMENTE) <= 15){
			temps ++;
			unMois();
		}

		// Affichage résultats
		System.out.print("[SIMU][RESULTAT] Etat Final en " + temps + " mois, est : ");
		System.out.println(eqRoumanie.toString());
		System.out.println("[SIMU][RESULTAT] Coût vrai final : " + getMasseSalarial() + " Unité de valeur.");

		System.out.println("[SIMU][RESULTAT] Etat Final en " + ((int)temps/12) + " ans - " + (temps - 12*((int)temps/12)) + " mois");
		System.out.println("[SIMU][RESULTAT] Nombre de projets réalisés : " + prRoumanie.getNombreProjet());
		System.out.println("[SIMU][RESULTAT] Nombre de projets en cours : " + prRoumanie.numberOfProjet());

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

	public void saveTitre(Equipe eq, ResAndDev pr){		
		String s = "Paramétres du système : ";
		
		s += ";TurnOver : " + ";";
		s += "ActiveTurnOver : " + ActiveTurnOver + ";";
		s += "TurnOverMode : " + TurnOverMode + ";";
		
		s += ";Recrutement : " + ";";
		s += "nbPeriodRecrutement : " + nbPeriodRecrutement + ";";
		s += "maxEmbaucheMmTemps : " + maxEmbaucheMmTemps + ";";	
		s += "enableMaxSizeEquipe : " + enableMaxSizeEquipe + ";";
		s += "maxSizeEquipe : " + maxSizeEquipe + ";";
		s += "NBMAXELEVE : " + NBMAXELEVE + ";";
		
		s += ";Divers : " + ";";
		s += "TEMPSMAX : " + TEMPSMAX + ";";
		
		
		try {
			FileWriter aEcrire=new FileWriter("sauvegardeEquipe.csv", true);			
			aEcrire.write(s);
			aEcrire.write("\n");
			aEcrire.write("Temps ; " + eq.titretoString());
			aEcrire.write("\n");
			aEcrire.close();

			aEcrire=new FileWriter("sauvegardeProjet.csv", true);
			aEcrire.write("Temps ; " + pr.toStringTitre());
			aEcrire.write("\n");
			aEcrire.close();
		}
		catch (IOException e){
			System.out.println("[SIMU] Probleme dans la sauvegarde de l'equipe");
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
			System.out.println("[SIMU] Probleme dans la sauvegarde de l'equipe");
			e.getStackTrace();
		}		
	}

	public void saveProjet(ResAndDev pr, int time){		
		try {
			FileWriter aEcrire=new FileWriter("sauvegardeProjet.csv", true);
			aEcrire.write("" + time + ";" + pr.toStringSave());
			aEcrire.write("\n");
			aEcrire.close();
		}
		catch (IOException e){
			System.out.println("[SIMU] Probleme dans la sauvegarde du projet");
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
