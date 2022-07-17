package it.banca.service;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

import it.banca.business.ContoCorrenteEjb;
import it.banca.business.ContoCorrenteEjbLocal;
import it.banca.data.ContoCorrente;

/**
 * Servlet implementation class BancaServlet
 */
public class BancaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PARAM_OPERAZIONE = "operazione";
	private final String PARAM_NUMERO = "numero";
	private final String PARAM_INTESTATARIO = "intestatario";
	private final String PARAM_IMPORTO = "importo";
	private ContoCorrenteEjb ContoEjb;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	@EJB
	private ContoCorrenteEjbLocal contoEjb= new ContoCorrenteEjb();
	
	public BancaServlet() {
		super();
	}
	private void errorRedirect(String msg, String destination, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("errorSession", msg);
		try {
			request.getServletContext().getRequestDispatcher(destination).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int operazione = Integer.parseInt(request.getParameter(PARAM_OPERAZIONE));
		if (operazione == 0) {
			int numeroCC = Integer.parseInt(request.getParameter(PARAM_NUMERO));
			float importo = Float.parseFloat(request.getParameter(PARAM_IMPORTO));
			boolean opRiuscita = contoEjb.preleva(numeroCC, importo);

			if (!opRiuscita) {
				response.getWriter().append("Operazione non riuscita, riprova!").append(request.getContextPath());
			} else {
				ContoCorrente CC = contoEjb.getContoCorrente(numeroCC);
				HttpSession session = request.getSession();
				session.setAttribute("operazioneRiuscitaSession", "Operazione riuscita");
				session.setAttribute("prelievoSession", CC);

				request.getServletContext().getRequestDispatcher("/prelievo.jsp").forward(request, response);
			}

		} else if (operazione == 1) {
			int numeroCC = Integer.parseInt(request.getParameter(PARAM_NUMERO));
			float importo = Float.parseFloat(request.getParameter(PARAM_IMPORTO));
			boolean opRiuscita = contoEjb.versa(numeroCC, importo);

			if (!opRiuscita) {
				errorRedirect("Operazione di versamento non riuscita, riprova", "/failedoperazione.jsp", request,
						response);
			} else {
				ContoCorrente CC = contoEjb.getContoCorrente(numeroCC);
				HttpSession session = request.getSession();
				session.setAttribute("versamentoSession", CC);

				request.getServletContext().getRequestDispatcher("/versamento.jsp").forward(request, response);
			}
		}

	}

}
