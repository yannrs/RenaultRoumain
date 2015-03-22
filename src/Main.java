import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Divers.Pays;
import Divers.StatutEntreprise;


public class Main {
	private Equipe eqFrance; // Not used first
	private Equipe eqRoumanie;
	private ResAndDev prFrance; // Not used first
	private ResAndDev prRoumanie;
	private int temps;
	
	public void initial(){		
		// -------- Creation de l'équipe
//		eqFrance = new Equipe(Pays.FRANCE);
		eqRoumanie = new Equipe(Pays.ROUMANIE);
		
		// Ajout des membres - Salariés
		// Expartiés - Expert
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		eqRoumanie.addMember(new Salarie(Pays.FRANCE, StatutEntreprise.EXPERT));
		
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

	
	
	
	public void unMois(){
		
		// Création de nouveau projet si possible
		prRoumanie.oneMonth();
		
		// Designer des tuteurs si possible
		eqRoumanie.repartirTuteur();
				
		// Augmenter l'experience si possible
		// + Faire augmenter le statut de toutes personnes étant capable de progresser.
		eqRoumanie.moisSuivant();
		
		// Calcul de la masse salarial
		System.out.println("Masse salarial à l'instant " + temps + " est de : " + eqRoumanie.salaireMois());
		
		// Sauvegarde des paramètres importantes en fin de mois
		saveEquipe(eqRoumanie, temps);
		saveProjet(prRoumanie, temps);
	}
	
	
	public void Simu(){
		temps = 0;
		while(temps < 80 && eqRoumanie.nbParStatut(StatutEntreprise.EXPERIMENTE) <= 30){
			temps ++;
			unMois();
		}
		System.out.println("[SIMU] Etat Final : ");
		System.out.println(eqRoumanie.toString());
	}
	
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
	
	
	public static void main(String[] args) {
		Main m = new Main();
		
		m.initial();
		m.Simu();
		
		
	}
	
	
}
