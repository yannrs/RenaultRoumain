import Divers.StatutEntreprise;


public class Optimisation {
	
	
	public void optimisationSalaire(){
		Simulation s = new Simulation();
		
		int minDEBUTANT = 0;
		int minINTERMEDIAIRE = 0;
		int minEXPERIMENTE = 0;
		int minEXPERT = 0;
		
		int maxDEBUTANT = 5;
		int maxINTERMEDIAIRE = 2;
		int maxEXPERIMENTE = 2;
		int maxEXPERT = 7;
		
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
		
		System.out.println("Minimum attend, en masse salarial : " + minMasseSalarial + " (max : " + maxMasseSalarial + ")");
		System.out.println("Nombre de solution trouvé : " + nbSolution + " sur les " + nbPosibilite +" testées");
		
	}
	
	
	public void essaiModele(){
		Simulation s = new Simulation();
		s.setDEBUG();
		s.initial(0, 0, 0, 5);
		s.getPrRoumanie().setMETHOD1();
		s.getPrRoumanie().setMETHOD2();
		s.getPrRoumanie().setMETHOD3();
		s.getEqRoumanie().EnableMaxSizeEquipe();
//		s.getEqRoumanie().DisableMaxSizeEquipe();	
		s.Simu();
	}
	
	
	public static void main(String[] args) {
//		(new Optimisation()).essaiModele();
		(new Optimisation()).optimisationSalaire();
	}
}
