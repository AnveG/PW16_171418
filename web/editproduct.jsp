<%-- 
    Document   : editproduct
    Created on : Nov 22, 2012, 3:41:18 PM
    Author     : Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="product" scope="request" class="db.Product"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modifica prodotto</title>
    </head>
    <body>
        <h1>Modifica prodotto</h1>
        
        <form method="POST" action="<c:url value="/EditProduct"/>">
            <input type="hidden" name="prodid" value="${product.id}"/>
            <table>
                <tr>
                    <td><label for="prodname">Nome: </label></td>
                    <%-- uso <c:out> perché nel nome ci posso mettere di tutto e il comando esegue l'escaping HTML dei caratteri --%>
                    <td><input id="prodname" type="text" name="prodname" value="<c:out value="${product.name}"/>" size="100"></td>
                </tr>
                <tr>
                    <td>
                        <label for="prodprice">Prezzo: </label>
                    </td>
                    <td>
                        <input id="prodprice" type="text" name="prodprice" value="${product.price}" size="6">
                    </td>
                    <td>
                        <span style="color: red"><c:out value="${priceerror}"/></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Salva">
                        <a href="<c:url value="/LoadProducts"/>">Annulla</a>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
