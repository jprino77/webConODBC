<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<%@ include file="../jsp/bodyHeader.jsp"%>
<div class="page-header">
	<form:form action="/alquiler/bajaModificacionAlquiler" modelAttribute="busquedaForm">

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
							class="form-control datepicker" />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-th"></span>
						</span>
					</div>
				</div>
			</div>
			
			
						<div class="col-md-3">
				<div class="form-group">
					<label for="fechaAlquilerHasta">Fecha Alquiler:</label>

					<div class='input-group date'>
						<form:input path='fechaAlquilerHasta' type='text'
							class="form-control datepicker" />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-th"></span>
						</span>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-offset-9 col-md-3">
				<div class="form-group">
						
		<input class="form-control btn btn-primary" type="submit"
			value="Consultar">
				</div>
			</div>
		</div>
	</form:form>
</div>
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

<c:if test="${fn:length(turnos) > 0}">



	<div class="row">
		<div class="col-md-12">

			<table id="table_id" class="display">
				<thead>
					<tr>
						<th>Cancha</th>
						<th>Tipo de cancha</th>
						<th>Deporte</th>
						<th>Fecha desde</th>
						<th>Fecha hasta</th>
						<th>Seleccionar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${turnos}" var="turno">

						<tr>

							<td align="center"><c:out value="${turno.cancha.codigo}"></c:out></td>
							<td align="center"><c:out
									value="${turno.cancha.tipoCancha.descripcion}"></c:out></td>
							<td align="center"><c:out
									value="${turno.cancha.deporte.descripcion}"></c:out></td>

							<td align="center"><c:out value="${turno.fechaHoraDesde}"></c:out></td>

							<td align="center"><c:out value="${turno.fechaHoraHasta}"></c:out></td>

							<td align="center">

									<div class="row">
									
									<input type="hidden" id="fechaHoraDesde_${turno.id}" name='cancha' value="${turno.fechaHoraDesde}" />
										<input type="hidden" name='cancha' value="${turno.cancha.id}" /> 
										<input type="hidden" id='filial_${turno.id}' value="${turno.cancha.filial.id}" /> 
										<input type="hidden" id='deporte_${turno.id}' value="${turno.cancha.deporte.id}" />
										<input type="hidden" value="${turno.id}" />
										
										<div class="col-md-6">

											<button class="form-control btn btn-primary modificar"
												value="Modificar" id='mod_${turno.id}' turno="${turno.id}"
												data-toggle="modal" data-target="#myModal"
												<c:if test="${!turno.puedeAnular}"> disabled </c:if>>Modificar</button>
										</div>
										<div class="col-md-6">
											<button class="form-control btn btn-danger anular"
												type="submit" value="Anular" id='${turno.id}'
												<c:if test="${!turno.puedeAnular}"> disabled </c:if>>Anular</button>
										</div>
									</div>

							</td>

						</tr>


					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

</c:if>
<%@ include file="../jsp/modalConfirmacion.jsp"%>

<%@ include file="../jsp/bodyFooter.jsp"%>