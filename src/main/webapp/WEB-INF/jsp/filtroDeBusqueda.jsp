<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
	<div class="col-md-12">
		<form:form class="form-inline" action="/alquiler/altaAlquiler" modelAttribute="busquedaForm">
			<div class="form-group">
			<form:label path="filial">Filial</form:label>
            <form:select class="form-control" path="filial" id="filial">
                <form:option value="0" label="--Seleccionar--"/>
                <form:options items="${filialMap}" />
            </form:select>
			</div>
			<div class="form-group">
				<label for="deporte">Deporte:</label> 

            <form:select class="form-control" path="deporte" id="deporte" disabled="true">
                <form:option value="0" label="--Seleccionar--"/>
                <form:options items="${deporteMap}" />
            </form:select>
			</div>

			<button class="btn btn-default">Submit</button>
		</form:form>
	</div>

</div>
