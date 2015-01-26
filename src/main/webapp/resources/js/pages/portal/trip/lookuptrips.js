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

$(document)
		.ready(
				function() {
					var REF_MAP_CANVAS = "#map_canvas";
					var REF_MAP_TEXTBOX = "#textbox";
					var REF_MAP_GEOCODING = "#geocoding";
					var REF_MAP_DIRECTIONS = "#directions";

					var ADDTRIP_REF_FORM = 'form[name="createTripForm"]';

					var REF_TRIPS_INPUT_START = ADDTRIP_REF_FORM
							+ ' .start_location';
					var REF_TRIPS_INPUT_END = ADDTRIP_REF_FORM
							+ ' .end_location';
					var	REF_TRIPS_INPUT_LAT_START = ADDTRIP_REF_FORM
					+ " input[name$='latitude_start']";
					var	REF_TRIPS_INPUT_LONG_START = ADDTRIP_REF_FORM
					+ " input[name$='longitude_start']";
					var	REF_TRIPS_INPUT_LAT_END = ADDTRIP_REF_FORM
					+ " input[name$='latitude_end']";
					var	REF_TRIPS_INPUT_LONG_END = ADDTRIP_REF_FORM
					+ " input[name$='longitude_end']";
					var REF_TRIPS_START_DATE = ADDTRIP_REF_FORM
					+ " input[name$='date_from']";
					var REF_TRIPS_END_DATE = ADDTRIP_REF_FORM
					+ " input[name$='date_to']";
					var REF_TRIPS_PRICE_FROM = ADDTRIP_REF_FORM
					+ " input[name$='price_from']"
					var REF_TRIPS_PRICE_TO = ADDTRIP_REF_FORM
					+ " input[name$='price_to']"
					var REF_TRIPS_MAX_DISTANCE = ADDTRIP_REF_FORM
					+ " input[name$='max_distance']"
			
					var REF_TRIPS_USER_ID = ADDTRIP_REF_FORM
							+ " input[name$='current_user_id']";

					// load googlemap controller
					console.log("Instanciating google map controller...");

					var ctrlOptions = {};
					var mapOptions = {
						"map_canvas" : $(REF_MAP_CANVAS)[0],
						"textbox" : $(REF_MAP_TEXTBOX)[0],
						"geocoding" : $(REF_MAP_GEOCODING)[0],
						"directions" : $(REF_MAP_DIRECTIONS)[0],
					};
					mapOptions["draggable"] = false;
					mapOptions["selectable"] = true;
					mapOptions["showDirectionsPanel"] = false;

					var mapCtrl = new de.htw.fb4.imi.jumpup.trip.MapController(
							mapOptions, ctrlOptions);

					/*
					 * ..:: initialize tripsController ::..
					 */
					var tripsOptions = {
						"getTripsUrl" : "rest/lookuptrips",
						"mapCtrl" : mapCtrl,
						"userId" : $(REF_TRIPS_USER_ID).val(),
					};
					var tripsCtrl = new de.htw.fb4.imi.jumpup.trip.TripsController(tripsOptions);

					if ($(REF_TRIPS_START_POINT).length > 0) {
						console
								.log("main.js: Binding input field for start in LookUpTrips");
						// auto completion for start point
						mapCtrl.gmap
								.setAutocomplete(
										$(REF_TRIPS_START_POINT),
										function(place) {
											validStart = place.geometry.location;
											$(REF_TRIPS_START_POINT).val(
													validStart);
											$(REF_TRIPS_START_COORD).val(
													place.geometry.location);
											tripsCtrl
													.setStartCoord(place.geometry.location);
										});
					}
					;
					if ($(REF_TRIPS_END_POINT).length > 0) {
						console
								.log("main.js: Binding input field for end in LookUpTrips");
						// auto completion for start point
						mapCtrl.gmap
								.setAutocomplete(
										$(REF_TRIPS_END_POINT),
										function(place) {
											validStart = place.geometry.location;
											$(REF_TRIPS_END_POINT).val(
													validStart);
											$(REF_TRIPS_END_COORD).val(
													place.geometry.location);
											tripsCtrl
													.setEndCoord(place.geometry.location);
										});
					}
					;
					/*
					 * ..:: lookUpTrips -> button event handler ::..
					 */
					$(REF_TRIPS_BTN)
							.click(
									(function() {
										console
												.log("lookuptrips ... button clicked...");
										startPoint = $(REF_TRIPS_START_POINT)
												.val();
										endPoint = $(REF_TRIPS_END_POINT).val();
										startCoord = $(REF_TRIPS_START_COORD)
												.val();
										endCoord = $(REF_TRIPS_END_COORD).val();
										startDate = $(REF_TRIPS_START_DATE)
												.val();
										endDate = $(REF_TRIPS_END_DATE).val();
										priceFrom = $(REF_TRIPS_PRICE_FROM)
												.val();
										priceTo = $(REF_TRIPS_PRICE_TO).val();
										maxDistance = $(REF_TRIPS_MAX_DISTANCE)
												.val();
										if (null != tripsCtrl) {
											tripsCtrl.fetchTrips(startPoint,
													endPoint, startCoord,
													endCoord, startDate,
													endDate, priceFrom,
													priceTo, maxDistance);
										}
									}));

				});