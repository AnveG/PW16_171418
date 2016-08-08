<%-- 
    Document   : home
    Created on : Oct 26, 2012, 3:39:33 PM
    Author     : Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*,db.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista prodotti</title>
    </head>
    <body>
        <%-- 
${user} prende il valore dagli attributi di Request,Session e Application in questo ordine         
        --%>
        <h1>Benvenuto, ${user.fullname} !</h1>
        <h2>Lista prodotti</h2>
        <p><a href="<c:url value="/EditProduct"/>">Nuovo prodotto</a></p>
        <table >
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Prezzo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        
                        <td><c:out value="${p.name}"/></td>
                        <td><c:out value="${p.price}"/></td>
                        <td>
                            <a href="<c:url value="/EditProduct?prodid=${p.id}"/>">Modifica</a>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
        

        <span><a href="<c:url value="/Logout"/>">Logout</a></span> 
    </body>
</html>
