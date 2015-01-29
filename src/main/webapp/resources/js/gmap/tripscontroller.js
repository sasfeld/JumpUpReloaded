/**
 * JumpUp.Me Car Pooling Application
 * 
 * Copyright (c) 2014-2015 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */

"use strict";

//namespaces
this.de = this.de || {};
this.de.htw = this.de.htw || {};
this.de.htw.fb4 = this.de.htw.fb4 || {};
this.de.htw.fb4.imi = this.de.htw.fb4.imi || {};
this.de.htw.fb4.imi.jumpup = this.de.htw.fb4.imi.jumpup || {};
this.de.htw.fb4.imi.jumpup.trip = this.de.htw.fb4.imi.jumpup.trip || {};

(function() {
			var
			REF_ACCORDION = "#accordion";
			// stores the only existing instance
			var _this;

			/*
			 * Create a new TripsController. - param options an array:
			 * getTripsUrl - the url to the json endpoint for the listing of all
			 * vehicles mapCtrl - the map controller, userId the id of the
			 * current logged in user
			 */
			de.htw.fb4.imi.jumpup.trip.TripsController = function(options) {
				_this = this;

				this.options = options;
				_this = this;
			};

			de.htw.fb4.imi.jumpup.trip.TripsController.prototype.setStartCoord = function(location) {
				this.startLatLng = location;
				console.log("trips.js -> setStartCoord -> location: "
						+ location);
			};

			de.htw.fb4.imi.jumpup.trip.TripsController.prototype.setEndCoord = function(location) {
				this.endLatLng = location;
				console.log("trips.js -> setEndCoord -> location: " + location);
			};

			/*
			 * HandleServerResponse will be called after a successfull request.
			 * @param data
			 */
			de.htw.fb4.imi.jumpup.trip.TripsController.prototype.handleServerResponse = function(data) {
					// TripInfo view helper
					var mapCtrl = _this.options.mapCtrl;
					// clear map
					mapCtrl.gmap.removeRoutes();
	
					var viewOptions = {
						"accordion" : $(REF_ACCORDION),
						"startLatLng" : _this.startLatLng,
						"endLatLng" : _this.endLatLng,
					};
					
					var tripInfoView = new de.htw.fb4.imi.jumpup.trip.TripInfo(viewOptions, mapCtrl.select);
	
					if (data.noTrips == true) {
						console.log(data);
						alert(data.message);
					}				
					else {
						// bad request?
						console.log(data);
						var multiple = true;
						// inform gui
						if (data.trips.length > 0) {
							console.log("Trips length: " + data.trips.length);
							for ( var tripIndex = 0; tripIndex < data.trips.length; tripIndex++) {								
								var singleTripQueryResult = data.trips[tripIndex];
								var trip = singleTripQueryResult.trip;
	
								var viaWaypoints = trip.viaWaypoints;
	
								var waypointsArray = new Array();
								if (viaWaypoints != null) {
									waypointsArray = viaWaypoints.split(";");
									waypointsArray.pop(); // last empty element
									for ( var i = 0; i < waypointsArray.length; i++) {
										waypointsArray[i] = "(" + waypointsArray[i]
												+ ")";
									}
									console.log("trips.js: waypoints array: "
											+ waypointsArray);
								}

								console.log("Showing route... " + tripIndex);
								mapCtrl.showRoute(trip.id, trip.startpoint,
										trip.endpoint, waypointsArray,
										multiple, function() {});

								// build selection view for user
								
								tripInfoView.addTrip(singleTripQueryResult);
							}
							;
	
						}
						;
					}
				;

				// activate accordion
				tripInfoView.reloadAccordion();
			};

			/*
			 * Error-Event method if the ajax request below fails.
			 */
			de.htw.fb4.imi.jumpup.trip.TripsController.prototype.handleError = function(xhr, ajaxOptions,
					thrownError) {
				console.log("TripsController: handleError");
				console.log(xhr);
				console.log(thrownError);
			};

			/*
			 * Fetch the trips to a given id.
			 */
			de.htw.fb4.imi.jumpup.trip.TripsController.prototype.fetchTrips = function(startPoint, endPoint, startCoord,
					endCoord, dateFrom, dateTo, priceFrom, priceTo, maxDistance) {
				console.log("TripsController: fetchTrips");
				console.log("startCoord: " + startCoord);
				console.log("endCoord: " + endCoord);
				console.log("startDate: " + dateFrom);
				console.log("endDate: " + dateTo);
				console.log("priceFrom: " + priceFrom);
				console.log("priceTo: " + priceTo);
				console.log("userId: " + this.options.userId);
				console.log("maxDistance: "+maxDistance);

				var __this = this;

				$.ajax({
					url : this.options.getTripsUrl,
					data : JSON.stringify({
						"startPoint" : startPoint,
						"latStartPoint" : startCoord.lat,
						"longStartPoint" : startCoord.long,
						"endPoint"   : endPoint,
						"latEndPoint" : endCoord.lat,
						"longEndPoint" : startCoord.long,
						"dateFrom" : dateFrom,
						"dateTo" : dateTo,
						"priceFrom" : priceFrom,
						"priceTo" : priceTo,
						"maxDistance" : maxDistance,
					}),
					contentType : 'application/json',
					type : "POST",
					success : this.handleServerResponse,
					error : this.handleError,
				});
			};
}())
