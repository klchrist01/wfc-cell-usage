package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class UserCellPhoneUsage {

	private CellPhone phone;
	private HashMap<UsageKey, MonthlyTotals> monthlyUsageData;
	private ArrayList<CellPhoneUsage> dailyRecords;
	
	public UserCellPhoneUsage(CellPhone phoneInfo) {
		phone = phoneInfo;
		monthlyUsageData = new HashMap<UsageKey, MonthlyTotals>();
		dailyRecords = new ArrayList<CellPhoneUsage>();
	}
	
	public void addUsageData(CellPhoneUsage usageRecord) {
		dailyRecords.add(usageRecord);
		LocalDate tempDate = usageRecord.getUsageDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		UsageKey newKey = new UsageKey(tempDate.getYear(), tempDate.getMonthValue());
		MonthlyTotals tempRecord = monthlyUsageData.get(newKey);
		if (tempRecord != null) {
			//System.out.println("Duplicate usage record for "+phone.getEmployeeId()+"Key="+newKey);
			tempRecord.add(usageRecord.getMinutesUsed(), usageRecord.getDataUsed());
		} else {
			MonthlyTotals newTot = new MonthlyTotals();
			newTot.data = usageRecord.getDataUsed();
			newTot.minutes = usageRecord.getMinutesUsed();
			newTot.year = tempDate.getYear();
			newTot.month = tempDate.getMonthValue();
			monthlyUsageData.put(newKey, newTot);
		}
		
	}
	
	public int getMinutesTotal(int month, int year) {
		UsageKey tempKey = new UsageKey(year, month);
		MonthlyTotals tempTotal = monthlyUsageData.get(tempKey);
		if (tempTotal == null) {
			return 0;
		} else {
			return tempTotal.minutes;
		}
	}
	
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
		
		public int hashCode() {
			return (Integer.toString(month)+Integer.toString(year)).hashCode();
		}
		public String toString() {
			return "UsageKey - Month="+month+" Year="+year;
		}
	}
	
	private class MonthlyTotals {
		int minutes;
		int month;
		int year;
		double data;
		
		void add(int min, double dat) {
			minutes+=min;
			data+=dat;
		}
		
		public String toString() {
			return "Monthly Totals: "+month+"/"+year+" Minutes: "+minutes+" Data: "+data;
		}
	}
}
