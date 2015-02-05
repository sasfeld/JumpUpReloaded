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


$(document).ready(function() {
	var REF_MAP_CANVAS = "#map_canvas";
	var	REF_MAP_TEXTBOX = "#textbox";
	var	REF_MAP_GEOCODING = "#geocoding";
	var	REF_MAP_DIRECTIONS = "#directions";
	
	var ADDTRIP_REF_FORM = '';
		
	var	REF_ADDTRIP_INPUT_START = ADDTRIP_REF_FORM
			+ ' .start_location';
	var	REF_ADDTRIP_INPUT_END = ADDTRIP_REF_FORM
			+ ' .end_location';
	var	REF_ADDTRIP_VIA_WAYPOINTS = ADDTRIP_REF_FORM
	+ " input[name$='via_waypoints']";
	var REF_TRIPS_START_DATE = ADDTRIP_REF_FORM
	+ " input[name$='start_date_time']";
	var REF_TRIPS_END_DATE = ADDTRIP_REF_FORM
	+ " input[name$='end_date_time']";
	
	$(REF_TRIPS_START_DATE).datetimepicker(
			de.htw.fb4.imi.jumpup.ui.datePickerOptions || {}
		);
	$(REF_TRIPS_END_DATE).datetimepicker(
			de.htw.fb4.imi.jumpup.ui.datePickerOptions || {}
		);
		
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
	mapOptions["draggable"] = true;
	mapOptions["selectable"] = false;
	mapOptions["showDirectionsPanel"] = true;

	var mapCtrl = new de.htw.fb4.imi.jumpup.trip.MapController(mapOptions,
				ctrlOptions);
	
	/*
	 * Draw Route and Display
	 */
	function updateRoute() {
		var startPointValue = $(
				REF_ADDTRIP_INPUT_START)
				.val();
		var endPointValue = $(
				REF_ADDTRIP_INPUT_END)
				.val();
		var viaWaypoints = $(
				REF_ADDTRIP_VIA_WAYPOINTS)
				.val();

		var waypointsArray = null;
		if ("" != viaWaypoints) {
			waypointsArray = mapCtrl
					.toOverviewArray(viaWaypoints);
		}

		if (0 != startPointValue.length
				&& 0 != endPointValue) {
			console
					.log("main.js: showing new route");
			mapCtrl.showRoute(null,
					startPointValue,
					endPointValue,
					waypointsArray,
					false);
		}
		;
	}
	
	/*
	 * Bind GoogleMap Autcomplete on start location field
	 */
	if ($(REF_ADDTRIP_INPUT_START).length > 0) {
		console
				.log("main.js: Binding input field for start in Addtrip");
		// auto completion for start point
		mapCtrl.gmap
				.setAutocomplete(
						$(REF_ADDTRIP_INPUT_START),
						function(place) {
							// start place
							// changed
							console.log(place);
							var validStart = place.formatted_address;
							$(
									REF_ADDTRIP_INPUT_START)
									.val(
											validStart);
							updateRoute();
						});
	}
	;

	/*
	 * Bind GoogleMap Autcomplete on target location field
	 */
	if ($(REF_ADDTRIP_INPUT_END).length > 0) {
		console
				.log("main.js: Binding input field for end in Addtrip");
		// auto completion for end point
		mapCtrl.gmap
				.setAutocomplete(
						$(REF_ADDTRIP_INPUT_END),
						function(place) {
							// end place
							// changed

							var validEnd = place.formatted_address;
							$(
									REF_ADDTRIP_INPUT_END)
									.val(
											validEnd);
							updateRoute();
						});
	}
	;
	
	// initially show persisted route (EDIT mode)
	updateRoute();
	
});