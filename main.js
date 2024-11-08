function showEventsRight()
	{
		$('#output_simulator_right').show();
		$('#event_list_right').hide();
		$('#positions_trucks_right').hide();
		$('#queued_trucks_right').hide();
		$('#map_canvas_right').hide();
	}
	function showScheduleRight()
	{
		$('#output_simulator_right').hide();
		$('#event_list_right').show();
		$('#positions_trucks_right').hide();
		$('#queued_trucks_right').hide();
		$('#map_canvas_right').hide();
	}
	function showPositionsRight()
	{
		$('#output_simulator_right').hide();
		$('#event_list_right').hide();
		$('#positions_trucks_right').show();
		$('#queued_trucks_right').hide();
		$('#map_canvas_right').hide();
	}
	function showPOEsRight()
	{
		$('#output_simulator_right').hide();
		$('#event_list_right').hide();
		$('#positions_trucks_right').hide();
		$('#queued_trucks_right').show();
		$('#map_canvas_right').hide();
	}
	function showMapRight()
	{
		$('#output_simulator_right').hide();
		$('#event_list_right').hide();
		$('#positions_trucks_right').hide();
		$('#queued_trucks_right').hide();
		$('#map_canvas_right').show();
		google.maps.event.trigger(map, 'resize');
	}
	
	function showEventsLeft()
	{
		$('#output_simulator_left').show();
		$('#event_list_left').hide();
		$('#positions_trucks_left').hide();
		$('#queued_trucks_left').hide();
		$('#map_canvas_left').hide();
	}
	function showScheduleLeft()
	{
		$('#output_simulator_left').hide();
		$('#event_list_left').show();
		$('#positions_trucks_left').hide();
		$('#queued_trucks_left').hide();
		$('#map_canvas_left').hide();
	}
	function showPositionsLeft()
	{
		$('#output_simulator_left').hide();
		$('#event_list_left').hide();
		$('#positions_trucks_left').show();
		$('#queued_trucks_left').hide();
		$('#map_canvas_left').hide();
	}
	function showPOEsLeft()
	{
		$('#output_simulator_left').hide();
		$('#event_list_left').hide();
		$('#positions_trucks_left').hide();
		$('#queued_trucks_left').show();
		$('#map_canvas_left').hide();
	}
	function showMapLeft()
	{
		$('#output_simulator_left').hide();
		$('#event_list_left').hide();
		$('#positions_trucks_left').hide();
		$('#queued_trucks_left').hide();
		$('#map_canvas_left').show();
		google.maps.event.trigger(map2, 'resize');
	}