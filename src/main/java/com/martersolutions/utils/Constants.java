package com.martersolutions.utils;

public class Constants {

	public static final String homeDir = System.getProperty("user.home");

	public static final String BASE_URL = homeDir + "/Petnetwork/Upload/";

	public static final String OWNER_PROPIC_FOLDER = BASE_URL + "OwnerProPics/";

	public static final String PET_PROPIC_FOLDER = BASE_URL + "PetProPics/";

	public static final String FEEDPIC_FOLDER = BASE_URL + "FeedPics/";

	public static final String THUMBNAIL_FOLDER = BASE_URL + "Thumbnails/";

	public static final String ARTICAL_FOLDER = BASE_URL + "ArticlePics/";

	public static final String PET_VACCINE_FOLDER = BASE_URL + "PetVaccinePics/";

	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;

	public static final String SIGNING_KEY = "http://martersolutions.com";

	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String HEADER_STRING = "Authorization";
}
