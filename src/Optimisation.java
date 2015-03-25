import Divers.StatutEntreprise;

/**
 *  Class Maitre : optimise les param�tres d'entr�s de la simulation 
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class Optimisation {
	// -- Param�tres de l'optimisation
	private int minDEBUTANT = 0;
	private	int minINTERMEDIAIRE = 0;
	private int minEXPERIMENTE = 0;
	private int minEXPERT = 0;

	private int maxDEBUTANT = 5;
	private int maxINTERMEDIAIRE = 2;
	private int maxEXPERIMENTE = 3;
	private int maxEXPERT = 20;

	
	/*****  Param�tre inh�rent � simulation *****/
	// A modifier directement et uniquement dans la class SIMULATION
	// Sinon rajouter un setter ...
	
	// Param�tres pouvant �tre modifi�s:
	//	- Nombre de personne maximum recrut� en m�me temps : NBMAXELEVE (par d�faut : 4)
	//	- Nombre de p�riode de recrutement par an : nbPeriodRecrutement (par d�faut : 3)
	//	- Activation de la restriction en nombre d'un �quipe : (par d�faut : true)
	//	- Nombre de personne composant une �quipe : (par d�faut : 30)
	
	//	- Taux d'actualisation du salaire : tauxActu (par d�faut : 0.002)
	
	//	- Activation du turnOver : ActiveTurnOver (true ou false) (par d�faut : false)
	//	- Mod�le utilis� pour le TurnOver : TurnOverMode (1 ou 2)

	//	- Temps de simulation : TEMPSMAX (par d�faut : 180 mois = 15 ans)
	
	//	- Nombre d'�l�ve pouvant �tre pris en m�me temps : (par d�faut : 4)
	
	//	- 3 M�thodes de recrutement : Class ResAndDev (par d�faut : toutes les 3 activ�es)

	
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
		System.out.println("[OPTI] Nombre de solution trouv� : " + nbSolution + " sur les " + nbPosibilite +" test�es");

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
		
		System.out.println("[OPTIM] Nombre de solution trouv� : " + nbSolution + " sur les " + nbPosibilite +" test�es");

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
		
		System.out.println("[OPTIM] Nombre de solution trouv� : " + nbSolution + " sur les " + nbPosibilite +" test�es");

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
