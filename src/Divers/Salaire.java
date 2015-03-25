package Divers;

/**
 * Salaire type d'un employé de Renoult
 * @author yann
 *
 */
public enum Salaire {
	ROUMAIN(25),
	FRANCAIS(100),
	MISSIONAIRE(150), // not used
	EXPATRIE(200);
	
	private int sal = 0;
	
	Salaire(int i){
		this.sal = i;
	}
	
	public int getMontant(){
		return this.sal;
	}
	
	public int getMontant(int temps, float taux){
		return (int) (this.sal/(Math.pow((1+taux), temps/(double)12)));
	}
	
	public String toString(){
		return "" + this.sal;
	}
		
}
