# Cell Phone Usage Report
Write a report for cell phone usages in the company for a given year, and print the report to your local printer.

### Data Model
Database tables are in comma separated files with the header in the first row. 

**CellPhone.csv**
*	employeeId
*	employeeName
*	purchaseDate
*	model

**CellPhoneUsageByMonth.csv** (beware that there may be more than one record for an employee on a single date, so it is not a perfect data in a perfect world)
*	employeeId
*	year
*	month
*	minutesUsed
*	dataUsed

### Report  

The report should contain the following information

**Header Section**

*	Report Run Date
*	Number of Phones
*	Total Minutes
*	Total Data
*	Average Minutes
*	Average Data

**Details Section**

For each company cell phone provide the following information
*	Employee Id
*	Employee Name
*	Model
*	Purchase Date
*	Minutes Usage
    *	one column for each month
*	Data Usage
    *	one column for each month


