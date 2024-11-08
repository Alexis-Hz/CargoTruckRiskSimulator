<html xmlns="http://www.w3.org/1999/xhtml"  xmlns:v="urn:schemas-microsoft-com:vml">  
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
 <title>CargoTrust</title> 

 <script type="text/javascript"
    src="http://maps.googleapis.com/maps/api/js?key=<YourMapsAPIKey>&sensor=false">
</script>

<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.css" />
<link href="maps.css" rel="stylesheet" type="text/css">
<link href="style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.js"></script>
<script type="text/javascript" src="servletConnect.js"></script>
<script type="text/javascript" src="main.js"></script>	

</head>
<body onload="init()">

<div data-role="page" id="home" align="center">
	<div data-role="header" data-theme="b" data-position="fixed" class="ui-header">
		<h1 style=color:white>CargoTrust Simulator</h1>
	</div><!-- end header -->
	
	<div data-role="content" data-theme="b" class="ui-content" style="position:relative; width: 100%; height: 100%;">
	<form name="cargo" method="post" action="ZenTerminal">
	
	<fieldset class="ui-grid-a">
		<div class="ui-block-a">
			<fieldset class="ui-grid-c">
			<div class="ui-block-a"><b>Status: </b></div>
			<div class="ui-block-b"><span id="status">Waiting...</span></div>	
			<div class="ui-block-c"><b>Session ID:</b></div>
			<div class="ui-block-d"><span id="session">Waiting...</span></div>	    
			</fieldset>
		</div>
		<div class="ui-block-b">
			<fieldset class="ui-grid-c">
			<div class="ui-block-a"><b>Speed: </b></div>
			<div class="ui-block-b"><span id="spd">15</span></div>				
			<div class="ui-block-c"><b>Time:</b></div>
			<div class="ui-block-d"><span id="time">Waiting...</span></div>	    
			</fieldset>
		</div>
	</fieldset>
	
	
	<fieldset class="ui-grid-a">
	<div class="ui-block-a">
		<div data-role="fieldcontain">
	   <fieldset data-role="controlgroup" data-type="horizontal" data-role="fieldcontain">
	         	<input type="button" name="button_stop" value="Stop" onclick="stop()"> 
	
	         	<input type="button" name="button_play" value="Play" onclick="play()"> 
	
	         	<input type="button" name="button_pause" value="Pause" onclick="pause()"> 
	
	         	<input type="button" name="button_step" value="Step Fwd" onclick="step()"> 
	    </fieldset>
		</div>
	</div>
	
	<div class="ui-block-b">
	<div data-role="fieldcontain">
	   <fieldset data-role="controlgroup" data-type="horizontal" data-role="fieldcontain">
	         	<input type="button" name="button_refresh" value="Refresh" onclick="refresh()"> 
	         	<select size="1" name="speed">
				  <option>100x(5 steps)</option>
				  <option selected="">10x(10 steps)</option>
				  <option>5x(20 steps)</option>
				  <option>1x</option>
				</select>
	         	<input type="button" name="button_per" value="Per" onclick="per()">
	
	    </fieldset>
		</div>
	</div>	
	</fieldset>
	
	<fieldset class="ui-grid-a">
	<div class="ui-block-a">
		<div data-role="fieldcontain">
	    <fieldset data-role="controlgroup" data-type="horizontal">
	         	<input type="radio" name="radio-choice-1" id="radio-choice-1" value="choice-1" checked="checked"/>
	         	<label for="radio-choice-1" onclick="showEventsLeft()">Events</label>
	
	         	<input type="radio" name="radio-choice-2" id="radio-choice-2" value="choice-2"  />
	         	<label for="radio-choice-2" onclick="showScheduleLeft()">Schedule</label>
	
	         	<input type="radio" name="radio-choice-3" id="radio-choice-3" value="choice-3"  />
	         	<label for="radio-choice-3" onclick="showPositionsLeft()" >Positions</label>
	
	         	<input type="radio" name="radio-choice-4" id="radio-choice-4" value="choice-4"  />
	         	<label for="radio-choice-4" onclick="showPOEsLeft()">POEs</label>
	         	
	    </fieldset>
		</div>
		
	  	    <textarea id="output_simulator_left" name="output_simulator_left"  readonly="readonly" style="position:relative; width: 100%; height: 100%;">Simulator Output</textarea>
		   	<textarea id="event_list_left" name="output_simulator_left"="event_list_left" readonly="readonly" style="position:relative; width: 100%; height: 100%; display: none;">Event List</textarea>
		    <textarea id="positions_trucks_left" name="output_simulator_left"="positions_trucks_left" readonly="readonly" style="position:relative; width: 100%; height: 100%; display: none;">Truck Positions</textarea>
		   	<textarea id="queued_trucks_left"  name="output_simulator_left"="queued_trucks_left" readonly="readonly" style="position:relative; width: 100%; height: 100%; display: none;">Queued Trucks</textarea>
		    <div id="map_canvas_left" name="output_simulator_left"="map_canvas_left" style="position:relative; width: 800px; height: 670px; display: none;"></div>
		    
				
	</div>
	
	<div class="ui-block-b">
		    <div id="map_canvas_right" name="map_canvas_right" style="position:relative; width: 800px; height: 670px;"></div>
	</div>

	</form>	
	</div><!-- end content -->
	
	<div data-role="footer" data-theme="b" data-position="fixed" class="ui-footer">
	<div data-role="fieldcontain">
	<textarea id="errors" name="errors" readonly="readonly" style="position:relative; width: 100%; height: 100%;">Status Report...</textarea>
	</div>
	<h1 style=color:white>Trust Laboratory, University of Texas at El Paso</h1>
	</div><!-- end footer -->
		
</div><!-- end page -->
</form>
</body>
</html>