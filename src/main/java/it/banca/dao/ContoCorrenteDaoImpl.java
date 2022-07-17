package it.banca.dao;

import it.banca.data.ContoCorrente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/*
 * Logica che permette di controllare se i lconto esiste o no e
 * di compiere le operazioni di:
 * 		richiesta saldo
 * 		versamento
 * 		prelievo
 */
public class ContoCorrenteDaoImpl implements ContoCorrenteDao {

	@PersistenceContext(unitName = "ContiPU")
	private EntityManager em;
	
	@Override
	public boolean versa(int numConto, float quantita) {
		if (esiste(numConto)) {
			ContoCorrente cc = getContoCorrente(numConto);
			cc.setSaldo(cc.getSaldo() + quantita);
			em.merge(cc);
			return true;
		}
		return false;
	}

	@Override
	public boolean preleva(int numConto, float quantita) {
		if (esiste(numConto)) {
			ContoCorrente cc = getContoCorrente(numConto);
			if (cc.getSaldo() > quantita) {
				cc.setSaldo(cc.getSaldo() - quantita);
				em.merge(cc);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean esiste(int numConto) {
		ContoCorrente conto = getContoCorrente(numConto);
		if (conto != null) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public ContoCorrente getContoCorrente(int numConto) {
		return em.find(ContoCorrente.class, numConto);
	}

}
