/**
 * JumpUp.Me Car Pooling Application
 *
 * Copyright (c) 2014 Sebastian Renner, Marco Seidler, Sascha Feldmann
 */
package de.htw.fb4.imi.jumpup.settings;

/**
 * <p>
 * </p>
 * 
 * @author <a href="mailto:me@saschafeldmann.de">Sascha Feldmann</a>
 * @since 10.11.2014
 * 
 */
public interface BeanNames
{
    String PASSWORD_HASH_GENERATOR = "password_hash_generator";

    String REGISTRATION_CONTROLLER = "registrationController";
    String USER_DETAILS_CONTROLLER = "userDetailsController";
    String REGISTRATION_CONFIRMATION_CONTROLLER = "registrationConfirmationController";
    String LOGIN_CONTROLLER = "loginController";
    String TRIP_REST_V1_CONTROLLER = "tripRestV1Controller";
    String USER_REST_V1_SECURED_CONTROLLER = "userSecuredRestV1Controller";
    String USER_REST_V1_UNSECUREDCONTROLLER = "userunsecuredRestV1Controller";

    String BOOKING_CONTROLLER = "bookingController";
    String BOOKING_LIST_CONTROLLER = "bookingListController";

    String WEBSITE_REGISTRATION = "website_registration";
    String WEBSITE_USER_DETAILS = "website_userDetails";
    String WEBSITE_TRIP_QUERY = "website_tripQuery";

    String TRANSLATOR = "translator";

    String PROFILE_VIEW_CONTROLLER = "profileViewController";

    String USERNAME_VALIDATOR = "usernameValidator";
    String PASSWORD_VALIDATOR = "passwordValidator";
    String REPEAT_PASSWORD_VALIDATOR = "repeatPasswordValidator";
    String PRENAME_VALIDATOR = "prenameValidator";
    String LASTNAME_VALIDATOR = "lastnameValidator";
    String EMAIL_VALIDATOR = "emailValidator";
    String USERNAME_OR_MAIL_VALIDATOR = "emailOrUsernameValidator";

    String USER_CONFIG_READER = "userConfigReader";
    String MAIL_CONFIG_READER = "mailConfigReader";
    String TRIP_CONFIG_READER = "tripConfigReader";

    String MAIL_SMTP_ADAPTER = "mailSmtpAdapter";

    String NAVIGATION_BEAN = "navigationBean";

    String DATE_OF_BIRTH = "dateOfBirthValidator";
    String MOBILE_NUMBER_VALIDATOR = "mobileNumberValidator";
    String SKYPE_VALIDATOR = "skypeValidator";
    String TOWN_VALIDATOR = "townValidator";
    String PLACE_OF_BIRTH_VALIDATOR = "placeOfBirthValidator";
    String COUNTRY_VALIDATOR = "countryValidator";
    String AVATAR_FILE_VALIDATOR = "avatarFileValidator";
    String LOCATION_VALIDATOR = "locationValidator";
    String LATITUDE_VALIDATOR = "latitudeValidator";
    String LONGITUDE_VALIDATOR = "longitudeValidator";
    String START_DATETIME_VALIDATOR = "startDatetimeValidator";
    String END_DATETIME_VALIDATOR = "endDatetimeValidator";
    String PRICE_VALIDATOR = "priceValidator";
    String OVERVIEW_PATH_VALIDATOR = "overviewPathValidator";
    String VIA_WAYPOINTS_VALIDATOR = "viaWaypointsValidator";
    String NUMBER_SEATS_VALIDATOR = "numberSeatsValidator";

    String WEBSITE_TRIP_CREATION = "websiteTripCreation";
    String TRIP_CREATION_CONTROLLER = "tripCreationController";
    String TRIP_QUERY_CONTROLLER = "tripQueryController";

    String TRIP_SEARCH_FILTER_CHAIN = "tripSearchFilterChain";
    String OVERLAPPING_TRIP_SEARCH_FILTER = "overlappingTripSearchChain";

    String LOCALE_HELPER = "localeHelper";

    String QUERY_RESULT_FACTORY = "queryResultFactory";
    String BOOKING_EJB = "bookingEjb";

    String TRIP_DAO = "tripDAO";
    String BOOKING_DAO = "bookingDAO";
    String USER_DAO = "userDAO";
    
    String GRAPH_BUILDER = "graphBuilder";
    
    String DIJKSTRA_SINGLE_PAIR = "dijkstraSinglePair";
    
    String BASIC_REST_CONTROLLER = "basicRestController";
    
    String TRIP_ENTITY_MAPPER = "tripEntityMapper";
    
    String RESPONSE_BUILDER = "responseBuilder";
    
    String WEBSITE_LOGIN = "websiteLogin";
    
    String REGISTRATION_SESSION = "registrationSession";

}
