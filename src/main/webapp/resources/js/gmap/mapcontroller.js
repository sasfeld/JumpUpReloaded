/**
 * JumpUp.Me Car Pooling Application
 * 
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */

"use strict";

// namespaces
this.de = this.de || {};
this.de.htw = this.de.htw || {};
this.de.htw.fb4 = this.de.htw.fb4 || {};
this.de.htw.fb4.imi = this.de.htw.fb4.imi || {};
this.de.htw.fb4.imi.jumpup = this.de.htw.fb4.imi.jumpup || {};
this.de.htw.fb4.imi.jumpup.trip = this.de.htw.fb4.imi.jumpup.trip || {};

( function() {
	var ADDTRIP_REF_FORM = 'form[name="createTripForm"]';
	
	var	REF_ADDTRIP_INPUT_LAT_START = ADDTRIP_REF_FORM
			+ " input[name$='latitude_start']";
	var	REF_ADDTRIP_INPUT_LONG_START = ADDTRIP_REF_FORM
	+ " input[name$='longitude_start']";
	var	REF_ADDTRIP_INPUT_LAT_END = ADDTRIP_REF_FORM
	+ " input[name$='latitude_end']";
	var	REF_ADDTRIP_INPUT_LONG_END = ADDTRIP_REF_FORM
	+ " input[name$='longitude_end']";
	// --> hidden input fields which needs to be stored in DB
	var
	REF_ADDTRIP_INPUT_STARTCOORD_VISIBLE = ADDTRIP_REF_FORM
	+ ' .start_location';
	var
	REF_ADDTRIP_INPUT_ENDCOORD_VISIBLE =  ADDTRIP_REF_FORM
	+ ' .end_location';;
	var
	REF_ADDTRIP_INPUT_DURATION = 'input[name="duration"]';
	var
	REF_ADDTRIP_INPUT_DISTANCE = 'input[name="distance"]';
	var
	REF_ADDTRIP_INPUT_OVERVIEW_PATH = ADDTRIP_REF_FORM
	+ " input[name$='overview_path']";
	var
	REF_ADDTRIP_INPUT_VIA_WAYPOINTS = ADDTRIP_REF_FORM
	+ " input[name$='via_waypoints']";
	var _this;

	/*
	 * Constructor function for this module. The google map will be initialized in
	 * the constructor. - param options, see the google map reference - throws an
	 * exception if the map isn't loaded
	 */
	de.htw.fb4.imi.jumpup.trip.MapController = function(mapsOptions, ctrlOptions) {
		_this = this;

		try {
			this.gmap = new de.htw.fb4.imi.jumpup.trip.GoogleMap( mapsOptions );
			if ( null != ctrlOptions ) {
				// no options
			}
		} catch ( e ) {
			throw e;
		}
		;

		/*
		 * fetch and store coordinate of points
		 */
		this.inputStartVisible = $( REF_ADDTRIP_INPUT_STARTCOORD_VISIBLE );
		this.inputEndVisible = $( REF_ADDTRIP_INPUT_ENDCOORD_VISIBLE );
		this.inputStartLat = $( REF_ADDTRIP_INPUT_LAT_START );
		this.inputStartLong = $( REF_ADDTRIP_INPUT_LONG_START );
		this.inputEndLat = $( REF_ADDTRIP_INPUT_LAT_END );
		this.inputEndLong = $( REF_ADDTRIP_INPUT_LONG_END );
		this.inputDuration = $( REF_ADDTRIP_INPUT_DURATION );
		this.inputDistance = $( REF_ADDTRIP_INPUT_DISTANCE );
		this.inputOverviewPath = $( REF_ADDTRIP_INPUT_OVERVIEW_PATH );
		this.inputViaWaypoints = $( REF_ADDTRIP_INPUT_VIA_WAYPOINTS );
	};

	/**
	 * Handle the response of Google's DirectionsService.
	 * 
	 * This method is given to the gMap controller as callback method.
	 * 
	 * It will be called by the Directions Service.
	 */
	de.htw.fb4.imi.jumpup.trip.MapController.prototype.handleRouteResponse = function(directionsResult) {
		console.log( "Map controller -> handling route response" );

		var singleRoute = directionsResult.routes[ 0 ];
		
		console.log(singleRoute);

		if ( 1 == singleRoute.legs.length ) { // no waypoints, only start
			// and endpoint
			var singleLeg = singleRoute.legs[ 0 ];
			var startLat = singleLeg.start_location.lat();
			var startLong = singleLeg.start_location.lng();
			var endLat = singleLeg.end_location.lat();
			var endLong = singleLeg.end_location.lng();
			var duration = singleLeg.duration.value; // seconds
			var distance = singleLeg.distance.value; // meter
			var overviewPath = singleRoute.overview_path;
			var viaWaypoints = singleLeg.via_waypoints;
			/*
			 * ..:: OverviewPath strategy ::..
			 */
			// change: active strategy in module overviewPathStrategy
			// (return type)
			var overviewStrategy = new de.htw.fb4.imi.jumpup.trip.ByDistanceStrategy();
			var overviewString = overviewStrategy.toString( overviewStrategy
					.execute( overviewPath ) );
			/*
			 * ..::::::::::::::::::::::::::::..
			 */
			/*
			 * ..:: ViaWaypoints ::..
			 */
			var waypointsStringConcat = "";
			for ( var waypointIndex = 0; waypointIndex < viaWaypoints.length; waypointIndex++ ) {
				// kb = latitude / breite; lb = longitude / länge
				waypointsStringConcat += viaWaypoints[ waypointIndex ].lat() + ","
						+ viaWaypoints[ waypointIndex ].lng() + ";";
			}
			/*
			 * ..::::::::::::::::::..
			 */

			console.log( "Map controller -> startLatLng: \n" + startLat + " - " + startLong );
			console.log( "Map controller -> endLatLng: \n" + endLat + " - " + endLong );
			console.log( "Map controller -> duration: \n" + duration );
			console.log( "Map controller -> overviewPath: \n" + overviewString );
			console
					.log( "Map controller -> viaWaypoints: \n" + waypointsStringConcat );

			/*
			 * ..:: fill hidden input fields ::..
			 */
			if ( _this.gmap.isDraggable() ) {
				_this.inputStartVisible.val( singleLeg.start_address );
				_this.inputEndVisible.val( singleLeg.end_address );
			}
			_this.inputStartLat.val( startLat );
			_this.inputStartLong.val( startLong );
			_this.inputEndLat.val( endLat );
			_this.inputEndLong.val( endLong );
			_this.inputDuration.val( duration );
			_this.inputDistance.val( distance );
			_this.inputOverviewPath.val( overviewString );
			_this.inputViaWaypoints.val( waypointsStringConcat );
			/*
			 * ..::::::::::::::::::::::::::::::..
			 */
			console.log( "value of input field start lat: " + _this.inputStartLat.val() );
		}
		;
	};

	de.htw.fb4.imi.jumpup.trip.MapController.prototype.select = function(tripid) {
			_this.gmap.selectByTripId( tripid );
	};
	
	/**
	 * Get the viaWaypoints array to be handed to the googlemap for a string as it's given by the backend.
	 * 
	 * @param viaWaypoints the String as given by the backend
	 * @return an array of elements that store latLng values.
	 */
	de.htw.fb4.imi.jumpup.trip.MapController.prototype.toOverviewArray = function(viaWaypoints) {		
		var waypointsArray = new Array();
		if (viaWaypoints != null) {
			waypointsArray = viaWaypoints.split(";");
			waypointsArray.pop(); // last empty element
			for ( var i = 0; i < waypointsArray.length; i++) {
				waypointsArray[i] = "(" + waypointsArray[i]
						+ ")";
			}			
		}
		return waypointsArray;
	};

	/**
	 * Show a single route on the map. - param start, the value of the starting
	 * point, must be a coordinate or a valid location. - param destination, the
	 * value of the destination point, must be a coordinate or a valid location. -
	 * param waypoints, an array of waypoints, must be coordinates or valid
	 * locations - param multiple set true if you want several routes to be
	 * rendered.
	 */
	de.htw.fb4.imi.jumpup.trip.MapController.prototype.showRoute = function(id, start, destination,
			waypoints, multiple, callbackSelect) {

		// remove rendered routes
		if ( !multiple ) {
			this.gmap.removeRoutes();
		}

		// show new route
		// this.gmap.showRoute( start, destination, $( "#sendBt" ),
		// this.handleRouteResponse );

		this.gmap
				.showRoute( id, start, destination, waypoints, this.handleRouteResponse, callbackSelect );
	};
	
}());