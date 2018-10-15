$(function() {

	//La funcion se encuentra en functionsHelper.js
	//inicializo datepickers y datetables para que funcionen como tales
	customDatePicker.init();
	$('#table_id').DataTable();
	$('#tableCanchas').DataTable();
	habilitarConsulta();

//corroboro si al rederizar la pagina el dropdown de deporte viene con datos si esta vacio lo deshabilito
	if ($("#deporte").val() != "0") {
		$("#deporte").removeAttr("disabled");
	} else {
		$("#deporte").attr("disabled", "disabled")
	}
	
	//chequeo si al renderizar la pagina ya me viene seteada una filial si es asi habilito el dropdown de deporte y el datepicker de fecha de alquiler
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
		//Limpio DropDown de deportes cada vez que cambio la Filial (evito que se dupliquen datos en el mismo)
		$("#deporte").val("0");
		$("#deporte").find('option').not(':first').remove();
		
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

		}
		
		habilitarConsulta();

	})
	
	$("#deporte").change(function() {
		habilitarConsulta();
	});
	
	$("#fechaAlquiler").on("dp.change",function(){
		
		
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
	
	
	$("#fechaAlquilerMod").on("dp.change", function(){
			
			
			if(this.value==""){
				//deshabilito los datepicker de hora si no tengo fecha seleccionado
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
				//buco los dias que se van a deshabilitar en el calendario
				ajaxCalls.getDiasDisabledFilial(filial,"fechaAlquilerMod");
				//busco la hora de apertura y cierre de la filial
				ajaxCalls.getHorasFilialDia(data);
				
				//habilito los datepicker de hora
				$("#horaInicioMod").removeAttr("disabled");
				$("#horaFinMod").removeAttr("disabled");
				$("#consultarMod").removeAttr("disabled");
				
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
	
	// inicio Cambio fecha minima y maxima que permiten los datepicker enfuncion del que le corresponda por ejemplo el de hora desde hasta
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
    
    $("#fechaAlquilerDesde").on("dp.change", function (e) {
        $('#fechaAlquilerHasta').data("DateTimePicker").minDate(e.date);
    });
    
    $("#fechaAlquilerHasta").on("dp.change", function (e) {
        $('#fechaAlquilerDesde').data("DateTimePicker").maxDate(e.date);
    });

 // fin Cambio fecha minima y maxima que permiten los datepicker enfuncion del que le corresponda por ejemplo el de hora desde hasta
	
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

	//Paso valores al formulario que esta dentro del modal que aparece cuando apretamos el boton de modificar
	$("#myModal").on("shown.bs.modal", function(e) {
		e.preventDefault();
		var element = e.relatedTarget
		
		var filial = $('#filial_' + element.getAttribute("turno")).val();
		var deporte = $('#deporte_' + element.getAttribute("turno")).val();
		
		$("#filialHidden").val(filial);
		$("#deporteHidden").val(deporte);
		$("#turnoHidden").val(element.getAttribute("turno"));
		$('#horaInicioMod').attr("disabled","disabled");
		$('#horaFinMod').attr("disabled","disabled");
		$('#consultarMod').attr("disabled","disabled");
		
		var filial = {
				filialId : filial
			};
		
		ajaxCalls.getDiasDisabledFilial(filial,"fechaAlquilerMod");

	});
	
	
	//Limpio campos al cerrarse el modal
	$("#myModal").on("hidden.bs.modal", function() {
		$("#resultado").html("");
		$('#fechaAlquilerMod').val("");
		
  		$('#horaInicioMod').val("");
  		$('#horaFinMod').val("");
	});

	//busco canchas a modificar y se rendeiza datatable
	$("#consultarMod").click(function(e) {
		
		e.preventDefault();
		$.post("/alquiler/buscarCanchasMod", 
				
				$("#buscarcanchasMod").serialize(), function(data) {
					$("#resultado").html(data);
					$('#tableCanchas').DataTable();
					$("[name=id]").val($("#turnoHidden").val());
				});
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
