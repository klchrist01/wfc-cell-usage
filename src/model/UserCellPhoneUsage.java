package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

public class UserCellPhoneUsage {

	private CellPhone phone;
	private HashMap<UsageKey, CellPhoneUsage> usageData;
	
	public UserCellPhoneUsage(CellPhone phoneInfo) {
		phone = phoneInfo;
		usageData = new HashMap<UsageKey, CellPhoneUsage>();
	}
	
	public void addUsageData(CellPhoneUsage usageRecord) {
		LocalDate tempDate = usageRecord.getUsageDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		UsageKey newKey = new UsageKey(tempDate.getYear(), tempDate.getMonthValue());
		usageData.put(newKey, usageRecord);
		
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
	}
}
