package report;

import java.util.*;
import model.CellPhone;
import model.CellPhoneUsage;
import model.UserCellPhoneUsage;
import util.DataReader;

public class ReportGenerator {

	private HashMap<String, UserCellPhoneUsage> phoneUsageData;
	

	public ReportGenerator(HashMap<String, UserCellPhoneUsage> phoneData) {
		phoneUsageData = phoneData;
	}

	public void generateHeader() {
		System.out.println("-- Cell Phone Usage Report Summary --");
		System.out.println("Date: ");		
		System.out.println("Number of Phones: ");
		System.out.println("Total Minutes: ");
		System.out.println("Total Data: ");
		System.out.println("Average Minutes: ");
		System.out.println("Average Data: ");
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
		ReportGenerator rg = new ReportGenerator(dr.getPhoneUsageData());
		rg.generateHeader();
		rg.generatePhoneUsers();
	}

}
