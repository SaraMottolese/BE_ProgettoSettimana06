package it.banca.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.LoggerFactory;

import it.banca.dao.ContoCorrenteDao;
import it.banca.dao.ContoCorrenteDaoImpl;
import it.banca.data.ContoCorrente;
import it.banca.service.BancaServlet;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import it.banca.service.ConnectionFactory;

/**
 * La classe ContoCorrenteEjb richiama i metodi del Dao come richiesto dalla traccia
 * In alcuni casi ho voluto utilizzare il linguaggio SQL per continuare a fare pratica
 */
@Stateless
@LocalBean
public class ContoCorrenteEjb implements ContoCorrenteEjbLocal, ContoCorrenteEjbRemote {

	@PersistenceContext(unitName = "ContiPU")
	private EntityManager em;
	private static final String GET_CONTO = "select * from contocorrente where numero=?";
	private static final String LOGIN = "select * from contocorrente where numero=? and pin=?;";
	private ContoCorrenteDao contoDao=new ContoCorrenteDaoImpl();
	private static org.slf4j.Logger Lg = LoggerFactory.getLogger(BancaServlet.class);

	/**
	 * Default constructor.
	 */
	public ContoCorrenteEjb() {

	}

	@Override
	public ContoCorrente getContoCorrente(int numConto) {
		return contoDao.getContoCorrente(numConto);
	}

	@Override
	public boolean controllaOperazione(String operazione,Float quantita,int pin,int numConto) {
		ContoCorrente conto=contoDao.getContoCorrente(numConto);
		if(!contoDao.esiste(numConto)) {
			return false;
		}else if(quantita<0) {
			return false;
		}else if(!(operazione.equals("saldo")) && !(operazione.equals("prelievo")) && !(operazione.equals("versamento"))) {
			return false;
		}else if(quantita>conto.getSaldo()) {
			return false;
		}
		
		return true;
	}

	@Override
	public ContoCorrente accedi(int numConto, int pin) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(LOGIN);
			ps.setInt(1, numConto);
			ps.setInt(2, pin);
			rs = ps.executeQuery();
			if(rs.next())
				return getContoCorrente(numConto);
				
		} catch (Exception e) {
			Lg.error("Questo numero di conto corrente non esiste");
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (Exception e) {
		}
		
		try {
			ps.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}

		return null;

	}

	public boolean versa(int numConto, float quantita) {
		return contoDao.versa(numConto, quantita);
		}

	public boolean preleva(int numConto, float saldo) {
		return contoDao.preleva(numConto, saldo);
	}

	public boolean esiste(int numConto) {
		return contoDao.esiste(numConto);
	}
}

