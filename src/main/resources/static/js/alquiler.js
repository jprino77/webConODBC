$(function() {

	//La funcion se encuentra en functionsHelper.js
	customDatePicker.init();
	$('#table_id').DataTable();
	$('#tableCanchas').DataTable();
	habilitarConsulta();


	if ($("#deporte").val() != "0") {
		$("#deporte").removeAttr("disabled");
	} else {
		$("#deporte").attr("disabled", "disabled")
	}
	
	if ($("#filial").val() != "0") {
		
		var data = {
				filialId : $("#filial").val()
			};
		ajaxCalls.getDiasDisabledFilial(data,"fechaAlquiler");
	}else{
		$("#fechaAlquiler").attr("disabled", "disabled");
	}
	
	if($("#fechaAlquiler").val()==""){
		
		$("#horaInicio").val("");
		$("#horaFin").val("");
		$("#horaInicio").attr("disabled","disabled");
		$("#horaFin").attr("disabled","disabled");
		
	}else{
		var data = {
				filialId : $("#filial").val(),
				fechaAlquiler : this.value
			};
		
		ajaxCalls.getHorasFilialDia(data);
		
		$("#horaInicio").removeAttr("disabled");
		$("#horaFin").removeAttr("disabled");
	}
	
	$("#filial").change(function() {
		if (this.value != "0") {
			var data = {
				filialId : this.value
			};
			$("#fechaAlquiler").removeAttr("disabled");
			//La funcion se encuentra en functionsHelper.js
			ajaxCalls.getDeportesFilial(data);
			ajaxCalls.getDiasDisabledFilial(data,"fechaAlquiler");
		} else {
			$("#deporte").attr("disabled", "disabled");
			$("#fechaAlquiler").attr("disabled", "disabled");
			$("#horaInicio").attr("disabled", "disabled");
			$("#horaFin").attr("disabled", "disabled");
			
			$("#fechaAlquiler").val("");
			$("#horaInicio").val("");
			$("#horaFin").val("");

			$("#deporte").val("0");
			$("#deporte").find('option').not(':first').remove();

		}
		
		habilitarConsulta();

	})
	
	$("#deporte").change(function() {
		habilitarConsulta();
	});
	
	$("#fechaAlquiler").blur(function(){
		
		
		if(this.value==""){
			
			$("#horaInicio").val("");
			$("#horaFin").val("");
			$("#horaInicio").attr("disabled","disabled");
			$("#horaFin").attr("disabled","disabled");
		
		}else{
			var data = {
					filialId : $("#filial").val(),
					fechaAlquiler : this.value
				};
			
			ajaxCalls.getHorasFilialDia(data);
			
			$("#horaInicio").removeAttr("disabled");
			$("#horaFin").removeAttr("disabled");
		}
		
		habilitarConsulta();

	});
	
	$("#horaInicio").blur(function(){
		
		if(this.value==""){
			$("#horaFin").val("");
			$("#horaFin").attr("disabled","disabled");
		}else{
			$("#horaFin").removeAttr("disabled");
		}
		habilitarConsulta();
	});
	
	$("#horaFin").blur(function(){
			
			habilitarConsulta();
	});
	
	
    $("#horaInicio").on("dp.change", function (e) {
        $('#horaFin').data("DateTimePicker").minDate(e.date);
        habilitarConsulta();
    });
    
    $("#horaFin").on("dp.change", function (e) {
        $('#horaInicio').data("DateTimePicker").maxDate(e.date);
        habilitarConsulta();
    });

	$('button[type=submit].alquilar').click(function(e) {

		//Prevent default submission of form
		e.preventDefault();
		var callButtonId = this.id;
		var formId = "form_" + this.id;
		//La funcion se encuentra en functionsHelper.js
		ajaxCalls.alquilar(callButtonId, formId)
	});

	$('button[type=submit].anular').click(function(e) {

		e.preventDefault();
		var turnoId = this.id;
		//La funcion se encuentra en functionsHelper.js
		ajaxCalls.anular(turnoId);
	});

	$("button.modificar").click(function(e) {
		e.preventDefault();
		var filial = $('#filial_' + this.getAttribute("turno")).val();
		var deporte = $('#deporte_' + this.getAttribute("turno")).val();
		$('#fechaAlquilerMod').data("DateTimePicker").format("YYYY-MM-DD").date($('#fechaHoraDesde_' + this.getAttribute("turno")).val()).format("DD/MM/YYYY");
		$("#filialHidden").val(filial);
		$("#deporteHidden").val(deporte);

	});

	$("#consultarMod").click(function(e) {

		e.preventDefault();
		$.post("/alquiler/buscarCanchasMod", 
				
				$("#buscarcanchasMod").serialize(), function(data) {
					$("#resultado").html(data);
					$('#tableCanchas').DataTable();

				});
	});

	$("#myModal").on("hidden.bs.modal", function() {
		$("#resultado").html("");
	});

	function habilitarConsulta(){
		
		if($("#deporte").val() != "0" && $("#filial").val() != "0" && $("#fechaAlquiler").val() !="" && $("#horaInicio").val() != "" && $("#horaFin").val() != ""){
			
			$("#consultarAlquiler").removeAttr("disabled");
		}else{
			$("#consultarAlquiler").attr("disabled","disabled");
		}
	}
});
