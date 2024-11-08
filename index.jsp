<html xmlns="http://www.w3.org/1999/xhtml"  xmlns:v="urn:schemas-microsoft-com:vml">  
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
 <title>CargoTrust</title> 
<link type="text/css" rel="stylesheet" href="gmap.css" /> 
<script src="http://maps.google.com/maps?file=api&amp;v=2.x&amp;key=ABQIAAAAQ3FcHgfEPKR8C0YPfsL48xSlijgElRe80ZJOm3JGkYhDwHBafRRWR9MibODg9-Ob99aiTLeAgPc-QA" type="text/javascript"></script> 
<script src="json.js" type="text/javascript"></script> 
<script src="geoxml.js" type="text/javascript"></script>

<link type="text/css" href="http://jqueryui.com/latest/themes/base/ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="http://jqueryui.com/latest/jquery-1.3.2.js"></script>
<script type="text/javascript" src="http://jqueryui.com/latest/ui/ui.core.js"></script>
<script type="text/javascript" src="http://jqueryui.com/latest/ui/ui.tabs.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
    $("#tabs").tabs();
  });
  </script>
  <script type="text/javascript">
  $(document).ready(function(){
    $("#tabs2").tabs();
  });
</script>


<script type="text/javascript">
function init()
{
	initialize();
	loop();
}

function initialize() {
    // `  var latlng = new google.maps.LatLng(31.63722222, -106.42861111);
    
    //     var myOptions = {
    //       zoom: 12,
    //       center: latlng,
    //       mapTypeId: google.maps.MapTypeId.SATELLITE
    //     };
    //     var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
		GUnload();
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
function poll()
{
	
}
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
var tpos = new Array(100);
function stateCallBack()
{
	if (ajax.readyState == 4) {
	     if(ajax.status == 200) {
	    	 
	    	 //document.all.output_simulator.innerHTML = ajax.responseText;
	    	 var array = ajax.responseText.split("|");
		   	 document.all.status.innerHTML = array[0];
		   	 document.all.session.innerHTML = array[1];
		   	 document.all.time.innerHTML = array[2]; 
		   	 document.all.output_simulator.innerHTML = array[3];
		   	 document.all.queued_trucks.innerHTML = array[4];
		   	 document.all.positions_trucks.innerHTML = array[5];
		   	 document.all.event_list.innerHTML = array[6];
		
			var mm = new GMarkerManager(map); 
			tpos = new Array(100);
			var array2 = array[5].split("\n");
			for(i=0;i<array2.lenght;i++)
			{
				var array3 = array2[i].split("(");
				var array4 = array3[1].split(",");
				var x = array4[0];
				var array5 = array4[1].split(")");
				var y = array5[0];
				
				var point = new GLatLng(x,y);
				tpos[i] = new GMarker(point);
			}
			mm.addMarkers(tpos,0,17);
			mm.refresh();
	     }
	     else {
	        alert("Error during AJAX State call. Please try again" + '\n' + ajax.responseText);
	        document.all.output_simulator.innerHTML = ajax.responseText;
	     }
	   }
		
}
function loop()
{
	refresh();
	setTimeout("loop()",1000);
}
</script>


<body  style="font-size:62.5%;" onload="init()">

<form name="cargo" method="post" action="ZenTerminal">
<h2 align="center">CargoTrust Simulator</h2> 
<hr>
<b>Status:</b> <span id="status">Waiting...</span>
<b>SessionID:</b> <span id="session">Waiting...</span>
<p>
<input type="button" name="button_stop" value="Stop" onclick="stop()"> 
<input type="button" name="button_play" value="Play" onclick="play()"> 
<input type="button" name="button_pause" value="Pause" onclick="pause()"> 
<input type="button" name="button_step" value="Step" onclick="step()"> 
<input type="button" name="button_refresh" value="Refresh" onclick="refresh()"> 
<input type="button" name="button_per" value="Per" onclick="per()"> 
<p>
Minutes within current session: <span id="time">Waiting...</span> at <span id="spd">15</span>

&nbsp;&nbsp;&nbsp;&nbsp;
How fast to progress: 
<select size="1" name="speed">
  <option>100x(5 steps)</option>
  <option selected="">10x(10 steps)</option>
  <option>5x(20 steps)</option>
  <option>1x</option>
</select>
<hr>

<table>
<td>
<div id="tabs">
    <ul width="100%">
        <li><a href="#fragment-11"><span>Simulator Event Output</span></a></li>
        <li><a href="#fragment-21"><span>Event Schedule</span></a></li>
    </ul>
    <div id="fragment-11">
  	    <textarea rows="30" cols="50" id="output_simulator" readonly="readonly"></textarea>
   	</div>
    <div id="fragment-21">
        <textarea rows="30" cols="50" id="event_list" readonly="readonly"></textarea>
    </div>
</div>
</td>
<td>
<div id="tabs2">
    <ul width="100%">
        <li><a href="#fragment-12"><span>Truck Positions</span></a></li>
        <li><a href="#fragment-22"><span></span>Trucks at Ports of Entry</a></li>
        <li><a href="#fragment-32"><span></span>Map</a></li>
    </ul>
    <div id="fragment-12">
  	    <textarea rows="30" cols="50" id="positions_trucks" readonly="readonly"></textarea>
   	</div>
    <div id="fragment-22">
        <textarea rows="30" cols="50" id="queued_trucks" readonly="readonly"></textarea>
    </div>
    <div id="fragment-32">
        <div id="map_canvas" style="position:relative; width: 800px; height: 670px; "></div>
    </div>
</div>
</td>
</table>
<p>
	<script type="text/javascript"> 

	    //<![CDATA[

	     var mmap=new GMap2(document.getElementById("map_canvas"),{draggableCursor: 'crosshair', draggingCursor: 'move'});
	    mmap.setCenter(new GLatLng(31.73722222,-106.42861111),13);
		mmap.setMapType(G_HYBRID_MAP);
	    mmap.addControl(new GLargeMapControl());
	    mmap.addControl(new GMapTypeControl());
	    mmap.enableScrollWheelZoom();
	    mmap.enableDoubleClickZoom();
	    mmap.enableContinuousZoom();
	    //var exml = new GeoXml("exml", mmap, "routes1-5.kml", {sidebarid:"the_side_bar"});
	    //exml.parse(); 
		var point = new GLatLng(31.73722222,-106.42861111);
		      var marker = createMarker(point,'<div style="width:240px">Some stuff to display</div>')
		      mmap.addOverlay(marker);


	    //]]>
		function createMarker(point,html) {
		        var marker = new GMarker(point);
		        GEvent.addListener(marker, "click", function() {
		          marker.openInfoWindowHtml(html);
		        });
		        return marker;
		      }
	    </script>
</p>
<hr>
<i>Trust Laboratory, University of Texas at El Paso</i>
</form>
</body>
</head>
</html>