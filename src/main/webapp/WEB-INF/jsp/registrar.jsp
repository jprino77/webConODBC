<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Club los amigos</title>
</head>

<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/registrar.css" rel="stylesheet">
<link
	href="http://localhost:8080/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">

<body>
	<div class="container wrapper">

		<form:form action="/registrar" modelAttribute="usuario" method="POST"
			class="form-content" autocomplete="off">
			<div class="row">
				<c:if test="${not empty msg}">
					<div class="alert alert-info">
						<strong>${msg}</strong>
					</div>
				</c:if>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<form:label path="nombre">Nombre:</form:label>
						<form:input path='nombre' type='text' class="form-control"
							cssErrorClass="form-control error" maxlength="45" />
						<form:errors path="nombre" cssClass="error" />

					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<form:label path="apellido">Apellido:</form:label>
						<form:input path='apellido' type='text' class="form-control"
							cssErrorClass="form-control error" maxlength="45" />
						<form:errors path="apellido" cssClass="error" />

					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<form:label path="fechaNAcimiento" for="fechaNAcimiento">Fecha Nacimiento:</form:label>

						<div class='input-group date'>
							<form:input id="fechaNAcimiento datepicker"
								path='fechaNAcimiento' type='text'
								class="form-control datepicker" onkeydown="return false"
								cssErrorClass="form-control datepicker error" />

							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-th"></span>
							</span>
						</div>
						<form:errors path="fechaNAcimiento" cssClass="error" />
					</div>
				</div>

			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<form:label path='calle' for="calle">calle:</form:label>
						<form:input path='calle' type='text' class="form-control"
							maxlength="100" cssErrorClass="form-control error" />
						<form:errors path="calle" cssClass="error" />
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<form:label path='altura' for="altura">Altura:</form:label>
						<form:input path='altura' type="number" class="form-control"
							cssErrorClass="form-control error" />
						<form:errors path="altura" cssClass="error" />

					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<form:label path='localidad' for="localidad">Localidad:</form:label>
						<form:select class="form-control" path="localidad" id="localidad"
							cssErrorClass="form-control error">
							<form:option value="" label="--Seleccionar--" />
							<form:options items="${localidadMap}" />
						</form:select>
						<form:errors path="localidad" cssClass="error" />
					</div>

				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<form:label path='telefono' for="altura">Telefono:</form:label>
						<form:input path='telefono' type='tel' class="form-control"
							cssErrorClass="form-control error" maxlength="15" />
						<form:errors path="telefono" cssClass="error" />

					</div>
				</div>

				<div class="col-md-6">
					<div class="form-group">
						<form:label path='email' for="altura">Email:</form:label>
						<form:input path='email' type='email' class="form-control"
							cssErrorClass="form-control error" maxlength="100" />
						<form:errors path="email" cssClass="error" />
					</div>

				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<form:label path='usuario' for="altura">Usuario:</form:label>
						<form:input path='usuario' type='text' class="form-control"
							maxlength="45" cssErrorClass="form-control error" />
						<form:errors path="usuario" cssClass="error" />

					</div>
				</div>

				<div class="col-md-6">
					<div class="form-group">
						<form:label path='clave' for="clave">Clave:</form:label>
						<form:input path='clave' type='password' class="form-control"
							maxlength="45" cssErrorClass="form-control error" />
						<form:errors path="clave" cssClass="error" />

					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<form:button class="form-control btn btn-primary" id="registrar"
							type="submit" value="Registrar">Registrar</form:button>
					</div>
				</div>
			</div>



		</form:form>

	</div>


</body>

<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://momentjs.com/downloads/moment-with-locales.min.js"></script>

<script
	src="http://localhost:8080/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script src="http://localhost:8080/js/registrar.js"></script>
</html>