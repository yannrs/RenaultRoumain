package Divers;

public enum EtatPsychologique {
	Sain(0.1),
	Normal(0.20),
	Deprime(0.75),	
	Suicidaire(0.99);
	
	private double sal = 0;
	
	EtatPsychologique(double i){
		this.sal = i;
	}
	
	public double getStat(){
		return this.sal;
	}
	
	public String toString(){
		return "" + this.sal;
	}
}
