<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<%@ include file="../jsp/bodyHeader.jsp"%>
<div class="page-header">
	<h1>Baja Modificaion Alquiler</h1>
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

								<div class="form-group">
									<input type="hidden" value="${turno.cancha.id}" /> <input
										type="hidden" value="${turno.cancha.filial.id}" /> <input
										type="hidden" value="${turno.cancha.deporte.id}" /> <input
										type="hidden" value="${turno.id}" />
									<c:if test="${turno.puedeAnular}">
										<button class="form-control btn btn-danger anular"
											type="submit" value="Anular" id='${turno.id}'>Anular</button>
									</c:if>
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