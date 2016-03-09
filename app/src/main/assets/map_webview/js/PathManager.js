function PathManager(){
	var polylines = [];
	var sourcePOI, destinationPOI;

	this.drawPath = function(opts){
		var poisJSON = opts.poisJSON;
		var currentFloor = opts.currentFloor;
		var offsetX = opts.offsetX;
		var offsetY = opts.offsetY;
		var poiManager = opts.poiManager;

		var path = JSON.parse(Android.getPath());

		if(!path){
			console.log("Error in function: startNavigation \nVariable: path \nMessage: Path is either null or has a length of 0");
			return;
		}

		var pastNode = null;
		for(var i in path){
			if(pastNode != null){
				var latlngs  = getLatLng(pastNode, path[i]);
				var polyline = L.polyline(latlngs, {color: 'red'}).addTo(map);
				this.polylines.push(polyline);
			}

			pastNode = path[i];
		}

		sourcePOI = path[0];
		destinationPOI = path[path.length-1];
		poiManager.changeStartAndEndPOIIcons('js/images/pin1.png'); //TO CHANGE

		function getLatLng(pastNode, currentNode){
			var latLng = [];
			for(var i = 0; i < poisJSON.length; i++){

				var poi = poisJSON[i];
				if(parseInt(currentFloor) == parseInt(poi["floor"]) && ( parseInt(poi["_id"]) == parseInt(currentNode) || parseInt(poi["_id"]) == parseInt(pastNode) ) ){
					var x = -mapWidth + (offsetX + parseInt(poi["x_coord"]));
					var y = -mapHeight + (offsetY + parseInt(poi["y_coord"]));
					latLng.push([y,x]);
					continue;
				}

				if(latLng.length == 2){
					break;
				}
			}
			return latLng;
		}
	};
}