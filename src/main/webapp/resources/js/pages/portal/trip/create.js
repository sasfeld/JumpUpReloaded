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
	
	var ADDTRIP_REF_FORM = 'form[name="createTripForm"]';
	
	var	REF_ADDTRIP_INPUT_STARTCOORD = ADDTRIP_REF_FORM
			+ ' input[name="startCoordinate"]';
	var	REF_ADDTRIP_INPUT_ENDCOORD = ADDTRIP_REF_FORM
			+ ' input[name="endCoordinate"]';		
	var	REF_ADDTRIP_INPUT_START = ADDTRIP_REF_FORM
			+ ' .start_location';
	var	REF_ADDTRIP_INPUT_END = ADDTRIP_REF_FORM
			+ ' .end_location';
	
	// load googlemap controller
	console.log("Instanciating google map controller...");
	
	var ctrlOptions = {
			"input_start_coord" : $(REF_ADDTRIP_INPUT_STARTCOORD),
			"input_end_coord" : $(REF_ADDTRIP_INPUT_ENDCOORD),
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

							validStart = place.geometry.location;
							$(
									REF_ADDTRIP_INPUT_START)
									.val(
											validStart);
//							updateRoute();
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

							validEnd = place.geometry.location;
							$(
									REF_ADDTRIP_INPUT_END)
									.val(
											validEnd);
//							updateRoute();
						});
	}
	;
	
});