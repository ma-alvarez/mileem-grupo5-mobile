package com.mileem;

public class ConfigManager {

	public static String URL_SERVER = "http://192.168.56.1:3000";
	public static String URL_SEARCH = URL_SERVER + "/filterpublications.json";
	
	//Interface params query names
	public static String PAGE = "?page=";
	public static String COUNT = "&count=";
	public static String ORDER_BY = "&orderby=";
	public static String ORDER_TYPE = "&ordertype=";
	
	public static String TRAN_TYPE = "&ttrans=";
	public static String PROP_TYPE = "&tprop=";
	public static String ZONE = "&zone=";
	public static String PRICE_FROM = "&pricefrom=";
	public static String PRICE_TO = "&priceto=";
	public static String SUP_FROM = "&supfrom=";
	public static String SUP_TO = "&supto=";
	public static String ROOMS = "&rooms=";
	public static String PUB_TIME_FROM = "&pubtimefrom=";
	public static String PUB_TIME_TO = "&pubtimeto=";
	
	public static String[] COUNT_OPT = {"10","20","50"};
	public static String[] ORDER_BY_OPT = {"rel","pubtime","price"};
	public static String[] ORDER_TYPE_OPT = {"desc","asc"};
	public static String[] TRAN_TYPE_OPT = {"buy","rent"};
	public static String[] PROP_TYPE_OPT = {"house","department"};
	public static String[] ROOMS_OPT = {"1","2","3","4"};
	
}
