package com.mileem.model;

import java.util.ArrayList;

import android.os.Bundle;

public class Publication {
	
	public static String TRAN_TYPE = "transaction_type";
	public static String PROP_TYPE = "property_type";
	public static String ADDRESS = "address";
	public static String ZONE = "zone";
	public static String AREA = "area";
	public static String NUM_OF_ROOMS = "number_of_rooms";
	public static String EXPENSES = "expenses";
	public static String PRICE = "price";
	public static String AGE = "age";
	public static String CREATED_DATE = "created_at";
	public static String UPDATED_DATE = "updated_at";
	public static String PUBLICATION_DATE = "publication_date";
	public static String CURRENCY = "currency";
	public static String LATITUDE = "latitude";
	public static String LONGITUDE = "longitude";
	public static String RELEVANCE = "relevance";
	public static String USER = "user";
	public static String USER_EMAIL = "email";
	public static String USER_PHONE = "phone";
	public static String ATTACHMENTS = "publication_attachments";
	public static String URL_IMAGE = "image";
	public static String LIST_URL_IMAGE = "list_images";
	public static String URL = "url";
	public static String URL_VIDEO = "video_link";
	
	private String transaction_type;
	private String property_type;
	private String address;
	private String zone;
	private int area;
	private int number_of_rooms;
	private double price;
	private double expenses;
	private String currency;
	private double age;
	private double latitude;
	private double longitude;
	private String relevance;
	private String created_date;
	private String updated_date;
	private String publication_date;
	private String user_phone;
	private String user_email;
	private String url_video;
	private ArrayList<String> imageURL_list;
	
	public Publication(){
		transaction_type = "def_type";
		property_type = "def_type";
		address = "def_address";
		area = 0;
		number_of_rooms = 0;
		price = 0;
		expenses = 0;
		age = 0;
		imageURL_list = new ArrayList<String>();
		
	}
	
	public Publication(Bundle bundle){
		transaction_type = bundle.getString(TRAN_TYPE);
		property_type = bundle.getString(PROP_TYPE);
		address = bundle.getString(ADDRESS);
		zone = bundle.getString(ZONE);
		area = bundle.getInt(AREA);
		number_of_rooms = bundle.getInt(NUM_OF_ROOMS);
		price = bundle.getDouble(PRICE);
		expenses = bundle.getDouble(EXPENSES);
		currency = bundle.getString(CURRENCY);
		age = bundle.getDouble(AGE);
		latitude = bundle.getDouble(LATITUDE);
		longitude = bundle.getDouble(LONGITUDE);
		relevance = bundle.getString(RELEVANCE);
		created_date = bundle.getString(CREATED_DATE);
		updated_date = bundle.getString(UPDATED_DATE);
		publication_date = bundle.getString(PUBLICATION_DATE);
		user_phone = bundle.getString(USER_PHONE);
		user_email = bundle.getString(USER_EMAIL);
		url_video = bundle.getString(URL_VIDEO);
		imageURL_list = bundle.getStringArrayList(LIST_URL_IMAGE);
	}
	
	public ArrayList<String> getListImagesURL() {
		return imageURL_list;
	}

	public void setListimagesURL(ArrayList<String> urls_image) {
		this.imageURL_list = urls_image;
	}
	
	public String getTransaction_type() {
		return transaction_type;
	}
	
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	
	public String getProperty_type() {
		return property_type;
	}
	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public int getArea() {
		return area;
	}
	
	public void setArea(int area) {
		this.area = area;
	}
	
	public int getNumber_of_rooms() {
		return number_of_rooms;
	}
	
	public void setNumber_of_rooms(int number_of_rooms) {
		this.number_of_rooms = number_of_rooms;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getExpenses() {
		return expenses;
	}
	
	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}
	public double getAge() {
		return age;
	}
	
	public void setAge(double age) {
		this.age = age;
	}
	
	public String getPhone() {
		return user_phone;
	}
	
	public void setPhone(String phone) {
		this.user_phone = phone;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double d) {
		this.longitude = d;
	}

	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	public String getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	public void addUrl_Image(String url){
		this.imageURL_list.add(url);
	}
	
	public String getImageURLAtIndex(int pos){
		return this.imageURL_list.get(pos);
	}
	
	public String toString(){
        StringBuilder sb = new StringBuilder();
 
        sb.append(TRAN_TYPE).append(" : ").append(transaction_type).append('\n');
        sb.append(PROP_TYPE).append(" : ").append(property_type).append('\n');
        sb.append(ADDRESS).append(" : ").append(address).append('\n');
        sb.append(PRICE).append(" : ").append(price).append('\n');
        sb.append(USER_PHONE).append(" : ").append(user_phone).append('\n');
        sb.append(AGE).append(" : ").append(age).append('\n');
        sb.append(EXPENSES).append(" : ").append(expenses).append('\n');
        sb.append(NUM_OF_ROOMS).append(" : ").append(number_of_rooms).append('\n');
        sb.append(AREA).append(" : ").append(area).append('\n');
        
		return sb.toString();
		
	}
	
	public Bundle getBundle(){
		Bundle bundle = new Bundle();
		bundle.putString(TRAN_TYPE, transaction_type);
		bundle.putString(PROP_TYPE, property_type);
		bundle.putString(ADDRESS,address);
		bundle.putString(ZONE,zone);
		bundle.putInt(AREA,area);
		bundle.putInt(NUM_OF_ROOMS,number_of_rooms);
		bundle.putDouble(PRICE, price);
		bundle.putDouble(EXPENSES,expenses);
		bundle.putString(CURRENCY,currency);
		bundle.putDouble(AGE,age);
		bundle.putDouble(LATITUDE,latitude);
		bundle.putDouble(LONGITUDE,longitude);
		bundle.putString(RELEVANCE,relevance);
		bundle.putString(CREATED_DATE,created_date);
		bundle.putString(UPDATED_DATE,updated_date);
		bundle.putString(PUBLICATION_DATE,publication_date);
		bundle.putString(USER_PHONE, user_phone);
		bundle.putString(USER_EMAIL,user_email);
		bundle.putString(URL_VIDEO,url_video);
		bundle.putStringArrayList(LIST_URL_IMAGE, imageURL_list);

		return bundle;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public String getUrl_video() {
		return url_video;
	}

	public void setUrl_video(String url_video) {
		this.url_video = url_video;
	}
}
