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
		if($("#fechaAlquiler").length){
			ajaxCalls.getDiasDisabledFilial(data,"fechaAlquiler");
		}
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
		if($("#fechaAlquiler").length){
			ajaxCalls.getHorasFilialDia(data);
		}
		
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
			if($("#fechaAlquiler").length){
				ajaxCalls.getDiasDisabledFilial(data,"fechaAlquiler");
			}
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
	
	
	$("#fechaAlquilerMod").blur(function(){
			
			
			if(this.value==""){
				
				$("#horaInicioMod").val("");
				$("#horaFinMod").val("");
				$("#horaInicioMod").attr("disabled","disabled");
				$("#horaFinMod").attr("disabled","disabled");
			
			}else{
				var data = {
						filialId : $("#filialHidden").val(),
						fechaAlquiler : this.value
					};
				
				var filial = {
						filialId : $("#filialHidden").val()
					};
				ajaxCalls.getDiasDisabledFilial(filial,"fechaAlquilerMod");
				ajaxCalls.getHorasFilialDia(data);
				
				$("#horaInicioMod").removeAttr("disabled");
				$("#horaFinMod").removeAttr("disabled");
			}
			
	
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
    
    
    $("#horaInicioMod").on("dp.change", function (e) {
        $('#horaFinMod').data("DateTimePicker").minDate(e.date);
    });
    
    $("#horaFinMod").on("dp.change", function (e) {
        $('#horaInicioMod').data("DateTimePicker").maxDate(e.date);
    });

	$('button[type=submit].alquilar').click(function(e) {

		//Prevent default submission of form
		e.preventDefault();
		var callButtonId = this.id;
		var formId = "form_" + this.id;
		//La funcion se encuentra en functionsHelper.js
		ajaxCalls.alquilar(callButtonId, formId)
	});

	//llamo funcion que genera el ajax para anular el turno
	$('button[type=submit].anular').on('click', function(e) {

		e.preventDefault();
		var turnoId = this.id;
		//La funcion se encuentra en functionsHelper.js
		ajaxCalls.anular(turnoId);
	});

	//Paso valores al formulario que esta dentro del modal que aparece cuando apretamos elboton de modificar
	$("#myModal").on("shown.bs.modal", function(e) {
		e.preventDefault();
		var element = e.relatedTarget
		
		var filial = $('#filial_' + element.getAttribute("turno")).val();
		var deporte = $('#deporte_' + element.getAttribute("turno")).val();
		$('#fechaAlquilerMod').data("DateTimePicker").format("YYYY-MM-DD").date($('#fechaHoraDesde_' + element.getAttribute("turno")).val()).format("DD/MM/YYYY").minDate(moment());
		$("#filialHidden").val(filial);
		$("#deporteHidden").val(deporte);
		$("#turnoHidden").val(this.getAttribute("turno"));

	});

	$("#consultarMod").click(function(e) {
		
		e.preventDefault();
		$.post("/alquiler/buscarCanchasMod", 
				
				$("#buscarcanchasMod").serialize(), function(data) {
					$("#resultado").html(data);
					$('#tableCanchas').DataTable();
					$("[name=id]").val($("#turnoHidden").val());
				});
	});

	//Cuando cierro modal borro datos de busqueda
	$("#myModal").on("hidden.bs.modal", function() {
		$("#resultado").html("");
	});

	//Habilito consulta de canchas en alta de alquiler
	function habilitarConsulta(){
		
		if($("#deporte").val() != "0" && $("#filial").val() != "0" && $("#fechaAlquiler").val() !="" && $("#horaInicio").val() != "" && $("#horaFin").val() != ""){
			
			$("#consultarAlquiler").removeAttr("disabled");
		}else{
			$("#consultarAlquiler").attr("disabled","disabled");
		}
	}
});
