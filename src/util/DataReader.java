package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.CellPhone;
import model.CellPhoneUsage;
import model.UserCellPhoneUsage;
/*
 * Reads in data from 2 CSV files.
 */

public class DataReader {

	private static SimpleDateFormat PHONE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static String PHONE_DATA_FILE = "./data/CellPhone.csv";
	public static String USAGE_DATA_FILE = "./data/CellPhoneUsageByMonth2.csv";
	
	private String phoneDataFile;
	private String phoneUsageFile;
	private Date minDate = new Date(); //Stores the earliest date from the usage records
	private Date maxDate = new Date(); //Stores the most recent date from the usage records
	private int totalMinutes;
	private double totalData;
	HashMap<String,UserCellPhoneUsage> phoneUsageData = new HashMap<String,UserCellPhoneUsage>();
	
	
	
	public DataReader() {
		phoneDataFile = PHONE_DATA_FILE;
		phoneUsageFile = USAGE_DATA_FILE;
	}
	
	public DataReader(String phoneFile, String usageFile) {
		phoneDataFile = phoneFile;
		phoneUsageFile = usageFile;
	}
	
	/*
	 * Reads in a CSV file of Cell Phone usage data. Also sums up usage minutes and data amounts.
	 * Assumes the following format in the CSV:
	 * Column 1 - Employee ID
	 * Column 2 - Date of usage (MM/dd/yyyy)
	 * Column 3 - Total minutes used on the given date
	 * Column 4 - Total data used on the given date
	 * The first line of the file is assumed to contain a header.
	 */
	private void getCellPhoneUsageData() {
		
		BufferedReader br;
		String[] tokens;
		int numTokens;
		int minutesUsed;
		double dataUsed;
		Date usageDate = new Date();
		CellPhoneUsage currentRecord;
		String currentEmp;
		
		try {
			maxDate = PHONE_DATE_FORMAT.parse("19000101");
			FileReader inFile = new FileReader(phoneUsageFile);
			br = new BufferedReader(inFile);
			String currentLine = br.readLine(); //Clear the header line
			while ((currentLine = br.readLine()) != null) {
				tokens = currentLine.split(",");
				numTokens = tokens.length;
				if (numTokens == 4) {
					try {
						usageDate = CellPhoneUsage.USAGE_DATE_FORMAT.parse(tokens[1]);
						if (usageDate.before(minDate)) {
							minDate = usageDate;
						}
						if (usageDate.after(maxDate)) {
							maxDate = usageDate;
						}
					} catch (ParseException pe) {
						System.out.println("CellPhone() "+pe);
					}

				} else {
					System.out.println("ERROR in DataReader.getCellPhoneUsageData() numTokens="+numTokens);
				}
				currentEmp = tokens[0];
				minutesUsed = Integer.parseInt(tokens[2]);
				dataUsed = Double.parseDouble(tokens[3]);
				currentRecord = new CellPhoneUsage(currentEmp,
						usageDate,
						minutesUsed,
						dataUsed);
				phoneUsageData.get(currentEmp).addUsageData(currentRecord);
				totalMinutes+=minutesUsed;
				totalData+=dataUsed;
			}
			if (br != null) br.close();
		} catch (Exception e) {
			System.out.println("getCellPhoneUsageData "+e);
		}
		
	}
	
	/*
	 * Reads in a CSV file of Cell Phone data. Assumes the following format:
	 * Column 1 - Employee ID
	 * Column 2 - Employee Name
	 * Column 3 - Purchase Date (yyyyMMdd)
	 * Column 4 - Phone model
	 * The first line of the file is assumed to contain a header.
	 */
	private void getCellPhoneData() {
		
		BufferedReader br;
		CellPhone currentRecord;
		String[] tokens;
		int numTokens;
		Date purchaseDate = new Date();
		try {
			FileReader inFile = new FileReader(phoneDataFile);
			br = new BufferedReader(inFile);
			String currentLine = br.readLine(); //Clear the header line

			while ((currentLine = br.readLine()) != null) {
				tokens = currentLine.split(",");
				numTokens = tokens.length;
				if (numTokens == 4) {
					try {
						purchaseDate = PHONE_DATE_FORMAT.parse(tokens[2]);
					} catch (ParseException pe) {
						System.out.println("DataReader.getCellPhoneData() "+pe);
					}

				} else {
					System.out.println("ERROR in DataReader.getCellPhoneData() numTokens="+numTokens);
				}
				currentRecord = new CellPhone(tokens[0], tokens[1], purchaseDate, tokens[3]);
				phoneUsageData.put(currentRecord.getEmployeeId(), new UserCellPhoneUsage(currentRecord));
			}
			if (br != null) br.close();
		} catch (Exception e) {
			System.out.println("getCellPhoneData "+e);
		} 
		
	}
	
	/*
	 * Loads the phone and usage data
	 */
	private void initializeData() {
		getCellPhoneData();
		getCellPhoneUsageData();
	}
	
	/*
	 * Returns a Map of cell phone usage data. The Map Key is the employee ID
	 */
	public HashMap<String,UserCellPhoneUsage> getPhoneUsageData() { 
		if(phoneUsageData.isEmpty()) {
			initializeData();
		}
		return phoneUsageData;
	}
	
	/*
	 * Returns the earliest date from the usage records
	 */
	public Date getMinDate() {
		return minDate;
	}

	/*
	 * Returns the most recent date from the cell phone usage records
	 */
	public Date getMaxDate() {
		return maxDate;
	}
	
	/*
	 * Total minutes for all records contained in the file
	 */
	public int getTotalMinutes() {
		return totalMinutes;
	}
	
	/*
	 * Total data used for all records contained in the file
	 */
	public double getTotalData() {
		return totalData;
	}

	/*
	 * Used for testing the class
	 */
	public static void main(String args[]) {

		DataReader dr = new DataReader();
		Iterator<UserCellPhoneUsage> dataIter = dr.getPhoneUsageData().values().iterator();
		while(dataIter.hasNext()) {
			System.out.println(dataIter.next().toString());
		}
		
		System.out.println("Max date is: "+dr.getMaxDate());
		System.out.println("Min date is: "+dr.getMinDate());

	}
}
