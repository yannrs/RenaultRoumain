import Divers.Pays;
import Divers.Sexe;
import Divers.SituationFamilial;


public class Personne {
	private int age; // en mois
	private Sexe sexe;
	private SituationFamilial famille;
	private Pays origine;
	

	/****************************/
	/*  	  CONSTRUCTEUR  	*/
	/****************************/
	public Personne() {
		this.age = 20*12;
		this.sexe = (Math.random() > 0.5 ? Sexe.HOMME : Sexe.FEMME);
		this.famille = SituationFamilial.NONE;
		this.origine = Pays.NONE;
	}
	
	public Personne(int age) {
		this.age = age;
		this.sexe = (Math.random() > 0.5 ? Sexe.HOMME : Sexe.FEMME);
		this.famille = SituationFamilial.NONE;
		this.origine = Pays.NONE;
	}
	
	public Personne(Pays origine) {
		this.age = 20*12;
		this.sexe = (Math.random() > 0.5 ? Sexe.HOMME : Sexe.FEMME);
		this.famille = SituationFamilial.NONE;
		this.origine = origine;
	}
	
	public Personne(int age, Sexe sexe) {
		this.age = age;
		this.sexe = sexe;
		this.famille = SituationFamilial.NONE;
		this.origine = Pays.NONE;
	}
	
	public Personne(int age, Sexe sexe, SituationFamilial famille) {
		this.age = age;
		this.sexe = sexe;
		this.famille = famille;
		this.origine = Pays.NONE;
	}
	
	public Personne(int age, Sexe sexe, SituationFamilial famille, Pays origine) {
		this.age = age;
		this.sexe = sexe;
		this.famille = famille;
		this.origine = origine;
	}

	/****************************/
	/*  	GETTER & SETTER  	*/
	/****************************/
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
	
	/****************************/
	/*  		METHODES  		*/
	/****************************/
	public void inccAGEMois(){
		setAge(getAge()+1);
	}
	
}
