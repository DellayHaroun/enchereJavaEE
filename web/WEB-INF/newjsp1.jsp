<%--hello.jsp--%>
<jsp:useBean id="hello" class="entites.HelloBean">
    <jsp:setProperty name="hello" property="prenom" value="harouun" />
    
</jsp:useBean>
<html>
    <head><title>Bonjour</title></head>
    <body>
        <H2>
            bonjour <jsp:getProperty name = "hello" property="nom"/>
            <br> <jsp:getProperty name ="hello" property="prenom"/>
                
        </H2>
    </body>
</html>