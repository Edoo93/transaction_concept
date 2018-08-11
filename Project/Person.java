package Project;

import java.util.HashMap;

/**
 * 
 * Class to create a customer or employee
 */

public class Person {
	
	private String name;
	private String lastName;
	private String age;
	private String persNumber;
	private String startDate;
	private String position;
	private String endDate;
	private String address;
	private String occupation;
	private String barcode;
	private String time;
	private boolean isEmployee;
	
	public Person(String name, String lastName, String age, String address, String persNumber){
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.address = address;
		this.persNumber = persNumber;
	}
	
	public Person(String name, String lastName, String age, boolean isEmployee, String address, String persNumber, String barcode){
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.isEmployee = isEmployee;
		this.address = address;
		this.persNumber = persNumber;
		this.barcode = barcode;
	}
	public Person(String name, String lastName, String persNumber, String position, String startDate, String endDate, String time) {
		this.name = name;
		this.lastName = lastName;
		this.position = position;
		this.startDate = startDate;
		this.endDate = endDate;
		this.persNumber = persNumber;
		this.time = time;
	}
	
	public Person(String name, String lastName, String persNumber, String position) {
		this.name = name;
		this.lastName = lastName;
		this.persNumber = persNumber;
		this.position = position;
		
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPersNumber() {
		return persNumber;
	}

	public void setPersNumber(String persNumber) {
		this.persNumber = persNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public String toString(){
		String str = "Namn: " + getName() + " Age: " + getAge() + " Address: " + getAddress() + " IDNUMBER: " + getPersNumber();
		return str;
	}
}
