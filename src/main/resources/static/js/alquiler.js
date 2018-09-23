$( function () {
    $('#table_id').DataTable();

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
    	}

    })

} );

