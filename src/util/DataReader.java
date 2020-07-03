package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.CellPhone;
import model.CellPhoneUsage;

public class DataReader {

	private static SimpleDateFormat PHONE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static String PHONE_DATA_FILE = "./data/CellPhone.csv";
	public static String USAGE_DATA_FILE = "./data/CellPhoneUsageByMonth.csv";
	
	private String phoneDataFile;
	private String phoneUsageFile;
	private Date minDate = new Date();
	private Date maxDate = new Date();
	
	
	public DataReader() {
		phoneDataFile = PHONE_DATA_FILE;
		phoneUsageFile = USAGE_DATA_FILE;
	}
	
	public DataReader(String phoneFile, String usageFile) {
		phoneDataFile = phoneFile;
		phoneUsageFile = usageFile;
	}
	
	/*
	 * Reads in a CSV file of Cell Phone usage data. Assumes the following format:
	 * Column 1 - Employee ID
	 * Column 2 - Date of usage (MM/dd/yyyy)
	 * Column 3 - Total minutes used on the given date
	 * Column 4 - Total data used on the given date
	 * The first line of the file is assumed to contain a header.
	 * @return An ArrayList of CellPhoneUsage type records
	 */
	public ArrayList<CellPhoneUsage> getCellPhoneUsageData() {
		ArrayList<CellPhoneUsage> results = new ArrayList<CellPhoneUsage>();
		BufferedReader br;
		String[] tokens;
		int numTokens;
		Date usageDate = new Date();
		
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
				results.add(new CellPhoneUsage(tokens[0], 
						usageDate, 
						Integer.parseInt(tokens[2]), 
						Double.parseDouble(tokens[3])));
			}
			if (br != null) br.close();
		} catch (Exception e) {
			System.out.println("getCellPhoneUsageData "+e);
		}
		return results;
	}
	
	/*
	 * Reads in a CSV file of Cell Phone data. Assumes the following format:
	 * Column 1 - Employee ID
	 * Column 2 - Employee Name
	 * Column 3 - Purchase Date (yyyyMMdd)
	 * Column 4 - Phone model
	 * The first line of the file is assumed to contain a header.
	 * @return A HashMap of CellPhone type records using Employee ID as the key
	 */
	public HashMap<String,CellPhone> getCellPhoneData() {
		HashMap<String,CellPhone> results = new HashMap<String,CellPhone>();
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
				results.put(currentRecord.getEmployeeId(), currentRecord);
			}
			if (br != null) br.close();
		} catch (Exception e) {
			System.out.println("getCellPhoneData "+e);
		} 
		return results;
	}
	
	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public static void main(String args[]) {

		DataReader dr = new DataReader();

		Iterator<CellPhone> phoneIter = dr.getCellPhoneData().values().iterator();
		while(phoneIter.hasNext()) {
			System.out.println(phoneIter.next().toString());
		}

		Iterator<CellPhoneUsage> dataIter = dr.getCellPhoneUsageData().iterator();
		while(dataIter.hasNext()) {
			System.out.println(dataIter.next().toString());
		}
		
		System.out.println("Max date is: "+dr.getMaxDate());
		System.out.println("Min date is: "+dr.getMinDate());

	}
}
