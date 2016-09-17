<%@ page language="java"
		 contentType="text/html; charset=UTF-8"
	     pageEncoding="UTF-8"%>
	     
<%@page import="org.pac4j.core.config.*"%>
<%@ page import="org.pac4j.http.client.indirect.FormClient" %>

<%

FormClient formClient = (FormClient) ConfigSingleton.getConfig()
                                                    .getClients()
                                                    .findClient("FormClient");
String callbackUrl = formClient.getCallbackUrl();

%>
	     
<!DOCTYPE html>

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
	</head>
	
	<body>
		
		<h1>Login</h1>
		
		<form method="POST" action="<%=callbackUrl%>">
		
		  <div>
			  <legend for="username">Username</legend>
			  <input id="username" type="text" name="username"/>
		  </div>
		  
          <div>
              <legend for="password">Password</legend>
              <input id="password" type="password" name="password"/>
          </div>
		
		  <input type="submit" name="submit" value="Login"/>
		
		</form>
		
	</body>

</html>