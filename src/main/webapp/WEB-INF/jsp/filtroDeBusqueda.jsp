<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
	<div class="col-md-12">
		<form:form class="form-inline" action="/alquiler/altaAlquiler" modelAttribute="busquedaForm">
			<div class="form-group">
			<form:label path="filial">Filial</form:label>
            <form:select class="form-control" path="filial">
                <form:option value="-" label="--Seleccionar--"/>
                <form:options items="${filialMap}" />
            </form:select>
			</div>
			<div class="form-group">
				<label for="sel1">Email:</label> <select class="form-control"
					id="sel1">
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
				</select>
			</div>

			<button class="btn btn-default">Submit</button>
		</form:form>
	</div>

</div>
