var customDatePicker = customDatePicker || {}

customDatePicker = (function () {
		var init= function(){
			
			$('.datepicker').datetimepicker({
				format: 'DD/MM/YYYY',
			    locale: 'es'
			});
			
            $('.datetimepicker').datetimepicker({
            	format: 'LT',
            	locale: 'es'
            });
		}
		return { init:init}
})()