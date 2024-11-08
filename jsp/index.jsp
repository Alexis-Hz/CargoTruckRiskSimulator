<html> 
	<head> 
		<title>CargoTrust Simulator Portal</title> 
		<!-- http://code.google.com/apis/ajaxlibs/documentation/ --> 
 
		<script type="text/javascript" 
		src="https://maps.googleapis.com/maps/api/js?key=<YourMapsAPIKey>"></script> 
		
		<script type="text/javascript" charset="utf-8">
			//google.load("maps", "2.x");
			//google.load("jquery", "1.3.1");
		</script>
		<script language="javascript" src="geotools.js"></script>
		<script src="proj4js-combined.js"></script>
		<!-- New Stuff starts here -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.css" />
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.js"></script>
		
		<!-- New Stuff ends here -->
		<script type="text/javascript" charset="utf-8"><!-- 
			//var markers;
			var southWest; 
	    	var northEast; 
	    	var lngSpan;
	    	var latSpan;
	    	var bounds;
	    	var elPasoJuarez; 

	    	var map;
	    	var markers = [];
	    	var allLatLng = [];
	    	var myLatlng = new google.maps.LatLng(31.63722222,-106.42861111);
	    	function initialize() {
		        var mapOptions = {
		          zoom: 10,
		          center: myLatlng,
		          mapTypeId: google.maps.MapTypeId.ROADMAP
		        };
		        map = new google.maps.Map(document.getElementById("map-canvas"),
		            mapOptions);

		       // var route0 = new google.maps.KmlLayer({
				//    url: 'http://gmaps-samples.googlecode.com/svn/trunk/ggeoxml/cta.kml'
				//  });
				//  route0.setMap(map);
		        //marker.setMap(map);
		    }
		    google.maps.event.addDomListener(window, 'load', init);
	    	
	    	console.log("origin marker " + myLatlng.lat() + ", " + myLatlng.lng());
	    	var marker = new google.maps.Marker({
			    position: myLatlng,
			    map: map,
			    title:"Hello World!"
			});

	    	// Add a marker to the map and push to the array.
	    	var markersBuffer = [];
	    	function addMarker(location) {
	    		//console.log("trying to push marker " + location.lat() + ", " + location.lng());
			  var marker = new google.maps.Marker({
			    map: map,
			    position: location,
			    title : location.lat() + "," + location.lng(),
			    icon: 'https://maps.gstatic.com/mapfiles/ms2/micons/truck.png'			    
			  });
			  markers.push(marker);
			  //console.log("pushed marker " + marker.getPosition().lat() + ", " + marker.getPosition().lng());
			}

			// Sets the map on all markers in the array.
			function setAllMap(mapx) {
			//	if(mapx == null)
			//		console.log("Null");
			  for (var i = 0; i < markers.length; i++) {
			    markers[i].setMap(mapx);
			    console.log("marker "+i+", "+ markers[i].getPosition().lat() + ", " + markers[i].getPosition().lng());
			  }
			}

			// Removes the markers from the map, but keeps them in the array.
			function clearMarkers() {
			  setAllMap(null);
			}

			// Shows any markers currently in the array.
			function showMarkers() {
			  setAllMap(map);
			}

			// Deletes all markers in the array by removing references to them.
			function deleteMarkers() {
			  clearMarkers();
			  markers = [];
			}
			
			function init()
			{
				tabOutputSimulator();
				initialize();
				loop();
			}
			  
			function getXMLObject()  //XML OBJECT
			{
			   var xmlHttp = false;
			   try {
			     xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")  // For Old Microsoft Browsers
			   }
			   catch (e) {
			     try {
			       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")  // For Microsoft IE 6.0+
			     }
			     catch (e2) {
			       xmlHttp = false   // No Browser accepts the XMLHTTP Object then false
			     }
			   }
			   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
			     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
			   }
			   return xmlHttp;  // Mandatory Statement returning the ajax object created
			}
			var ajax = new getXMLObject();	//xmlhttp holds the ajax object
			function stop()
			{
				if(ajax) 
				{
			        ajax.open("POST","ZenTerminal",true); //ZenTerminal will be the servlet name
			        ajax.onreadystatechange  = stateCallBack;
			        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			        ajax.send("act=stop"); //Posting txtname to Servlet
			    }
			}
			function play()
			{
				if(ajax) 
				{
					ajax.open("POST","ZenTerminal",true); //ZenTerminal will be the servlet name
			        //ajax.onreadystatechange  = stateCallBack;
			        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			        ajax.send("act=play"); //Posting txtname to Servlet
			    }
			}
			function pause()
			{
				if(ajax) 
				{
			        ajax.open("POST","ZenTerminal",true); //ZenTerminal will be the servlet name
			        ajax.onreadystatechange  = stateCallBack;
			        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			        ajax.send("act=pause"); //Posting txtname to Servlet
			    }
			}
			function step()
			{
				if(ajax) 
				{
			        ajax.open("POST","ZenTerminal",true); //ZenTerminal will be the servlet name
			        ajax.onreadystatechange  = stateCallBack;
			        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			        ajax.send("act=step"); //Posting txtname to Servlet
			    }
			}
			function refresh()
			{
				if(ajax) 
				{
			        ajax.open("POST","ZenTerminal",true); //ZenTerminal will be the servlet name
			        ajax.onreadystatechange  = stateCallBack;
			        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			        ajax.send("act=refresh"); //Posting txtname to Servlet
			    }
			}
			function per()
			{
				if(ajax) 
				{
			        ajax.open("POST","ZenTerminal",true); //ZenTerminal will be the servlet name
			        ajax.onreadystatechange  = stateCallBack;
			        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			        ajax.send("act=per"); //Posting txtname to Servlet
			    }
			}
			var lats = [];
			var lons = [];
			var texs = [];
			var latlon = [];
			function stateCallBack()
			{
				if (ajax.readyState == 4) {
				     if(ajax.status == 200) {
				    	 
				    	 //document.all.output_simulator.innerHTML = ajax.responseText;
				    	 
				    	 var array = ajax.responseText.split("|");
					   	 document.all.status.innerHTML = array[0];
					   	 document.all.session.innerHTML = array[1];
					   	 document.all.sim_time.innerHTML = array[2]; 
					   	 document.all.output_simulator.innerHTML = array[3];
					   	 document.all.queued_trucks.innerHTML = array[4];
					   	 document.all.positions_trucks.innerHTML = array[5];
					   	 document.all.event_list.innerHTML = array[6];

					   	if(typeof(array[5])!="undefined")
					   	{ 
					   		document.all.errorbox.innerHTML = "NOT";
						   	if(array[5].length > 10)
						   	{
						   		document.all.errorbox.innerHTML = "IN";
								var array2 = array[5].split('\n');
								var tr = "";
								var i;
								//showMarkers();
								deleteMarkers();//new
								for(i = 0; i < array2.length; i++)
								{
									if(array2[i].length > 0)
									{
										var array3 = array2[i].split(" ");
										var array4 = array3[4].split(",");
										
										lats[i] = array4[0].substring(1);
										var end  = array4[1].length -1;
										lons[i] = array4[1].substring(0, end);

										//var lat = Math.round(lats[i]);
										//var lon = Math.round(lons[i]);

										var lat = lats[i];
										var lon = lons[i];

										console.log("lat: " + lat + " lon: " + lon);
										texs[i] = "TID: " + array3[0] + " VIN: " + array3[1] + '\n' +"Status: " + array3[2] + '\n'+lat + ", "+lon + '\n';

										//create a wgs84 coordinate
										//wgs84=new GT_WGS84();
										//wgs84.setDegrees(lat, lon);
										/*
										Proj4js.defs["EPSG:26913"] = "+proj=utm +zone=13 +ellps=GRS80 +datum=NAD83 +units=m +no_defs";
										Proj4js.defs["EPSG:4326"] = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs";
										var source = new Proj4js.Proj("EPSG:26913");   
										var dest = new Proj4js.Proj("EPSG:4326");

										//var p = new Proj4js.Point(367558.7642,3498216.4419);  
										var p = new Proj4js.Point(lat,lon); 
										Proj4js.transform(source, dest, p);  
										*/
																			
										//allLatLng//here											
										//var point = new GLatLng(p.y , p.x);
										//console.log (p.y + "," + p.x);
										//var point = new google.maps.LatLng(p.y , p.x);
										var point = new google.maps.LatLng(lon , lat);
										addMarker(point);
										latlon[i] = point;
										texs[i] += "Latitude "+lat + " Longitude "+lon +'\n'+'\n';
										tr += texs[i];
									}
								}
								//markers = markersBuffer.slice(0);
								showMarkers();
								document.all.digest.innerHTML = tr;
							}
					   	}		   	 
				     }
				     else {
				    	 document.all.errorbox.innerHTML = "Error on statecallback"+'\n'+ajax.responseText;
				        //document.all.output_simulator.innerHTML = ajax.responseText;
				     }
				   }
			}



			
			function loop()
			{
				refresh();
				//updateTrucks();
				setTimeout("loop()",1000);
			}
			function updateTrucks()
			{
				//for (var i = 0; i < 10; i++) {
				//	var point = new GLatLng(southWest.lat() + latSpan * Math.random(),
			    //	        southWest.lng() + lngSpan * Math.random());
		    	//	markers[i].setPoint(point);
		    	//	
		    	//}
		    	//deleteMarkers();
		    	for (var i = 0; i < markers.length; i++) {
					//var point = new GLatLng(lons[i],lats[i]);
					markers[i].setMap(map);
		    		//markers[i].bindInfoWindowHtml("<html>"+texs[i]+ "</html>")
		    		
		    	}
		    	
			}
			
			function tabOutputSimulator()
			{
				$( "#output_simulator" ).show();
			 	$( "#event_list" ).hide();
			 	$( "#positions_trucks" ).hide();
			 	$( "#queued_trucks" ).hide();
			 	$( "#digest" ).hide();
			 	$( "#errorbox" ).hide();
			}
			function tabEventList()
			{
				$( "#output_simulator" ).hide();
			 	$( "#event_list" ).show();
			 	$( "#positions_trucks" ).hide();
			 	$( "#queued_trucks" ).hide();
			 	$( "#digest" ).hide();
			 	$( "#errorbox" ).hide();
			}
			function tabPositionsTrucks()
			{
				$( "#output_simulator" ).hide();
			 	$( "#event_list" ).hide();
			 	$( "#positions_trucks" ).show();
			 	$( "#queued_trucks" ).hide();
			 	$( "#digest" ).hide();
			 	$( "#errorbox" ).hide();
			}
			function tabQueuedTrucks()
			{
				$( "#output_simulator" ).hide();
			 	$( "#event_list" ).hide();
			 	$( "#positions_trucks" ).hide();
			 	$( "#queued_trucks" ).show();
			 	$( "#digest" ).hide();
			 	$( "#errorbox" ).hide();
			}
			function tabDigest()
			{
				$( "#output_simulator" ).hide();
			 	$( "#event_list" ).hide();
			 	$( "#positions_trucks" ).hide();
			 	$( "#queued_trucks" ).hide();
			 	$( "#digest" ).show();
			 	$( "#errorbox" ).hide();
			}
			function tabErrorbox()
			{
				$( "#output_simulator" ).hide();
			 	$( "#event_list" ).hide();
			 	$( "#positions_trucks" ).hide();
			 	$( "#queued_trucks" ).hide();
			 	$( "#digest" ).hide();
			 	$( "#errorbox" ).show();
			}
		--></script>
		
		<style type="text/css" media="screen"> 
		    #map-canvas { float:left; width:500px; height:500px; }
		    #message { position:absolute; padding:10px; background:#555; color:#fff; width:75px; }
    		#list { float:right; width:200px; background:#eee; list-style:none; padding:0; }
   			#list li { padding:10px; }
    		#list li:hover { background:#555; color:#fff; cursor:pointer; cursor:hand; }
    		
    		* {padding:0; margin:0;}

			html {
				padding:15px 15px 0;
				font-family:sans-serif;
				font-size:14px;
			}


			.tabs li {
				list-style:none;
				display:inline;
			}

			.tabs a {
				padding:5px 10px;
				display:inline-block;
				text-decoration:none;
			}

			.tabs a.active {
				background:#fff;
				color:#000;
			}
			.outputArea
			{
				position:absolute;
				top:30;
				left:5;
				height:200;
				resize: none;
			} 
			#one
			{
			}
			#two
			{
			position:relative;
			height:500px;
			}
			#three
			{
			}
}
		</style> 
		
		

	</head>
	 
	<body> 
		<!--  New code Start-->
		<form name="cargo" method="post" action="ZenTerminal">
		
		<div data-role="page" id="home" align="left">
			<div data-role="header" data-theme="b" data-position="fixed" class="ui-header">
				<h1 style=color:white>CargoTrust Simulator</h1>
			</div><!-- end header -->
			
			<div data-role="content" data-theme="b" class="ui-content" style="position:relative; width: 100%; height: 100%;">
			
			<fieldset class="ui-grid-a">
				<div class="ui-block-a">
					<b>Status: </b>					
					<span id="status">Waiting...</span>	
					<b>Session ID:</b>
					<span id="session">Waiting...</span>	    
					<b>Speed: </b>
					<span id="spd">1</span>			
					<b>Time:</b>
					<span id="sim_time">Waiting...</span>
				</div>
				
				
				<div class="ui-block-b">
				<b>Minutes within current session: <span id="time">Waiting...</span> at <span id="spd">1</span> Simulated Speed</b>
				</div>
			    
				
			</fieldset>
			
			<fieldset class="ui-grid-a">
				<div data-role="fieldcontain" style="margin-left: auto; margin-right: auto; width: 400px;">
			   <fieldset data-role="controlgroup" data-type="horizontal" data-role="fieldcontain">
			         	<input type="button" name="button_stop" value="Stop" onclick="stop()"> 
						<input type="button" name="button_play" value="Play" onclick="play()"> 
						<input type="button" name="button_pause" value="Pause" onclick="pause()"> 
						<input type="button" name="button_refresh" value="Refresh" onclick="refresh()"> 
						<!--
						<input type="button" name="button_step" value="Step" onclick="step()"> 
						<input type="button" name="button_per" value="Per" onclick="per()"> 
						<select size="1" name="speed">
				 <option>100x(5 steps)</option>
				 <option selected="">10x(10 steps)</option>
			 	 <option>5x(20 steps)</option>
			 	 <option>1x</option>
				</select>
				-->
			    </fieldset>
				</div>
			</fieldset>
			
			<fieldset class="ui-grid-a" id="one">
			<div class="ui-block-a" id="two">
				<ul class='tabs'>
   				<li><a href='#output_simulator' onclick="tabOutputSimulator()">output</a></li>
			    <li><a href='#event_list' onclick="tabEventList()">events</a></li>
			    <li><a href='#positions_trucks' onclick="tabPositionsTrucks()">trucks</a></li>
			    <li><a href='#queued_trucks' onclick="tabQueuedTrucks()">queued</a></li>
			    <li><a href='#digest' onclick="tabDigest()">digest</a></li>
			    <li><a href='#errorbox' onclick = "tabErrorbox()">errorbox</a></li>
			  	</ul>
			  	<textarea class="outputArea" id='output_simulator' readonly="readonly" style="height: 450px">
			    output_simulator
			  	</textarea>
			  	<textarea class="outputArea" id='event_list' readonly="readonly" style="height: 450px">
			    event_list
			  	</textarea>
			  	<textarea class="outputArea" id='positions_trucks' readonly="readonly" style="height: 450px">
			    positions_trucks
			  	</textarea>
			 	<textarea class="outputArea" id='queued_trucks' readonly="readonly" style="height: 450px">
			    queued_trucks
			  	</textarea>
			 	<textarea class="outputArea" id='digest' readonly="readonly" style="height: 450px">
			    digest
			  	</textarea>
			  	<textarea class="outputArea" id='errorbox' readonly="readonly" style="height: 450px">
			    errorbox
			  	</textarea>
			  	
			  	<!--  
			  	//$( "#output_simulator" ).click(tabOutputSimulator());
			 	//$( "#event_list" ).click(tabEventList());
			 	//$( "#positions_trucks" ).click(tabPositionsTrucks());
			 	//$( "#queued_trucks" ).click(tabQueuedTrucks());
			 	//$( "#digest" ).click(tabDigest());
			 	//$( "#errorbox" ).click(tabErrorbox());
			 	
				<textarea rows="30" cols="50" id="output_simulator" readonly="readonly"></textarea>
				<textarea rows="30" cols="50" id="event_list" readonly="readonly"></textarea>
				<textarea rows="30" cols="50" id="positions_trucks" readonly="readonly"></textarea>
				<textarea rows="30" cols="50" id="queued_trucks" readonly="readonly"></textarea>
				<textarea rows="30" cols="50" id="digest" readonly="readonly"></textarea>
				<textarea rows="30" cols="50" id="errorbox" readonly="readonly"></textarea>
				-->
			</div>
			<div class="ui-block-b" id="three">
			<div id="map-canvas" style="width: 500px;height: 500px"></div>
			<div id="pointList"></div>
					<!--  
					<div id="message0" style="display:none;">
						Test text.
					</div>
					<div id="message1" style="display:none;">
						Test text.
					</div>
					<div id="message2" style="display:none;">
						Test text.
					</div>
					<div id="message3" style="display:none;">
						Test text.
					</div>
					<div id="message4" style="display:none;">
						Test text.
					</div>
					<div id="message5" style="display:none;">
						Test text.
					</div>
					<div id="message6" style="display:none;">
						Test text.
					</div>
					<div id="message7" style="display:none;">
						Test text.
					</div>
					<div id="message8" style="display:none;">
						Test text.
					</div>
					<div id="message9" style="display:none;">
						Test text.
					</div>
					-->
			</div>
			</fieldset>
			
			<!--  
			<table>
				<tr>
				<td>
					<ul id="list"></ul>
					<div id="message0" style="display:none;">
						Test text.
					</div>
					<div id="message1" style="display:none;">
						Test text.
					</div>
					<div id="message2" style="display:none;">
						Test text.
					</div>
					<div id="message3" style="display:none;">
						Test text.
					</div>
					<div id="message4" style="display:none;">
						Test text.
					</div>
					<div id="message5" style="display:none;">
						Test text.
					</div>
					<div id="message6" style="display:none;">
						Test text.
					</div>
					<div id="message7" style="display:none;">
						Test text.
					</div>
					<div id="message8" style="display:none;">
						Test text.
					</div>
					<div id="message9" style="display:none;">
						Test text.
					</div>
				</td>
				
				</tr>
			</table>
			-->
			</div><!-- end content -->
			
			<div data-role="footer" data-theme="b" data-position="fixed" class="ui-footer">
			<div data-role="fieldcontain">
			<textarea id="errors" name="errors" readonly="readonly" style="position:relative; width: 100%; height: 100%;">Status Report...</textarea>
			</div>
			<h1 style=color:white>Trust Laboratory, University of Texas at El Paso</h1>
			</div><!-- end footer -->
				
		</div>
		</form>
		<!-- New Code End -->
		
	</body> 
</html>