import Divers.StatutEntreprise;


public class Optimisation {
	int minDEBUTANT = 30;
	int minINTERMEDIAIRE = 0;
	int minEXPERIMENTE = 0;
	int minEXPERT = 0;
	
	int maxDEBUTANT = 30;
	int maxINTERMEDIAIRE = 2;
	int maxEXPERIMENTE = 3;
	int maxEXPERT = 20;
	
	public void optimisationSalaire(){
		Simulation s = new Simulation();
		
//		int minDEBUTANT = 0;
//		int minINTERMEDIAIRE = 0;
//		int minEXPERIMENTE = 0;
//		int minEXPERT = 0;
//		
//		int maxDEBUTANT = 5;
//		int maxINTERMEDIAIRE = 2;
//		int maxEXPERIMENTE = 2;
//		int maxEXPERT = 7;
		
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
						s.getEqRoumanie().EnableMaxSizeEquipe();
						
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
		
		System.out.println("Configuration Minimal : ");

		System.out.println(" Nb DEBUTANT : " + configMin[0] +
				" - Nb INTERMEDIARE : " + configMin[1]  +
				" - Nb EXPERIMENTE : " + configMin[2]  +
				" - Nb EXPERT : " + configMin[3] );
		
		System.out.println("Minimum attend, en masse salarial : " + minMasseSalarial + " (max : " + maxMasseSalarial + ")");
		System.out.println("Nombre de solution trouvé : " + nbSolution + " sur les " + nbPosibilite +" testées");
		
	}
	

	public void optimisationTemps(){
		Simulation s = new Simulation();
		
//		int minDEBUTANT = 0;
//		int minINTERMEDIAIRE = 0;
//		int minEXPERIMENTE = 0;
//		int minEXPERT = 0;
//		
//		int maxDEBUTANT = 5;
//		int maxINTERMEDIAIRE = 2;
//		int maxEXPERIMENTE = 2;
//		int maxEXPERT = 7;
		
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
						s.getEqRoumanie().EnableMaxSizeEquipe();
						
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
		
		System.out.println("Configuration Minimal : ");

		System.out.println(" Nb DEBUTANT : " + configMin[0] +
				" - Nb INTERMEDIARE : " + configMin[1]  +
				" - Nb EXPERIMENTE : " + configMin[2]  +
				" - Nb EXPERT : " + configMin[3] );
		
		System.out.println("Minimum attend, en masse salarial : " + minTemps + " (max : " + maxTemps + ")");
		System.out.println("Nombre de solution trouvé : " + nbSolution + " sur les " + nbPosibilite +" testées");
		
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
						s.getEqRoumanie().EnableMaxSizeEquipe();
						
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
		
		System.out.println("Configuration Minimal : ");

		System.out.println(" Nb DEBUTANT : " + configMin[0] +
				" - Nb INTERMEDIARE : " + configMin[1]  +
				" - Nb EXPERIMENTE : " + configMin[2]  +
				" - Nb EXPERT : " + configMin[3] );
		
		System.out.println("Minimum attend, en masse salarial : " + minMasseSalarial + " (max : " + maxMasseSalarial + ")");
		System.out.println("Minimum attend, en temps : " + minTemps + " (max : " + maxTemps + ")");
		System.out.println("Nombre de solution trouvé : " + nbSolution + " sur les " + nbPosibilite +" testées");
		
	}

	
	
	public void essaiModele(){
		Simulation s = new Simulation();
		s.setDEBUG();
		s.initial(0, 0, 1, 2);
		s.getPrRoumanie().setMETHOD1();
		s.getPrRoumanie().setMETHOD2();
		s.getPrRoumanie().setMETHOD3();
		s.getEqRoumanie().EnableMaxSizeEquipe();
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
