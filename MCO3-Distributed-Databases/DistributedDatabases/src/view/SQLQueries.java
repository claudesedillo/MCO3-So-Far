package view;

public class SQLQueries {

	// TODO return result sets to controller
	// Country Codes - EUROPE
	public static String countryCodeEu1 = "CAN";
	public static String countryCodeEu2 = "USA";
	public static String countryCodeEu3 = "FRA";
	public static String countryCodeEu4 = "TJK";
	public static String countryCodeEu5 = "KAZ";
	
	// Country Codes - ASIA
	public static String countryCodeAsAf1 = "VNM";
	public static String countryCodeAsAf2 = "AUS";
	public static String countryCodeAsAf3 = "NER";
	public static String countryCodeAsAf4 = "MNG";
	public static String countryCodeAsAf5 = "STP";
	
	//disel for all regions
	public static String readAllData1 = "SELECT c.CountryCode, c.CountryName, cr.region, dy.data AS 'US$ Per Liter of Disel', dy.yearc " +
			"FROM country c NATURAL JOIN countryregion cr NATURAL JOIN databyyear dy NATURAL JOIN series s " +
			"WHERE seriesCode in (\"EP.PMP.DESL.CD\") " +
			"AND YearC IN(\"2012 [YR2012]\", \"2013 [YR2013]\",\"2014 [YR2014]\") " +
			"ORDER BY region";

	//gas for all regions
	public static String readAllData2 = "SELECT c.CountryCode, c.CountryName, cr.region, dy.data AS 'US$ Per Liter of Gas', dy.yearc " +
			"FROM country c NATURAL JOIN countryregion cr NATURAL JOIN databyyear dy NATURAL JOIN series s " +
			"WHERE seriesCode in (\"EP.PMP.SGAS.CD\") " +
			"AND YearC IN(\"2012 [YR2012]\", \"2013 [YR2013]\",\"2014 [YR2014]\") " +
			"ORDER BY region";

	//disel for europe
	public static String readEuropeData1 = "SELECT c.CountryCode, c.CountryName, cr.region, dy.data AS 'US$ Per Liter of Gas', dy.yearc " +
			"FROM country c NATURAL JOIN countryregion cr NATURAL JOIN databyyear dy NATURAL JOIN series s " +
			"WHERE seriesCode in (\"EP.PMP.DESL.CD\") " +
			"AND cr.region IN (\"Europe & Central Asia\", \"Latin America & Caribbean\", \"North America\") " +
			"AND YearC IN(\"2012 [YR2012]\", \"2013 [YR2013]\",\"2014 [YR2014]\") " +
			"ORDER BY region";

	//gas for europe
	public static String readEuropeData2 = "SELECT c.CountryCode, c.CountryName, cr.region, dy.data AS 'US$ Per Liter of Gas', dy.yearc " +
			"FROM country c NATURAL JOIN countryregion cr NATURAL JOIN databyyear dy NATURAL JOIN series s " +
			"WHERE seriesCode in (\"EP.PMP.SGAS.CD\") " +
			"AND cr.region IN (\"Europe & Central Asia\", \"Latin America & Caribbean\", \"North America\") " +
			"AND YearC IN(\"2012 [YR2012]\",\"2013 [YR2013]\",\"2014 [YR2014]\") " +
			"ORDER BY region";

	//disel for asia
	public static String readAsiaData1 = "SELECT c.CountryCode, c.CountryName, cr.region, dy.data AS 'US$ Per Liter of Gas', dy.seriescode " +
			"FROM country c NATURAL JOIN countryregion cr NATURAL JOIN databyyear dy NATURAL JOIN series s " +
			"WHERE seriesCode in (\"EP.PMP.DESL.CD\") " +
			"AND cr.region IN (\"East Asia & Pacific\", \"Middle East & North Africa\", \"South Asia\", \"Sub-Saharan Africa\") " +
			"AND YearC IN(\"2012 [YR2012]\", \"2013 [YR2013]\", \"2014 [YR2014]\") " +
			"ORDER BY region";
			
	//gas for asia
	public static String readAsiaData2 = "SELECT c.CountryCode, c.CountryName, cr.region, dy.data AS 'US$ Per Liter of Gas', dy.seriescode " +
			"FROM country c NATURAL JOIN countryregion cr NATURAL JOIN databyyear dy NATURAL JOIN series s " +
			"WHERE seriesCode in (\"EP.PMP.DESL.CD\") " +
			"AND cr.region IN (\"East Asia & Pacific\", \"Middle East & North Africa\", \"South Asia\", \"Sub-Saharan Africa\") " +
			"AND YearC IN(\"2012 [YR2012]\", \"2013 [YR2013]\", \"2014 [YR2014]\") " +
			"ORDER BY region";	
	
	
	//insert 2013 gas to canada
		public static String insertEuropeData1 = "INSERT INTO databyyear " +
				"VALUES('" + countryCodeEu1 + "', 'EMP.PMP.SGAS.CD', '2013 [YR 2013]', 1.00)";

