package model;

import java.util.Date;


public class CellPhone {

	private String employeeId;
	private String employeeName;
	private Date purchaseDate;
	private String model;

	/*
	 * Simple model class to store cell phone data read in from a CSV file
	 */
	public CellPhone(String emp, String name, Date purchase, String phonemodel) {
		employeeId = emp;
		employeeName = name;
		purchaseDate = purchase;
		model = phonemodel;
	}

	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String toString() {
		return "Employee ID: "+employeeId+"\nEmployee Name: "+employeeName+"\nPhone Purchase Date: "+CellPhoneUsage.USAGE_DATE_FORMAT.format(purchaseDate)+"\nPhone Model: "+model;
	}

}
