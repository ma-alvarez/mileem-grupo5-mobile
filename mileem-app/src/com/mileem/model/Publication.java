package com.mileem.model;

import java.util.ArrayList;

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
	public static String URL = "url";
	
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
	private String latitude;
	private String longitude;
	private String relevance;
	private String created_date;
	private String updated_date;
	private String publication_date;
	private String user_phone;
	private String user_email;
	private ArrayList<String> urls_image;
	
	public Publication(){
		transaction_type = "def_type";
		property_type = "def_type";
		address = "def_address";
		area = 0;
		number_of_rooms = 0;
		price = 0;
		expenses = 0;
		age = 0;
		urls_image = new ArrayList<String>();
		
	}
	
	public ArrayList<String> getUrls_image() {
		return urls_image;
	}

	public void setUrls_image(ArrayList<String> urls_image) {
		this.urls_image = urls_image;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
		this.urls_image.add(url);
	}
	
	public String getUrl_Image(int pos){
		return this.urls_image.get(pos);
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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
}