	//insert 2013 gas to US
	public static String insertEuropeData2 = "INSERT INTO databyyear " +
			"VALUES('" + countryCodeEu2 + "', 'EP.PMP.SGAS.CD', '2013 [YR 2013]', 1)";

	//insert 2013 gas to Vietnam
	public static String insertAsiaAfricaData1 = "INSERT INTO databyyear " +
			"VALUES ('" + countryCodeAsAf1 + "', 'EP.PMP.SGAS.CD', '2013 [YR 2013]', 25)";

	//insert 2013 gas to Australia
	public static String insertAsiaAfricaData2 = "INSERT INTO databyyear " +
			"VALUES ('" + countryCodeAsAf2 + "', 'EP.PMP.SGAS.CD', '2013 [YR 2013]', 19)";

	//insert 2013 diesel data to france
	public static String insertAllData1 = "INSERT INTO databyyear " +
			"VALUES('"+ countryCodeEu3 +"', 'EP.PMP.DESL.CD', '2013 [YR 2013]', 1.18)";
	
	//insert 2013 gas data to mongolia
	public static String insertAllData2 = "INSERT INTO databyyear " +
			"VALUES('" + countryCodeAsAf4 + "', 'EP.PMP.SGAS.CD', '2013 [YR 2013]', 2.35)";
	
	//update 2014 diesel to canada
	public static String updateEuropeData1 = "UPDATE databyyear " +
			"SET data = data + 10 " +
			"WHERE yearc='2014 [YR2014]' AND countrycode='" + countryCodeEu1 + "' AND " +
			"seriescode = 'EP.PMP.DESL.CD';";

	//update 2014 gas to usa
	public static String updateEuropeData2 = "UPDATE databyyear " +
			"SET data = data + 10" +
			"WHERE yearc='2014 [YR2014]' AND countrycode='" + countryCodeEu2 + "' " +
			"AND seriescode = 'EP.PMP.SGAS.CD';";

	//update 2012 gas for vietnam
	public static String updateAsiaData1 = "UPDATE databyyear " +
			"SET data = data + 10 " +
			"WHERE yearc='2012 [YR2012]' AND countrycode='" + countryCodeAsAf1 + "' AND " +
			"seriescode = 'EP.PMP.SGAS.CD'";

	//update 2012 gas for australia
	public static String updateAsiaData2 = "UPDATE databyyear " +
			"SET data = data + 10 " +
			"WHERE yearc='2012 [YR2012]' AND countrycode='" + countryCodeAsAf2 + "' " +
			"AND seriescode = 'EP.PMP.SGAS.CD';";

	//update 2014 diesel for vietnam
	public static String updateAllData1 = "UPDATE databyyear " +
			"SET data = data + 10 " +
			"WHERE yearc = '2014 [YR2014]' AND (CountryCode = '" + countryCodeAsAf1 + "') " +
			"AND seriescode = 'EP.PMP.DESL.CD'; ";
	
	//update 2012 gas for kazakhstan
	public static String updateAllData2 = "UPDATE databyyear " +
			"SET data = data + 10 " +
			"WHERE yearc = '2012 [YR2012]' AND (CountryCode = '" + countryCodeEu5 + "') " +
			"AND seriescode = 'EP.PMP.SGAS.CD';";

	//delete 2012 gas from canada
	public static String deleteEuropeData1 = "DELETE FROM databyyear " +
			"WHERE yearc='2012 [YR2012]' AND countrycode='" + countryCodeEu1 + "' AND seriescode ='EP.PMP.SGAS.CD'; ";

	//delete 2012 diesel from usa
	public static String deleteEuropeData2 = "DELETE FROM databyyear " +
			"WHERE yearc='2012 [YR2012]' AND countrycode='" + countryCodeEu2 + "' " +
			"AND (seriescode ='EP.PMP.DESL.CD'); ";

	//delete 2012 diesel from vietnam
	public static String deleteAsiaData1 = "DELETE FROM databyyear " +
			"WHERE yearc='2014 [YR2014]' AND countrycode='" + countryCodeAsAf1 + "' AND seriescode ='EP.PMP.SGAS.CD'; ";

	//delete 2014 gas from australia
	public static String deleteAsiaData2 = "DELETE FROM databyyear " +
			"WHERE yearc='2014 [YR2014]' AND countrycode='" + countryCodeAsAf2 + "' " +
			"AND (seriescode ='EP.PMP.SGAS.CD'); ";
	
	//delete 2014 gas from france
	public static String deleteAllData1 = "DELETE FROM databyyear " +
			"WHERE yearc='2014 [YR2014]' AND (countrycode ='" + countryCodeEu3 + "') " +
			"AND seriescode = 'EP.PMP.SGAS.CD'; ";

	//delete 2012 gas from tajikistan
	public static String deleteAllData2 = "DELETE FROM databyyear " +
			"WHERE yearc='2012 [YR2012]' AND (countrycode='" + countryCodeEu4 + "') " +
			"AND (seriescode = 'EP.PMP.SGAS.CD');";

	public static String getRegionFromCountryCode = "SELECT region " +
			"FROM countryregion " +
			"WHERE countrycode = '";
}