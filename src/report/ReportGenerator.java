package report;

import java.text.SimpleDateFormat;
import java.util.*;
import model.CellPhone;
import model.CellPhoneUsage;
import model.UserCellPhoneUsage;
import util.DataReader;

public class ReportGenerator {

	private HashMap<String, UserCellPhoneUsage> phoneUsageData;
	private int totalMinutes;
	private double totalData;
	private Date maxDate;
	private Date minDate;
	public static SimpleDateFormat MONTH_YEAR_DATE_FORMAT = new SimpleDateFormat("MMM yyyy");
	

	public ReportGenerator(HashMap<String, UserCellPhoneUsage> phoneData, int minutes, double data, Date max, Date min) {
		phoneUsageData = phoneData;
		totalMinutes = minutes;
		totalData = data;
		maxDate = max;
		minDate = min;
	}

	public void generateHeader() {
		int numUsers = phoneUsageData.size();
		System.out.println("-- Cell Phone Usage Report Summary --");
		System.out.println("Report Run Date: "+new Date());	
		System.out.println("Data Starting Date: "+MONTH_YEAR_DATE_FORMAT.format(maxDate));
		System.out.println("Data Ending Date: "+MONTH_YEAR_DATE_FORMAT.format(minDate));
		System.out.println("Number of Phones: "+phoneUsageData.size());
		System.out.println("Total Minutes: "+totalMinutes);
		System.out.println("Total Data: "+String.format("%.2f", totalData));
		System.out.println("Average Minutes: "+totalMinutes/numUsers);
		System.out.println("Average Data: "+String.format("%.2f",totalData/numUsers));
	}

	public void generatePhoneUsers() {
		phoneUsageData.forEach((k,v) -> outputUserSummary(k));
	}

	public void outputUserSummary(String emp) {
		System.out.println(phoneUsageData.get(emp));
		System.out.println("Total Minutes Used: ");
		System.out.println("Total Data Used: ");
	}

	static public void main (String[] args) {
		DataReader dr = new DataReader();
		ReportGenerator rg = new ReportGenerator(dr.getPhoneUsageData(), dr.getTotalMinutes(), dr.getTotalData(), dr.getMaxDate(),dr.getMinDate());
		rg.generateHeader();
		//rg.generatePhoneUsers();
	}

}
