<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
	<div class="col-md-12">
		<div class="form-group">
			<form:label path="filial">Filial</form:label>
			<form:select class="form-control" path="filial" id="filial">
				<form:option value="0" label="--Seleccionar--" />
				<form:options items="${filialMap}" />
			</form:select>
		</div>
		<div class="form-group">
			<label for="deporte">Deporte:</label>

			<form:select class="form-control" path="deporte" id="deporte"
				disabled="true">
				<form:option value="0" label="--Seleccionar--" />
				<form:options items="${deporteMap}" />
			</form:select>
		</div>

		<div class="form-group">
			<div class='input-group date'>
				<form:input path='fechaAlquiler' type='text' class="form-control datepicker" /> <span
					class="input-group-addon"> <span
					class="glyphicon glyphicon-th"></span>
				</span>
			</div>
		</div>
		<div class="form-group">
			<div class='input-group date'>
				<form:input path='horaInicio' type='text' class="form-control datetimepicker" /> <span
					class="input-group-addon"> <span
					class="glyphicon glyphicon-time"></span>
				</span>
			</div>
		</div>
		<div class="form-group">
			<input class="form-control btn btn-primary" type="submit"
				value="Consultar">
		</div>
	</div>

</div>
