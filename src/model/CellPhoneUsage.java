package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CellPhoneUsage {

	private String employeeId;
	private Date usageDate;
	private int minutesUsed;
	private double dataUsed;
	public static SimpleDateFormat USAGE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	public CellPhoneUsage(String emp, Date usageDate, int minutes, double data) {
		employeeId = emp;
		this.usageDate = usageDate;
		minutesUsed = minutes;
		dataUsed = data;
	}

	public Date getUsageDate() {
		return usageDate;
	}

	public void setUsageDate(Date usageDate) {
		this.usageDate = usageDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getMinutesUsed() {
		return minutesUsed;
	}

	public void setMinutesUsed(int minutesUsed) {
		this.minutesUsed = minutesUsed;
	}

	public double getDataUsed() {
		return dataUsed;
	}

	public void setDataUsed(double dataUsed) {
		this.dataUsed = dataUsed;
	}

	public String toString() {
		return "--CellPhoneUsage--\n"+"EmpID: "+employeeId+"\nUsage Date: "+CellPhoneUsage.USAGE_DATE_FORMAT.format(usageDate)+"\nMinutes: "+minutesUsed+"\nData: "+dataUsed;
	}

}
