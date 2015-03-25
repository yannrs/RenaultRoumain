import Divers.StatutEntreprise;

/**
 *  Class Maitre : optimise les paramétres d'entrés de la simulation 
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class Optimisation {
	// -- Paramétres de l'optimisation
	private int minDEBUTANT = 0;
	private	int minINTERMEDIAIRE = 0;
	private int minEXPERIMENTE = 0;
	private int minEXPERT = 0;

	private int maxDEBUTANT = 5;
	private int maxINTERMEDIAIRE = 2;
	private int maxEXPERIMENTE = 3;
	private int maxEXPERT = 20;

	
	/*****  Paramétre inhérent à simulation *****/
	// A modifier directement et uniquement dans la class SIMULATION
	// Sinon rajouter un setter ...
	
	// Paramétres pouvant être modifiés:
	//	- Nombre de personne maximum recruté en même temps : NBMAXELEVE (par défaut : 4)
	//	- Nombre de période de recrutement par an : nbPeriodRecrutement (par défaut : 3)
	//	- Activation de la restriction en nombre d'un équipe : (par défaut : true)
	//	- Nombre de personne composant une équipe : (par défaut : 30)
	
	//	- Taux d'actualisation du salaire : tauxActu (par défaut : 0.002)
	
	//	- Activation du turnOver : ActiveTurnOver (true ou false) (par défaut : false)
	//	- Modèle utilisé pour le TurnOver : TurnOverMode (1 ou 2)

	//	- Temps de simulation : TEMPSMAX (par défaut : 180 mois = 15 ans)
	
	//	- Nombre d'élève pouvant être pris en même temps : (par défaut : 4)
	
	//	- 3 Méthodes de recrutement : Class ResAndDev (par défaut : toutes les 3 activées)

	
	/********************************************/
	/*  		METHODES : Optimisation  		*/
	/********************************************/	
	public void optimisationSalaire(){
		Simulation s = new Simulation();

		int minMasseSalarial = Integer.MAX_VALUE;
		int maxMasseSalarial = 0;
		int[] configMin = { 0, 0, 0, 0};
		int nbSolution = 0;


		for(int nbDEBUTANT = minDEBUTANT ; nbDEBUTANT < maxDEBUTANT ; nbDEBUTANT++ ){
			for(int nbINTERMEDIAIRE = minINTERMEDIAIRE ; nbINTERMEDIAIRE < maxINTERMEDIAIRE ; nbINTERMEDIAIRE++ ){
				for(int nbEXPERIMENTE = minEXPERIMENTE ; nbEXPERIMENTE < maxEXPERIMENTE ; nbEXPERIMENTE++ ){
					for(int nbEXPERT = minEXPERT ; nbEXPERT < maxEXPERT ; nbEXPERT++ ){
						s.Reset();
						s.initial(nbDEBUTANT, nbINTERMEDIAIRE, nbEXPERIMENTE, nbEXPERT);

						s.getPrRoumanie().setMETHOD1();
						s.getPrRoumanie().setMETHOD2();
						s.getPrRoumanie().setMETHOD3();
//						s.getEqRoumanie().EnableMaxSizeEquipe();

						// SOLUTION ?
						if(s.Simu() && s.getEqRoumanie().nbParStatut(StatutEntreprise.EXPERIMENTE) >= 15) {
							nbSolution++;

							// MINIMAL ?
							if(minMasseSalarial > s.getMasseSalarial()){
								minMasseSalarial = s.getMasseSalarial();
								configMin[0] = nbDEBUTANT;
								configMin[1] = nbINTERMEDIAIRE;
								configMin[2] = nbEXPERIMENTE;
								configMin[3] = nbEXPERT;
							}	

							// MAXI ?
							if(maxMasseSalarial <  s.getMasseSalarial()) maxMasseSalarial = s.getMasseSalarial();	
						}
					}
				}
			}
		}

		int nbPosibilite = (maxDEBUTANT - minDEBUTANT)*(maxINTERMEDIAIRE - minINTERMEDIAIRE)*(maxEXPERIMENTE - minEXPERIMENTE)*(maxEXPERT - minEXPERT);

		System.out.println("[OPTI] Configuration Minimal : ");

		System.out.println("[OPTI] Nb DEBUTANT : " + configMin[0] +
				" - Nb INTERMEDIARE : " + configMin[1]  +
				" - Nb EXPERIMENTE : " + configMin[2]  +
				" - Nb EXPERT : " + configMin[3] );

		System.out.println("[OPTI] Minimum attend, en masse salarial : " + minMasseSalarial + " (max : " + maxMasseSalarial + ")");
		System.out.println("[OPTI] Nombre de solution trouvé : " + nbSolution + " sur les " + nbPosibilite +" testées");

	}


	public void optimisationTemps(){
		Simulation s = new Simulation();

		int minTemps = Integer.MAX_VALUE;
		int maxTemps = 0;
		int[] configMin = { 0, 0, 0, 0};
		int nbSolution = 0;


		for(int nbDEBUTANT = minDEBUTANT ; nbDEBUTANT < maxDEBUTANT ; nbDEBUTANT++ ){
			for(int nbINTERMEDIAIRE = minINTERMEDIAIRE ; nbINTERMEDIAIRE < maxINTERMEDIAIRE ; nbINTERMEDIAIRE++ ){
				for(int nbEXPERIMENTE = minEXPERIMENTE ; nbEXPERIMENTE < maxEXPERIMENTE ; nbEXPERIMENTE++ ){
					for(int nbEXPERT = minEXPERT ; nbEXPERT < maxEXPERT ; nbEXPERT++ ){
						s.Reset();
						s.initial(nbDEBUTANT, nbINTERMEDIAIRE, nbEXPERIMENTE, nbEXPERT);

						s.getPrRoumanie().setMETHOD1();
						s.getPrRoumanie().setMETHOD2();
						s.getPrRoumanie().setMETHOD3();
//						s.getEqRoumanie().EnableMaxSizeEquipe();

						// SOLUTION ?
						if(s.Simu() && s.getEqRoumanie().nbParStatut(StatutEntreprise.EXPERIMENTE) >= 15) {
							nbSolution++;

							// MINIMAL ?
							if(minTemps > s.getTemps()){
								minTemps = s.getTemps();
								configMin[0] = nbDEBUTANT;
								configMin[1] = nbINTERMEDIAIRE;
								configMin[2] = nbEXPERIMENTE;
								configMin[3] = nbEXPERT;
							}	

							// MAXI ?
							if(maxTemps <  s.getTemps()) maxTemps = s.getTemps();	
						}
					}
				}
			}
		}

		int nbPosibilite = (maxDEBUTANT - minDEBUTANT)*(maxINTERMEDIAIRE - minINTERMEDIAIRE)*(maxEXPERIMENTE - minEXPERIMENTE)*(maxEXPERT - minEXPERT);

		System.out.println("[OPTIM] Configuration Minimal : ");
		System.out.println(" Nb DEBUTANT : " + configMin[0] +
				" - Nb INTERMEDIARE : " + configMin[1]  +
				" - Nb EXPERIMENTE : " + configMin[2]  +
				" - Nb EXPERT : " + configMin[3] );

		System.out.println("[OPTIM] Minimum attend, en masse salarial : " + minTemps + " (max : " + maxTemps + ")");
		System.out.println("[OPTIM] Convertion du temps : " + minTemps + " mois = " + ((int)minTemps/12) + " ans - " + (minTemps - 12*((int)minTemps/12)) + " mois");
		
		System.out.println("[OPTIM] Nombre de solution trouvé : " + nbSolution + " sur les " + nbPosibilite +" testées");

	}


	public void optimisationTempsSalaire(int gainTemps, int gainSalaire){
		Simulation s = new Simulation();

		int minTemps = Integer.MAX_VALUE;
		int maxTemps = 1;
		int minMasseSalarial = Integer.MAX_VALUE;
		int maxMasseSalarial = 1;

		int minOpti = Integer.MAX_VALUE;
		int maxOpti = 0;
		int[] configMin = { 0, 0, 0, 0};
		int nbSolution = 0;


		for(int nbDEBUTANT = minDEBUTANT ; nbDEBUTANT < maxDEBUTANT ; nbDEBUTANT++ ){
			for(int nbINTERMEDIAIRE = minINTERMEDIAIRE ; nbINTERMEDIAIRE < maxINTERMEDIAIRE ; nbINTERMEDIAIRE++ ){
				for(int nbEXPERIMENTE = minEXPERIMENTE ; nbEXPERIMENTE < maxEXPERIMENTE ; nbEXPERIMENTE++ ){
					for(int nbEXPERT = minEXPERT ; nbEXPERT < maxEXPERT ; nbEXPERT++ ){
						s.Reset();
						s.initial(nbDEBUTANT, nbINTERMEDIAIRE, nbEXPERIMENTE, nbEXPERT);

						s.getPrRoumanie().setMETHOD1();
						s.getPrRoumanie().setMETHOD2();
						s.getPrRoumanie().setMETHOD3();
//						s.getEqRoumanie().EnableMaxSizeEquipe();

						// SOLUTION ?
						if(s.Simu() && s.getEqRoumanie().nbParStatut(StatutEntreprise.EXPERIMENTE) >= 15) {
							nbSolution++;

							// MINIMAL ?
							if(minOpti > (s.getTemps()*gainTemps/(float)maxTemps) + (s.getMasseSalarial()*gainSalaire/(float)maxMasseSalarial)){
								minOpti = (int) ((s.getTemps()*gainTemps/(float)maxTemps) + (s.getMasseSalarial()*gainSalaire/(float)maxMasseSalarial));
								minTemps = s.getTemps();
								minMasseSalarial = s.getMasseSalarial();
								configMin[0] = nbDEBUTANT;
								configMin[1] = nbINTERMEDIAIRE;
								configMin[2] = nbEXPERIMENTE;
								configMin[3] = nbEXPERT;
							}	

							// MAXI ?
							if( maxOpti <  s.getTemps()*gainTemps/maxTemps + s.getMasseSalarial()*gainSalaire/maxMasseSalarial){
								maxOpti = s.getTemps()*gainTemps/maxTemps + s.getMasseSalarial()*gainSalaire/maxMasseSalarial;
								maxTemps = s.getTemps();	
								maxMasseSalarial = s.getMasseSalarial();
							}
						}
					}
				}
			}
		}

		int nbPosibilite = (maxDEBUTANT - minDEBUTANT)*(maxINTERMEDIAIRE - minINTERMEDIAIRE)*(maxEXPERIMENTE - minEXPERIMENTE)*(maxEXPERT - minEXPERT);

		System.out.println("[OPTIM] Configuration Minimal : ");

		System.out.println("[OPTIM] Nb DEBUTANT : " + configMin[0] +
				" - Nb INTERMEDIARE : " + configMin[1]  +
				" - Nb EXPERIMENTE : " + configMin[2]  +
				" - Nb EXPERT : " + configMin[3] );

		System.out.println("[OPTIM] Minimum attend, en MASSE SALARIAL : " + minMasseSalarial + " (max : " + maxMasseSalarial + ")");
		System.out.println("[OPTIM] Minimum attend, en TEMPS : " + minTemps + " (max : " + maxTemps + ")");
		System.out.println("[OPTIM] Convertion du temps : " + minTemps + " mois = " + ((int)minTemps/12) + " ans - " + (minTemps - 12*((int)minTemps/12)) + " mois");
		
		System.out.println("[OPTIM] Nombre de solution trouvé : " + nbSolution + " sur les " + nbPosibilite +" testées");

	}



	public void essaiModele(){
		Simulation s = new Simulation();
		s.setDEBUG();
		s.initial(0, 0, 0, 5);
		s.getPrRoumanie().setMETHOD1();
		s.getPrRoumanie().setMETHOD2();
		s.getPrRoumanie().setMETHOD3();
//		s.setTurnOver((float) 0.2);
//		s.getEqRoumanie().EnableMaxSizeEquipe(); // ou
		//		s.getEqRoumanie().DisableMaxSizeEquipe();	
		s.Simu();
	}


	public static void main(String[] args) {
		(new Optimisation()).essaiModele();
		//		(new Optimisation()).optimisationSalaire();
		//		(new Optimisation()).optimisationTemps();
		//		(new Optimisation()).optimisationTempsSalaire(10, 2);
	}
}
