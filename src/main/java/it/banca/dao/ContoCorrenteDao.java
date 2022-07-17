package it.banca.dao;

import it.banca.data.ContoCorrente;

public interface ContoCorrenteDao {
	
	public boolean versa(int numeConto, float quantita);
	
	public boolean preleva(int numConto, float quantita);
	
	public boolean esiste(int numConto);
	
	public ContoCorrente getContoCorrente(int numConto);
	

}
