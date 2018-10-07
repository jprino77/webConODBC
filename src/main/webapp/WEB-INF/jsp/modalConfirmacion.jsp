<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="modal fade" id="myModal" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
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
				<form:form id="buscarcanchasMod" modelAttribute="busquedaForm">
					<form:input id="filialHidden" path='filial' type='hidden' />
					<form:input id="deporteHidden" path='deporte' type='hidden' />
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="fechaAlquilerMod">Fecha Alquiler:</label>

								<div class='input-group date'>
									<form:input id="fechaAlquilerMod" path='fechaAlquiler' type='text'
										class="form-control datepicker" />
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-th"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="horaInicio">Hora inicio:</label>
								<div class='input-group date'>
									<form:input path='horaInicio' type='text'
										class="form-control datetimepicker" />
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-time"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="horaFin">Hora fin:</label>
								<div class='input-group date'>
									<form:input path='horaFin' type='text'
										class="form-control datetimepicker" />
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
								<input class="form-control btn btn-primary" id="consultarMod" type="button"
									value="Consultar">
							</div>
						</div>
					</div>
				</form:form>

			</div>
			<div class="modal-body">
				<div id="resultado">
				
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>