$( function () {
	
	customDatePicker.init();	
    $('#table_id').DataTable();
    
    if($("#deporte").val() != "0"){
    	$("#deporte").removeAttr("disabled");
    }else{
    	$("#deporte").attr("disabled","disabled")
    }
    
    $("#filial").change(function(){
    	if(this.value != "0"){
        	var data ={ filialId: this.value};
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
    	}else{
    		$("#deporte").attr("disabled","disabled");
    		$("#deporte").val("0");
    		$("#deporte").find('option').not(':first').remove();

    	}

    })
    
       $('button[type=submit].alquilar').click(function(e) {
    	   
      //Prevent default submission of form
        e.preventDefault();
        var callButtonId = this.id;
	    var formId = "form_"+this.id;
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
       });

} );

