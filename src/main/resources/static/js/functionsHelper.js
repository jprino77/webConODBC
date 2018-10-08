var customDatePicker = customDatePicker || {}
var ajaxCalls = ajaxCalls || {}

customDatePicker = (function () {
		var init= function(){
			
			$('#fechaAlquilerDesde.datepicker, #fechaAlquilerHasta.datepicker').datetimepicker({
				format: 'DD/MM/YYYY',
			    locale: 'es',
			    useCurrent: false,
			});
			
			$("#fechaAlquiler, #fechaAlquilerMod").datetimepicker({
				format: 'DD/MM/YYYY',
			    locale: 'es',
			    useCurrent: false,
			    minDate: moment(),
			    
			});
			
            $('.datetimepicker').datetimepicker({
            	format: 'HH:mm',
            	locale: 'es',
            	
            });
		}
		return { init:init}
})();


ajaxCalls = (function(){
	
	var getDeportesFilial = function(data){
		$.ajax({
        	  url: "/alquiler/deportes",
        	  data: data,
        	  method: "POST"
        	}).done(function(deporteList) {
        		
        		$.each(deporteList, function(index, deporte) {
        		     $('#deporte')
        		         .append($("<option></option>")
        		         .attr("value",deporte.id)
        		         .text(deporte.descripcion));
        		});
        	  $("#deporte").removeAttr("disabled");
        	}).error(function(){
        		$("#deporte").attr("disabled","disabled")
        	});
  	
		
	}
	
	var getDiasDisabledFilial = function(data, datePickerId){

		$.ajax({
      	  url: "/alquiler/diasFilial",
      	  data: data,
      	  method: "POST"
      	}).done(function(diasFilialList) {
      		
      		$('#'+datePickerId).data("DateTimePicker").daysOfWeekDisabled(diasFilialList)
      		
      	});
	}
	
	
	var getHorasFilialDia = function(data){

		$.ajax({
      	  url: "/alquiler/horasFilial",
      	  data: data,
      	  method: "POST"
      	}).done(function(horaFilial) {
      		var dateInicio = new Date();
      		var dateFin = new Date();
      		var horaInicio;
      		var minutoInicio;
      		var horaFin;
      		var minutoFin;
      		
      		if(horaFilial.horaDesde.hour < 10){
      			horaInicio = "0"+ horaFilial.horaDesde.hour;
      		}else{
      			horaInicio = horaFilial.horaDesde.hour;
      		}
      		
      		if(horaFilial.horaDesde.minute < 10){
      			minutoInicio = "0"+ horaFilial.horaDesde.minute;
      		}else{
      			minutoInicio = horaFilial.horaDesde.minute;
      		}
      		
      		if(horaFilial.hasta.hour < 10){
      			horaFin = "0"+ horaFilial.hasta.hour;
      		}else{
      			horaFin = horaFilial.hasta.hour;
      		}
      		
      		if(horaFilial.hasta.minute < 10){
      			minutoFin = "0"+ horaFilial.hasta.minute;
      		}else{
      			minutoFin = horaFilial.hasta.minute;
      		}
      		
      		dateInicio.setHours(horaInicio,minutoInicio)
      		dateFin.setHours(horaFin,minutoFin)
      		
      		//Cuando esta en vista de alta
      		if($('#horaInicio').length){
          		$('#horaInicio').data("DateTimePicker").minDate(dateInicio).format("HH:mm");
          		$('#horaInicio').data("DateTimePicker").maxDate(dateFin).format("HH:mm");
          		
          		$('#horaFin').data("DateTimePicker").minDate(dateInicio).format("HH:mm");
          		$('#horaFin').data("DateTimePicker").maxDate(dateFin).format("HH:mm");
          		
          		$('#horaInicio').data("DateTimePicker").date(dateInicio);
          		$('#horaFin').data("DateTimePicker").date(dateFin);
      		}

      		
      		
      		//Cuando esta en vista de Modificacion
      		if($('#horaInicioMod').length){
	      		$('#horaInicioMod').data("DateTimePicker").minDate(dateInicio).format("HH:mm");
	      		$('#horaInicioMod').data("DateTimePicker").maxDate(dateFin).format("HH:mm");
	      		
	      		$('#horaFinMod').data("DateTimePicker").minDate(dateInicio).format("HH:mm");
	      		$('#horaFinMod').data("DateTimePicker").maxDate(dateFin).format("HH:mm");
	      		
	      		$('#horaInicioMod').data("DateTimePicker").date(dateInicio);
	      		$('#horaFinMod').data("DateTimePicker").date(dateFin);
      		}
      		
      	});
	}
	
	var alquilar = function(callButtonId,formId){
		
		$.ajax({
	         url : '/alquiler/alquilar',
	         method: "POST",
	         data : $('#'+formId).serialize(),
	         success : function(res) {
	        	 $(".msg").html('');
	        	 if(res == true){
		        	 $(".msg").addClass("alert alert-success").append("<strong>Alquiler realizado con exito</strong>");
		        	 $('#'+callButtonId).attr("disabled","disabled");
	        	 }else{
		        	 $(".msg").addClass("alert alert-danger").append("<strong>Error al realizar alquiler</strong>");
	        	 }
	         },
	         error:function(res) {
	        	 $(".msg").html('');
	        	 $(".msg").addClass("alert alert-danger").append("<strong>Error al realizar alquiler</strong>");
	         } 
	      })
		
	}
	
	var anular = function(turnoId){
		
		$.ajax({
 	         url : '/alquiler/anular/'+turnoId,
 	         method: "GET",
 	         success : function(res) {
 	        	 $(".msg").html('');
 	        	 if(res == true){
 		        	 $(".msg").addClass("alert alert-success").append("<strong>Anulaci&iacute;on realizada con exito</strong>");
 		        	 $('#'+turnoId).css("display","none");
 		        	 $('#mod_'+turnoId).css("display","none");
 	        	 }else{
 		        	 $(".msg").addClass("alert alert-danger").append("<strong>Error al realizar Anulaci&iacute;on</strong>");
 	        	 }
 	         },
 	         error:function(res) {
 	        	 $(".msg").html('');
 	        	 $(".msg").addClass("alert alert-danger").append("<strong>Error al realizar Anulaci&iacute;on</strong>");
 	         } 
 	      })
		
	}
	
	return {getDeportesFilial:getDeportesFilial,
			getDiasDisabledFilial:getDiasDisabledFilial,
			getHorasFilialDia:getHorasFilialDia,
			alquilar:alquilar,
			anular:anular}
})();