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
this.de.htw.fb4.imi.jumpup.booking = this.de.htw.fb4.imi.jumpup.booking || {};


$(document).ready(function() {
	var REF_MAP_CANVAS = "#map_canvas";
	var	REF_MAP_TEXTBOX = "#textbox";
	var	REF_MAP_GEOCODING = "#geocoding";
	var	REF_MAP_DIRECTIONS = "#directions";
		
	var	REF_PASSENGER_INPUT_START_LAT = "input[name$='passenger_start_location_lat']";
	var	REF_PASSENGER_INPUT_START_LONG = " input[name$='passenger_start_location_long']";
	var	REF_PASSENGER_INPUT_END_LAT = "input[name$='passenger_end_location_lat']";
	var	REF_PASSENGER_INPUT_END_LONG = " input[name$='passenger_end_location_long']";
	var	REF_DRIVER_INPUT_START = "input[name$='driver_start_location']";
	var	REF_DRIVER_INPUT_END = " input[name$='driver_end_location']";
	var	REF_DRIVER_VIA_WAYPOINTS = " input[name$='driver_via_waypoints']";
	var	REF_PASSENGER_ICON_URL = " input[name$='passenger_icon_url']";
	
	
	// load googlemap controller
	console.log("Instanciating google map controller...");
	
	var ctrlOptions = {
		};
	var mapOptions = {
			"map_canvas" : $(REF_MAP_CANVAS)[0],
			"textbox" : $(REF_MAP_TEXTBOX)[0],
			"geocoding" : $(REF_MAP_GEOCODING)[0],
			"directions" : $(REF_MAP_DIRECTIONS)[0],
	};
	mapOptions["draggable"] = false;
	mapOptions["selectable"] = false;
	mapOptions["showDirectionsPanel"] = true;

	var mapCtrl = new de.htw.fb4.imi.jumpup.trip.MapController(mapOptions,
				ctrlOptions);
	
	/*
	 * Draw Route and Display
	 */
	function showRoute() {
		var startPointValue = $(
				REF_DRIVER_INPUT_START)
				.val();
		var endPointValue = $(
				REF_DRIVER_INPUT_END)
				.val();
		var viaWaypoints = $(
				REF_DRIVER_VIA_WAYPOINTS)
				.val();
		var passengerStartLat = $(
				REF_PASSENGER_INPUT_START_LAT)
				.val();
		var passengerStartLong = $(
				REF_PASSENGER_INPUT_START_LONG)
				.val();
		var passengerEndLat = $(
				REF_PASSENGER_INPUT_END_LAT)
				.val();
		var passengersEndLong = $(
				REF_PASSENGER_INPUT_END_LONG)
				.val();
		var passengerIcon = $(
				REF_PASSENGER_ICON_URL)
				.val();

		var waypointsArray = null;
		if ("" != viaWaypoints) {
			waypointsArray = mapCtrl
					.toOverviewArray(viaWaypoints);
		}

		if (0 != startPointValue.length
				&& 0 != endPointValue.length) {
			console
					.log("main.js: showing new route");
			mapCtrl.showRoute(null,
					startPointValue,
					endPointValue,
					waypointsArray,
					false);
		};
		
		// display passenger's start and destination location markers
		if (0 != passengerStartLat.length
				&& 0 != passengerEndLat.length) {
			mapCtrl.gmap.showMarkerAtAddress(passengerStartLat, passengerStartLong, { "title" : "Passenger's start point", 
				"icon" : passengerIcon });
			mapCtrl.gmap.showMarkerAtAddress(passengerEndLat, passengersEndLong, { "title" : "Passenger's destination",
				"icon" : passengerIcon });
		}
	}
	
	// initially show persisted
	showRoute();
	
});