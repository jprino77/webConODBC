<%@ include file="../jsp/bodyHeader.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="page-header">
	<form:form class="form-inline" action="/alquiler/buscarCanchas"
		modelAttribute="busquedaForm">

		<%@ include file="../jsp/filtroDeBusqueda.jsp"%>
	</form:form>

</div>
<c:if test="${fn:length(canchas) > 0}">
	<div class="row">
		<div class="col-md-12">
			<div class="msg">
				
			</div>

		</div>
	</div>
	<div class="row">
		<div class="col-md-12">

			<table id="table_id" class="display">
				<thead>
					<tr>
						<th>Cancha</th>
						<th>Tipo de cancha</th>
						<th>Seleccionar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${canchas}" var="cancha">

						<tr>

							<td align="center"><c:out value="${cancha.codigo}"></c:out></td>
							<td align="center"><c:out value="${cancha.tipoCancha.descripcion}"></c:out></td>
							<td align="center">

								<div class="form-group">
									<form:form id='form_${cancha.id}' class="form-inline"
										action="/alquiler/alquilar" modelAttribute="turno"
										method="POST">
										<form:input path="fechaHoraDesde" type="hidden"
											value="${busquedaForm.fechaAlquiler} ${busquedaForm.horaInicio}" />
										<form:input path="fechaHoraHasta" type="hidden"
											value="${busquedaForm.fechaAlquiler} ${busquedaForm.horaInicio}" />
										<form:input path="cancha.id" type="hidden" value="${cancha.id}" />

										<form:button class="form-control btn btn-primary alquilar"
											type="submit" value="Alquilar" id='${cancha.id}'>Alquilar</form:button>
									</form:form>
								</div>

							</td>

						</tr>


					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

</c:if>
<%@ include file="../jsp/bodyFooter.jsp"%>