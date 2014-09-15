package com.mileem.model;

public class Publication {
	
	public static String TRAN_TYPE = "transaction_type";
	public static String PROP_TYPE = "property_type";
	public static String ADDRESS = "address";
	public static String AREA = "area";
	public static String NUM_OF_ROOMS = "number_of_rooms";
	public static String EXPENSES = "expenses";
	public static String PRICE = "price";
	public static String AGE = "age";
	public static String PHONE = "phone";
	
	private String transaction_type;
	private String property_type;
	private String address;
	private int area;
	private int number_of_rooms;
	private double price;
	private double expenses;
	private double age;
	private String phone;
	
	public Publication(){
		transaction_type = "def_type";
		property_type = "def_type";
		address = "def_address";
		area = 0;
		number_of_rooms = 0;
		price = 0;
		expenses = 0;
		age = 0;
		phone = "0000-0000";
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
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString(){
        StringBuilder sb = new StringBuilder();
 
        sb.append(TRAN_TYPE).append(" : ").append(transaction_type).append('\n');
        sb.append(PROP_TYPE).append(" : ").append(property_type).append('\n');
        sb.append(ADDRESS).append(" : ").append(address).append('\n');
        sb.append(PRICE).append(" : ").append(price).append('\n');
        sb.append(PHONE).append(" : ").append(phone).append('\n');
        sb.append(AGE).append(" : ").append(age).append('\n');
        sb.append(EXPENSES).append(" : ").append(expenses).append('\n');
        sb.append(NUM_OF_ROOMS).append(" : ").append(number_of_rooms).append('\n');
        sb.append(AREA).append(" : ").append(area).append('\n');
        
		return sb.toString();
		
	}
}
