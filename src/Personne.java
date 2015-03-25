import Divers.EtatPsychologique;
import Divers.Pays;
import Divers.Sexe;
import Divers.SituationFamilial;

/**
 * Class regroupement les caractéristiques pouvant influencé la carrière d'une personne
 * Age ; Sexe ; Situation familial ; Pays d'origine Origine 
 * @author Yann RAVEL-SIBILLOT
 *
 */
public class Personne {
	private int age; // en mois
	private Sexe sexe;
	private SituationFamilial famille;
	private Pays origine;

	// TurnOver
	private int tempsInactif;
	private EtatPsychologique etat;

	/****************************/
	/*  	  CONSTRUCTEUR  	*/
	/****************************/
	public Personne() {
		this(20*12 , (Math.random() > 0.5 ? Sexe.HOMME : Sexe.FEMME), SituationFamilial.NONE, Pays.NONE);
	}

	public Personne(int age) {
		this(age , (Math.random() > 0.5 ? Sexe.HOMME : Sexe.FEMME), SituationFamilial.NONE, Pays.NONE);
	}

	public Personne(Pays origine) {
		this(20*12 , (Math.random() > 0.5 ? Sexe.HOMME : Sexe.FEMME), SituationFamilial.NONE, origine);
	}

	public Personne(int age, Sexe sexe) {
		this(age, sexe, SituationFamilial.NONE);
	}

	public Personne(int age, Sexe sexe, SituationFamilial famille) {
		this(age, sexe, famille, Pays.NONE);
	}

	public Personne(int age, Sexe sexe, SituationFamilial famille, Pays origine) {
		this.age = age;
		this.sexe = sexe;
		this.famille = famille;
		this.origine = origine;
		this.tempsInactif = 0;
		this.etat = (Math.random() > 0.9 ? EtatPsychologique.Suicidaire : EtatPsychologique.Deprime);
	}

	
	/****************************/
	/*  	GETTER & SETTER  	*/
	/****************************/
	/**
	 * Obtenu en année
	 * @return
	 */
	public int getAge() {
		return (int) age/12;
	}	
	private void setAge(int age) {
		this.age = age;
	}

	public Sexe getSexe() {
		return sexe;
	}
	private void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	public SituationFamilial getFamille() {
		return famille;
	}
	private void setFamille(SituationFamilial famille) {
		this.famille = famille;
	}

	public Pays getOrigine() {
		return origine;
	}
	private void setOrigine(Pays origine) {
		this.origine = origine;
	}

	public EtatPsychologique getEtat() {
		return etat;
	}
	public void setEtat(EtatPsychologique etat) {
		this.etat = etat;
	}
	
	public int getTempsInactif() {
		return tempsInactif;
	}
	public void inccTempsInactif() {
		tempsInactif++;
	}
	
	public void setTempsInactif(int tempsInactif) {
		this.tempsInactif = tempsInactif;
	}


	/****************************/
	/*  		METHODES  		*/
	/****************************/
	public void inccAGEMois(){
		setAge(getAge()+1);
	}

	/**
	 * Estime la probabilité de quitter l'entreprise pour une personne donnée
	 * @param environnement // 
	 * @return
	 */
	public float etatPsycho(double environnement){	
		double entropie = Math.random();
		double etat = environnement*this.getEtat().getStat() ; // 0 équilibré -> 1 Suicidaire

		// Changement d'état potentiel
		if(etat > 0.75 ) inccEtat();
		if(etat < 0.15 ) deccEtat();
		
		return (float) (etat*entropie);
	}

	
	public void inccEtat(){
		switch(this.getEtat()){
		case Sain:
			this.setEtat(EtatPsychologique.Normal);
		case Normal:
			this.setEtat(EtatPsychologique.Deprime);
		case Deprime:
			this.setEtat(EtatPsychologique.Suicidaire);
		case Suicidaire:
			this.setEtat(EtatPsychologique.Suicidaire);
		default:
			this.setEtat(EtatPsychologique.Normal);
		}
	}
	
	public void deccEtat(){
		switch(this.getEtat()){
		case Sain:
			this.setEtat(EtatPsychologique.Sain);
		case Normal:
			this.setEtat(EtatPsychologique.Sain);
		case Deprime:
			this.setEtat(EtatPsychologique.Normal);
		case Suicidaire:
			this.setEtat(EtatPsychologique.Deprime);
		default:
			this.setEtat(EtatPsychologique.Normal);
		}
	}
}
