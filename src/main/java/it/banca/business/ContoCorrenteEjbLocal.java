package it.banca.business;

import it.banca.data.ContoCorrente;
import jakarta.ejb.Local;

@Local
public interface ContoCorrenteEjbLocal {
	
	public ContoCorrente getContoCorrente(int numConto);
	
	public boolean controllaOperazione(String operazione,Float quantita,int pin,int numConto);
	
	public ContoCorrente accedi(int numConto, int pin);
	
	public boolean versa(int numConto, float quantita);
	
	public boolean preleva(int numConto, float saldo);
	
	public boolean esiste(int numConto);

}
