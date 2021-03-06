package report;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import model.UserCellPhoneUsage;
import util.DataReader;

/*
 * Generates a report to the specified output device (default is System.out) based on the data
 * passed/contained in the phoneUsageData Map<String, UserCellPhoneUsage>
 */
public class ReportGenerator {

	private HashMap<String, UserCellPhoneUsage> phoneUsageData;
	//Total cell phone minutes used by all users
	private int totalMinutes;
	//Total data usage from all users
	private double totalData;
	//Latest usage date
	private Date maxDate;
	//Earliest usage date
	private Date minDate;
	private PrintStream outputDevice;
	public static SimpleDateFormat MONTH_YEAR_DATE_FORMAT = new SimpleDateFormat("MMM yyyy");



	public ReportGenerator(HashMap<String, UserCellPhoneUsage> phoneData, int minutes, double data, Date max, Date min) {
		phoneUsageData = phoneData;
		totalMinutes = minutes;
		totalData = data;
		maxDate = max;
		minDate = min;
		//This could be configured to used a local printer instead of System.out
		outputDevice = System.out;
	}
	
	private void output(String line) {
		outputDevice.println(line);
	}

	/*
	 * Outputs the report summary data
	 */
	public void generateHeader() {
		int numUsers = phoneUsageData.size();
		output("-- Cell Phone Usage Report Summary --\n");
		output("Report Run Date: "+new Date());	
		output("Data Starting Date: "+MONTH_YEAR_DATE_FORMAT.format(maxDate));
		output("Data Ending Date: "+MONTH_YEAR_DATE_FORMAT.format(minDate));
		output("Number of Phones: "+phoneUsageData.size());
		output("Total Minutes: "+totalMinutes);
		output("Total Data: "+String.format("%.2f", totalData));
		output("Average Minutes: "+totalMinutes/numUsers);
		output("Average Data: "+String.format("%.2f",totalData/numUsers));
	}

	/*
	 * Outputs detail usage data from each phone/user
	 */
	public void generatePhoneUsers() {
		output("\n-- Cell Phone Usage Detail --");
		phoneUsageData.forEach((k,v) -> outputUserSummary(k));
	}

	/*
	 * Helper method to determine when we are done processing cell phone usage data
	 */
	private boolean done(int currentYear, int currentMonth, int maxYear, int maxMonth) {
		if (currentYear > maxYear) {
			return true;
		} else if (currentYear == maxYear ) {
			return currentMonth > maxMonth;
		} else {
			return false;
		}

	}

	/*
	 * For each phone/user, output monthly usage information
	 */
	public void outputUserSummary(String emp) {
		UserCellPhoneUsage usage = phoneUsageData.get(emp); 
		output(usage.toString());

		LocalDate tempMinDate = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate tempMaxDate = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int currentYear = tempMinDate.getYear();
		int currentMonth = tempMinDate.getMonthValue();
		int maxYear = tempMaxDate.getYear();
		int maxMonth = tempMaxDate.getMonthValue();
		StringBuffer columnHeader = new StringBuffer();
		StringBuffer userMonthlyMinutes = new StringBuffer();
		StringBuffer userMonthlyData = new StringBuffer();

		while (!done(currentYear, currentMonth, maxYear, maxMonth)) {
			columnHeader.append(currentMonth+"/"+currentYear+"\t");
			userMonthlyMinutes.append(usage.getMinutesTotal(currentMonth,currentYear)+"\t");
			userMonthlyData.append(String.format("%.2f", usage.getDataTotal(currentMonth,currentYear))+"\t");
			if (currentMonth == 12) {
				currentYear++;
				currentMonth = 1;
			} else {
				currentMonth++;
			}
		}
		output("Monthly Phone Usage\n");
		output(columnHeader.toString());
		output(userMonthlyMinutes.toString());

		output("\nMonthly Data Usage\n");
		output(columnHeader.toString());
		output(userMonthlyData.toString());
	}

	static public void main (String[] args) {
		try {
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath+"application.properties";
			Properties appProps = new Properties();
			appProps.load(new FileInputStream(appConfigPath));
			String phoneDataFile = appProps.getProperty("phoneDataFile");
			String phoneUsageFile = appProps.getProperty("usageDataFile");
			DataReader dr = new DataReader(phoneDataFile, phoneUsageFile);
			ReportGenerator rg = new ReportGenerator(dr.getPhoneUsageData(), dr.getTotalMinutes(), dr.getTotalData(), dr.getMaxDate(),dr.getMinDate());
			rg.generateHeader();
			rg.generatePhoneUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
