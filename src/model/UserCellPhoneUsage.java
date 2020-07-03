package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
/*
 * Class that holds information for the CellPhone, daily usage records, and Monthly totals
 */
public class UserCellPhoneUsage {

	private CellPhone phone;
	private HashMap<UsageKey, MonthlyTotals> monthlyUsageData;
	private ArrayList<CellPhoneUsage> dailyRecords;
	
	public UserCellPhoneUsage(CellPhone phoneInfo) {
		phone = phoneInfo;
		monthlyUsageData = new HashMap<UsageKey, MonthlyTotals>();
		dailyRecords = new ArrayList<CellPhoneUsage>();
	}
	
	/*
	 * This method adds the CellPhoneUsage record to the dailyRecords ArrayList. It also increases the MonthlyTotals
	 * record for the given year and month
	 */
	public void addUsageData(CellPhoneUsage usageRecord) {
		dailyRecords.add(usageRecord);
		LocalDate tempDate = usageRecord.getUsageDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		UsageKey newKey = new UsageKey(tempDate.getYear(), tempDate.getMonthValue());
		MonthlyTotals tempRecord = monthlyUsageData.get(newKey);
		if (tempRecord != null) {
			tempRecord.add(usageRecord.getMinutesUsed(), usageRecord.getDataUsed());
		} else {
			MonthlyTotals newTot = new MonthlyTotals(usageRecord, tempDate);
			monthlyUsageData.put(newKey, newTot);
		}
		
	}
	
	/*
	 * Returns the total minutes used for the given month and year
	 */
	public int getMinutesTotal(int month, int year) {
		UsageKey tempKey = new UsageKey(year, month);
		MonthlyTotals tempTotal = monthlyUsageData.get(tempKey);
		if (tempTotal == null) {
			return 0;
		} else {
			return tempTotal.minutes;
		}
	}
	
	/*
	 * Returns the total data used for the given month and year
	 */
	public double getDataTotal(int month, int year) {
		UsageKey tempKey = new UsageKey(year, month);
		MonthlyTotals tempTotal = monthlyUsageData.get(tempKey);
		if (tempTotal == null) {
			return 0;
		} else {
			return tempTotal.data;
		}
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer(phone +"\n");
		Iterator<MonthlyTotals> iter = monthlyUsageData.values().iterator();
		while (iter.hasNext()) {
			result.append(iter.next());
			result.append("\n");
		}
		return result.toString();
	}
	
	/*
	 * Helper Key class for storing data in the monthlyUsageData Map.
	 */
	private class UsageKey {
		int month;
		int year;
		
		UsageKey(int yr, int mn) {
			month = mn;
			year = yr;
		}
		
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			UsageKey tempKey = (UsageKey)obj;
			if (tempKey.month == month && tempKey.year == year)
				return true;
			else
				return false;
		}
		
		/*
		 * Simple hash function based on concatenating month and year
		 */
		public int hashCode() {
			return (Integer.toString(month)+Integer.toString(year)).hashCode();
		}
		public String toString() {
			return "UsageKey - Month="+month+" Year="+year;
		}
	}
	
	/*
	 * Helper class to store minutes and data totals for the given month and year
	 */
	private class MonthlyTotals {
		int minutes;
		int month;
		int year;
		double data;
		
		MonthlyTotals(CellPhoneUsage usageRecord, LocalDate tempDate) {
			data = usageRecord.getDataUsed();
			minutes = usageRecord.getMinutesUsed();
			year = tempDate.getYear();
			month = tempDate.getMonthValue();
		}
		
		/*
		 * Adds to the existing values
		 */
		void add(int min, double dat) {
			minutes+=min;
			data+=dat;
		}
		
		public String toString() {
			return "MonthlyTotals: "+month+"/"+year+" Minutes: "+minutes+" Data: "+String.format("%.2f", data);
		}
	}
}
