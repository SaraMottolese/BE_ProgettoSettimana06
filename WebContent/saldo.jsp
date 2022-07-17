<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="it.banca.data.ContoCorrente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	ContoCorrente conto = (ContoCorrente) session.getAttribute("conto");
	%>
<div class="container text-center">
      <div class="operazioni">
          <div class="d-block">
            <form action="operazione" method="get">
              <fieldset>
                <h2>Saldo</h2>
                <label class="form-label" for="numero">Numero CC:</label>
                  <input class="form-control" name="numero"> <br/> 
              </fieldset>
             <h1><%=conto.getSaldo() %></h1>
            </form>
          </div>
        </div>
    </div>

</body>
</html>