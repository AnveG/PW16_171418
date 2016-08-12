<%-- 
    Document   : index
    Created on : Oct 26, 2012, 3:37:53 PM
    Author     : Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Esempio servlet di login</h1>
        
        <form action="Login" method="POST">
            <div>
                <label for="input_username">Username:</label> <input id="input_username" type="text" name="nickname" value="" size="20" />
            </div>
            <div>
                <label for="input_password">Password:</label> <input id="input_password" type="password" name="password" value="" size="20" />
            </div>
            <div>
                <div style="color: #FF0000">${message}</div>
                <input type="submit" value="Login" />
                <input type="reset" value="Reset" />
            </div>
        </form>
    </body>
</html>
