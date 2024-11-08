function init()
{
	initialize();
	loop();
}

function initialize() {
   	//GUnload();
   	mapInitialize();
  }
  var map;
  var map2;
function mapInitialize() {
    var latlng = new google.maps.LatLng(-34.397, 150.644);
    var myOptions = {
      zoom: 8,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map_canvas_right"),
        myOptions);
    
    var latlng2 = new google.maps.LatLng(-34.397, 150.644);
    var myOptions2 = {
      zoom: 8,
      center: latlng2,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map2 = new google.maps.Map(document.getElementById("map_canvas_left"),
        myOptions2);
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
		   	 
		   	 document.all.output_simulator_right.innerHTML = array[3];
		   	 document.all.output_simulator_left.innerHTML = array[3];
		   	 
		   	 document.all.queued_trucks_right.innerHTML = array[4];
		   	 document.all.queued_trucks_left.innerHTML = array[4];
		   	 
		   	 document.all.positions_trucks_right.innerHTML = array[5];
		   	 document.all.positions_trucks_left.innerHTML = array[5];
		   	 
		   	 document.all.event_list_right.innerHTML = array[6];
		   	 document.all.event_list_left.innerHTML = array[6];
		
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
	    	//alert("Error during AJAX State call. Please try again" + '\n' + ajax.responseText);
	    	document.all.errors.innerHTML = "Error During Ajax Call";
	        
	        //document.all.output_simulator.innerHTML = ajax.responseText;
	     }
	   }
		
}
function loop()
{
	refresh();
	setTimeout("loop()",1000);
}