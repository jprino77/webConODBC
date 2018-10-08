<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jsp/bodyHeader.jsp"%>

<div class="page-header">
	<div class="row">
		<div class="col-md-12">
			<div class="msg ajaxMsg"></div>
		</div>
		<div class="col-md-12">
			<c:if test="${not empty msg}">
				<div class=" alert alert-info">
					<strong>${msg}</strong>
				</div>
			</c:if>
		</div>
	</div>
	<form:form action="/alquiler/buscarCanchas" modelAttribute="busquedaForm" autocomplete="off">

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<form:label path="filial">Filial</form:label>
					<form:select class="form-control" path="filial" id="filial">
						<form:option value="0" label="--Seleccionar--" />
						<form:options items="${filialMap}" />
					</form:select>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="deporte">Deporte:</label>

					<form:select class="form-control" path="deporte" id="deporte"
						disabled="true">
						<form:option value="0" label="--Seleccionar--" />
						<form:options items="${deporteMap}" />
					</form:select>
				</div>
			</div>

		</div>

		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label for="fechaAlquiler">Fecha Alquiler:</label>

					<div class='input-group date'>
						<form:input path='fechaAlquiler' type='text'
							class="form-control datepicker" onkeydown="return false" />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-th"></span>
						</span>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label for="horaInicio">Hora inicio:</label>
					<div class='input-group date'>
						<form:input path='horaInicio' type='text'
							class="form-control datetimepicker" onkeydown="return false" />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-time"></span>
						</span>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label for="horaFin">Hora fin:</label>
					<div class='input-group date'>
						<form:input path='horaFin' type='text'
							class="form-control datetimepicker" onkeydown="return false"/>
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-time"></span>
						</span>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-offset-9 col-md-3">
				<div class="form-group">
						
		<input id="consultarAlquiler" class="form-control btn btn-primary" type="submit" disabled
			value="Consultar">
				</div>
			</div>
		</div>
	</form:form>

</div>
<%@ include file="../jsp/datatableAlquiler.jsp"%>
<%@ include file="../jsp/bodyFooter.jsp"%>