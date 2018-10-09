<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Club los amigos</title>
</head>

<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<link href="css/login.css" rel="stylesheet">


<body>

	<div class="wrapper">
		<div id="formContent">
			<!-- Tabs Titles -->
			<!-- Icon -->
			<div class="first">
				<h1>Club Los Amigos</h1>
			</div>
			<div>
				<c:if test="${not empty error}">
					<div class="alert alert-danger">
						<strong>${error}</strong>
					</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="alert alert-info">
						<strong>${msg}</strong>
					</div>
				</c:if>
			</div>
			<!-- Login Form -->
			<form name='loginForm'
				action="<c:url value='j_spring_security_check' />" method='POST'>

				<input type="text" id="login" class="second" name='usuario'
					placeholder="usuario" /> 
				<input type="password" id="password"
					class="third" name='clave' placeholder="clave" /> 
				<input
					type="submit" class="fourth" value="Ingresar" />
				<a href="/registrar">Crear Cuenta </a>
				<input
					type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>


		</div>
	</div>

</body>

<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</html>