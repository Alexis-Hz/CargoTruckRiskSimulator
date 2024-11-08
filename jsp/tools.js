<script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAA4E81HbFbQhktqy0SUOXkqhRXA_CSNwn4D5Xxvrxo-4zx7xOvYxSG8zio8YDNk5G-8Tzj4OUakL9BTA">
<script type="text/javascript" charset="utf-8">
	google.load("maps", "2.x");
	google.load("jquery", "1.3.1");
</script>
$(document).ready(function(){
	var map = new GMap2(document.getElementById('map'));
	var burnsvilleMN = new GLatLng(44.797916,-93.278046);
	map.setCenter(burnsvilleMN, 8);
});